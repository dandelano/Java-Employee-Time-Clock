/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timeclock.reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper class for fonts used in reports.
 * @author dannyjdelanojr
 */
public class MyFonts {
    
    public static final Map<String, Font> font;
    static {
        Map<String, Font> aMap = new HashMap<>();        
        aMap.put("title", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
        aMap.put("header", new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.WHITE));
        aMap.put("normal", new Font(Font.FontFamily.HELVETICA, 12));
        aMap.put("bold", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        font = Collections.unmodifiableMap(aMap);
    }
}
