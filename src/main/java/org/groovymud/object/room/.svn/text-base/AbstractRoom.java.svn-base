package org.groovymud.object.room;

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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.observer.Observer;
import org.groovymud.object.AbstractContainer;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.behaviours.LookBehaviour;
import org.groovymud.object.behaviours.RoomLookBehaviour;
import org.groovymud.object.registry.InventoryHandler;

public abstract class AbstractRoom extends AbstractContainer implements Room, Observer {

	private InventoryHandler exits;

	@Override
	public boolean checkCanAddItem(MudObject object) {
		return true;
	}

	public MudObject removeExit(Exit object) {
		return removeItemFromInventoryHandler(object, getExits());
	}

	public void addExit(Exit exit) {
		addItemToInventoryHandler(exit, getExits());
	}

	public InventoryHandler getExits() {
		if (exits == null) {
			exits = new InventoryHandler();
		}
		return exits;
	}

	public Exit getExit(String direction) {
		return (Exit) getExits().getMudObject(direction);
	}

	public double getWeight() {
		return MudObject.CANNOT_PICK_UP;
	}

	public void setWeight(long weight) {
	}

	public void setExits(List<Exit> exits) {
		Iterator exitIterator = exits.iterator();
		getExits().clear();
		while (exitIterator.hasNext()) {
			Exit exit = (Exit) exitIterator.next();
			addExit(exit);
		}
	}

	@Override
	public void doEvent(IScopedEvent event) {
		doEventInCollection(event, getExits().getMudObjects());
		super.doEvent(event);
	}

	@Override
	public boolean doCommand(String command, String argsAsString, MudObject performingObject) {
		boolean done = false;
		Set<MudObject> allContents = getItemsFromInventoryHandler(getExits());
		allContents.addAll(getItems());

		if (performingObject instanceof Container) {
			done = doCommandInContents(command, argsAsString, (Container) performingObject, allContents);
		}
		if (!done) {
			done = super.doCommand(command, argsAsString, performingObject);
		}
		return done;
	}

	public EventScope getScope() {
		return EventScope.ROOM_SCOPE;
	}

	public LookBehaviour getLookBehaviour() {
		return new RoomLookBehaviour();
	}

}
