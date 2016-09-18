package workloadstats.domain.model;

import workloadstats.domain.model.Event;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

/**
 *
 * @author Ilkka
 */
public class Exam extends Event {
    
    public Exam(DateTime start, DateTime end, String summary) {
        super(start, end, summary);        
    }
    
    public Exam(VEvent ve) {
        super(ve);
    }
    
}
