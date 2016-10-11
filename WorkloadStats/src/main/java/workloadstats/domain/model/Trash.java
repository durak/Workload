package workloadstats.domain.model;

import net.fortuna.ical4j.model.PropertyList;
import workloadstats.domain.model.Event;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Calendar VEvent identified as TRASH and not belonging to our data model.
 * 
 * @author Ilkka
 */
public class Trash extends Event {
    
    public Trash(VEvent ve) {
        super(ve);
    }
    
    public Trash(PropertyList pl) {
        super(pl);
    }
    
}
