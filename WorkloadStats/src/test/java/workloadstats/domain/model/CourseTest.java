/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.domain.model;

import java.util.Calendar;
import net.fortuna.ical4j.model.Date;

import net.fortuna.ical4j.model.component.VEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import workloadstats.domain.model.Course;

/**
 *
 * @author Ilkka
 */
public class CourseTest {
    
    Course course;
    Date start;
    Date end;
    
    public CourseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Calendar c = Calendar.getInstance();
        start = new Date(new Date(c.getTime()));

        VEvent ve = new VEvent(start, "testi");
        course = new Course(ve);

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void konstruktoriAsettaaAlkuajanOikein() {
        assertEquals(start, course.getStartDate().getDate());
    }
}
