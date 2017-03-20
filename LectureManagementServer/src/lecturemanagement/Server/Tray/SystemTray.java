/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Tray;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Amr
 */
public class SystemTray {

    private static final String iconImageLoc = "http://icons.iconarchive.com/icons/martz90/circle-addon2/24/google-translate-icon.png";
    private Stage stage;
    private Timer notificationTimer = new Timer();
    private DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

    private void addAppToTray() {
        try {
            java.awt.Toolkit.getDefaultToolkit();
            if (!java.awt.SystemTray.isSupported()) {
                Platform.exit();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(iconImageLoc);
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));
            // if the user selects the default menu item (which includes the app name), 
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("Show Window");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                notificationTimer.cancel();
                Platform.exit();
                tray.remove(trayIcon);
            });
            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // create a timer which periodically displays a notification message.
            notificationTimer.schedule(
                    new TimerTask() {
                @Override
                public void run() {
                    javax.swing.SwingUtilities.invokeLater(()
                            -> trayIcon.displayMessage(
                                    "Notice",
                                    "Don't forget using - CTRL + C - to get the translation ",
                                    java.awt.TrayIcon.MessageType.INFO
                            )
                    );
                }
            }, 5000, 1500000);

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    public SystemTray(Stage stage) {
        this.stage = stage;
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
        Platform.setImplicitExit(false);
    }

}
