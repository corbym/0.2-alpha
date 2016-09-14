package org.groovymud.object.registry;

import groovy.lang.GroovyObjectSupport;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.groovymud.engine.JMudEngine;
import org.groovymud.object.AbstractMudObject;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.AbstractMOB;
import org.groovymud.object.alive.Player;
import org.groovymud.object.room.Location;
import org.groovymud.object.room.Room;
import org.groovymud.shell.command.CommandInterpreter;

import com.thoughtworks.xstream.XStream;

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
public abstract class MudObjectAttendant {

	private String theVoid;
	private String playerImpl;

	private JMudEngine mudEngine;

	private GroovyScriptEngine groovyScriptEngine;

	private String baseRoomLocation;

	private String mudSpacePlayerLocation;

	private static final Logger logger = Logger.getLogger(MudObjectAttendant.class);

	private Registry objectRegistry;

	private CommandInterpreter interpreter;

	public MudObjectAttendant() {
		super();
	}

	public MudObject load(String path, boolean initialise) throws CompilationFailedException, FileNotFoundException, InstantiationException {
		return load(path, null, initialise);
	}

	/**
	 * loads all mudobject objects and initialises them
	 * 
	 * @param path
	 *            path to the script (can be . or / form)
	 * @param constructorArgs
	 *            list of objects to pass to the constructor
	 * @param initialise
	 *            call initialise on the object?
	 * @param permissions
	 *            security permissions
	 * @return
	 * @throws CompilationFailedException
	 * 
	 * @throws InstantiationException
	 * @throws FileNotFoundException
	 */

	public MudObject load(String path, List<?> constructorArgs, boolean initialise) throws CompilationFailedException, InstantiationException, FileNotFoundException {
		AbstractMudObject obj = null;

		try {
			Class<?> objectClass = null;
			try {
				objectClass = Class.forName(path);
			} catch (ClassNotFoundException e) {

			}
			if (objectClass == null) {
				objectClass = loadClass(path);
			}
			if (constructorArgs == null) {
				obj = createNewInstance(objectClass);
			} else {
				obj = createNewInstance(objectClass, constructorArgs);
			}
			obj.setScriptFileName(path);
			if (initialise) {
				configure(obj);
			}
		} catch (IllegalAccessException e) {
			logger.error(e, e);
		} catch (ResourceException e) {
			logger.error(e, e);
		} catch (ScriptException e) {
			logger.error(e, e);
		} catch (IllegalArgumentException e) {
			logger.error(e, e);
		} catch (SecurityException e) {
			logger.error(e, e);
		} catch (InvocationTargetException e) {
			logger.error(e, e);
		} catch (NoSuchMethodException e) {
			logger.error(e, e);
		}
		return obj;
	}

	public Class<?> loadClass(String path) throws ResourceException, ScriptException {
		return getGroovyScriptEngine().loadScriptByName(path);
	}

	protected AbstractMudObject createNewInstance(Class<?> objectClass, List constructorList) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {
		Iterator<?> x = constructorList.iterator();
		Class<?>[] classes = new Class[constructorList.size()];
		Object[] objects = new Object[constructorList.size()];
		int i = 0;
		while (x.hasNext()) {
			Object obj = x.next();
			classes[i] = obj.getClass();
			objects[i] = obj;
			i++;
		}
		return (AbstractMudObject) objectClass.getConstructor(classes).newInstance(objects);
	}

	protected AbstractMudObject createNewInstance(Class<?> objectClass) throws InstantiationException, IllegalAccessException {
		return (AbstractMudObject) objectClass.newInstance();
	}

	protected void configure(MudObject obj) {
	    obj.setCommandInterpreter(getInterpreter());
        obj.setMudObjectAttendant(this);
        obj.setRegistry(getInterpreter().getObjectRegistry());
        
		if (isContainerInstance(obj)) {
			Container con = castToContainer(obj);
			if (con.getInventoryHandler() == null) {
				InventoryHandler handler = createInventoryHandler();
				con.setInventoryHandler(handler);
			}
			con.getInventoryHandler().relinkContent(con);
		}

		
		obj.initialise();
		if (obj != null && isRoomInstance(obj)) {
			getObjectRegistry().addRoom(castToRoom(obj));
		} else {
			getObjectRegistry().addItem(obj);
		}

	}

	protected Room castToRoom(MudObject obj) {
		return (Room) obj;
	}

	protected boolean isRoomInstance(MudObject obj) {
		return obj instanceof Room;
	}

	protected boolean isContainerInstance(MudObject obj) {
		return obj instanceof Container;
	}

	protected Container castToContainer(MudObject obj) {
		return ((Container) obj);
	}

	public Player loadPlayerData(String username) throws CompilationFailedException, ResourceException, ScriptException, FileNotFoundException {
		Player player = null;
		XStream xstream = getXStream();
		GroovyScriptEngine groovyScriptEngine = getGroovyScriptEngine();
		if (groovyScriptEngine.getParentClassLoader() == null) {
			groovyScriptEngine.loadScriptByName(getPlayerImpl());
		}
		File playerFile = createFile(username);
		if (playerFile.exists()) {
			InputStream xmlIn = createFileInputStream(playerFile);
			player = (Player) xstream.fromXML(xmlIn);
			configure(player);

		}
		return player;
	}

	protected InputStream createFileInputStream(File playerFile) throws FileNotFoundException {
		return new FileInputStream(playerFile);
	}

	protected File createFile(String username) {
		return new File(mudSpacePlayerLocation + username + ".xml");
	}

	public void movePlayerToLocation(Player player) throws InstantiationException, FileNotFoundException, CompilationFailedException {
		Container room;
        String containerName = player.getCurrentLocation().getCurrentContainerFileName();

		room = (Container) getObjectRegistry().getMudObject(containerName);
		if (room == null) {
			room = (Container) load(containerName, true);
		}
		if (room == null) {
			throw new InstantiationException("room was null!");
		}
		room.addItem(player);
	}

	public void movePlayerToVoid(Player player) throws IOException, FileNotFoundException {
		try {
        	Location location = new Location(theVoid);
        
            player.setCurrentLocation(location);
			movePlayerToLocation(player);
		} catch (CompilationFailedException e) {
			logger.error(e, e);
		} catch (InstantiationException e) {
			logger.error(e, e);
		}
	}

	public Player createNewPlayer(String username) throws InstantiationException, FileNotFoundException, CompilationFailedException {
		Player player = (Player) load(playerImpl, false);
		String upperName = username.substring(0, 1).toUpperCase() + username.substring(1);

		player.setName(upperName);
		player.addShortName(upperName);
		player.addShortName(username);
		configure(player);

		return player;
	}

	public void savePlayerData(Player player) {
		XStream xstream = getXStream();
		try {
			xstream.omitField(GroovyObjectSupport.class, "metaClass");
			xstream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			FileOutputStream out = new FileOutputStream(createFile(player.getPlayerCredentials().getUsername()));
			xstream.toXML(player, out);

		} catch (FileNotFoundException e) {
			try {
				player.getTerminalOutput().writeln(e.getMessage());
			} catch (IOException e1) {
				logger.error(e1, e1);
			}

			logger.error(e, e);
		}
	}

	public Registry getObjectRegistry() {
		return objectRegistry;
	}

	public void setObjectRegistry(Registry objectRegistry) {
		this.objectRegistry = objectRegistry;
	}

	public String getMudSpacePlayerLocation() {
		return mudSpacePlayerLocation;
	}

	public void setMudSpacePlayerLocation(String mudSpacePlayerLocation) {
		this.mudSpacePlayerLocation = mudSpacePlayerLocation.trim();
	}

	public String getBaseRoomLocation() {
		return baseRoomLocation;
	}

	public void setBaseRoomLocation(String baseRoomLocation) {
		this.baseRoomLocation = baseRoomLocation.trim();
	}

	public abstract XStream getXStream();

	public abstract InventoryHandler createInventoryHandler();

	public GroovyScriptEngine getGroovyScriptEngine() {
		return groovyScriptEngine;
	}

	public void setGroovyScriptEngine(GroovyScriptEngine groovyScriptEngine) {
		this.groovyScriptEngine = groovyScriptEngine;
	}

	public CommandInterpreter getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(CommandInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	public JMudEngine getMudEngine() {
		return mudEngine;
	}

	public void setMudEngine(JMudEngine mudEngine) {
		this.mudEngine = mudEngine;
	}

	public String getTheVoid() {
		return theVoid;
	}

	public void setTheVoid(String theVoid) {
		this.theVoid = theVoid;
	}

	public String getPlayerImpl() {
		return playerImpl;
	}

	public void setPlayerImpl(String playerImpl) {
		this.playerImpl = playerImpl;
	}
}
