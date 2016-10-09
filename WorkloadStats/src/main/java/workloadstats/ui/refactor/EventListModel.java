package workloadstats.ui.refactor;

import edu.emory.mathcs.backport.java.util.Collections;
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
import workloadstats.utils.EventComparatorChronological;
import workloadstats.utils.Utility;

/**
 * ListModel for event JList, uses CourseListModel as a point of entry for
 * domain objects
 *
 * @author Ilkka
 */
public class EventListModel extends AbstractListModel implements ListSelectionListener, ListDataListener {

    private CourseListModel clm;

    private List<Event> events;
    private Course course;

    public EventListModel(CourseListModel clm) {
        this.clm = clm;
        events = new ArrayList<>();
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

    /**
     * A bit complicated process: Listens to ListSelectionEvents from
     * CourseListModel, and updates this model with events. User can freely select any
     * number of courses to add.
     *
     * @param lse
     */
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        JList listThatFiredASelectionEvent = (JList) lse.getSource();
        int[] selectedIndices = listThatFiredASelectionEvent.getSelectedIndices();
        List<Event> newCollectionOfEventsToShow = new ArrayList<>();
        
        for (int i = 0; i < selectedIndices.length; i++) {
            Course oneOfSelected = (Course) clm.getElementAt(selectedIndices[i]);
            newCollectionOfEventsToShow.addAll(oneOfSelected.getAllEvents());
        }
        
//        ListSelectionModel listSelectionModelOfThatList = listThatFiredASelectionEvent.getSelectionModel();
//        
//        
//        int minIndex = listThatFiredASelectionEvent.getMinSelectionIndex();
//        int maxIndex = listThatFiredASelectionEvent.getMaxSelectionIndex();
//
//        //Check every index between min and max: if user has selected them -> add events
//        if (!lse.getValueIsAdjusting()) {            
//            for (int i = minIndex; i <= maxIndex; i++) {
//                if (listSelectionModelOfThatList.isSelectedIndex(i)) {
//                    Course oneOfSelectedCourses = (Course) clm.getElementAt(i);
//                    newCollectionOfEventsToShow.addAll(oneOfSelectedCourses.getAllEvents());
//                }
//            }
//        }
        //Sort events chronologically
        Collections.sort(newCollectionOfEventsToShow, new EventComparatorChronological());
        events = newCollectionOfEventsToShow;
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

    /**
     * For convenience a getter for the underlying CourseListModel data model
     *
     * @return
     */
    public CourseListModel getClm() {
        return clm;
    }

}
