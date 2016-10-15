package workloadstats.domain.model;

import workloadstats.domain.Personal;
import workloadstats.domain.Exam;
import workloadstats.domain.Event;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.UidGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ilkka
 */
public class EventTest {

    Event ev1;
    Event ev2;

    UidGenerator ug;

    public EventTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SocketException {
        this.ug = new UidGenerator("uidGen");
        Calendar c = Calendar.getInstance();
        Date start = new Date(new Date(c.getTime()));
        Date end = new Date(new Date(c.getTime()));
        VEvent ve1 = new VEvent(start, end, "testi1");
        VEvent ve2 = new VEvent(start, end, "testi2");
        ve1.getProperties().add(ug.generateUid());
        ve2.getProperties().add(ug.generateUid());

        ev1 = new Exam(ve1);
        ev2 = new Personal(ve2);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void eventinSummarykenttaPalautuuOikein() {
        assertEquals("testi1", ev1.getEventName());
        assertEquals("testi2", ev2.getEventName());

    }

    @Test
    public void eventinVoiAsettaaToisenParentiksi() {
        ev1.parentAnotherEvent(ev2);
        String ev2RelatedTo = ev2.getProperty(Property.RELATED_TO).getValue();

        String parentUid = ev1.getProperty(Property.UID).getValue();

        assertEquals(parentUid, ev2RelatedTo);

    }


    @Test
    public void eventinStatuksenMuutosConfirmedOnnistuuEkanKerran() {
        ev1.setStatusConfirmed();
        assertEquals("CONFIRMED", ev1.getProperty(Property.STATUS).getValue());
    }

    @Test
    public void eventinStatuksenMuutosConfirmedOnnistuuUseammanKerran() {
        ev1.setStatusTentative();
        ev1.setStatusConfirmed();
        ev1.setStatusConfirmed();
        assertEquals("CONFIRMED", ev1.getProperty(Property.STATUS).getValue());
    }

    @Test
    public void eventinStatuksenMuutosTentativeOnnistuuEkanKerran() {
        ev1.setStatusTentative();
        assertEquals("TENTATIVE", ev1.getProperty(Property.STATUS).getValue());
    }
    
    @Test
    public void eventinStatuksenMuutosTentativeOnnistuuUseammanKerran() {
        ev1.setStatusConfirmed();
        ev1.setStatusTentative();
        ev1.setStatusTentative();
        assertEquals("TENTATIVE", ev1.getProperty(Property.STATUS).getValue());
    }
    @Test
    public void eventinStatuksenMuutosCancelledOnnistuuEkanKerran() {
        ev1.setStatusCancelled();
        assertEquals("CANCELLED", ev1.getProperty(Property.STATUS).getValue());
    }
    
    @Test
    public void eventinStatuksenMuutosCancelledOnnistuuUseammanKerran() {
        ev1.setStatusConfirmed();
        ev1.setStatusCancelled();
        ev1.setStatusCancelled();
        assertEquals("CANCELLED", ev1.getProperty(Property.STATUS).getValue());
    }
    
    @Test
    public void eventinStatusPalautuuOikein() {
        assertEquals("TENTATIVE", ev1.getEventStatus());
        ev1.setStatusConfirmed();
        assertEquals("CONFIRMED", ev1.getEventStatus());        
    }
    
    @Test
    public void ajanPalautusStringinaToimii() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
        c.setTime(ev1.getStartDate().getDate());
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        int y = c.get(Calendar.YEAR);
        int mo = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        
        String hours = String.format("%02d", h);
        String minute = String.format("%02d", m);
        String year = String.format("%04d", y);
        String month = String.format("%02d", mo);
        String day = String.format("%02d", d);
        
        assertEquals(ev1.getStartDateString(), year + "." + month + "." + day);
        assertEquals(ev1.getEndDateString(),  year + "." + month + "." + day);
        assertEquals(ev1.getStartTime(), hours + "." + minute);
    }

}
