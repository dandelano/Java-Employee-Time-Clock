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
 * SwingWorker that will add the employee to the DB and then call an update to
 * the GUI.
 *
 * @author dannyjdelanojr
 */
public class AddEmployeeWorker extends SwingWorker<Employee, Integer> {

    private final Employee addEmp;
    private TimeClock mainTc = null;

    public AddEmployeeWorker(TimeClock tc, Employee emp) {
        this.addEmp = emp;
        this.mainTc = tc;
    }

    @Override
    protected Employee doInBackground() throws Exception {
        DbEmployeeManager empMan = new DbEmployeeManager();
        Employee returnEmp = empMan.addEmployee(this.addEmp);

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
                mainTc.addEmployeeToList(emp);
            } else {
                JOptionPane.showMessageDialog(null, "Error occurred while adding employee");
            }
        } catch (InterruptedException | ExecutionException | HeadlessException e) {
            Debugger.log(e);
        }
    }

}
