package workloadstats.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.utils.EventBuilder;
import workloadstats.domain.Course;
import workloadstats.domain.Event;
import workloadstats.utils.Ac;
import workloadstats.utils.PropId;

/**
 * ActionListener for data model modification buttons. ListSelectionListener to
 * track user's selection on both CourseList and EventList. Allows data model
 * modification only when 1 course and 1 event are selected.
 *
 * When user takes action, suitable dialog is shown to get input from the user.
 * If information is valid, data model is modified, else user gets an error
 * dialog.
 *
 * @author Ilkka
 */
public class CalendarActionListener implements ActionListener, ListSelectionListener {

    private Container container;
    private CourseListModel clmodel;
    private EventListModel elmodel;
    private int[] courseSelections;
    private int[] eventSelections;
    private List<JButton> buttons;
    private JList courseList;

    public CalendarActionListener(Container cntr, JList crsList, EventListModel elm, List<JButton> btns) {
        this.container = cntr;
        this.courseList = crsList;
        this.clmodel = (CourseListModel) crsList.getModel();
        this.elmodel = elm;
        this.buttons = btns;
        resetAvailableButtons();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(Ac.NEWCOURSE.name())) {
            newCourseAction();
        }

        if (ae.getActionCommand().equals(Ac.DELETECOURSE.name())) {
            deleteCourseAction();
            resetAvailableButtons();
        }

        if (ae.getActionCommand().equals(Ac.NEWEVENT.name())) {
            newEventAction();
        }

        if (ae.getActionCommand().equals(Ac.DELETE_EVENT.name())) {
            deleteEventAction();

        }
    }

    /**
     * Set selected indices from both CourseList and EventList. If multiple
     * indices are selected, disable some buttons to avoid complications.
     *
     * @param lse
     */
    @Override
    public void valueChanged(ListSelectionEvent lse) {

        if (!lse.getValueIsAdjusting()) {
            JList sourceList = (JList) lse.getSource();
            int[] selectedIndices = sourceList.getSelectedIndices();

            if (sourceList.getName().equals("courselist")) {
                this.courseSelections = selectedIndices;
                this.eventSelections = null;
            }

            if (sourceList.getName().equals("eventlist")) {
                this.eventSelections = selectedIndices;
            }
        }

        resetAvailableButtons();
    }

    /**
     * Set available buttons to to match available operations i.e. if no course
     * is selected, no course can be deleted -> disable DELETECOURSE button
     */
    private void resetAvailableButtons() {

        boolean newEvent, deleteEvent, newCourse, deleteCourse;
        newEvent = deleteEvent = newCourse = deleteCourse = true;

        if (courseSelections == null) {
            newEvent = deleteEvent = deleteCourse = false;
        } else if (courseSelections.length > 1) {
            newEvent = deleteEvent = false;
        } else if (courseSelections.length == 0) {
            newEvent = deleteEvent = deleteCourse = false;
        }

        if (eventSelections == null) {
            deleteEvent = false;
        }

        for (JButton button : buttons) {
            if (button.getActionCommand().equals(Ac.NEWEVENT.name())) {
                button.setEnabled(newEvent);
            }
            if (button.getActionCommand().equals(Ac.DELETE_EVENT.name())) {
                button.setEnabled(deleteEvent);
            }
            if (button.getActionCommand().equals(Ac.NEWCOURSE.name())) {
                button.setEnabled(newCourse);
            }
            if (button.getActionCommand().equals(Ac.DELETECOURSE.name())) {
                button.setEnabled(deleteCourse);
            }
        }
    }

    private void newCourseAction() {
        // Create dialog to prompt user for input on needed PropIds
        PropId[] userInputNeeded = {PropId.COURSENAME, PropId.DATE};
        CalendarEventUserInputPanel newCoursePanel = new CalendarEventUserInputPanel(userInputNeeded, "Uuden kurssin tiedot");
        int choice = JOptionPane.showConfirmDialog(container, newCoursePanel,
                "Syötä uusi kurssi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Try to create a new course with user input, if ok insert to datamodel
        if (choice == JOptionPane.OK_OPTION) {
            Map<PropId, String> answers = newCoursePanel.getValues();

            try {
                if (EventBuilder.verifyCourseData(answers)) {
                    throw new Exception("");
                }
                Course newCourse = EventBuilder.buildNewCourse(answers);
                clmodel.addNewCourse(newCourse);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(container, "Virhe syötteessä", "ALARM", JOptionPane.ERROR_MESSAGE);
//                Logger.getLogger(CalendarActionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteCourseAction() {
        int oldSize = courseSelections.length;
        int[] oldSelection = courseSelections;
        List<Course> toBeDeleted = new ArrayList<>();

        // Fetch toBeDeleted courses from data model
        for (int i = 0; i < oldSize; i++) {
            Course del = (Course) clmodel.getElementAt(oldSelection[i]);
            int choice = JOptionPane.showConfirmDialog(container, "Haluatko varmasti poistaa kurssin?\n" + del.getEventName(),
                    "Huomio!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) {
                toBeDeleted.add(del);
            }
        }
        // remove from data model only after user has been prompted on all selected removals to avoid index problems
        for (Course course : toBeDeleted) {
            clmodel.removeCourse(course);
        }
    }

    private void newEventAction() {
        // Create dialog to prompt user for input on needed PropIds
        PropId[] neededAnswers = {PropId.EVENTNAME,
            PropId.EVENTTYPE, PropId.DATE, PropId.STARTTIME,
            PropId.ENDTIME, PropId.STATUS};

        CalendarEventUserInputPanel newEventPanel = new CalendarEventUserInputPanel(neededAnswers, "Uuden tapahtuman tiedot");
        int choice = JOptionPane.showConfirmDialog(container, newEventPanel,
                "Syötä uusi tapahtuma", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Try to create a new event with user input, if ok insert to datamodel
        if (choice == JOptionPane.OK_OPTION) {
            Map<PropId, String> answers = newEventPanel.getValues();

            try {
                if (EventBuilder.verifyEventData(answers)) {
                    throw new Exception("");
                }
                Event newEvent = EventBuilder.buildNewEvent(answers);
                clmodel.addEvent(courseSelections[0], newEvent);
                courseList.setSelectedIndices(courseSelections);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(container, "Virhe syötteessä", "ALARM", JOptionPane.ERROR_MESSAGE);
//                Logger.getLogger(CalendarActionListener.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void deleteEventAction() {
        int oldSize = eventSelections.length;
        int[] oldSelection = eventSelections;
        List<Event> toBeDeleted = new ArrayList<>();

        // Fetch toBeDeleted courses from data model
        for (int i = 0; i < oldSize; i++) {
            Event del = (Event) elmodel.getElementAt(oldSelection[i]);
            int choice = JOptionPane.showConfirmDialog(container, "Haluatko varmasti poistaa tapahtuman?\n" + del.getStartDateString() + " " + del.getEventName(),
                    "Huomio!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) {
                toBeDeleted.add(del);
            }
        }
        // remove from data model only after user has been prompted on all selected removals to avoid index problems
        for (Event event : toBeDeleted) {
            clmodel.removeEvent(courseSelections[0], event);
        }
        /*
        Refresh courseList selection, so that eventList will be notified of the
        changes and refresh.
         */
        int oldindex = courseSelections[0];
        courseList.clearSelection();
        courseList.setSelectedIndex(oldindex);
    }

}
