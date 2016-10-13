package workloadstats.ui;

import java.awt.GridLayout;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import workloadstats.utils.DateTools;
import workloadstats.utils.PropId;

/**
 * Generic user input panel that compiles it contents based on an array of
 * PropId enums.
 *
 * @author Ilkka
 */
public class CalendarEventUserInputPanel extends JPanel {

    private PropId[] userInputNeeded;
    private Map<PropId, JComponent> panelFields;

    public CalendarEventUserInputPanel(PropId[] userInputNeeded, String title) {
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
                String[] values = {"CONFIRMED", "TENTATIVE", "CANCELLED"};
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
        }

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

            if (jc.getName().equals(PropId.DATE.name())) {
                JSpinner js = (JSpinner) jc;
                Date d = (Date) js.getValue();
                values.put(id, DateTools.dateFormatter(d));

            } else if (jc.getName().equals(PropId.STARTTIME.name())) {
                JSpinner js = (JSpinner) jc;
                Date d = (Date) js.getValue();
//                values.put(id, dateFormatter(d, "HHmm"));
                values.put(id, DateTools.timeFormatter(d));
            } else if (jc.getName().equals(PropId.ENDTIME.name())) {
                JSpinner js = (JSpinner) jc;
                Date d = (Date) js.getValue();
//                values.put(id, dateFormatter(d, "HHmm"));
                values.put(id, DateTools.timeFormatter(d));
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

    /**
     * Date JSpinner with format as input This is unfortunately very
     * problematic, as it doesn't keep time correctly, When formatted to HH:mm,
     * the spinner jumps to epoch time on user interaction. DateTools class is
     * needed to dodge this problem.
     *
     * @param inputFormat
     * @return
     */
    private JSpinner dateSpinner(String inputFormat) {
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setValue(new Date(0));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, inputFormat);
//        dateEditor.getFormat().setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
        dateSpinner.setEditor(dateEditor);

        dateSpinner.setValue(new Date());
        return dateSpinner;
    }

    /**
     * JSpinner with String array values
     *
     * @param values
     * @return
     */
    private JSpinner stringSpinner(String[] values) {
        JSpinner statusSPinner = new JSpinner(new SpinnerListModel(values));
        if (statusSPinner.getEditor() instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor spinEdit = (JSpinner.DefaultEditor) statusSPinner.getEditor();
            spinEdit.getTextField().setHorizontalAlignment(JTextField.LEFT);
        }
        return statusSPinner;
    }

}
