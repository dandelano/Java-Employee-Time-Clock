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
import timeclock.data.DbTimePunchManager;
import timeclock.objects.Employee;
import timeclock.objects.TimePunch;
import timeclock.utilities.Debugger;

/**
 * SwingWorker that clocks in an employee and updates the GUI Employee list.
 *
 * @author dannyjdelanojr
 */
public class ClockInWorker extends SwingWorker<Boolean, Integer> {

    private Employee ciEmp = null;
    private TimePunch empTp = null;
    private TimeClock mainTc = null;

    public ClockInWorker(TimeClock tc, Employee emp) {
        this.ciEmp = emp;
        mainTc = tc;
        empTp = new TimePunch(emp.getEmployeeID());
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        DbTimePunchManager tpMan = new DbTimePunchManager();
        return tpMan.punchIn(this.empTp);
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                mainTc.clockEmployee(this.ciEmp, true);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to clock in employee");
            }

        } catch (InterruptedException | ExecutionException | HeadlessException e) {
            Debugger.log(e);
        }
    }
}
