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
import workloadstats.domain.model.Event;

/**
 *
 * @author Ilkka
 */
public class EventStatsPanel extends JPanel {

    private Event selectedEvent;
        
    private JTextField otsikkoSisalto2;
    private JLabel otsikkoSisalto;
    private JLabel pvmSisalto;
    private JLabel kellonaikaSisalto;
    private JLabel paikkaSisalto;
    private JLabel lasnaSisalto;

    public EventStatsPanel() {
        selectedEvent = null;
        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnän tiedot"));
        
        // Upper panel for eventStats
        JPanel upper = new JPanel();
        GridLayout layout = new GridLayout(5, 2);
        upper.setLayout(layout);
        this.add(upper, BorderLayout.CENTER);

        JLabel otsikko = new JLabel("Otsikko");
        JLabel pvm = new JLabel("Pvm");
        JLabel kellonaika = new JLabel("Kellonaika");
        JLabel paikka = new JLabel("Paikka");
        JLabel lasna = new JLabel("Läsnä");
        
        otsikkoSisalto2 = new JTextField("sisalto");
        otsikkoSisalto = new JLabel("otsikko sisalto");
        pvmSisalto = new JLabel("Pvm sisalto");
        kellonaikaSisalto = new JLabel("Kellonaika sisalto");
        paikkaSisalto = new JLabel("Paikka sisalto");
        lasnaSisalto = new JLabel("Läsnä sisalto");

        upper.add(otsikko);
//        upper.add(otsikkoSisalto);
        upper.add(otsikkoSisalto2);
        upper.add(pvm);
        upper.add(pvmSisalto);
        upper.add(kellonaika);
        upper.add(kellonaikaSisalto);
        upper.add(paikka);
        upper.add(paikkaSisalto);
        upper.add(lasna);
        upper.add(lasnaSisalto);

    }

    public void setEvent(Event event) {
        otsikkoSisalto.setText(event.getEventName());
        otsikkoSisalto2.setText(event.getEventName());
    }
}
