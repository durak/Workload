package workloadstats.calendardata;


import workloadstats.utils.EventType;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;
import workloadstats.domain.Course;
import workloadstats.domain.Event;


/**
 * Control class for handling University calendar data importing
 *
 * @author Ilkka
 */
public class CalendarImportManager {
    
    private CalendarImportParser calendarImportParser;
    private CalendarImportBuilder calendarImportBuilder;
    private Map<String, EventType> identifiedSummaries;   

    public CalendarImportManager(Calendar calendar) throws SocketException {        

        this.calendarImportParser = new CalendarImportParser(calendar);
        this.calendarImportBuilder = new CalendarImportBuilder();        
    }
    
    /**
     * Return Unique summaries found in the imported raw calendar events
     * @return 
     */
    public Set<String> getSummariesToIdentify() {
        return calendarImportParser.getUniqueSummaries();
    }

    /**
     * Giant method for parsing raw imported data with userinput. To be refactored in the future.
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
                Course course = calendarImportBuilder.getNewCourse(dummy, oldSummary, newSummary);                

                //let's add events to this course
                for (String s : oldSummariesThatBelongToThisCourse) {
                    String newChildSummary = newSummaries.get(s);
                    EventType newChildEt = identifiedTypes.get(s);                    
                    List<Event> oldUnidentifiedEvents = calendarImportParser.getEventsOfASummary(s);
                    List<Event> newIdentifiedEvents = calendarImportBuilder.getEventsForParent(oldUnidentifiedEvents, newChildEt, newChildSummary);
                    course.addEventList(newIdentifiedEvents);
                }
                
                crs.add(course);      
                
                
            }
        }
        
        return crs;
    }

}
