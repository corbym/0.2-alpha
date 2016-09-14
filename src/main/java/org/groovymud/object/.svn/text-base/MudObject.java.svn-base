package org.groovymud.object;

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
import java.util.List;

import org.groovymud.engine.event.HeartBeatListener;
import org.groovymud.engine.event.observer.IObservable;
import org.groovymud.object.behaviours.LookBehaviour;
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.object.registry.Registry;
import org.groovymud.shell.command.CommandInterpreter;
import org.groovymud.object.room.Location;

public interface MudObject extends IObservable, HeartBeatListener {

    public final static long CANNOT_PICK_UP = -1L;

    public void initialise();

    public String getScriptFileName();

    public void setScriptFileName(String fileName);

    public String getDescription();

    public void setDescription(String description);

    public String getName();

    public void setName(String name);

    public String getShortDescription();

    public void setShortDescription(String desc);

    public List<String> getShortNames();

    public void addShortName(String shortNames);

    public double getWeight();

    public void setWeight(double weight);

    public void dest(boolean wep);

    public Container getCurrentContainer();

    public void setCurrentContainer(Container owner);

    public Location getCurrentLocation();

    public CommandInterpreter getCommandInterpreter();

    public void setCommandInterpreter(CommandInterpreter interpreter);

    public MudObjectAttendant getMudObjectAttendant();

    public void setMudObjectAttendant(MudObjectAttendant mudObjectAttendant);

    public void setCurrentLocation(Location location);

    public MudObject load(String path);

    boolean doCommand(String command, String argsAsString, MudObject performingObject);

    public boolean requiresArticle();

    public void setRequiresIndefinateArticle(boolean requiresIndefinateArticle);

    public LookBehaviour getLookBehaviour();

    public void setRegistry(Registry objectRegistry);

    public Registry getRegistry();

}
