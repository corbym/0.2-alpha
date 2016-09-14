package org.groovymud.object;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.alive.Player;
import org.groovymud.shell.MockExtendedTerminalIO;

public class AbstractContainerTest extends TestCase {

    boolean called = false;
    MockExtendedTerminalIO mockIO;

    protected void setUp() throws Exception {
        mockIO = new MockExtendedTerminalIO();
    }

    protected AbstractMudObject createObject() {
        AbstractMudObject object = new AbstractMudObject() {

            public void initialise() {

            }

            public void doHeartBeat() {
                // TODO Auto-generated method stub

            }

            public boolean hasCommand(String command) {
                // TODO Auto-generated method stub
                return false;
            }

        };
        return object;
    }

    private Alive createNewPlayer() {
        MockControl playerCtrl = MockControl.createNiceControl(Player.class);
        return (Alive) playerCtrl.getMock();
    }

    public void testUpdate() {
        MockControl eCtrl = MockControl.createControl(IScopedEvent.class);

        final IScopedEvent event = (IScopedEvent) eCtrl.getMock();
        event.getScope();
        eCtrl.setReturnValue(EventScope.CONTAINER);
        eCtrl.replay();

        final Observable ob = new Observable() {

            @Override
            public void doEvent(IScopedEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void fireEvent(IScopedEvent event) {
                // TODO Auto-generated method stub

            }

        };
        AbstractContainer container = new AbstractContainer() {

            public void doHeartBeat() {
                // TODO Auto-generated method stub

            }

            public void initialise() {
                // TODO Auto-generated method stub

            }

            @Override
            public void doEvent(IScopedEvent event) {
                // TODO Auto-generated method stub
                called = true;
            }

        };
        container.update(ob, event);
        assertTrue(called);
    }

    public void testNotifyContents() {

    }
}
