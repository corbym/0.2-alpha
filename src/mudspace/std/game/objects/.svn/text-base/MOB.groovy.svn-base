package std.game.objects

import org.groovymud.object.alive.AbstractMOB;
import org.groovymud.object.behaviours.LookBehaviourimport std.game.behaviours.MOBLookBehaviour

import org.groovymud.utils.Dice
import org.groovymud.object.MudObject

public class MOB extends AbstractMOB{
  
    private boolean dead

    private boolean inCombat = false
  
    def oldDescription
    def oldName
    
    public void doHeartBeat(){
    
    }
    

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }

    public boolean isInCombat() {
        return inCombat;
    }
    
    public boolean doCommand(String command, String argsAsString, MudObject performingObject) {
        boolean done = false;

        if (performingObject.equals(this)) {
            done = doCommandInContents(command, argsAsString, performingObject, getItems());
            //if (!done) {
            //    done = doCommandInContents(command, argsAsString, (Container) performingObject, getItemsFromInventoryHandler(getRaceBehaviour().getHoldingInventory()));
            //}
            //if (!done) {
            //    done = doCommandInContents(command, argsAsString, (Container) performingObject, getItemsFromInventoryHandler(getRaceBehaviour().getWearingInventory()));
            //}
        }
        if (!done) {
            done = super.doCommand(command, argsAsString, performingObject);
        }
        return done;
    }
    
    public String getMessage(String key, String[] replacements){
        return "bonkers";
    }
    
    public String getArrivalMessage(String direction){
        return "${getName()} arrives from the $direction.";
    }
    
    public String getDepartureMessage(String direction){
        return "${getName()} departs $direction.";
    }
    
	@Override
	public void addStatus(String status) {
		if(status == "net dead"){
			oldDescription = getDescription()
			setDescription ("$oldDescription ${getName()} looks all white and chalky...")
			oldName = getName()
			setName("The net dead statue of $oldName")
		}
	}

	@Override
	public void removeStatus(String status) {
		if(status == "net dead"){
			setDescription ("$oldDescription")
			setName(oldName)
		}		
	}
	
    public LookBehaviour getLookBehaviour(){        
        return new MOBLookBehaviour()        
    }	
}
