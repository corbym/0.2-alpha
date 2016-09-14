package std.game.objects.trappings;

import org.groovymud.object.MudObject;

public interface Wearable extends MudObject {

    public int getMaxWearable();

    public String getBodyPartNameFor();

    public long getWornWeight();

    public int getNumberOfBodyPartsRequired();

    public boolean canWear(def abstractRace);
}
