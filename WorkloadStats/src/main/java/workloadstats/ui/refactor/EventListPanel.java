package workloadstats.ui.refactor;

import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.ui.EventRenderer;

/**
 *
 * @author Ilkka
 */
public class EventListPanel extends JPanel implements ListDataListener {

    private Course selectedCourse;
    private Event selectedEvent;

    private List<Event> events;
    private JList eventList;

    public EventListPanel(JList eventList) {

        this.eventList = eventList;
        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnät"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        JScrollPane scrollPane = new JScrollPane(eventList);
        add(scrollPane);

        //eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   
        // Set custom renderer for event list
        eventList.setCellRenderer(new EventRenderer());
    }

    /**
     * ListDataListener Listen to changes in the underlying course data model
     * Only function is to clear event list selections in case the course
     * data model changes.
     *
     * @param lde
     */
    @Override
    public void intervalAdded(ListDataEvent lde) {
//        System.out.println("Interval added \n" + lde.toString());
    }

    @Override
    public void intervalRemoved(ListDataEvent lde) {
//        System.out.println("Interval removed \n" + lde.toString());
    }

    @Override
    public void contentsChanged(ListDataEvent lde) {
        eventList.clearSelection();

//        System.out.println("contents changed \n" + lde.toString());
//        System.out.println("eventListPanel2 " + lde.getSource());
    }

}
