package workloadstats.utils;

import java.util.Comparator;
import workloadstats.domain.model.Event;

/**
 *
 * @author Ilkka
 */
public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event t, Event t1) {
        return t.getStartDate().getDate().compareTo(t1.getStartDate().getDate());
    }
    
}
