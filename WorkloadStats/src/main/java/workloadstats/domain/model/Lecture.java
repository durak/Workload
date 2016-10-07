package workloadstats.domain.model;

import workloadstats.domain.model.Event;
import java.util.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;


/**
 * Lecture type calendar event
 * @author Ilkka
 */
public class Lecture extends Event {
    
//    private String name;
//    private String summary;
//    private String uid;
//    private Date start;
//    private Date end;

    public Lecture(DateTime start, DateTime end, String summary) {
//        super(start, end, courseId);
        super(start, end, summary);        
    }
    
    public Lecture(VEvent ve) {
        super(ve);        
    }
    
    public void test() {
        
    }
    
    

    
}


