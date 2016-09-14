package std.game.combat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.groovymud.object.AbstractMudObject;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.AbstractMOB;
import org.groovymud.object.alive.Alive;
import org.groovymud.utils.CountingMap;
import org.groovymud.utils.Dice;
import org.groovymud.utils.MessageBucket;
import org.groovymud.utils.MessengerUtils;
import org.groovymud.utils.WordUtils;

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
public class CombatBehaviour {

    private static final Logger logger = Logger.getLogger(CombatBehaviour.class);

    private final static long SECONDS_IN_ONE_ROUND = 5;

    public static final String TARGET_LOST_MESSAGE_KEY = "TARGET_LOST";
    public static final String TARGET_LOST_MESSAGE_SELF_KEY = "TARGET_LOST_SELF";
    public static final String TARGET_ATTACK_WAIT_MESSAGE_KEY = "ATTACK_WAIT_KEY";

    private static final int MAX_HUNTING_ROUNDS = 5;

    private static final int DEFAULT_HUNTING_COUNT_LIMIT = 0;

    private CountingMap hunting = null;

    private Set targets;

    private Iterator targetIterator;

    private Iterator attackIterator;

    private Alive attacker;

    private MessageBucket messageBucket;

    public CombatBehaviour(Alive attacker) {
        initialise(attacker);
    }

    protected void initialise(Alive attacker) {
        this.attacker = attacker;
    }

    /**
     * This method is called from the heartbeat of the player and happens every
     * second. if the targets are in range of the attacker, combat takes place
     * between the attacker and each of the targets.
     * 
     * targets must added before the fight is started attacker must have attacks
     * in his getAttacks set
     */
    public final void fight() {
        Alive attacker = getAttacker();
        Set targets = getTargets();
        Set attacks = attacker.getAttacks();
        checkTargetsInRange(targets, attacks);

        if (targets.size() == 0) {
            String srcMsg = getMessageFromBucket(TARGET_LOST_MESSAGE_SELF_KEY);
            String roomMsg = getMessageFromBucket(TARGET_LOST_MESSAGE_KEY);
            sendMessage(attacker, srcMsg, roomMsg);
            attacker.setInCombat(false);
            return;
        }
        // inform targets of being attacked by
        if (rollToAttack(attacker)) {
            doAttack(attacker, attacks);
        } else {
            sendMessage(attacker, getMessageFromBucket(TARGET_ATTACK_WAIT_MESSAGE_KEY), attacker.getName() + " circles " + WordUtils.resolvePossessiveGender(attacker.getGender()) + " apponent...");
        }

    }

    protected String getMessageFromBucket(String key) {
        def race = attacker.getRaceBehaviour();
        return race.getMessageBucket().getMessage(key);
    }

    protected void sendMessage(Alive attacker, String srcMessage, String roomMessage) {
        MessengerUtils.sendMessageToRoom(attacker, srcMessage, roomMessage);
    }

    protected boolean rollToAttack(Alive attacker) {
        Dice die = createDice(2, 12);
        int roll = die.roll();
        int dex = attacker.getRaceBehaviour().getStatistics().getDexterity();
        return roll < dex;
    }

    protected void checkTargetsInRange(Set targets, Set attacks) {
        CountingMap outOfRangeMap = new CountingMap();
        for (AbstractAttack attack : attacks) {
            for (MudObject target : targets) {
                if (!attack.targetInRange(target)) {
                    outOfRangeMap.increment(target);
                }
            }
        }

        Set outOfRange = outOfRangeMap.getKeysAt(attacks.size());

        CountingMap hunting = getHunting();
        for (Object object : outOfRange) {
            MudObject mObj = (MudObject) object;
            hunting.increment(object);
            MessengerUtils.sendMessageToRoom(getAttacker(), "You are hunting " + WordUtils.affixDefiniteArticle(mObj), "");
        }
        Set huntingSet = hunting.getKeysAbove(DEFAULT_HUNTING_COUNT_LIMIT);
        getTargets().removeAll(huntingSet);
        hunting.values().removeAll(huntingSet);
    }

    /**
     * should be overridden for different guild behaviour / guild command
     * behavior attacks this is a newbie basic attack this basic do attack
     * iterates over the targets for each attack per round used. more advanced
     * algorithms could perform targetted attacking, concentrated attacks on a
     * set of target classes/races or whatever you wanted!
     * 
     * @param attacker
     * @param attacks
     */
    protected void doAttack(Alive attacker, Set attacks) {

        def nextAttack = null;
        MudObject nextTarget = null;

        nextAttack =  getAttackIterator().next();
        nextTarget =  getTargetIterator().next();
        Dice dice = createDice(1, 100);
        long roll = dice.roll();
        long attackToHit = adjustToHit(attacker, nextAttack, nextTarget);

        if (roll > attackToHit) {
            long damage = nextAttack.getDamageRoll();
            damage -= nextTarget.defend(nextAttack, damage, attackToHit, roll);
            if (nextAttack.targetInRange(nextTarget)) {
                nextAttack.hit(attacker, nextTarget, damage, attackToHit, roll);
            } else {
                nextAttack.miss(attacker, nextTarget, attackToHit, roll);
            }
        } else {
            nextAttack.miss(attacker, nextTarget, attackToHit, roll);
        }

    }

    protected Dice createDice(int no, int max) {
        return new Dice(no, max);
    }

    protected long adjustToHit(Alive attacker, def nextAttack, def nextTarget) {
        def attackerGuild = attacker.getGuildBehaviour();
        long attackToHit = attackerGuild.getSkill(nextAttack.getSkillUsed()).getValue();
        attackToHit += nextAttack.getToHitModifier();
        attackToHit += attackerGuild.getSkill(nextAttack.getSkillUsed()).getValue();

        if (nextTarget instanceof AbstractMOB) {
            AbstractMOB monster = (AbstractMOB) nextTarget;
            attackToHit -= monster.getRaceBehaviour().getStatistics().getDexterity();
        }
        return attackToHit;

    }

    protected int calculateAttacksPerRound(Alive attacker) {
        long attacksperrnd = Math.round(attacker.getRaceBehaviour().getStatistics().getDexterity() / (2 * SECONDS_IN_ONE_ROUND));
        return (int) (attacksperrnd == 0 ? 1 : attacksperrnd);
    }

    /**
     * when a fight starts, the targets specified by kill should be added to the
     * targets list.
     * 
     * @return
     */
    public Set<MudObject> getTargets() {
        if (targets == null) {
            targets = Collections.synchronizedSet(new HashSet());
        }
        return targets;
    }

    public void addTarget(MudObject target) {
        getTargets().add(target);
    }

    public void removeTarget(MudObject object) {
        getTargets().remove(object);
    }

    public Iterator getTargetIterator() {
        if (targetIterator == null || !targetIterator.hasNext()) {
            setTargetIterator(getTargets().iterator());
        }
        return targetIterator;
    }

    public void setTargetIterator(Iterator targetIterator) {
        this.targetIterator = targetIterator;
    }

    public Iterator getAttackIterator() {
        if (attackIterator == null || !attackIterator.hasNext()) {
            setAttackIterator(getAttacker().getAttacks().iterator());
        }
        return attackIterator;
    }

    public void setHunting(CountingMap hunting) {
        this.hunting = hunting;
    }

    public CountingMap getHunting() {
        if (hunting == null) {
            hunting = new CountingMap();
        }
        return hunting;
    }

    public void setAttacker(Alive attacker) {
        this.attacker = attacker;
    }

    public Alive getAttacker() {
        return attacker;
    }

    private void setAttackIterator(Iterator attackIterator) {
        this.attackIterator = attackIterator;
    }

    public void setMessageBucket(MessageBucket messageBucket) {
        this.messageBucket = messageBucket;
    }

    public MessageBucket getMessageBucket() {
        if (messageBucket == null) {
            this.messageBucket = new MessageBucket();
        }
        return messageBucket;
    }

}
