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
 * SwingWorker that deletes all timepunches, the employee from the database and
 * then updates the GUI Employee list.
 *
 * @author dannyjdelanojr
 */
public class DeleteEmployeeWorker extends SwingWorker<Employee, Integer> {

    private Employee deleteEmp = null;
    private TimeClock mainTc = null;

    public DeleteEmployeeWorker(TimeClock tc, Employee emp) {
        this.deleteEmp = emp;
        this.mainTc = tc;
    }

    @Override
    protected Employee doInBackground() throws Exception {
        DbEmployeeManager empMan = new DbEmployeeManager();
        Employee returnEmp = empMan.deleteEmployee(this.deleteEmp);

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
                mainTc.deleteEmployeeFromList(emp);
            } else {
                JOptionPane.showMessageDialog(null, "Error occurred while deleting employee");
            }
        } catch (HeadlessException | InterruptedException | ExecutionException e) {
            Debugger.log(e);
        }
    }

}
