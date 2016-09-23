/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.domain.model;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.fortuna.ical4j.model.Date;

import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.UidGenerator;
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
    VEvent ve1;
    VEvent ve2;
    Date start;
    Date end;

    UidGenerator ug;

    public CourseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SocketException {

        this.ug = new UidGenerator("uidGen");
        Calendar c = Calendar.getInstance();
        start = new Date(new Date(c.getTime()));

        ve1 = new VEvent(start, "testi");
        ve2 = new VEvent(start, "testi2");
        ve1.getProperties().add(ug.generateUid());
        ve2.getProperties().add(ug.generateUid());
        course = new Course(ve1);

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

    @Test
    public void yksienEventtienLisaysMeneeOikeisiinListoihin() {
        course.addEventList(lisaaEriEventtejaKurssiin());

        assertEquals(1, course.getExams().size());
        assertEquals(1, course.getExercises().size());
        assertEquals(1, course.getLectures().size());
        assertEquals(1, course.getPersonal().size());
        assertEquals(1, course.getTeamwork().size());
    }

    @Test
    public void useampienEventtienLisaysMeneeOikeisiinListoihin() {
        course.addEventList(lisaaEriEventtejaKurssiin());
        course.addEventList(lisaaEriEventtejaKurssiin());
        
        assertEquals(2, course.getExams().size());
        assertEquals(2, course.getExercises().size());
        assertEquals(2, course.getLectures().size());
        assertEquals(2, course.getPersonal().size());
        assertEquals(2, course.getTeamwork().size());
    }

    private List<Event> lisaaEriEventtejaKurssiin() {
        List<Event> events = new ArrayList<>();
        events.add(new Exam(ve2));
        events.add(new Exercise(ve2));
        events.add(new Lecture(ve2));
        events.add(new Personal(ve2));
        events.add(new Teamwork(ve2));
        return events;
    }
}
