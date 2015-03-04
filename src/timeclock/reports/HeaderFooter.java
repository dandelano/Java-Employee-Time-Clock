/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Event class that creates page header and footer.
 * @author demont-imac
 */
public class HeaderFooter extends PdfPageEventHelper {    
    // Phrase for the header.
    Phrase header;
    // Current page number.
    int pagenumber;

    public HeaderFooter(String head, Font font) {
        header = new Phrase(head, font);
    }

    /**
     * Increase the page number.
     *
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
     * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        pagenumber++;
    }

    /**
     * Adds the header and the footer.
     *
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
     * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");

        // Show page header at top center
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, header,
                (rect.getLeft() + rect.getRight()) / 2, rect.getTop() + 12, 0);

        // Show page number at bottom center
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
    }
}
