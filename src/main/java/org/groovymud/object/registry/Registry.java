package org.groovymud.object.registry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.groovymud.engine.JMudEngine;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.action.DestroyEvent;
import org.groovymud.engine.event.action.SaveEvent;
import org.groovymud.engine.event.messages.MessageEvent;
import org.groovymud.engine.event.observer.IObservable;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.engine.event.observer.Observer;
import org.groovymud.engine.event.system.MovementEvent;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.alive.Player;
import org.groovymud.object.room.Room;
import org.groovymud.shell.command.CommandInterpreter;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;
import org.groovymud.shell.telnetd.LoginShell;

/* Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
public class Registry implements Container, Observer {

    private final static Logger logger = Logger.getLogger(Registry.class);

    private Map<LoginShell, Player> activePlayerHandles;

    private InventoryHandler inventoryHandler;

    private JMudEngine mudEngine;

    private MudObjectAttendant mudObjectAttendant;

    private CommandInterpreter commandInterpreter;

    public void addRoom(Room room) {

        getInventoryHandler().addMudObject(room.getScriptFileName(), room);
        room.setCurrentContainer(this);
        addItem(room);
        castRoomToObservable(room).addObserver(this);
    }

    protected IObservable castRoomToObservable(Room room) {
        return ((IObservable) room);
    }

    public void addActivePlayer(LoginShell shell, Player player) {
        addActivePlayerHandle(shell, player);
        addItem(player);
        if (logger.isDebugEnabled()) {
            logger.debug("There are " + getActivePlayerHandles().size() + " connections active and " + (Runtime.getRuntime().freeMemory() / 1000) + " kBytes free");
        }
    }

    public void removeaActivePlayer(Player player) {
        getActivePlayerHandlesMap().values().remove(player);
    }

    public MudObject removeItem(MudObject object) {
        if (object instanceof Player) {
            removeaActivePlayer((Player) object);
        }
        return getInventoryHandler().removeMudObject(object);
    }

    public JMudEngine getMudEngine() {
        return mudEngine;
    }

    public void setMudEngine(JMudEngine mudEngine) {
        this.mudEngine = mudEngine;
    }

    public void addActivePlayerHandle(LoginShell shell, Player player) {
        getActivePlayerHandlesMap().put(shell, player);
    }

    public Player getPlayerByHandle(LoginShell handle) {
        return (Player) getActivePlayerHandlesMap().get(handle);
    }

    protected Map<LoginShell, Player> getActivePlayerHandlesMap() {
        if (activePlayerHandles == null) {
            Map synchronizedMap = Collections.synchronizedMap(new WeakHashMap<LoginShell, Player>());
            activePlayerHandles = synchronizedMap;
        }
        return activePlayerHandles;
    }

    protected void notifyContents(IObservable observable, IScopedEvent event) {
        Iterator contentIterator = getItems().iterator();
        while (contentIterator.hasNext()) {
            Object o = contentIterator.next();
            if (o instanceof Observer) {
                castToObserver(o).update(observable, event);
            }
        }
    }

    protected Observer castToObserver(Object o) {
        return ((Observer) o);
    }

    public void update(IObservable object, IScopedEvent arg) {
        doUpdate(object, arg);
    }

    public void doUpdate(IObservable o, IScopedEvent arg) {
        if (arg instanceof MessageEvent) {
            notifyAll(arg);
        }
        if (arg instanceof MovementEvent) {
            doMovementEvent(arg);
        }
        if (arg instanceof DestroyEvent) {
            doDestroyEvent(arg);
        }
        if (arg instanceof SaveEvent) {
            doSaveEvent(arg);
        }

    }

    protected void doSaveEvent(IScopedEvent arg) {
        SaveEvent ev = (SaveEvent) arg;
        getMudEngine().getObjectAttendant().savePlayerData((Player) ev.getSource());
    }

    protected void notifyAll(IScopedEvent arg) {
        MessageEvent mEvent = (MessageEvent) arg;
        Map everyone = getInventoryHandler().getMudObjectsMap(true);
        Iterator all = everyone.values().iterator();
        while (all.hasNext()) {
            HashSet nextSet = (HashSet) all.next();
            Iterator setIterator = nextSet.iterator();
            while (setIterator.hasNext()) {
                Alive obj = (Alive) setIterator.next();
                ExtendedTerminalIO term = obj.getTerminalOutput();
                if (term == null) {
                    continue;
                }
                if (mEvent.getSource() == obj) {
                    try {
                        term.writeln(mEvent.getSourceMessage());
                    } catch (IOException e) {
                        logger.error(e, e);
                    }
                } else {
                    try {
                        if (mEvent.getScopeMessage() != null) {
                            term.writeln(mEvent.getScopeMessage());
                        }
                        if (mEvent.getTargets().contains(obj)) {
                            term.writeln(mEvent.getTargetMessage());
                        }
                    } catch (IOException e) {
                        logger.error(e, e);
                    }
                }
            }
        }
    }

    protected void doDestroyEvent(IScopedEvent arg) {
        DestroyEvent ev = (DestroyEvent) arg;
        MudObject object = (MudObject) ev.getSource();
        if (object instanceof Player) {
            fireLeavingMessage((Player) object);
        }
        if (object instanceof Container) {
            unregisterContents(((Container) object));
        }
        removeItem(object);
        object.getCurrentContainer().removeItem(object);
        if (object instanceof Player) {
            try {
                ((Player) object).getTerminalOutput().close();
            } catch (IOException e) {
                logger.error(e, e);
            }
        }
    }

    public void unregisterContents(Container container) {
        for (MudObject obj : container.getInventoryHandler().getMudObjects()) {
            // remove objects from the game, but not from the container 
            if (obj instanceof Container) {
                unregisterContents(castToContainer(obj));
            }
            removeItem(obj);
        }
    }

    private Container castToContainer(MudObject obj) {
        return ((Container) obj);
    }

    protected void fireLeavingMessage(Player player) {
        MessageEvent leaves = new MessageEvent(EventScope.GLOBAL_SCOPE);
        leaves.setScopeMessage("[" + player.getName() + " leaves GroovyMud]");
        leaves.setSource(player);
        leaves.setSourceMessage("Thanks for visiting!!");
        ((Observable) player).fireEvent(leaves);
    }

    protected synchronized void doMovementEvent(IScopedEvent arg) {
        MovementEvent event = (MovementEvent) arg;
        Alive movingObject = event.getMovingObject();
        Container foundRoom = (Container) getMudObject(event.getRoomLocation());
        if (foundRoom == null) {
            Exception anException = null;
            try {
                MudObjectAttendant attendant = getMudObjectAttendant();
                foundRoom = (Container) attendant.load(event.getRoomLocation(), true);

            } catch (CompilationFailedException e) {
                logger.error(e, e);
                anException = e;
            } catch (FileNotFoundException e) {
                logger.error(e, e);
                anException = e;
            } catch (InstantiationException e) {
                logger.error(e, e);
                anException = e;
            } finally {
                if (anException != null) {
                    try {
                        movingObject.getTerminalOutput().writeln(anException.getMessage());
                    } catch (IOException e) {
                        logger.error(e, e);
                    }
                }
            }
        }
        event.setFoundRoom(foundRoom);
        // pass the event back to the room, with an altered scope
        event.setScope(EventScope.CONTAINER);
        ((Observable) event.getSource()).doEvent(event);
    }

    public String getCurrentContainerFileName() {
        return null;
    }

    public MudObjectAttendant getMudObjectAttendant() {
        return mudObjectAttendant;
    }

    public void setCommandInterpreter(CommandInterpreter interpreter) {
        this.commandInterpreter = interpreter;
    }

    public void setMudObjectAttendant(MudObjectAttendant mudObjectAttendant) {
        this.mudObjectAttendant = mudObjectAttendant;
    }

    public Map getMudObjectsMap() {
        return getInventoryHandler().getMudObjectsMap();
    }

    public HashSet<LoginShell> getActivePlayerHandles() {
        return new HashSet<LoginShell>(getActivePlayerHandlesMap().keySet());
    }

    public void addItem(MudObject object) {
        getInventoryHandler().addMudObject(object);
    }

    public MudObject getMudObject(String name) {
        return getInventoryHandler().getMudObject(name);
    }

    public Set<MudObject> getItems(String name) {
        return getInventoryHandler().getMudObjects(name);
    }

    public Set<MudObject> getItems() {
        return getInventoryHandler().getMudObjects();
    }

    public void setInventoryHandler(InventoryHandler inventoryHandler) {
        this.inventoryHandler = inventoryHandler;
    }

    public InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }

    public EventScope getScope() {
        // TODO Auto-generated method stub
        return EventScope.GLOBAL_SCOPE;
    }

    public CommandInterpreter getCommandInterpreter() {
        return commandInterpreter;
    }

}
