package workloadstats.domain;

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
    
}
