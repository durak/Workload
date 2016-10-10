/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui.refactor;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.control.EventBuilder;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.utils.Ac;
import workloadstats.utils.PropId;
import workloadstats.ui.NewEventPanel;

/**
 * Button listener for data model modification buttons
 *
 * @author Ilkka
 */
public class CalendarEventButtonListener2 implements ActionListener, ListSelectionListener {

//    private JPanel panel;
    private Container container;
    private JList courseList;
    private JList eventList;
    private CourseListModel datamodel;
    private int[] courseSelections;
    private List<JButton> buttons;

    public CalendarEventButtonListener2(Container container, JList courseList, JList eventList, List<JButton> buttons) {
        this.container = container;
        this.courseList = courseList;
        this.eventList = eventList;
        this.datamodel = (CourseListModel) courseList.getModel();
        this.buttons = buttons;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(Ac.NEWCOURSE.name())) {

            PropId[] userInputNeeded = {PropId.COURSENAME, PropId.DATE};
            NewEventPanel newCoursePanel = new NewEventPanel(userInputNeeded, "Uuden kurssin tiedot");
            int choice = JOptionPane.showConfirmDialog(container, newCoursePanel,
                    "Syötä uusi kurssi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (choice == JOptionPane.OK_OPTION) {
                Map<PropId, String> answers = newCoursePanel.getValues();

                try {
                    Course newCourse = EventBuilder.buildNewCourse(answers);
                    datamodel.addNewCourse(newCourse);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(container, "Virhe syötteessä", "ALARM", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(CalendarEventButtonListener2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (ae.getActionCommand().equals(Ac.DELETECOURSE.name())) {
            int oldSelection = courseSelections[0];
            if (courseSelections.length == 1) {
                int choice = JOptionPane.showConfirmDialog(container, "Haluatko varmasti poistaa kurssin?",
                        "Achtung!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (choice == JOptionPane.OK_OPTION) {
                    datamodel.removeCourseAt(courseSelections[0]);
                }

            }
        }
        if (ae.getActionCommand().equals(Ac.NEWEVENT.name())) {

            PropId[] neededAnswers = {PropId.EVENTNAME,
                PropId.EVENTTYPE, PropId.DATE, PropId.STARTTIME,
                PropId.ENDTIME, PropId.STATUS};

            NewEventPanel newEventPanel = new NewEventPanel(neededAnswers, "Uuden tapahtuman tiedot");
            int choice = JOptionPane.showConfirmDialog(container, newEventPanel,
                    "Syötä uusi tapahtuma", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (choice == JOptionPane.OK_OPTION) {
                Map<PropId, String> answers = newEventPanel.getValues();

                try {
                    Event newEvent = EventBuilder.buildNewEvent(answers);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(container, "Virhe syötteessä", "ALARM", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(CalendarEventButtonListener2.class.getName()).log(Level.SEVERE, null, ex);
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
            this.courseSelections = selectedIndices;
            if (selectedIndices.length > 1) {
                resetButtons(false);
            } else {
                resetButtons(true);
            }
        }
        
        System.out.println("Kutsu tuli " + courseList.getName());

    }

//    public void resetButtons(Boolean b) {
//        Component[] all = container.getComponents();
//
//        for (int i = 0; i < all.length; i++) {
//            if (all[i] instanceof JButton) {
//                JButton button = (JButton) all[i];
//                button.setEnabled(b);
//            }
//
//        }
//    }
    public void resetButtons(Boolean b) {
        for (JButton button : buttons) {
            button.setEnabled(b);
        }
    }

}
