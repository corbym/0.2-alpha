package org.groovymud.utils;

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
import org.groovymud.object.MudObject;

/**
 * helps dealing with words and articles
 * 
 * @author matt
 * 
 */
public class WordUtils {

	/**
	 * won't work for all o ending words properly.
	 * 
	 * @param word
	 * @return
	 */
	public static String pluralize(String word) {
		if (word.endsWith("s")) {
			return word;
		}
		if (word.endsWith("y")) {
			return word.replaceAll("/y$/", "ies");
		}
		if (word.matches("(s|z|ch|sh|x)$")) {
			return word += "es";
		}

		return word += "s";
	}

	public static String affixIndefiniteArticle(MudObject obj) {
		if (obj.requiresArticle()) {
			return affixIndefiniteArticle(obj.getName(), false);
		}
		return obj.getName();
	}

	public static String affixDefiniteArticle(MudObject obj) {
		if (obj.requiresArticle()) {
			return affixDefiniteArticle(obj.getName(), false);
		}
		return obj.getName();
	}

	public static String affixIndefiniteArticle(String word) {
		return WordUtils.affixIndefiniteArticle(word, false);
	}

	public static String affixIndefiniteArticle(String word, boolean capitalise) {
		if (word.matches("$(a|e|i|o|u)")) {
			return (capitalise ? "An" : "an") + " " + word;
		}
		return (capitalise ? "A" : "a") + " " + word;
	}

	public static String affixDefiniteArticle(String word) {
		return affixDefiniteArticle(word, false);
	}

	public static String affixDefiniteArticle(String word, boolean capitalise) {
		return (capitalise ? "The" : "the") + " " + word;
	}

	public static String resolvePossessiveGender(String gender) {
		if (gender != null && gender.equalsIgnoreCase("male")) {
			return "his";
		}
		if (gender != null && gender.equalsIgnoreCase("female")) {
			return "her";
		}
		return "it's";
	}

	public static String resolveThirdPersonSingular(String gender) {
		if (gender != null && gender.equalsIgnoreCase("male")) {
			return "he";
		}
		if (gender != null && gender.equalsIgnoreCase("female")) {
			return "she";
		}
		return "it";
	}

	/**
	 * 
	 * @param word
	 * @return
	 */
	public static String depluralize(String word) {
		if (word.matches("^(a-zA-Z)ies$")) {
			return word.substring(0, word.length() - 1);
		}
		if (word.matches("ies$")) {
			return word.replaceAll("ies$", "y");
		}
		if (word.matches("(s|z|ch|sh|x)es$")) {
			return word.replaceAll("es$", "");
		}
		return word.replaceAll("s$", "");

	}
}
