package workloadstats.calendardata.mycalendar;

import java.util.List;
import net.fortuna.ical4j.model.Calendar;
import workloadstats.domain.model.Course;

/**
 *
 * @author Ilkka
 */
public class MyCalendarControl {
        
    private Calendar calendar;
    private List<Course> courses;
    private MyCalendarParser myCalendarParser;
    
    
    public MyCalendarControl(Calendar calendar) {
        this.calendar = calendar;
        this.myCalendarParser = new MyCalendarParser(calendar);
        courses = this.myCalendarParser.getCourses();
        
    }
    
    public List<Course> getCourses() {
        return courses;
    }
}
