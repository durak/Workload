package workloadstats.domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Status;

/**
 * Abstract calendar event type
 *
 * @author Ilkka
 */
public abstract class Event extends VEvent {

    private String eventSummary;
    private String status;
    private String relatedTo;

    public Event(DateTime start, DateTime end, String summary) {
        super(start, end, summary);
        this.eventSummary = summary;
//        super.getProperties().add(new Categories(courseId));

    }

    public Event(VEvent ve) {
        super(ve.getProperties());
    }

    public Event(PropertyList pl) {
        super(pl);
    }

    /**
     * Set this event's UID as the value of child event's RELATED_TO property
     *
     * @param child
     */
    public void parentAnotherEvent(Event child) {
        if (!child.equals(this)) {
            child.addPropertyToVEvent(Property.RELATED_TO, this.getUid().getValue());
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

    /**
     * Set Status property to TENTATIVE
     */
    public void setStatusTentative() {
        this.status = "TENTATIVE";
        this.getProperties().remove(this.getStatus());
        Status s = new Status("TENTATIVE");
        this.getProperties().add(s);
    }

    /**
     * Set status property to CONFIRMED
     */
    public void setStatusConfirmed() {
        this.status = "CONFIRMED";
        this.getProperties().remove(this.getStatus());
        Status s = new Status("CONFIRMED");
        this.getProperties().add(s);
//        addPropertyToVEvent(Property.STATUS, "CONFIRMED");
    }

    public void setStatusCancelled() {
        this.status = "CANCELLED";
        this.getProperties().remove(this.getStatus());
        Status s = new Status("CANCELLED");
        this.getProperties().add(s);
//        addPropertyToVEvent(Property.STATUS, "CANCELLED");
    }

    /**
     * Get summary as String
     *
     * @return
     */
    public String getEventName() {
        return this.getSummary().getValue();
    }

    /**
     * Get event's status as a string
     *
     * @return String of status, possible values CONFIRMED, CANCELLED or
     * TENTATIVE
     */
    public String getEventStatus() {
        if (this.getProperty(Property.STATUS) == null) {
            setStatusTentative();
        }

        return this.getProperty(Property.STATUS).getValue();
    }

    /**
     * Get Start date as string, Helsinki timezone
     *
     * @return date in format yyyy.MM.dd
     */
    public String getStartDateString() {
        String date = toHelsinkiTime(this.getStartDate().getDate());
        date = date.substring(0, 8);
        date = date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8);

        return date;
    }

    /**
     * Get End date as string, Helsinki timezone
     *
     * @return date in format yyyy.MM.dd
     */
    public String getEndDateString() {
        String date = toHelsinkiTime(this.getEndDate().getDate());
        date = date.substring(0, 8);
        date = date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8);

        return date;
    }

    /**
     * Get Start time as string, Helsinki timezone
     *
     * @return time in format HH.mm
     */
    public String getStartTime() {
        Date date = this.getStartDate().getDate();
        String time = toHelsinkiTime(date);
        time = time.substring(9, 11) + "." + time.substring(11, 13);

        return time;
    }

    private String toHelsinkiTime(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
        return dateFormatter.format(date);
    }

}
