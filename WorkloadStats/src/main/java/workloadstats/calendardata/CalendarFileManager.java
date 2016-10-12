package workloadstats.calendardata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

/**
 * Calendar file operations
 *
 * @author Ilkka
 */
public class CalendarFileManager {

    private final String calendarconfig = "calendarconfig.ics";
    private CalendarBuilder calendarBuilder;
    private CalendarOutputter calendarOutputter;

    TimeZoneRegistry tzr = TimeZoneRegistryFactory.getInstance().createRegistry();

    public CalendarFileManager() {
        this.calendarBuilder = new CalendarBuilder();
        this.calendarOutputter = new CalendarOutputter();
        

    }

    /**
     * To avoid complicated calendar setup, first we import a pre-generated
     * empty calendar file.
     */
    private Calendar readConfig() {
        InputStream in = CalendarFileManager.class.getClassLoader().getResourceAsStream(calendarconfig);

        Calendar calendar = null;
        try {
            calendar = calendarBuilder.build(in);
            TimeZoneRegistry  r = calendarBuilder.getRegistry();
            
        } catch (IOException ex) {
            Logger.getLogger(CalendarFileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserException ex) {
            Logger.getLogger(CalendarFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return calendar;
    }

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

//    public Calendar newCalendar() {
//        Calendar calendar = new Calendar();
//        calendar.getProperties().add(new ProdId("-//WorkLoadStats//iCal4j 1.0//EN"));
//        calendar.getProperties().add(Version.VERSION_2_0);
//        calendar.getProperties().add(CalScale.GREGORIAN);
//
//        return calendar;
//    }
//    public void addVEvents(List<VEvent> vevents) {
//        currentCalendar.getComponents().addAll(vevents);
//    }
}
