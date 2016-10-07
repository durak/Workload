/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Personal;

/**
 * JPanel container for JList of events
 *
 * @author Ilkka
 */
public class EventListPanel extends JPanel {

    private MyCalendarControl myCalendarControl;
    private EventStatsPanel eventStatsPanel;
    private Course selectedCourse;
    private Event selectedEvent;

    private List<Event> events;
    private JList list;
    DefaultListModel model;

    public EventListPanel(List<Event> events, EventStatsPanel eventStatsPanel) {
        this.eventStatsPanel = eventStatsPanel;
        this.events = events;
        initPanelComponents();
    }

    public EventListPanel(MyCalendarControl myCalendarControl, Course course, EventStatsPanel eventStatsPanel) {
        this.myCalendarControl = myCalendarControl;
        this.selectedCourse = course;
        events = course.getAllEvents();
        this.eventStatsPanel = eventStatsPanel;
        initPanelComponents();
    }

    public void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnät"));

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        selectedEvent = null;
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane scrollPane = new JScrollPane(list);
        list.setCellRenderer(new EventRenderer());

        ////////////////////////
        ListSelectionModel selectionModel = new SingleSelectionListModel() {
            public void updateSingleSelection(int oldIndex, int newIndex) {
                ListModel m = list.getModel();
                selectedEvent = (Event) m.getElementAt(newIndex);
                eventStatsPanel.setEvent(selectedCourse, selectedEvent);

                System.out.println(selectedEvent.getEventName());
            }
        };
        list.setSelectionModel(selectionModel);

        ////////////////////////
        add(scrollPane);

        ////////////////////////////////////////////////////////////////////////
        JButton newEventButton = new JButton("Uusi tapahtuma");

        newEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                VEvent newVev = new VEvent();
//                Course newCou = new Course(newVev);
//                model.addElement(newCou);

                String summary = JOptionPane.showInputDialog("Anna tapahtumalle nimi");
                String date = JOptionPane.showInputDialog("Anna tapahtumalle päivämäärä (muoto VVVVKKPP");
                String startTime = JOptionPane.showInputDialog("Anna tapahtumalle aloitusaika (muoto HHMMSS");
                String endTime = JOptionPane.showInputDialog("Anna tapahtumalle lopetusaika (muoto HHMMSS");
                String startDateTime = date + "T" + startTime;
                String endDateTime = date + "T" + endTime;

                try {

                    Personal newPersonal = myCalendarControl.buildNewPersonal(summary, startDateTime, endDateTime, selectedCourse);

//                    model.addElement(newPersonal);
                    reset(selectedCourse);
//                    courses.add(newCourse);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Virhe syötteessä", "ALARM", JOptionPane.ERROR_MESSAGE);

                    Logger.getLogger(CourseListPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        add(newEventButton);
    }

    public void reset(Course course) {
        this.selectedCourse = course;
        events = course.getAllEvents();
        Event[] ev = events.toArray(new Event[events.size()]);
        list.setListData(ev);
//        list.setSelectedIndex(0);
    }

}
