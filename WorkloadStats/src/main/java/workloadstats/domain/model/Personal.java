package workloadstats.domain.model;

import workloadstats.domain.model.Event;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

/**
 *
 * @author Ilkka
 */
public class Personal extends Event {
    
    public Personal(DateTime start, DateTime end, String summary) {
        super(start, end, summary);
    }
    
    public Personal(VEvent ve) {
        super(ve);
    }
    
}
