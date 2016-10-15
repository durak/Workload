package workloadstats.calendardata;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.util.UidGenerator;
import workloadstats.domain.Course;
import workloadstats.domain.Event;
import workloadstats.domain.Exercise;
import workloadstats.domain.Lecture;
import workloadstats.domain.Personal;
import workloadstats.domain.Teamwork;
import workloadstats.domain.Trash;
import workloadstats.utils.EventType;

/**
 * Helper class for turning parsed University calendar data into domain model
 * objects
 *
 * @author Ilkka
 */
public class CalendarImportBuilder {

    private final UidGenerator ug;

    public CalendarImportBuilder() throws SocketException {
        this.ug = new UidGenerator("uidGen");

    }

    public Course getNewCourse(Event dummy, String oldSummary, String newSummary) {
        PropertyList props = new PropertyList();
        props.add(new DtStart(dummy.getStartDate().getDate()));
        props.add(new DtEnd(dummy.getEndDate().getDate()));
        props.add(new DtStamp());
        props.add(new Summary(newSummary));
        props.add(ug.generateUid());
        props.add(new Categories(oldSummary));
        props.add(new Categories("COURSE"));

        Course crs = new Course(props);
        crs.setStatusTentative();

        return crs;
    }
    
    /**
     * Turn a list of unidentified dummy events to identified Domain model events
     * @param dummies
     * @param et
     * @param newSummary
     * @return List of identified domain model instances
     */
    public List<Event> getEventsForParent(List<Event> dummies, EventType et, String newSummary) {
        List<Event> identifiedEvents = new ArrayList<>();
        for (Event dummy : dummies) {

            dummy.getSummary().setValue(newSummary);
            dummy.getProperties().add(new Categories(et.toString()));
            dummy.getProperties().add(new DtStamp());
            dummy.setStatusTentative();

            Event iNeedAHome = null;
            switch (et) {

                case LECTURE:
                    iNeedAHome = new Lecture(dummy);

                    break;
                case EXERCISE:
                    iNeedAHome = new Exercise(dummy);

                    break;
                case PERSONAL:
                    iNeedAHome = new Personal(dummy);

                    break;
                case TEAMWORK:
                    iNeedAHome = new Teamwork(dummy);

                    break;
                case TRASH:
                    iNeedAHome = new Trash(dummy);

                    break;
            }

            identifiedEvents.add(iNeedAHome);
        }
        return identifiedEvents;
    }
}
