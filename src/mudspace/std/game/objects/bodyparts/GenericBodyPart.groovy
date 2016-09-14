package std.game.objects.bodyparts;

import std.game.objects.trappings.AbstractWeapon;
import std.game.objects.trappings.Holdable;
import std.game.objects.trappings.Wearable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.groovymud.object.AbstractContainer;
import org.groovymud.object.MudObject;
import std.game.combat.AbstractAttack
import std.game.combat.Defence
import std.game.races.Statistics

import org.groovymud.object.registry.InventoryHandler;

public class GenericBodyPart extends AbstractContainer {

    private boolean canHold = false;
    private boolean holding = false;

    private Set defences; // defences are cumulative
    private Set attacks; // attacks that you are not using but
    // still exist if unwielding items

    protected InventoryHandler linkedParts; // for a pair of arms, legs, etc -
    // could be more than one linked!!
    protected InventoryHandler heldItem;

   
    public GenericBodyPart(String shortName, Boolean canHold){
		this.partShortName = shortName
	    heldItem = new InventoryHandler();	    
		setCanHold(canHold.booleanValue())
	}
    
    public void initialise(){
		setName("${partShortName}")
		setDescription("An ordinary looking ${partShortName}.")
		setShortNames(["${partShortName}"])	
	}
	
    @Override
    public boolean checkCanAddItem(MudObject object) {

        if (object instanceof Wearable) {
            Wearable wearable = (Wearable) object;
            if (wearable.getNumberOfBodyPartsRequired() > 1 && linkedParts.size() < wearable.getNumberOfBodyPartsRequired() - 1) {
                return false;
            }
            // if the body part's names matched the items bodypartfor class
            // object then say yes
            if (wearable.getBodyPartNameFor().equals(getName())) {
                int containsAmount = getInventoryHandler().getMudObject(wearable.getBodyPartNameFor()).size();
                if (containsAmount < wearable.getMaxWearable()) {
                    return superCheckCanAddItem(object);
                }
            }
        }
        return false;
    }

    /**
     * REMEMBER addItem does not check if it can be added. use
     * {@<code>checkCanAddItem()</code>}
     */
    @Override
    public void addItem(MudObject object) {
        Wearable item = (Wearable) object;
        if (item.getNumberOfBodyPartsRequired() > 1) {
            HashSet<MudObject> linkedParts = getLinkedParts().getMudObjects();
            int amount = item.getNumberOfBodyPartsRequired();
            Iterator i = linkedParts.iterator();
            while (i.hasNext() && amount > 0) {
                GenericBodyPart obj = (GenericBodyPart) i.next();
                obj.addItem(object);
                amount--;
            }
        }
        super.addItem(object);
    }

    @Override
    public MudObject removeItem(MudObject object) {
        HashSet<MudObject> linkedParts = getLinkedParts().getMudObjects();
        Iterator i = linkedParts.iterator();
        while (i.hasNext()) {
            GenericBodyPart obj = (GenericBodyPart) i.next();
            obj.removeItem(object);
        }
        return super.removeItem(object);
    }

    public boolean holdItem(Holdable item) {
        if (holding || !canHold) {
            return false;
        }

        heldItem.addMudObject((MudObject)item);
        holding = true;
        return holding; // make sure we return the old item, can only hold one
        // thing at a time in this part
    }

    public MudObject lowerItem(Holdable item) {
        item = (Holdable) heldItem.removeMudObject(item);
        holding = false;
        return item;
    }

    protected boolean superCheckCanAddItem(MudObject object) {
        return super.checkCanAddItem(object);
    }

    public boolean canHold() {
        return canHold;
    }

    public void setCanHold(boolean holdable) {
        this.canHold = holdable;
    }

    public Set getDefences() {
        Set defences = this.defences;
        if (holding) {
            Holdable item = getHeldItem();
            defences.addAll(item.getDefences());
        }
        Set wearing = getInventoryHandler().getItemsOfClass(Wearable.class);
        for (MudObject obj : wearing) {
            Wearable wearable = (Wearable) obj;
            defences.addAll(wearable.getDefences());
        }
        return defences;
    }

    public void setDefences(Set<Defence> defences) {
        this.defences = defences;
    }

    public Set getAttacks() {
        if (holding) {
            Holdable item = getHeldItem();
            if (item instanceof AbstractWeapon) {
                AbstractWeapon weapon = (AbstractWeapon) item;
                return weapon.getAttacks();
            }
            // holding none weapons renders the limb useless
            return new HashSet();
        }
        return attacks;
    }

    public Holdable getHeldItem() {
        return ((Holdable) heldItem.getMudObjects().toArray()[0]);
    }

    public InventoryHandler getHeldInventory() {
        return heldItem;
    }

    public void setAttacks(Set<AbstractAttack> attacks) {
        this.attacks = attacks;
    }

    /**
     * defers modding of character stats to worn/held items
     * 
     * @param moddedStats
     */
    public void modifyStatistics(Statistics moddedStats) {
        if (holding) {
            Holdable item = getHeldItem();
            item.modifyStatistics(moddedStats);
        }
        for (MudObject obj : getInventoryHandler().getMudObjects()) {
            obj.modifyStatistics(moddedStats);
        }
    }

    public InventoryHandler getLinkedParts() {
        if (linkedParts == null) {
            linkedParts = new InventoryHandler();
        }
        return linkedParts;
    }

    public void setLinkedParts(Set<GenericBodyPart> linkedParts) {
        for (GenericBodyPart part : linkedParts) {
            getLinkedParts().addMudObject(part);
        }
    }
    
    
	
	
	public void doHeartBeat(){
		// freaky, i never thought of an arm as having an heartbeat...
		// this could lead to interesting things..
	}

}
