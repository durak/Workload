/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ilkka
 */
public class EventStatsPanel extends JPanel {

    public EventStatsPanel() {
        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kalenterimerkinnän tiedot"));
        GridLayout layout = new GridLayout(5, 2);
        this.setLayout(layout);
        JLabel jl1 = new JLabel("Otsikko");
        JLabel jl2 = new JLabel("otsikko sisalto");
        JLabel jl3 = new JLabel("Pvm");
        JLabel jl4 = new JLabel("Pvm sisalto");
        JLabel jl5 = new JLabel("Kellonaika");
        JLabel jl6 = new JLabel("Kellonaika sisalto");
        JLabel jl7 = new JLabel("Paikka");
        JLabel jl8 = new JLabel("Paikka sisalto");
        JLabel jl9 = new JLabel("Läsnä");
        JLabel jl10 = new JLabel("Läsnä sisalto");
        

        this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        this.add(jl7);
        this.add(jl8);
        this.add(jl9);
        this.add(jl10);

    }
}
