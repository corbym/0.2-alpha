/**
 * 
 */
package std.game.objects.exits

import org.groovymud.engine.event.EventScope
import org.groovymud.engine.event.IScopedEvent


/**
 * @author matt
 *
 */
public class DoorEvent implements IScopedEvent{

    private EventScope scope = EventScope.CONTAINER
    private def source
    private String targetRoomScriptName
    private String targetDirectionName
    private boolean opened;
    
    public String getTargetRoomScriptName(){
        return targetRoomScriptName
    }
    
    public void setTargetRoomScriptName(String scriptName){
        targetRoomScriptName = scriptName
    }
    
    public String getTargetDirection(){
        return targetDirection
    }
    
    public void setTargetDirection(String targetDirectionName){
        this.targetDirectionName = targetDirectionName
    }
    /* (non-Javadoc)
     * @see org.groovymud.engine.event.IScopedEvent#getScope()
     */
    public EventScope getScope(){
        // TODO Auto-generated method stub
        return scope
    }
    
    /* (non-Javadoc)
     * @see org.groovymud.engine.event.IScopedEvent#setScope(org.groovymud.engine.event.EventScope)
     */
    public void setScope(EventScope scope){
        // TODO Auto-generated method stub
        this.scope = scope
    }
    
    /* (non-Javadoc)
     * @see org.groovymud.engine.event.IScopedEvent#setSource(java.lang.Object)
     */
    public void setSource(Object object){
        // TODO Auto-generated method stub
        source = object
    }
    
    /* (non-Javadoc)
     * @see org.groovymud.engine.event.IScopedEvent#getSource()
     */
    public Object getSource(){
        // TODO Auto-generated method stub
        return source
    }
    public boolean wasOpened(){
        return opened
    }
    public void setOpened(boolean open){
        opened = open
    }
}
