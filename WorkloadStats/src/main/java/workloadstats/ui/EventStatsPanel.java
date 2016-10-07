/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.Personal;

/**
 *
 * @author Ilkka
 */
public class EventStatsPanel extends JPanel {

    private MyCalendarControl myCalendarControl;
    private Course selectedCourse;
    private Event selectedEvent;

//    private JTextField otsikkoSisalto2;
    private JLabel otsikkoSisalto;
    private JLabel pvmSisalto;
    private JLabel alkuSisalto;
    private JLabel kestoSisalto;
    private JLabel paikkaSisalto;
    private JTextField lasnaSisalto;

    public EventStatsPanel(MyCalendarControl myCalendarControl) {
        this.myCalendarControl = myCalendarControl;
        selectedEvent = null;
        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnän tiedot"));
//        GridLayout layout = new GridLayout(1, 1);
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        // Upper panel for eventStats
//        JPanel upper = new JPanel();
//        GridLayout layout = new GridLayout(6, 2);
//        upper.setLayout(layout);
        this.add(upperInit());
        this.add(lowerInit());

    }

    /**
     * Set parameter's event as the current event and push information to upper
     * JPanel
     *
     * @param event
     */
    public void setEvent(Course course, Event event) {
        selectedCourse = course;
        selectedEvent = event;
        otsikkoSisalto.setText(event.getEventName());
//        otsikkoSisalto2.setText(event.getEventName());
        pvmSisalto.setText(event.getStartDateString());
        alkuSisalto.setText(event.getStartTime());
        String kestoLongToString = Long.toString(myCalendarControl.getEventDuration(event));
        kestoSisalto.setText(kestoLongToString);
        lasnaSisalto.setText(event.getEventStatus());

    }

    /*
      Initialize upper JPanel
     */
    private JPanel upperInit() {
        JPanel upper = new JPanel();
        GridLayout layout = new GridLayout(6, 2);
        upper.setLayout(layout);

        JLabel otsikko = new JLabel("Otsikko");
        JLabel pvm = new JLabel("Pvm");
        JLabel alku = new JLabel("Alku");
        JLabel kesto = new JLabel("Kesto");
        JLabel paikka = new JLabel("Paikka");
        JLabel lasna = new JLabel("Läsnä");

//        otsikkoSisalto2 = new JTextField("sisalto");
        otsikkoSisalto = new JLabel("otsikko sisalto");
        pvmSisalto = new JLabel("Pvm sisalto");
        alkuSisalto = new JLabel("alku sisalto");
        kestoSisalto = new JLabel("kesto sisalto");
        paikkaSisalto = new JLabel("Paikka sisalto");
        lasnaSisalto = new JTextField("Läsnä sisalto");

        upper.add(otsikko);
//        upper.add(otsikkoSisalto2);
        upper.add(otsikkoSisalto);
        upper.add(pvm);
        upper.add(pvmSisalto);
        upper.add(alku);
        upper.add(alkuSisalto);
        upper.add(kesto);
        upper.add(kestoSisalto);
//        upper.add(paikka);
//        upper.add(paikkaSisalto);
        upper.add(lasna);
        upper.add(lasnaSisalto);

        return upper;
    }

    private JPanel lowerInit() {
        JPanel lower = new JPanel();
        JButton paivita = new JButton("Päivitä");
        JButton uusiEvent = new JButton("Uusi tapahtuma");
        lower.add(paivita);
        lower.add(uusiEvent);

        ////////////////////////////////////////////////////////////////////////
        paivita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String lasnaOloStatus = lasnaSisalto.getText();
                if (lasnaOloStatus.equals("TENTATIVE")) {
                    selectedEvent.setStatusTentative();
                } else if (lasnaOloStatus.equals("CONFIRMED")) {
                    selectedEvent.setStatusConfirmed();
                } else if (lasnaOloStatus.equals("CANCELLED")) {
                    selectedEvent.setStatusCancelled();
                } else {
                    JOptionPane.showMessageDialog(null, "alert", "Virhe syötteessä", JOptionPane.ERROR_MESSAGE);
                }
//                if (model.getSize() > 0) {
//                    courses.remove(model.getElementAt(list.getSelectedIndex()));
//                    courses.remove(model.getElementAt(0));
//                    model.removeElementAt(list.getSelectedIndex());
//                    System.out.println("courses size" + courses.size());
//                    System.out.println("model sieze " + model.size());
//                }
            }
        });

        ////////////////////////////////////////////////////////////////////////
        uusiEvent.addActionListener(new ActionListener() {
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
                    System.out.println(selectedCourse.getAllEvents());
                    Personal newPersonal = myCalendarControl.buildNewPersonal(summary, startDateTime, endDateTime, selectedCourse);
                    System.out.println("entäs nyt");
                    System.out.println(selectedCourse.getAllEvents());
//                            model.addElement(newCourse);
//                    courses.add(newCourse);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "alert", "Virhe syötteessä", JOptionPane.ERROR_MESSAGE);

                    Logger.getLogger(CourseListPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        ////////////////////////////////////////////////////////////////////////
        return lower;
    }

}
