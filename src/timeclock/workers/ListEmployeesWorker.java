/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.workers;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import timeclock.data.DbEmployeeManager;
import timeclock.objects.Employee;
import timeclock.reports.EmployeeListReport;
import timeclock.utilities.Debugger;

/**
 * SwingWorker that creates a report of all Employees in the database.
 *
 * @author dannyjdelanojr
 */
public class ListEmployeesWorker extends SwingWorker<String, Integer> {

    @Override
    protected String doInBackground() throws Exception {
        // Get the list of employees from database
        DbEmployeeManager empMan = new DbEmployeeManager();
        final ArrayList<Employee> empList = new ArrayList<>(empMan.getEmployeeList());

        // PDF creation example        
        EmployeeListReport empReport = new EmployeeListReport(empList);
        empReport.createPdf();

        return "Finished Creating Employee Report";
    }

    @Override
    protected void done() {
        try {
            JOptionPane.showMessageDialog(null, get());
        } catch (InterruptedException | ExecutionException | HeadlessException e) {
            Debugger.log(e);
        }
    }

}
