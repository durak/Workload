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
import workloadstats.ui.refactor.CalendarEventButtonListener;
import workloadstats.ui.refactor.CourseListPanel;
import workloadstats.ui.refactor.EventListModel;
import workloadstats.ui.refactor.EventListPanel;
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
        eventList.setName("eventlist");

        Ac[] menuButtons = {Ac.NEWCALENDAR, Ac.LOADCALENDAR, Ac.IMPORTCALENDAR, Ac.SAVECALENDAR};
        JPanel mainMenu = new ButtonsPanel(menuButtons, "Main menu");
        mainMenu.setMaximumSize(new Dimension(400, 20));

        Ac[] eventButtons = {Ac.NEWCOURSE, Ac.DELETECOURSE, Ac.NEWEVENT, Ac.DELETE_EVENT};
        ButtonsPanel buttonsPanel = new ButtonsPanel(eventButtons, "Toiminnot");
        buttonsPanel.setMaximumSize(new Dimension(400, 20));

        CalendarEventButtonListener calEvButLstr = new CalendarEventButtonListener(container, courseList, elm, buttonsPanel.getButtons());
        courseList.addListSelectionListener(calEvButLstr);
        eventList.addListSelectionListener(calEvButLstr);
        buttonsPanel.addListener(calEvButLstr);

        SelectionStatsPanel selectionStatsPanel = new SelectionStatsPanel(elm);

        eventList.addListSelectionListener(selectionStatsPanel);
        EventInformationPanel eventInfoPanel = new EventInformationPanel();

        EventListPanel middleEventListPanel = new EventListPanel(eventList);

        CourseListPanel courseListPanel = new CourseListPanel(courseList, clm, elm);

        eventList.addListSelectionListener(eventInfoPanel);
        elm.addListDataListener(middleEventListPanel);

        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        west.add(mainMenu);
        west.add(courseListPanel);
        west.add(buttonsPanel);

        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.add(eventInfoPanel);
        east.add(selectionStatsPanel);

//        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        GridBagConstraints gbc = new GridBagConstraints();
//        container.setLayout(new GridBagLayout());

//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 0.1;
//        gbc.weighty = 0.5;        
//        gbc.fill = GridBagConstraints.BOTH;
//        container.add(west, gbc);
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        gbc.weightx = 0.5;
//        gbc.weighty = 0.5;
//        gbc.fill = GridBagConstraints.BOTH;
//        container.add(eventListPanel, gbc);
//        gbc.gridx = 2;
//        gbc.gridy = 0;
//
//        gbc.fill = GridBagConstraints.BOTH;
//        container.add(east, gbc);
        Dimension eastDimension = new Dimension(500, 800);
        east.setMinimumSize(eastDimension);
//        east.setMaximumSize(eastDimension);
//        east.setSize(eastDimension);
//        east.setPreferredSize(east.getPreferredSize());
        east.setPreferredSize(eastDimension);
//        Dimension eventListDimension = new Dimension(400, 400);
//        middleEventListPanel.setMaximumSize(eventListDimension);
//        middleEventListPanel.setMinimumSize(eventListDimension);
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
