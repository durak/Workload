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

/**
 *
 * @author Ilkka
 */
public class CalendarParser {

    private Calendar calendar;

    public CalendarParser(Calendar calendar) {
        this.calendar = calendar;

    }

    public List<VEvent> allVEvents() {
        List<VEvent> allVEvents = new ArrayList<>();
        for (Iterator i = calendar.getComponents(Component.VEVENT).iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            VEvent ve = (VEvent) component;
            allVEvents.add(ve);
        }

        return allVEvents;
    }

    public Set<Summary> uniqueVEventSummaries() {
        Set<Summary> uniqueCourseSummaries = new HashSet<>();
        for (VEvent ve : allVEvents()) {
            Summary s = ve.getSummary();
            uniqueCourseSummaries.add(s);
        }

        return uniqueCourseSummaries;
    }

    public Map<Summary, List<VEvent>> vEventsPerSummary() {
        Map<Summary, List<VEvent>> vps = new HashMap<>();
        for (Summary uniqueVEventSummary : uniqueVEventSummaries()) {
            vps.put(uniqueVEventSummary, new ArrayList<>());
            for (VEvent ve : allVEvents()) {
                if (ve.getSummary().equals(uniqueVEventSummary)) {
                    vps.get(uniqueVEventSummary).add(ve);
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
