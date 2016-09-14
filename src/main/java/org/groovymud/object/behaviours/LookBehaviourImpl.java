/**
 * 
 */
package org.groovymud.object.behaviours;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;
import static org.groovymud.utils.WordUtils.affixIndefiniteArticle;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;

/**
 * @author matt
 *
 */
public class LookBehaviourImpl implements LookBehaviour{

    private final static Logger logger = Logger.getLogger(LookBehaviourImpl.class);

    public void doLook(Alive looker, MudObject target) {
        try {
            ExtendedTerminalIO stream = looker.getTerminalOutput();
            writeLookHeader(stream, looker, target);
            writeLookBody(stream, looker, target);
            writeLookFooter(stream, target);
        } catch (IOException e) {
            logger.error(e, e);
        } 
    }

    void writeLookHeader(ExtendedTerminalIO stream, Alive looker, MudObject object) throws IOException {
        stream.writeln("You see " + affixIndefiniteArticle(object.getName()) + ":");
    }

    void writeLookBody(ExtendedTerminalIO stream, Alive looker, MudObject object) throws IOException {
        if (object.getDescription() != null) {
            stream.writeln(object.getDescription());
        }
    }

    void writeLookFooter(ExtendedTerminalIO stream, MudObject object) throws IOException {
    }
   

}
