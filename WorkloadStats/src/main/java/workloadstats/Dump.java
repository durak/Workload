/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;
import workloadstats.domain.Lecture;
import workloadstats.utils.CalendarParser;
import workloadstats.utils.HyCalendarFormatter;
import workloadstats.utils.VEventManager;

/**
 *
 * @author Ilkka
 */
public class Dump {

    public Dump() {

    }

    public void jotain(Calendar calendarr) throws IOException, URISyntaxException, ParseException, ValidationException {
        Calendar calendar = calendarr;
        
        /*
        Filtering events
         */
        java.util.Calendar today = java.util.Calendar.getInstance();
        today.set(java.util.Calendar.HOUR_OF_DAY, 0);
        today.clear(java.util.Calendar.MINUTE);
        today.clear(java.util.Calendar.SECOND);

        java.util.Calendar eka = java.util.Calendar.getInstance();
        eka.set(java.util.Calendar.HOUR_OF_DAY, 0);
        eka.clear(java.util.Calendar.MINUTE);
        eka.clear(java.util.Calendar.SECOND);

        java.util.Calendar toka = java.util.Calendar.getInstance();
        toka.set(java.util.Calendar.HOUR_OF_DAY, 1);
        toka.clear(java.util.Calendar.MINUTE);
        toka.clear(java.util.Calendar.SECOND);

        // create a period starting now with a duration of one (1) day..
        Period period = new Period(new DateTime(today.getTime()), new Dur(10, 0, 0, 0));
        Filter filter = new Filter(new PeriodRule(period));

        for (Iterator i = filter.filter(calendar.getComponents(Component.VEVENT)).iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            VEvent ve = (VEvent) component;
//            System.out.println("  test  " + ve.getLocation());
            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
//                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }

        /*
        New VEvent
         */
        DateTime start = new DateTime(eka.getTime());
        DateTime end = new DateTime(toka.getTime());
        VEvent uusi = new VEvent(start, end, "testinimi");

        System.out.println("AAA AAA");

        Property status = new Status("TENTATIVE");
        status.setValue("ss");

//        System.out.println(testEventti.getClass());
//        System.out.println(kurssi.getExams());
//        System.out.println(kurssi.getLectures());
//        System.out.println("sss");
//        System.out.println(kurssi.getLectures());
        UidGenerator ug = new UidGenerator("uidGen");
        Uid uid = ug.generateUid();

        uusi.getProperties().add(uid);
        Categories cat = new Categories("Kurssi");
        uusi.getProperties().add(cat);

        uid = ug.generateUid();
        uid = uusi.getUid();
//        testEventti.getProperties().add(uid);
//        testEventti.getProperties().add(cat);
//        System.out.println(testEventti.getName());
//        System.out.println(testEventti.getClass());
//        System.out.println(testEventti.getDateStamp());
//        System.out.println(testEventti.getProperties());

        calendar.getComponents().add(uusi);

//        calendar.getComponents().add(testEventti);
        for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();

//            System.out.println("Component [" + component.getName() + "]");
            StringBuilder sb = new StringBuilder();
//            sb.append(component.getName());
            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
//                System.out.println(sb.toString());
                Property property = (Property) j.next();

//                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
//                if (property.getName().equals("LOCATION")) {
//                    property.setValue("LIPASTO");
//                }
                sb.append(property.getName());
                sb.append(",");

            }
//            System.out.println(sb.toString());
        }

        calendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        FileOutputStream fout = new FileOutputStream("mycalendar.ics");

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.setValidating(false);
        outputter.output(calendar, fout);

        System.out.println("iterointi");
        HyCalendarFormatter hyF = new HyCalendarFormatter(calendar);
        hyF.UniqueVevents(calendar);

        CalendarParser hyParser = new CalendarParser(calendar);
        VEventManager veventManager = new VEventManager();

        Map<Summary, List<VEvent>> eventitPerSummary = hyParser.vEventsPerSummary();
        for (Summary summary : eventitPerSummary.keySet()) {
//            System.out.println(summary);
            List<VEvent> eventit = eventitPerSummary.get(summary);
            veventManager.relateVEventsToParent(eventit, uusi);

            for (VEvent vEvent : eventit) {
//                System.out.println("   " + vEvent.getStartDate());
//                System.out.println("   " + vEvent.getProperties().get(5));
//                System.out.println("   " + vEvent.getProperty("RELATED-TO"));
//                System.out.println(vEvent);
            }

            veventManager.deletePropertyFromVEvents(eventit, "RELATED-TO");
            for (VEvent vEvent : eventit) {

//                System.out.println("   " + vEvent.getProperty("RELATED-TO"));
//
            }

        }

        VEvent v = new Lecture(start, end, "s");
        System.out.println(v.getClass());
        veventManager.addPropertyToVEvent(v, Property.CATEGORIES, "test");
        veventManager.addPropertyToVEvent(v, Property.CATEGORIES, "test2");
        System.out.println(v.getProperty(Property.CATEGORIES).isCalendarProperty());
        System.out.println(v.getProperty(Property.CATEGORIES).getValue());
        System.out.println(v.getProperties());
    }
}
