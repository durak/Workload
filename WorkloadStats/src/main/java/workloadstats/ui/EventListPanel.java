/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;

/**
 * JPanel container for JList of events
 *
 * @author Ilkka
 */
public class EventListPanel extends JPanel {
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

    public EventListPanel(Course course, EventStatsPanel eventStatsPanel) {
        this.selectedCourse = course;
        events = course.getAllEvents();
        initPanelComponents();
    }

    public void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnät"));

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        selectedEvent = null;
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane pane = new JScrollPane(list);
        list.setCellRenderer(new EventRenderer());

//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ////////////////////////
        ListSelectionModel selectionModel = new SingleSelectionListModel() {
            public void updateSingleSelection(int oldIndex, int newIndex) {
                ListModel m = list.getModel();
                selectedEvent = (Event) m.getElementAt(newIndex);
                System.out.println(selectedEvent.getEventName());
                eventStatsPanel.setEvent(selectedCourse, selectedEvent);
//                                    System.out.println(selectedEvent.getEventName());
            }
        };
        list.setSelectionModel(selectionModel);

        ////////////////////////

        add(pane, BorderLayout.NORTH);
    }

    public void reset(Course course) {
        this.selectedCourse = course;
        events = course.getAllEvents();
        Event[] ev = events.toArray(new Event[events.size()]);
        list.setListData(ev);
//        list.setSelectedIndex(0);
    }
    
    
}


