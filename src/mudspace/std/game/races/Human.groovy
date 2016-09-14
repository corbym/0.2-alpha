package std.game.races

import std.game.races.AbstractRace;
import java.util.ArrayList
import java.util.List
import std.game.races.Statistics;
import java.lang.Math;import org.groovymud.utils.Dice
import std.game.objects.bodyparts.GenericBodyPart;
/**
 * AbstractRace is NOT a mud object, so you need to use the MOB to load the parts
 * 
 */
class Human extends AbstractRace{
    def mob
    def toHitChanceMap
    
    public Human(def mob){
        setRace("Human");
		
        def head = mob.load("std.game.objects.bodyparts.GenericBodyPart", ["head", false]);
			
        def leg1 = mob.load("std.game.objects.bodyparts.SidedBodyPart", ["leg", "left", false]);
        def leg2 = mob.load("std.game.objects.bodyparts.SidedBodyPart", ["leg", "right", false]);
		
		
		leg1.setLinkedParts([leg2] as Set);
		
		def arm1 = mob.load("std.game.objects.bodyparts.SidedBodyPart",  ["arm", "left",  false] );
		def arm2 = mob.load("std.game.objects.bodyparts.SidedBodyPart", ["arm", "right",  false] );
			
		arm1.setLinkedParts([arm2] as Set);
		
		def hand1 = mob.load("std.game.objects.bodyparts.SidedBodyPart",  ["hand", "left", true] );
		def hand2 = mob.load("std.game.objects.bodyparts.SidedBodyPart", ["hand", "right", true]  );
		
		hand1.setLinkedParts([hand2] as Set);
		
		def foot1 = mob.load("std.game.objects.bodyparts.SidedBodyPart", ["foot", "left", false] );
		def foot2 = mob.load("std.game.objects.bodyparts.SidedBodyPart", ["foot", "right", false] );
		
		foot1.setLinkedParts([foot2] as Set);
		
		def finger1 = mob.load("std.game.objects.bodyparts.SidedBodyPart", ["finger", "left", false] );
		def finger2 = mob.load("std.game.objects.bodyparts.SidedBodyPart", ["finger", "right", false] );
		
		def torso = mob.load("std.game.objects.bodyparts.GenericBodyPart", ["torso", false] );
		
		setBodyParts([head, leg1, leg2, arm1, arm2, foot1, foot2, finger1,finger2, torso] as Set)
		
		toHitChanceMap = [(0..5) : head, (6..15) : leg1, (16..25): leg2, (26..35): arm1, (36..45): arm2, (46..47): foot1, (48..49) : foot2, (50..51): finger1, (52..53) : finger2, (54..100): torso]
		
		def bucket = getMessageBucket();
		bucket.putMessage(ARRIVAL_MESSAGE_KEY, "${mob.getName()} arrives from the %1")
		bucket.putMessage(DEPARTURE_MESSAGE_KEY, "${mob.getName()} leaves %1")
		bucket.putMessage(ARRIVAL_MESSAGE_SELF_KEY, "${mob.getName()} arrives from the %1")
		bucket.putMessage(DEPARTURE_MESSAGE_SELF_KEY, "${mob.getName()} leaves %1")
		
    }
    
    public GenericBodyPart getHitBodyPart(int roll){
        def part = null
        toHitChanceMap.each{key, value -> 
                                if(key.contains(roll)){
                                    part = value 
                                    return
                                }
        }
        return part
    }
    
 	public void generateStats(){
 	    Dice dice = new Dice(2, 20)
 		Statistics stats = new Statistics();
 		stats.setIntelligence(dice.roll())
 		stats.setStrength(dice.roll())
 		stats.setWisdom(dice.roll())
 		stats.setDexterity(dice.roll())
 		stats.setConstitution(dice.roll());
 		setStatistics(stats)
 	}

    public List getRaceCommands(){
    	return new ArrayList();
    }
    
    protected void modifyStatistics(Statistics stats){
        
    }
    
}