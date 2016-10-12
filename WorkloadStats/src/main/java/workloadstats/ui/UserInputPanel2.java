package workloadstats.ui;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import workloadstats.utils.PropId;

/**
 *
 * @author Ilkka
 */
public class UserInputPanel2 extends JPanel {

    private PropId[] userInputNeeded;
    private Map<PropId, JComponent> panelFields;

    public UserInputPanel2(PropId[] userInputNeeded, String title) {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        setLayout(new GridLayout(6, 2));
        this.userInputNeeded = userInputNeeded;
        panelFields = new HashMap<>();

        for (PropId id : userInputNeeded) {
            if (id.equals(PropId.DATE)) {
                JSpinner ds = dateSpinner("dd.MM.yyyy");
                ds.setName(id.name());
                panelFields.put(id, ds);
            } else if (id.equals(PropId.STARTTIME)) {
                JSpinner ds = dateSpinner("HH:mm");
                ds.setName(id.name());
                panelFields.put(id, ds);
            } else if (id.equals(PropId.ENDTIME)) {
                JSpinner ds = dateSpinner("HH:mm");
                ds.setName(id.name());
                panelFields.put(id, ds);
            } else if (id.equals(PropId.STATUS)) {
                String[] values = {"CONFIRMED", "CANCELLED"};
                JSpinner ss = stringSpinner(values);
                ss.setName(id.name());
                panelFields.put(id, ss);
            } else if (id.equals(PropId.EVENTTYPE)) {
                String[] values = {"PERSONAL", "EXERCISE", "LECTURE", "TEAMWORK", "EXAM"};
                JSpinner ss = stringSpinner(values);
                ss.setName(id.name());
                panelFields.put(id, ss);
            } else {
                JTextField jt = new JTextField(10);
                jt.setName(id.name());
                panelFields.put(id, jt);
            }
            add(new JLabel(id.getDescr()));
            add(panelFields.get(id));
            System.out.println(id + " " + panelFields.get(id));

        }

    }

    public String getValue(PropId field) {
//        return panelFields.get(field).getText();
        return "";
    }

    /**
     * Return a map of user's answers
     *
     * @return
     */
    public Map getValues() {
        Map<PropId, String> values = new HashMap<>();
        for (PropId id : userInputNeeded) {
            JComponent jc = panelFields.get(id);
            System.out.println(jc.getName());
            if (jc.getName().equals(PropId.DATE.name())) {
                JSpinner js = (JSpinner) jc;
                Date d = (Date) js.getValue();
                values.put(id, dateFormatter(d, "yyyyMMdd"));
            } else if (jc.getName().equals(PropId.STARTTIME.name())) {
                JSpinner js = (JSpinner) jc;
                Date d = (Date) js.getValue();
                System.out.println(d.toString());
                values.put(id, dateFormatter(d, "HHmm"));
            } else if (jc.getName().equals(PropId.ENDTIME.name())) {
                JSpinner js = (JSpinner) jc;
                Date d = (Date) js.getValue();
                System.out.println(d.toString());
                values.put(id, dateFormatter(d, "HHmm"));
            } else if (jc.getName().equals(PropId.STATUS.name())) {
                JSpinner js = (JSpinner) jc;
                String s = (String) js.getValue();
                values.put(id, s);
            } else if (jc.getName().equals(PropId.EVENTTYPE.name())) {
                JSpinner js = (JSpinner) jc;
                String s = (String) js.getValue();
                values.put(id, s);
            } else {
                JTextField jt = (JTextField) jc;
                values.put(id, jt.getText());
            }

        }
        return values;
    }

    private JSpinner dateSpinner(String inputFormat) {
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, inputFormat);
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());
        return dateSpinner;
    }

    private String dateFormatter(Date date, String format) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
//        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormatter.format(date);
    }

    private JSpinner stringSpinner(String[] values) {
        JSpinner statusSPinner = new JSpinner(new SpinnerListModel(values));
        if (statusSPinner.getEditor() instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor spinEdit = (JSpinner.DefaultEditor) statusSPinner.getEditor();
            spinEdit.getTextField().setHorizontalAlignment(JTextField.LEFT);
        }
        return statusSPinner;
    }

}
