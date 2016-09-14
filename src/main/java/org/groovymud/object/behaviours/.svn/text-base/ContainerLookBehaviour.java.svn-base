package org.groovymud.object.behaviours;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.registry.InventoryHandler;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;


public class ContainerLookBehaviour extends LookBehaviourImpl {
    
    protected void writeLookHeader(ExtendedTerminalIO stream, Alive looker,  MudObject target) throws IOException {
        super.writeLookBody(stream, looker, target);
        stream.writeln("The " + target.getName() + " contains:");
    }

    public void writeLookBody(ExtendedTerminalIO stream, Alive looker,  MudObject target) throws IOException {
        ContentsHelper contentHelper = createContentsHelper();
        InventoryHandler handler = ((Container)target).getInventoryHandler();
        Map<Object, Set<MudObject>> dead = new HashMap<Object, Set<MudObject>>(handler.getMudObjectsMap(false));

        String deadContents = contentHelper.getContentsDescription(dead, looker, false);
        if (dead.size() > 0) {
            stream.write(deadContents);
        }
    }

    protected ContentsHelper createContentsHelper() {
        return new ContentsHelper();
    }

    @Override
    protected void writeLookFooter(ExtendedTerminalIO stream,  MudObject target) throws IOException {
        // TODO Auto-generated method stub
        stream.writeln(".");
    }

  

}
