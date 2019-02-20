package edu.studyup.util;

import java.util.HashMap;
import java.util.Map;

import edu.studyup.entity.Event;

/***
 * 
 * This class is a temporary class to be used in place of a database. The static
 * variable eventList holds all the event data.
 * 
 * @author Shivani
 * 
 */
public class DataStorage {
	private static Map<Integer, Event> eventData = new HashMap<Integer, Event>();

	public static void addEvent(int id, Event e) {
		eventData.put(id, e);
	}
	
	public static Event getEvent(int id) {
		return eventData.get(id);
	}
	
	public static void removeEvent(int id) {
		eventData.remove(id);
	}
	
	public static void emptyData() {
		eventData.clear();
	}

	public static Map<Integer, Event> copyData() {
		Map<Integer, Event> copy = eventData;
		return copy;
	}
}
