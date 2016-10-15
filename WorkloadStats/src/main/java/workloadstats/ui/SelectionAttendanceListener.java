package workloadstats.ui;

import workloadstats.utils.Ac;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import workloadstats.domain.Event;

/**
 * Change selected event's attendance status
 * @author Ilkka
 */
public class SelectionAttendanceListener implements ActionListener {

    private Event event;
    
    public SelectionAttendanceListener() {        
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
