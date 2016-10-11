package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponent(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void initComponent(Container container) {

        // Initialize Data model
        CourseListModel clm = new CourseListModel(myCalendarControl.getCourses());
        EventListModel elm = new EventListModel(clm);

        // Initialize Course and Event lists with Data models
        // datalistener not needed in current setup
        // clm.addListDataListener(elm);
        JList courseList = new JList(clm);
        courseList.setName("courselist");
        JList eventList = new JList(elm);
        eventList.setName("eventlist");
        
        // Initialize CourseListPanel and EventListPanel
        CourseListPanel courseListPanel = new CourseListPanel(courseList);
        EventListPanel middleEventListPanel = new EventListPanel(eventList);

        // Initialize Main menu
        Ac[] menuButtons = {Ac.NEWCALENDAR, Ac.LOADCALENDAR, Ac.IMPORTCALENDAR, Ac.SAVECALENDAR};
        JPanel mainMenu = new ButtonsPanel(menuButtons, "Main menu");
        mainMenu.setMaximumSize(new Dimension(400, 20));

        // Initialize Calendar buttons
        Ac[] eventButtons = {Ac.NEWCOURSE, Ac.DELETECOURSE, Ac.NEWEVENT, Ac.DELETE_EVENT};
        ButtonsPanel buttonsPanel = new ButtonsPanel(eventButtons, "Toiminnot");
        buttonsPanel.setMaximumSize(new Dimension(400, 20));
        
        // Initialize Selection Statistics & Selection Information panels
        SelectionStatsPanel selectionStatsPanel = new SelectionStatsPanel(elm);
        SelectionInformationPanel eventInfoPanel = new SelectionInformationPanel();
        
        
        /*
         *  Add listeners:
         *  CalendarEventListener
         *   -> Course list (ListSelectionListener)
         *   -> Event list (ListSelectionListener)
         *   -> Calendar buttons (ActionListener)
         *  SelectionStatsPanel
         *   -> EventList (ListSelectionListener)
         *  SelectionInformationPanel
         *   -> EventList (ListSelectionListener)
         *  EventListPanel
         *   -> EventListModel (ListDataListener)
         *  EventDataModel
         *   -> CourseList (ListSelectionListener)
         */
        
        CalendarEventListener calEvButLstr = new CalendarEventListener(container, courseList, elm, buttonsPanel.getButtons());
        courseList.addListSelectionListener(calEvButLstr);
        eventList.addListSelectionListener(calEvButLstr);
        buttonsPanel.addListener(calEvButLstr);       
        eventList.addListSelectionListener(selectionStatsPanel);
        eventList.addListSelectionListener(eventInfoPanel);
        elm.addListDataListener(middleEventListPanel);
        courseList.addListSelectionListener(elm);
        
        // Construct user interface with initialized parts
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        west.add(mainMenu);
        west.add(courseListPanel);
        west.add(buttonsPanel);

        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.add(eventInfoPanel);
        east.add(selectionStatsPanel);

        Dimension eastDimension = new Dimension(400, 800);
        Dimension westDimension = new Dimension(300, 800);
        west.setPreferredSize(westDimension);
        east.setPreferredSize(eastDimension);
        middleEventListPanel.setSize(middleEventListPanel.getPreferredSize());

        container.add(west, BorderLayout.WEST);
        container.add(middleEventListPanel, BorderLayout.CENTER);
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
