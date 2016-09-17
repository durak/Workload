package workloadstats.utils;

import java.util.List;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

/**
 *
 * @author Ilkka
 */
public class CalendarBuilderImpl extends CalendarBuilder {

    public CalendarBuilderImpl() {

    }

    public Calendar newCalendar() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//I.T.K.//WorkLoadStats1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        
        return calendar;
    }
    
    public void addVEvents(Calendar calendar, List<VEvent> vevents) {
        calendar.getComponents().addAll(vevents);        
    }

}
