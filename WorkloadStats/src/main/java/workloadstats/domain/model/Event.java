package workloadstats.domain.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Summary;

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

    public Event(VEvent ve) {
        super(ve.getProperties());
    }

    public Event(PropertyList pl) {
        super(pl);
    }

    public void parentAnotherEvent(Event child) {
        if (!child.equals(this)) {
            child.addPropertyToVEvent(Property.RELATED_TO, this.getUid().getValue());
        }
    }

    public void childToAnotherEvent(Event parent) {
        if (!parent.equals(this)) {
            this.addPropertyToVEvent(Property.RELATED_TO, parent.getUid().getValue());
        }
    }

    public void addPropertyToVEvent(String property, String value) {
        PropertyFactoryImpl pf = PropertyFactoryImpl.getInstance();
        try {
            if (this.getProperty(property) == null) {
                this.getProperties().add(pf.createProperty(property));
            }
            this.getProperty(property).setValue(value);
        } catch (IOException ex) {

        } catch (URISyntaxException ex) {

        } catch (ParseException ex) {

        }
    }

    public void setStatusConfirmed() throws IOException, URISyntaxException, ParseException {
        this.status = "CONFIRMED";
        Property status = this.getProperties().getProperty("STATUS");
        status.setValue("CONFIRMED");
    }

    public String getEventName() {
        return this.getSummary().getValue();
    }

}
