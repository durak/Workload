package workloadstats.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;
import workloadstats.domain.Event;
import workloadstats.domain.Uncategorized;

/**
 *
 * @author Ilkka
 */
public class CalendarParserEvent {

    private Calendar calendar;

    public CalendarParserEvent(Calendar calendar) {
        this.calendar = calendar;

    }
    
        public List<Event> allEvents() {
        List<Event> allEvents = new ArrayList<>();
        for (Iterator i = calendar.getComponents(Component.VEVENT).iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            VEvent ve = (VEvent) component;
            Uncategorized un = new Uncategorized(ve);

            
            allEvents.add(un);
        }

        return allEvents;
    }

    public Set<Summary> uniqueEventSummaries() {
        Set<Summary> uniqueCourseSummaries = new HashSet<>();
        for (Event ve : allEvents()) {
            Summary s = ve.getSummary();
            uniqueCourseSummaries.add(s);
        }

        return uniqueCourseSummaries;
    }

    public Map<Summary, List<Event>> eventsPerSummary() {
        Map<Summary, List<Event>> vps = new HashMap<>();
        for (Summary uniqueEventSummary : uniqueEventSummaries()) {
            vps.put(uniqueEventSummary, new ArrayList<>());
            for (Event ve : allEvents()) {
                if (ve.getSummary().equals(uniqueEventSummary)) {
                    vps.get(uniqueEventSummary).add(ve);
                }
            }
        }

        return vps;
    }
    
    

    private String cleanUp(String hyString) {
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
