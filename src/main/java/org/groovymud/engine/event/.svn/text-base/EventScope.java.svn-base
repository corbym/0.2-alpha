package org.groovymud.engine.event;

import org.groovymud.engine.event.observer.Observer;

/* Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
public class EventScope {

    private int scope;

    /**
     *A general container scope.
     */
    public static final EventScope CONTAINER = new EventScope(0);

    /**
     * player to player scope, delivered from one "player" to another
     */
    public static final EventScope PLAYER_SCOPE = new EventScope(1);

    /**
     * room scope events, passed to all contents
     */
    public static final EventScope ROOM_SCOPE = new EventScope(2);

    /**
     * mud engine events, such as channel info or room loading events
     */
    public static final EventScope GLOBAL_SCOPE = new EventScope(3);

    private EventScope(int scopeLevel) {
        this.scope = scopeLevel;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public boolean isObjectInScope(Observer observer) {
        if (observer.getScope().equals(this)) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof EventScope)) {
            return false;
        }
        return this.scope == ((EventScope) obj).getScope();
    }
}
