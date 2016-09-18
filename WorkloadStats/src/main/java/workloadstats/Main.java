/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import net.fortuna.ical4j.model.ValidationException;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.calendar.hycalendar.HyCalendarControl;
import workloadstats.calendar.mycalendar.MyCalendarParser;
import workloadstats.utils.CalendarBuilderImpl;

/**
 *
 * @author Ilkka
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParserException, URISyntaxException, ParseException, ValidationException {

        Scanner lukija = new Scanner(System.in);
        Boolean test = false;
        Boolean test2 = true;

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
        if (test2) {
            File calendarFile = new File("mycalendar2.ics");
            FileInputStream my = new FileInputStream(calendarFile);
            CalendarBuilderImpl builder = new CalendarBuilderImpl();
            Calendar myCal = builder.build(my);
            MyCalendarParser myParser = new MyCalendarParser(myCal);
            List<Course> courses = myParser.getCourses();
            System.out.println(courses.size());
            for (Course course : courses) {
                System.out.println(course);
                List<Event> events = course.getAllEvents();
                for (Event event : events) {
                    System.out.println(event);

                }
            }

        }

    }
;

//        System.out.print(kurssit.get(0).getProperty(Property.CATEGORIES).getParameters().size());
//        System.out.println(kurssit);
//        for (Course course : kurssit) {
//            System.out.println("Kurssin tiedot: ");
//            System.out.println(course);
//            System.out.println("Kurssin luennot: ");
//            for (Event lecture : course.getLectures()) {
//                System.out.println("");
//                System.out.println(lecture);
//            }
//            System.out.println("Kurssin harkat: ");
//            for (Event exercise : course.getExercises()) {
//                System.out.println("");
//                System.out.println(exercise);
//            }
//        }
//        for (Iterator i = hyCal2.getComponents(Component.VEVENT).iterator(); i.hasNext();) {
//            Component component = (Component) i.next();
//            VEvent ve = (VEvent) component;
//            String summary = ve.getSummary().getValue();
//            System.out.println("summary jota haetaan on: " + summary);
//            for (Course course : kurssit) {
//                PropertyList pl = course.getProperties().getProperties(Property.CATEGORIES);
//                for (Object object : pl) {
//                    System.out.println(object.toString());
//                }
////                    System.out.println("Löytyi match!");
////                    System.out.println("Vanhalla summarrylla " + summary + " löytyi eventti jonka uusi summary on " + course.getSummary().getValue());
//                
//            }
//
//        }
//        VEvent eka = (VEvent) hyCal.getComponents(Component.VEVENT).get(0);
//        System.out.println(eka);
//        Lecture test = new Lecture(eka);
//        test.getProperties().add(new DtStamp());
//        System.out.println(test);
//        Date alku = eka.getStartDate().getDate();
//        Date loppu = eka.getEndDate().getDate();
//
//        System.out.println("alku: " + alku);
//        System.out.println("loppu " + loppu);
//
//        System.out.println("alku: " + alku);
//        System.out.println("loppu " + loppu);
//        HyCalendarParse hyParse = new HyCalendarParse(hyCal);
//        List<Course> courses = hyParse.parseCalendar();
//        for (Course course : courses) {
//            if (course.getLectures() != null) {
//                System.out.println("Kurssi: " + course);
//                for (Event lecture : course.getLectures()) {
//                    System.out.println("   " + lecture);
//                }
//            }
//        }
//        CalendarParserEvent hyParser = new CalendarParserEvent(hyCal);
//        Set<Summary> hyUniikit = hyParser.uniqueEventSummaries();
//        Map<Summary, List<Event>> eventsPerSummary = hyParser.eventsPerSummary();
//        
//        
//        
//        for (Iterator i = hyUniikit.iterator(); i.hasNext();) {
//            Summary sm = (Summary) i.next();
//            System.out.println(sm.getValue());
//            System.out.print("Onko tämä kurssi vai jokin muu tapahtuma? (vastaa kurssi tai muu): ");
//            if (lukija.nextLine().equals("kurssi")) {
//
//            }
//        }
//
//
//
//testataan categories toimintaa
//        VEvent testi = hyParser.allEvents().get(0);
//        testi.getProperties().add(new DtStamp());
//        testi.getProperties().add(new Categories("a"));
//        testi.getProperties().add(new Categories("b"));
//        PropertyList propTest = testi.getProperties().getProperties(Property.CATEGORIES);
//        System.out.println(propTest);
//        testi.validate();
//        System.out.println(testi);
//
//        int hyUniikitKokoEnsin = hyUniikit.size();
//        int eventsPerSummaryKokoEnsin = eventsPerSummary.entrySet().size();
//
//        List<Course> courses = new ArrayList<>();
//        for (Iterator i = hyUniikit.iterator(); i.hasNext();) {
//            Summary sm = (Summary) i.next();
//            Summary copy = (Summary) sm.copy();
//
//            System.out.println(sm.getValue());
//            System.out.print("Onko tämä kurssi vai jokin muu tapahtuma? (vastaa kurssi tai muu): ");
//
//            if (lukija.nextLine().equals("kurssi")) {
//                System.out.print("Anna kurssille nimi: ");
//                String courseName = lukija.nextLine();
//
//                List<Event> lectures = eventsPerSummary.get(sm);
//
//                VEvent firstLecture = lectures.get(0);
//
//                PropertyList props = new PropertyList();
//                DtStart start = firstLecture.getStartDate();
//                DtEnd end = firstLecture.getEndDate();
//                Summary summary = new Summary(courseName);
//                Uid kurssiUid = ug.generateUid();
//                Categories cat1 = new Categories(firstLecture.getSummary().getValue());
//                Categories cat2 = new Categories("COURSE");
//                Status tentative = VEVENT_TENTATIVE;
////                ParameterList pl = new ParameterList();
////                ParameterFactoryImpl pf = ParameterFactoryImpl.getInstance();
////                pl.add(pf.createParameter(Parameter.VALUE, "value"));
////                Categories cat2 = new Categories(pl, "cat");
//
//                props.add(start);
//                props.add(end);
//                props.add(summary);
//                props.add(kurssiUid);
//                props.add(tentative);
//                props.add(cat1);
//                props.add(cat2);
//
//                Course uusiKurssi = new Course(props);
//
//                //ListIterator luokkamuutosta varten
//                ListIterator<Event> listIt = lectures.listIterator();
//                while (listIt.hasNext()) {
//                    Event conversion = new Lecture(listIt.next());
//                    conversion.childToAnotherEvent(uusiKurssi);
//                    System.out.println("conversion: " + conversion.getSummary());
//                    System.out.println("sm: " + sm);
////                    conversion.getSummary().setValue(courseName + " LUENTO");
//                    conversion.addPropertyToVEvent(Property.SUMMARY, courseName);
//
//                    System.out.println("conversion: " + conversion.getSummary());
//                    System.out.println("sm: " + sm);
//                    System.out.println("copy: " + copy);
//                    conversion.getProperties().add(new Categories("LECTURE"));
//                    conversion.getProperties().add(VEVENT_TENTATIVE);
//                    listIt.set(conversion);
////                    listIt.set(new Lecture(current));
//                }
//
//                uusiKurssi.addEventList((List<Event>) lectures);
//                courses.add(uusiKurssi);
////                System.out.println(uusiKurssi);
////                System.out.println(lectures);
//
//                eventsPerSummary.remove(sm);
////                i.remove();
//                hyUniikit.remove(sm);
//
////                Categories cat2 = new Categories(aList, courseName)
//            }
//        }
// Debuggaustulosteita
//
//        System.out.println("hyUniikit koko aluksi: " + hyUniikitKokoEnsin);
//        System.out.println("hyUniikit koko nyt: " + hyUniikit.size());
//        System.out.println("eventsPerSummary entryset koko aluksi: " + eventsPerSummaryKokoEnsin);
//        System.out.println("eventsPerSummary entryset koko nyt: " + eventsPerSummary.entrySet().size());
//        System.out.println("kurssilistan koko lopuksi: " + courses.size());
//        for (Iterator i = eventsPerSummary.entrySet().iterator(); i.hasNext();) {
//            Summary sm =  (Summary) i.next();
//            System.out.println(sm.getValue());
//            System.out.print("Onko tämä kurssi vai jokin muu tapahtuma? (vastaa kurssi tai muu): ");
//            if (lukija.nextLine().equals("kurssi")) {
//                System.out.print("Anna kurssille nimi: ");
//                String courseName = lukija.nextLine();
//
//                List<Event> lectures = eventsPerSummary.get(sm);
//                VEvent firstLecture = lectures.get(0);
//
//                PropertyList props = new PropertyList();
//                Uid kurssiUid = ug.generateUid();
//                DtStart s = firstLecture.getStartDate();
//                DtEnd e = firstLecture.getEndDate();
//                Categories cat = new Categories(firstLecture.getSummary().getValue());
//                ParameterList pl = new ParameterList();
//                ParameterFactoryImpl pf = ParameterFactoryImpl.getInstance();
//                pl.add(pf.createParameter("name", "value"));
//                Categories cat2 = new Categories(pl, "cat");
//
////                Categories cat2 = new Categories(aList, courseName)
//                
//                
//            }
//        }
//        Date alku = eka.getStartDate().getDate();
//        Date loppu = eka.getEndDate().getDate();
//
//        System.out.println("alku: " + alku);
//        System.out.println("loppu " + loppu);
//        for (Iterator i = hyCal.getComponents(Component.VEVENT).iterator(); i.hasNext();) {
//            Component component = (Component) i.next();
//            VEvent ve = (VEvent) component;
//            if (ve.getStartDate().getDate().before(alku)) {
//                alku = ve.getStartDate().getDate();
//            }
//
//            if (ve.getEndDate().getDate().after(loppu)) {
//                loppu = ve.getEndDate().getDate();
//            }
//        }
//
//        System.out.println("alku: " + alku);
//        System.out.println("loppu " + loppu);
//        HyCalendarParse hyParse = new HyCalendarParse(hyCal);
//        List<Course> courses = hyParse.parseCalendar();
//        for (Course course : courses) {
//            if (course.getLectures() != null) {
//                System.out.println("Kurssi: " + course);
//                for (Event lecture : course.getLectures()) {
//                    System.out.println("   " + lecture);
//                }
//            }
//        }
//        CalendarParserEvent hyParser = new CalendarParserEvent(hyCal);
//        Set<Summary> hyUniikit = hyParser.uniqueEventSummaries();
//        Map<Summary, List<Event>> eventsPerSummary = hyParser.eventsPerSummary();
//
//
//
//        for (Iterator i = hyUniikit.iterator(); i.hasNext();) {
//            Summary sm = (Summary) i.next();
//            System.out.println(sm.getValue());
//            System.out.print("Onko tämä kurssi vai jokin muu tapahtuma? (vastaa kurssi tai muu): ");
//            if (lukija.nextLine().equals("kurssi")) {
//
//            }
//        }
//
//
//
//testataan categories toimintaa
//        VEvent testi = hyParser.allEvents().get(0);
//        testi.getProperties().add(new DtStamp());
//        testi.getProperties().add(new Categories("a"));
//        testi.getProperties().add(new Categories("b"));
//        PropertyList propTest = testi.getProperties().getProperties(Property.CATEGORIES);
//        System.out.println(propTest);
//        testi.validate();
//        System.out.println(testi);
//
//        int hyUniikitKokoEnsin = hyUniikit.size();
//        int eventsPerSummaryKokoEnsin = eventsPerSummary.entrySet().size();
//
//        List<Course> courses = new ArrayList<>();
//        for (Iterator i = hyUniikit.iterator(); i.hasNext();) {
//            Summary sm = (Summary) i.next();
//            Summary copy = (Summary) sm.copy();
//
//            System.out.println(sm.getValue());
//            System.out.print("Onko tämä kurssi vai jokin muu tapahtuma? (vastaa kurssi tai muu): ");
//
//            if (lukija.nextLine().equals("kurssi")) {
//                System.out.print("Anna kurssille nimi: ");
//                String courseName = lukija.nextLine();
//
//                List<Event> lectures = eventsPerSummary.get(sm);
//
//                VEvent firstLecture = lectures.get(0);
//
//                PropertyList props = new PropertyList();
//                DtStart start = firstLecture.getStartDate();
//                DtEnd end = firstLecture.getEndDate();
//                Summary summary = new Summary(courseName);
//                Uid kurssiUid = ug.generateUid();
//                Categories cat1 = new Categories(firstLecture.getSummary().getValue());
//                Categories cat2 = new Categories("COURSE");
//                Status tentative = VEVENT_TENTATIVE;
////                ParameterList pl = new ParameterList();
////                ParameterFactoryImpl pf = ParameterFactoryImpl.getInstance();
////                pl.add(pf.createParameter(Parameter.VALUE, "value"));
////                Categories cat2 = new Categories(pl, "cat");
//
//                props.add(start);
//                props.add(end);
//                props.add(summary);
//                props.add(kurssiUid);
//                props.add(tentative);
//                props.add(cat1);
//                props.add(cat2);
//
//                Course uusiKurssi = new Course(props);
//
//                //ListIterator luokkamuutosta varten
//                ListIterator<Event> listIt = lectures.listIterator();
//                while (listIt.hasNext()) {
//                    Event conversion = new Lecture(listIt.next());
//                    conversion.childToAnotherEvent(uusiKurssi);
//                    System.out.println("conversion: " + conversion.getSummary());
//                    System.out.println("sm: " + sm);
////                    conversion.getSummary().setValue(courseName + " LUENTO");
//                    conversion.addPropertyToVEvent(Property.SUMMARY, courseName);
//
//                    System.out.println("conversion: " + conversion.getSummary());
//                    System.out.println("sm: " + sm);
//                    System.out.println("copy: " + copy);
//                    conversion.getProperties().add(new Categories("LECTURE"));
//                    conversion.getProperties().add(VEVENT_TENTATIVE);
//                    listIt.set(conversion);
////                    listIt.set(new Lecture(current));
//                }
//
//                uusiKurssi.addEventList((List<Event>) lectures);
//                courses.add(uusiKurssi);
////                System.out.println(uusiKurssi);
////                System.out.println(lectures);
//
//                eventsPerSummary.remove(sm);
////                i.remove();
//                hyUniikit.remove(sm);
//
////                Categories cat2 = new Categories(aList, courseName)
//            }
//        }
// Debuggaustulosteita
//
//        System.out.println("hyUniikit koko aluksi: " + hyUniikitKokoEnsin);
//        System.out.println("hyUniikit koko nyt: " + hyUniikit.size());
//        System.out.println("eventsPerSummary entryset koko aluksi: " + eventsPerSummaryKokoEnsin);
//        System.out.println("eventsPerSummary entryset koko nyt: " + eventsPerSummary.entrySet().size());
//        System.out.println("kurssilistan koko lopuksi: " + courses.size());
//        for (Iterator i = eventsPerSummary.entrySet().iterator(); i.hasNext();) {
//            Summary sm =  (Summary) i.next();
//            System.out.println(sm.getValue());
//            System.out.print("Onko tämä kurssi vai jokin muu tapahtuma? (vastaa kurssi tai muu): ");
//            if (lukija.nextLine().equals("kurssi")) {
//                System.out.print("Anna kurssille nimi: ");
//                String courseName = lukija.nextLine();
//
//                List<Event> lectures = eventsPerSummary.get(sm);
//                VEvent firstLecture = lectures.get(0);
//
//                PropertyList props = new PropertyList();
//                Uid kurssiUid = ug.generateUid();
//                DtStart s = firstLecture.getStartDate();
//                DtEnd e = firstLecture.getEndDate();
//                Categories cat = new Categories(firstLecture.getSummary().getValue());
//                ParameterList pl = new ParameterList();
//                ParameterFactoryImpl pf = ParameterFactoryImpl.getInstance();
//                pl.add(pf.createParameter("name", "value"));
//                Categories cat2 = new Categories(pl, "cat");
//
////                Categories cat2 = new Categories(aList, courseName)
//
//
//            }
//        }
}
