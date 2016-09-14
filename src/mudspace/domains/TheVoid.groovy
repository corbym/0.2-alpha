package domains

import org.groovymud.object.room.AbstractRoom
import org.groovymud.object.alive.Alive


class TheVoid extends AbstractRoom{
	void initialise(){
		setName("The Void");
	
		setShortNames(["void", "the void"]);
		setShortDescription("void");
	
		setDescription("You are standing in the void. This is where everything goes that doesn't have " +
						" a room to speak of." );	
	}
	
	void doHeartBeat(){
	}
}