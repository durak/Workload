package workloadstats.ui.utils;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import workloadstats.domain.model.Event;

/**
 * Custom rendered for a JList of events
 * @author Ilkka
 */
public class EventListRenderer extends JLabel implements ListCellRenderer<Event> {

    public EventListRenderer() {
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
