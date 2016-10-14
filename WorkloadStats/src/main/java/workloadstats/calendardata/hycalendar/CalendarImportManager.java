package workloadstats.calendardata.hycalendar;

import workloadstats.utils.EventType;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.utils.EventUtility;



/**
 * Control class for handling University calendar data importing
 *
 * @author Ilkka
 */
public class CalendarImportManager {

    private Scanner lukija;
    private CalendarImportParser calendarImportParser;
    private CalendarImportSorter calendarImportSorter;
    private Map<String, EventType> identifiedSummaries;
    private List<Course> courses;

    public CalendarImportManager(Calendar calendar) throws SocketException {
        this.lukija = new Scanner(System.in);

        this.calendarImportParser = new CalendarImportParser(calendar);
        this.calendarImportSorter = new CalendarImportSorter();
//        this.identifiedSummaries = identifyUniqueSummaries();

        this.courses = new ArrayList<>();
    }

    /**
     * Return list of courses identified by the user in Course event-wrappers
     *
     * @return List of courses in event-wrappers
     */
    public List<Course> getCourses() {
        return courses;
    }

////////////////////////////////////////////////////////////////////////////////   
    /**
     * Identify found unique summaries with input from the user Uses getId() as
     * a helper
     *
     * @return Map with summaries as keys and identification EventTypes as
     * values
     */

    public Set<String> getSummariesToIdentify() {
        return calendarImportParser.getUniqueSummaries();
    }

    /**
     * Giant method for parsing imported data with userinput. To be refactored in the future.
     * @param newSummaries
     * @param identifiedTypes
     * @param identifiedCourses
     * @param eventParents
     * @return 
     */
    public List<Course> userIdentifiedEvents(Map<String, String> newSummaries, Map<String, EventType> identifiedTypes, Map<String, Boolean> identifiedCourses, Map<String, String> eventParents) {
        List<Course> crs = new ArrayList<>();

        for (String oldSummary : identifiedCourses.keySet()) {

            boolean isCourse = identifiedCourses.get(oldSummary);
            if (isCourse) {
                String newSummary = newSummaries.get(oldSummary);
                EventType eventType = identifiedTypes.get(oldSummary);

                List<String> oldSummariesThatBelongToThisCourse = new ArrayList<>();
                //base event for the course itself is some event too -> we need to add it too
                oldSummariesThatBelongToThisCourse.add(oldSummary);
                for (String s : eventParents.keySet()) {
                    if (eventParents.get(s).equals(newSummary)) {
                        if (!identifiedCourses.get(s)) {
                            oldSummariesThatBelongToThisCourse.add(s);
                        }
                    }
                }

                //let's make a course
                Event dummy = calendarImportParser.getEventsOfASummary(oldSummary).get(0);                
                Course course = calendarImportSorter.getNewCourse(dummy, oldSummary, newSummary);                

                //let's add events to this course
                for (String s : oldSummariesThatBelongToThisCourse) {
                    String newChildSummary = newSummaries.get(s);
                    EventType newChildEt = identifiedTypes.get(s);
                    List<Event> oldUnidentifiedEvents = calendarImportParser.getEventsOfASummary(s);
                    List<Event> newIdentifiedEvents = calendarImportSorter.getEventsForParent(oldUnidentifiedEvents, eventType, newChildSummary);
                    course.addEventList(newIdentifiedEvents);
                }
                
                crs.add(course);      
                
                
            }
        }
        
        return crs;
    }


//    /**
//     * Get type identification from the user
//     *
//     * @param summary to be identified
//     * @return identified EventType
//     */

////////////////////////////////////////////////////////////////////////////////    
    /**
     * Build Course-events from calendar events identified as courses
     */

//    /**
//     * Get new names for summaries from the user Helper method for
//     * buildCoursesFromIdentifiedEvents
//     *
//     * @param summary old summary name
//     * @return new summary from user input
//     */

////////////////////////////////////////////////////////////////////////////////    


}
