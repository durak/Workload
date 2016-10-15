package workloadstats.domain;

import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Lecture type calendar event
 *
 * @author Ilkka
 */
public class Lecture extends Event {

    public Lecture(VEvent ve) {
        super(ve);
    }

    public Lecture(PropertyList props) {
        super(props);
    }

}
