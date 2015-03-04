/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock;

import timeclock.utilities.Debugger;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import timeclock.crypto.PassEncryptor;
import timeclock.data.DbConnection;
import timeclock.objects.Employee;
import timeclock.utilities.AppSettings;
import timeclock.utilities.DateUtils;
import timeclock.workers.AddEmployeeWorker;
import timeclock.workers.ClockInWorker;
import timeclock.workers.ClockOutWorker;
import timeclock.workers.CreateReportWorker;
import timeclock.workers.DeleteEmployeeWorker;
import timeclock.workers.EditEmployeeWorker;
import timeclock.workers.EmployeeTimeReportWorker;
import timeclock.workers.ListEmployeesWorker;
import timeclock.workers.WhoIsClockedInWorker;

/**
 * This is the main GUI for the app.
 *
 * @author demont-imac
 */
public class TimeClock extends JFrame {
    // TODO: ADMIN_LOGIN - Implement a better admin login method
    private String ADMIN_PASS;
    private String TITLE;
    private final int TIMER_DELAY = 1000;
    // Holds the list of employees and the selected employee
    private ArrayList<Employee> EmpNames;
    private Employee empSelectedEmployee;
    private String strSelectedEmployee;
    // Main Menubar
    private JMenuBar menuBar;
    // File menu and Items
    private JMenu fileMenu;
    private JMenuItem adminMenuItem;
    private JMenuItem userModeMenuItem;
    private JMenuItem exitMenuItem;
    // Admin menu and Items
    private JMenu employeeMenu;
    private JMenuItem editMenuItem;
    private JMenuItem addEmpMenuItem;
    private JMenuItem deleteEmpMenuItem;
    private JMenuItem whoClockInMenuItem;
    // Report menu and Items
    private JMenu reportMenu;
    private JMenuItem createReportMenuItem;
    private JMenuItem listEmpMenuItem;
    private JMenuItem employeeTimeReportMenuItem;
    // User interface
    private JButton clockInBtn, clockOutBtn;
    private JComboBox cboEmpList;
    // Time clock 
    private JLabel lblHeading;
    private Timer timer;

    
    /**
     * Create the window and start the clock display
     */
    TimeClock(ArrayList<Employee> employees) {
        AppSettings appSet = new AppSettings();
        appSet.loadSettings();
        ADMIN_PASS = appSet.getAdminPass();
        TITLE = appSet.getAppTitle();
        // Get the employee names
        EmpNames = employees;
        // Sort them alphabetically by first name, then last name        
        Collections.sort(EmpNames);
        // initialize window
        initComponents();
        // start the display clock
        this.timer.start();
    }

    /**
     * Creates the layout and sets up the main window.
     */
    private void initComponents() {
        // Set propeties for window
        setSize(400, 275);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setTitle(this.TITLE);
        // disable the x-button(close) 
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // disable resizing of the window
        setResizable(false);

        // Use inherited method getContentPane to get window's content pane 
        Container container = getContentPane();
        container.setLayout(new BorderLayout(3, 1));  // Set default layout 

        // Add panel for heading and set layout
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create and set the menus
        createMenus();
        setJMenuBar(menuBar);

        // Set text in label using html for multiline 
        lblHeading = new JLabel("Starting...");
        lblHeading.setFont(new Font("Dialog", Font.PLAIN, 24));
        // Add label to panel
        headingPanel.add(lblHeading);

        // Add heading panel and call the builds and add other panels
        container.add(headingPanel, BorderLayout.NORTH);
        container.add(createInputPanel(), BorderLayout.CENTER);
        // Create the timer object
        createTimer();

        // After all set up is finished, show the window
        setVisible(true);
        Debugger.log("Opened app: " + new Date());
    }

    /**
     * Creates the menu layout for the app.
     */
    private void createMenus() {
        adminMenuItem = new JMenuItem();
        adminMenuItem.setMnemonic('a');
        adminMenuItem.setText("Admin");
        adminMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminMenuItemActionPerformed(evt);
            }
        });

        userModeMenuItem = new JMenuItem();
        userModeMenuItem.setMnemonic('u');
        userModeMenuItem.setText("User Mode");
        userModeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userModeMenuItemActionPerformed(evt);
            }
        });

        exitMenuItem = new JMenuItem();
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        editMenuItem = new JMenuItem();
        editMenuItem.setMnemonic('e');
        editMenuItem.setText("Edit Employee");
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });

        addEmpMenuItem = new JMenuItem();
        addEmpMenuItem.setMnemonic('d');
        addEmpMenuItem.setText("Add Employee");
        addEmpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpMenuItemActionPerformed(evt);
            }
        });

        deleteEmpMenuItem = new JMenuItem();
        deleteEmpMenuItem.setMnemonic('d');
        deleteEmpMenuItem.setText("Delete Employee");
        deleteEmpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEmpMenuItemActionPerformed(evt);
            }
        });

        whoClockInMenuItem = new JMenuItem();
        whoClockInMenuItem.setMnemonic('d');
        whoClockInMenuItem.setText("List Clocked In");
        whoClockInMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whoClockInMenuItemActionPerformed(evt);
            }
        });

        createReportMenuItem = new JMenuItem();
        createReportMenuItem.setMnemonic('a');
        createReportMenuItem.setText("Create All Times Report");
        createReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createReportMenuItemActionPerformed(evt);
            }
        });

        employeeTimeReportMenuItem = new JMenuItem();
        employeeTimeReportMenuItem.setMnemonic('t');
        employeeTimeReportMenuItem.setText("Create Employee Time Report");
        employeeTimeReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeTimeReportMenuItemActionPerformed(evt);
            }
        });

        listEmpMenuItem = new JMenuItem();
        listEmpMenuItem.setMnemonic('l');
        listEmpMenuItem.setText("Create Employee List Report");
        listEmpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listEmpMenuItemActionPerformed(evt);
            }
        });

        // Create Admin Menu
        reportMenu = new JMenu();
        reportMenu.setMnemonic('r');
        reportMenu.setText("Report");
        reportMenu.add(createReportMenuItem);
        reportMenu.add(employeeTimeReportMenuItem);
        reportMenu.add(listEmpMenuItem);

        // Create Admin Menu
        employeeMenu = new JMenu();
        employeeMenu.setMnemonic('e');
        employeeMenu.setText("Employee");
        employeeMenu.add(editMenuItem);
        employeeMenu.add(addEmpMenuItem);
        employeeMenu.add(deleteEmpMenuItem);
        employeeMenu.add(whoClockInMenuItem);

        // Create File Menu
        fileMenu = new JMenu();
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        fileMenu.add(adminMenuItem);
        fileMenu.add(exitMenuItem);

        // Only display the file menu on start
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
    }

    /**
     * Perform the action for admin menu item, will check the for a password
     * before setting the menus to Admin mode.
     *
     * @param evt
     */
    private void adminMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        String inputPass = InputDialog.showPasswordInput();
        
        // Check for correct password
        if (validatePassword(inputPass,true)) {
            setAdminMenuMode(true);
            Debugger.log("Admin logged in: " + new Date());
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect Password or Incorrect Privileges", "Incorrect", JOptionPane.ERROR_MESSAGE);
            Debugger.log("Wrong password admin menu: " + new Date());
        }
    }

    /**
     * Perform the action for user mode menu item, sets the menus back to normal
     * user mode.
     *
     * @param evt
     */
    private void userModeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        setAdminMenuMode(false);
        Debugger.log("Admin logged out: " + new Date());
    }

    /**
     * Performs the action for exit menu item, checks for admin password before
     * allowing the program to exit.
     *
     * @param evt
     */
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        String inputPass = InputDialog.showPasswordInput();
        
        // Check for correct password
        if (validatePassword(inputPass,true)) {
            Debugger.log("Admin closed application: " + new Date());
            shutDown();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect Password or Incorrect Privileges", "Incorrect", JOptionPane.ERROR_MESSAGE);
            Debugger.log("Wrong password for exit: " + new Date());
        }

    }

    /**
     * Performs the action for edit menu item.
     *
     * @param evt
     */
    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        Employee editEmp = InputDialog.showEmployeeInput(empSelectedEmployee);
        if (editEmp != null) {
            (new EditEmployeeWorker(this, editEmp)).execute();
            // Remove all items from combobox so we can refill it after employee added
            cboEmpList.removeAllItems();
        }
    }

    /**
     * Performs the action for Add Employee menu item.
     *
     * @param evt
     */
    private void addEmpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        Employee addEmp = InputDialog.showEmployeeInput();
        if (addEmp != null) {
            (new AddEmployeeWorker(this, addEmp)).execute();
            // Remove all items from combobox so we can refill it after employee added
            cboEmpList.removeAllItems();
        }
    }

    /**
     * Performs the action for Delete Employee menu item.
     *
     * @param evt
     */
    private void deleteEmpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        String confirmation = InputDialog.showDeleteEmployeeConfirm(empSelectedEmployee);
        
        // User hit cancel
        if(confirmation == null)
            return;
        
        // Check for correct input
        if (confirmation.equals("DELETE")) {
            (new DeleteEmployeeWorker(this, empSelectedEmployee)).execute();
            // Remove all items from combobox so we can refill it after employee added
            cboEmpList.removeAllItems();
        } else {
            JOptionPane.showMessageDialog(null, "Employee was not deleted because\nconfirmation was not correct.", "Incorrect", JOptionPane.ERROR_MESSAGE);
            Debugger.log("Wrong input to delete employee: " + new Date());
        }

    }

    /**
     * Performs the action for Delete Employee menu item.
     *
     * @param evt
     */
    private void whoClockInMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        (new WhoIsClockedInWorker()).execute();
    }

    /**
     * Performs the action for List Employee menu item.
     *
     * @param evt
     */
    private void listEmpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        (new ListEmployeesWorker()).execute();
    }

    /**
     * Performs the action for List Employee menu item.
     *
     * @param evt
     */
    private void createReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        (new CreateReportWorker()).execute();
    }

    /**
     * Performs the action for List Employee menu item.
     *
     * @param evt
     */
    private void employeeTimeReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        Date[] dateRange = InputDialog.showDateTimePicker();
        if (dateRange != null) {
            (new EmployeeTimeReportWorker(empSelectedEmployee, dateRange)).execute();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Sorry, an error occurred while trying to get the dates.",
                    "Error Getting Dates", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This displays or hides the admin menu on the menubar.
     *
     * @param isAdmin true to show admin, false to hide it
     */
    private void setAdminMenuMode(boolean isAdmin) {
        if (isAdmin) {
            menuBar.add(employeeMenu);
            menuBar.add(reportMenu);
            fileMenu.remove(adminMenuItem);
            fileMenu.insert(userModeMenuItem, 0);
            setJMenuBar(menuBar);
        } else {
            menuBar.remove(employeeMenu);
            menuBar.remove(reportMenu);
            fileMenu.remove(userModeMenuItem);
            fileMenu.insert(adminMenuItem, 0);
            setJMenuBar(menuBar);
        }
    }

    /**
     * Creates the input panel for the clock in and out function
     *
     * @return JPanel that contains the buttons for clocking in and out
     */
    private JPanel createInputPanel() {
        // Create the employee list
        if (!EmpNames.isEmpty()) {
            // If list of employees not empty add to the combobox
            cboEmpList = new JComboBox(EmpNames.toArray());
        } else {
            // List of employees is empty, so add placeholder string
            String[] empty = {"empty"};
            cboEmpList = new JComboBox(empty);
        }

        // Create the action listener for the combobox
        cboEmpList.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpListActionPerformed(evt);
            }
        });

        // Make sure we have employees to select
        if (cboEmpList.getItemCount() > 0) {
            cboEmpList.setSelectedIndex(0);
        }

        // Create buttons and actionListener
        clockInBtn = new JButton("Clock In");
        clockOutBtn = new JButton("Clock Out");

        clockInBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clockInBtnActionPerformed(evt);
            }
        });

        clockOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clockOutBtnActionPerformed(evt);
            }
        });

        // Create the input panel for clocking in
        JPanel inputPanel = new JPanel();
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cboEmpList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(clockInBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(clockOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(88, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap(124, Short.MAX_VALUE)
                        .addComponent(cboEmpList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(clockInBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(clockOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(88, 88, 88)));

        return inputPanel;
    }

    /**
     * Performs the action for employee list combo box.
     *
     * @param evt
     */
    private void cboEmpListActionPerformed(java.awt.event.ActionEvent evt) {
        // if first item not equal to a string, then we should be able 
        // to cast as an Employee object
        if (cboEmpList.getItemCount() > 0) {
            if ((cboEmpList.getItemAt(0)).getClass() != "".getClass()) {
                empSelectedEmployee = (Employee) cboEmpList.getSelectedItem();
                strSelectedEmployee = empSelectedEmployee.getFirstName();
            }
        }
    }

    /**
     * Performs the action for clock in button.
     *
     * @param evt
     */
    private void clockInBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (!empSelectedEmployee.getIsClockedIn_bool()) {
            String inputPass = InputDialog.showPasswordInput();
            
            // Check for correct password
            if (validatePassword(inputPass,false)) {            
                (new ClockInWorker(this, empSelectedEmployee)).execute();
                Debugger.log(strSelectedEmployee + " clocked in at " + new Date());
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
                Debugger.log("Wrong password Clock In: " + new Date());
            }
        } else {
            JOptionPane.showMessageDialog(null, "User Already Clocked In.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Performs the action for clock out button.
     *
     * @param evt
     */
    private void clockOutBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (empSelectedEmployee.getIsClockedIn_bool()) {
            String inputPass = InputDialog.showPasswordInput();
            
            // Check for correct password
            if (validatePassword(inputPass,false)) {
                (new ClockOutWorker(this, empSelectedEmployee)).execute();
                Debugger.log(strSelectedEmployee + " clocked out at " + new Date());
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
                Debugger.log("Wrong password Clock Out: " + new Date());
            }
        } else {
            JOptionPane.showMessageDialog(null, "User Not Clocked In.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This creates the Timer for the time clock window.
     */
    private void createTimer() {
        this.timer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                lblHeading.setText(DateUtils.getTime());
            }
        });
    }

    /**
     * Adds a new employee to the list
     *
     * @param emp
     */
    public void addEmployeeToList(Employee emp) {
        // Add to ArrayList and Add to cboEmpList
        EmpNames.add(emp);

        // Sort the list
        Collections.sort(EmpNames);

        // Add all Employees to list
        for (Employee aEmp : EmpNames) {
            cboEmpList.addItem(aEmp);
        }

        // Set to first name in list
        cboEmpList.setSelectedIndex(0);
    }

    /**
     * Deletes the employee from the list.
     *
     * @param emp
     */
    public void deleteEmployeeFromList(Employee emp) {
        int empIndex = 0;
        boolean found = false;
        while (empIndex < EmpNames.size() && found == false) {
            if (EmpNames.get(empIndex).getEmployeeID() == emp.getEmployeeID()) {
                Debugger.log("Deleted Employee " + EmpNames.get(empIndex).toString());
                EmpNames.remove(empIndex);
                found = true;
            }
            empIndex++;
        }

        // Add the Employee list to the combobox
        for (Employee aEmp : EmpNames) {
            cboEmpList.addItem(aEmp);
        }

        empSelectedEmployee = EmpNames.get(0);
        strSelectedEmployee = empSelectedEmployee.getFirstName();
    }

    /**
     * Updates the employees in the list.
     *
     * @param emp
     */
    public void updateEmployeeInfoInList(Employee emp) {
        int empIndex = 0;
        boolean found = false;
        while (empIndex < EmpNames.size() && found == false) {
            if (EmpNames.get(empIndex).getEmployeeID() == emp.getEmployeeID()) {
                emp.setClockedIn(EmpNames.get(empIndex).getIsClockedIn_bool());
                EmpNames.set(empIndex, emp);
                found = true;
                Debugger.log("Updated info for " + EmpNames.get(empIndex).toString());
            }
            empIndex++;
        }

        // Sort the list
        Collections.sort(EmpNames);

        // Add the Employee list to the combobox
        for (Employee aEmp : EmpNames) {
            cboEmpList.addItem(aEmp);
        }

        empSelectedEmployee = emp;
        strSelectedEmployee = empSelectedEmployee.getFirstName();
    }

    /**
     * Updates the clock status for the employee list.
     *
     * @param emp
     * @param in
     */
    public void clockEmployee(Employee emp, boolean in) {
        int empIndex = 0;
        boolean found = false;
        while (empIndex < EmpNames.size() && found == false) {
            if (EmpNames.get(empIndex).getEmployeeID() == emp.getEmployeeID()) {
                EmpNames.get(empIndex).setClockedIn(in);
                found = true;
                Debugger.log("Updated Clock Status for " + EmpNames.get(empIndex).toString());
            }
            empIndex++;
        }
    }

    /**
     * Method to stop clock and shutdown the database.
     */
    private void shutDown() {
        this.timer.stop();
        DbConnection.shutdownDerby();
    }

    /**
     * Validate the users password, and also validate if user has admin 
     * privileges.
     * @param pass the entered password
     * @param admin if admin privileges is required
     * @return 
     */
    private boolean validatePassword(String pass,boolean admin)
    {
        // User hit cancel
        if (pass == null) {
            return false;
        }            
        // Password encryption
        PassEncryptor passEn = new PassEncryptor();
        /* If admin privilege not required, just check that the employee 
         * and pass are a match */
        if(!admin)
            return passEn.checkPassword(pass,empSelectedEmployee.getPassword());
        else
        {
            /* If admin is required, check that selected employee has admin
             * privileges and the pass is a match or check if the master password
             * was entered as an override */
            boolean isAdmin = empSelectedEmployee.getIsAdmin_bool();
            boolean isPassMatch = passEn.checkPassword(pass,empSelectedEmployee.getPassword());
            boolean isMasterPass = passEn.checkPassword(pass, this.ADMIN_PASS);
            return (isAdmin && isPassMatch)|| isMasterPass;
            // Uncomment to bypass password
            //return true;
        }
    }
}
