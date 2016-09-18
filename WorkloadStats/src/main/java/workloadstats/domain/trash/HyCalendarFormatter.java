package workloadstats.domain.trash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;

/**
 * Class for reformatting Helsinki University's calendar file
 *
 * @author Ilkka
 */
public class HyCalendarFormatter {

    private Calendar hyCalendar;

    private Map<Summary, String> summaries;

    public HyCalendarFormatter(Calendar calendar) {
        this.hyCalendar = calendar;

        this.summaries = new HashMap<>();
    }

    public void UniqueVevents(Calendar calendar) {

        Set<Summary> uniqueCourseSummaries = new HashSet<>();        

        for (Iterator i = hyCalendar.getComponents(Component.VEVENT).iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            VEvent ve = (VEvent) component;
            Summary summary = (Summary) component.getProperty("SUMMARY");

//            uniqueCourses.add(cleanUp(component.getProperty("SUMMARY").getValue()));
            uniqueCourseSummaries.add(summary);            
//            summaries.put(summary, cleanUp(summary.getValue()));
            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
//                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }

        for (Summary uniqueCourseSummary : uniqueCourseSummaries) {
            this.summaries.put(uniqueCourseSummary, cleanUp(uniqueCourseSummary.getValue()));            
        }
        
        
        for (Map.Entry<Summary, String> summary : this.summaries.entrySet()) {
            System.out.println(summary.getValue());
        }



    }
    
    

    public String cleanUp(String hyString) {
//        String clean = hyString.replaceAll("[^a-zA-Z0-9_-]", "");

        String clean = hyString;
        clean = clean.replaceAll("[^a-zA-Z0-9 ]", "");
        String[] split = clean.split("\\s+");
        clean = "";
        for (int i = 0; i < (split.length / 2); i++) {
            clean += split[i] + " ";
        }
        return clean;
    }
}
