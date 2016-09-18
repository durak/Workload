package workloadstats.newpackage;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;
import workloadstats.domain.Course;
import workloadstats.domain.Event;
import workloadstats.domain.Lecture;

/**
 *
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

    
    public List<Course> getCourses() {
        return courses;
    }
    
////////////////////////////////////////////////////////////////////////////////    
    private EventType getId(String summary) {
        System.out.println(summary);
        System.out.print("Mikä tämä on: ");
        String tyyppi = lukija.nextLine();
        EventType t = EventType.valueOf(tyyppi);
        return t;
    }

    private Map<String, EventType> identifyUniqueSummaries() {
        Map<String, EventType> is = new HashMap<>();
        
        Set<String> uniqueSummaries = hyParser.getUniqueSummaries();
        for (String uniqueSummary : uniqueSummaries) {
            EventType identified = getId(uniqueSummary);
            is.put(uniqueSummary, identified);
        }        
        
        return is;
    }
////////////////////////////////////////////////////////////////////////////////    
    private String getBetterName(String summary) {
        System.out.println(summary);
        System.out.print("Anna parempi nimi tälle: ");
        String name = lukija.nextLine();
        return name;
    }
    
    
    public void buildCoursesFromIdentifiedEvents() {
        for (String identifiedSummary : identifiedSummaries.keySet()) {
            if (identifiedSummaries.get(identifiedSummary).equals(EventType.COURSE)) {
                String newSummary = getBetterName(identifiedSummary);
                List<Event> courseEvents = hyParser.getEventsOfASummary(identifiedSummary);
                Course newCourse = hySorter.getNewCourse(identifiedSummary,newSummary, courseEvents);
                courses.add(newCourse);
                
                List<Event> lectures = hySorter.getNewLectures(newSummary, courseEvents);
                newCourse.addEventList(lectures);
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Ask the user what course is related to this exercise summary
     * @param exerciseSummary 
     * @return related course
     */
    private Course getRelatedCourse(String exerciseSummary) {
        System.out.println(exerciseSummary);
        System.out.print("Mihin kurssiin tämä kuuluu: ");
        String kurssi = lukija.nextLine();
        
        Course chosenCourse = null;
        for (Course course : courses) {
            if(course.getCourseName().equals(kurssi)) {
                chosenCourse = course;
            }
        }
        
        return chosenCourse;
    }
    
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

}
