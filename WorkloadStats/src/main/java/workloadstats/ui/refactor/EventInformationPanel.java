package workloadstats.ui.refactor;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.model.Event;
import workloadstats.ui.EventAttendancePanel;
import workloadstats.ui.EventAttendanceListener;
import workloadstats.utils.Ac;
import workloadstats.utils.EventUtility;

/**
 *
 * @author Ilkka
 */
public class EventInformationPanel extends JPanel implements ListSelectionListener {

    private Event selectedEvent;

    private JLabel titleValue;
    private JLabel dateValue;
    private JLabel startTimeValue;
    private JLabel durationValue;
    private JLabel locationValue;

    private JRadioButton attendYes;
    private JRadioButton attendNo;
    private JRadioButton attendMaybe;

    private EventAttendancePanel eventAttendancePanel;
    private EventAttendanceListener eventAttendance;

    public EventInformationPanel() {
        eventAttendance = new EventAttendanceListener();
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
            durationValue.setText(kestoLongToString);
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
        eventAttendancePanel = new EventAttendancePanel(buttonIds, "Osallistutko / osallistuitko tapahtumaan", eventAttendance);

        return eventAttendancePanel;
    }

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
//        System.out.println(eventList.getModel());
    }

}
