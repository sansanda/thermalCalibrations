package filesManagement;

import java.io.*;

/** Filter to work with JFileChooser to select java file types. **/
public class TxtFilter extends javax.swing.filechooser.FileFilter
{
  public boolean accept (File f) {
    return f.getName ().toLowerCase ().endsWith (".txt")
          || f.isDirectory ();
  }
  
  public String getDescription () {
    return "Text files (*.txt)";
  }
} // class JavaFilter