/**
 * 
 */
package domains.minnovar.objects.weapons

import org.groovymud.object.alive.Alive
import std.game.objects.trappings.AbstractWeapon

import org.groovymud.utils.Dice

import std.game.combat.attacks.SharpAttack
/**
 * @author corbym
 *
 */
public class Knife extends AbstractWeapon{
	
	
	void initialise(){
		setName("knife")
		addShortName("knife")
		Dice attackDice = new Dice(1, 6)
		addAttack(new SharpAttack(attackDice));
	}
	public void doWield(Alive alive){
		
	}
	
	public void doLower(Alive alive){
		
	}
	public String getBodyPartFor(){
		return "hand"
	}
	public int getNumberOfBodyPartsRequired(){
		return 1
	}
	public void doHeartBeat(){
		
	}
	public boolean canHold(def race){
	    return true
	}
}
