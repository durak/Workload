package workloadstats.domain;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

/**
 *
 * @author Ilkka
 */
public class Teamwork extends Event {
    
    public Teamwork(DateTime start, DateTime end, String summary) {
        super(start, end, summary);
    }
    
}