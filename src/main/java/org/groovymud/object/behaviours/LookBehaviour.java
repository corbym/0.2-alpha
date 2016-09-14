package org.groovymud.object.behaviours;

import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;


public interface LookBehaviour {
    public void doLook(Alive looker, MudObject target);
        
}
