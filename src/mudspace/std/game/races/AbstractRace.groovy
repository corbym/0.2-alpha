package std.game.races;

import java.util.HashSet
import java.util.List
import java.util.Set

import std.game.objects.bodyparts.GenericBodyPart

import org.groovymud.object.MudObject
import org.groovymud.object.alive.AbstractMOB
import std.game.combat.AbstractAttack
import std.game.combat.Defence
import org.groovymud.object.registry.InventoryHandler
import org.groovymud.utils.MessageBucket
import org.groovymud.utils.MessengerUtils
import org.groovymud.utils.WordUtils

/**
 * Copyright 2008 Matthew Corby-Eaglen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
public abstract class AbstractRace {

    public static final String ARRIVAL_MESSAGE_KEY = "ARRIVAL_MESSAGE";
    public static final String DEPARTURE_MESSAGE_KEY = "MOVING_MESSAGE";

    public static final String ARRIVAL_MESSAGE_SELF_KEY = "ARRIVAL_MESSAGE_SELF";
    public static final String DEPARTURE_MESSAGE_SELF_KEY = "MOVING_MESSAGE_SELF";

    private static final int STRENGTH_CAPACITY_MULTIPLIER = 100;

    private String race;

    private Set bodyParts;

    private Statistics baseStats;

    private MessageBucket messageBucket;

    private boolean agressive;

    /**
     * when you construct a race, you must add at LEAST the message keys above
     * to it, otherwise when the MOB moves there will be no movement message.
     * 
     * of course, not having a movement message might be part of the race
     * behaviour :D
     */
    public AbstractRace() {
        generateStats();
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Statistics getStatistics() {
        Statistics moddedStats = new Statistics(baseStats);

        for (GenericBodyPart part : getBodyParts()) {
            part.modifyStatistics(moddedStats);
        }
        this.modifyStatistics(moddedStats);
        return moddedStats;
    }

    protected abstract void modifyStatistics(Statistics moddedStats);

    public abstract void generateStats();

    /**
     * a list of commands that the race can perform
     */
    public abstract List<String> getRaceCommands();

    protected void setStatistics(Statistics stats) {
        this.baseStats = stats;
    }

    public Set getBodyParts() {
        if (bodyParts == null) {
            bodyParts = new HashSet();
        }
        return bodyParts;
    }

    public void setBodyParts(Set bodyParts) {
        this.bodyParts = bodyParts;
    }

    public void doWear(AbstractMOB wearer, def item) {
        if (item.canWear(this)) {
            Set bodyParts = getBodyPartsByName(item.getBodyPartNameFor());
            for (GenericBodyPart part : bodyParts) {
                if (addItemToBodyPart(item, part)) {
                    sendMessage(wearer, "wear", item);
                    return;
                }
            }
            sendMessageToPlayer(wearer, "You have nowhere to wear that.");
        } else {
            sendMessageToPlayer(wearer, "You cannot wear that.");
        }
    }

    protected void sendMessageToPlayer(AbstractMOB wearer, String string) {
        MessengerUtils.sendMessageToPlayer(wearer, string);
    }

    protected void sendMessage(AbstractMOB subject, String action, MudObject mudObject) {
        String name = (mudObject.requiresArticle() ? WordUtils.affixIndefiniteArticle(mudObject.getName()) : mudObject.getName());
        String roomMessage = WordUtils.affixDefiniteArticle(subject) + " " + WordUtils.pluralize(action) + " " + name;
        String sourceMessage = "You " + action + " " + name;
        MessengerUtils.sendMessageToRoom(subject, sourceMessage, roomMessage);
    }

    protected boolean addItemToBodyPart(def item, GenericBodyPart part) {
        if (part.checkCanAddItem(item)) {
            part.addItem(item);
            return true;
        }
        return false;
    }

    public void doRemove(AbstractMOB wearer, def item) {
        // get from any inventory collection
        Set bodyParts = getBodyParts();
        for (GenericBodyPart part : bodyParts) {
            // find first available slot and remove it
            MudObject remove = part.removeItem(item);
            if (remove != null) {
                wearer.getInventoryHandler().addMudObject((MudObject) remove);
                sendMessage(wearer, "remove", item);
                return;
            }
        }
        sendMessageToPlayer(wearer, "Nothing to remove.");
    }

    public void doHold(AbstractMOB holdee, def item) {
        if (item.canHold(this)) {
            Set bodyParts = getBodyParts();
            for (GenericBodyPart part : bodyParts) {
                // find first available slot and hold it there
                if (part.holdItem(item)) {
                    sendMessage(holdee, "hold", item);
                    return;
                }
            }
            sendMessageToPlayer(holdee, "You cannot hold that because you are holding something else. Try lowering it?");
        } else {
            sendMessageToPlayer(holdee, "You cannot hold that.");
        }
    }

    public void doLower(AbstractMOB holder, def item) {
        Set bodyParts = getBodyParts();
        for (GenericBodyPart part : bodyParts) {
            if (part.getHeldItem().equals(item)) {
                holder.getInventoryHandler().addMudObject(part.lowerItem(item));
                sendMessage(holder, "lower", item);
                return;
            }
        }
        sendMessageToPlayer(holder, "You cannot lower that.");
    }

    protected Set getBodyPartsByName(String bodyPartFor) {
        HashSet parts = new HashSet<GenericBodyPart>();
        for (GenericBodyPart part : getBodyParts()) {
            if (part.getName().equals(bodyPartFor)) {
                parts.add(part);
            }
        }
        return parts;
    }

    public Set getAttacks() {
        Set attacks = new HashSet();
        for (GenericBodyPart part : getBodyParts()) {
            attacks.addAll(part.getAttacks());
        }
        return attacks;
    }

    /**
     * returns all worn items. not backed by the inventory
     * 
     * @return
     */
    public InventoryHandler getWearingInventory() {
        InventoryHandler allWorn = new InventoryHandler();
        for (GenericBodyPart part : getBodyParts()) {
            allWorn.addAll(part.getInventoryHandler());
        }
        return allWorn;
    }

    /**
     * returns all held items. not backed by the inventory
     * 
     * @return
     */
    public InventoryHandler getHoldingInventory() {
        InventoryHandler allHeld = new InventoryHandler();
        for (GenericBodyPart part : getBodyParts()) {
            allHeld.addAll(part.getHeldInventory());
        }
        return allHeld;
    }

    public Set getDefences() {
        Set defences = new HashSet();
        for (GenericBodyPart parts : getBodyParts()) {
            defences.addAll(parts.getDefences());
        }
        return defences;
    }

    public abstract GenericBodyPart getHitBodyPart(int roll);

    public Statistics getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(Statistics baseStats) {
        this.baseStats = baseStats;
    }

    public boolean isAgressive() {
        return agressive;
    }

    public void setAgressive(boolean agressive) {
        this.agressive = agressive;
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
