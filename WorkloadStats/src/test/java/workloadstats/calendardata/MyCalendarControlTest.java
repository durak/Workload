/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.calendardata;

import java.util.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import workloadstats.domain.Course;

/**
 *
 * @author Ilkka
 */
public class MyCalendarControlTest {
    MyCalendarControl mycalControl;
    
    public MyCalendarControlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mycalControl = new MyCalendarControl();
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
    public void aluksiTyhjaKalenteri() {
        assertEquals(mycalControl.getCourses().size(), 0);
    }
    @Test
    public void kalenterinTyhjaaminenToimii() {
        Calendar c = Calendar.getInstance();
        Date start = new Date(new Date(c.getTime()));
        VEvent ve1 = new VEvent(start, "testi");                        
        Course newCourse = new Course(ve1);
        mycalControl.getCourses().add(newCourse);
        assertEquals(mycalControl.getCourses().size(), 1);
        mycalControl.initEmptyCalendar();
        assertEquals(mycalControl.getCourses().size(), 0);
    }
    
    @Test
    public void kalenterinConfiginResettaaminenToimii() {
        Calendar c = Calendar.getInstance();
        Date start = new Date(new Date(c.getTime()));
        VEvent ve1 = new VEvent(start, "testi");                        
        Course newCourse = new Course(ve1);
        mycalControl.getCourses().add(newCourse);
        mycalControl.updateCalendar();
        assertEquals(mycalControl.getCourses().get(0), newCourse);
    }
    
    
}
