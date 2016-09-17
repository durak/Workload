package workloadstats.domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Status;

public class Course extends Event {

    private String courseId;
    private String name;
    private int credit;
    private Date start;
    private Date end;

    private boolean finished;

    private List<Event> lectures;
    private List<Event> exercises;
    private List<Event> personal;
    private List<Event> teamwork;
    private List<Event> exams;
    private List<List> allEvents;

    public Course(VEvent ve) {
        super(ve);
        this.lectures = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.personal = new ArrayList<>();
        this.teamwork = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.allEvents = new ArrayList<>();
    }

    public Course(PropertyList pl) {
        super(pl);
        this.lectures = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.personal = new ArrayList<>();
        this.teamwork = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.allEvents = new ArrayList<>();
    }

//    public Course(String courseId) {
//        this.courseId = courseId;
//        this.lectures = new ArrayList<>();
//        this.exercises = new ArrayList<>();
//        this.personal = new ArrayList<>();
//        this.teamwork = new ArrayList<>();
//        this.exams = new ArrayList<>();
//        this.allEvents = new ArrayList<>();
//
//        allEvents.add(lectures);
//        allEvents.add(exercises);
//        allEvents.add(personal);
//        allEvents.add(teamwork);
//        allEvents.add(exams);
//
//    }
//    public String getName() {
//        return "";
//    }
    public void addEvent(Event event) {
        if (event.getClass() == Lecture.class) {
            lectures.add(event);
        }
        if (event.getClass() == Exercise.class) {
            exercises.add(event);
        }
        if (event.getClass() == Personal.class) {
            personal.add(event);
        }
        if (event.getClass() == Teamwork.class) {
            teamwork.add(event);
        }

        if (event.getClass() == Exam.class) {
            exams.add(event);
        }
    }

    public void addEventList(List<Event> list) {
        for (Event event : list) {
            addEvent(event);
        }
    }

    public List<Event> getLectures() {
        return this.lectures;
    }

    public List<Event> getExercises() {
        return this.exercises;
    }

    public List<Event> getPersonal() {
        return this.personal;
    }

    public List<Event> getTeamwork() {
        return this.teamwork;
    }

    public List<Event> getExams() {
        return this.exams;
    }

    public void finishCourse() throws IOException, URISyntaxException, ParseException {

        for (List eventList : this.allEvents) {
            for (Iterator it = eventList.iterator(); it.hasNext();) {
                Event e = (Event) it.next();
                Property status = e.getProperties().getProperty("STATUS");
                status.setValue("CONFIRMED");

            }
        }
    }

}
