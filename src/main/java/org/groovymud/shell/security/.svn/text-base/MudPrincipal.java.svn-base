package org.groovymud.shell.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.security.auth.Subject;

import net.sf.cglib.core.CollectionUtils;

import org.apache.log4j.Logger;

import com.sun.security.auth.PrincipalComparator;

public class MudPrincipal implements Principal, PrincipalComparator, Serializable {

	private static final String[] levels = { "god", "lord", "creator", "trialcreator", "player", "guest" };
	private static final Logger logger = Logger.getLogger(MudPrincipal.class);
	/**
	 * @serial
	 */
	private static final long serialVersionUID = 6368149668689598146L;

	String name;
	int value;

	public MudPrincipal(String string) {
		name = string;
		value = getMudPrincipalLevel(string);
	}

	private int getMudPrincipalLevel(String string) {
		return Arrays.asList(levels).indexOf(string);
	}

	public String getName() {
		return name;
	}

	public boolean implies(Subject subject) {
		Set<MudPrincipal> set = subject.getPrincipals(MudPrincipal.class);
		Iterator<MudPrincipal> i = set.iterator();
		while (i.hasNext()) {
			MudPrincipal p = i.next();
			if (p.value == this.value) {
				logger.debug(p.name + " == " + this.name);
				return true;
			}
			if (p.value < this.value) {
				logger.debug(this.name + " -> " + p.name);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MudPrincipal)) {
			return false;
		}
		return ((MudPrincipal) obj).name.equals(name);
	}

	public String toString() {
		return super.toString() + ": " + this.name;
	}

	@Override
	public int hashCode() {
		return getName().hashCode() * 37 + value;
	}
}
