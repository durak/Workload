package workloadstats.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.control.EventBuilder;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.EventType;
import workloadstats.ui.refactor.CourseListModel;

/**
 * Button listener for data model modification buttons
 *
 * @author Ilkka
 */
public class CalendarEventButtonListener implements ActionListener, ListSelectionListener {

    private JPanel pane;
    private Component cmpnt;
    private CourseListModel datamodel;
    private int courseIndex;
    private int[] selections;

    public CalendarEventButtonListener(JPanel pane, CourseListModel datamodel) {
        this.cmpnt = cmpnt;
        this.pane = pane;
        this.datamodel = datamodel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(Ac.NEWCOURSE.name())) {

        }
        if (ae.getActionCommand().equals(Ac.DELETECOURSE.name())) {

        }
        if (ae.getActionCommand().equals(Ac.NEWEVENT.name())) {
            NewEventPanel newEventPanel = new NewEventPanel();
            int choice = JOptionPane.showConfirmDialog(pane, newEventPanel,
                    "Syötä uusi tapahtuma", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (choice == JOptionPane.OK_OPTION) {
                Map<EvPropId, String> answers = newEventPanel.getValues();

                try {
                    Event newEvent = EventBuilder.buildNewEvent(answers);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pane, "Virhe syötteessä", "ALARM", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(CalendarEventButtonListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        if (ae.getActionCommand().equals(Ac.DELETE_EVENT.name())) {

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        JList courseList = (JList) lse.getSource();
        int[] selectedIndices = courseList.getSelectedIndices();
        if (selectedIndices.length > 1) {
            resetButtons(false);
        } else {
            resetButtons(true);
        }

        System.out.println("calendarbuttonlistener " + lse.getSource());
        if (!lse.getValueIsAdjusting()) {
            System.out.println("eventin eka " + lse.getFirstIndex());
            System.out.println("eventin vika " + lse.getLastIndex());
            System.out.println("listan selektiot" + courseList.getSelectedIndices().length);
        }
        System.out.println("calendarbuttonlistener " + lse.getClass());

    }

    public void resetButtons(Boolean b) {
        Component[] all = pane.getComponents();

        for (int i = 0; i < all.length; i++) {
            if (all[i] instanceof JButton) {
                JButton button = (JButton) all[i];
                button.setEnabled(b);
            }

        }
    }

}
