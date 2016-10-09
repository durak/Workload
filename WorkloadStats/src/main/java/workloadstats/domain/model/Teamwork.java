package workloadstats.domain.model;

import workloadstats.domain.model.Event;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Teamwork type calendar event
 * @author Ilkka
 */
public class Teamwork extends Event {
    
    public Teamwork(DateTime start, DateTime end, String summary) {
        super(start, end, summary);
    }
    
    public Teamwork(VEvent ve) {
        super(ve);
    }
    
    public Teamwork(PropertyList pl) {
        super(pl);
    }
    
}
