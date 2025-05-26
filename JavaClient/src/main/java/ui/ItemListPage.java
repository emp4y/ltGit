package ui;

import api.ItemPoster;
import api.ApiClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ItemListPage extends JFrame {

    private int floor;
    private String roomNumber;
    private DefaultListModel<String> listModel;
    private JList<String> itemList;

    public ItemListPage(int floor, String roomNumber) {
        this.floor = floor;
        this.roomNumber = roomNumber;

        setTitle(roomNumber + " өрөөний алдагдсан зүйлс");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Font baseFont = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(roomNumber + " өрөөний алдагдсан зүйлс", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();

        String json = """
{
  "floor": %d,
  "location": "%s"
}
""".formatted(floor, roomNumber);
        try {
            String response = ApiClient.post("/items/search", json);
            JSONArray arr = new JSONArray(response);

            String[] columns = {"ID", "Name", "Floor", "Location", "Found By", "Commission"};
            Object[][] data = new Object[arr.length()][columns.length];

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                listModel.addElement(obj.getString("name") + " " + obj.getString("found_by"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to server.");
        }

        itemList = new JList<>(listModel);
        itemList.setFont(baseFont);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(245, 245, 245));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addButton = new JButton("Нэмэх");
        JButton removeButton = new JButton("Устгах");
        JButton backButton = new JButton("Буцах");

        // Style buttons uniformly
        JButton[] buttons = {addButton, removeButton, backButton};
        for (JButton btn : buttons) {
            btn.setFont(baseFont);
            btn.setBackground(new Color(0, 102, 204));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setPreferredSize(new Dimension(100, 35));

            // Hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(0, 82, 168));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(0, 102, 204));
                }
            });
        }

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(backButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Button actions:
        // Add new item dialog
        addButton.addActionListener(e -> {
            String newItem = JOptionPane.showInputDialog(this, "Шинэ зүйлсийн мэдээлэл оруулна уу:", "Нэмэх", JOptionPane.PLAIN_MESSAGE);
            String response = ItemPoster.insertItem(newItem.trim(), floor, roomNumber, 1);
            JOptionPane.showMessageDialog(null, response);
            if (newItem != null && !newItem.trim().isEmpty()) {
                listModel.addElement(newItem.trim());
            }
        });

        // Remove selected item confirmation
        removeButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Сонгосон зүйлсийг устгах уу?",
                        "Устгах баталгаажуулалт",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    listModel.remove(selectedIndex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Устгах зүйлсийг сонгоно уу.", "Анхааруулга", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Back button returns to floor map
        backButton.addActionListener(e -> {
            dispose();
            new FloorMapPage(floor);
        });

        add(mainPanel);
        setVisible(true);
    }
}
