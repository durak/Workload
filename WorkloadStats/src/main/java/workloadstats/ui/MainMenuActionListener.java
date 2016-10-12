package workloadstats.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.utils.Ac;

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
            int choice = JOptionPane.showConfirmDialog(container, "Haluatko varmasti avata uuden kalenterin,\n tallentamattomat tiedot tuhoutuvat.",
                    "Huomio!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) {
                myCalendarControl.initEmptyCalendar();
                courseListModel.updateCourses(myCalendarControl.getCourses());
                courseList.clearSelection();
            }
        }

        if (ae.getActionCommand().equals(Ac.LOADCALENDAR.name())) {
            int returnValue = fileChooser.showOpenDialog(container);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
                boolean load = myCalendarControl.loadFile(selectedFile);

                if (load) {
                    courseListModel.updateCourses(myCalendarControl.getCourses());
                    courseList.clearSelection();
                    courseList.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(container, "Kalenteritiedosto ei ole ohjelman luoma.\n Käytä Tuo tapahtumia -ominaisuutta", "ALARM", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (ae.getActionCommand().equals(Ac.IMPORTCALENDAR.name())) {

        }

        if (ae.getActionCommand().equals(Ac.SAVECALENDAR.name())) {
            myCalendarControl.updateCalendar();
            myCalendarControl.saveCalendar();
        }
    }

    private void fileChooserInit() {
        this.fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("calendar files (.ics)", "ics"));
    }

}
