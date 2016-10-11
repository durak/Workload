package workloadstats.ui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import workloadstats.utils.Ac;

/**
 * Create a panel of buttons with array of Ac enums.
 * Button text if Ac.value and actionCommand Ac.name
 * @author Ilkka
 */
public class ButtonsPanel extends JPanel {
    
    private Ac[] buttonIds;
    private List<JButton> buttons;

    public ButtonsPanel(Ac[] buttonIds, String title) {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        this.buttonIds = buttonIds;        
        buttons = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {                
        this.setLayout(new GridLayout(2, 2, 10, 10));
        
        for (Ac buttonId : buttonIds) {
            JButton jb = new JButton(buttonId.getDescr());
            jb.setActionCommand(buttonId.name());
            buttons.add(jb);
            this.add(jb);
        }
    }
    
    public List<JButton> getButtons() {
        return buttons;
    }
    
    public void addListener(ActionListener al) {
        for (JButton button : buttons) {
            button.addActionListener(al);
        }
    }
    

}
