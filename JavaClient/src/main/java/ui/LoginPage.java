package ui;

import api.ApiClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPage() {
        setTitle("Хаясан гээснээ олоорой");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modern and clean system font
        Font coolFont = new Font("Segoe UI", Font.PLAIN, 14); // Or try "Arial", "Tahoma"

        // Main panel with layout and background
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Нэвтрэх нэр:");
        userLabel.setFont(coolFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(coolFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Нууц үг:");
        passwordLabel.setFont(coolFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(coolFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Нэвтрэх");
        loginButton.setFont(coolFont);
        loginButton.setBackground(new Color(0, 102, 204));  // Strong blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        // Button hover effect
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 82, 168));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 102, 204));
            }
        });

        add(panel);

        // Login logic
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = usernameField.getText();
                String pass = new String(passwordField.getPassword());
                String json = """
{
  "username": "%s",
  "password": "%s"
}
""".formatted(user, pass);
                try {
                    String response = ApiClient.post("/users/authenticate", json);
                    System.out.println("Server response: " + response);

                    if (response.contains("\"authenticated\":true")) {
                        JOptionPane.showMessageDialog(LoginPage.this, "Амжилттай нэвтэрлээ!");
                        dispose();
                        new FloorSelector();
                    } else {
                        JOptionPane.showMessageDialog(LoginPage.this, "Нэр эсвэл нууц үг буруу.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error connecting to server.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new LoginPage();
    }
}
