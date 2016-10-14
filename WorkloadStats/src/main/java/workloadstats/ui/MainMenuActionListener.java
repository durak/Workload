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
import workloadstats.calendardata.hycalendar.CalendarImportManager;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.domain.model.Course;
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
        fileChooserInit();
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
                CalendarImportManager calImpMan = myCalendarControl.getCalendarImportManager(selectedFile);

                if (calImpMan != null) {
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

    private void fileChooserInit() {
        this.fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("calendar files (.ics)", "ics"));
    }

    /**
     * Query user for confirmation before initializing current calendar
     *
     * @param calImpMan
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
     * Open fileChooser instance and try to load a calendar file. Only files
     * created by this program are accepted.
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
                + "2. Valitse tapahtumien tyypit\n"
                + "3. Laita rasti \"päätapahtuman\" kohdalle (esim. luennot)\n";

        JOptionPane.showMessageDialog(container, message, "Huomio!", JOptionPane.PLAIN_MESSAGE);

        CalendarEventIdentifierPanel eip = new CalendarEventIdentifierPanel(calendImpMan.getSummariesToIdentify(), "Tunnista tapahtumat");
        int choice = JOptionPane.showConfirmDialog(container, eip,
                "Tuodaan merkintöjä", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        Map<String, String> newSummaries = eip.getNewSummaries();
        Map<String, EventType> identifiedTypes = eip.getIdentifiedEventTypes();
        Map<String, Boolean> identifiedCourses = eip.getCourseSummaries();
        Map<String, String> eventParents = eip.getEventParents();


        List<Course> newImports = calendImpMan.userIdentifiedEvents(newSummaries, identifiedTypes, identifiedCourses, eventParents);
        
        
        myCalendarControl.importNewCourses(newImports);
        courseListModel.updateCourses(myCalendarControl.getCourses());
        courseList.clearSelection();
        courseList.setSelectedIndex(0);

    }

}
