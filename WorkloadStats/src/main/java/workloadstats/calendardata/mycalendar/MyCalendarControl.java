package workloadstats.calendardata.mycalendar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.model.Calendar;
import workloadstats.calendardata.CalendarFileManager;
import workloadstats.domain.model.Course;

/**
 * Control class for program's own calendar data & domain model object handling
 *
 * @author Ilkka
 */
public class MyCalendarControl {

    private Calendar currentCalendar;
    private List<Course> courses;
    private MyCalendarParser myCalendarParser;
    private CalendarFileManager calendarFileManager;

    public MyCalendarControl() {
        this.calendarFileManager = new CalendarFileManager();
        this.currentCalendar = calendarFileManager.getEmptyCalendar();
        this.myCalendarParser = new MyCalendarParser(currentCalendar);
        courses = this.myCalendarParser.getCourses();
        
        
    }

    public void initEmptyCalendar() {
        this.currentCalendar = calendarFileManager.getEmptyCalendar();        
    }

    public List<Course> getCourses() {
        return courses;
    }
    
    public void updateCalendar() {
        Calendar updated = calendarFileManager.getEmptyCalendar();
        for (Course course : courses) {
            updated.getComponents().add(course);
            updated.getComponents().addAll(course.getAllEvents());            
        }
        currentCalendar = updated;
    }
    
    public Calendar getCalendar() {
        return currentCalendar;
    }
    
    public boolean saveCalendar() {
        try {
            return calendarFileManager.saveCalendarToFile(currentCalendar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyCalendarControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
