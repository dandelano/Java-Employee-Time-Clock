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
import timeclock.utilities.Debugger;

/**
 * SwingWorker that get a list of all Employees currently clocked in.
 *
 * @author dannyjdelanojr
 */
public class WhoIsClockedInWorker extends SwingWorker<String, Integer> {

    @Override
    protected String doInBackground() throws Exception {
        DbEmployeeManager empMan = new DbEmployeeManager();
        ArrayList<Employee> emps = empMan.getClockedInEmployees();
        StringBuilder sb = new StringBuilder();
        sb.append(emps.isEmpty() ? "No employees clocked in." : "");
        for (Employee emp : emps) {
            sb.append(emp.toString());
            sb.append("\n");
        }
        return sb.toString();
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
