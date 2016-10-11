package workloadstats.ui;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.EventType;
import workloadstats.utils.EventListAnalysis;
import workloadstats.utils.StatusType;

/**
 * Show statistics from user's multiple selected events Listens to EventList for
 * selection changes, gets data from EventListModel for the calculations.
 *
 * @author Ilkka
 */
public class SelectionStatsPanel extends JPanel implements ListSelectionListener {

    private EventListModel elmodel;
    private JLabel[] statsLabels;
    private String borderTitle;

    public SelectionStatsPanel(EventListModel elm) {
        this.elmodel = elm;
        this.borderTitle = "Statistiikkaa (valitse vähintään kaksi tapahtumaa)";
        initPanelComponents();

    }

    private void initPanelComponents() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(borderTitle));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.statsLabels = new JLabel[36];

        /*
         * Initialize 42 JLabels in 7 rows
         * First row: permanent titles, do not change with selections
         * Next 6 rows: statistics, values in accordance to user's selections
         *  -> Stored in statsLabels[]
         */
        JPanel stats = new JPanel();
        stats.setLayout(new GridLayout(7, 6, 0, 5));

        String[] titles = {"Tapahtuma", "Läsnä", "Poissa", "Harkinnassa", "Yhteensä", "Läsnä-%"};
        String[] events = {"Luento", "Harkka", "Personal", "Teamwrk", "Koe", "Yhteensä"};

        for (int i = 0; i < 6; i++) {
            JLabel jl = new JLabel(titles[i]);
            jl.setHorizontalAlignment(SwingConstants.CENTER);
            jl.setHorizontalTextPosition(SwingConstants.CENTER);
            stats.add(jl);
        }

        int eventType = 0;
        for (int i = 0; i < 36; i++) {

            if (i % 6 == 0) {
                JLabel type = new JLabel(events[eventType]);
                type.setHorizontalTextPosition(SwingConstants.CENTER);
                type.setHorizontalAlignment(SwingConstants.CENTER);
                stats.add(type);
                statsLabels[i] = type;
                eventType++;
            } else {
                JLabel value = new JLabel("-");
                value.setHorizontalTextPosition(SwingConstants.CENTER);
                value.setHorizontalAlignment(SwingConstants.CENTER);
                stats.add(value);
                statsLabels[i] = value;
            }

        }

        this.add(stats);
    }

    private void setBorder(String s) {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(s));
    }

    /**
     * Reset statsLabels[] values with new selection's statistics
     * EventListAnalysis class provides results
     *
     * @param el
     */
    private void updateStats(List<Event> el) {
        List<JLabel> updated = new ArrayList<>();
        EventListAnalysis ela = new EventListAnalysis(el);
        setBorder("Statistiikkaa, aikaväli: " + ela.getTimespan());

        EventType[] eventTypes = {EventType.LECTURE, EventType.EXERCISE, EventType.PERSONAL, EventType.TEAMWORK, EventType.EXAM};
        StatusType[] statusTypes = {StatusType.CONFIRMED, StatusType.CANCELLED, StatusType.TENTATIVE};

        // Ugly run thru the statsLabels array to update values, can't be bothered now to get a prettier solution
        int x = 0;
        for (EventType eventType : eventTypes) {
            if (x % 6 == 0) {
                x++;
            }
            for (StatusType statusType : statusTypes) {
                statsLabels[x].setText(ela.getEventTypeAndStatusDuration(eventType, statusType));
                x++;

                if (x % 6 > 3) {
                    statsLabels[x].setText(ela.getEventTypeTotalDuration(eventType));
                    x++;
                    statsLabels[x].setText(ela.getAttendancePercentage(eventType));
                    x++;
                }
            }
        }
        x++;
        for (StatusType statusType : statusTypes) {
            statsLabels[x].setText(ela.getStatusTotalDuration(statusType));
            x++;
        }
        statsLabels[x].setText(ela.getTotalDuration());
        x++;
        statsLabels[x].setText(ela.getTotalAttendancePercentage());
    }

    /**
     * When event selections change, update user view
     *
     * @param lse
     */
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        JList evList = (JList) lse.getSource();
        if (!lse.getValueIsAdjusting()) {
            int[] userSelections = evList.getSelectedIndices();
            List<Event> selectedEvents = new ArrayList<>();

            if (userSelections.length > 1) {
                for (int i : userSelections) {
                    selectedEvents.add((Event) elmodel.getElementAt(i));
                }
                updateStats(selectedEvents);
            } else {
                for (int i = 0; i < statsLabels.length; i++) {
                    if (i % 6 != 0) {
                        statsLabels[i].setText("-");
                    }
                }
                setBorder(borderTitle);
            }
        }

    }

}
