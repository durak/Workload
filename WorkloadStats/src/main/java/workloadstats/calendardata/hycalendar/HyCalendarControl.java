package workloadstats.calendardata.hycalendar;

import workloadstats.domain.model.EventType;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Lecture;

/**
 * Control class for handling University calendar data importing
 * @author Ilkka
 */
public class HyCalendarControl {

    private Scanner lukija;

    private HyCalendarParser hyParser;
    private HyEventSorter hySorter;

    private final Map<String, EventType> identifiedSummaries;

    private List<Course> courses;

    public HyCalendarControl(Calendar calendar) throws SocketException {
        this.lukija = new Scanner(System.in);

        this.hyParser = new HyCalendarParser(calendar);
        this.hySorter = new HyEventSorter();
        this.identifiedSummaries = identifyUniqueSummaries();

        this.courses = new ArrayList<>();
    }
    
    /**
     * Return list of courses identified by the user in Course event-wrappers
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
    private Map<String, EventType> identifyUniqueSummaries() {
        Map<String, EventType> identifiedSummaries = new HashMap<>();

        Set<String> uniqueSummaries = hyParser.getUniqueSummaries();
        for (String uniqueSummary : uniqueSummaries) {
            EventType identified = getId(uniqueSummary);
            identifiedSummaries.put(uniqueSummary, identified);
        }

        return identifiedSummaries;
    }

    /**
     * Get type identification from the user
     *
     * @param summary to be identified
     * @return identified EventType
     */
    private EventType getId(String summary) {
        System.out.println(summary);
        System.out.print("Mikä tämä on: ");
        String tyyppi = lukija.nextLine();
        EventType t = EventType.valueOf(tyyppi);
        return t;
    }
////////////////////////////////////////////////////////////////////////////////    

    /**
     * Build Course-events from calendar events identified as courses
     */
    public void buildCoursesFromIdentifiedEvents() {
        for (String identifiedSummary : identifiedSummaries.keySet()) {
            if (identifiedSummaries.get(identifiedSummary).equals(EventType.COURSE)) {
                String newSummary = getBetterName(identifiedSummary);
                List<Event> courseEvents = hyParser.getEventsOfASummary(identifiedSummary);
                Course newCourse = hySorter.getNewCourse(identifiedSummary, newSummary, courseEvents);
                courses.add(newCourse);

                List<Event> lectures = hySorter.getNewLectures(newSummary, courseEvents);
                newCourse.addEventList(lectures);
            }
        }
    }

    /**
     * Get new names for summaries from the user Helper method for
     * buildCoursesFromIdentifiedEvents
     *
     * @param summary old summary name
     * @return new summary from user input
     */
    private String getBetterName(String summary) {
        System.out.println(summary);
        System.out.print("Anna parempi nimi tälle: ");
        String name = lukija.nextLine();
        return name;
    }
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Build Exercise-events from calendar events identified as Exercises
     */
    public void buildExercisesFromIdentifiedEvents() {
        for (String identifiedSummary : identifiedSummaries.keySet()) {
            if (identifiedSummaries.get(identifiedSummary).equals(EventType.EXERCISE)) {
                Course relatedCourse = getRelatedCourse(identifiedSummary);
                String newSummary = relatedCourse.getCourseName();
                List<Event> exerciseEvents = hyParser.getEventsOfASummary(identifiedSummary);

                List<Event> exercises = hySorter.getNewExercises(newSummary, exerciseEvents);
                relatedCourse.addEventList(exercises);
            }
        }
    }

    /**
     * Ask the user what course is related to this exercise summary
     *
     * @param exerciseSummary
     * @return related course
     */
    private Course getRelatedCourse(String exerciseSummary) {
        System.out.println(exerciseSummary);
        System.out.print("Mihin kurssiin tämä kuuluu: ");
        String kurssi = lukija.nextLine();

        Course chosenCourse = null;
        for (Course course : courses) {
            if (course.getCourseName().equals(kurssi)) {
                chosenCourse = course;
            }
        }

        return chosenCourse;
    }

}
