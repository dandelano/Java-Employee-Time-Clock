/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.ArrayList;
import java.util.List;
import timeclock.objects.Employee;
import timeclock.utilities.DateUtils;

/**
 * The table report that lists all employees in the database.
 * @author demont-imac
 */
public class EmployeeListReport extends TableReport{
    
    /**
     * Constructor to pass the list of employees to the report generator.
     *
     * @param empList
     */
    public EmployeeListReport(ArrayList<Employee> empList) {
        // TODO: FILE_CHOOSER - Implement file chooser for save locations
        super(empList,"Employees/" + DateUtils.getFileDate() 
                + "_Employees.pdf","Current Employees as of " 
                + DateUtils.getDate("MM/dd/yyyy"));                             
    }

    @Override
    public PdfPTable createTable(List list) {
        PdfPTable table = new PdfPTable(new float[]{2, 3, 4, 1, 1});
        table.setWidthPercentage(100f);
        PdfPCell dCell = table.getDefaultCell();
        dCell.setUseAscender(true);
        dCell.setUseDescender(true);
        dCell.setPadding(3);
        dCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        dCell.setBackgroundColor(BaseColor.DARK_GRAY);

        table.addCell(new Phrase("Employee ID", MyFonts.font.get("header")));
        table.addCell(new Phrase("First Name", MyFonts.font.get("header")));
        table.addCell(new Phrase("Last Name", MyFonts.font.get("header")));
        table.addCell(new Phrase("Salary", MyFonts.font.get("header")));
        table.addCell(new Phrase("Admin", MyFonts.font.get("header")));

        dCell.setBackgroundColor(null);
        table.setHeaderRows(1);
        table.setFooterRows(0);

        // used for alternating row color
        int rowCount = 0;
        // Display all employees
        for (Object obj : list) {
            Employee emp = (Employee)obj;
            rowCount++;
            dCell.setBackgroundColor((rowCount % 2 == 0 ?BaseColor.LIGHT_GRAY : null));
            
            table.addCell(new Paragraph(Integer.toString(emp.getEmployeeID()), MyFonts.font.get("normal")));
            table.addCell(new Paragraph(emp.getFirstName(), MyFonts.font.get("normal")));
            table.addCell(new Paragraph(emp.getLastName(), MyFonts.font.get("normal")));
            table.addCell(new Paragraph(emp.getIsSalary_bool() ? "Yes" : "No", MyFonts.font.get("normal")));
            table.addCell(new Paragraph(emp.getIsAdmin_bool() ? "Yes" : "No", MyFonts.font.get("normal")));
        }

        return table;
    }
}
