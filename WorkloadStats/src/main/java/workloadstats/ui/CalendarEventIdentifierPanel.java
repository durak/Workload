package workloadstats.ui;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import workloadstats.utils.EventType;
import workloadstats.ui.utils.TransferFocus;

/**
 * User input panel for identifying new imports to calendar Complicated process:
 * user inputs new name for a "main event" (preferably lectures) of the course,
 * and this user input needs to be shown in a JSpinner for the user as they
 * match the other events of this course to this name. Giant size for now, will
 * be refactored in the future.
 *
 * @author Ilkka
 */
public class CalendarEventIdentifierPanel extends JPanel {

    private final EventType[] availableEventTypes = {EventType.LECTURE, EventType.EXERCISE, EventType.TRASH, EventType.PERSONAL, EventType.TEAMWORK};

    private Set<String> summaries;
    private Map<String, JTextArea> userInputOnSummaries;
    private Map<String, JTextArea> userMatchesOnSummaries;
    private Map<String, JSpinner> userInputOnEventType;
    private Map<String, JCheckBox> userInputOnCourse;
    private Map<String, JSpinner> userMatch;
    private List<JTextArea> availableMatches;

    public CalendarEventIdentifierPanel(Set<String> smrs, String title) {
        this.summaries = smrs;
        this.userInputOnSummaries = new HashMap<>();
        this.userInputOnEventType = new HashMap<>();
        this.userInputOnCourse = new HashMap<>();
        this.userMatchesOnSummaries = new HashMap<>();
        this.userMatch = new HashMap<>();
        this.availableMatches = new ArrayList<>();

        setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        setLayout(new GridLayout(summaries.size() + 1, 4));

        initComponents();
    }

    private void initComponents() {
        String[] titles = {"Tapahtuma", "Tyyppi", "\"Päätapahtuma kurssilla\"", "Pyöritä alitapahtumien kohdalle päätapahtuma"};
        for (String title : titles) {
            JPanel jp = new JPanel();
            jp.add(new JLabel(title));
            add(jp);
        }
        JTextArea[] userInputTextAreas = new JTextArea[summaries.size()];
        JScrollPane[] userInputs = new JScrollPane[summaries.size()];
        JTextArea[] matches = new JTextArea[summaries.size()];

        int i = 0;
        for (String summary : summaries) {
            JTextArea userInput = new JTextArea(3, 30);
            userInput.setLineWrap(true);
            TransferFocus.patch(userInput);
            userInput.setText(summary);
            this.userInputOnSummaries.put(summary, userInput);
            JScrollPane userScroll = new JScrollPane(userInput);

            JTextArea match = new JTextArea(3, 30);
            match.setLineWrap(true);
            match.setEditable(false);
            match.setText(summary);

            this.userMatchesOnSummaries.put(summary, match);

            UserEventNameListener userListener = new UserEventNameListener(match, userInput);
            userInput.getDocument().addDocumentListener(userListener);

            //
            userInputTextAreas[i] = userInput;
            //
            userInputs[i] = userScroll;
            matches[i] = match;

            i++;
        }
        int j = 0;

        JCheckBox[] courseCheckBoxes = new JCheckBox[summaries.size()];
        JSpinner[] matchSpinners = new JSpinner[summaries.size()];
        for (String summary : summaries) {

            JCheckBox jcb = new JCheckBox();

            JPanel jcbWrap = new JPanel();
            jcbWrap.add(jcb);

            JSpinner js = eventTypeSpinner(availableEventTypes);

            this.userInputOnEventType.put(summary, js);
            this.userInputOnCourse.put(summary, jcb);

            add(userInputs[j]);
            add(js);
            add(jcbWrap);
            JSpinner jsMatch = jTextSpinner(matches);
            this.userMatch.put(summary, jsMatch);
            add(jsMatch);

            courseCheckBoxes[j] = jcb;
            matchSpinners[j] = jsMatch;

            // Disable Matchspinner if event was identified as a course
            jcb.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    if (ie.getStateChange() == ItemEvent.SELECTED) {
                        jsMatch.setEnabled(false);
                    }
                    if (ie.getStateChange() == ItemEvent.DESELECTED) {
                        jsMatch.setEnabled(true);
                    }
                }
            });

            j++;
        }

        for (int k = 0; k < summaries.size(); k++) {
            courseCheckBoxes[k].addItemListener(new MatchListener(userInputTextAreas[k], matchSpinners));
        }

    }

    public Map<String, String> getNewSummaries() {
        Map<String, String> m = new HashMap<>();
        for (String summary : summaries) {
            if (notTrash(summary)) {
                m.put(summary, userInputOnSummaries.get(summary).getText());
            }

        }
        return m;
    }

    public Map<String, EventType> getIdentifiedEventTypes() {
        Map<String, EventType> m = new HashMap<>();
        for (String summary : summaries) {

            if (notTrash(summary)) {
                JSpinner js = (JSpinner) userInputOnEventType.get(summary);
                EventType et = (EventType) js.getValue();
                m.put(summary, et);
            }
        }
        return m;
    }

    public Map<String, Boolean> getCourseSummaries() {
        Map<String, Boolean> m = new HashMap<>();
        for (String summary : summaries) {
            if (notTrash(summary)) {
                m.put(summary, userInputOnCourse.get(summary).isSelected());
            }
        }
        return m;
    }

    public Map<String, String> getEventParents() {
        Map<String, String> m = new HashMap<>();
        for (String summary : summaries) {
            if (notTrash(summary)) {
                m.put(summary, (String) userMatch.get(summary).getValue());
            }
        }
        return m;
    }

    private boolean notTrash(String summary) {
        JSpinner js = userInputOnEventType.get(summary);
        EventType et = (EventType) js.getValue();
        return !et.equals(EventType.TRASH);
    }

    /**
     * JSpinner with String array values
     *
     * @param values
     * @return
     */
    private JSpinner eventTypeSpinner(EventType[] values) {
        JSpinner statusSpinner = new JSpinner(new SpinnerListModel(values));
        if (statusSpinner.getEditor() instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor spinEdit = (JSpinner.DefaultEditor) statusSpinner.getEditor();
            spinEdit.getTextField().setHorizontalAlignment(JTextField.LEFT);
        }

        return statusSpinner;
    }

    /**
     * JSpinner with JTextArea array values
     *
     * @param values
     * @return
     */
    private JSpinner jTextSpinner(JTextArea[] values) {
        JTextArea title = new JTextArea(3, 30);
        title.setLineWrap(true);
        title.setEditable(false);
        title.setText("pyöritä alitapahtumien kohdalle päätapahtuma");

        JTextArea[] beginning = new JTextArea[1];
        beginning[0] = title;

        JSpinner statusSpinner = new JSpinner();
        statusSpinner.setModel(new SpinnerListModel(beginning) {
            @Override
            public Object getValue() {
                JTextArea jta = (JTextArea) super.getValue();

                return jta.getText();
            }
        });

        return statusSpinner;
    }

    /**
     * Update user's input on event names to match spinner values
     */
    class UserEventNameListener implements DocumentListener {

        JTextArea toUpdate;
        JTextArea toListen;

        public UserEventNameListener(JTextArea tu, JTextArea tl) {
            toUpdate = tu;
            toListen = tl;
        }

        private void updateValue() {
            StringBuffer sb = new StringBuffer();
            String str = toListen.getText();
            sb.append(str);
            if (str != null) {
                toUpdate.setText(sb.toString());
            }
        }

        @Override
        public void insertUpdate(DocumentEvent de) {
            updateValue();
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            updateValue();
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            updateValue();
        }

    }

    class MatchListener implements ItemListener {

        JTextArea toEnableOrDisable;
        JSpinner[] toUpdate;

        public MatchListener(JTextArea toEoD, JSpinner[] toUpdate) {
            toEnableOrDisable = toEoD;
            this.toUpdate = toUpdate;
        }

        @Override
        public void itemStateChanged(ItemEvent ie) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                availableMatches.add(toEnableOrDisable);
                for (JSpinner jSpinnerToUpdate : toUpdate) {
                    SpinnerListModel slm = (SpinnerListModel) jSpinnerToUpdate.getModel();
                    slm.setList(availableMatches);
                }

            }

            if (ie.getStateChange() == ItemEvent.DESELECTED) {
                availableMatches.remove(toEnableOrDisable);
                if (availableMatches.isEmpty()) {
                    availableMatches.add(new JTextArea("Valitse ainakin yksi päätapahtuma"));
                }
            }

            for (JSpinner jSpinnerToUpdate : toUpdate) {
                SpinnerListModel slm = (SpinnerListModel) jSpinnerToUpdate.getModel();
                slm.setList(availableMatches);
            }

        }

    }

}
