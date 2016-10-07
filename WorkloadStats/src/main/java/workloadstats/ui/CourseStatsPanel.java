/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JPanel for stats of the chosen course
 *
 * @author Ilkka
 */
public class CourseStatsPanel extends JPanel {

    public CourseStatsPanel() {
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

}
