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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import timeclock.objects.Employee;
import timeclock.objects.TimePunch;
import timeclock.utilities.DateUtils;

/**
 * The table report that lists the time punches for a given employee.
 * @author demont-imac
 */
public class EmployeeTimeReport extends TableReport{
    private final Employee employee;
    
    /**
     *
     * @param emp
     * @param punchList
     * @param start
     * @param end
     */
    public EmployeeTimeReport(Employee emp, ArrayList<TimePunch> punchList,Timestamp start,Timestamp end) {
        // TODO: FILE_CHOOSER - Implement file chooser for save locations
        super(punchList,"Timesheets/" + emp.getEmployeeID() 
                + "_" + DateUtils.getFileDate() + "_TimeSheet.pdf",emp.getFirstName() + "'s timesheet for " 
                + DateUtils.getDateFromTimestamp(start) + " to "
                + DateUtils.getDateFromTimestamp(end));     
        this.employee = emp;
    }

    @Override
    public PdfPTable createTable(List list) {
        // Create table and set basic table properties
        PdfPTable table = new PdfPTable(new float[]{1, 4, 4, 3});
        table.setWidthPercentage(100f);
        PdfPCell dCell = table.getDefaultCell();
        dCell.setUseAscender(true);
        dCell.setUseDescender(true);
        dCell.setPadding(3);
        dCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // Set header background color
        dCell.setBackgroundColor(BaseColor.DARK_GRAY);

        // Display the employee first in header
        table.addCell(new Phrase(Integer.toString(employee.getEmployeeID()), MyFonts.font.get("header")));
        table.addCell(new Phrase(employee.getFirstName(), MyFonts.font.get("header")));
        table.addCell(new Phrase(employee.getLastName(), MyFonts.font.get("header")));
        table.addCell(new Phrase("", MyFonts.font.get("header")));
        // Display the column names in header
        table.addCell(new Phrase("", MyFonts.font.get("header")));
        table.addCell(new Phrase("Time In", MyFonts.font.get("header")));
        table.addCell(new Phrase("Time Out", MyFonts.font.get("header")));
        table.addCell(new Phrase("Time Worked", MyFonts.font.get("header")));

        // Reset the cell background color
        dCell.setBackgroundColor(null);
        // Set the number of rows included in the header and footer
        table.setHeaderRows(2);
        table.setFooterRows(0);

        // Make a variable to hold the total of all time worked            
        long totalTimeDiff = 0;
        // used for alternating row color
        int rowCount = 0;
        // Display all time punches
        for (Object obj : list) {
            TimePunch tp = (TimePunch)obj;
            rowCount++;
            dCell.setBackgroundColor((rowCount % 2 == 0 ?BaseColor.LIGHT_GRAY : null));            
            
            Timestamp ts1, ts2;
            ts1 = tp.getPunchInTimestamp();
            ts2 = tp.getPunchOutTimestamp();
            totalTimeDiff += ts2.getTime() - ts1.getTime();
            String id = Integer.toString(tp.getPunchId());
            String in = DateUtils.getDateTimeFromTimestamp(ts1);
            String out = DateUtils.getDateTimeFromTimestamp(ts2);
            String diff = DateUtils.getTimestampDiff_Str(ts1, ts2);
            // Add information to row
            table.addCell(new Paragraph("", MyFonts.font.get("normal")));
            table.addCell(new Paragraph(in, MyFonts.font.get("normal")));
            table.addCell(new Paragraph(out, MyFonts.font.get("normal")));
            table.addCell(new Paragraph(diff, MyFonts.font.get("normal")));
        }

        // Set background to null
        dCell.setBackgroundColor(null);
        // Get total time worked and create a totals row
        table.addCell(new Paragraph("", MyFonts.font.get("normal")));
        table.addCell(new Paragraph("", MyFonts.font.get("normal")));
        table.addCell(new Paragraph("Total:", MyFonts.font.get("bold")));
        table.addCell(new Paragraph(DateUtils.getTimeStrFromLong(totalTimeDiff), MyFonts.font.get("bold")));

        return table;
    }
}
