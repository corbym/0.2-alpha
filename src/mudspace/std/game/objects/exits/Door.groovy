package std.game.objects.exits

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


import org.groovymud.object.room.Exit
import org.groovymud.object.alive.Alive
import org.groovymud.object.MudObject
import org.groovymud.object.room.Room
import org.groovymud.engine.event.IScopedEvent
import sun.security.action.GetLongAction

import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
import static org.groovymud.utils.MessengerUtils.sendMessageToPlayer
import org.groovymud.utils.Dice

import std.game.objects.exits.DoorEvent
import org.groovymud.object.room.Exit

/**
 * an example door
 * @author corbym
 *
 */
public class Door extends Exit{
    boolean open = false
    Door otherDoor = null;
    
    
    public Door(String direction, String arrivalDirection, String leadsToRoom) {
        super(direction, arrivalDirection, leadsToRoom)
        this.setName("${getDirection()} door");
        this.addShortName(direction);
        this.addShortName(direction.substring(0, 1));
    }
    
    void initialise() {
        open = false;
        setHitPoints(1000) 
        otherDoor = findOtherDoor(getArrivalDirection(), getDestinationRoom())
    }
    void doOpen(player){
        if(!open){
            sendMessageToRoom(player, "You open the ${getDirection()} door", "${player.getName()} opens the ${getDirection()} door.")
            open = true;
            if(otherDoor != null){
                otherDoor.open = true
            }
            fireDoorEvent(player)
        }else{
            sendMessageToPlayer(player, "You try to open the ${getDirection()} door but it is already open.")
        }        
    }
    
    void doClose(player){
       if(open){
           sendMessageToRoom(player, "You close the ${getDirection()} door", "${player.getName()} closes the ${getDirection()} door.")
           open = false;
           if(otherDoor != null){
               otherDoor.open = false
           }
           fireDoorEvent(player)
       }else{
           sendMessageToPlayer(player, "You try to close the ${getDirection()} door but it is already closed.")
       }    
    }
    
    boolean doCommand(String command, String argsAsStr, MudObject player) {
        if(command == "open" && argsAsStr =="door"){
            sendMessageToPlayer(player, "Which door do you want to open?")
            return true
        }
        return false
    }
    
    void findOtherDoor(String direction, roomScriptName){
        def otherRoom = getMudObjectAttendant().getObjectRegistry().getMudObject(roomScriptName)
        if(otherRoom != null){
            def otherExit = otherRoom.getExit(direction)
            if(otherExit instanceof Door){
                otherDoor = otherExit
                this.open = otherDoor.open
            }
        }
    }
    
    void fireDoorEvent(player){
        DoorEvent openEvent = new DoorEvent()
        openEvent.setSource(this)
        openEvent.setTargetRoomScriptName(getDestinationRoom())
        openEvent.setTargetDirection(getArrivalDirection())
        openEvent.setOpened(open)
        fireEvent(openEvent)
    }
    
    void doHeartBeat() {
        if(open){
            def dice = new Dice(1, 100);
            def roll = dice.roll()
            // do some emotes if the door is open .. 
            def message = null
            if((0..5).contains(roll)){
                message = "The ${getName()} creaks a little."
            }
            if((6..10).contains(roll)){
                message = "The ${getName()} swings on its hinges, as if a slight wind has caught it."
            }
            
            if(message != null){
                sendMessageToRoom(this, "", message);
            }
        }
    }
    
    void go(Alive player) {
        if(open){
            super.go(player)
        }else{
            sendMessageToRoom(player, "You try to go ${getDirection()} but the door is closed!", "${player.getName()} tries to go ${getDirection()} but the door is closed!")            
        }
        
    }
}
