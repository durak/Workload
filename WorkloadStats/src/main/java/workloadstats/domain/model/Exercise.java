package workloadstats.domain.model;

import workloadstats.domain.model.Event;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

/**
 *
 * @author Ilkka
 */
public class Exercise extends Event {
    
    public Exercise(DateTime start, DateTime end, String summary) {
        super(start, end, summary);
    }
    
    public Exercise(VEvent ve) {
        super(ve);
    }
    
}
