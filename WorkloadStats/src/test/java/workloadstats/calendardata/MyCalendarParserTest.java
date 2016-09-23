package workloadstats.calendardata;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import workloadstats.calendardata.CalendarBuilderImpl;
import workloadstats.calendardata.mycalendar.MyCalendarParser;
import workloadstats.domain.model.Course;
import workloadstats.utils.EventUtilities;

/**
 *
 * @author Ilkka
 */
public class MyCalendarParserTest {

    Calendar calendar;
    List<Course> courses;

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

        EventUtilities eu = new EventUtilities();
        File calendarFile = new File("testCalendar.ics");
        FileInputStream my = new FileInputStream(calendarFile);
        CalendarBuilderImpl builder = new CalendarBuilderImpl();
        calendar = builder.build(my);
        MyCalendarParser myParser = new MyCalendarParser(calendar);
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
}
