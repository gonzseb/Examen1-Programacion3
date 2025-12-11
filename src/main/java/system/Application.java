package system;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import system.presentation.board.BoardView;
import system.presentation.board.Controller;
import system.presentation.board.Model;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Application {
    public static void main(String[] args) {
        // --- 1. Set FlatLaf Look and Feel ---
        try {
            // Clean look
            FlatMacDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf. Falling back to default L&F.");
            // If FlatLaf fails (e.g., jar missing), the application will still run.
        }

        BoardView boardView = new BoardView();
        Model model = new Model();
        Controller controller = new Controller(boardView, model);

        JFrame window = new JFrame("Project Management System");
        window.setSize(1100, 600);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // --- 2. Load and Set Application Icon (Logo) ---
        // We load the image using the classpath, assuming 'logo.png' is in src/main/resources/images/
        try {
            // The path must start with '/' to indicate the root of the classpath (your 'resources' folder)
            URL iconURL = Application.class.getResource("/images/appLogo.png");
            if (iconURL != null) {
                Image iconImage = new ImageIcon(iconURL).getImage();
                window.setIconImage(iconImage);
            } else {
                System.err.println("Icon file not found at /images/appLogo.png");
            }
        } catch (Exception e) {
            System.err.println("Error loading application icon: " + e.getMessage());
        }

        // Create tabbed pane
        JTabbedPane tabs = new JTabbedPane();

        // BoardView panel as a tab
        tabs.addTab("Projects", boardView.getPanel());

        // Set tabbed pane as window content
        window.setContentPane(tabs);

        window.setVisible(true);

    }

    // Keep your custom error color
    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);
}