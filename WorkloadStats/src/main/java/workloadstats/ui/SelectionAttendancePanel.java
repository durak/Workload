package workloadstats.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import workloadstats.domain.model.Event;
import workloadstats.utils.Ac;

/**
 * Radiobuttonpanel for event attendance status changes
 * @author Ilkka
 */
public class SelectionAttendancePanel extends JPanel {
    
    private Ac[] buttonIds;
    private List<JRadioButton> buttons;
    private ButtonGroup group;
    private SelectionAttendanceListener esl;

    public SelectionAttendancePanel(Ac[] buttonIds, String title, SelectionAttendanceListener esl) {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        this.buttonIds = buttonIds;
        this.esl = esl;
        this.buttons = new ArrayList<>();
        this.group = new ButtonGroup();
        initComponents();
    }

    private void initComponents() {        
        for (Ac buttonId : buttonIds) {            
            JRadioButton jrb = new JRadioButton(buttonId.getDescr());
            jrb.setActionCommand(buttonId.name());
            jrb.setEnabled(false);
            jrb.addActionListener(esl);
            buttons.add(jrb);
            group.add(jrb);
            this.add(jrb);
        }

    }
    
    public void resetGroup(Event selected) {
        for (JRadioButton button : buttons) {
            if (selected == null) {
                button.setEnabled(false);
            } else {
                button.setSelected(button.getActionCommand().equals(selected.getEventStatus()));
                button.setEnabled(true);
            }
        }
    }
}
