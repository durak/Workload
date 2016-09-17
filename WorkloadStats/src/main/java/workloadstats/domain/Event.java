package workloadstats.domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;

/**
 *
 * @author Ilkka
 */
public abstract class Event extends VEvent {

    private String courseId;
    private String status;
    private String relatedTo;

    public Event(DateTime start, DateTime end) {
        super(start, end, "testinimi");
        this.status = "TENTATIVE";
    }

    public Event(DateTime start, DateTime end, String courseId) {
        super(start, end, "testinimi");
        this.courseId = courseId;
        super.getProperties().add(new Categories(courseId));

    }

    public void setStatusConfirmed() throws IOException, URISyntaxException, ParseException {
        this.status = "CONFIRMED";
        Property status = this.getProperties().getProperty("STATUS");
        status.setValue("CONFIRMED");
    }

}
