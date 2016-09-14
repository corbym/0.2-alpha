/**
 * 
 */
package commands.player
import net.wimpi.telnetd.io.terminal.TerminalManager;

def manager = TerminalManager.getReference()
def terms = manager.availableTerminals as List
def io = source.getTerminalOutput()
def termType = args[0].trim()
if(terms.contains(termType)){
	io.setTerminal(termType)
	io.writeln("Term set to " + termType)
}else if(termType == "types"){
	io.writeln("Valid term types:")
	terms.each{it -> io.writeln(it)}
}else{
	io.writeln("Invalid term type: " + termType)
}

