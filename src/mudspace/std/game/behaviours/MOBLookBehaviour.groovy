/**
 * 
 */
package std.game.behaviours

import org.groovymud.object.behaviours.ContentsHelper
import org.groovymud.shell.telnetd.ExtendedTerminalIO
import org.groovymud.object.alive.Alive
import org.groovymud.object.MudObject

import org.groovymud.object.behaviours.ContainerLookBehaviour
import org.groovymud.object.registry.InventoryHandler
/**
 * @author matt
 *
 */
public class MOBLookBehaviour extends ContainerLookBehaviour{
	private static final String CARRYING_DESC = "carrying";
	private static final String HOLDING_DESC = "holding";
	private static final String WEARING_DESC = "wearing";
	
	void writeLookHeader(ExtendedTerminalIO stream, Alive looker, MudObject object) throws IOException {
		if (object.equals(looker)) {
			stream.writeln("Looking at yourself?? Vanity it's self!");
			stream.writeln("You look like:");
		} else {
			stream.writeln("You see ${object.getName()}") //+ " the " + object.getGuildBehaviour().getGuildTitle());
		}
		stream.writeln(object.getDescription());
	}
	
	void writeLookInventory(ExtendedTerminalIO stream, Alive looker){
		writeLookInventory(stream, CARRYING_DESC, looker.getInventoryHandler(), looker, looker);
	}
	
	void writeLookInventory(ExtendedTerminalIO stream, String invTypeAction, InventoryHandler handler, MudObject obj, Alive looker) throws IOException {
		if (looker.equals(obj)) {
			stream.writeln("You are $invTypeAction: ");
		} else {
			stream.writeln("${obj.getName()} is  $invTypeAction: ");
		}
		ContentsHelper helper = new ContentsHelper();
		String wearing = helper.getContentsDescription(handler.getMudObjectsMap(), obj, false);
		stream.writeln(wearing.equals("") ? "Nothing." : wearing);
	}
	
	void writeLookFooter(ExtendedTerminalIO stream) throws IOException {
	}
	
	void writeLookBody(ExtendedTerminalIO stream, Alive looker, MudObject object) throws IOException {
		//writeLookInventory(stream, WEARING_DESC, obj.getRaceBehaviour().getWearingInventory(),  obj, looker);
		//writeLookInventory(stream, HOLDING_DESC, obj.getRaceBehaviour().getHoldingInventory(), obj, looker);
		writeLookInventory(stream, CARRYING_DESC, object.getInventoryHandler(), object, looker);
	}
	
}
