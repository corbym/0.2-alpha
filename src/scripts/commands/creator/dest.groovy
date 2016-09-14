package commands.creator

import org.groovymud.object.room.Room

def stream = source.getTerminalOutput()

if(args == null){
	stream.writeln "Usage: dest objectname [index]"
}

def item =  source.getCurrentContainer().getItems(argstr)

if(item == null){
    item = source.getItems(argstr);
}

if(item == null){
	item = globalRegistry.getMudObject(argstr)
}
if(item == null){
	if(args[0] == "here"){
		item = source.getCurrentContainer()
	}
}
if(item != null){
	def index = 0;
	def items = []
	if(item instanceof Set){
	    items.addAll(item)
	}else{
	    items = [item]
	}
	try{
    	if(args[2] != null){
    		index = Integer.parseInt(args[2]).intValue();
    		index -= 1;    		
    	}
    	item = items[index]
    	item.dest(true)
    	
    	if(source.getCurrentContainer().equals(item)){
    		source.getMudObjectAttendant().movePlayerToVoid(source)
    	}
	}catch(java.lang.NumberFormatException e){
		stream.writeln "Usage: dest objectname [index]";
	}
	
}else{
	stream.writeln "Cannot find a ${args[0]} to dest"
}