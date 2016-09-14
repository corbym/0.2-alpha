package domains.minnovar.objects

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


import org.groovymud.object.AbstractContainer
import org.groovymud.object.registry.MudObjectAttendant

public class CigarettePacket extends AbstractContainer{
	
	static final int MAX_CIGS_IN_PACKET= 20

	void initialise(){
		setName("packet of cigarettes")
		addShortName("cigs")
		addShortName("packet")
		addShortName("cigarettes")
		setWeight(0.1)
		setMaxCapacity(2);
		def i = 0
		while(i < MAX_CIGS_IN_PACKET){
			def cig = load("domains.minnovar.objects.Cigarette")
			this.addItem(cig)
			i++
		}
		setDescription("A packet of normal cigarettes. It looks like it has ${getItems().size()} cigarettes left in it.")
		
	}
	
	void doHeartBeat(){
		
	}
	
}