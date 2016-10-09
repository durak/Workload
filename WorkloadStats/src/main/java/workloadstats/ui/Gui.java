package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import workloadstats.calendardata.hycalendar.HyCalendarControl;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
import workloadstats.ui.refactor.CourseListModel;
import workloadstats.ui.refactor.CourseListPanel2;
import workloadstats.ui.refactor.EventListModel;
import workloadstats.ui.refactor.EventListPanel2;
import workloadstats.ui.refactor.EventStatsPanel2;

/**
 *
 * @author Ilkka
 */
public class Gui implements Runnable {

    private HyCalendarControl hyCalendarControl;
    private MyCalendarControl myCalendarControl;

    private JFrame frame;

    public Gui(MyCalendarControl myCalendarControl) {
//        this.hyCalendarControl = hyCalendarControl;
        this.myCalendarControl = myCalendarControl;
    }

    @Override
    public void run() {
        frame = new JFrame("WorkloadStats");
        frame.setPreferredSize(new Dimension(1200, 800));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponent(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void initComponent(Container container) {

        CourseListModel clm = new CourseListModel(myCalendarControl.getCourses());
        EventListModel elm = new EventListModel(clm);
        clm.addListDataListener(elm);
                        
        JList courseList = new JList(clm);
        JList eventList = new JList(elm);

        MenuButtonPanel menuButtonPanel = new MenuButtonPanel();
        CourseStatsPanel courseStatsPanel = new CourseStatsPanel();
        EventStatsPanel2 eventStatsPanel = new EventStatsPanel2();
        EventListPanel2 eventListPanel = new EventListPanel2(eventList);
        CourseListPanel2 courseListPanel = new CourseListPanel2(myCalendarControl, eventListPanel, clm, elm);
        
        eventList.addListSelectionListener(eventStatsPanel);
        elm.addListDataListener(eventListPanel);



        JPanel west = new JPanel(new GridLayout(2, 1));
        west.add(menuButtonPanel);
        west.add(courseListPanel);

        JPanel east = new JPanel(new GridLayout(2, 1));
        east.add(eventStatsPanel);
        east.add(courseStatsPanel);

        container.add(west, BorderLayout.WEST);
        container.add(eventListPanel, BorderLayout.CENTER);
        container.add(east, BorderLayout.EAST);

    }

    public JFrame getFrame() {
        return frame;
    }

    //testing
    public List<Course> testaustaVartenKurssitUlosGuista() {
        return this.myCalendarControl.getCourses();
    }

}
