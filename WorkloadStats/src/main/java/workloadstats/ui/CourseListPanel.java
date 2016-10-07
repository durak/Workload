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
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.fortuna.ical4j.model.component.VEvent;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;

/**
 * JPanel container for a JList of courses
 *
 * @author Ilkka
 */
public class CourseListPanel extends JPanel {
    private EventListPanel eventListPanel;
    private List<Course> courses;
    private JList list;
    DefaultListModel model;
    Course selectedCourse;

    public CourseListPanel(List<Course> courses, EventListPanel eventListPanel) {
        this.eventListPanel = eventListPanel;
        this.courses = courses;
        initPanelComponents();
    }

    public void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kurssilista"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        selectedCourse = null;
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane scrollPane = new JScrollPane(list);
        list.setCellRenderer(new CourseListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (Course course : courses) {
            model.addElement(course);
        }

        /*
        
         */
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                boolean isAdjusting = e.getValueIsAdjusting();

                if (!isAdjusting) {
                    selectedCourse = (Course) list.getSelectedValue();
                    for (Event event : selectedCourse.getAllEvents()) {
                        System.out.println(event.getEventName() + " " + event.getStartDate());

                    }
                    eventListPanel.reset(selectedCourse);
                }

            }
        });

        JButton addButton = new JButton("Add Element");
        JButton removeButton = new JButton("Remove Element");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                VEvent newVev = new VEvent();
//                Course newCou = new Course(newVev);
//                model.addElement(newCou);
                System.out.println("test");
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.getSize() > 0) {
                    model.removeElementAt(0);
                }
            }
        });

        add(scrollPane, BorderLayout.NORTH);
        add(addButton, BorderLayout.SOUTH);
//        add(removeButton, BorderLayout.EAST);

    }

}
