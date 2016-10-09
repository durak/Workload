package workloadstats.ui.refactor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
import workloadstats.ui.Ac;
import workloadstats.ui.CalendarEventButtonListener;
import workloadstats.ui.CourseListRenderer;
import workloadstats.ui.NewCoursePanel;

/**
 *
 * @author Ilkka
 */
public class CourseListPanel2 extends JPanel {

    private MyCalendarControl myCalendarControl;
    private EventListPanel2 eventListPanel;
    private List<Course> courses;
    private JList list;

    private CourseListModel clm;
    private EventListModel elm;

    //            
    Course selectedCourse;

    public CourseListPanel2(MyCalendarControl myCalendarControl, EventListPanel2 eventListPanel, CourseListModel clm, EventListModel elm) {
        this.clm = clm;
        this.elm = elm;
        this.myCalendarControl = myCalendarControl;
        this.eventListPanel = eventListPanel;
        this.courses = myCalendarControl.getCourses();
        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kurssilista"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        
        JPanel buttons = new JPanel(new GridLayout(2, 2, 10, 10));
        CalendarEventButtonListener cebl = new CalendarEventButtonListener(buttons, clm);

        list = new JList(clm);
        list.addListSelectionListener(elm);
        list.addListSelectionListener(cebl);
        list.setCellRenderer(new CourseListRenderer());
        JScrollPane scrollPane = new JScrollPane(list);
        
        

        
        
        
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


//        deleteCourseButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                if (list.getSelectedValue() == null) {
//                    JOptionPane.showMessageDialog(scrollPane, "Valitse ensin kurssi", "Alert", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    int choice = JOptionPane.showConfirmDialog(scrollPane, "Haluatko varmasti poistaa kurssin?",
//                            "Achtung!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//                    if (choice == JOptionPane.OK_OPTION) {
//                        clm.removeCourseAt(list.getSelectedIndex());
//                    }
//                }
//            }
//        });

        add(scrollPane);

        add(buttons);

    }

}
