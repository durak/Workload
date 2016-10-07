/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.GridLayout;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import workloadstats.domain.model.Course;

/**
 *
 * @author Ilkka
 */
public class CoursePanelUnused extends JPanel {
    private JList courseList;
    
    public CoursePanelUnused() {

        super(new GridLayout(1, 3));
        luoKomponentit();
    }

    private void luoKomponentit() {
        courseList.setModel(new AbstractListModel<Course>() {
            @Override
            public int getSize() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Course getElementAt(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        add(new JButton("Suorita"));
        add(new JButton("Testaa"));
        add(new JButton("Lähetä"));
    }

}
