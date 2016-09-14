package org.groovymud.object.registry;

import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.groovymud.object.AbstractMudObject;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Player;
import org.groovymud.object.room.AbstractRoom;
import org.groovymud.object.room.Room;
import org.groovymud.shell.command.CommandInterpreter;

import com.thoughtworks.xstream.XStream;

public class MudObjectAttendantTest extends TestCase {

	boolean methodCalled;
	protected boolean methodCalled1;
	protected boolean methodCalled2;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		Logger.getRootLogger().setLevel(Level.OFF);
	}

	/*
	 * Test method for
	 * 'org.groovymud.object.registry.MudObjectAttendant.load(String, boolean)'
	 */
	public void testLoad() throws ResourceException, ScriptException, InstantiationException, IllegalAccessException, CompilationFailedException, FileNotFoundException, MalformedURLException {
		String scriptName = "/mockScriptName.groovy";
		final AbstractMudObject mudObject = new AbstractMudObject() {

			@Override
			public void doHeartBeat() {
				// TODO Auto-generated method stub

			}

		};
		MockControl scriptCtrl = MockClassControl.createControl(GroovyScriptEngine.class, new Class[] { String.class }, new String[] { new String("file://") });
		final GroovyScriptEngine eng = (GroovyScriptEngine) scriptCtrl.getMock();
		eng.loadScriptByName(scriptName);
		scriptCtrl.setReturnValue(mudObject.getClass());
		scriptCtrl.replay();

		MudObjectAttendant attnd = new MudObjectAttendant() {

			@Override
			protected AbstractMudObject createNewInstance(Class<?> objectClass) throws InstantiationException, IllegalAccessException {
				// TODO Auto-generated method stub
				return mudObject;
			}

			@Override
			public InventoryHandler createInventoryHandler() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GroovyScriptEngine getGroovyScriptEngine() {
				// TODO Auto-generated method stub
				return eng;
			}

			@Override
			protected void configure(MudObject obj) {
				// TODO Auto-generated method stub

				assertSame(mudObject, obj);

			}

			@Override
			public XStream getXStream() {
				// TODO Auto-generated method stub
				return null;
			}

		};

		MudObject myObj = attnd.load(scriptName, true);
		scriptCtrl.verify();
		assertEquals(mudObject.getClass(), myObj.getClass());
		assertEquals(myObj.getScriptFileName(), scriptName);
	}

	public void testConfigureMudObject() {

		MockControl ctrl = MockControl.createControl(MudObject.class);
		MudObject mockMudObject = (MudObject) ctrl.getMock();

		MockControl regCtrl = MockClassControl.createControl(Registry.class);
		final Registry reg = (Registry) regCtrl.getMock();
		reg.addItem(mockMudObject);
		regCtrl.setVoidCallable();
		regCtrl.replay();
		final Registry reg2 = new Registry();

		final CommandInterpreter interpreter = new CommandInterpreter() {
			public Registry getObjectRegistry() {
				return reg2;
			}
		};

		MudObjectAttendant mudObjectAttendant = new MudObjectAttendant() {

			@Override
			public Registry getObjectRegistry() {
				// TODO Auto-generated method stub
				return reg;
			}

			@Override
			public CommandInterpreter getInterpreter() {
				// TODO Auto-generated method stub
				return interpreter;
			}

			@Override
			public InventoryHandler createInventoryHandler() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public XStream getXStream() {
				// TODO Auto-generated method stub
				return null;
			}

		};

		mockMudObject.setCommandInterpreter(interpreter);
		ctrl.setVoidCallable();
		mockMudObject.setMudObjectAttendant(mudObjectAttendant);
		ctrl.setVoidCallable();
		mockMudObject.setRegistry(reg2);
		ctrl.setVoidCallable();
		mockMudObject.initialise();
		ctrl.setVoidCallable();

		ctrl.replay();

		mudObjectAttendant.configure(mockMudObject);
		regCtrl.verify();
		ctrl.verify();

	}

	public void testConfigureContainer() {
		MockControl ctrl = MockControl.createControl(MudObject.class);
		final MudObject mockMudObject = (MudObject) ctrl.getMock();

		MockControl regCtrl = MockClassControl.createControl(Registry.class);
		final Registry reg = (Registry) regCtrl.getMock();
		reg.addItem(mockMudObject);
		regCtrl.setVoidCallable();
		regCtrl.replay();

		MockControl contCtrl = MockControl.createStrictControl(Container.class);
		final Container container = (Container) contCtrl.getMock();

		final InventoryHandler mockInv = new InventoryHandler() {
			@Override
			public void relinkContent(Container obj) {
				assertTrue(obj == container);
			}
		};

		container.getInventoryHandler();
		contCtrl.setReturnValue(null);

		container.setInventoryHandler(mockInv);
		contCtrl.setDefaultVoidCallable();

		container.getInventoryHandler();
		contCtrl.setReturnValue(mockInv);

		contCtrl.replay();

		final Registry reg2 = new Registry();

		final CommandInterpreter interpreter = new CommandInterpreter() {
			public Registry getObjectRegistry() {
				return reg2;
			}
		};
		MudObjectAttendant mudObjectAttendant = new MudObjectAttendant() {

			@Override
			protected boolean isContainerInstance(MudObject obj) {
				// TODO Auto-generated method stub
				return true;
			}

			public InventoryHandler createInventoryHandler() {
				return mockInv;
			};

			@Override
			protected Container castToContainer(MudObject obj) {
				// TODO Auto-generated method stub
				return container;
			}

			@Override
			public Registry getObjectRegistry() {
				// TODO Auto-generated method stub
				return reg;
			}

			@Override
			public CommandInterpreter getInterpreter() {
				// TODO Auto-generated method stub
				return interpreter;
			}

			@Override
			public XStream getXStream() {
				// TODO Auto-generated method stub
				return null;
			}

		};

		mockMudObject.setCommandInterpreter(interpreter);
		ctrl.setVoidCallable();
		mockMudObject.setMudObjectAttendant(mudObjectAttendant);
		ctrl.setVoidCallable();
		mockMudObject.setRegistry(reg2);
		ctrl.setVoidCallable();
		mockMudObject.initialise();
		ctrl.setVoidCallable();

		ctrl.replay();

		mudObjectAttendant.configure(mockMudObject);

		ctrl.verify();
		contCtrl.verify();
		regCtrl.verify();

	}

	public void testConfigureRoom() {
		MockControl ctrl = MockControl.createControl(MudObject.class);
		MudObject mockMudObject = (MudObject) ctrl.getMock();
		final Room room = new AbstractRoom() {

			@Override
			public void doHeartBeat() {
				// TODO Auto-generated method stub

			}

		};
		MockControl regCtrl = MockClassControl.createControl(Registry.class);
		final Registry reg = (Registry) regCtrl.getMock();
		reg.addRoom(room);
		regCtrl.setVoidCallable();
		regCtrl.replay();
		MockControl contCtrl = MockControl.createStrictControl(Container.class);
		final Container container = (Container) contCtrl.getMock();

		final InventoryHandler mockInv = new InventoryHandler() {
			@Override
			public void relinkContent(Container obj) {
				// TODO Auto-generated method stub
				assertTrue(obj == container);
			}
		};

		container.getInventoryHandler();
		contCtrl.setDefaultReturnValue(null);
		container.setInventoryHandler(mockInv);
		contCtrl.setDefaultVoidCallable();
		container.getInventoryHandler();
		contCtrl.setDefaultReturnValue(mockInv);

		contCtrl.replay();
		final Registry reg2 = new Registry();

		final CommandInterpreter interpreter = new CommandInterpreter() {
			public Registry getObjectRegistry() {
				return reg2;
			}
		};
		MudObjectAttendant mudObjectAttendant = new MudObjectAttendant() {

			@Override
			protected Room castToRoom(MudObject obj) {
				// TODO Auto-generated method stub
				return room;
			}

			@Override
			protected boolean isContainerInstance(MudObject obj) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected boolean isRoomInstance(MudObject obj) {
				// TODO Auto-generated method stub
				return true;
			}

			public InventoryHandler createInventoryHandler() {
				return mockInv;
			};

			@Override
			protected Container castToContainer(MudObject obj) {
				// TODO Auto-generated method stub
				return container;
			}

			@Override
			public Registry getObjectRegistry() {
				// TODO Auto-generated method stub
				return reg;
			}

			@Override
			public CommandInterpreter getInterpreter() {
				// TODO Auto-generated method stub
				return interpreter;
			}

			@Override
			public XStream getXStream() {
				// TODO Auto-generated method stub
				return null;
			}

		};

		mockMudObject.setCommandInterpreter(interpreter);
		ctrl.setVoidCallable();
		mockMudObject.setMudObjectAttendant(mudObjectAttendant);
		ctrl.setVoidCallable();
		mockMudObject.setRegistry(reg2);
		ctrl.setVoidCallable();
		mockMudObject.initialise();
		ctrl.setVoidCallable();

		ctrl.replay();

		mudObjectAttendant.configure(mockMudObject);

		ctrl.verify();
		contCtrl.verify();
		regCtrl.verify();

	}

	/*
	 * Test method for
	 * 'org.groovymud.object.registry.MudObjectAttendant.loadPlayerData(String)'
	 */
	public void testLoadPlayerData() throws CompilationFailedException, FileNotFoundException, ResourceException, ScriptException {
		final GroovyClassLoader mockClassLoader = new GroovyClassLoader() {

		};
		MockControl playerCtrl = MockControl.createControl(Player.class);
		final Player player = (Player) playerCtrl.getMock();
		final InputStream inputStream = new ByteArrayInputStream(new byte[] { '<', 'x', 'm', 'l', '/', '>' });

		final XStream mockXStream = new XStream() {

			@Override
			public Object fromXML(InputStream input) {
				// TODO Auto-generated method stub
				assertEquals(inputStream, input);
				methodCalled1 = true;
				return player;
			}
		};
		MockControl scriptCtrl = MockClassControl.createControl(GroovyScriptEngine.class, new Class[] { String.class }, new String[] { new String("file://") });
		final GroovyScriptEngine eng = (GroovyScriptEngine) scriptCtrl.getMock();
		eng.getParentClassLoader();
		scriptCtrl.setDefaultReturnValue(null);
		eng.loadScriptByName("player.Impl");
		scriptCtrl.setReturnValue(player.getClass());
		scriptCtrl.replay();
		MudObjectAttendant attend = new MudObjectAttendant() {

			protected InputStream createFileInputStream(File playerFile) throws FileNotFoundException {
				return inputStream;
			}

			@Override
			public String getPlayerImpl() {
				// TODO Auto-generated method stub
				return "player.Impl";
			}

			@Override
			protected File createFile(String username) {
				// TODO Auto-generated method stub
				return new File(username) {

					@Override
					public boolean exists() {
						// TODO Auto-generated method stub
						return true;
					}
				};
			}

			@Override
			public XStream getXStream() {
				// TODO Auto-generated method stub
				return mockXStream;
			}

			@Override
			public InventoryHandler createInventoryHandler() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void configure(MudObject obj) {

				methodCalled2 = true;
			}

			@Override
			public GroovyScriptEngine getGroovyScriptEngine() {
				// TODO Auto-generated method stub
				return eng;
			}
		};

		attend.loadPlayerData("player");

		assertTrue(methodCalled1);
		assertTrue(methodCalled2);
	}

	public void testCreateNewPlayer() throws CompilationFailedException, FileNotFoundException, InstantiationException {
		String username = "wombat";
		String upperuname = username.substring(0, 1).toUpperCase() + username.substring(1);
		String password = "x";
		MockControl mockCtrl = MockControl.createControl(Player.class);
		final Player mockPlayer = (Player) mockCtrl.getMock();
		mockPlayer.setName(username);
		mockCtrl.setVoidCallable();

		mockPlayer.setName(upperuname);
		mockCtrl.setVoidCallable();
		mockPlayer.setName(upperuname);
		mockCtrl.setVoidCallable();
		mockPlayer.getName();
		mockCtrl.setDefaultReturnValue(upperuname);
		mockPlayer.addShortName(upperuname);
		mockCtrl.setVoidCallable();
		mockPlayer.addShortName(upperuname);
		mockCtrl.setVoidCallable();
		mockPlayer.initialise();
		mockCtrl.setVoidCallable();
		MudObjectAttendant attnd = new MudObjectAttendant() {

			@Override
			public MudObject load(String path, boolean initialise) throws CompilationFailedException, InstantiationException, FileNotFoundException {
				// TODO Auto-generated method stub
				return mockPlayer;
			}

			@Override
			public InventoryHandler createInventoryHandler() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void configure(MudObject obj) {
				// TODO Auto-generated method stub
				methodCalled = true;
			}

			@Override
			public XStream getXStream() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		attnd.createNewPlayer(username);
		assertTrue(methodCalled);
	}

}
