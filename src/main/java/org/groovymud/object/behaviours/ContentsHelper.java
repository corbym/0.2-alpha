package org.groovymud.object.behaviours;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.groovymud.object.AbstractMudObject;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.utils.NumberToWordConverter;
import org.groovymud.utils.WordUtils;

public class ContentsHelper {
	/**
	 * helper method to get a description of the contents in a map. the map
	 * would contain name/value pairs from getMudObjectsMap() although this can
	 * be any map with name, hashset<mudobject> values
	 * 
	 * @param contents
	 * @param looker
	 * @param alive
	 * @return
	 * @throws IOException
	 */
	public String getContentsDescription(Map<Object, Set<MudObject>> contents, Alive looker, boolean alive) {
		StringBuffer description = new StringBuffer();
		Map contentCopy = new HashMap(contents);
		removeMudObject(looker, contentCopy);
		Set keyset = contentCopy.keySet();
		Iterator keys = keyset.iterator();
		int i = 0;
		while (keys.hasNext()) {
			String key = (String) keys.next();
			HashSet set = (HashSet) contentCopy.get(key);
			Iterator x = set.iterator();
			AbstractMudObject obj = (AbstractMudObject) x.next();
			if (set.size() > 1) {
				description.append(NumberToWordConverter.convert(set.size(), i == 0) + " " + WordUtils.pluralize(key));
			}
			if (set.size() == 1) {
				if (obj.requiresArticle()) {
					key = WordUtils.affixIndefiniteArticle(key, i == 0);
				}
				description.append(key);
			}
			if (keys.hasNext()) {
				if (i == contentCopy.size() - 2) {
					description.append(" and ");
				} else {
					description.append(", ");
				}
			}

			i++;
		}
		return description.toString();
	}

	/**
	 * removes the specified object from a map of contents warning: alters the
	 * contents map
	 * 
	 * @param obj
	 * @param contents
	 * @param keys
	 */
	protected void removeMudObject(MudObject obj, Map<Object, HashSet<MudObject>> contents) {
		Set keyset = new HashSet(contents.keySet());
		Iterator keys = keyset.iterator();

		while (keys.hasNext()) {
			String key = (String) keys.next();
			HashSet set = (HashSet) contents.get(key);
			set.remove(obj);
			if (set.isEmpty()) {
				contents.remove(key);
			}
		}
	}
}
