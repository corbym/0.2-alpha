package minnovar
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
import org.groovymud.object.room.AbstractRoom
import org.groovymud.object.MudObject
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.object.room.Exit;

class TownSquare1 extends AbstractRoom{
	
	void initialise(){
		setName("Town Square, Minnovar");
	
		setShortNames(["Town Square", "square", "Square", "town square", "town"]);
		setShortDescription("Minnovar town square.");
		
		Exit north = load("org.groovymud.object.room.Exit", ["north", "south", "domains.minnovar.TownSquare3"]) 
		Exit south = load("org.groovymud.object.room.Exit", ["south", "north", "domains.minnovar.TownSquare2"])
		def west = load("std.game.objects.exits.Door", ["west", "east", "domains.minnovar.Shop"])
		setExits([north, south, west])
	
		setDescription("You are standing in ${getShortDescription()}. It's a nice sunny day, and everythings groovy." );
		if(getInventoryHandler().getMudObject("packet") == null){
			def packet = load("domains.minnovar.objects.CigarettePacket")
			addItem(packet)
		}
		if(getMudObjectAttendant().getObjectRegistry().getMudObject("Town Crier") == null){
			def crier = load("domains.minnovar.objects.TownCrier")
			addItem(crier)
		}
		
	}
	
	void doHeartBeat(){
	}
	
}