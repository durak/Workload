/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Show statistics from user's selected events
 *
 * @author Ilkka
 */
public class SelectionStatsPanel extends JPanel implements ListSelectionListener {

    public SelectionStatsPanel() {
        initPanelComponents();
    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Kurssistatsit"));
//        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        GridLayout layout = new GridLayout(3, 3);
        this.setLayout(layout);
        JLabel jl1 = new JLabel("jl1eeeeeeeeeeeeeeeeeeeeeeeeee");
        JLabel jl2 = new JLabel("jl1");
        JLabel jl3 = new JLabel("jl1");
        JLabel jl4 = new JLabel("jl1");
        JLabel jl5 = new JLabel("jl1");
        JLabel jl6 = new JLabel("jl1");
        
        this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        
        
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
