package workloadstats.domain.model;

import net.fortuna.ical4j.model.PropertyList;
import workloadstats.domain.model.Event;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Uncategorized calendar event
 * @author Ilkka
 */
public class Uncategorized extends Event {
    
    public Uncategorized(VEvent ve) {
        super(ve);
    }
    
    public Uncategorized(PropertyList pl) {
        super(pl);
    }
    
}
