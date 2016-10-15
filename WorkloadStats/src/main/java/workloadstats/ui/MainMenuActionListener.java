package workloadstats.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import workloadstats.calendardata.CalendarImportManager;
import workloadstats.calendardata.MyCalendarControl;
import workloadstats.domain.Course;
import workloadstats.utils.Ac;
import workloadstats.utils.EventType;

/**
 *
 * @author Ilkka
 */
public class MainMenuActionListener implements ActionListener {

    private MyCalendarControl myCalendarControl;
    private CourseListModel courseListModel;
    private JList courseList;
    private Container container;
    private JFileChooser fileChooser;

    public MainMenuActionListener(Container cntr, MyCalendarControl mcc, CourseListModel clm, JList crsLst) {
        this.container = cntr;
        this.myCalendarControl = mcc;
        this.courseListModel = clm;
        this.courseList = crsLst;

        //Create filechooser instance and set file filter for calendar files only
        this.fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("calendar files (.ics)", "ics"));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals(Ac.NEWCALENDAR.name())) {
            newCalendarAction();
        }

        if (ae.getActionCommand().equals(Ac.LOADCALENDAR.name())) {
            loadCalendarAction();
        }

        if (ae.getActionCommand().equals(Ac.IMPORTCALENDAR.name())) {
            int returnValue = fileChooser.showOpenDialog(container);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Get CalendarImportManager instance from myCalendarControl with user selected file
                CalendarImportManager calImpMan = myCalendarControl.getCalendarImportManager(selectedFile);
                if (calImpMan != null) {
                    // Call import action
                    calendarImportAction(calImpMan);
                } else {
                    JOptionPane.showMessageDialog(container, "Kalenteritiedosto ei kelpaa", "ALARM", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (ae.getActionCommand().equals(Ac.SAVECALENDAR.name())) {
            myCalendarControl.saveCalendar();
        }

    }

    /**
     * Query user for confirmation before initializing current calendar
     */
    private void newCalendarAction() {
        int choice = JOptionPane.showConfirmDialog(container, "Haluatko varmasti avata uuden kalenterin,\n tallentamattomat tiedot tuhoutuvat.",
                "Huomio!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (choice == JOptionPane.OK_OPTION) {
            myCalendarControl.initEmptyCalendar();
            courseListModel.updateCourses(myCalendarControl.getCourses());
            courseList.clearSelection();
        }
    }

    /**
     * Try to load a calendar file. Only files created by this program are
     * accepted.
     */
    private void loadCalendarAction() {
        if (fileChooser.showOpenDialog(container) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            boolean load = myCalendarControl.loadFile(selectedFile);

            if (load) {
                courseListModel.updateCourses(myCalendarControl.getCourses());
                courseList.clearSelection();
                courseList.setSelectedIndex(0);
            } else {
                String error = "Kalenteritiedosto ei ole ohjelman luoma.\n Käytä Tuo merkintöjä -toimintoa";
                JOptionPane.showMessageDialog(container, error, "ALARM", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Import events from calendar files not built by this program
     *
     * @param calendImpMan CalendarImportManager instance
     */
    private void calendarImportAction(CalendarImportManager calendImpMan) {
        String message = "Tuodaan kalenterimerkintöjä nykyiseen kalenteriin.\n"
                + "Toimi seuraavasti:\n"
                + "1. Nimeä tapahtumat uudelleen\n"
                + "2. Valitse tapahtumien tyypit, TRASH jos et halua tuoda tapahtumaa.\n"
                + "3. Laita rasti \"päätapahtuman\" kohdalle (esim. luennot)\n"
                + "4. Pyöritä alitapahtumien kohdalle päätapahtuma\n";

        JOptionPane.showMessageDialog(container, message, "Huomio!", JOptionPane.PLAIN_MESSAGE);

        CalendarEventIdentifierPanel eip = new CalendarEventIdentifierPanel(calendImpMan.getSummariesToIdentify(), "Tunnista tapahtumat");
        int choice = JOptionPane.showConfirmDialog(container, eip,
                "Tuodaan merkintöjä", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (choice == JOptionPane.OK_OPTION) {
            // Get user inputted identification data
            Map<String, String> newSummaries = eip.getNewSummaries();
            Map<String, EventType> identifiedTypes = eip.getIdentifiedEventTypes();
            Map<String, Boolean> identifiedCourses = eip.getCourseSummaries();
            Map<String, String> eventParents = eip.getEventParents();
            // Get identified imports with user input
            List<Course> newImports = calendImpMan.userIdentifiedEvents(newSummaries, identifiedTypes, identifiedCourses, eventParents);
            
            // Add imports to current calendar
            myCalendarControl.importNewCourses(newImports);
            // Update user view
            courseListModel.updateCourses(myCalendarControl.getCourses());
            courseList.clearSelection();
            courseList.setSelectedIndex(0);
        }

    }

}
