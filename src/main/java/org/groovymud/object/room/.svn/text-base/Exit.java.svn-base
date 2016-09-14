package org.groovymud.object.room;

import groovy.util.ResourceException;
import groovy.util.ScriptException;

import org.apache.log4j.Logger;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.messages.MessageEvent;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.engine.event.system.MovementEvent;
import org.groovymud.object.AbstractMudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.behaviours.LookBehaviour;
import org.groovymud.object.behaviours.LookBehaviourImpl;

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
public class Exit extends AbstractMudObject {

    private final static Logger logger = Logger.getLogger(Exit.class);
    private String direction;
    private String arrivalDirection;

    private String destinationRoom;
    private static LookBehaviour lookBehaviour;
    public Exit(String direction, String arrivalDirection, String leadsToRoom) {
        super();
        this.destinationRoom = leadsToRoom;
        this.direction = direction;
        this.arrivalDirection = arrivalDirection;
        this.setName(direction);
        this.addShortName(direction);
        this.addShortName(direction.substring(0, 1));
    }

    @Override
    public String getDescription() {
        // get the description of the room this exit leads to
        return super.getDescription();
    }

    public void go(Alive object) {
        (castToObservable(object)).fireEvent(new MovementEvent(object.getCurrentContainer(), this.getDestinationRoom(), object, this));
    }

    public void doEvent(IScopedEvent event) {

        if (event instanceof MovementEvent) {
            MovementEvent movement = (MovementEvent) event;
            if (movement.getSourceExit().equals(this)) {
                Room sourceRoom = (Room) movement.getSource();
                Room foundRoom = (Room) movement.getFoundRoom();
                Alive movingObject = movement.getMovingObject();
                doMove(sourceRoom, foundRoom, movingObject);
            }
        }
    }

    protected void doMove(Room sourceRoom, Room foundRoom, Alive movingObject) {
        Observable observable = castToObservable(movingObject);
        if (foundRoom != null) {
            MessageEvent event = createMessageEvent();
            event.setScope(EventScope.ROOM_SCOPE);
            event.setSource(movingObject);
            event.setSourceMessage("You go " + getDirection() + ".");
            event.setScopeMessage(movingObject.getDepartureMessage(getDirection()));
            observable.fireEvent(event);

            foundRoom.addItem(movingObject);

            MessageEvent arriveEvent = createMessageEvent();
            arriveEvent.setScope(EventScope.ROOM_SCOPE);
            arriveEvent.setSource(movingObject);
            arriveEvent.setSourceMessage(null);
            arriveEvent.setScopeMessage(movingObject.getArrivalMessage(getArrivalDirection()));
            observable.fireEvent(arriveEvent);

            try {
                getCommandInterpreter().doCommand("look", null, movingObject);
            } catch (ResourceException e) {
                logger.error(e, e);
            } catch (ScriptException e) {
                // TODO Auto-generated catch block
                logger.error(e, e);
            }
        }
    }

    public Observable castToObservable(Alive movingObject) {
        return (Observable) movingObject;
    }

    protected String[] createMovingStringArray(Alive movingObject, String direction) {
        return new String[] { movingObject.getName(), getDirection() };
    }

    protected MessageEvent createMessageEvent() {
        return new MessageEvent(EventScope.ROOM_SCOPE);
    }

    public String getDestinationRoom() {
        return destinationRoom;
    }

    public void setDestinationRoom(String destinationRoom) {
        this.destinationRoom = destinationRoom;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getArrivalDirection() {
        return arrivalDirection;
    }

    public void setArrivalDirection(String arrivalDirection) {
        this.arrivalDirection = arrivalDirection;
    }

    @Override
    public void doHeartBeat() {
       
    }

    public void setLookBehaviour(LookBehaviour lookBehaviour) {
        if(Exit.lookBehaviour == null){
            Exit.lookBehaviour = new LookBehaviourImpl();
        }
        Exit.lookBehaviour = lookBehaviour;
    }

    public LookBehaviour getLookBehaviour() {
        return lookBehaviour;
    }

}
