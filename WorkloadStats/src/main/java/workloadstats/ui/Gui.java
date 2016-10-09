/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

        initFramePaneComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void initFramePaneComponents(Container container) {
//        GridLayout layout = new GridLayout(2, 3);
//        container.setLayout(layout);
        
        MenuButtonPanel menuButtonPanel = new MenuButtonPanel();
        CourseStatsPanel courseStatsPanel = new CourseStatsPanel();
//        EventStatsPanel eventStatsPanel = new EventStatsPanel(myCalendarControl);
//        EventListPanel eventListPanel = new EventListPanel(myCalendarControl, myCalendarControl.getCourses().get(0), eventStatsPanel);
//        CourseListPanel courseListPanel = new CourseListPanel(myCalendarControl, eventListPanel);
        
        EventStatsPanel2 eventStatsPanel = new EventStatsPanel2();
        CourseListModel clm = new CourseListModel(myCalendarControl.getCourses());
        EventListModel elm = new EventListModel(clm);
        clm.addListDataListener(elm);
        
        EventListPanel2 eventListPanel = new EventListPanel2(myCalendarControl, elm, eventStatsPanel);
        
        CourseListPanel2 courseListPanel = new CourseListPanel2(myCalendarControl, eventListPanel, clm, elm);
        
        
        
        JPanel west = new JPanel(new GridLayout(2, 1));
        west.add(menuButtonPanel);
        west.add(courseListPanel);
        
        JPanel east = new JPanel(new GridLayout(2,1));
        east.add(eventStatsPanel);
        east.add(courseStatsPanel);
        
//        container.add(menuButtonPanel, BorderLayout.NORTH);
        container.add(west, BorderLayout.WEST);
        container.add(eventListPanel, BorderLayout.CENTER);
        container.add(east, BorderLayout.EAST);
        
//        JLabel teksti = new JLabel("Tekstikenttä!");
//        container.add(teksti);
//        container.add(new JButton("Pohjoinen (North)"), BorderLayout.NORTH);
//        container.add(new JButton("Itä (East)"), BorderLayout.EAST);
//        container.add(new JButton("Etelä (South)"), BorderLayout.SOUTH);
//        container.add(new JButton("Länsi (West)"), BorderLayout.WEST);

    }

    public JFrame getFrame() {
        return frame;
    }
    
    //testing
    public List<Course> testaustaVartenKurssitUlosGuista() {
        return this.myCalendarControl.getCourses();
    }

}
