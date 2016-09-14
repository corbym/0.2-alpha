package domains.minnovar
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

class TownSquare3 extends AbstractRoom{
	void initialise(){
		setName("Town Square, Minnovar");
	
		setShortNames(["Town Square", "square", "Square", "town square", "town"]);
		setShortDescription("Minnovar town square.");
		def south = load("org.groovymud.object.room.Exit", ["south", "north", "domains.minnovar.TownSquare1"])
		setExits([south])
	
		setDescription("You are standing in the town square. It's a nice sunny day, and everythings" +
						" groovy." );
	}
	
	void doHeartBeat(){
	}
	
	boolean doCommand(String command, List args, MudObject source){
		if(command == "look"){
		    if(args[0]!=null && args[0] == "square"){
		        source.getTerminalOutput().writeln("A lovely looking square. Very pleasant!")
		    }
		}
	}
}