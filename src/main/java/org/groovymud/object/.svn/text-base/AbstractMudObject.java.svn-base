package org.groovymud.object;

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
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.action.DestroyEvent;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.object.behaviours.LookBehaviour;
import org.groovymud.object.behaviours.LookBehaviourImpl;
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.object.registry.Registry;
import org.groovymud.object.room.Location;
import org.groovymud.shell.command.CommandInterpreter;

public abstract class AbstractMudObject extends Observable implements MudObject {

    private Collection<? extends Principal> principals;

    private final static Logger logger = Logger.getLogger(AbstractMudObject.class);
    private String name;
    private String description;
    private double weight;

    private long hitPoints;

    private List<String> shortNames;

    private String shortDescription;

    private String scriptFileName;

    private transient Container currentContainer;

	protected Location currentLocation;

    private transient CommandInterpreter commandInterpreter;

    private transient MudObjectAttendant mudObjectAttendant;

    private transient Registry registry;

    private boolean requiresIndefinateArticle = true;

    public void heartBeat() {
        doHeartBeat();
    }

    public void initialise() {

    }

    public abstract void doHeartBeat();

    public final void addShortName(String shortName) {
        getShortNames().add(shortName);
    }

    public List<String> getShortNames() {
        if (shortNames == null) {
            shortNames = new LinkedList<String>();
        }
        return shortNames;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrentContainer(Container container) {
        if (container instanceof AbstractMudObject) {
			Location location = new Location(((AbstractMudObject) container).getScriptFileName());
			setCurrentLocation(location);
        }
        this.addObserver(container);
        this.currentContainer = container;
    }

    public void dest(boolean wep) {

        DestroyEvent event = new DestroyEvent();
        event.setSource(this);
        event.setScope(EventScope.GLOBAL_SCOPE);
        fireEvent(event);

    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    /**
     * loads objects using the mudattendant
     * 
     * @param script
     * @return
     */
    public MudObject load(String script) {
        return load(script, null);
    }

    /**
     * loads objects using the mudattendant
     * 
     * @param script
     * @return
     */
    public MudObject load(String script, List constructorArgs) {
        MudObject object = null;
        try {
            object = getMudObjectAttendant().load(script, constructorArgs, true);
        } catch (CompilationFailedException e) {
            logger.error(e, e);
        } catch (FileNotFoundException e) {
            logger.error(e, e);
        } catch (InstantiationException e) {
            logger.error(e, e);
        }
        return object;
    }

    /**
     * performs a command with the object. this is the default impl, returns
     * false. override to add your own actions
     * 
     * @param command
     *            - command name, get, put, look etc
     * @param argsAsString
     *            - arguments on this command
     * @param performingObject
     *            - the object that is doing this command
     */
    public boolean doCommand(String command, String argsAsString, MudObject performingObject) {
        return false;
    }

    public Collection<? extends Principal> getPrincipals() {
        return principals;
    }

    public void setPrincipals(Collection<? extends Principal> playerPrincipals) {
        this.principals = playerPrincipals;
    }

    public boolean requiresArticle() {
        return requiresIndefinateArticle;
    }

    public void setRequiresIndefinateArticle(boolean requiresIndefinateArticle) {
        this.requiresIndefinateArticle = requiresIndefinateArticle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setShortNames(List<String> shortNames) {
        this.shortNames = shortNames;
    }

    public long getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(long hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * does nothing, override to do events for this objec concrete so you don't
     * have to override if not wanted.
     */
    public void doEvent(IScopedEvent arg) {
    }

    public Container getCurrentContainer() {
        return currentContainer;
    }

    public String getContainerFileName() {
        Container currentContainer = this.getCurrentContainer();
        if (currentContainer instanceof MudObject) {
            return ((AbstractMudObject) getCurrentContainer()).getScriptFileName();
        }
        return null;
    }

    public String getScriptFileName() {
        return scriptFileName;
    }

    public void setScriptFileName(String scriptFileName) {
        this.scriptFileName = scriptFileName;
    }

	public Location getCurrentLocation() {
		return currentLocation;
    }

	public void setCurrentLocation(Location location) {
		this.currentLocation = location;
    }

    public CommandInterpreter getCommandInterpreter() {
        return commandInterpreter;
    }

    public void setCommandInterpreter(CommandInterpreter commandInterpreter) {
        this.commandInterpreter = commandInterpreter;
    }

    public MudObjectAttendant getMudObjectAttendant() {
        return mudObjectAttendant;
    }

    public void setMudObjectAttendant(MudObjectAttendant mudObjectAttendant) {
        this.mudObjectAttendant = mudObjectAttendant;
    }

    public LookBehaviour getLookBehaviour() {       
        return new LookBehaviourImpl();
    }


}
