package commands.player

import org.groovymud.object.alive.Player

def who = globalRegistry.getItems()
source.getTerminalOutput().writeln("Who's online:")

who.each
{
	player -> 	
		 source.getTerminalOutput().write(player instanceof Player ? player.getName() + " is online \r\n" : "")
}