/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.util.List;
import javax.swing.AbstractListModel;
import workloadstats.domain.model.Course;

/**
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getElementAt(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        public void addNewCollection(Course newCourse) {        
        this.courses.add(newCourse);
        fireContentsChanged(this, 0, getSize());
    }
    
}
