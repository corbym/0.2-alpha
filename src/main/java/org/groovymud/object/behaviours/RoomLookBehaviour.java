package org.groovymud.object.behaviours;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.wimpi.telnetd.io.terminal.ColorHelper;
import net.wimpi.telnetd.io.terminal.Colorizer;

import org.apache.log4j.Logger;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.room.Exit;
import org.groovymud.object.room.Room;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;
import org.groovymud.utils.NumberToWordConverter;

public class RoomLookBehaviour extends ContainerLookBehaviour {

    private static final Logger logger = Logger.getLogger(RoomLookBehaviour.class);

    protected void writeLookHeader(ExtendedTerminalIO stream, Alive looker, MudObject target) throws IOException {
        
        stream.writeln(ColorHelper.colorizeText(target.getName(), ColorHelper.GREEN));
    }

    public void writeLookBody(ExtendedTerminalIO stream, Alive looker, MudObject target) throws IOException {
        Room container = (Room) target;
        
        stream.writeln(ColorHelper.colorizeText(target.getDescription(), ColorHelper.BOLD));
        if (container.getInventoryHandler().getMudObjectsMap(false).size() > 0) {
            stream.write("You see: ");
            super.writeLookBody(stream, looker, target);
            stream.writeln(".");
        }
        Map<Object, Set<MudObject>> alive = container.getInventoryHandler().getMudObjectsMap(true);
        ContentsHelper contentsHelper = createContentsHelper();
        String aliveContents = contentsHelper.getContentsDescription(alive, looker, true);

        int aliveSize = container.getInventoryHandler().size(true) - 1;
        if (aliveSize > 0) {
            stream.write(ColorHelper.colorizeText(aliveContents, ColorHelper.GREEN));
        }
        if (aliveSize > 0) {
            stream.writeln(aliveSize > 1 ? " are here." : " is here.");
        }
    }

    protected void writeLookFooter(ExtendedTerminalIO stream, MudObject target) {
        Room room = (Room) target;
        Set exits = room.getExits().getMudObjects();
        try {
            stream.write(ColorHelper.colorizeText("There " + (exits.size() == 1 && exits.size() != 0 ? "is" : "are") + " " + NumberToWordConverter.convert(exits.size(), false) + " exit" + (exits.size() == 1 ? "" : "s") + ": ", ColorHelper.CYAN));
            Iterator iter = exits.iterator();
            int i = 0;
            while (iter.hasNext()) {
                Exit exit = (Exit) iter.next();
                stream.write(ColorHelper.colorizeText(exit.getDirection() + (i < exits.size() - 1 ? ", " : "\r\n" ), ColorHelper.WHITE));
                i++;
            }
        } catch (IOException e) {
            logger.error(e, e);
        }

    }

}
