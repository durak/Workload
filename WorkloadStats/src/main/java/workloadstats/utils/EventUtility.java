package workloadstats.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import net.fortuna.ical4j.model.PropertyList;
import workloadstats.domain.Course;
import workloadstats.domain.Event;

/**
 * Static helper utility methods
 *
 * @author Ilkka
 */
public class EventUtility {

    private EventUtility() {
    }

    /**
     * Calculate duration in minutes for a calendar event
     *
     * @param event
     * @return duration in minutes
     */
    public static long getDuration(Event event) {
        Instant start = event.getStartDate().getDate().toInstant();
        Instant end = event.getEndDate().getDate().toInstant();

        long gap = ChronoUnit.MINUTES.between(start, end);

        return gap;
    }

    /**
     * Calculate sum of durations for a list of events
     *
     * @param events
     * @return Sum of durations in minutes
     */
    public static long getSumOfDurations(List<Event> events) {
        long sum = 0;
        for (Event event : events) {
            sum += getDuration(event);
        }
        return sum;
    }

    public static boolean findIfCoursesShareSameOldSummary(Course c1, Course c2) {
        PropertyList pl = c1.getProperties("CATEGORIES");
        PropertyList pl2 = c2.getProperties("CATEGORIES");
        for (Object object : pl2) {
            if (pl.contains(object) && !object.toString().contains("COURSE")) {
                return true;
            }
        }

        return false;
    }
    
    public static boolean findIfEventsShareSameStartAndEndTimes(Event ev1, Event ev2) {
        boolean eventsAreSame = true;
        if (!ev1.getStartDate().getDate().equals(ev2.getStartDate().getDate())) {
            eventsAreSame = false;
        }
        if (!ev1.getEndDate().getDate().equals(ev2.getEndDate().getDate())) {
            eventsAreSame = false;
        }
        
        return eventsAreSame;
    }
    
    public static boolean findIfEventWithSameStartAndEndTimeExistsInAListOfEvents(Event ev, List<Event> lev) {
        for (Event oldEvent : lev) {
            if (findIfEventsShareSameStartAndEndTimes(ev, oldEvent)) {
                return true;
            }
        }
        
        return false;
    }

}
