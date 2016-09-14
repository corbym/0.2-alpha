package utilsimport org.groovymud.utils.WordUtils
import org.groovymud.object.MudObject
import org.groovymud.object.Container

import static org.groovymud.utils.WordUtils.*


/**
 * helps find the objects based on regexp matches. 
 * set the objectName and index and it will look for the object in a hashset or a container
 * @author matt
 *
 */
 class MatchedObject{
  
    	String foundNames = "" // used for storing a list of found object names		
    	String objectName = "" // the parsed object name
		int index = 0
	
		static final def allMatcher = /^(all$|all .*$)/
		boolean all(){
			def val = (this.objectName ==~ allMatcher)
			return val
		}
		static final def pluralMatcher = /^.+s$/
		boolean plural(){
			return (this.objectName ==~ pluralMatcher)
		}
		
		// finds the object in a container, returns a Map if all is set or was plural
		// otherwise returns the object
		protected def findObjectInContainer(Container container){
			def wasAll = false
			if(all()){
				objectName = objectName.replaceFirst("all", "")
				wasAll = true
			}
			if(plural()){
			    objectName = depluralize(objectName)
			    wasAll = true
			}
			def objs
			if(objectName == ""){
			    objs = container.getItems();			    
			}else{
			    objs = container.getItems(objectName)
			}
			
			if(wasAll || objs == null){
				return objs
			}
			return objs.asList().toArray()[index - 1]	
		}
		// finds the object in a given hashset, returns a HashSet if all is set
		// collates a list of object names
		// otherwise returns the object
		public def findObjectIn(HashSet set){
		    def ret = [] as HashSet
			set.each{
			    prospectiveContainer -> 
				if(prospectiveContainer instanceof Container){
					def found = findObjectInContainer(prospectiveContainer);
					if(found instanceof HashSet){
					    found.eachWithIndex{
							obj, idx -> 
								foundNames += 
								    affixIndefiniteArticle(obj.getName()) + 
									(idx < found.size() - 1 ? ", " : "") +
									(idx == found.size() - 1 ? " and " : "")
					    }
						ret.addAll(found)
					}else{
					    foundNames = affixIndefinateArticle(found.getName())
						ret.add(found)
					}
				}
			}
			if(all()){
				return ret
			}
			return ret.asList().toArray()[index]
		}
	}
