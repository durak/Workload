package workloadstats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.ValidationException;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.calendardata.hycalendar.HyCalendarControl;
import workloadstats.calendardata.mycalendar.MyCalendarParser;
import workloadstats.calendardata.CalendarBuilderImpl;
import workloadstats.utils.EventUtilities;

/**
 *
 * @author Ilkka
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParserException, URISyntaxException, ParseException, ValidationException {

        Scanner lukija = new Scanner(System.in);
        Boolean test = true;
        Boolean test2 = false;

        /*
         * Yliopiston kalenterin parseaminen ja tallentaminen uudeksi kalenteritiedostoksi 
         */
        if (test) {
            File calendarFile = new File("fi.ics");
            File calendarFile2 = new File("my.ics");
            FileInputStream fin = new FileInputStream(calendarFile);
            FileInputStream my = new FileInputStream(calendarFile2);

            CalendarBuilderImpl builder = new CalendarBuilderImpl();

            Calendar hyCal = builder.build(fin);
            Calendar myCal = builder.build(my);

            HyCalendarControl hyControl = new HyCalendarControl(hyCal);
            hyControl.buildCoursesFromIdentifiedEvents();
            hyControl.buildExercisesFromIdentifiedEvents();
            List<Course> kurssit = hyControl.getCourses();

            for (Course course : kurssit) {
                List<Event> courseEvents = course.getAllEvents();
                myCal.getComponents().add(course);
                myCal.getComponents().addAll(courseEvents);
            }

            FileOutputStream fout = new FileOutputStream("mycalendar2.ics");

            CalendarOutputter outputter = new CalendarOutputter();
            outputter.setValidating(false);
            outputter.output(myCal, fout);
        }
        
        /*
         * Ohjelman luoman kalenteritiedoston uudelleen parseaminen 
         */
        if (test2) {
            EventUtilities eu = new EventUtilities();
            File calendarFile = new File("mycalendar2.ics");
            FileInputStream my = new FileInputStream(calendarFile);
            CalendarBuilderImpl builder = new CalendarBuilderImpl();
            Calendar myCal = builder.build(my);
            MyCalendarParser myParser = new MyCalendarParser(myCal);
            List<Course> courses = myParser.getCourses();

            System.out.println(courses.size());
            for (Course course : courses) {
                System.out.println(course);
                System.out.println("sum of dur: " + eu.getSumOfDurations(course.getAllEvents()));

                System.out.println();
                List<Event> events = course.getAllEvents();
                for (Event event : events) {
                    System.out.println(event.getEventName());
                    System.out.println("kesto: " + eu.getDuration(event));
                }
            }

        }

    }

}
