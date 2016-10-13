package workloadstats.calendardata.hycalendar;

import workloadstats.utils.EventType;
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
//    private Map<String, EventType> identifyUniqueSummaries() {
//        Map<String, EventType> identifiedSummaries = new HashMap<>();
//
//        Set<String> uniqueSummaries = calendarImportParser.getUniqueSummaries();
//        for (String uniqueSummary : uniqueSummaries) {
//            EventType identified = getId(uniqueSummary);
//            identifiedSummaries.put(uniqueSummary, identified);
//        }
//
//        return identifiedSummaries;
//    }
    public Set<String> getSummariesToIdentify() {
        return calendarImportParser.getUniqueSummaries();
    }

    public List<Course> userIdentifiedEvents(Map<String, String> newSummaries, Map<String, EventType> identifiedTypes, Map<String, Boolean> identifiedCourses, Map<String, String> eventParents) {
        List<Course> crs = new ArrayList<>();

        for (String oldSummary : identifiedCourses.keySet()) {
            String newSummary = newSummaries.get(oldSummary);
            EventType eventType = identifiedTypes.get(oldSummary);
            boolean isCourse = identifiedCourses.get(oldSummary);

            List<String> childSummaries = new ArrayList<>();
            childSummaries.add(oldSummary);
            for (String oldEventSummary : newSummaries.keySet()) {
                String parentForOldSummary = eventParents.get(oldEventSummary);
                if (newSummary.equals(parentForOldSummary) && !identifiedCourses.get(oldEventSummary)) {
                    childSummaries.add(oldEventSummary);
                }

            }

            // Turn dummy event into a course with user inputted summary
            if (isCourse) {
                Event dummy = calendarImportParser.getEventsPerSummaryString().get(oldSummary).get(0);
                Course finallyIamCourse = (calendarImportSorter.getNewCourse(dummy, oldSummary, newSummaries.get(oldSummary)));

                for (String childSummary : childSummaries) {
                    EventType et = identifiedTypes.get(childSummary);
                    String newEventSummary = newSummaries.get(childSummary);
                    List<Event> dummies = calendarImportParser.getEventsOfASummary(childSummary);
                    finallyIamCourse.addEventList(dummies);
                }
                crs.add(finallyIamCourse);
            }

        }
        return crs;
    }

    private List<Event> turnEventsToUserInputtedType(List<Event> rawEvents, EventType et) {
        return null;
    }

//    /**
//     * Get type identification from the user
//     *
//     * @param summary to be identified
//     * @return identified EventType
//     */
//    private EventType getId(String summary) {
//        System.out.println(summary);
//        System.out.print("Mikä tämä on: ");
//        String tyyppi = lukija.nextLine();
//        EventType t = EventType.valueOf(tyyppi);
//        return t;
//    }
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Build Course-events from calendar events identified as courses
     */
//    public void buildCoursesFromIdentifiedEvents() {
//        for (String identifiedSummary : identifiedSummaries.keySet()) {
//            if (identifiedSummaries.get(identifiedSummary).equals(EventType.COURSE)) {
//                String newSummary = getBetterName(identifiedSummary);
//                List<Event> courseEvents = calendarImportParser.getEventsOfASummary(identifiedSummary);
//                Course newCourse = calendarImportSorter.getNewCourse(identifiedSummary, newSummary, courseEvents);
//                courses.add(newCourse);
//
//                List<Event> lectures = calendarImportSorter.getNewLectures(newSummary, courseEvents);
//                newCourse.addEventList(lectures);
//            }
//        }
//    }
//    /**
//     * Get new names for summaries from the user Helper method for
//     * buildCoursesFromIdentifiedEvents
//     *
//     * @param summary old summary name
//     * @return new summary from user input
//     */
//    private String getBetterName(String summary) {
//        System.out.println(summary);
//        System.out.print("Anna parempi nimi tälle: ");
//        String name = lukija.nextLine();
//        return name;
//    }
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Build Exercise-events from calendar events identified as Exercises
     */
    public void buildExercisesFromIdentifiedEvents() {
        for (String identifiedSummary : identifiedSummaries.keySet()) {
            if (identifiedSummaries.get(identifiedSummary).equals(EventType.EXERCISE)) {
                Course relatedCourse = getRelatedCourse(identifiedSummary);
                String newSummary = relatedCourse.getCourseName();
                List<Event> exerciseEvents = calendarImportParser.getEventsOfASummary(identifiedSummary);

                List<Event> exercises = calendarImportSorter.getNewExercises(newSummary, exerciseEvents);
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
