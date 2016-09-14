package org.groovymud.object;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.action.DestroyEvent;
import org.groovymud.engine.event.observer.IObservable;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.engine.event.observer.Observer;
import org.groovymud.object.behaviours.ContainerLookBehaviour;
import org.groovymud.object.behaviours.LookBehaviour;
import org.groovymud.object.behaviours.LookBehaviourImpl;
import org.groovymud.object.registry.InventoryHandler;
import org.groovymud.object.registry.Registry;

/**
 * a mud object container, like a sack or something
 * 
 * has a default inventory handler
 * 
 * @author corbym
 * 
 */
public abstract class AbstractContainer extends AbstractMudObject implements Container {
    
    private final static Logger logger = Logger.getLogger(AbstractContainer.class);

    private InventoryHandler inventoryHandler;
    private long maxCapacity;
   
    public boolean checkCanAddItem(MudObject object) {
        if (object.getWeight() == CANNOT_PICK_UP || object == this) {
            return false;
        }
        double currentCapacity = calculateCurrentCapacity();
        return currentCapacity + getCapacityAlpha(object) <= getMaxCapacity();
    }

    /**
     * calculates the "fullness" of the container based on a function of the
     * weight
     * 
     * @return
     */
    protected long calculateCurrentCapacity() {
        long capacity = 0;
        Collection<MudObject> set = getItems();
        Iterator<MudObject> setI = set.iterator();

        while (setI.hasNext()) {
            MudObject x = (MudObject) setI.next();
            capacity += getCapacityAlpha(x);
        }

        return capacity;
    }

    protected double getCapacityAlpha(MudObject x) {
        return x.getWeight() / 10;
    }

    public void addItem(MudObject object) {
        addItemToInventoryHandler(object, getInventoryHandler());
    }

    protected void addItemToInventoryHandler(MudObject object, InventoryHandler handler) {
        if (!checkCanAddItem(object)) {
            logger.warn("cannot add object to this container");
            return;
        }
        // remove it from its old container if it exists
        if (object.getCurrentContainer() != null) {
            object.getCurrentContainer().removeItem(object);
        }
        object.setCurrentContainer(this);
        object.setCurrentLocation(null);

        handler.addMudObject(object);
    }

    public MudObject removeItem(MudObject object) {
        return removeItemFromInventoryHandler(object, getInventoryHandler());
    }

    protected MudObject removeItemFromInventoryHandler(MudObject object, InventoryHandler handler) {
        if (object instanceof Observable) {
            ((Observable) object).deleteObserver(this);
        }
        return handler.removeMudObject(object);
    }

    public Set<MudObject> getItems() {
        return getItemsFromInventoryHandler(getInventoryHandler());
    }

    protected Set<MudObject> getItemsFromInventoryHandler(InventoryHandler handler) {
        return handler.getMudObjects();
    }

    /**
     * 
     * @returns map containing the name, hashset(object) mapping for the
     *          inventory handler's contents
     */
    public Map<Object, Set<MudObject>> getMudObjectsMap() {
        return getMudObjectsMapFromInventoryHandler(getInventoryHandler());
    }

    protected Map<Object, Set<MudObject>> getMudObjectsMapFromInventoryHandler(InventoryHandler handler) {
        return handler.getMudObjectsMap();
    }

    public Set<MudObject> getItems(String name) {
        return getItemsFromInventoryHandler(name, getInventoryHandler());
    }

    protected Set<MudObject> getItemsFromInventoryHandler(String name, InventoryHandler handler) {
        return handler.getMudObjects(name);
    }

    public InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }

    public void setInventoryHandler(InventoryHandler handler) {
        this.inventoryHandler = handler;
    }

    public void update(IObservable o, IScopedEvent arg) {
        if (!arg.getScope().isObjectInScope((Observer) this)) {
            setChanged();
            notifyObservers(arg);
        } else {
            doEvent(arg);
        }
    }

    public void doEvent(IScopedEvent event) {
        doEventInCollection(event, getItems());
    }

    public void doEventInCollection(IScopedEvent event, Collection<? extends IObservable> collection) {
        Iterator<? extends IObservable> contentIterator = collection.iterator();
        while (contentIterator.hasNext()) {
            IObservable o = contentIterator.next();
            o.doEvent(event);
        }
    }

    @Override
    public boolean doCommand(String command, String argsAsString, MudObject performingObject) {
        return super.doCommand(command, argsAsString, performingObject);
    }

    public boolean doCommandInContents(String command, String argsAsString, Container performingObject, Collection<MudObject> collection) {
        boolean done = false;

        Iterator<MudObject> items = collection.iterator();
        while (items.hasNext()) {
            MudObject contentItem = items.next();
            done = contentItem.doCommand(command, argsAsString, (MudObject) performingObject);
            if (done) {
                break;
            }
        }

        return done;
    }

	@Override
    public double getWeight() {
        return super.getWeight() + calculateCurrentCapacity();
    }

    public EventScope getScope() {
        return EventScope.CONTAINER;
    }

    public void setMaxCapacity(long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public long getMaxCapacity() {
        return maxCapacity;
    }
    public LookBehaviour getLookBehaviour() {       
        return new ContainerLookBehaviour();
    }


}
