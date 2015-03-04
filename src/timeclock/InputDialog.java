/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import timeclock.crypto.PassEncryptor;
import timeclock.objects.Employee;
import timeclock.utilities.RequestFocusListener;

/**
 * This contains all of the Input dialogs used to get information from the user.
 *
 * @author dannyjdelanojr
 */
public final class InputDialog {

    private static final int MAX_INPUT_LENGTH = 10;

    /**
     * This shows a dialog for entering a password up to the length of the
     * MAX_INPUT_LENGTH constant.
     *
     * @return The password as String or null
     */
    public static String showPasswordInput() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password:");
        JPasswordField pass = new JPasswordField(InputDialog.MAX_INPUT_LENGTH);
        pass.addHierarchyListener(new RequestFocusListener());
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Enter Password",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) // pressing OK button
        {
            char[] password = pass.getPassword();
            String thePass = new String(password);
            return thePass;
        } else {
            return null;
        }
    }

    /**
     * This shows a confirmation dialog for deleting an Employee from the
     * application, requires the user to enter a password as acceptance.
     *
     * @param emp
     * @return The password as String or null
     */
    public static String showDeleteEmployeeConfirm(Employee emp) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Build message
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body width='440'>");
        sb.append("<h3>Are you sure you want to delete ");
        sb.append(emp.getEmployeeID());
        sb.append(" ");
        sb.append(emp.getFirstName());
        sb.append(" ");
        sb.append(emp.getLastName());
        sb.append("?</h3>");
        sb.append("<h5>WARNING: All information for this employee will be deleted!</h5>");
        JLabel lblMsg = new JLabel(sb.toString());

        // Build sub panel with password and label
        JLabel lblPass = new JLabel("Enter 'DELETE' to confirm:");
        JTextField pass = new JTextField(InputDialog.MAX_INPUT_LENGTH);
        pass.addHierarchyListener(new RequestFocusListener());
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());
        subPanel.add(lblPass, BorderLayout.NORTH);
        subPanel.add(pass, BorderLayout.SOUTH);

        panel.add(lblMsg, BorderLayout.CENTER);
        panel.add(subPanel, BorderLayout.SOUTH);

        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Delete User",
                JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);

        if (option == 0) // pressing OK button
        {
            
            return pass.getText().trim();
        } else {
            return null;
        }
    }

    /**
     * This shows a dialog for entering a new Employees information.
     *
     * @return The Employee object created with the users input or null.
     */
    public static Employee showEmployeeInput() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JLabel lblFirst = new JLabel("Enter the Emplyee's First Name:");
        JTextField txtFirst = new JTextField(20);
        txtFirst.addHierarchyListener(new RequestFocusListener());

        JLabel lblLast = new JLabel("Enter the Emplyee's Last Name:");
        JTextField txtLast = new JTextField(20);

        JLabel lblSalary = new JLabel("Check if the Employee is Salary:");
        JCheckBox cbSalary = new JCheckBox();
        cbSalary.setSelected(false);

        JLabel lblAdmin = new JLabel("Check if the Employee is Admin:");
        JCheckBox cbAdmin = new JCheckBox();
        cbAdmin.setSelected(false);

        JLabel lblPass = new JLabel("Enter a Password for Employee:");
        JTextField txtPass = new JTextField(InputDialog.MAX_INPUT_LENGTH);

        // Add all components to the panel
        panel.add(lblFirst);
        panel.add(txtFirst);
        panel.add(lblLast);
        panel.add(txtLast);
        panel.add(lblSalary);
        panel.add(cbSalary);
        panel.add(lblAdmin);
        panel.add(cbAdmin);
        panel.add(lblPass);
        panel.add(txtPass);

        // Set the names for the buttons
        String[] options = new String[]{"OK", "Cancel"};

        // Display the dialog and get the button pressed
        int option = JOptionPane.showOptionDialog(null, panel, "Enter Employee Information",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) // pressing OK button
        {
            String first = txtFirst.getText();
            String last = txtLast.getText();
            int salary = (cbSalary.isSelected()) ? 1 : 0;
            int admin = (cbAdmin.isSelected()) ? 1 : 0;                                                       
            String pass = txtPass.getText().trim();
            
            if (!first.isEmpty() && !last.isEmpty() && !pass.isEmpty()) {
                // Get password and encrypt it                                   
                PassEncryptor passEn = new PassEncryptor();                                    
                String encryptedPass = passEn.encryptPassword(pass);
                Employee emp = new Employee(first, last, salary, admin, encryptedPass);
                return emp;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * This shows a dialog for editing an Employees information.
     *
     * @param oldEmp is the Employee to edit
     * @return The Employee object created with the users input or null.
     */
    public static Employee showEmployeeInput(Employee oldEmp) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JLabel lblFirst = new JLabel("Enter the Emplyee's First Name:");
        JTextField txtFirst = new JTextField(20);
        // Get current value
        txtFirst.setText(oldEmp.getFirstName());
        txtFirst.addHierarchyListener(new RequestFocusListener());

        JLabel lblLast = new JLabel("Enter the Emplyee's Last Name:");
        JTextField txtLast = new JTextField(20);
        // Get current value
        txtLast.setText(oldEmp.getLastName());

        JLabel lblSalary = new JLabel("Check if the Employee is Salary:");
        JCheckBox cbSalary = new JCheckBox();
        // Get current value
        cbSalary.setSelected(oldEmp.getIsSalary_bool());

        JLabel lblAdmin = new JLabel("Check if the Employee is Admin:");
        JCheckBox cbAdmin = new JCheckBox();
        // Get current value
        cbAdmin.setSelected(oldEmp.getIsAdmin_bool());

        // TODO: PASSWORD - remove when new change password method created
        JLabel lblPass = new JLabel("Enter Only if Changing Password:");
        JTextField txtPass = new JTextField(InputDialog.MAX_INPUT_LENGTH);
        // Get current value
        txtPass.setText("");

        // Add all components to the panel
        panel.add(lblFirst);
        panel.add(txtFirst);
        panel.add(lblLast);
        panel.add(txtLast);
        panel.add(lblSalary);
        panel.add(cbSalary);
        panel.add(lblAdmin);
        panel.add(cbAdmin);
        panel.add(lblPass);
        panel.add(txtPass);

        // Set the names for the buttons
        String[] options = new String[]{"OK", "Cancel"};

        // Display the dialog and get the button pressed
        int option = JOptionPane.showOptionDialog(null, panel, "Enter Employee Information",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) // pressing OK button
        {
            String first = txtFirst.getText();
            String last = txtLast.getText();
            int salary = (cbSalary.isSelected()) ? 1 : 0;
            int admin = (cbAdmin.isSelected()) ? 1 : 0;
            String pass = txtPass.getText().trim();
            // TODO: PASSWORD - Implement a new way of updating passwords for users
            if(pass.length() > 3)
            {
                // new password was entered, encrpyt and set it to update
                PassEncryptor passEn = new PassEncryptor();                                    
                pass = passEn.encryptPassword(pass);
            }else
            {
                // No new password was entered, use old password
                pass = oldEmp.getPassword();
            }

            if (!first.isEmpty() && !last.isEmpty() && !pass.isEmpty()) {
                Employee emp = new Employee(oldEmp.getEmployeeID(), first, last, salary, admin, pass, oldEmp.getEmployeeStatus_int());
                return emp;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * This shows a dialog for selecting a date range for creating reports.
     *
     * @return A Date array containing the start date([0]) and the end date([1])
     * or null.
     */
    public static Date[] showDateTimePicker() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // Get start dates
        Date endDate = new Date();
        Date startDate = new Date(endDate.getTime() - 14 * 24 * 3600 * 1000);
        // Create start date picker
        JLabel lblStart = new JLabel("Start Date:");
        JDateChooser startDateChooser = new JDateChooser();
        startDateChooser.setDate(startDate);
        for (Component comp : startDateChooser.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setColumns(50);
                ((JTextField) comp).setEditable(false);
            }
        }

        // Create end date picker
        JLabel lblEnd = new JLabel("End Date:");
        JDateChooser endDateChooser = new JDateChooser();
        endDateChooser.setDate(endDate);
        for (Component comp : endDateChooser.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setColumns(50);
                ((JTextField) comp).setEditable(false);
            }
        }

        panel.add(lblStart);
        panel.add(startDateChooser);
        panel.add(lblEnd);
        panel.add(endDateChooser);

        // Set the names for the buttons
        String[] options = new String[]{"OK", "Cancel"};

        // Display the dialog and get the button pressed
        int option = JOptionPane.showOptionDialog(null, panel, "Enter Report Dates",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) // pressing OK button
        {
            // Get entered info
            Date[] dates = new Date[2];
            dates[0] = startDateChooser.getDate();
            dates[1] = endDateChooser.getDate();
            return dates;
        } else {
            return null;
        }
    }
}
