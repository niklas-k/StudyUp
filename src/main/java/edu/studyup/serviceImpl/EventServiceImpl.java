package edu.studyup.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.studyup.entity.Event;
import edu.studyup.entity.Student;
import edu.studyup.service.EventService;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

public class EventServiceImpl implements EventService {

	@Override
	public Event updateEventName(int eventID, String name) throws StudyUpException {
		Event event = DataStorage.getEvent(eventID);
		if(event == null) {
			throw new StudyUpException("No event found.");
		}

		if(name.length() > 20) {
			throw new StudyUpException("Length too long. Maximun is 20");
		}
		
		if(name.length() <= 0) {
			throw new StudyUpException("No name given.");
		}
		
		event.setName(name);
		DataStorage.addEvent(eventID, event);
		event = DataStorage.getEvent(event.getEventID());
		return event;
	}

	@Override
	public List<Event> getActiveEvents() {
		Map<Integer, Event> eventData = DataStorage.copyData();
		List<Event> activeEvents = new ArrayList<>();
		
		eventData.forEach((i, e) -> { if(e.getDate().after(new Date())) { activeEvents.add(e); }; });
		
		return activeEvents;
	}

	@Override
	public List<Event> getPastEvents() {
		Map<Integer, Event> eventData = DataStorage.copyData();
		List<Event> pastEvents = new ArrayList<>();
		
		eventData.forEach((i, e) -> { if(e.getDate().before(new Date())) { pastEvents.add(e); }; });
		
		return pastEvents;
	}

	@Override
	public Event addStudentToEvent(Student student, int eventID) throws StudyUpException {
		Event event = DataStorage.getEvent(eventID);
		if(event == null) {
			throw new StudyUpException("No event found.");
		}
		List<Student> presentStudents = event.getStudents();
		if(presentStudents == null) {
			presentStudents = new ArrayList<>();
		}
		
		if(presentStudents.size() >= 2) {
			throw new StudyUpException("Maximum occupancy.");
		}
		
		presentStudents.add(student);
		event.setStudents(presentStudents);		
		DataStorage.addEvent(eventID, event);
		return DataStorage.getEvent(eventID);
	}

	@Override
	public Event deleteEvent(int eventID) {		
		Event e = DataStorage.getEvent(eventID);
		DataStorage.removeEvent(eventID);
		return e;
	}

}
