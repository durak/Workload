/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.calendar.hycalendar;

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
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Uncategorized;

/**
 * TODO: Wrappaa koko kalenteri tähän yksinkertaisuuden vuoksi
 * eli vielä tarvitaan muiden komponenttien (timezonet yms) ronkkimista
 * @author Ilkka
 */
public class HyCalendarParser {

    private Calendar calendar;
    
    private final List<Event> allEvents;
    private final Set<String> uniqueEventSummaryStrings;
    private final Map<String, List<Event>> eventsPerSummaryString;

    public HyCalendarParser(Calendar calendar) {
        this.calendar = calendar;
        this.allEvents = allEvents();
        this.uniqueEventSummaryStrings = uniqueEventSummaryStrings();
        this.eventsPerSummaryString = eventsPerSummaryString();
        
    }
    
    
    public List<Event> getAllEvents() {
        return allEvents;
    }
    
    public Set<String> getUniqueSummaries() {
        return uniqueEventSummaryStrings;
    }
    
    public Map<String, List<Event>> getEventsPerSummaryString() {
        return eventsPerSummaryString;
    }
    
    public List<Event> getEventsOfASummary(String summary) {
        List<Event> events = eventsPerSummaryString.get(summary);
        return events;
    }
    
    
    

    //CalendarParserEventin tehtäviä
    /**
     * Find all VEvents in this calendar
     *
     * @return All VEvents from this calendar in a Event wrapper, classified as
     * Uncategorized
     */
    private List<Event> allEvents() {
        List<Event> allEvents = new ArrayList<>();
        for (Iterator i = calendar.getComponents(Component.VEVENT).iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            VEvent ve = (VEvent) component;
            Uncategorized un = new Uncategorized(ve);

            allEvents.add(un);
        }

        return allEvents;
    }

    /**
     * Find unique calendar summaries from VEvents in this calendar (meaning the
     * descriptions shown for the events)
     *
     * @return a Set of unique summaries in String form
     */
    private Set<String> uniqueEventSummaryStrings() {
        Set<String> uniqueEventSummaries = new HashSet<>();
        for (Event ve : this.allEvents) {
            String summary = ve.getSummary().getValue();
            uniqueEventSummaries.add(summary);
        }

        return uniqueEventSummaries;
    }

    /**
     * Produce a Map of found unique summaryStrings as keys, and Lists of the
     * Events with that summary as values.
     *
     * @return key= SummaryString : value= List of Events with that
     * summaryString
     */
    private Map<String, List<Event>> eventsPerSummaryString() {
        Map<String, List<Event>> vps = new HashMap<>();
        for (String uniqueEventSummaryString : this.uniqueEventSummaryStrings) {
            vps.put(uniqueEventSummaryString, new ArrayList<>());
            for (Event ve : this.allEvents) {
                if (ve.getSummary().getValue().equals(uniqueEventSummaryString)) {
                    vps.get(uniqueEventSummaryString).add(ve);
                }
            }
        }

        return vps;
    }

    
    /*
    *
    *
    *
    *
    * SUMMARY (as opposed to String) versions below
    *
    *
    *
    *
    
    */
    
    
    
    
    /**
     * Find unique calendar summaries from VEvents in this calendar (meaning the
     * descriptions shown for the events)
     *
     * @return a Set of unique summaries
     */
    public Set<Summary> uniqueEventSummaries() {
        Set<Summary> uniqueCourseSummaries = new HashSet<>();
        for (Event ve : allEvents()) {
            Summary s = ve.getSummary();
            uniqueCourseSummaries.add(s);
        }

        return uniqueCourseSummaries;
    }

    /**
     * Produce a Map of found unique summaries as keys, and Lists of the Events
     * with that summary as values.
     *
     * @return key= Summary : value= List of Events with that summary
     */
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

}
