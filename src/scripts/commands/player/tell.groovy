package commands.player

import org.groovymud.engine.event.messages.MessageEvent
import org.groovymud.engine.event.EventScope

import static org.groovymud.utils.MessengerUtils.sendMessageToMud

stream = source.getTerminalOutput()


def target = globalRegistry.getMudObject(args[0])
def tell = args.subList(1, args.size())
def tellstr = tell.join(' ')

if(target != null){
	
	def srcSays = "You tell ${target.getName()}: $tellstr"
	def scpSays = "${source.getName()} tells you: $tellstr"
	
	sendMessageToMud(source, srcSays, scpSays)
}else{
	stream.writeln("Cannot find ${args[0]} to tell anything to.")
}