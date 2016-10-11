package workloadstats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.calendardata.mycalendar.MyCalendarParser;
import workloadstats.calendardata.CalendarBuilderImpl;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.EventType;
import workloadstats.domain.model.Exercise;
import workloadstats.ui.Gui;
import workloadstats.utils.EventListAnalysis;
import workloadstats.utils.EventUtility;
import workloadstats.utils.StatusType;

/**
 *
 * @author Ilkka
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParserException, URISyntaxException, ParseException, ValidationException {

        List<Course> kurssitForGui = null;
        Calendar calendarTestGui = null;
        Scanner lukija = new Scanner(System.in);
        Boolean test = false;
        Boolean test2 = false;

        File calendarFile = new File("mycalendar2.ics");
        FileInputStream my = new FileInputStream(calendarFile);
        CalendarBuilderImpl builder = new CalendarBuilderImpl();
        Calendar myCal = builder.build(my);
        MyCalendarControl myCalendarControl = new MyCalendarControl(myCal);

        Gui gui = new Gui(myCalendarControl);
        SwingUtilities.invokeLater(gui);

        /*
         * Yliopiston kalenterin parseaminen ja tallentaminen uudeksi kalenteritiedostoksi 
         */
//        if (test) {
//            File calendarFile = new File("fi.ics");
//            File calendarFile2 = new File("my.ics");
//            FileInputStream fin = new FileInputStream(calendarFile);
//            FileInputStream my = new FileInputStream(calendarFile2);
//
//            CalendarBuilderImpl builder = new CalendarBuilderImpl();
//
//            Calendar hyCal = builder.build(fin);
//            Calendar myCal = builder.build(my);
//
//            HyCalendarControl hyControl = new HyCalendarControl(hyCal);
//            hyControl.buildCoursesFromIdentifiedEvents();
//            hyControl.buildExercisesFromIdentifiedEvents();
//            List<Course> kurssit = hyControl.getCourses();
//
//            for (Course course : kurssit) {
//                List<Event> courseEvents = course.getAllEvents();
//                myCal.getComponents().add(course);
//                myCal.getComponents().addAll(courseEvents);
//            }
//
//            FileOutputStream fout = new FileOutputStream("mycalendar2.ics");
//
//            CalendarOutputter outputter = new CalendarOutputter();
//            outputter.setValidating(false);
//            outputter.output(myCal, fout);
//        }

        /*
         * Ohjelman luoman kalenteritiedoston uudelleen parseaminen 
         */
        if (test2) {
            MyCalendarParser myParser = new MyCalendarParser(myCal);
            List<Course> courses = myParser.getCourses();
            kurssitForGui = courses;
            calendarTestGui = myCal;

            System.out.println(courses.size());
            for (Course course : courses) {
                System.out.println(course);
                System.out.println("sum of dur: " + EventUtility.getSumOfDurations(course.getAllEvents()));

                System.out.println();
                List<Event> events = course.getAllEvents();
                for (Event event : events) {
                    System.out.println(event.getEventName());
                    System.out.println("kesto: " + EventUtility.getDuration(event));
                }
            }

        }

//        JFrame frame = new JFrame("List Model Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setContentPane(new CourseListPanel(kurssitForGui));
//        frame.setContentPane(new EventListPanel(kurssitForGui.get(1).getAllEvents()));
//        frame.setSize(260, 200);
//        frame.setVisible(true);
        //date-testing
        java.util.Calendar today = java.util.Calendar.getInstance();
        today.set(java.util.Calendar.HOUR_OF_DAY, 0);
        today.clear(java.util.Calendar.MINUTE);
        today.clear(java.util.Calendar.SECOND);
        DateTime dt = new DateTime("19980118T230000");
        System.out.println(dt);

//        List<Course> kurssit = gui.testaustaVartenKurssitUlosGuista();
//        Event satunnainen = kurssit.get(0).getAllEvents().get(0);
//        System.out.println(satunnainen);
//        satunnainen.getProperties().remove(Property.STATUS);
//        satunnainen.getProperties().remove(Property.RELATED_TO);
//        satunnainen.getProperty(Property.STATUS).setValue("s");
//        System.out.println(satunnainen);
//        java.util.Calendar calendar = java.util.Calendar.getInstance();
//        calendar.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
//        calendar.set(java.util.Calendar.DAY_OF_MONTH, 25);
//        DateTime day = new DateTime(calendar.getTime());
//        Exam testi = new Exam(day, day, "summary");
//        testi.setStatusCancelled();
//        testi.setStatusConfirmed();
//        System.out.println(testi);
//        
//        Event joku = kurssitForGui.get(0).getAllEvents().get(0);
//        Lecture joku2 = (Lecture) joku;
//        joku2.setStatusCancelled();
//        VEvent uuspa = new VEvent(day, "summary");
//        PropertyFactoryImpl pf = PropertyFactoryImpl.getInstance();
//        uuspa.getProperties().add(pf.createProperty(Property.STATUS));
//        uuspa.getProperties().getProperty(Property.STATUS).setValue("VE");
//        
////        uuspa.getProperties().add();
//        System.out.println(uuspa);
////        joku.setStatusCancelled();
//        System.out.println(joku);
//        satunnainen.getProperty(Property.STATUS).setValue("VEVENT_CANCELLED");
//        MyCalendarParser myParser = new MyCalendarParser(myCal);
//        List<Course> courses = myParser.getCourses();
//        kurssitForGui = courses;
//        CourseListModel clm = new CourseListModel(kurssitForGui);
//        EventListModel elm = new EventListModel(clm);
//        JList testJlist = new JList(clm);
//        testJlist.addListSelectionListener(elm);
//        clm.addListDataListener(elm);
//        Course testOutput = (Course) clm.getElementAt(0);
////        System.out.println(testOutput);
////        System.out.println(clm.getSize());
//        clm.addNewCourse(testOutput);
////        System.out.println(clm.getSize());
//        // uuden lisäämnen ei aiheuta toimenpiteitä nyt
//        
//        //selection
//        testJlist.setSelectedIndex(5);
//        
//        Event e = (Event) clm.getElementAt(0);
//        System.out.println(e.equals(clm.getElementAt(0)));
//        System.out.println(e);
//        elm.removeEvent(e);
        





        List<Course> a = gui.testaustaVartenKurssitUlosGuista();
        List<Event> evs = new ArrayList<>();
        for (Course course : a) {
            evs.addAll(course.getAllEvents());            
        }
        EventListAnalysis ela = new EventListAnalysis(evs);
//        System.out.println(ela.getTotalDuration());
        System.out.println(ela.getStatusTotalDuration(StatusType.TENTATIVE));
        System.out.println(ela.getStatusTotalDuration(StatusType.CONFIRMED));
        System.out.println(ela.getEventTypeTotalDuration(EventType.EXERCISE));
        System.out.println(ela.getEventTypeTotalDuration(EventType.LECTURE));
//        System.out.println(ela.getTimespan());


    }
    


}
