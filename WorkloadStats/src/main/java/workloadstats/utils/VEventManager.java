package workloadstats.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.RelatedTo;

/**
 *
 * @author Ilkka
 */
public class VEventManager {

    PropertyFactoryImpl pf = PropertyFactoryImpl.getInstance();

    public VEventManager() {

    }
    
    
    public void relateVEventToParent(VEvent child, VEvent parent) {
        if (!child.equals(parent)) {
            addPropertyToVEvent(child, Property.RELATED_TO, parent.getProperty(Property.UID).getValue());
        }
    }

    public void relateVEventsToParent(List<VEvent> childs, VEvent parent) throws IOException, URISyntaxException, ParseException {
        for (VEvent child : childs) {
            if (child.equals(parent)) {
                continue;
            }
            Property rt = child.getProperties().getProperty("RELATED-TO");
            if (rt != null) {
                rt.setValue(parent.getUid().getValue());
            } else {
                child.getProperties().add(new RelatedTo(parent.getUid().getValue()));
            }
        }
    }

    public List<VEvent> findVEventsWithParent(List<VEvent> vevents, VEvent parent) {
        List<VEvent> children = new ArrayList<>();
        for (VEvent ve : vevents) {
            String childRt = ve.getProperty(Property.RELATED_TO).getValue();
            String parentUid = parent.getUid().getValue();
            if (childRt != null && parentUid != null && childRt.equals(parentUid)) {
                children.add(ve);
            }
        }

        return children;
    }

    public void addPropertyToVEvent(VEvent ve, String property, String value) {
        try {
            if (ve.getProperty(property) == null) {
                ve.getProperties().add(pf.createProperty(property));
            }
            ve.getProperty(property).setValue(value);
        } catch (IOException ex) {
            Logger.getLogger(VEventManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(VEventManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VEventManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addPropertyToVEvents(List<VEvent> vevents, Property property) {
        String pName = property.getName();
        for (VEvent vevent : vevents) {
            if (vevent.getProperty(pName) == null) {
                vevent.getProperties().add(property);
            } else {

                try {
                    vevent.getProperty(pName).setValue(property.getValue());
                } catch (IOException ex) {
                    Logger.getLogger(VEventManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(VEventManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(VEventManager.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    
    public void deletePropertyFromVEvent(VEvent ve, String property) {
        ve.getProperties().remove(property);
    }

    public void deletePropertyFromVEvents(List<VEvent> vevents, String property) {

        for (VEvent vevent : vevents) {
            for (Iterator it = vevent.getProperties().iterator(); it.hasNext();) {
                Property p = (Property) it.next();
                if (p.getName().equals(property)) {
                    it.remove();
                }
            }
        }
    }
}
