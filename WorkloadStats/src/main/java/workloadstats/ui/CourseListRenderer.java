/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import workloadstats.domain.model.Course;

/**
 * Custom renderer for CourseListPanel (a Jlist of Courses)
 * @author Ilkka
 */
public class CourseListRenderer extends JLabel implements ListCellRenderer<Course> {

    public CourseListRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Course> list, Course course, int index,
            boolean isSelected, boolean cellHasFocus) {

        String courseName = course.getCourseName();
        setText(courseName);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

}
