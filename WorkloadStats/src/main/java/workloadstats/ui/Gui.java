package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import workloadstats.calendardata.MyCalendarControl;
import workloadstats.utils.Ac;

/**
 * Main GUI builder
 * @author Ilkka
 */
public class Gui implements Runnable {

    private MyCalendarControl myCalendarControl;
    private JFrame frame;

    public Gui(MyCalendarControl myCalendarControl) {
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
        CourseListModel courseListModel = new CourseListModel(myCalendarControl.getCourses());
        EventListModel eventListModel = new EventListModel(courseListModel);

        // Initialize Course and Event lists with Data models
        // datalistener not needed in current setup
        // clm.addListDataListener(elm);
        JList courseList = new JList(courseListModel);
        courseList.setName("courselist");
        JList eventList = new JList(eventListModel);
        eventList.setName("eventlist");

        // Initialize CourseListPanel and EventListPanel
        CourseListPanel courseListPanel = new CourseListPanel(courseList);
        EventListPanel middleEventListPanel = new EventListPanel(eventList);

        // Initialize Main menu
        Ac[] menuButtons = {Ac.NEWCALENDAR, Ac.LOADCALENDAR, Ac.IMPORTCALENDAR, Ac.SAVECALENDAR};
        ButtonsPanel mainMenu = new ButtonsPanel(menuButtons, "Main menu");
        mainMenu.setMaximumSize(new Dimension(400, 20));

        // Initialize Calendar buttons
        Ac[] eventButtons = {Ac.NEWCOURSE, Ac.DELETECOURSE, Ac.NEWEVENT, Ac.DELETE_EVENT};
        ButtonsPanel calendarActionPanel = new ButtonsPanel(eventButtons, "Toiminnot");
        calendarActionPanel.setMaximumSize(new Dimension(400, 20));

        // Initialize Selection Statistics & Selection Information panels
        SelectionStatsPanel selectionStatsPanel = new SelectionStatsPanel(eventListModel);
        SelectionInformationPanel selectionInfoPanel = new SelectionInformationPanel();

        // Initialize Calendar and Main Menu listeners 
        CalendarActionListener calActListener = new CalendarActionListener(container, courseList, eventListModel, calendarActionPanel.getButtons());
        MainMenuActionListener mainMenuLstr = new MainMenuActionListener(container, myCalendarControl, courseListModel, courseList);

        /*
         *  Add Listeners:
         *  CalendarActionListener
         *   -> Course list (ListSelectionListener)
         *   -> Event list (ListSelectionListener)
         *   -> Calendar buttons (ActionListener)
         */
        courseList.addListSelectionListener(calActListener);
        eventList.addListSelectionListener(calActListener);
        calendarActionPanel.addListener(calActListener);

        /*
         *  MainMenuActionListener
         *   -> mainMenu         
         */
        mainMenu.addListener(mainMenuLstr);
        /*
         *  SelectionStatsPanel
         *   -> EventList (ListSelectionListener)
         */
        eventList.addListSelectionListener(selectionStatsPanel);
        /*
         *  SelectionInformationPanel
         *   -> EventList (ListSelectionListener)
         */
        eventList.addListSelectionListener(selectionInfoPanel);

        /*
         *  EventListPanel
         *   -> EventListModel (ListDataListener)
         */
        eventListModel.addListDataListener(middleEventListPanel);
        /*
         *  EventDataModel
         *   -> CourseList (ListSelectionListener)
         */
        courseList.addListSelectionListener(eventListModel);

        // Construct user interface with initialized parts
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        west.add(mainMenu);
        west.add(courseListPanel);
        west.add(calendarActionPanel);

        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.add(selectionInfoPanel);
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

}
