/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.adminUI;

import phinma_coc_gsd_ticketing_system.adminUI.archives;
import phinma_coc_gsd_ticketing_system.adminUI.viewtickets;
import phinma_coc_gsd_ticketing_system.adminUI.homepageadmin;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;
import phinma_coc_gsd_ticketing_system.frstframes.login;
import phinma_coc_gsd_ticketing_system.userUI.profile;

/**
 *
 * @author ACER
 */
public class workerslist extends javax.swing.JFrame {

    private javax.swing.JPanel workerContainer;
    private javax.swing.JScrollPane scrollPane;
    
    
    public workerslist() {
        initComponents();
        
        setupFullScreenWrapper();
        
        setActiveSidebarButton(jButton4);
        addGoldHoverEffect(jButton1);
        addGoldHoverEffect(jButton2);
        addGoldHoverEffect(jButton8);
        addGoldHoverEffect(jButton15);
        addGoldHoverEffect(jButton6);
        addGoldHoverEffect(jButton5);
        customInit();
    }

    private java.awt.Image bgImage;

    private void setupFullScreenWrapper() {
    this.setSize(1310, 780);
    this.setPreferredSize(new java.awt.Dimension(1310, 780));
    this.setMinimumSize(new java.awt.Dimension(1310, 780));
    this.setLocationRelativeTo(null); // Centers window on screen when minimized/restored

    this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    
    try {
        java.net.URL imgUrl = getClass().getResource("/phinma_coc_gsd_ticketing_system/hand-drawn-school-icons-seamless-pattern-isolated-in-green-board-for-education-background-wallpaper-vector.jpg");
        if (imgUrl != null) {
            bgImage = new javax.swing.ImageIcon(imgUrl).getImage();
        }
    } catch (Exception e) {
        System.err.println("Error loading background image: " + e.getMessage());
    }

    javax.swing.JPanel outerBackgroundPanel = new javax.swing.JPanel(new java.awt.GridBagLayout()) {
        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            if (bgImage != null) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
                g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new java.awt.Color(0, 102, 51));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    };

    jPanel1.setPreferredSize(new java.awt.Dimension(1310, 780));
    jPanel1.setMinimumSize(new java.awt.Dimension(1310, 780));
    jPanel1.setMaximumSize(new java.awt.Dimension(1310, 780));

    this.getContentPane().removeAll();
    outerBackgroundPanel.add(jPanel1);
    this.setContentPane(outerBackgroundPanel);

    this.revalidate();
    this.repaint();
}
    
    private void setActiveSidebarButton(javax.swing.JButton btn) {
    Color activeBg = new Color(255, 204, 51); // Gold background
    Color activeFg = new Color(0, 102, 51);   // Dark Green text

    float normalSize = btn.getFont().getSize2D();
    float activeSize = btn.getText().trim().length() > 17 ? 18.0f : normalSize;

    btn.setContentAreaFilled(true);
    btn.setOpaque(true);
    btn.setBackground(activeBg);
    btn.setForeground(activeFg);
    btn.setFont(btn.getFont().deriveFont(java.awt.Font.BOLD, activeSize));
    btn.setFocusPainted(false);
}
    
    private void addGoldHoverEffect(javax.swing.JButton btn) {
    Color hoverBg = new Color(255, 204, 51); // Gold background
    Color hoverFg = new Color(0, 102, 51);   // Dark Green text

    float normalSize = btn.getFont().getSize2D();
    // Auto-scale down to 16pt for long labels (e.g. "User Administration") to prevent text clipping
    float hoverSize = btn.getText().trim().length() > 17 ? 18.0f : normalSize;

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setBackground(hoverBg);
            btn.setForeground(hoverFg);
            btn.setFont(btn.getFont().deriveFont(java.awt.Font.BOLD, hoverSize));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            btn.setContentAreaFilled(false);
            btn.setOpaque(false);
            btn.setForeground(java.awt.Color.WHITE);
            btn.setFont(btn.getFont().deriveFont(java.awt.Font.PLAIN, normalSize));
        }
    });
}
    
    private void customInit() {
        // --- 1. CONVERT jPanel4 TO DYNAMIC CARD CONTAINER ---
        jPanel4.removeAll();
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.setPreferredSize(new java.awt.Dimension(980, 280));

        // Header Panel
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(null);
        headerPanel.setPreferredSize(new java.awt.Dimension(980, 35));
        headerPanel.setBackground(new java.awt.Color(235, 238, 242));
        headerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(180, 180, 180)));

        String[] headers = {"WORKER CODE", "WORKER'S NAME", "PHONE #", "STATUS", "ACTION"};
        int[] boundsX   = {20,            220,             470,       680,      870};
        int[] boundsW   = {150,           220,             170,       160,      80};

        for (int i = 0; i < headers.length; i++) {
            javax.swing.JLabel lbl = new javax.swing.JLabel(headers[i]);
            lbl.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
            lbl.setHorizontalAlignment(i == 3 || i == 4 ? javax.swing.SwingConstants.CENTER : javax.swing.SwingConstants.LEFT);
            lbl.setBounds(boundsX[i], 7, boundsW[i], 20);
            headerPanel.add(lbl);
        }

        // Dynamic Scrollable Card Container
        workerContainer = new javax.swing.JPanel();
        workerContainer.setLayout(new javax.swing.BoxLayout(workerContainer, javax.swing.BoxLayout.Y_AXIS));
        workerContainer.setBackground(java.awt.Color.WHITE);

        scrollPane = new javax.swing.JScrollPane(workerContainer);
        scrollPane.setPreferredSize(new java.awt.Dimension(980, 245));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 1));
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel4.add(headerPanel, java.awt.BorderLayout.NORTH);
        jPanel4.add(scrollPane, java.awt.BorderLayout.CENTER);

        // --- 2. SEARCH & FILTER INITIALIZATION ---
        jTextField1.setLayout(null);
        jTextField1.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        jTextField1.setText("Search Worker...");
        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setMargin(new java.awt.Insets(2, 10, 2, 115));
        jTextField1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180), 1),
            javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0)
        ));

        jButton14.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        jButton14.setText("Clear Search");
        jButton14.setFocusPainted(false);
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton14.setBackground(new java.awt.Color(242, 242, 242));
        jButton14.setForeground(new java.awt.Color(60, 60, 60));
        jButton14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(180, 180, 180)));

        jTextField1.add(jButton14);
        java.lang.Runnable updateButtonBounds = () -> {
            int w = jTextField1.getWidth() > 0 ? jTextField1.getWidth() : 600;
            int h = jTextField1.getHeight() > 0 ? jTextField1.getHeight() : 35;
            jButton14.setBounds(w - 110, 0, 110, h);
        };
        jTextField1.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                updateButtonBounds.run();
            }
        });
        updateButtonBounds.run();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Unassigned", "Assigned", "Day Off" }));
        jComboBox2.setSelectedIndex(0);

        // Focus & Filter Listeners
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (jTextField1.getText().equals("Search Worker...")) {
                    jTextField1.setText("");
                    jTextField1.setForeground(java.awt.Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jTextField1.getText().trim().isEmpty()) {
                    jTextField1.setForeground(new java.awt.Color(153, 153, 153));
                    jTextField1.setText("Search Worker...");
                }
            }
        });

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { loadWorkersFromDatabase(); }
            public void removeUpdate(DocumentEvent e) { loadWorkersFromDatabase(); }
            public void changedUpdate(DocumentEvent e) { loadWorkersFromDatabase(); }
        });

        jComboBox2.addActionListener(e -> loadWorkersFromDatabase());

        jButton14.addActionListener(e -> {
            jTextField1.setText("Search Worker...");
            jTextField1.setForeground(new java.awt.Color(153, 153, 153));
            jComboBox2.setSelectedIndex(0);
            loadWorkersFromDatabase();
        });

        jButton3.addActionListener(e -> handleAddWorker());

        loadWorkersFromDatabase();

        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private javax.swing.JPanel createWorkerRowCard(String code, String name, String phone, String statusStr) {
        javax.swing.JPanel row = new javax.swing.JPanel(null);
        row.setBackground(java.awt.Color.WHITE);
        row.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(230, 230, 230)));
        row.setPreferredSize(new java.awt.Dimension(970, 48));
        row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 48));

        // Code
        javax.swing.JLabel lblCode = new javax.swing.JLabel(code);
        lblCode.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
        lblCode.setBounds(20, 14, 150, 20);
        row.add(lblCode);

        // Name
        javax.swing.JLabel lblName = new javax.swing.JLabel(name);
        lblName.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        lblName.setBounds(220, 14, 220, 20);
        row.add(lblName);

        // Phone #
        javax.swing.JLabel lblPhone = new javax.swing.JLabel(phone);
        lblPhone.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        lblPhone.setBounds(470, 14, 170, 20);
        row.add(lblPhone);

        // Status Badge Pill
        javax.swing.JLabel lblStatus = new javax.swing.JLabel(statusStr, javax.swing.SwingConstants.CENTER);
        lblStatus.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        lblStatus.setForeground(java.awt.Color.WHITE);
        lblStatus.setOpaque(true);
        
        if (statusStr.contains("Day Off")) {
            lblStatus.setBackground(new java.awt.Color(108, 117, 125)); // Gray badge for Day Off
        } else if (statusStr.contains("Assigned")) {
            lblStatus.setBackground(new java.awt.Color(14, 165, 233));   // Blue badge for Assigned
        } else {
            lblStatus.setBackground(new java.awt.Color(34, 197, 94));    // Green badge for Active/Unassigned
        }
        
        lblStatus.setBounds(670, 12, 160, 24);
        row.add(lblStatus);

        // Action Button
        javax.swing.JButton btnEdit = new javax.swing.JButton("EDIT");
        btnEdit.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setFocusPainted(false);
        btnEdit.setBounds(870, 11, 80, 26);
        btnEdit.addActionListener(e -> handleEditWorkerDialog(code, name, phone, statusStr));
        row.add(btnEdit);

        return row;
    }
    
    public void loadWorkersFromDatabase() {
        if (workerContainer == null) return;
        workerContainer.removeAll();

        String keyword = jTextField1.getText().trim();
        if ("Search Worker...".equals(keyword)) keyword = "";

        String selectedFilter = jComboBox2.getSelectedItem() != null ? jComboBox2.getSelectedItem().toString() : "All";

        String query = "SELECT w.worker_code, w.worker_name, w.phone_number, w.status, " +
                       "(SELECT COUNT(*) FROM tbl_tickets t WHERE t.assigned_worker = w.worker_name AND t.status IN ('Assigned', 'In Progress', 'Accepted', 'Pending')) AS active_tickets " +
                       "FROM tbl_workers w WHERE 1=1";

        if (!keyword.isEmpty()) {
            query += " AND (w.worker_code LIKE ? OR w.worker_name LIKE ? OR w.phone_number LIKE ?)";
        }
        query += " ORDER BY w.worker_id ASC";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (!keyword.isEmpty()) {
                String pat = "%" + keyword + "%";
                pstmt.setString(1, pat);
                pstmt.setString(2, pat);
                pstmt.setString(3, pat);
            }

            ResultSet rs = pstmt.executeQuery();
            boolean hasData = false;

            while (rs.next()) {
                String code = rs.getString("worker_code");
                String name = rs.getString("worker_name");
                String phone = rs.getString("phone_number");
                String dbStatus = rs.getString("status");
                int activeTickets = rs.getInt("active_tickets");

                if (dbStatus == null || dbStatus.trim().isEmpty() || "Unassigned".equalsIgnoreCase(dbStatus) || "Assigned".equalsIgnoreCase(dbStatus)) {
                    dbStatus = "Active";
                } else if ("Off Duty".equalsIgnoreCase(dbStatus) || "Day-Off".equalsIgnoreCase(dbStatus)) {
                    dbStatus = "Day Off";
                }

                String assignmentState = (activeTickets > 0) ? "Assigned" : "Unassigned";
                String combinedStatus = assignmentState + "/" + dbStatus;

                // Dropdown Filter Logic
                if (!"All".equalsIgnoreCase(selectedFilter)) {
                    if ("Day Off".equalsIgnoreCase(selectedFilter) && !"Day Off".equalsIgnoreCase(dbStatus)) continue;
                    if ("Assigned".equalsIgnoreCase(selectedFilter) && !"Assigned".equalsIgnoreCase(assignmentState)) continue;
                    if ("Unassigned".equalsIgnoreCase(selectedFilter) && !"Unassigned".equalsIgnoreCase(assignmentState)) continue;
                }

                hasData = true;
                javax.swing.JPanel card = createWorkerRowCard(code, name, phone, combinedStatus);
                workerContainer.add(card);
            }

            if (!hasData) {
                workerContainer.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 30)));
                javax.swing.JLabel noData = new javax.swing.JLabel("No workers found.");
                noData.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 16));
                noData.setForeground(java.awt.Color.GRAY);
                noData.setAlignmentX(CENTER_ALIGNMENT);
                workerContainer.add(noData);
            }

            updateDashboardCounts();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error Loading Workers: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        workerContainer.revalidate();
        workerContainer.repaint();
    }
    
    
    private void handleEditWorkerDialog(String workerCode, String currentName, String currentPhone, String fullStatus) {
        String currentDutyStatus = fullStatus.contains("/") ? fullStatus.split("/")[1].trim() : fullStatus;
        if (!"Day Off".equalsIgnoreCase(currentDutyStatus)) {
            currentDutyStatus = "Active";
        }

        // 1. Activate background dim overlay on the main window
        setBlurOverlay(true);

        // 2. Create Custom Modal Dialog
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Edit Worker Details", true);
        dialog.setUndecorated(true);
        dialog.setSize(440, 370);
        dialog.setLocationRelativeTo(this);

        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(java.awt.Color.WHITE);
        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 2));

        // --- HEADER PANEL ---
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        headerPanel.setBackground(new java.awt.Color(0, 102, 51));
        headerPanel.setPreferredSize(new java.awt.Dimension(440, 50));
        headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));

        javax.swing.JLabel lblTitle = new javax.swing.JLabel("Edit Worker Details");
        lblTitle.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 16));
        lblTitle.setForeground(java.awt.Color.WHITE);

        javax.swing.JLabel lblBadge = new javax.swing.JLabel("[" + workerCode + "]");
        lblBadge.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
        lblBadge.setForeground(new java.awt.Color(255, 204, 51)); // Gold accent

        headerPanel.add(lblTitle, java.awt.BorderLayout.WEST);
        headerPanel.add(lblBadge, java.awt.BorderLayout.EAST);

        // --- FORM BODY ---
        javax.swing.JPanel bodyPanel = new javax.swing.JPanel();
        bodyPanel.setLayout(new javax.swing.BoxLayout(bodyPanel, javax.swing.BoxLayout.Y_AXIS));
        bodyPanel.setBackground(java.awt.Color.WHITE);
        bodyPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Worker's Name Input
        javax.swing.JLabel lblName = new javax.swing.JLabel("Worker's Name:");
        lblName.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        lblName.setForeground(new java.awt.Color(50, 50, 50));
        lblName.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JTextField txtName = new javax.swing.JTextField(currentName);
        txtName.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13));
        txtName.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        txtName.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        txtName.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
            javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // Phone Number Input
        javax.swing.JLabel lblPhone = new javax.swing.JLabel("Phone Number:");
        lblPhone.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        lblPhone.setForeground(new java.awt.Color(50, 50, 50));
        lblPhone.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JTextField txtPhone = new javax.swing.JTextField(currentPhone);
        txtPhone.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13));
        txtPhone.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        txtPhone.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        txtPhone.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
            javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // Status Dropdown
        javax.swing.JLabel lblStatus = new javax.swing.JLabel("Duty Status:");
        lblStatus.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        lblStatus.setForeground(new java.awt.Color(50, 50, 50));
        lblStatus.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JComboBox<String> comboStatus = new javax.swing.JComboBox<>(new String[]{"Active", "Day Off"});
        comboStatus.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13));
        comboStatus.setSelectedItem("Day Off".equalsIgnoreCase(currentDutyStatus) ? "Day Off" : "Active");
        comboStatus.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        comboStatus.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        comboStatus.setBackground(java.awt.Color.WHITE);

        bodyPanel.add(lblName);
        bodyPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
        bodyPanel.add(txtName);
        bodyPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));
        bodyPanel.add(lblPhone);
        bodyPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
        bodyPanel.add(txtPhone);
        bodyPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));
        bodyPanel.add(lblStatus);
        bodyPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
        bodyPanel.add(comboStatus);

        // --- FOOTER BUTTONS PANEL ---
        javax.swing.JPanel footerPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 12, 12));
        footerPanel.setBackground(new java.awt.Color(245, 247, 250));
        footerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(220, 220, 220)));

        javax.swing.JButton btnCancel = new javax.swing.JButton("Cancel");
        btnCancel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setBackground(new java.awt.Color(220, 225, 230));
        btnCancel.setForeground(new java.awt.Color(50, 50, 50));
        btnCancel.setPreferredSize(new java.awt.Dimension(90, 32));

        javax.swing.JButton btnSave = new javax.swing.JButton("Save Changes");
        btnSave.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setBackground(new java.awt.Color(0, 102, 51));
        btnSave.setForeground(java.awt.Color.WHITE);
        btnSave.setPreferredSize(new java.awt.Dimension(125, 32));

        btnCancel.addActionListener(e -> {
            dialog.dispose();
            setBlurOverlay(false);
        });

        btnSave.addActionListener(e -> {
            String newName = txtName.getText().trim();
            String newPhone = txtPhone.getText().trim();
            String newDutyStatus = (String) comboStatus.getSelectedItem();

            if (newName.isEmpty() || newPhone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Worker's Name and Phone # cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String updateQuery = "UPDATE tbl_workers SET worker_name = ?, phone_number = ?, status = ? WHERE worker_code = ?";

            try (Connection conn = DBConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setString(1, newName);
                pstmt.setString(2, newPhone);
                pstmt.setString(3, newDutyStatus);
                pstmt.setString(4, workerCode);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    dialog.dispose();
                    setBlurOverlay(false);
                    JOptionPane.showMessageDialog(this, "Worker " + workerCode + " updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadWorkersFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update record in database.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Database Update Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        footerPanel.add(btnCancel);
        footerPanel.add(btnSave);

        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
        mainPanel.add(bodyPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(footerPanel, java.awt.BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);
        dialog.setVisible(true);
    }
    
    private void handleAddWorker() {
        String name = jTextField2.getText().trim();
        String phone = jTextField3.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Worker's Name and Phone Number!", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nextCode = generateNextWorkerCode();
        // Default worker status is set to 'Active' upon creation
        String insertQuery = "INSERT INTO tbl_workers (worker_code, worker_name, phone_number, status) VALUES (?, ?, ?, 'Active')";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, nextCode);
            pstmt.setString(2, name);
            pstmt.setString(3, phone);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Worker " + nextCode + " added successfully!");
                jTextField2.setText("");
                jTextField3.setText("");
                loadWorkersFromDatabase();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Insert Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String generateNextWorkerCode() {
    String query = "SELECT MAX(worker_id) AS max_id FROM tbl_workers";
    int nextId = 1;

    try (Connection conn = DBConnection.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        if (rs.next()) {
            nextId = rs.getInt("max_id") + 1;
        }
    } catch (SQLException e) {
        nextId = 1;
    }

    return String.format("W-%04d", nextId);
}

    private void updateDashboardCounts() {
        int unassigned = 0;
        int assigned = 0;
        int dayOff = 0;
        int total = 0;

        String query = "SELECT w.status, " +
                       "(SELECT COUNT(*) FROM tbl_tickets t WHERE t.assigned_worker = w.worker_name AND t.status IN ('Assigned', 'In Progress', 'Accepted', 'Pending')) AS active_tickets " +
                       "FROM tbl_workers w";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                total++;
                String dbStatus = rs.getString("status");
                int activeTickets = rs.getInt("active_tickets");

                if ("Off Duty".equalsIgnoreCase(dbStatus) || "Day Off".equalsIgnoreCase(dbStatus) || "Day-Off".equalsIgnoreCase(dbStatus)) {
                    dayOff++;
                }

                if (activeTickets > 0) {
                    assigned++;
                } else {
                    unassigned++;
                }
            }
        } catch (SQLException ex) {
            // Keep existing values on error
        }

        jLabel17.setText(String.valueOf(unassigned));
        jLabel18.setText(String.valueOf(assigned));
        jLabel19.setText(String.valueOf(dayOff));
        jLabel20.setText(String.valueOf(total));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(222, 252, 232));

        jPanel2.setBackground(new java.awt.Color(0, 102, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GSD TICKETING SYSTEM");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(930, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBackground(new java.awt.Color(2, 153, 78));
        jPanel3.setPreferredSize(new java.awt.Dimension(208, 408));

        jButton1.setBackground(new java.awt.Color(2, 153, 78));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Home");
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(2, 153, 78));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("View Tickets");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(2, 153, 78));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 204, 51));
        jButton4.setText("Worker List");
        jButton4.setContentAreaFilled(false);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(2, 153, 78));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("← Log Out");
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(2, 153, 78));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Ticket Confirmation");
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(2, 153, 78));
        jButton8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Archives");
        jButton8.setContentAreaFilled(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(2, 153, 78));
        jButton15.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("User Administration");
        jButton15.setContentAreaFilled(false);
        jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/icons8-profile-48.png"))); // NOI18N
        jButton16.setContentAreaFilled(false);
        jButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton16)
                .addGap(72, 72, 72)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(jButton2)
                .addGap(34, 34, 34)
                .addComponent(jButton4)
                .addGap(34, 34, 34)
                .addComponent(jButton8)
                .addGap(34, 34, 34)
                .addComponent(jButton15)
                .addGap(34, 34, 34)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(60, 60, 60))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Worker's Name");

        jLabel4.setText("Phone #");

        jLabel5.setText("Status");

        jLabel7.setText("Worker's Code");

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton9.setText("EDIT");
        jButton9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton10.setText("EDIT");
        jButton10.setToolTipText("");
        jButton10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton11.setText("EDIT");
        jButton11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton12.setText("EDIT");
        jButton12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton13.setText("EDIT");
        jButton13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(175, 175, 175)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 256, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addGap(11, 11, 11)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setText("Search Worker...");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Assigned", "Unassigned", "Day-Off" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("WORKER'S MANAGEMENT");

        jPanel5.setBackground(new java.awt.Color(255, 204, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Worker's Name:");
        jLabel9.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Worker's #:");
        jLabel10.setToolTipText("");

        jButton3.setBackground(new java.awt.Color(0, 204, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Submit");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setText("Add Worker");

        jPanel6.setBackground(new java.awt.Color(222, 252, 232));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 51)));

        jLabel13.setText("Unassigned:");
        jLabel13.setToolTipText("");

        jLabel14.setText("Assigned:");
        jLabel14.setToolTipText("");

        jLabel15.setText("Off Duty:");
        jLabel15.setToolTipText("");

        jLabel16.setText("Total Workers:");
        jLabel16.setToolTipText("");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("17");
        jLabel17.setToolTipText("");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("3");
        jLabel18.setToolTipText("");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("5");
        jLabel19.setToolTipText("");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText("25");
        jLabel20.setToolTipText("");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17))
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel18))
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19))
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel20))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setText("Dashboard");

        jButton7.setText("Archive a Worker →");
        jButton7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton14.setText("Clear Search");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(107, 107, 107)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addGap(0, 0, 0)
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton1.setEnabled(false);
        new homepageadmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jButton6.setEnabled(false);
        new update().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UserSession.clear();
            new login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jButton2.setEnabled(false);
        new viewtickets().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
        this,
        "<html><b>CRITICAL WARNING: Danger Zone</b><br><br>" +
        "You are about to access the Worker Deletion/Archival Portal.<br>" +
        "Removing a worker will revoke their system access and archive their records.<br>" +
        "Are you sure you want to proceed?</html>",
        "High-Risk Operation Warning",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
        // 2. Apply dim/blur overlay over workerslist
        setBlurOverlay(true);

        // 3. Open workerDel passing this parent reference
        workerDel delDialog = new workerDel(this);
        delDialog.setVisible(true);
    }
}                                        

        // Helper method to toggle dim/blur background overlay
        public void setBlurOverlay(boolean enable) {
    if (enable) {
        this.setGlassPane(new javax.swing.JComponent() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setColor(new java.awt.Color(0, 0, 0, 120));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        });
        this.getGlassPane().setVisible(true);
    } else {
        this.getGlassPane().setVisible(false);
    }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jButton8.setEnabled(false);
        new archives().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        jButton15.setEnabled(false);
        new userAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        jButton16.setEnabled(false); // Prevents opening duplicate profile windows
        
        profile prof = new profile();
        prof.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                jButton16.setEnabled(true); // Re-enables button when profile window is closed
            }
        });
        prof.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(workerslist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(workerslist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(workerslist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(workerslist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new workerslist().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
