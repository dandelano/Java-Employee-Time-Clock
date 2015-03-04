/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.workers;

import java.awt.HeadlessException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import timeclock.data.DbTimePunchManager;
import timeclock.objects.Employee;
import timeclock.objects.TimePunch;
import timeclock.reports.EmployeeTimeReport;
import timeclock.utilities.DateUtils;
import timeclock.utilities.Debugger;

/**
 * SwingWorker that creates the time report for the selected Employee.
 *
 * @author dannyjdelanojr
 */
public class EmployeeTimeReportWorker extends SwingWorker<String, Integer> {

    private Employee erEmp = null;
    private Timestamp startDate = null;
    private Timestamp endDate = null;

    public EmployeeTimeReportWorker(Employee emp, Date[] dates) {
        // Get the employee to search for
        this.erEmp = emp;
        // Get the timestamps for the dates to search
        this.startDate = DateUtils.getStartTimestampFromDate(dates[0]);
        this.endDate = DateUtils.getEndTimestampFromDate(dates[1]);
    }

    @Override
    protected String doInBackground() throws Exception {
        DbTimePunchManager tpMan = new DbTimePunchManager();
        ArrayList<TimePunch> timesList = tpMan.getTimePunches(erEmp, startDate, endDate);

        // use new report creator to             
        EmployeeTimeReport timeReport = new EmployeeTimeReport(erEmp, timesList, startDate, endDate);
        timeReport.createPdf();
        return "Finished creating employee time report";
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
