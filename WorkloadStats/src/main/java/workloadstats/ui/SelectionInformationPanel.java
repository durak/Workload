package workloadstats.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.model.Event;
import workloadstats.ui.SelectionAttendancePanel;
import workloadstats.ui.SelectionAttendanceListener;
import workloadstats.utils.Ac;
import workloadstats.utils.EventUtility;

/**
 * Panel for current Event list selection information 
 * Uses EventAttendance panel & SelectionAttendanceListener for attendance input from the user.
 * If selection size > 1, everything is disabled;
 *
 * @author Ilkka
 */
public class SelectionInformationPanel extends JPanel implements ListSelectionListener {

    private Event selectedEvent;

    private JLabel titleValue;
    private JLabel dateValue;
    private JLabel startTimeValue;
    private JLabel durationValue;
    private JLabel locationValue;

    private JRadioButton attendYes;
    private JRadioButton attendNo;
    private JRadioButton attendMaybe;

    private SelectionAttendancePanel eventAttendancePanel;
    private SelectionAttendanceListener eventAttendance;


    public SelectionInformationPanel() {
        eventAttendance = new SelectionAttendanceListener();
        selectedEvent = null;

        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnän tiedot"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        this.add(upperInit());
        this.add(lowerInit());
    }

    /**
     * Set panel view to current selected event
     *
     * @param event
     */
    public void resetEvent(Event event) {
        selectedEvent = event;
        if (selectedEvent == null) {
            titleValue.setText("---");
            dateValue.setText("---");
            startTimeValue.setText("---");
            durationValue.setText("---");

        } else {
            titleValue.setText(event.getEventName());
            dateValue.setText(event.getStartDateString());
            startTimeValue.setText(event.getStartTime());
            String kestoLongToString = Long.toString(EventUtility.getDuration(event));
            durationValue.setText(kestoLongToString + " min");
            eventAttendance.setEvent(selectedEvent);
        }
        resetRadioButtons();
    }

    private void resetRadioButtons() {
        eventAttendancePanel.resetGroup(selectedEvent);
    }

    /*
      Initialize upper part of panel
     */
    private JPanel upperInit() {
        JPanel upper = new JPanel(new GridLayout(4, 2));

        JLabel title = new JLabel("Otsikko");
        JLabel date = new JLabel("Pvm");
        JLabel startTime = new JLabel("Alku");
        JLabel duration = new JLabel("Kesto");
        JLabel location = new JLabel("Paikka");

        titleValue = new JLabel("---");
        dateValue = new JLabel("---");
        startTimeValue = new JLabel("---");
        durationValue = new JLabel("---");
        locationValue = new JLabel("---");

        upper.add(title);
        upper.add(titleValue);
        upper.add(date);
        upper.add(dateValue);
        upper.add(startTime);
        upper.add(startTimeValue);
        upper.add(duration);
        upper.add(durationValue);

        return upper;
    }

    private JPanel lowerInit() {
        Ac[] buttonIds = {Ac.CONFIRMED, Ac.CANCELLED, Ac.TENTATIVE};
        eventAttendancePanel = new SelectionAttendancePanel(buttonIds, "Osallistutko / osallistuitko tapahtumaan", eventAttendance);

        return eventAttendancePanel;
    }

    /**
     * Listen to selection changes in the Event List
     *
     * @param lse
     */
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        JList eventList = (JList) lse.getSource();
        if (eventList.getSelectedIndices().length > 1) {
            resetEvent(null);
        } else {
            Event selection = (Event) eventList.getSelectedValue();
            resetEvent(selection);
        }

//        System.out.println("EventStatsPanel2 " + eventList);
//        System.out.println("kukkuu" + eventList.getName());
    }

}
