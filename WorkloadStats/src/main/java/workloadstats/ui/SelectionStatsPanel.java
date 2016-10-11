/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.EventType;
import workloadstats.ui.refactor.EventListModel;
import workloadstats.utils.EventListAnalysis;
import workloadstats.utils.StatusType;

/**
 * Show statistics from user's selected events
 *
 * @author Ilkka
 */
public class SelectionStatsPanel extends JPanel implements ListSelectionListener {

    private EventListModel elmodel;

    private JLabel[] statsLabels;
    GridBagConstraints gbc = new GridBagConstraints();
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

        JPanel stats = new JPanel();
        stats.setLayout(new GridLayout(7, 6, 10, 10));

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

    private void updateStats(List<Event> el) {
        List<JLabel> updated = new ArrayList<>();
        EventListAnalysis ela = new EventListAnalysis(el);
        setBorder("Statistiikkaa, aikaväli: " + ela.getTimespan());

        EventType[] eventTypes = {EventType.LECTURE, EventType.EXERCISE, EventType.PERSONAL, EventType.TEAMWORK, EventType.EXAM};
        StatusType[] statusTypes = {StatusType.CONFIRMED, StatusType.CANCELLED, StatusType.TENTATIVE};

        int z = 1;
        for (int i = 0; i < 36; i++) {
            if (i % 6 == 0) {
                System.out.println("tapahtuma");
                i++;
            }
            while (i % 6 <= 3) {
                System.out.println("event " + z);
                i++;
            }
            System.out.println("event " + z + " total");
            System.out.println("event " + z + " total");
            i++;
            i++;
            z++;
        }

        int x = 0;
        int y = 0;
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

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        JList evList = (JList) lse.getSource();
        if (!lse.getValueIsAdjusting()) {
            int[] iit = evList.getSelectedIndices();
            List<Event> selectedEvents = new ArrayList<>();
            if (iit.length > 1) {
                for (int i : iit) {
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

//            if (evList.getSelectedIndices().length >= 2) {
//                List<Event> el = elmodel.getEvents();
//                if (el != null) {
//                    updateStats(el);
//                }
//            }
        }

    }

}
