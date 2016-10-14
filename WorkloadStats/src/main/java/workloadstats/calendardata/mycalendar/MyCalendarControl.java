package workloadstats.calendardata.mycalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.model.Calendar;
import workloadstats.calendardata.CalendarFileManager;
import workloadstats.calendardata.hycalendar.CalendarImportManager;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.utils.EventUtility;

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
    /**
     * Load empty calendar as the current calendar
     */
    public void initEmptyCalendar() {
        this.currentCalendar = calendarFileManager.getEmptyCalendar();
        this.myCalendarParser = new MyCalendarParser(currentCalendar);
        courses = this.myCalendarParser.getCourses();
    }
    
    /**
     * Return all courses in the current calendar
     * @return 
     */
    public List<Course> getCourses() {
        return courses;
    }
    
    /**
     * Reset current calendar's config file
     */
    public void updateCalendar() {
        Calendar updated = calendarFileManager.getEmptyCalendar();
        for (Course course : courses) {
            updated.getComponents().add(course);
            updated.getComponents().addAll(course.getAllEvents());
        }
        currentCalendar = updated;
    }
    
    
    /**
     * Save current calendar to file
     * @return 
     */
    public boolean saveCalendar() {
        updateCalendar();
        try {
            return calendarFileManager.saveCalendarToFile(currentCalendar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyCalendarControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Load selectedFile as the current calendar
     * @param selectedFile
     * @return 
     */
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
    
    /**
     * Return new CalendarImportManager instance
     * @param selectedFile
     * @return 
     */
    public CalendarImportManager getCalendarImportManager(File selectedFile) {
        CalendarImportManager calImpMan = null;
        try {

            Calendar imported = calendarFileManager.loadCalendarFile(selectedFile);
            calImpMan = new CalendarImportManager(imported);

        } catch (Exception ex) {
            Logger.getLogger(MyCalendarControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calImpMan;
    }

    /**
     * Go through a list of courses to be imported if course that already exists
     * stems from same OldSummary -> check for events to update to his existing
     * course else add imported course
     *
     * @param imports List of courses to import
     */
    public void importNewCourses(List<Course> imports) {
        List<Course> newCoursesToAdd = new ArrayList<>();

        for (Course toBeImported : imports) {
            boolean addNew = true;

            for (Course existingCourse : courses) {

                if (EventUtility.findIfCoursesShareSameOldSummary(toBeImported, existingCourse)) {
                    addNew = false;

                    for (Event newEvent : toBeImported.getAllEvents()) {
                        if (!EventUtility.findIfEventWithSameStartAndEndTimeExistsInAListOfEvents(newEvent, existingCourse.getAllEvents())) {
                            existingCourse.addEvent(newEvent);
                        }
                    }
                }
            }
            if (addNew) {
                newCoursesToAdd.add(toBeImported);
            }
        }

        courses.addAll(newCoursesToAdd);
    }

}
