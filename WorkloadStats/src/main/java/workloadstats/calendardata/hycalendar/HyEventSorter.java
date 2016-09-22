package workloadstats.calendardata.hycalendar;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import static net.fortuna.ical4j.model.property.Status.VEVENT_TENTATIVE;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.util.UidGenerator;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Exercise;
import workloadstats.domain.model.Lecture;

/**
 *
 * @author Ilkka
 */
public class HyEventSorter {

    private final UidGenerator ug;

    public HyEventSorter() throws SocketException {
        this.ug = new UidGenerator("uidGen");

    }

    /**
     * Compile new Course event-wrapper
     *
     * @param oldSummary summary from the University calendar
     * @param newSummary new summary from the user
     * @param courseEvents List of events identified as the main lectures of
     * this course, used as a source of information
     * @return new Course event-wrapper
     */
    public Course getNewCourse(String oldSummary, String newSummary, List<Event> courseEvents) {
        if (courseEvents == null) {
            return null;
        }
        PropertyList props = new PropertyList();
        props.add(new DtStart(getStartDate(courseEvents)));
        props.add(new DtEnd(getEndDate(courseEvents)));
        props.add(new DtStamp());
        props.add(new Summary(newSummary));
        props.add(ug.generateUid());
        props.add(new Categories(oldSummary));
        props.add(new Categories("COURSE"));
        props.add(VEVENT_TENTATIVE);

        return new Course(props);
    }

    /**
     * Get earliest value in DTStart in a list of events
     *
     * @param events List of events used as a time frame
     * @return earliest found DTstart date
     */
    public Date getStartDate(List<Event> events) {
        if (events == null) {
            return null;
        }
        Date start = events.get(0).getStartDate().getDate();
        for (Event event : events) {
            if (event.getStartDate().getDate().before(start)) {
                start = event.getStartDate().getDate();
            }
        }
        return start;
    }

    /**
     * Get the last value in DTEnd in a list of events
     *
     * @param events List of events used as a time frame
     * @return last found DTEnd date
     */
    public Date getEndDate(List<Event> events) {
        if (events == null) {
            return null;
        }
        Date end = events.get(0).getStartDate().getDate();
        for (Event event : events) {
            if (event.getEndDate().getDate().after(end)) {
                end = event.getEndDate().getDate();
            }
        }
        return end;
    }

    /**
     * Turn a list of University calendar events identified as lectures to a
     * list of Lecture objects in Event-wrappers
     *
     * @param newSummary New summary provided by the user
     * @param courseEvents List of events from University calendar that were
     * identified as lectures, but are still generic Event objects
     * @return List of Lecture-objects
     */
    public List<Event> getNewLectures(String newSummary, List<Event> courseEvents) {
        if (courseEvents == null) {
            return null;
        }
        List<Event> lectures = new ArrayList<>();
        for (Event courseEvent : courseEvents) {
            Lecture newLecture = new Lecture(courseEvent);
            newLecture.getSummary().setValue(newSummary + " Luento");
            newLecture.getProperties().add(new Categories("LECTURE"));
            newLecture.getProperties().add(VEVENT_TENTATIVE);
            newLecture.getProperties().add(new DtStamp());
            lectures.add(newLecture);
        }

        return lectures;
    }
    /**
     * Turn a list of University calendar events identified as exercises to a
     * list of Exercise objects in Event-wrappers
     * @param newSummary New summary provided by the user
     * @param exerciseEvents List of events from University calendar that were
     * identified as exercises, but are still generic Event objects
     * @return List of Exercise-objects
     */
    List<Event> getNewExercises(String newSummary, List<Event> exerciseEvents) {
        if (exerciseEvents == null) {
            return null;
        }
        List<Event> exercises = new ArrayList<>();
        for (Event exerciseEvent : exerciseEvents) {
            Exercise newExercise = new Exercise(exerciseEvent);
            newExercise.getSummary().setValue(newSummary + " Harkka");
            newExercise.getProperties().add(new Categories("EXERCISE"));
            newExercise.getProperties().add(VEVENT_TENTATIVE);
            newExercise.getProperties().add(new DtStamp());
            exercises.add(newExercise);
        }
        return exercises;
    }

}
