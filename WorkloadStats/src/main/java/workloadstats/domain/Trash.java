package workloadstats.domain;

import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Calendar VEvent identified as TRASH and not belonging to our data model.
 *
 * @author Ilkka
 */
public class Trash extends Event {

    public Trash(VEvent ve) {
        super(ve);
    }

    public Trash(PropertyList pl) {
        super(pl);
    }

}
