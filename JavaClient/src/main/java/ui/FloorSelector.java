package ui;

import javax.swing.*;
import java.awt.*;

public class FloorSelector extends JFrame {

    public FloorSelector() {
        setTitle("Давхар сонгох");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set a modern, readable font
        Font coolFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Panel with vertical layout and spacing
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Title label
        JLabel label = new JLabel("Давхараа сонгоно уу", SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // space

        // Buttons
        JButton btn7 = createStyledButton("7-р давхар", coolFont);
        JButton btn8 = createStyledButton("8-р давхар", coolFont);
        JButton btn9 = createStyledButton("9-р давхар", coolFont);

        // Add buttons to panel
        panel.add(btn7);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btn8);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btn9);

        // Button actions
        btn7.addActionListener(e -> openFloor(7));
        btn8.addActionListener(e -> openFloor(8));
        btn9.addActionListener(e -> openFloor(9));

        add(panel);
        setVisible(true);
    }

    // Helper to style buttons consistently
    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 82, 168));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204));
            }
        });

        return button;
    }

    private void openFloor(int floor) {
        dispose(); // Close this selector
        new FloorMapPage(floor); // Open next screen (you must define FloorMapPage)
    }
}
