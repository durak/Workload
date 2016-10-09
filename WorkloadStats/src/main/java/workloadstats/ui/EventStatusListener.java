package workloadstats.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import workloadstats.domain.model.Event;

/**
 * Change selected event's status
 * @author Ilkka
 */
public class EventStatusListener implements ActionListener {

    private Event event;
    
    public EventStatusListener() {        
    }
    
    public void setEvent(Event ev) {
        this.event = ev;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(Ac.TENTATIVE.name())) {
            event.setStatusTentative();
        }
        if (ae.getActionCommand().equals(Ac.CANCELLED.name())) {
            event.setStatusCancelled();
        }
        if (ae.getActionCommand().equals(Ac.CONFIRMED.name())) {
            event.setStatusConfirmed();
        }

    }

}
