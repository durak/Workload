package workloadstats.calendardata;

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
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Trash;

/**
 * Parser functions for imported University calendar data
 *
 * @author Ilkka
 */
public class CalendarImportParser {

    private Calendar calendar;

    private final List<Event> allEvents;
    private final Set<String> uniqueEventSummaryStrings;
    private final Map<String, List<Event>> eventsPerSummaryString;

    public CalendarImportParser(Calendar calendar) {
        this.calendar = calendar;
        this.allEvents = allEvents();
        this.uniqueEventSummaryStrings = uniqueEventSummaryStrings();
        this.eventsPerSummaryString = eventsPerSummaryString();

    }

    public Set<String> getUniqueSummaries() {
        return uniqueEventSummaryStrings;
    }

    public List<Event> getEventsOfASummary(String summary) {
        List<Event> events = eventsPerSummaryString.get(summary);
        return events;
    }

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
            Trash un = new Trash(ve);

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
     * @return key = SummaryString : value= List of Events with that
     * summaryString
     */
    private Map<String, List<Event>> eventsPerSummaryString() {
        Map<String, List<Event>> eps = new HashMap<>();
        for (String uniqueEventSummaryString : this.uniqueEventSummaryStrings) {
            eps.put(uniqueEventSummaryString, new ArrayList<>());
            for (Event ve : this.allEvents) {
                if (ve.getSummary().getValue().equals(uniqueEventSummaryString)) {
                    eps.get(uniqueEventSummaryString).add(ve);
                }
            }
        }

        return eps;
    }

}
