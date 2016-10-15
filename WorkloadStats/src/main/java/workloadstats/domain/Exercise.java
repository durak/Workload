package workloadstats.domain;

import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Exercise type calendar event
 *
 * @author Ilkka
 */
public class Exercise extends Event {

    public Exercise(VEvent ve) {
        super(ve);
    }

    public Exercise(PropertyList pl) {
        super(pl);
    }

}
