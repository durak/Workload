package workloadstats.domain;

import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Exam type calendar event
 *
 * @author Ilkka
 */
public class Exam extends Event {

    public Exam(VEvent ve) {
        super(ve);
    }

    public Exam(PropertyList pl) {
        super(pl);
    }

}
