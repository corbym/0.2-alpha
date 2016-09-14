package org.groovymud.object.registry;

import org.groovymud.object.AbstractMudObject;
import org.groovymud.object.MudObject;

import junit.framework.TestCase;


public class InventoryHandlerTest extends TestCase {

    public void testAddMudObjectStringMudObject() {
        InventoryHandler hand = new InventoryHandler();
        
        MudObject obj = createMudObject();
        hand.addMudObject(obj);
        assertTrue(hand.size() == 1);
        hand.addMudObject(obj);
        assertTrue(hand.size() == 1);
        MudObject obj2 = createMudObject();
        hand.addMudObject(obj2);
        assertTrue(hand.size() == 2);
    }

    private AbstractMudObject createMudObject() {
        return new AbstractMudObject(){
            @Override
            public String getName() {
                // TODO Auto-generated method stub
                return "obj";
            }

            @Override
            public void doHeartBeat() {
                // TODO Auto-generated method stub
                
            }
        };
    }

    public void testRemoveMudObjectMudObject() {
        InventoryHandler hand = new InventoryHandler();
        MudObject obj = createMudObject();
        hand.addMudObject(obj);
        assertTrue(hand.size() == 1);
        hand.removeMudObject(obj);
        assertTrue(hand.size() == 0);
        assertTrue(hand.getMudObject("obj") == null);
        hand.addMudObject(obj);
        assertTrue(hand.size() == 1);
    }

    public void testRemoveMudObjectStringInt() {
        InventoryHandler hand = new InventoryHandler();
        MudObject obj = createMudObject();
        hand.addMudObject(obj);
        MudObject obj2 = createMudObject();
        hand.addMudObject(obj2);
        assertTrue(hand.size() == 2);
        hand.removeMudObject("obj", 1);
        assertTrue(hand.size() == 1);
    }

}
