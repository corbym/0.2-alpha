package org.groovymud.object.room;

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
/**
 * A location object holds data associated with the players current location you
 * can override this to provide more data to the mud, useful for virtual areas.
 * 
 * @author corbym
 * 
 */
public class Location {
	private String fileName; // for persistence

	public Location(String fileName) {
		this.fileName = fileName;
	}

	public void setCurrentContainerFileName(String currentContainerFileName) {
		this.fileName = currentContainerFileName;
	}

	public String getCurrentContainerFileName() {
		return fileName;
	}
}
