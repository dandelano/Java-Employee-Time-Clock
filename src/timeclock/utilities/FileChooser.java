/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.utilities;

import java.io.File;
import javax.swing.JFileChooser;

/**
 * This provides an option to choose the save location of the reports generated.
 *
 * @author demont-imac
 */
public class FileChooser {

    private File file;

    public FileChooser() {
        // TODO: FILE_CHOOSER - Implement a JFileChooser to choose file locations
    }

    public String showDialog(String fileName) {
        fileName += ".pdf";
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Choose save location");
        fc.setSelectedFile(new File(fileName));
        //In response to a button click:
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                this.file = fc.getSelectedFile();
                //This is where a real application would save the file.
                Debugger.log("Saving: " + file.getAbsolutePath() + ".");
                return file.getAbsolutePath();
            } else {
                Debugger.log("Save command cancelled by user.");
                return null;
            }
    }
    
}
