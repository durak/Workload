package workloadstats.calendardata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import workloadstats.domain.Course;
import workloadstats.domain.Event;


/**
 *
 * @author Ilkka
 */
public class MyCalendarParserTest {

    Calendar calendar;
    List<Course> courses;
    MyCalendarParser myParser;

    public MyCalendarParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException, ParserException {

        File calendarFile = new File("testCalendar.ics");
        FileInputStream my = new FileInputStream(calendarFile);        
        CalendarBuilder cb = new CalendarBuilder();
        calendar = cb.build(my);
        myParser = new MyCalendarParser(calendar);
        courses = myParser.getCourses();
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void jasentelynJalkeenPalautetaanOikeaMaaraOlioita() {
        int iCalJasennetytLuokittelemattomatMerkinnat = calendar.getComponents(Component.VEVENT).size();
        int olioidenMaara = 0;
        for (Course course : courses) {
            olioidenMaara++;                                //itse kurssiolio
            olioidenMaara += course.getAllEvents().size();  //kaikki kurssin merkinnät            
        }
        assertEquals(iCalJasennetytLuokittelemattomatMerkinnat, olioidenMaara);
    }
    
    @Test
    public void eventilleLoydetaanParentKurssi() {
        Course crs = courses.get(0);
        Event ev = crs.getAllEvents().get(0);
        assertEquals(myParser.getCourseForEvent(ev), crs);                
    }
}
