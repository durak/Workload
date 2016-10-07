package workloadstats.calendardata.mycalendar;

import java.net.SocketException;
import java.text.ParseException;
import java.util.List;
import net.fortuna.ical4j.model.Calendar;
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
import workloadstats.utils.EventUtilities;

/**
 * Control class for program's own calendar data & domain model object handling
 * @author Ilkka
 */
public class MyCalendarControl {

    private Calendar calendar;
    private List<Course> courses;
    private MyCalendarParser myCalendarParser;
    private final UidGenerator ug;
    private EventUtilities eventUtilities;

    public MyCalendarControl(Calendar calendar) throws SocketException {
        this.ug = new UidGenerator("uidGen");
        this.eventUtilities = new EventUtilities();
        this.calendar = calendar;
        this.myCalendarParser = new MyCalendarParser(calendar);
        courses = this.myCalendarParser.getCourses();

    }

    public List<Course> getCourses() {
        return courses;
    }
    
    /**
     * Build a new Course event with input from the user
     * @param summary
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException 
     */
    public Course buildNewCourse(String summary, String startDate, String endDate) throws ParseException {                                        
        PropertyList props = new PropertyList();
        props.add(new DtStart(startDate));
        props.add(new DtEnd(endDate));
        props.add(new DtStamp());
        props.add(new Summary(summary));
        props.add(ug.generateUid());        
        props.add(new Categories("COURSE"));
        props.add(VEVENT_TENTATIVE);
        Course newCourse = new Course(props);
        
        return newCourse;
    }
    
    /**
     * Calculate parameter event's duration in minutes
     * @param event
     * @return Duration in minutes
     */
    public long getEventDuration(Event event) {
        return eventUtilities.getDuration(event);
    }
}
