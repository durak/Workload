/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Event;

/**
 *
 * @author Ilkka
 */
public class EventStatsPanel extends JPanel {

    private MyCalendarControl myCalendarControl;
    private Event selectedEvent;

    private JTextField otsikkoSisalto2;
    private JLabel otsikkoSisalto;
    private JLabel pvmSisalto;
    private JLabel alkuSisalto;
    private JLabel kestoSisalto;
    private JLabel paikkaSisalto;
    private JLabel lasnaSisalto;

    public EventStatsPanel(MyCalendarControl myCalendarControl) {
        this.myCalendarControl = myCalendarControl;
        selectedEvent = null;
        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnän tiedot"));

        // Upper panel for eventStats
//        JPanel upper = new JPanel();
//        GridLayout layout = new GridLayout(6, 2);
//        upper.setLayout(layout);
        this.add(upperInit(), BorderLayout.CENTER);

    }

    /**
     * Set parameter's event as the current event and push information to upper JPanel
     * @param event 
     */
    public void setEvent(Event event) {
        otsikkoSisalto.setText(event.getEventName());
        otsikkoSisalto2.setText(event.getEventName());
        pvmSisalto.setText(event.getStartDateString());
        alkuSisalto.setText(event.getStartTime());
        String kestoLongToString = Long.toString(myCalendarControl.getEventDuration(event));
        kestoSisalto.setText(kestoLongToString);
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

        otsikkoSisalto2 = new JTextField("sisalto");
        otsikkoSisalto = new JLabel("otsikko sisalto");
        pvmSisalto = new JLabel("Pvm sisalto");
        alkuSisalto = new JLabel("alku sisalto");
        kestoSisalto = new JLabel("kesto sisalto");
        paikkaSisalto = new JLabel("Paikka sisalto");
        lasnaSisalto = new JLabel("Läsnä sisalto");

        upper.add(otsikko);
        upper.add(otsikkoSisalto2);
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

}
