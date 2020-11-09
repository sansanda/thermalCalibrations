package classesForTesting;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
  static Image image = Toolkit.getDefaultToolkit().getImage("trayIcon.gif");

  static TrayIcon trayIcon = new TrayIcon(image, "Temperature Calibration");

  public static void main(String[] a) throws Exception {
    if (SystemTray.isSupported()) {
      SystemTray tray = SystemTray.getSystemTray();

      trayIcon.setImageAutoSize(true);
      trayIcon.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println("In here");
          trayIcon.displayMessage("Temperature Calibration!", "TTCs", TrayIcon.MessageType.INFO);
        }
      });

      try {
        tray.add(trayIcon);
        Thread.sleep(2000);
        tray.remove(trayIcon);
      } catch (AWTException e) {
        System.err.println("TrayIcon could not be added.");
      }
    }
  }
}
