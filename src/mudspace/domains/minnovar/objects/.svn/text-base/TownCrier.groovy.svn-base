package domains.minnovar.objects

import static org.groovymud.utils.WordUtils.*

import static org.groovymud.utils.MessengerUtils.sendMessageToRoom

import std.game.objects.MOB

import org.groovymud.utils.Dice

class TownCrier extends MOB{
	void initialise() {
		setName("Town Crier")
		addShortName("crier") 
		Dice dice = new Dice(1, 100);
		def rnd = dice.roll()
		if(rnd > 50){
		    setGender("male")
		}else{
		    setGender("female")
		}
		setDescription("The ${getName()} looks like ${resolveThirdPersonSingular(getGender())}'s about to shout, /very/ loudly...")

		setRequiresIndefinateArticle(true)
	}
	/**
	 * The mob heartbeat is called every mud tick.
	 * 
	 */
	void doHeartBeat() {
		Dice dice = new Dice(1, 100)
		def rnd = dice.roll()
		if((0..5).contains(rnd)){
			sendMessageToRoom this, "", "The ${getName()} shuffles ${resolvePossessiveGender(getGender())} feet."            
		}
		if((6..10).contains(rnd)){
			sendMessageToRoom this, "", "The ${getName()} shouts: O Yay, O Yay!."
		}
	}

}