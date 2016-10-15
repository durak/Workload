package workloadstats;

import javax.swing.SwingUtilities;
import workloadstats.calendardata.MyCalendarControl;
import workloadstats.ui.Gui;

/**
 *
 * @author Ilkka
 */
public class Main {

    public static void main(String[] args) {

        // Start WorkLoadStats app
        
        MyCalendarControl myCalendarControl = new MyCalendarControl();
        Gui gui = new Gui(myCalendarControl);
        SwingUtilities.invokeLater(gui);

    }

}
