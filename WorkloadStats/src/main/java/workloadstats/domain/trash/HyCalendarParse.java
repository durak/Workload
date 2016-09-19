package workloadstats.domain.trash;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Status;
import static net.fortuna.ical4j.model.property.Status.VEVENT_TENTATIVE;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Exercise;
import workloadstats.domain.model.Lecture;
import workloadstats.ui.TextUi;
import workloadstats.domain.trash.CalendarParserEvent;

/**
 *
 * @author Ilkka
 */
public class HyCalendarParse {

    public enum Type {
        COURSE,
        LECTURE,
        EXERCISE,
        PERSONAL,
        TEAMWORK
    }

    Type type;

    private Calendar calendar;
    private CalendarParserEvent hyParser;
    private Set<Summary> hyUniikit;
    private Map<Summary, List<Event>> eventsPerSummary;

    private UidGenerator ug;
    private TextUi tui;
    private Scanner lukija;

    public HyCalendarParse(Calendar calendar) throws SocketException {
        this.ug = new UidGenerator("uidGen");
        this.tui = new TextUi();
        this.calendar = calendar;
        this.lukija = new Scanner(System.in);
        hyParser = new CalendarParserEvent(calendar);
        hyUniikit = hyParser.uniqueEventSummaries();
        eventsPerSummary = hyParser.eventsPerSummary();
    }

    public List<Course> parseCalendar() {
        firstParse();

//        List<Course> courses = parseCourses();
//        courses = parseLectures(courses);
//        courses = parseExercises(courses);
        return null;
    }

//    // Placeholder, kunnes keksin miten handlataan
//    private String kysyKayttajaltaOnkoTamaKurssi(Summary sm) {
//        return tui.mikaTamaOn(sm.getValue());
//    }
//
//    private Course kysyKayttajaltaMihinKurssiinTamaLiittyy(Summary sm, List<Course> courses) {
//        String vastaus = tui.mihinKurssiinTamaKuuluu(sm.getValue());
//        for (Course course : courses) {
//            if (course.getCourseName().equals(vastaus)) {
//                return course;
//            }
//        }
//
//        return courses.get(0);
//    }
//
//    private String kysyKayttajaltaHaluttuNimiKurssille() {
//        return tui.kysyKayttajaltaKurssilleNimi();
//    }
//
    private Course buildCourse(String oldSummary, String courseName, VEvent firstLecture) {

        PropertyList props = new PropertyList();
        props.add(firstLecture.getStartDate());                         //DTSTART
        props.add(firstLecture.getEndDate());
        props.add(new Summary(courseName));
        props.add(ug.generateUid());
        props.add(new Categories(oldSummary));
        props.add(new Categories("COURSE"));
        props.add(VEVENT_TENTATIVE);

        return new Course(props);
    }

    public void firstParse() {
        List<List<Event>> listojenLista = new ArrayList<>();

        for (Iterator<Summary> it = hyUniikit.iterator(); it.hasNext();) {
            Summary summary = it.next();

            System.out.println(summary.getValue());
            System.out.print("Mikä tapahtuma tämä on?: ");
            String tyyppi = lukija.nextLine();
            System.out.println("Anna parempi nimi: ");
            String nimi = lukija.nextLine();

            type = Type.valueOf(tyyppi);
            System.out.println("type:" + type);

            String oldSummary = summary.getValue();
            List<Event> events = eventsPerSummary.get(summary);

            switch (type) {
                case COURSE:

                    ListIterator<Event> listIt = events.listIterator();
                    while (listIt.hasNext()) {
                        Event conversion = conversion = new Lecture(listIt.next());

//                conversion.childToAnotherEvent(newCourse);
//                conversion.getSummary().setValue(courseName + " LUENTO");
                        conversion.getProperties().add(new Categories("LECTURE"));
                        conversion.getProperties().add(VEVENT_TENTATIVE);
                        listIt.set(conversion);
//                            }            
                    }

                case LECTURE:

            }

            ListIterator<Event> listIt = events.listIterator();
            while (listIt.hasNext()) {
                Event conversion = conversion = new Lecture(listIt.next());

//                conversion.childToAnotherEvent(newCourse);
//                conversion.getSummary().setValue(courseName + " LUENTO");
                conversion.getProperties().add(new Categories("LECTURE"));
                conversion.getProperties().add(VEVENT_TENTATIVE);
                listIt.set(conversion);
//                            }            
            }
        }
//
//    private List<Event> buildLectures(List<Event> lectures, Course parent, String courseName) {
//        ListIterator<Event> listIt = lectures.listIterator();
//        while (listIt.hasNext()) {
//            Event conversion = new Lecture(listIt.next());
//            conversion.childToAnotherEvent(parent);
//            conversion.getSummary().setValue(courseName + "");
//            conversion.getProperties().add(new Categories("LECTURE"));
//            conversion.getProperties().add(VEVENT_TENTATIVE);
//            listIt.set(conversion);
//        }
//
//        return lectures;
//    }
//
//    private List<Event> buildExercises(List<Event> exercises, Course parent, String courseName) {
//        ListIterator<Event> listIt = exercises.listIterator();
//        while (listIt.hasNext()) {
//            Event conversion = new Exercise(listIt.next());
//            conversion.childToAnotherEvent(parent);
//            conversion.getSummary().setValue(courseName + " HARKKA");
//            conversion.getProperties().add(new Categories("EXERCISE"));
//            conversion.getProperties().add(VEVENT_TENTATIVE);
//            listIt.set(conversion);
//        }
//        return exercises;
//    }

//    public List<Course> parseCourses() {
//        List<Course> courses = new ArrayList<>();
//
//        for (Iterator i = hyUniikit.iterator(); i.hasNext();) {
//            Summary oldSummary = (Summary) i.next();
//
//            // Summary pitää lähettää käyttäjälle nähtäväksi tässä
//            //saadaan vastaus ja saadaan kurssille nimi 
//            System.out.println(oldSummary.getValue());
//            System.out.println("anna kurssi");
//            if (lukija.nextLine().equals("kurssi")) {
//                System.out.print("Anna kurssille nimi: ");
//                String courseName = lukija.nextLine();
//
//                VEvent firstLecture = eventsPerSummary.get(oldSummary).get(0);
//                System.out.println(oldSummary);
////                PropertyList props = new PropertyList();
////                props.add(firstLecture.getStartDate());                         //DTSTART
////                props.add(firstLecture.getEndDate());
////                props.add(new Summary(courseName));
////                props.add(ug.generateUid());
////                props.add(new Categories(firstLecture.getSummary().getValue()));
////                props.add(new Categories("COURSE"));
////                props.add(VEVENT_TENTATIVE);
//
//                Course newCourse = buildCourse(oldSummary.getValue(), courseName, firstLecture);
//
////                newCourse.addEventList(buildLectures(lectures, newCourse, courseName));
////                ListIterator<Event> listIt = lectures.listIterator();
////                while (listIt.hasNext()) {
////                    Event conversion = new Lecture(listIt.next());
////                    conversion.childToAnotherEvent(newCourse);
////                    conversion.getSummary().setValue(courseName + " LUENTO");
////                    conversion.getProperties().add(new Categories("LECTURE"));
////                    conversion.getProperties().add(VEVENT_TENTATIVE);
////                    listIt.set(conversion);
////                }
//                System.out.println("sm ennen remove: " + oldSummary);
//
//                courses.add(newCourse);
//
//                eventsPerSummary.remove(oldSummary);
//                i.remove();
//                System.out.println("sm jälkeen remove: " + oldSummary);
//
//            }
//            System.out.println("hysize: " + this.hyUniikit.size());
//        }
//
//        return courses;
//    }
//    public List<Course> parseLectures(List<Course> courses) {
////        for (Course course : courses) {
////            List<
////            newCourse.addEventList(buildLectures(lectures, newCourse, courseName));
////            ListIterator<Event> listIt = lectures.listIterator();
////            while (listIt.hasNext()) {
////                Event conversion = new Lecture(listIt.next());
////                conversion.childToAnotherEvent(newCourse);
////                conversion.getSummary().setValue(courseName + " LUENTO");
////                conversion.getProperties().add(new Categories("LECTURE"));
////                conversion.getProperties().add(VEVENT_TENTATIVE);
////                listIt.set(conversion);
////            }
////        }
//
//        return null;
//    }
//    public List<Course> parseExercises(List<Course> courses) {
//        System.out.println(hyUniikit.size());
//        for (Iterator i = hyUniikit.iterator(); i.hasNext();) {
//            Summary sm = (Summary) i.next();
//
//            Course mihinNamaKuuluu = kysyKayttajaltaMihinKurssiinTamaLiittyy(sm, courses);
//            String courseName = mihinNamaKuuluu.getCourseName();
//            List<Event> exercises = eventsPerSummary.get(sm);
//
//            mihinNamaKuuluu.addEventList(buildExercises(exercises, mihinNamaKuuluu, courseName));
//
//            eventsPerSummary.remove(sm);
//            i.remove();
//            System.out.println("hysize: " + hyUniikit.size());
//        }
//
//        return courses;
//    }
//}
    }
}
