package workloadstats.ui;

import workloadstats.ui.utils.EventListRenderer;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Panel for showing the event listing
 * @author Ilkka
 */
public class EventListPanel extends JPanel implements ListDataListener {
      
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
        
        // Set custom renderer for event list
        eventList.setCellRenderer(new EventListRenderer());
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
    }

}
