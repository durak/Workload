package workloadstats.ui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import workloadstats.calendardata.FieldTitle;

/**
 * JPanel for user input of new calendar event information
 *
 * @author Ilkka
 */
public class NewEventPanel extends JPanel {

    private Map<FieldTitle, JTextField> panelFields;

    public NewEventPanel() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Uuden tapahtuman tiedot"));
        setLayout(new GridLayout(4, 2));
        panelFields = new HashMap<>();

        List<FieldTitle> userInputNeededFor = new ArrayList<>();
        userInputNeededFor.add(FieldTitle.EVENTNAME);
        userInputNeededFor.add(FieldTitle.DATE);
        userInputNeededFor.add(FieldTitle.STARTTIME);
        userInputNeededFor.add(FieldTitle.ENDTIME);

        for (FieldTitle fieldTitle : userInputNeededFor) {
            panelFields.put(fieldTitle, new JTextField(10));
            add(new JLabel(fieldTitle.getDescr()));
            add(panelFields.get(fieldTitle));
        }

    }

    public String getValue(FieldTitle field) {
        return panelFields.get(field).getText();
    }

}
