package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class FloorMapPage extends JFrame {
    private int selectedFloor;
    private JLabel floorImageLabel;
    private HashMap<String, Rectangle> roomAreas;

    public FloorMapPage(int floor) {
        this.selectedFloor = floor;
        setTitle(floor + "-р давхрын зураглал");
        setSize(950, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font coolFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Top label
        JLabel titleLabel = new JLabel(floor + "-р давхрын өрөөнүүд", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Load and scale image
        ImageIcon originalIcon = new ImageIcon("src/resources/floorplan.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(850, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        floorImageLabel = new JLabel(scaledIcon);
        floorImageLabel.setPreferredSize(new Dimension(850, 600));
        floorImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        defineRoomClickAreas();

        floorImageLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point click = e.getPoint();
                for (String room : roomAreas.keySet()) {
                    if (roomAreas.get(room).contains(click)) {
                        openItemList(room);
                        break;
                    }
                }
            }

            public void mouseMoved(MouseEvent e) {
                Point hover = e.getPoint();
                boolean inside = false;
                for (Rectangle area : roomAreas.values()) {
                    if (area.contains(hover)) {
                        inside = true;
                        break;
                    }
                }
                floorImageLabel.setCursor(inside ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
            }
        });

        floorImageLabel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                Point hover = e.getPoint();
                boolean overRoom = false;
                for (Rectangle r : roomAreas.values()) {
                    if (r.contains(hover)) {
                        overRoom = true;
                        break;
                    }
                }
                floorImageLabel.setCursor(overRoom ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
            }
        });

        JScrollPane scrollPane = new JScrollPane(floorImageLabel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Layout
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void defineRoomClickAreas() {
        roomAreas = new HashMap<>();

        // ⚠️ Coordinates depend on actual image. Adjust based on your scaled image
        roomAreas.put("701", new Rectangle(70, 160, 60, 45));
        roomAreas.put("702", new Rectangle(150, 160, 60, 45));
        roomAreas.put("703", new Rectangle(230, 160, 60, 45));
        roomAreas.put("704", new Rectangle(310, 160, 60, 45));
        roomAreas.put("705", new Rectangle(390, 160, 60, 45));
        roomAreas.put("706", new Rectangle(470, 160, 60, 45));
        roomAreas.put("707", new Rectangle(550, 160, 60, 45));
    }

    private void openItemList(String roomNumber) {
        dispose();
        new ItemListPage(selectedFloor, roomNumber);
    }
}
