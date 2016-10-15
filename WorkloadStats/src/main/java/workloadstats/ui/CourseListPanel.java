package workloadstats.ui;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import workloadstats.domain.model.Course;
import workloadstats.ui.utils.CourseListRenderer;


/**
 * Extended JPanel for course list view
 * @author Ilkka
 */
public class CourseListPanel extends JPanel {
    
    private JList courseList;
    private Course selectedCourse;

    public CourseListPanel(JList courseList) {        
        this.courseList = courseList;
        initComponents();
    }

    private void initComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kurssilista"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);        
        
        courseList.setCellRenderer(new CourseListRenderer());
        JScrollPane scrollPane = new JScrollPane(courseList);                               
        add(scrollPane);
    }

}
