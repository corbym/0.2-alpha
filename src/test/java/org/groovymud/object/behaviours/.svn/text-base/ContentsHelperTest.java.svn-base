package org.groovymud.object.behaviours;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.groovymud.object.MudObject;
import org.groovymud.object.alive.AbstractMOB;
import org.groovymud.object.alive.Alive;

public class ContentsHelperTest extends TestCase {

	public void testGetContentsDescription() {
		Set<MudObject> objects = new HashSet<MudObject>();
		objects.add(createMudObject());
		Set<MudObject> objects2 = new HashSet<MudObject>();
		MudObject two = createMudObject();
		objects.add(two);

		Map<Object, Set<MudObject>> contents = new TreeMap<Object, Set<MudObject>>();
		contents.put("something", objects);
		contents.put("somethingelse", objects2);

		ContentsHelper helper = new ContentsHelper();
		String desc = helper.getContentsDescription(contents, (Alive) two, true);
		assertEquals("A something", desc);
	}

	public void testGetContentsDescriptionTwoOthers() {
		Set<MudObject> objects = new HashSet<MudObject>();
		objects.add(createMudObject());
		objects.add(createMudObject());

		Set<MudObject> objects2 = new HashSet<MudObject>();
		MudObject two = createMudObject();
		objects.add(two);
		Set<MudObject> objects3 = new HashSet<MudObject>();
		objects3.add(createMudObject());

		Map<Object, Set<MudObject>> contents = new TreeMap<Object, Set<MudObject>>();
		contents.put("something", objects);
		contents.put("somethingelse", objects2);
		contents.put("one more", objects3);

		ContentsHelper helper = new ContentsHelper();
		String desc = helper.getContentsDescription(contents, (Alive) two, true);
		assertEquals("A one more and two somethings", desc);
	}

	protected MudObject createMudObject() {
		MudObject obj = new AbstractMOB() {

			public void doHeartBeat() {
				// TODO Auto-generated method stub

			}

			public void initialise() {
				// TODO Auto-generated method stub

			}

			public String getArrivalMessage(String direction) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getDepartureMessage(String direction) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getMessage(String messageKey, String[] replacements) {
				// TODO Auto-generated method stub
				return null;
			}


			public void addStatus(String name) {
				// TODO Auto-generated method stub
				
			}

			public void removeStatus(String name) {
				// TODO Auto-generated method stub
				
			}

		};
		return obj;
	}
}
