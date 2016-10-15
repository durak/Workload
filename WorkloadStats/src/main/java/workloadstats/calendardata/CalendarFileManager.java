package workloadstats.calendardata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.property.ProdId;


/**
 * Calendar file operations
 *
 * @author Ilkka
 */
public class CalendarFileManager {
    
    private final String programIdentifier = "WorkLoadStats";
    private final String calendarconfig = "calendarconfig.ics";
    private CalendarBuilder calendarBuilder;
    private CalendarOutputter calendarOutputter; 

    public CalendarFileManager() {
        this.calendarBuilder = new CalendarBuilder();
        this.calendarOutputter = new CalendarOutputter();

    }

    /**
     * To avoid complicated calendar setup, first we import a pre-generated
     * empty calendar file as a config file.
     */
    private Calendar readConfig() {
        InputStream in = CalendarFileManager.class.getClassLoader().getResourceAsStream(calendarconfig);

        Calendar calendar = null;
        try {
            calendar = calendarBuilder.build(in);            

        } catch (IOException ex) {
            Logger.getLogger(CalendarFileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserException ex) {
            Logger.getLogger(CalendarFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return calendar;
    }
    
    /**
     * Returns a new empty calendar file
     * @return 
     */
    public Calendar getEmptyCalendar() {
        return readConfig();
    }

    public boolean saveCalendarToFile(Calendar cal) throws FileNotFoundException {
        FileOutputStream fout = new FileOutputStream("mytestoutput.ics");
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.setValidating(true);
        try {
            outputter.output(cal, fout);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CalendarFileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ValidationException ex) {
            Logger.getLogger(CalendarFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
            
    /**
     * Loads parameter file and builds a calendar.
     * @param selectedFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParserException 
     */
    public Calendar loadCalendarFile(File selectedFile) throws FileNotFoundException, IOException, ParserException {
        FileInputStream selection = new FileInputStream(selectedFile);

        return calendarBuilder.build(selection);
    }
    
    /**
     * Checks if a calendar file was built by this program.
     * Used to block user from trying to load a generic ics file with load function,
     * when they should use Import function instead.
     * @param calendar
     * @return 
     */
    public boolean wasCalendarFileBuiltWithThisProgram(Calendar calendar) {
        ProdId id = calendar.getProductId();
        if (id == null) {
            return false;
        }
        return (id.getValue().contains(programIdentifier));                
    }
    

}
