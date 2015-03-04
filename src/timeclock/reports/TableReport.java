/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timeclock.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import timeclock.utilities.Debugger;
//import timeclock.utilities.FileChooser;

/**
 * Base class for reports that contain a table.
 * @author dannyjdelanojr
 */
abstract class TableReport {
    private final String fileName;
    private final String title;
    private final List<Object> list;
   
    TableReport(List l,String fn,String t){
        this.list = new ArrayList<Object>(l);
        this.fileName = fn;
        this.title = t;
    }
    
    /**
     * Creates the PDF with the employee list.
     */
    public void createPdf() {
        // Step 1 - create the Document
        Document document = new Document(PageSize.A4, 36, 36, 54, 54);
        try {            
            // Step 2 - create the PdfWriter and arguements
            // TODO: FILE_CHOOSER - Change how I get the file name and location, put dialog here
            // FileChooser fc = new FileChooser();
            // this.fileName = fc.showDialog("Employees");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(this.fileName));
            HeaderFooter event = new HeaderFooter(this.title, MyFonts.font.get("title"));
            writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
            writer.setPageEvent(event);
            // Step 3 - Open the document
            document.open();
            // Step 4 - create and add content
            PdfPTable table = createTable(this.list);            
            document.add(table);
            // Step 5 - close the document
            document.close(); // no need to close PDFwriter?
        } catch (DocumentException | FileNotFoundException e) {
            Debugger.log(e);
        }
    }
    
    abstract PdfPTable createTable(List list);
}
