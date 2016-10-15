package workloadstats.ui.utils;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import workloadstats.domain.model.Course;

/**
 * Custom renderer for JList of courses
 * @author Ilkka
 */
public class CourseListRenderer extends JLabel implements ListCellRenderer<Course> {

    public CourseListRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Course> list, Course course, int index,
            boolean isSelected, boolean cellHasFocus) {

        String courseName = course.getEventName();
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
