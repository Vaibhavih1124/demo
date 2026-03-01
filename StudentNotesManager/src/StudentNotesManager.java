import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentNotesManager {

    public static void main(String[] args) {
        // Launch GUI safely
        SwingUtilities.invokeLater(() -> {
            new StudentNotesManager().createGUI();
        });
    }

    void createGUI() {
        JFrame frame = new JFrame("Student Notes Manager");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Student Notes Manager", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btn = new JButton("Open Notes");

        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.add(btn, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
