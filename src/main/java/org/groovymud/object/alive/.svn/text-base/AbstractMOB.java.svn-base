package org.groovymud.object.alive;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.messages.MessageEvent;
import org.groovymud.engine.event.system.MovementEvent;
import org.groovymud.object.AbstractContainer;
import org.groovymud.object.room.AbstractRoom;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;

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
public abstract class AbstractMOB extends AbstractContainer implements Alive {

    private final transient static Logger logger = Logger.getLogger(AbstractRoom.class);

    private static final double CANNOT_PICK_UP = -1;

    private transient ExtendedTerminalIO terminalIO = null;

    private String gender;

    public void heartBeat() {
        doHeartBeat();
    }

    public ExtendedTerminalIO getTerminalOutput() {
        return terminalIO;
    }

    public void setTerminalOutput(ExtendedTerminalIO output) {
        this.terminalIO = output;
    }

    public void doEvent(IScopedEvent arg) {
        if (arg instanceof MessageEvent) {
            MessageEvent event = (MessageEvent) arg;
            if (getTerminalOutput() != null) {
                try {
                    getTerminalOutput().writeln(event.getMessage(this));
                } catch (IOException e) {
                    logger.error(e, e);
                }
            }
        }
    }

    public double getWeight() {
        return CANNOT_PICK_UP;
    }

    public EventScope getScope() {
        return EventScope.PLAYER_SCOPE;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

}
