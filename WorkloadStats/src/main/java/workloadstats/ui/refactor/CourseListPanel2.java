package workloadstats.ui.refactor;

import java.awt.BorderLayout;
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

        CalendarEventButtonListener cebl = new CalendarEventButtonListener(this, clm);
        
        list = new JList(clm);        
        list.addListSelectionListener(elm);
        list.addListSelectionListener(cebl);


        JScrollPane scrollPane = new JScrollPane(list);
        list.setCellRenderer(new CourseListRenderer());

        JButton newCourseButton = new JButton("Uusi kurssi");
        newCourseButton.setActionCommand(Ac.NEWEVENT.name());
        newCourseButton.addActionListener(cebl);
        JButton deleteCourseButton = new JButton("Poista kurssi");
        
        
//        newCourseButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                NewCoursePanel newCoursePanel = new NewCoursePanel();
//                int choice = JOptionPane.showConfirmDialog(scrollPane, newCoursePanel,
//                        "Syötä uusi kurssi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//                if (choice == JOptionPane.OK_OPTION) {
//                    String summary = newCoursePanel.getNameText();
//                    String date = newCoursePanel.getStartDateText();
//                    date = date + "T000000";
//
//                    try {
//                        Course newCourse = myCalendarControl.buildNewCourse(summary, date, date);
//                        clm.addNewCourse(newCourse);                        
//                    } catch (Exception ex) {
//                        JOptionPane.showMessageDialog(scrollPane, "Virhe syötteessä", "Alert", JOptionPane.ERROR_MESSAGE);
//
//                        Logger.getLogger(CourseListPanel2.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//            }
//        });

        deleteCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                
                if (list.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(scrollPane, "Valitse ensin kurssi", "Alert", JOptionPane.ERROR_MESSAGE);
                } else {
                    int choice = JOptionPane.showConfirmDialog(scrollPane, "Haluatko varmasti poistaa kurssin?",
                            "Achtung!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (choice == JOptionPane.OK_OPTION) {
                        clm.removeCourseAt(list.getSelectedIndex());
                    }
                }
            }
        });

        add(scrollPane, BorderLayout.NORTH);
        add(newCourseButton, BorderLayout.SOUTH);
        add(deleteCourseButton, BorderLayout.SOUTH);

    }

}    

