package workloadstats.calendardata.mycalendar;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;
import workloadstats.domain.model.EventType;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Exercise;
import workloadstats.domain.model.Lecture;
import workloadstats.domain.model.Personal;
import workloadstats.domain.model.Teamwork;
import workloadstats.domain.model.Uncategorized;

/**
 * Parser for programs own calendar data & domain model object builder
 * @author Ilkka
 */
public class MyCalendarParser {

    private Calendar calendar;

    private final List<Course> courses;
    private final Map<EventType, List<Event>> eventsPerType;
    private final Map<String, Course> courseWithString;
    private final Map<String, Course> courseWithUid;

    public MyCalendarParser(Calendar calendar) {
        this.calendar = calendar;
        this.courses = new ArrayList<>();
        this.eventsPerType = initializeEventsMap();
        this.courseWithString = new HashMap<>();
        this.courseWithUid = new HashMap<>();

        allEvents();
        addEventstoCourses();
    }
    
    /**
     * Get list of Course events parsed from data
     * @return List of Course events
     */
    public List<Course> getCourses() {
        return this.courses;
    }
    
    /**
     * Find a Course event that is a parent for the parameter event
     * @param ve
     * @return 
     */
    public Course getCourseForEvent(Event ve) {        
        String uid = ve.getProperty(Property.RELATED_TO).getValue();
        return courseWithUid.get(uid);
    }

    private Map<EventType, List<Event>> initializeEventsMap() {
        Map<EventType, List<Event>> evpt = new HashMap<>();
        EnumSet<EventType> set = EnumSet.allOf(EventType.class);
        for (EventType eventType : set) {
            evpt.put(eventType, new ArrayList<>());
        }
        return evpt;
    }

    private void allEvents() {

        for (Iterator i = calendar.getComponents(Component.VEVENT).iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            VEvent ve = (VEvent) component;

            EventType t = eventIdentifier(ve);
            Event iNeedAHome;
            switch (t) {
                case COURSE:
                    iNeedAHome = new Course(ve);
                    this.courses.add((Course) iNeedAHome);
                    courseWithString.put(iNeedAHome.getEventName(), (Course) iNeedAHome);
                    courseWithUid.put(iNeedAHome.getUid().getValue(), (Course) iNeedAHome);
                    break;
                case LECTURE:
                    iNeedAHome = new Lecture(ve);
                    eventsPerType.get(t).add(iNeedAHome);
                    break;
                case EXERCISE:
                    iNeedAHome = new Exercise(ve);
                    eventsPerType.get(t).add(iNeedAHome);
                    break;
                case PERSONAL:
                    iNeedAHome = new Personal(ve);
                    eventsPerType.get(t).add(iNeedAHome);
                    break;
                case TEAMWORK:
                    iNeedAHome = new Teamwork(ve);
                    eventsPerType.get(t).add(iNeedAHome);
                    break;
                case TRASH:
                    iNeedAHome = new Uncategorized(ve);
                    eventsPerType.get(t).add(iNeedAHome);
                    break;
            }

        }

    }

    private EventType eventIdentifier(VEvent unidentifiedEvent) {
        EnumSet<EventType> set = EnumSet.allOf(EventType.class);
        EventType search = EventType.TRASH;
        for (EventType eventType : set) {
            for (Iterator i = unidentifiedEvent.getProperties().getProperties(Property.CATEGORIES).iterator(); i.hasNext();) {
                Categories c = (Categories) i.next();
                if (eventType.toString().equals(c.getValue())) {
                    search = eventType;
                }
            }

        }

        return search;
    }

    private void addEventstoCourses() {
        for (EventType eventType : eventsPerType.keySet()) {
            List<Event> li = eventsPerType.get(eventType);
            for (Iterator<Event> it = li.iterator(); it.hasNext();) {
                Event event = it.next();
                Course parent = getCourseForEvent(event);
                parent.addEvent(event);
            }
        }
    }

}
