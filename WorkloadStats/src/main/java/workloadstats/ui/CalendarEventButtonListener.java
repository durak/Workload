package workloadstats.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button listener for data model modification buttons
 * @author Ilkka
 */
public class CalendarEventButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(Ac.NEWCOURSE.name())) {
            
        }
        if (ae.getActionCommand().equals(Ac.DELETECOURSE.name())) {
            
        }
        if (ae.getActionCommand().equals(Ac.NEWEVENT.name())) {
            
        }
        if (ae.getActionCommand().equals(Ac.DELETE_EVENT.name())) {
            
        }        
    }
    
}
