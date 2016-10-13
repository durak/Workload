package workloadstats.calendardata.mycalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import workloadstats.calendardata.CalendarFileManager;
import workloadstats.calendardata.hycalendar.CalendarImportManager;
import workloadstats.calendardata.hycalendar.CalendarImportParser;
import workloadstats.calendardata.hycalendar.CalendarImportSorter;
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
        this.myCalendarParser = new MyCalendarParser(currentCalendar);
        courses = this.myCalendarParser.getCourses();
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
        updateCalendar();
        try {
            return calendarFileManager.saveCalendarToFile(currentCalendar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyCalendarControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean loadFile(File selectedFile) {
        try {
            Calendar loaded = calendarFileManager.loadCalendarFile(selectedFile);
            if (!calendarFileManager.wasCalendarFileBuiltWithThisProgram(loaded)) {
                return false;
            }                        
            myCalendarParser = new MyCalendarParser(loaded);
            currentCalendar = loaded;
            courses = myCalendarParser.getCourses();
            return true;

        } catch (Exception ex) {            
            Logger.getLogger(MyCalendarControl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public CalendarImportManager getCalendarImportManager(File selectedFile) {
        CalendarImportManager calImpMan = null;        
        try {
            
            Calendar imported = calendarFileManager.loadCalendarFile(selectedFile);
            calImpMan = new CalendarImportManager(imported);
//            myCalendarParser = new MyCalendarParser(loaded);
//            currentCalendar = loaded;
//            courses = myCalendarParser.getCourses();
            

        } catch (Exception ex) {
            Logger.getLogger(MyCalendarControl.class.getName()).log(Level.SEVERE, null, ex);                        
        }
        return calImpMan;
    }
    
    public void importNewCourses(List<Course> imports) {
        this.courses = imports;
    }

}
