import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainUI {

    static int selectedId = -1;

    public static void main(String[] args) {

        NoteDAO.createTable();

        JFrame frame = new JFrame("Student Notes Manager");
        frame.setSize(800, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Color bg = new Color(200, 240, 255);
        Font font = new Font("Segoe UI", Font.PLAIN, 20);

        // ===== HEADER =====
        JLabel heading = new JLabel("STUDENT NOTES MANAGER", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 50));
        heading.setOpaque(true);
        heading.setBackground(bg);
        frame.add(heading, BorderLayout.NORTH);

        // ===== LEFT PANEL =====
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setFont(font);
        JScrollPane listPane = new JScrollPane(list);

        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Search");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        JPanel left = new JPanel(new BorderLayout());
        left.add(searchPanel, BorderLayout.NORTH);
        left.add(listPane, BorderLayout.CENTER);

        // ===== RIGHT PANEL =====
        JTextField titleField = new JTextField();
        JTextArea contentArea = new JTextArea();
        JCheckBox importantBox = new JCheckBox("IMPORTANT NOTE⭐");

        titleField.setFont(font);
        contentArea.setFont(font);
        importantBox.setBackground(bg);

        JPanel form = new JPanel(new GridLayout(6, 1, 10, 10));
        form.setBackground(bg);
        form.add(new JLabel("TITLE"));
        form.add(titleField);
        form.add(new JLabel("CONTENT"));
        form.add(new JScrollPane(contentArea));
        form.add(importantBox);

        JPanel center = new JPanel(new GridLayout(2, 2));
        center.add(left);
        center.add(form);
        frame.add(center, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JButton addBtn = new JButton("ADD");
        JButton updateBtn = new JButton("UPDATE");
        JButton deleteBtn = new JButton("DELETE");

        addBtn.setBackground(new Color(120, 200, 120));
        updateBtn.setBackground(new Color(255, 200, 120));
        deleteBtn.setBackground(new Color(255, 120, 120));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(bg);
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);

        frame.add(btnPanel, BorderLayout.SOUTH);

        // ===== LOAD NOTES =====
        Runnable loadNotes = () -> {
            model.clear();
            for (String[] n : NoteDAO.getAllNotes()) {
                String star = n[3].equals("1") ? " ⭐" : "";
                model.addElement(n[0] + " - " + n[1] + star);
            }
        };

        loadNotes.run();

        // ===== EVENTS =====
        addBtn.addActionListener(e -> {
            NoteDAO.addNote(
                    titleField.getText(),
                    contentArea.getText(),
                    importantBox.isSelected());
            titleField.setText("");
            contentArea.setText("");
            importantBox.setSelected(false);
            loadNotes.run();
        });

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String sel = list.getSelectedValue();
                if (sel != null) {
                    selectedId = Integer.parseInt(sel.split(" - ")[0]);
                    for (String[] n : NoteDAO.getAllNotes()) {
                        if (Integer.parseInt(n[0]) == selectedId) {
                            titleField.setText(n[1]);
                            contentArea.setText(n[2]);
                            importantBox.setSelected(n[3].equals("1"));
                        }
                    }
                }
            }
        });

        updateBtn.addActionListener(e -> {
            if (selectedId != -1) {
                NoteDAO.updateNote(
                        selectedId,
                        titleField.getText(),
                        contentArea.getText(),
                        importantBox.isSelected());
                loadNotes.run();
            }
        });

        deleteBtn.addActionListener(e -> {
            if (selectedId != -1) {
                NoteDAO.deleteNote(selectedId);
                selectedId = -1;
                titleField.setText("");
                contentArea.setText("");
                importantBox.setSelected(false);
                loadNotes.run();
            }
        });

        searchBtn.addActionListener(e -> {
            model.clear();
            ArrayList<String[]> results = NoteDAO.searchNotes(searchField.getText());
            for (String[] n : results) {
                String star = n[3].equals("1") ? " ⭐" : "";
                model.addElement(n[0] + " - " + n[1] + star);
            }
        });

        frame.setVisible(true);
    }
}
