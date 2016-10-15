package workloadstats.domain;

import java.util.ArrayList;
import java.util.List;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Course type calendar event All other events must belong to a course
 *
 * @author Ilkka
 */
public class Course extends Event {
      
    private List<Event> lectures;
    private List<Event> exercises;
    private List<Event> personal;
    private List<Event> teamwork;
    private List<Event> exams;

    public Course(VEvent ve) {
        super(ve);        
        this.lectures = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.personal = new ArrayList<>();
        this.teamwork = new ArrayList<>();
        this.exams = new ArrayList<>();

    }

    public Course(PropertyList pl) {
        super(pl);        
        this.lectures = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.personal = new ArrayList<>();
        this.teamwork = new ArrayList<>();
        this.exams = new ArrayList<>();

    }


    /**
     * Return a list of all events in this course
     *
     * @return
     */
    public List<Event> getAllEvents() {
        List<Event> allEvents = new ArrayList<>();
        allEvents.addAll(lectures);
        allEvents.addAll(exercises);
        allEvents.addAll(personal);
        allEvents.addAll(teamwork);
        allEvents.addAll(exams);

        return allEvents;
    }

    /**
     * Add calendar event to this course
     *
     * @param ev
     */
    public void addEvent(Event ev) {
        super.parentAnotherEvent(ev);
        updateStartDateOnAddition(ev);
        updateEndDateOnAddition(ev);

        if (ev.getClass() == Lecture.class) {
            lectures.add(ev);
        }
        if (ev.getClass() == Exercise.class) {
            exercises.add(ev);
        }
        if (ev.getClass() == Personal.class) {
            personal.add(ev);
        }
        if (ev.getClass() == Teamwork.class) {
            teamwork.add(ev);
        }
        if (ev.getClass() == Exam.class) {
            exams.add(ev);
        }
    }

    /**
     * Remove calendar event from this course
     *
     * @param ev
     */
    public void removeEvent(Event ev) {
        if (ev.getClass() == Lecture.class) {
            lectures.remove(ev);
        }
        if (ev.getClass() == Exercise.class) {
            exercises.remove(ev);
        }
        if (ev.getClass() == Personal.class) {
            personal.remove(ev);
        }
        if (ev.getClass() == Teamwork.class) {
            teamwork.remove(ev);
        }
        if (ev.getClass() == Exam.class) {
            exams.remove(ev);
        }
    }

    /**
     * Add list of events to this course
     *
     * @param list
     */
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

    /**
     * If event added to this course has earlier start date or later end date,
     * adjust course duration.
     */
    private void updateStartDateOnAddition(Event ev) {
        Date evStartDate = null;
        if (ev.getStartDate() != null) {
            evStartDate = ev.getStartDate().getDate();
        }
        if (evStartDate != null) {
            if (this.getStartDate().getDate().after(evStartDate)) {
                this.getStartDate().setDate(evStartDate);
            }
        }

    }

    private void updateEndDateOnAddition(Event ev) {
        Date evEndDate = null;

        if (ev.getEndDate() != null) {
            evEndDate = ev.getEndDate().getDate();
        }

        if (evEndDate != null) {
            if (this.getEndDate().getDate().before(evEndDate)) {
                this.getEndDate().setDate(evEndDate);
            }
        }

    }
}
