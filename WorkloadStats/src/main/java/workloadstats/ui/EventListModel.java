package workloadstats.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.utils.EventComparatorChronological;

/**
 * ListModel for event JList, uses CourseListModel as a point of entry for
 * domain objects
 *
 * @author Ilkka
 */
public class EventListModel extends AbstractListModel implements ListSelectionListener {

    private CourseListModel clm;
    private List<Event> events;    

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


    /**
     * A bit complicated process: Listens to ListSelectionEvents from
     * CourseListModel, and updates this model with events. User can freely
     * select any number of courses to add.
     *
     * @param lse
     */
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        JList courseList = (JList) lse.getSource();
        int[] selectedIndices = courseList.getSelectedIndices();
        List<Event> newCollectionOfEventsToShow = new ArrayList<>();

        //Add all events from courses the user has selected
        for (int i = 0; i < selectedIndices.length; i++) {
            Course oneOfSelected = (Course) clm.getElementAt(selectedIndices[i]);
            newCollectionOfEventsToShow.addAll(oneOfSelected.getAllEvents());
        }

        //Sort events chronologically
        Collections.sort(newCollectionOfEventsToShow, new EventComparatorChronological());
        events = newCollectionOfEventsToShow;
        fireContentsChanged(this, 0, getSize());
    }

}
