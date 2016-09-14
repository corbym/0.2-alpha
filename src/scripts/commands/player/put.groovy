package commands.player
import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
import utils.MatchedObject
import utils.GenericSyntaxParser
import org.groovymud.object.Container
import static org.groovymud.utils.WordUtils.*import org.groovymud.object.MudObject/**
 *  puts one object into another
 *  put [all] objectName[s] [index] (in|into) objectName[s] [index]
 */
stream = source.getTerminalOutput()

GenericSyntaxParser parser = new GenericSyntaxParser()
parser.parse(argstr, "(in|into)")

if(parser.subjectObject != null){
    // can only put what you are holding into other stuff
    if(parser.prepositionObject != null){
		def obj = parser.prepositionObject.findObjectInContainer(source)
		def subjObj = parser.subjectObject.findObjectInContainer(source)
		if(obj instanceof Container){
		    if(obj instanceof Set){
		        def map = source.getMudObjectsMap().clone()
			    def remove = [] as Set			    
			    actualObjs.each{
				    it ->
				       if(!checkAndAdd(subjObj, it)){
				    	   source.removeMudObject(it, map);
				       }				       				    
				}			 
			    contentsDescription = source.getContentsDescription(map, source, false)
		    }else{
		        
		        contentsDescription = affixIndefiniteArticle(subjObj);
			    if(!checkAndAdd(obj, subjObj)){
			    	return
			    }
			}
			if(contentsDescription.trim() != ""){
    		    def scopeMsg = "${affixDefiniteArticle(source)} puts ${contentsDescription} into ${affixIndefiniteArticle(obj)}."
    		    def sourceMsg = "You put $contentsDescription into ${affixDefiniteArticle(obj)}."
    		    sendMessageToRoom(source, sourceMsg, scopeMsg)
			}
		}
	}
}
def checkAndAdd(def container, def item){
    if(container.checkCanAddItem(item)){
    	container.addItem(item)
    	return true
    }else{
		stream.writeln("You cannot put ${affixIndefiniteArticle(item)} into ${affixDefiniteArticle(container)}.")							
		return false
    }
}