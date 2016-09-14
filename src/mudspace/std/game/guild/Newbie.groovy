package	std.game.guild

import std.game.guild.AbstractGuildBehaviour
import std.game.guild.skill.SkillTreeDecorator
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.object.alive.Alive

class Newbie extends AbstractGuildBehaviour{
	public Newbie(Alive mob){
		super(mob);
		setGuildName("Newbie");
	}
	
	void progressSkillTree(SkillTreeDecorator skilltree, String skillLongName, long valueDelta){
		// code so that they can advance as a newbie
	}
}