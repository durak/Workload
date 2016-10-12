/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import workloadstats.calendardata.mycalendar.MyCalendarControl;
import workloadstats.utils.Ac;

/**
 *
 * @author Ilkka
 */
public class MainMenuActionListener implements ActionListener {

    private MyCalendarControl myCalendarControl;
    private CourseListModel courseListModel;
    
    public MainMenuActionListener(MyCalendarControl mcc, CourseListModel clm) {
        this.myCalendarControl = mcc;
        this.courseListModel = clm;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(Ac.NEWCALENDAR.name())) {
            System.out.println("aaaa");
        }

        if (ae.getActionCommand().equals(Ac.LOADCALENDAR.name())) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
                
            }
        }
        if (ae.getActionCommand().equals(Ac.IMPORTCALENDAR.name())) {

        }

        if (ae.getActionCommand().equals(Ac.SAVECALENDAR.name())) {
            myCalendarControl.updateCalendar();
            myCalendarControl.saveCalendar();
        }
    }

}
