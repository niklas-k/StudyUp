package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		//Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		
		DataStorage.eventData.put(event.getEventID(), event);
	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

//	@Test
//	//example
//	void testUpdateEventName_GoodCase() throws StudyUpException {
//		int eventID = 1;
//		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
//		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
//	}
//	
//	@Test
//	//example
//	void testUpdateEvent_WrongEventID_badCase() {
//		int eventID = 3;
//		Assertions.assertThrows(StudyUpException.class, () -> {
//			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
//		  });
//	}
	
	@Test 
	void testAddStudent_updatesSize_GoodCase() throws StudyUpException{
		Student student1 = new Student();
		student1.setFirstName("Jane");
		student1.setLastName("Doe");
		student1.setEmail("JaneDoe@email.com");
		student1.setId(2);	
		Event event = DataStorage.eventData.get(1);
		int priorSize = event.getStudents().size();
		eventServiceImpl.addStudentToEvent(student1, 1);
		event = DataStorage.eventData.get(1);
		int newSize = event.getStudents().size();
		assert(newSize == priorSize + 1);
	}
	
	@Test void testAddStudent_addThirdStudent_BadCase() throws StudyUpException {
		//Create Student
		Student student1 = new Student();
		student1.setFirstName("Jane");
		student1.setLastName("Doe");
		student1.setEmail("JaneDoe@email.com");
		student1.setId(2);		
		eventServiceImpl.addStudentToEvent(student1, 1);
		
		//Create Student
		Student student2 = new Student();
		student2.setFirstName("Jonathan");
		student2.setLastName("Doe");
		student2.setEmail("JonDoe@email.com");
		student2.setId(3);
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(student2, 1);
		  });
		
	}
	
	@Test 
	void testActiveEvents_GoodCase() {
		Event event1 = DataStorage.eventData.get(1);
		//set date to future
		Date date = new Date(event1.getDate().getTime() + 500000);
		event1.setDate(date);
		DataStorage.eventData.put(1, event1);
		
		List<Event> events = eventServiceImpl. getActiveEvents();
		for(Event e: events) {
			Date currDate = new Date();
			//checks if currDate comes before event date
			assert(currDate.before(e.getDate()));
		}
	}
	
	@Test
	void testActiveEvents_notActiveEvent_BadCase() {
		Event event1 = DataStorage.eventData.get(1);
		//set date to past
		Date date = new Date(0);
		event1.setDate(date);
		DataStorage.eventData.put(1, event1);
		
		List<Event> events = eventServiceImpl.getActiveEvents();
		for(Event e: events) {
			Date currDate = new Date();
			//checks if currDate comes before event date
			assert(currDate.before(e.getDate()));
		}
	}
	
}
