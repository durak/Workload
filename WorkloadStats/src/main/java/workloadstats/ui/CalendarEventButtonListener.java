package workloadstats.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import workloadstats.domain.model.Course;
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

            EvPropId[] userInputNeeded = {EvPropId.COURSENAME, EvPropId.DATE};
            NewEventPanel newCoursePanel = new NewEventPanel(userInputNeeded, "Uuden kurssin tiedot");
            int choice = JOptionPane.showConfirmDialog(pane, newCoursePanel,
                    "Syötä uusi kurssi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (choice == JOptionPane.OK_OPTION) {
                Map<EvPropId, String> answers = newCoursePanel.getValues();

                try {
                    Course newCourse = EventBuilder.buildNewCourse(answers);
                    datamodel.addNewCourse(newCourse);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pane, "Virhe syötteessä", "ALARM", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(CalendarEventButtonListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (ae.getActionCommand().equals(Ac.DELETECOURSE.name())) {

        }
        if (ae.getActionCommand().equals(Ac.NEWEVENT.name())) {

            EvPropId[] neededAnswers = {EvPropId.EVENTNAME,
                EvPropId.EVENTTYPE, EvPropId.DATE, EvPropId.STARTTIME,
                EvPropId.ENDTIME, EvPropId.STATUS};

            NewEventPanel newEventPanel = new NewEventPanel(neededAnswers, "Uuden tapahtuman tiedot");
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
        if (!lse.getValueIsAdjusting()) {
            int[] selectedIndices = courseList.getSelectedIndices();

            if (selectedIndices.length > 1) {
                resetButtons(false);
            } else {
                resetButtons(true);
            }
        }

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
