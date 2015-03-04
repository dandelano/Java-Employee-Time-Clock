/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.workers;

import java.awt.HeadlessException;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import timeclock.TimeClock;
import timeclock.data.DbEmployeeManager;
import timeclock.objects.Employee;
import timeclock.utilities.Debugger;

/**
 * SwingWorker that updates the Employee in the database, and also updates the
 * GUI Employee list.
 *
 * @author dannyjdelanojr
 */
public class EditEmployeeWorker extends SwingWorker<Employee, Integer> {

    private Employee eEmp = null;
    private TimeClock mainTc = null;

    public EditEmployeeWorker(TimeClock tc, Employee emp) {
        this.eEmp = emp;
        this.mainTc = tc;

    }

    @Override
    protected Employee doInBackground() throws Exception {
        DbEmployeeManager empMan = new DbEmployeeManager();
        Employee returnEmp = empMan.editEmployee(this.eEmp);

        if (returnEmp != null) {
            return returnEmp;
        } else {
            return null;
        }
    }

    @Override
    protected void done() {
        try {
            Employee emp = get();
            if (emp != null) {
                mainTc.updateEmployeeInfoInList(emp);
            } else {
                JOptionPane.showMessageDialog(null, "Error occurred while editing employee");
            }
        } catch (InterruptedException | ExecutionException | HeadlessException e) {
            Debugger.log(e);
        }
    }

}
