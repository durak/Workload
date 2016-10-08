package workloadstats.ui;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JPanel for user input of new course information
 * @author Ilkka
 */
public class NewCoursePanel extends JPanel {

    private JTextField name;
    private JTextField startDate;
    
    public NewCoursePanel() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Uuden kurssin tiedot"));
        setLayout(new GridLayout(2, 2));
        JLabel n = new JLabel("Kurssin nimi:");
        JLabel d = new JLabel("Aloituspäivä:");
        name = new JTextField(10);
        startDate = new JTextField(10);    
        
        add(n);
        add(name);
        add(d);
        add(startDate);
        
    }
    
    public String getNameText() {
        return name.getText();
    }
    
    public String getStartDateText() {
        return startDate.getText();
    }

}
