package com.nutrisci.ui;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The entry point of the NutriSci application.
 * <p>
 * This class is responsible for initializing the UI look and feel (Nimbus),
 * setting up the mock database, and launching the {@link LoginUI} window.
 * </p>
 *
 * <p><b>Note:</b> This class currently uses a {@link MockUserDataBase} for testing purposes.
 * Replace it with the actual backend database integration in production.</p>
 */
public class Main {

    /**
     * The main method that launches the NutriSci application UI.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                // Setup: Initialize the mock profile database (for testing purposes)
                MockUserDataBase profileDatabase = new MockUserDataBase();

                // Attempt to set the Nimbus Look and Feel for consistent modern UI appearance
                for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        try {
                            UIManager.setLookAndFeel(info.getClassName());
                        } catch (ClassNotFoundException |
                                 InstantiationException |
                                 IllegalAccessException |
                                 UnsupportedLookAndFeelException e) {
                            e.printStackTrace(); // Log look-and-feel exceptions
                        }
                        break;
                    }
                }

                // Launch the login interface using the mock database
                new LoginUI(profileDatabase.getProfileDB()).show();
            }
        });
    }
}
