package workloadstats.utils;

import java.util.Comparator;
import workloadstats.domain.Event;

/**
 *
 * @author Ilkka
 */
public class EventComparatorChronological implements Comparator<Event> {

    @Override
    public int compare(Event t, Event t1) {
        return t.getStartDate().getDate().compareTo(t1.getStartDate().getDate());
    }
    
}
