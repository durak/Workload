package workloadstats.ui;

import workloadstats.utils.PropId;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JPanel for user input of new calendar event property information.
 * Uses standardized field identificator PropId Enums which also provide the questions
 * the user needs to answer.
 * @author Ilkka
 */
public class UserInputPanel extends JPanel {

    private PropId[] userInputNeeded;
    private Map<PropId, JTextField> panelFields;
    

    public UserInputPanel(PropId[] userInputNeeded, String title) {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        setLayout(new GridLayout(6, 2));
        this.userInputNeeded = userInputNeeded;
        panelFields = new HashMap<>();

        for (PropId id : userInputNeeded) {
            panelFields.put(id, new JTextField(10));
            add(new JLabel(id.getDescr()));
            add(panelFields.get(id));
        }
    }

    public String getValue(PropId field) {
        return panelFields.get(field).getText();
    }

    /**
     * Return a map of user's answers
     *
     * @return
     */
    public Map getValues() {
        Map<PropId, String> values = new HashMap<>();
        for (PropId id : userInputNeeded) {
            values.put(id, panelFields.get(id).getText());
        }
        return values;
    }
}
