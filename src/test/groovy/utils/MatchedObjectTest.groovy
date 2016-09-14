/**
 * 
 */
package utils

import junit.framework.TestCase
import org.easymock.MockControlimport org.groovymud.object.AbstractContainerimport groovy.util.ProxyGeneratorimport groovy.mock.interceptor.StubForimport static groovy.util.Proxy.proxyimport groovy.util.Proxy
import org.groovymud.object.Container


/**
 * @author corbym
 *
 */
public class MatchedObjectTest extends TestCase{
	
	/* (non-Javadoc)
	 * @see utils.MatchedObject#all()
	 */
	void testAll(){
		MatchedObject matched = new MatchedObject();
		matched.objectName = "all cookies"        
		assertTrue(matched.all())
		
		matched.objectName = "ship"          
		assertFalse(matched.all())
		
		matched.objectName = "all echoes"
		assertTrue(matched.all())
		
		matched.objectName = "echoes"
		assertFalse(matched.all())
	}
	
	/* (non-Javadoc)
	 * @see utils.MatchedObject#plural()
	 */
	void testPlural(){
		MatchedObject matched = new MatchedObject();
		matched.objectName = "cookies"        
		assertTrue(matched.plural())
		
		matched.objectName = "ship"          
		assertFalse(matched.plural())
		
		matched.objectName = "echoes"
		assertTrue(matched.plural())
	}
	
	/* (non-Javadoc)
	 * @see utils.MatchedObject#findObjectIn(org.groovymud.object.Container)
	 */
	void testFindObjectInContainer(){
	    def closureMap = [ doHeartbeat: {}, 
	                       getName : { return "monkey" }] as Map
	    def interfaces = [Container] as List
	   	def mudObj1 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	def mudObj2 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	
		def items = [mudObj1, mudObj2] as HashSet
		MockControl control = MockControl.createNiceControl(Container.class)
		Container testContainer = control.getMock()        
		testContainer.getItems("monkey")
		control.setDefaultReturnValue(items)
		control.replay()
		
		MatchedObject matched = new MatchedObject()
	    
	    matched.objectName = "monkey"
	    def returnVal = matched.findObjectInContainer(testContainer) 
	    assertTrue(returnVal == mudObj1 || returnVal == mudObj2)
	    control.verify()
	    
	}
	/* (non-Javadoc)
	 * @see utils.MatchedObject#findObjectIn(org.groovymud.object.Container)
	 */
	void testFindAllObjectInContainer(){
	    def closureMap = [ doHeartbeat: {}, 
	                       getName : { return "monkey" }] as Map
	    def interfaces = [Container] as List
	   	def mudObj1 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	def mudObj2 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	
		def items = [mudObj1, mudObj2] as HashSet
		MockControl control = MockControl.createNiceControl(Container.class)
		Container testContainer = control.getMock()        
		testContainer.getItems("monkey")
		control.setDefaultReturnValue(items)
		control.replay()
		
		MatchedObject matched = new MatchedObject()
	    
	    matched.objectName = "all monkeys"
	    def returnVal = matched.findObjectInContainer(testContainer) 
	    assertTrue(returnVal == items)
	    control.verify()
	    
	}	
	/* (non-Javadoc)
	 * @see utils.MatchedObject#findObjectIn(java.util.HashSet)
	 */
	void testFindObjectInHashSet(){
	    
	    def proxy = new MockMatchedObject()
	    proxy.objectName = "monkey"
	    def findObjs = [proxy.mudObj1, proxy.mudObj2] as HashSet
		    
		def obj = proxy.findObjectIn(findObjs)
		assertTrue(obj == proxy.mudObj1 || obj == proxy.mudObj2)
	}
	/* (non-Javadoc)
	 * @see utils.MatchedObject#findObjectIn(java.util.HashSet)
	 */
	void testFindObjectInHashSetAll(){
	    
	    def proxy = new MockMatchedObject()
	    proxy.objectName = "all monkeys"
	    def findObjs = [proxy.mudObj1, proxy.mudObj2] as HashSet
		    
		def obj = proxy.findObjectIn(findObjs)
		assertTrue(obj == findObjs)
	}
}

class MockMatchedObject extends MatchedObject{

    def closureMap = [ doHeartbeat: {}, 
                       getName : { return "monkey" }] as Map
    def interfaces = [Container] as List
   	def mudObj1 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
   	def mudObj2 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
   	
    protected Object findObjectInContainer(Container container){
		return  [mudObj1, mudObj2] as HashSet
    }
    
    
}
