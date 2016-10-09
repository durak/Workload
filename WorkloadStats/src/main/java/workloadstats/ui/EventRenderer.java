/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import workloadstats.domain.model.Event;

/**
 * Custom rendered for a JList of events
 * @author Ilkka
 */
public class EventRenderer extends JLabel implements ListCellRenderer<Event> {

    public EventRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Event> list, Event event, int index,
            boolean isSelected, boolean cellHasFocus) {
        
        String eventName = event.getEventName();
        String eventDate = event.getStartDateString();
        setText(eventDate + " " + eventName);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

}
