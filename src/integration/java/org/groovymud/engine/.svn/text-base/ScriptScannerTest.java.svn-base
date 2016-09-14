package org.groovymud.engine;
import java.io.IOException;

import groovy.util.GroovyScriptEngine;

import org.groovymud.engine.ScriptScanner;
import org.groovymud.object.registry.InventoryHandler;
import org.groovymud.object.registry.MudObjectAttendant;

import com.thoughtworks.xstream.XStream;

import junit.framework.TestCase;



public class ScriptScannerTest extends TestCase {
    public void testScanner(){
        ScriptScanner scanner = new ScriptScanner();
        
        scanner.setMudSpace("C:\\dev\\workspace-groovymud\\GroovyMud-0.2-alpha\\src\\mudspace\\");
        scanner.setAttendant(new MudObjectAttendant(){
        	@Override
        	public GroovyScriptEngine getGroovyScriptEngine() {
        		try {
					return new GroovyScriptEngine(new String[]{"C:\\dev\\workspace-groovymud\\GroovyMud-0.2-alpha\\src\\mudspace\\"});
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
        	}
			@Override
			public InventoryHandler createInventoryHandler() {
				return new InventoryHandler();
			}
			@Override
			public XStream getXStream() {
				return null;
			}
        	
        });
        scanner.scan();
        
    }
}
