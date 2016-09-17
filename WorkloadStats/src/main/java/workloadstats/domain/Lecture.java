package workloadstats.domain;

import java.util.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

public class Lecture extends VEvent {
    
    private String name;
    private String summary;
    private String uid;
    private Date start;
    private Date end;

    public Lecture(DateTime start, DateTime end, String courseId) {
//        super(start, end, courseId);
        super(start, end, courseId);
        
    }
    
    public void test() {
        
    }
    
    

    
}


