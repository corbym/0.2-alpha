/**
 * 
 */
package domains.minnovar

import org.groovymud.object.room.AbstractRoom


/**
 * @author matt
 *
 */
class Shop extends AbstractRoom{
    void initialise() {
        setName("The Town Shop, Minnovar");
    	
		setShortNames(["Town Shop", "shop", "Shop", "town shop"]);
		setShortDescription("Minnovar town shop.");
		setDescription("You are in the old shoppe.")
        def east = load("std.game.objects.exits.Door", ["east", "west", "domains.minnovar.TownSquare1"])
    	setExits([east])
    }
    void doHeartBeat() {
        
    }
    
}
