package workloadstats.domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Status;

public class Course {

    private String courseId;
    private String name;
    private int credit;
    private Date start;
    private Date end;

    private boolean finished;

    private List<VEvent> lectures;
    private List<VEvent> exercises;
    private List<VEvent> personal;
    private List<VEvent> teamwork;
    private List<VEvent> exams;
    private List<List> allEvents;

    public Course(String courseId) {
        this.courseId = courseId;
        this.lectures = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.personal = new ArrayList<>();
        this.teamwork = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.allEvents = new ArrayList<>();

        allEvents.add(lectures);
        allEvents.add(exercises);
        allEvents.add(personal);
        allEvents.add(teamwork);
        allEvents.add(exams);

    }

    public String getName() {
        return "";
    }

    public void addEvent(VEvent vevent) {
        if (vevent.getClass() == Lecture.class) {
            lectures.add(vevent);
        }
        if (vevent.getClass() == Exercise.class) {
            exercises.add(vevent);
        }
        if (vevent.getClass() == Personal.class) {
            personal.add(vevent);
        }
        if (vevent.getClass() == Teamwork.class) {
            teamwork.add(vevent);
        }

        if (vevent.getClass() == Exam.class) {
            exams.add(vevent);
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
