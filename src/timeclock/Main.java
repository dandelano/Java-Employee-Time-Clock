/*
 * TimeClock v1.0
 * This is a small time tracking project that allows employee to
 * clock in and out and also allows an administrator to 
 * add/edit/list employees, also allows them to generate reports for
 * the times worked.
 *
 * This project uses the following libraries:
 * - JavaDB( derby.jar)
 * - iTextpdf-5.4.5.jar
 * - jasypt-1.9.1-lite.jar
 * - jCalendar-1.4.jar
 * - jgoodies-common-1.2.0.jar
 * - jgoodies-looks-2.4.1.jar
 */

package timeclock;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import timeclock.data.DbConnection;
import timeclock.data.DbEmployeeManager;
import timeclock.data.DbTableManager;
import timeclock.objects.Employee;
import timeclock.utilities.DateUtils;
import timeclock.utilities.Debugger;

/**
 * This is the main thread to start the app.
 * @author demont-imac
 */
public class Main {
    
    /**
     * The main method that is called first, the start of the app.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {    
            // Try and set the look and feel to use the jGoodies Look style
            UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
        }catch(UnsupportedLookAndFeelException e)
        {
            Debugger.log(e);
        }
        
        /* Configure Database Settings */
        DbConnection.configureDBSettings();
        
        /* Check that all tables are in place */
        if(DbTableManager.checkTables()== true)
        {        
            // Get the list of employees from database
            DbEmployeeManager empMan = new DbEmployeeManager();
            final ArrayList<Employee> empList = new ArrayList<>(empMan.getEmployeeList());

            /* Create and display the form */
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new TimeClock(empList).setVisible(true);
                }
            });
        }else
        {
            Debugger.log("Error Creating tables: " + DateUtils.getDateTime());
            JOptionPane.showMessageDialog(null, "The database may be corrupt or not configured correctly.", "Database Error", JOptionPane.ERROR_MESSAGE, null);
        }
        /* Test Examples */
        
        /* properties sample 
        AppSettings appSet = new AppSettings();
        appSet.loadSettings();
        Debugger.log(appSet.getAdminPass()); */
        
 
        /* File chooser sample
        FileChooser fc = new FileChooser();
        String name = fc.showDialog("Employees");
        if(name != null)
            Debugger.log(name);*/
        
        /* Password encryption sample
        PassEncryptor passEn = new PassEncryptor();
        String myPass = "pass";
        String encryptedPass = passEn.encryptPassword(myPass);
        Debugger.log(myPass);
        Debugger.log(encryptedPass);
        Debugger.log(passEn.checkPassword(myPass, encryptedPass));
        Debugger.log(passEn.checkPassword("NotIt", encryptedPass));*/
    }  
}
