package utils

import utils.MatchedObject

public class GenericSyntaxParser{
  	   
        // matches the object name 
    	public static final def OBJECT_NAME_EXP = /^([a-zA-Z\s])+(?=[0-9]+)|^[a-zA-Z\s]+$/
    	
    	// matches the index
    	public static final def INDEX_EXP = /[0-9]+$/

    	
    	MatchedObject subjectObject;
    	MatchedObject prepositionObject;
    	
    	/**
		 * parses some syntax sentence
		 * eg. to parse "get x from z" pass it to this fucntion with prepositionExp = "from"
		 * ... parse("get x from z", "from") puts x in subjectObject and z in prepositionObject
		 * .. also matches get x 2 from y 3 puts the second object x in subject and the 3rd object
		 * found y in the prep
		 */
		def void parse(argstr, prepositionExp){
	    	def prepositionExpSpaced = /$prepositionExp /
		    def postPrepositionExp = /$prepositionExp [a-zA-Z\s]+(\s[0-9]*)?$/ 
		    def subjectExpression = /^[a-zA-Z\s0-9]+(?= $prepositionExp)|^[a-zA-Z\s]+(\s[0-9]+)?$/
		        	
		   	subjectObject = parseSubject(argstr, subjectExpression);
		   	prepositionObject = parsePrepostionPart(argstr, prepositionExpSpaced, postPrepositionExp)		   
		}

		/**
		 * finds the first half of the sentence
		 * @return a MatchedObject containing the name and index of the parsed sentence
		 */
		protected def MatchedObject parseSubject(argstr, simpleExp){
		    def getMatcher = (argstr =~ simpleExp) 
		    if(!getMatcher.find()){
		        return null
		    }
		    MatchedObject matchedObject = new MatchedObject();
		    
			def objectGroup = getMatcher.group();

			def objMatcher = (objectGroup =~ OBJECT_NAME_EXP)
			def indexMatcher = (objectGroup =~ INDEX_EXP)
			
			// find the left hand expression object name and index
			if(objMatcher.find()){
				matchedObject.objectName = objMatcher.group().trim()
				if(indexMatcher.find()){
					matchedObject.index = Integer.parseInt(indexMatcher.group())
				}
			}
			
			return matchedObject;	
		}
		/**
		 * matches a more complex sentence with a preposition, specifically
		 * filling in a matchedobject with the preposition object and index
		 * @return MatchedObject containing the preposition object and index
		 */
		protected def MatchedObject parsePrepostionPart(argstr, preposition, complexExp){
			MatchedObject prepMatchedObject = null 
			def prepMatcher = (argstr =~ complexExp)
			boolean prepositionFound = prepMatcher.find()
			if(prepositionFound){
				prepMatchedObject = new MatchedObject();		
				// find the preposition expression, if it exists.
				def prepClause = prepMatcher.group()
				prepClause = prepClause.replaceFirst(preposition,'')
				def prepObjectMatcher = (prepClause =~ OBJECT_NAME_EXP)
				if(prepObjectMatcher.find()){
					prepMatchedObject.objectName = prepObjectMatcher.group().trim()
					def indexMatcher = (prepClause =~ INDEX_EXP)
					if(indexMatcher.find()){
					    prepMatchedObject.index = Integer.parseInt(indexMatcher.group());
					}
				}
			}
			return prepMatchedObject;
		}

}