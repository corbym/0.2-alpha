package org.groovymud.object.room;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import junit.framework.TestCase;

import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.messages.MessageEvent;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.object.alive.Alive;
import org.groovymud.shell.command.CommandInterpreter;

public class ExitTest extends TestCase {

    int call = 0;

    public void testDoMove() throws ResourceException, ScriptException {
        final String[] mockMovingStringArray = new String[] { new String("moved") };

        MockClassControl leaveEventCtrl = (MockClassControl) MockClassControl.createControl(MessageEvent.class, new Class[] { EventScope.class }, new Object[] { EventScope.ROOM_SCOPE });
        MockClassControl arriveEventCtrl = (MockClassControl) MockClassControl.createControl(MessageEvent.class, new Class[] { EventScope.class }, new Object[] { EventScope.ROOM_SCOPE });

        MockControl srControl = MockControl.createControl(Room.class);
        MockControl fndControl = MockControl.createControl(Room.class);

        Room sourceRoom = (Room) srControl.getMock();
        Room foundRoom = (Room) fndControl.getMock();

        final MessageEvent leaveEvent = (MessageEvent) leaveEventCtrl.getMock();
        final MessageEvent arriveEvent = (MessageEvent) arriveEventCtrl.getMock();
        MockControl obsCtrl = MockClassControl.createControl(Observable.class);
        final Observable movingObject = (Observable) obsCtrl.getMock();
        movingObject.fireEvent(leaveEvent);
        obsCtrl.setDefaultVoidCallable();
        movingObject.fireEvent(arriveEvent);
        obsCtrl.setDefaultVoidCallable();
        
        MockControl aliveCtrl = MockClassControl.createControl(Alive.class);
        final Alive aliveMovingObject = (Alive) aliveCtrl.getMock();


        aliveMovingObject.getDepartureMessage("north");
        aliveCtrl.setDefaultReturnValue("boing");
        aliveMovingObject.getArrivalMessage("south");
        aliveCtrl.setDefaultReturnValue("freak");
        aliveCtrl.replay();

        sourceRoom.removeItem(aliveMovingObject);
        srControl.setDefaultReturnValue(aliveMovingObject);

        foundRoom.addItem(aliveMovingObject);
        fndControl.setDefaultVoidCallable();
        srControl.replay();
        fndControl.replay();
        leaveEvent.setScope(EventScope.ROOM_SCOPE);
        leaveEvent.setSource(movingObject);
        leaveEventCtrl.setDefaultVoidCallable();
        leaveEvent.setSourceMessage("You go north.");
        leaveEventCtrl.setDefaultVoidCallable();
        leaveEvent.setScopeMessage("mover goes north.");
        leaveEventCtrl.setDefaultVoidCallable();

        arriveEvent.setScope(EventScope.ROOM_SCOPE);
        arriveEvent.setSource(movingObject);
        arriveEventCtrl.setDefaultVoidCallable();
        arriveEvent.setSourceMessage(null);
        arriveEventCtrl.setDefaultVoidCallable();
        arriveEvent.setScopeMessage("move arrives from the south.");
        arriveEventCtrl.setDefaultVoidCallable();

        leaveEventCtrl.replay();
        arriveEventCtrl.replay();

        MockControl intControl = MockClassControl.createControl(CommandInterpreter.class);

        final CommandInterpreter mockInterpreter = (CommandInterpreter) intControl.getMock();
        mockInterpreter.doCommand("look", null, aliveMovingObject);
        intControl.setDefaultReturnValue(null);
        intControl.replay();

        Exit testExit = new Exit("north", "south", "/an/other/room") {

            protected MessageEvent createMessageEvent() {
                if (call++ == 0) {
                    return leaveEvent;
                }
                return arriveEvent;
            }

            @Override
            public Observable castToObservable(Alive alive) {
                assertEquals(aliveMovingObject, alive);
                return movingObject;
            }

            protected String[] createMovingStringArray(Alive movingObject, String direction) {
                assertEquals("mover", movingObject.getName());
                if (!direction.equals("north") && !direction.equals("south")) {
                    fail(direction);
                }
                // TODO Auto-generated method stub
                return mockMovingStringArray;
            }

            @Override
            public void doHeartBeat() {
                // TODO Auto-generated method stub

            }

            @Override
            public CommandInterpreter getCommandInterpreter() {
                // TODO Auto-generated method stub
                return mockInterpreter;
            }
        };

        testExit.doMove(sourceRoom, foundRoom, aliveMovingObject);
        leaveEventCtrl.verify();
        arriveEventCtrl.verify();
        aliveCtrl.verify();
        srControl.verify();
        fndControl.verify();
        intControl.verify();
    }

}
