package workloadstats.domain;

import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Personal type calendar event
 *
 * @author Ilkka
 */
public class Personal extends Event {

    public Personal(VEvent ve) {
        super(ve);
    }

    public Personal(PropertyList pl) {
        super(pl);
    }
}
