/** Copyright 2008 Matthew Corby-Eaglen
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
package std.game.combat;

import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.utils.Dice;

/**
 * @author corbym
 * 
 */
public abstract class AbstractAttack {

    private String skillUsed;
    private String attackType;
    private int toHitModifier;

    private int additionalDamage; // + 2 etc

    private Dice attackDice;

    public AbstractAttack(Dice attackDice) {
        this.attackDice = attackDice;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.groovymud.object.combat.Attack#getSkillUsed()
     */
    public String getSkillUsed() {
        return skillUsed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.groovymud.object.combat.Attack#getAttackType()
     */
    public String getAttackType() {
        return attackType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.groovymud.object.combat.Attack#getToHitModifier()
     */
    public long getToHitModifier() {
        return toHitModifier;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.groovymud.object.combat.Attack#hit(org.groovymud.object.alive.Alive,
     * org.groovymud.object.MudObject, long, long, long)
     */
    public abstract void hit(Alive attacker, MudObject target, long damage, long attackToHit, long roll);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.groovymud.object.combat.Attack#miss(org.groovymud.object.alive.Alive,
     * org.groovymud.object.MudObject, long, long)
     */
    public abstract void miss(Alive attacker, MudObject nextTarget, long attackToHit, long roll);

    /*
     * (non-Javadoc)
     * 
     * @see org.groovymud.object.combat.Attack#getDamageRoll()
     */
    public long getDamageRoll() {
        return attackDice.roll() + additionalDamage;
    }

    public int getAdditionalDamage() {
        return additionalDamage;
    }

    public void setAdditionalDamage(int additionalDamage) {
        this.additionalDamage = additionalDamage;
    }

    public Dice getAttackDice() {
        return attackDice;
    }

    public void setAttackDice(Dice attackDice) {
        this.attackDice = attackDice;
    }

    public void setSkillUsed(String skillUsed) {
        this.skillUsed = skillUsed;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public void setToHitModifier(int toHitModifier) {
        this.toHitModifier = toHitModifier;
    }

    public abstract boolean targetInRange(MudObject nextTarget);
}
