package std.game.guild;

import org.groovymud.object.alive.Alive;

import std.game.guild.skill.Skill
import std.game.guild.skill.SkillTree
import std.game.guild.skill.SkillTreeDecorator
import std.game.combat.CombatBehaviour;

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
/**
 * this represents a players current guild behaviour
 * 
 * So a level 10 fighter's guild behaviour might include a level 10 combat
 * behaviour object defined in the MUD with bash, charge and dodge commands
 * allowed
 * 
 */
public abstract class AbstractGuildBehaviour {

    private String guildName;

    private String guildTitle;

    private SkillTreeDecorator skillTree;

    private transient CombatBehaviour combatBehaviour;

    private Alive mobFor;

    public AbstractGuildBehaviour(Alive mobFor) {
        this.mobFor = mobFor;
    }

    public CombatBehaviour getCombatBehaviour() {
        if (combatBehaviour == null) {
            combatBehaviour = createCombatBehaviour();
        }
        return combatBehaviour;
    }

    protected CombatBehaviour createCombatBehaviour() {
        return new CombatBehaviour(getMobFor());
    }

    public void setCombatBehaviour(CombatBehaviour combatBehaviour) {
        this.combatBehaviour = combatBehaviour;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public SkillTreeDecorator getSkillTree() {
        return skillTree;
    }

    public void setSkillTree(SkillTreeDecorator skillTree) {
        this.skillTree = skillTree;
    }

    public Skill getSkill(String skillLongName) {
        return getSkillTree().getSkillByName(skillLongName);
    }

    public boolean addSkill(String skillLongName, long value) {
        return getSkillTree().addSkill(skillLongName, value);
    }

    /**
     * override for other guild behaviours. should allow progression for the
     * various skill trees for this guild and limit levels or add tree branches
     * as required
     */
    public abstract void progressSkillTree(SkillTreeDecorator skilltree, String skillLongName, long valueDelta);

    public String getGuildTitle() {
        return guildTitle;
    }

    public void setGuildTitle(String guildTitle) {
        this.guildTitle = guildTitle;
    }

    public void setMobFor(Alive mobFor) {
        this.mobFor = mobFor;
    }

    public Alive getMobFor() {
        return mobFor;
    }

}
