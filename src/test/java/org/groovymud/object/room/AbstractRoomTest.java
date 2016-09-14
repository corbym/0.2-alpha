package org.groovymud.object.room;

import junit.framework.TestCase;

import org.groovymud.shell.MockExtendedTerminalIO;

public class AbstractRoomTest extends TestCase {

    public void testAddExit() {
        MockExtendedTerminalIO stream = new MockExtendedTerminalIO();

        Room room = new AbstractRoom() {

            public void doHeartBeat() {
                // TODO Auto-generated method stub

            }

            public void initialise() {
                // TODO Auto-generated method stub

            }

        };
        Exit exit1 = new Exit("dir1", "fromDir", "path/leads/") {

            @Override
            public void doHeartBeat() {
                // TODO Auto-generated method stub

            }

        };
        room.addExit(exit1);

        assertEquals(exit1, room.getExit("dir1"));
        assertNull(room.getExit("dir2"));
    }


}
