package workloadstats.ui.refactor;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Event;
import workloadstats.ui.EventStatusListener;
import workloadstats.ui.Ac;
import workloadstats.utils.Utility;

/**
 *
 * @author Ilkka
 */
public class EventStatsPanel2 extends JPanel implements ListSelectionListener {

    private Event selectedEvent;

    private JLabel titleValue;
    private JLabel dateValue;
    private JLabel startTimeValue;
    private JLabel durationValue;
    private JLabel locationValue;

    private JRadioButton attendYes;
    private JRadioButton attendNo;
    private JRadioButton attendMaybe;

    private EventStatusListener eventStatusListener;

    public EventStatsPanel2() {
        eventStatusListener = new EventStatusListener();
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
     * Set parameter's event as the current event and push information to upper
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
            String kestoLongToString = Long.toString(Utility.getDuration(event));
            durationValue.setText(kestoLongToString);
            eventStatusListener.setEvent(selectedEvent);
        }
        resetRadioButtons();
    }

    private void resetRadioButtons() {
        JRadioButton[] rbuttons = {attendYes, attendNo, attendMaybe};

        for (int i = 0; i < rbuttons.length; i++) {
            JRadioButton j = rbuttons[i];
            if (selectedEvent == null) {
                j.setEnabled(false);
            } else {
                String command = j.getActionCommand();
                j.setSelected(selectedEvent.getEventStatus().equals(command));
                j.setEnabled(true);
            }
        }
    }

    /*
      Initialize upper part of panel
     */
    private JPanel upperInit() {
        JPanel upper = new JPanel();
        GridLayout layout = new GridLayout(6, 2);
        upper.setLayout(layout);

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
        JPanel lower = new JPanel();
        lower.setBorder(javax.swing.BorderFactory.createTitledBorder("Osallistutko / osallistuitko tapahtumaan"));

        attendYes = new JRadioButton("Kyllä");
        attendYes.setActionCommand(Ac.CONFIRMED.name());
        attendYes.setEnabled(selectedEvent != null);
        attendNo = new JRadioButton("Ei");
        attendNo.setActionCommand(Ac.CANCELLED.name());
        attendNo.setEnabled(selectedEvent != null);
        attendMaybe = new JRadioButton("Harkitsen");
        attendMaybe.setActionCommand(Ac.TENTATIVE.name());
        attendMaybe.setEnabled(selectedEvent != null);

        attendYes.addActionListener(eventStatusListener);
        attendNo.addActionListener(eventStatusListener);
        attendMaybe.addActionListener(eventStatusListener);

        lower.add(attendMaybe);
        lower.add(attendYes);
        lower.add(attendNo);

        ButtonGroup eventStatusGroup = new ButtonGroup();
        eventStatusGroup.add(attendMaybe);
        eventStatusGroup.add(attendYes);
        eventStatusGroup.add(attendNo);

        return lower;
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        JList eventList = (JList) lse.getSource();
        Event selection = (Event) eventList.getSelectedValue();        
        resetEvent(selection);
        System.out.println("EventStatsPanel2 " + eventList);
        System.out.println(eventList.getModel());
    }

}
