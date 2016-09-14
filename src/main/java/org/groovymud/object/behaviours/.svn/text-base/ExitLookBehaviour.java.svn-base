package org.groovymud.object.behaviours;


import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.room.Exit;


public class ExitLookBehaviour implements LookBehaviour{

    public void doLook(Alive looker, MudObject target) {
        Exit exit = (Exit) target;

        target.getMudObjectAttendant().getObjectRegistry().getMudObject(exit.getDestinationRoom());
        
        target.getLookBehaviour().doLook(looker, target);
    }

}
