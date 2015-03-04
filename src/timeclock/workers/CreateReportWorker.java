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
import timeclock.data.DbTimePunchManager;
import timeclock.utilities.Debugger;

/**
 * SwingWorker that prints all time punches to the debugger for now.
 *
 * @author dannyjdelanojr
 */
public class CreateReportWorker extends SwingWorker<String, Integer> {

    @Override
    protected String doInBackground() throws Exception {
        DbTimePunchManager tpMan = new DbTimePunchManager();
        tpMan.printTimePunches();
        return "Finished Creating Report";
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
