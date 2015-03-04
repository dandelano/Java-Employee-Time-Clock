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
 * SwingWorker that clocks out an employee and updates the GUI Employee list.
 *
 * @author dannyjdelanojr
 */
public class ClockOutWorker extends SwingWorker<Boolean, Integer> {

    private Employee coEmp = null;
    private TimePunch empTp = null;
    private TimeClock mainTc = null;

    public ClockOutWorker(TimeClock tc, Employee emp) {
        this.coEmp = emp;
        mainTc = tc;
        empTp = new TimePunch(emp.getEmployeeID());
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        DbTimePunchManager tpMan = new DbTimePunchManager();
        return tpMan.punchOut(this.empTp);
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                mainTc.clockEmployee(this.coEmp, false);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to clock out employee");
            }
        } catch (InterruptedException | ExecutionException | HeadlessException e) {
            Debugger.log(e);
        }
    }

}
