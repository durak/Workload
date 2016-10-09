package workloadstats.ui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private final EvPropId[] userInputNeededFor = {EvPropId.EVENTNAME, EvPropId.EVENTTYPE, EvPropId.DATE, EvPropId.STARTTIME,
        EvPropId.ENDTIME, EvPropId.STATUS};

    private Map<EvPropId, JTextField> panelFields;

    public NewEventPanel() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Uuden tapahtuman tiedot"));
        setLayout(new GridLayout(6, 2));
        panelFields = new HashMap<>();

        for (EvPropId fieldTitle : userInputNeededFor) {
            panelFields.put(fieldTitle, new JTextField(10));
            add(new JLabel(fieldTitle.getDescr()));
            add(panelFields.get(fieldTitle));
        }
    }

    public String getValue(EvPropId field) {
        return panelFields.get(field).getText();
    }

    public Map getValues() {
        Map<EvPropId, String> values = new HashMap<>();
        for (EvPropId evPropId : userInputNeededFor) {
            values.put(evPropId, panelFields.get(evPropId).getText());
        }
        return values;
    }

}
