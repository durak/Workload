package workloadstats.ui.refactor;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
import workloadstats.utils.Ac;
import workloadstats.ui.CalendarEventButtonListener;
import workloadstats.ui.ButtonsPanel;
import workloadstats.ui.CourseListRenderer;


/**
 *
 * @author Ilkka
 */
public class CourseListPanel2 extends JPanel {
    
    private JList courseList;

    private CourseListModel clm;
    private EventListModel elm;

    //            
    Course selectedCourse;

    public CourseListPanel2(JList courseList, CourseListModel clm, EventListModel elm) {
        this.clm = clm;
        this.elm = elm;     
        this.courseList = courseList;
        initComponents();
    }

    private void initComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kurssilista"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        
        JPanel buttons = new JPanel(new GridLayout(2, 2, 10, 10));
        
        CalendarEventButtonListener cebl = new CalendarEventButtonListener(buttons, clm);

        courseList.addListSelectionListener(elm);
        courseList.addListSelectionListener(cebl);
        courseList.setCellRenderer(new CourseListRenderer());
        JScrollPane scrollPane = new JScrollPane(courseList);                               
        
        JButton newCourseButton = new JButton("Uusi kurssi");
        newCourseButton.setActionCommand(Ac.NEWCOURSE.name());
        newCourseButton.addActionListener(cebl);
        JButton deleteCourseButton = new JButton("Poista kurssi");
        deleteCourseButton.setActionCommand(Ac.DELETECOURSE.name());
        deleteCourseButton.addActionListener(cebl);

        JButton newEventButton = new JButton("Uusi tapahtuma");
        newEventButton.setActionCommand(Ac.NEWEVENT.name());
        newEventButton.addActionListener(cebl);
        JButton deleteEventButton = new JButton("Poista tapahtuma");

        
        buttons.setBorder(javax.swing.BorderFactory.createTitledBorder("Toiminnot"));
        buttons.add(newCourseButton);
        buttons.add(deleteCourseButton);
        buttons.add(newEventButton);
        buttons.add(deleteEventButton);

        add(scrollPane);
//        add(buttons);
//        add(new CalendarEventButtonsPanel());

    }

}
