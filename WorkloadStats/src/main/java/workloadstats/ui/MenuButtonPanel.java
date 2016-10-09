package workloadstats.ui;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * JPanel container for main menu buttons
 *
 * @author Ilkka
 */
public class MenuButtonPanel extends JPanel {

    public MenuButtonPanel() {
        initPanelComponents();
    }

    public void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Main menu"));
        GridLayout gridLayout = new GridLayout(2, 2, 10, 10);
        this.setLayout(gridLayout);
        
        JButton newCalendar = new JButton("Uusi kalenteri");
        JButton importEvents = new JButton("Tuo merkintöjä");
        JButton loadCalendar = new JButton("Lataa kalenteri");
        JButton saveCalendar = new JButton("Tallenna kalenteri");        
        
        this.add(newCalendar);
        this.add(importEvents);
        this.add(loadCalendar);
        this.add(saveCalendar);
        
    }

}
