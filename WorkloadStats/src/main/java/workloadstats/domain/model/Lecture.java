package workloadstats.domain.model;

import workloadstats.domain.model.Event;
import java.util.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Lecture type calendar event
 *
 * @author Ilkka
 */
public class Lecture extends Event {

    public Lecture(DateTime start, DateTime end, String summary) {
        super(start, end, summary);
    }

    public Lecture(VEvent ve) {
        super(ve);
    }

    public Lecture(PropertyList props) {
        super(props);
    }

}
