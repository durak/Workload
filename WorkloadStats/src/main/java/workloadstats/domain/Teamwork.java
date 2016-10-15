package workloadstats.domain;

import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Teamwork type calendar event
 *
 * @author Ilkka
 */
public class Teamwork extends Event {

    public Teamwork(VEvent ve) {
        super(ve);
    }

    public Teamwork(PropertyList pl) {
        super(pl);
    }

}
