package workloadstats.ui;

import java.util.List;
import javax.swing.AbstractListModel;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;

/**
 * Data Model for courselist, connects domain objects with the user interface
 *
 * @author Ilkka
 */
public class CourseListModel extends AbstractListModel {

    private List<Course> courses;

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
        int index = courses.indexOf(course);
        boolean removed = this.courses.remove(course);
        if (removed) {
            fireContentsChanged(this, 0, getSize());
        }
        
        if (index >= 0) {
            fireIntervalRemoved(this, index, index);
        }
    }

    public void removeCourseAt(int i) {
        removeCourse((Course) getElementAt(i));
        fireContentsChanged(this, 0, getSize());
        if (i >= 0) {
            fireIntervalRemoved(this, i, i);
        }
    }

    public void addEvent(int i, Event event) {
        courses.get(i).addEvent(event);
        fireContentsChanged(this, 0, getSize());
    }

    public void removeEvent(int i, Event event) {
        courses.get(i).removeEvent(event);
        fireContentsChanged(this, 0, getSize());
    }

}
