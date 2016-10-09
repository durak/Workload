package workloadstats.ui;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JPanel for user input of new calendar event information
 *
 * @author Ilkka
 */
public class NewEventPanel extends JPanel {

    private EvPropId[] userInputNeeded;

    private Map<EvPropId, JTextField> panelFields;

    public NewEventPanel(EvPropId[] userInputNeeded, String title) {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        setLayout(new GridLayout(6, 2));
        this.userInputNeeded = userInputNeeded;
        panelFields = new HashMap<>();

        for (EvPropId evPropId : userInputNeeded) {
            panelFields.put(evPropId, new JTextField(10));
            add(new JLabel(evPropId.getDescr()));
            add(panelFields.get(evPropId));
        }
    }

    public String getValue(EvPropId field) {
        return panelFields.get(field).getText();
    }

    /**
     * Return a map of user's answers
     *
     * @return
     */
    public Map getValues() {
        Map<EvPropId, String> values = new HashMap<>();
        for (EvPropId evPropId : userInputNeeded) {
            values.put(evPropId, panelFields.get(evPropId).getText());
        }
        return values;
    }

}
