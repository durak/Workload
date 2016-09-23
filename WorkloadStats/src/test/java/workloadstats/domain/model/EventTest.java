/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.domain.model;

import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
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
        VEvent ve1 = new VEvent(start, "testi1");
        VEvent ve2 = new VEvent(start, "testi2");
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
    public void eventinVoiAsettaaChildiksi() {       
        ev1.childToAnotherEvent(ev2);
        String ev1RelatedTo = ev1.getProperty(Property.RELATED_TO).getValue();
        
        String parentUid = ev2.getProperty(Property.UID).getValue();
        
        assertEquals(parentUid, ev1RelatedTo);
    }
    
    @Test
    public void eventinStatuksenMuutosConfirmedOnnistuu() {
        ev1.setStatusConfirmed();
        assertEquals("CONFIRMED", ev1.getProperty(Property.STATUS).getValue());
        
    }
    


}
