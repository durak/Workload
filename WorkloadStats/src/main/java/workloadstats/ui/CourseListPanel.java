/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.fortuna.ical4j.model.component.VEvent;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;

/**
 * JPanel container for a JList of courses
 *
 * @author Ilkka
 */
public class CourseListPanel extends JPanel {

    private MyCalendarControl myCalendarControl;
    private EventListPanel eventListPanel;
    private List<Course> courses;
    private JList list;
    //
    DefaultListModel model;
    ListSelectionModel selectionModel;
    //            
    Course selectedCourse;

    public CourseListPanel(MyCalendarControl myCalendarControl, EventListPanel eventListPanel) {
        this.myCalendarControl = myCalendarControl;
        this.eventListPanel = eventListPanel;
        this.courses = myCalendarControl.getCourses();
        initPanelComponents();
    }

    public void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kurssilista"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        selectedCourse = null;
        model = new DefaultListModel();
        list = new JList(model);
//        list = new JList();
        JScrollPane scrollPane = new JScrollPane(list);
        list.setCellRenderer(new CourseListRenderer());
//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ////////////////////////////////
        ListSelectionModel selectionModel = new SingleSelectionListModel() {
            public void updateSingleSelection(int oldIndex, int newIndex) {
                ListModel m = list.getModel();
                selectedCourse = (Course) m.getElementAt(newIndex);
                eventListPanel.reset(selectedCourse);
            }
        };
        list.setSelectionModel(selectionModel);

        ////////////////////////////////
        for (Course course : courses) {
            model.addElement(course);
        }

        /*
        
         */
//        list.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//
//                boolean isAdjusting = e.getValueIsAdjusting();
//
//                if (!isAdjusting) {
//                    selectedCourse = (Course) list.getSelectedValue();
//                    for (Event event : selectedCourse.getAllEvents()) {
//                        System.out.println(event.getEventName() + " " + event.getStartDate());
//
//                    }
//                    eventListPanel.reset(selectedCourse);
//                }
//
//            }
//        });
        JButton newCourseButton = new JButton("Uusi kurssi");
        JButton deleteCourseButton = new JButton("Poista kurssi");

        newCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                VEvent newVev = new VEvent();
//                Course newCou = new Course(newVev);
//                model.addElement(newCou);
                System.out.println("test");
                String summary = JOptionPane.showInputDialog("Anna kurssille nimi");
                String date = JOptionPane.showInputDialog("Anna kurssille aloituspäivämäärä (muoto VVVVKKPP");
                date = date + "T000000";

                try {
                    Course newCourse = myCalendarControl.buildNewCourse(summary, date, date);
                    model.addElement(newCourse);
                    courses.add(newCourse);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "alert", "Virhe syötteessä", JOptionPane.ERROR_MESSAGE);

                    Logger.getLogger(CourseListPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        deleteCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.getSize() > 0) {
                    courses.remove(model.getElementAt(list.getSelectedIndex()));
//                    courses.remove(model.getElementAt(0));
                    model.removeElementAt(list.getSelectedIndex());
                    System.out.println("courses size" + courses.size());
                    System.out.println("model sieze " + model.size());
                }
            }
        });

        add(scrollPane, BorderLayout.NORTH);
        add(newCourseButton, BorderLayout.SOUTH);
        add(deleteCourseButton, BorderLayout.SOUTH);

    }

}
