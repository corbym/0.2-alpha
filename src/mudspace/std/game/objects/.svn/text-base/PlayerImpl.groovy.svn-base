package std.game.objects
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
 */import org.groovymud.engine.event.action.SaveEventimport org.groovymud.engine.event.action.DestroyEventimport org.groovymud.object.alive.Playerimport javax.security.auth.Subjectimport org.groovymud.engine.event.EventScopeimport org.groovymud.shell.security.PlayerCredentials
//import std.game.guild.Newbie
//import std.game.races.Human

import org.groovymud.object.MudObject
import org.groovymud.engine.event.observer.Observable
import org.groovymud.engine.event.IScopedEvent
import org.groovymud.engine.event.EventScope;

import org.groovymud.object.registry.MudObjectAttendant;

import std.game.objects.MOB

public class PlayerImpl extends MOB implements Player{
	Subject subject;
	PlayerCredentials playerCredentials;
	
	void initialise(){
		super.initialise();
		setDescription("${getName()} looks pretty cool.")
		setCurrentContainerFileName("domains.minnovar.TownSquare1");
		setMaxCapacity(50)
		//setGuildBehaviour(new Newbie(this))
		//setRaceBehaviour(new Human(this))
	}
	
	public void dest(boolean wep) {
		if (!wep) {
			SaveEvent save = new SaveEvent();
			save.setSource(this);
			save.setScope(EventScope.GLOBAL_SCOPE);
			fireEvent(save);
		}
		super.dest(wep);
	}
	void doHeartBeat(){
	}
	
	void doUpdate(Observable o, IScopedEvent event){
		
	}
	
	@Override
	public boolean requiresArticle() {
		return false;
	}
	
	public Subject getSubject(){
		if(subject == null){
			subject = new Subject();
		}
		return subject
	}
	
	public void setSubject(Subject subject){
		this.subject = subject
	}
	public PlayerCredentials getPlayerCredentials(){
		return playerCredentials;
	}
	
	public void setPlayerCredentials(PlayerCredentials credentials){
		playerCredentials = credentials;
	}
}