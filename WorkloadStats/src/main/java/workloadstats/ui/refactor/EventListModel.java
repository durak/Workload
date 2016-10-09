package workloadstats.ui.refactor;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;

/**
 * Model for eventlist
 *
 * @author Ilkka
 */
public class EventListModel extends AbstractListModel implements ListSelectionListener, ListDataListener {

    private CourseListModel clm;

    private List<Event> events;
    private Course course;

    public EventListModel(CourseListModel clm) {
        this.clm = clm;
        Course c = (Course) clm.getElementAt(0);
        events = c.getAllEvents();
    }

    @Override
    public int getSize() {
        return events.size();
    }

    @Override
    public Object getElementAt(int i) {
        return events.get(i);
    }

    public void addNewEvent(Event newEvent) {
        this.course.addEvent(newEvent);
        //this, alku, loppu
        fireContentsChanged(this, 0, getSize());
    }

    public void removeEvent(Event event) {
        System.out.println(event);
        this.course.removeEvent(event);
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        System.out.println("listselection value changed");
        JList courseJList =(JList) lse.getSource();                        
        ListSelectionModel courseLsm = courseJList.getSelectionModel();
        int minIndex = courseJList.getMinSelectionIndex();
        int maxIndex = courseJList.getMaxSelectionIndex();
        System.out.println(courseJList.getSelectedIndex());
        List<Event> newEvents = new ArrayList<>();
        if (!lse.getValueIsAdjusting()) {
            for (int i = minIndex; i <= maxIndex; i++) {
                if (courseLsm.isSelectedIndex(i)) {
                    Course temp = (Course) clm.getElementAt(i);
//                    System.out.println(temp);
                    newEvents.addAll(temp.getAllEvents());
                }                
            }                                                                                             
        }
        events = newEvents;
        
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public void intervalAdded(ListDataEvent lde) {
        System.out.println("Interval added \n" + lde.toString());
    }

    @Override
    public void intervalRemoved(ListDataEvent lde) {
        System.out.println("Interval removed \n" + lde.toString());
    }

    @Override
    public void contentsChanged(ListDataEvent lde) {
        System.out.println("contents changed \n" + lde.toString());
    }
    
    public CourseListModel getClm() {
        return clm;
    }

}
