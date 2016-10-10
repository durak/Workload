package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import workloadstats.calendardata.hycalendar.HyCalendarControl;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
import workloadstats.ui.refactor.CalendarEventButtonListener2;
import workloadstats.ui.refactor.CourseListModel;
import workloadstats.ui.refactor.CourseListPanel2;
import workloadstats.ui.refactor.EventListModel;
import workloadstats.ui.refactor.EventListPanel2;
import workloadstats.ui.refactor.EventInformationPanel;
import workloadstats.utils.Ac;

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
//        clm.addListDataListener(elm);

        JList courseList = new JList(clm);
        courseList.setName("courselist");
        JList eventList = new JList(elm);
        eventList.setName("eventList");

        Ac[] menuButtons = {Ac.NEWCALENDAR, Ac.LOADCALENDAR, Ac.IMPORTCALENDAR, Ac.SAVECALENDAR};
        JPanel mainMenu = new ButtonsPanel(menuButtons, "Main menu");
        mainMenu.setMaximumSize(new Dimension(300, 20));
        

        Ac[] eventButtons = {Ac.NEWCOURSE, Ac.DELETECOURSE, Ac.NEWEVENT, Ac.DELETE_EVENT};
        ButtonsPanel buttonsPanel = new ButtonsPanel(eventButtons, "Toiminnot");
        buttonsPanel.setMaximumSize(new Dimension(300, 20));
                
        CalendarEventButtonListener2 cebl2 = new CalendarEventButtonListener2(container, courseList, eventList, buttonsPanel.getButtons());
        courseList.addListSelectionListener(cebl2);
        eventList.addListSelectionListener(cebl2);
        buttonsPanel.addListener(cebl2);

        SelectionStatsPanel courseStatsPanel = new SelectionStatsPanel();
        EventInformationPanel eventStatsPanel = new EventInformationPanel();
        EventListPanel2 eventListPanel = new EventListPanel2(eventList);
        CourseListPanel2 courseListPanel = new CourseListPanel2(courseList, clm, elm);

        eventList.addListSelectionListener(eventStatsPanel);
        elm.addListDataListener(eventListPanel);

//        JPanel west = new JPanel(new GridLayout(3, 1));
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        west.add(mainMenu);
        west.add(courseListPanel);

        west.add(buttonsPanel);

//        JPanel east = new JPanel(new GridLayout(2, 1));
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
//        east.setLayout(new GridLayout(2, 1));
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
