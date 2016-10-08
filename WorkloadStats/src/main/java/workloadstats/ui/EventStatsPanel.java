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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

    JRadioButton yes;
    JRadioButton no;
    JRadioButton maybe;

    private EventStatusListener eventStatusListener;

    public EventStatsPanel(MyCalendarControl myCalendarControl) {
        this.myCalendarControl = myCalendarControl;
        selectedEvent = null;
        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnän tiedot"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

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
        pvmSisalto.setText(event.getStartDateString());
        alkuSisalto.setText(event.getStartTime());
        String kestoLongToString = Long.toString(myCalendarControl.getEventDuration(event));
        kestoSisalto.setText(kestoLongToString);
        eventStatusListener.setEvent(selectedEvent);
        resetRadioButtons();
    }

    private void resetRadioButtons() {
        JRadioButton[] rbuttons = {yes, no, maybe};
        for (int i = 0; i < rbuttons.length; i++) {
            JRadioButton reset = rbuttons[i];
            String command = reset.getActionCommand();
            reset.setSelected(selectedEvent.getEventStatus().equals(command));
        }
    }

    /*
      Initialize upper part of panel
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

        otsikkoSisalto = new JLabel("---");
        pvmSisalto = new JLabel("---");
        alkuSisalto = new JLabel("---");
        kestoSisalto = new JLabel("---");
        paikkaSisalto = new JLabel("---");

        upper.add(otsikko);
        upper.add(otsikkoSisalto);
        upper.add(pvm);
        upper.add(pvmSisalto);
        upper.add(alku);
        upper.add(alkuSisalto);
        upper.add(kesto);
        upper.add(kestoSisalto);
//        upper.add(paikka);
//        upper.add(paikkaSisalto);

        return upper;
    }

    /*
     Initialize lower part of panel
     */
//    private JPanel lowerInit() {
//        JPanel lower = new JPanel();
//        JButton paivita = new JButton("Päivitä");
//
//        lower.add(paivita);
//
//        paivita.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String lasnaOloStatus = lasnaSisalto.getText();
//                if (lasnaOloStatus.equals("TENTATIVE")) {
//                    selectedEvent.setStatusTentative();
//                } else if (lasnaOloStatus.equals("CONFIRMED")) {
//                    selectedEvent.setStatusConfirmed();
//                } else if (lasnaOloStatus.equals("CANCELLED")) {
//                    selectedEvent.setStatusCancelled();
//                } else {
//                    JOptionPane.showMessageDialog(lower, "Virhe syötteessä", "Alert", JOptionPane.ERROR_MESSAGE);
//                }
//
//            }
//        });
//        
//        
//
//        return lower;
//    }
    private JPanel lowerInit() {
        JPanel lower = new JPanel();
//        lower.setLayout(new BoxLayout(lower, 2));
        lower.setBorder(javax.swing.BorderFactory.createTitledBorder("Osallistutko / osallistuitko tapahtumaan"));

        yes = new JRadioButton("Kyllä");
        yes.setActionCommand(StatusAc.CONFIRMED.name());
        no = new JRadioButton("Ei");
        no.setActionCommand(StatusAc.CANCELLED.name());
        maybe = new JRadioButton("Harkitsen");
        maybe.setActionCommand(StatusAc.TENTATIVE.name());

        eventStatusListener = new EventStatusListener();
        yes.addActionListener(eventStatusListener);
        no.addActionListener(eventStatusListener);
        maybe.addActionListener(eventStatusListener);

        lower.add(maybe);
        lower.add(yes);
        lower.add(no);

        ButtonGroup eventStatusGroup = new ButtonGroup();
        eventStatusGroup.add(maybe);
        eventStatusGroup.add(yes);
        eventStatusGroup.add(no);

        return lower;
    }

}
