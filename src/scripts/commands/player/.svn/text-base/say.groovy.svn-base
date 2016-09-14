package commands.player

import org.groovymud.engine.event.messages.MessageEvent
import org.groovymud.engine.event.EventScope
import static org.groovymud.utils.MessengerUtils.sendMessageToRoom

stream = source.getTerminalOutput()
handler = source.getCurrentContainer().getInventoryHandler()
contents = handler.getMudObjects(true)

srcSays = "You say: $argstr"
scpSays = "${source.getName()} says: $argstr"

sendMessageToRoom(source, srcSays, scpSays)
