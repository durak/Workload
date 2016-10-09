package workloadstats.ui.refactor;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import workloadstats.domain.model.Course;

/**
 * Model for courselist
 *
 * @author Ilkka
 */
public class CourseListModel extends AbstractListModel {

    private List<Course> courses;
    private Course selected;

    public CourseListModel(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public int getSize() {
        return courses.size();
    }

    @Override
    public Object getElementAt(int i) {
        return courses.get(i);
    }

    public void addNewCourse(Course newCourse) {
        int index = courses.size();
        this.courses.add(newCourse);
        //this, alku, loppu
        fireContentsChanged(this, 0, getSize());
        fireIntervalAdded(this, index, index);
    }

    public void removeCourse(Course course) {
        boolean removed = this.courses.remove(course);
        if (removed) {
            fireContentsChanged(this, 0, getSize());
        }
        int index = courses.indexOf(course);
        if (index >= 0) {
            fireIntervalRemoved(this, index, index);
        }
    }
    
    public void removeCourseAt(int i) {
        removeCourse((Course) getElementAt(i));
    }
    
    public Course getSelectedCourse() {
        
        return null;
    }

}
