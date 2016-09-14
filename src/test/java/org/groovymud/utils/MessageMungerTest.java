package org.groovymud.utils;

import junit.framework.TestCase;

import org.groovymud.object.AbstractMudObject;

public class MessageMungerTest extends TestCase {

    public void testMungeMessageStringMudObject() {
        String message = "%0 goes %1";

        String[] replacements = new String[] { "one", "two" };
        assertEquals("one goes two", MessageMunger.mungeMessage(message, replacements));

    }

    // public void testMungeMessageFor() {
    // String message = "${0} ${1} ${2} and ${3}!";
    // MudObject obj1 = createMudObject();
    // obj1.setName("one");
    // MudObject obj2 = createMudObject();
    // obj2.setName("two");
    // MudObject obj3 = createMudObject();
    // obj3.setName("three");
    //
    // assertEquals("You attack two and three",
    // MessageMunger.mungeMessage(message, obj1, Arrays.asList(new Object[] {
    // obj1, new String[] { "attack", "attacks" }, obj2, obj3 })));
    // assertEquals("one attacks two and you",
    // MessageMunger.mungeMessage(message, obj2, Arrays.asList(new Object[] {
    // obj1, new String[] { "attack", "attacks" }, obj2, obj3 })));
    // assertEquals("one attacks three and you",
    // MessageMunger.mungeMessage(message, obj3, Arrays.asList(new Object[] {
    // obj1, new String[] { "attack", "attacks" }, obj2, obj3 })));
    // }

    private AbstractMudObject createMudObject() {
        AbstractMudObject obj1 = new AbstractMudObject() {

            public void initialise() {

            }

            public void doHeartBeat() {
                // TODO Auto-generated method stub

            }

            public boolean hasCommand(String command) {
                // TODO Auto-generated method stub
                return false;
            }

            public void setWeight(long weight) {
                // TODO Auto-generated method stub

            }

        };
        return obj1;
    }
}
