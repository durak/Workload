package workloadstats.ui.refactor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import workloadstats.ui.EvPropId;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Personal;
import workloadstats.ui.CourseListPanel;
import workloadstats.ui.EventRenderer;
import workloadstats.ui.EventStatsPanel;
import workloadstats.ui.NewEventPanel;
import workloadstats.ui.SingleSelectionListModel;

/**
 *
 * @author Ilkka
 */
public class EventListPanel2 extends JPanel implements ListDataListener {

    private MyCalendarControl myCalendarControl;
//    private EventStatsPanel eventStatsPanel;
    private Course selectedCourse;
    private Event selectedEvent;

    private List<Event> events;
    private JList list;

    private EventListModel elm;
    private EventStatsPanel2 eventStatsPanel2;

    public EventListPanel2(MyCalendarControl myCalendarControl, EventListModel elm, EventStatsPanel2 eventStatsPanel2) {
        this.eventStatsPanel2 = eventStatsPanel2;
        this.elm = elm;
        this.myCalendarControl = myCalendarControl;

        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnät"));

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        selectedEvent = null;
        elm.addListDataListener(this);
        list = new JList(elm);
        list.addListSelectionListener(eventStatsPanel2);
        
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        list.setCellRenderer(new EventRenderer());


        add(scrollPane);


        JButton newEventButton = new JButton("Uusi tapahtuma");

        newEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                NewEventPanel newEventPanel = new NewEventPanel();
                int choice = JOptionPane.showConfirmDialog(scrollPane, newEventPanel,
                        "Syötä uusi tapahtuma", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (choice == JOptionPane.OK_OPTION) {

                    String summary = newEventPanel.getValue(EvPropId.EVENTNAME);
                    String date = newEventPanel.getValue(EvPropId.DATE);
                    String startTime = newEventPanel.getValue(EvPropId.STARTTIME);
                    String startDateTime = date + "T" + startTime + "00";
                    String endTime = newEventPanel.getValue(EvPropId.ENDTIME);
                    String endDateTime = date + "T" + endTime + "00";

                    try {

                        Personal newPersonal = myCalendarControl.buildNewPersonal(summary, startDateTime, endDateTime, selectedCourse);
                        reset(selectedCourse);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(scrollPane, "Virhe syötteessä", "ALARM", JOptionPane.ERROR_MESSAGE);

                        Logger.getLogger(CourseListPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
        );
        add(newEventButton);
    }

    public void reset(Course course) {
        this.selectedCourse = course;
        events = course.getAllEvents();
        Event[] ev = events.toArray(new Event[events.size()]);
        list.setListData(ev);
//        list.setSelectedIndex(0);
    }

    /**
     * Listen to changes in the underlying data model
     *
     * @param lde
     */

    @Override
    public void intervalAdded(ListDataEvent lde) {
        System.out.println("Interval added \n" + lde.toString());
    }

    /**
     * Listen to changes in the underlying data model
     *
     * @param lde
     */
    @Override
    public void intervalRemoved(ListDataEvent lde) {
        System.out.println("Interval removed \n" + lde.toString());
    }

    /**
     * Listen to changes in the underlying data model
     *
     * @param lde
     */
    @Override
    public void contentsChanged(ListDataEvent lde) {
        list.clearSelection();

        System.out.println("contents changed \n" + lde.toString());
        System.out.println("eventListPanel2 " + lde.getSource());
    }

}
