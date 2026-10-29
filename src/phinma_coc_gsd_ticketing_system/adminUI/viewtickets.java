/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.adminUI;

import phinma_coc_gsd_ticketing_system.adminUI.homepageadmin;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import phinma_coc_gsd_ticketing_system.connections.UserSession;
import phinma_coc_gsd_ticketing_system.frstframes.login;
import phinma_coc_gsd_ticketing_system.userUI.profile;

/**
 *
 * @author ACER
 */
public class viewtickets extends javax.swing.JFrame {

    private javax.swing.JPanel ticketContainer;
    private javax.swing.JScrollPane scrollPane;
    private boolean isUpdatingPlaceholder = false;
    
public viewtickets() {
        initComponents();
        
        setupFullScreenWrapper();
        
        setActiveSidebarButton(jButton2);
        addGoldHoverEffect(jButton1);
        addGoldHoverEffect(jButton4);
        addGoldHoverEffect(jButton7);
        addGoldHoverEffect(jButton10);
        addGoldHoverEffect(jButton6);
        addGoldHoverEffect(jButton5);
        
        setupTableAndContainer();
        setupSearchAndFilters();
        remodelCounterPanel();
        loadCounters();
        loadTickets();
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
    
    private void setupTableAndContainer() {
        jPanel4.removeAll();
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.setPreferredSize(new java.awt.Dimension(1030, 420));
        jPanel4.setMinimumSize(new java.awt.Dimension(1030, 420));

        // Header Panel
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(null);
        headerPanel.setPreferredSize(new java.awt.Dimension(1030, 35));
        headerPanel.setBackground(new java.awt.Color(235, 238, 242));
        headerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(180, 180, 180)));

        String[] headers = {"CODE", "Tickets", "Location", "Time", "Set Level", "Status", "Assign Ticket"};
        int[] boundsX   = {15,     110,       320,        440,    590,         720,      850};
        int[] boundsW   = {80,     190,       110,        140,    110,         110,      160};

        for (int i = 0; i < headers.length; i++) {
            javax.swing.JLabel lbl = new javax.swing.JLabel(headers[i]);
            lbl.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
            lbl.setHorizontalAlignment(i == 3 || i == 5 ? javax.swing.SwingConstants.CENTER : javax.swing.SwingConstants.LEFT);
            lbl.setBounds(boundsX[i], 7, boundsW[i], 20);
            headerPanel.add(lbl);
        }

        // Dynamic Scrollable Body
        ticketContainer = new javax.swing.JPanel();
        ticketContainer.setLayout(new javax.swing.BoxLayout(ticketContainer, javax.swing.BoxLayout.Y_AXIS));
        ticketContainer.setBackground(java.awt.Color.WHITE);

        scrollPane = new javax.swing.JScrollPane(ticketContainer);
        scrollPane.setPreferredSize(new java.awt.Dimension(1030, 385));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 1));
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel4.add(headerPanel, java.awt.BorderLayout.NORTH);
        jPanel4.add(scrollPane, java.awt.BorderLayout.CENTER);
        jPanel4.revalidate();
        jPanel4.repaint();
    }
    
    
    private void setupSearchAndFilters() {
        // Populate dropdown filters with "ALL" as default
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "Pending", "Assigned", "In Progress", "Resolved" }));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "PH", "MS", "MW", "MN", "SHS" }));
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);

        // 1. Style search bar text field
        jTextField1.setLayout(null);
        jTextField1.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        jTextField1.setText("Search a ticket...");
        jTextField1.setForeground(java.awt.Color.GRAY);
        jTextField1.setMargin(new java.awt.Insets(2, 10, 2, 115)); // Inner margin to prevent text under button
        jTextField1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180), 1),
            javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0)
        ));

        // 2. Style Clear Search button as an integrated right-side button
        jButton3.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
        jButton3.setText("Clear Search");
        jButton3.setFocusPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setBackground(new java.awt.Color(242, 242, 242));
        jButton3.setForeground(new java.awt.Color(60, 60, 60));
        jButton3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(180, 180, 180)));

        // 3. Attach Clear button directly inside the right edge of jTextField1
        jTextField1.add(jButton3);
        java.lang.Runnable updateButtonBounds = () -> {
            int w = jTextField1.getWidth() > 0 ? jTextField1.getWidth() : 649;
            int h = jTextField1.getHeight() > 0 ? jTextField1.getHeight() : 35;
            jButton3.setBounds(w - 110, 0, 110, h);
        };
        jTextField1.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                updateButtonBounds.run();
            }
        });
        updateButtonBounds.run();

        // 4. Re-define NetBeans' GroupLayout so jTextField1 expands to 649px and aligns flush left
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)))
        );

        // Focus Placeholder Listener
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().equals("Search a ticket...")) {
                    isUpdatingPlaceholder = true;
                    jTextField1.setText("");
                    jTextField1.setForeground(java.awt.Color.BLACK);
                    isUpdatingPlaceholder = false;
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().trim().isEmpty()) {
                    isUpdatingPlaceholder = true;
                    jTextField1.setText("Search a ticket...");
                    jTextField1.setForeground(java.awt.Color.GRAY);
                    isUpdatingPlaceholder = false;
                }
            }
        });

        // Live Search Listener
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { triggerSearch(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { triggerSearch(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { triggerSearch(); }

            private void triggerSearch() {
                if (!isUpdatingPlaceholder) {
                    loadTickets();
                }
            }
        });

        // Clear Search Action
        jButton3.addActionListener(e -> {
            isUpdatingPlaceholder = true;
            jTextField1.setText("Search a ticket...");
            jTextField1.setForeground(java.awt.Color.GRAY);
            jComboBox1.setSelectedIndex(0);
            jComboBox2.setSelectedIndex(0);
            isUpdatingPlaceholder = false;
            loadTickets();
        });

        // Dropdown Filter Listeners
        jComboBox1.addActionListener(e -> loadTickets());
        jComboBox2.addActionListener(e -> loadTickets());

        // Refresh container layout
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    
    private void loadCounters() {
        int pending = 0, assigned = 0, resolved = 0, archived = 0, total = 0;

        String ticketSql = "SELECT status, COUNT(*) AS cnt FROM tbl_tickets GROUP BY status";
        String archiveSql = "SELECT COUNT(*) AS cnt FROM tbl_archived_tickets";

        try (Connection conn = phinma_coc_gsd_ticketing_system.connections.DBConnection.connect()) {
            if (conn != null) {
                try (PreparedStatement pstmt = conn.prepareStatement(ticketSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String st = rs.getString("status");
                        int count = rs.getInt("cnt");
                        total += count;
                        if ("Pending".equalsIgnoreCase(st)) {
                            pending += count;
                        } else if ("Assigned".equalsIgnoreCase(st) || "Accepted".equalsIgnoreCase(st) || "In Progress".equalsIgnoreCase(st)) {
                            assigned += count;
                        } else if ("Resolved".equalsIgnoreCase(st)) {
                            resolved += count;
                        }
                    }
                }

                try (PreparedStatement pstmt = conn.prepareStatement(archiveSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        archived = rs.getInt("cnt");
                        total += archived;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        jLabel13.setText(String.valueOf(pending));
        jLabel14.setText(String.valueOf(assigned));
        jLabel15.setText(String.valueOf(resolved));
        jLabel16.setText(String.valueOf(archived));
        jLabel17.setText(String.format("%06d", total));
    }
    
    private void remodelCounterPanel() {
        jPanel5.removeAll();
        jPanel5.setLayout(new java.awt.BorderLayout(0, 6));
        jPanel5.setBackground(java.awt.Color.WHITE);
        jPanel5.setPreferredSize(new java.awt.Dimension(1030, 75));
        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 1),
            javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        // Header Title
        jLabel7.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        jLabel7.setForeground(new java.awt.Color(0, 102, 51));
        jLabel7.setText("SYSTEM TICKETS METRICS");
        jPanel5.add(jLabel7, java.awt.BorderLayout.NORTH);

        // 5 Stat Cards Container
        javax.swing.JPanel cardsPanel = new javax.swing.JPanel(new java.awt.GridLayout(1, 5, 10, 0));
        cardsPanel.setOpaque(false);

        // Create Individual Stat Cards
        cardsPanel.add(createStatCard("PENDING", jLabel13, new java.awt.Color(254, 243, 199), new java.awt.Color(245, 158, 11)));
        cardsPanel.add(createStatCard("ASSIGNED", jLabel14, new java.awt.Color(224, 242, 254), new java.awt.Color(14, 165, 233)));
        cardsPanel.add(createStatCard("RESOLVED", jLabel15, new java.awt.Color(220, 252, 231), new java.awt.Color(34, 197, 94)));
        cardsPanel.add(createStatCard("ARCHIVED", jLabel16, new java.awt.Color(243, 232, 255), new java.awt.Color(168, 85, 247)));
        cardsPanel.add(createStatCard("TOTAL TICKETS", jLabel17, new java.awt.Color(209, 250, 229), new java.awt.Color(5, 150, 105)));

        jPanel5.add(cardsPanel, java.awt.BorderLayout.CENTER);
        jPanel5.revalidate();
        jPanel5.repaint();
    }

    private javax.swing.JPanel createStatCard(String title, javax.swing.JLabel valueLabel, java.awt.Color bgColor, java.awt.Color accentColor) {
        javax.swing.JPanel card = new javax.swing.JPanel(new java.awt.GridLayout(2, 1, 0, 2));
        card.setBackground(bgColor);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(accentColor, 1),
            javax.swing.BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        javax.swing.JLabel titleLbl = new javax.swing.JLabel(title);
        titleLbl.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 10));
        titleLbl.setForeground(accentColor.darker());

        valueLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 18));
        valueLabel.setForeground(accentColor.darker());

        card.add(titleLbl);
        card.add(valueLabel);

        return card;
    }
    
    private void loadTickets() {
        if (ticketContainer == null) return;
        ticketContainer.removeAll();

        String keyword = jTextField1.getText().trim();
        if ("Search a ticket...".equals(keyword)) keyword = "";

        // Remove '#' so inputs like "#0014" become "0014"
        String cleanKeyword = keyword.replace("#", "").trim();

        String statusFilter = jComboBox1.getSelectedItem() != null ? jComboBox1.getSelectedItem().toString() : "ALL";
        String buildingFilter = jComboBox2.getSelectedItem() != null ? jComboBox2.getSelectedItem().toString() : "ALL";

        StringBuilder sql = new StringBuilder("SELECT * FROM tbl_tickets WHERE 1=1");

        if (!keyword.isEmpty()) {
            sql.append(" AND (equipment LIKE ? OR room LIKE ? OR building LIKE ? OR CAST(ticket_id AS CHAR) LIKE ? OR LPAD(ticket_id, 4, '0') LIKE ? OR username LIKE ?)");
        }
        if (!"ALL".equalsIgnoreCase(statusFilter)) {
            sql.append(" AND status = ?");
        }
        if (!"ALL".equalsIgnoreCase(buildingFilter)) {
            sql.append(" AND building = ?");
        }

        sql.append(" ORDER BY ticket_id DESC");

        try (Connection conn = phinma_coc_gsd_ticketing_system.connections.DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (!keyword.isEmpty()) {
                String pat = "%" + keyword + "%";
                String cleanPat = "%" + cleanKeyword + "%";
                pstmt.setString(idx++, pat);        // equipment
                pstmt.setString(idx++, pat);        // room
                pstmt.setString(idx++, pat);        // building
                pstmt.setString(idx++, cleanPat);   // raw ticket_id (e.g., "14")
                pstmt.setString(idx++, cleanPat);   // formatted ticket_id (e.g., "0014")
                pstmt.setString(idx++, pat);        // username
            }
            if (!"ALL".equalsIgnoreCase(statusFilter)) pstmt.setString(idx++, statusFilter);
            if (!"ALL".equalsIgnoreCase(buildingFilter)) pstmt.setString(idx++, buildingFilter);

            ResultSet rs = pstmt.executeQuery();
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                int id = rs.getInt("ticket_id");
                String code = String.format("#%04d", id);
                String equipment = rs.getString("equipment");
                String others = rs.getString("others_specified");
                String title = ("Others".equalsIgnoreCase(equipment) && others != null && !others.isEmpty()) ? others : (equipment != null ? equipment : "General Repair");
                String location = (rs.getString("room") != null ? rs.getString("room") : "") + ", " + (rs.getString("building") != null ? rs.getString("building") : "");
                String priority = rs.getString("priority") != null ? rs.getString("priority") : "Low";
                String status = rs.getString("status") != null ? rs.getString("status") : "Pending";
                String assignedWorker = rs.getString("assigned_worker");

                String timeCreated = "N/A";
                java.sql.Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    timeCreated = new java.text.SimpleDateFormat("MM-dd-yy / hh:mm a").format(ts);
                }

                javax.swing.JPanel card = createTicketRowCard(id, code, title, location, timeCreated, priority, status, assignedWorker);
                ticketContainer.add(card);
            }

            if (!hasData) {
                ticketContainer.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 30)));
                javax.swing.JLabel noData = new javax.swing.JLabel("No tickets found.");
                noData.setFont(new java.awt.Font("Tahoma", java.awt.Font.ITALIC, 16));
                noData.setForeground(java.awt.Color.GRAY);
                noData.setAlignmentX(CENTER_ALIGNMENT);
                ticketContainer.add(noData);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ticketContainer.revalidate();
        ticketContainer.repaint();
        loadCounters();
    }

    private javax.swing.JPanel createTicketRowCard(int ticketId, String code, String problem, String location, String time, String priority, String statusStr, String assignedWorker) {
    javax.swing.JPanel row = new javax.swing.JPanel(null);
    row.setBackground(java.awt.Color.WHITE);
    row.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(220, 220, 220)));
    row.setPreferredSize(new java.awt.Dimension(1020, 45));
    row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 45));

    boolean isResolved = "Resolved".equalsIgnoreCase(statusStr);

    // 1. Ticket Code
    javax.swing.JLabel lblCode = new javax.swing.JLabel(code, javax.swing.SwingConstants.LEFT);
    lblCode.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
    lblCode.setBounds(15, 12, 80, 20);
    row.add(lblCode);

    // 2. Ticket Problem
    javax.swing.JLabel lblProb = new javax.swing.JLabel(problem, javax.swing.SwingConstants.LEFT);
    lblProb.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
    lblProb.setBounds(110, 12, 190, 20);
    row.add(lblProb);

    // 3. Location
    javax.swing.JLabel lblLoc = new javax.swing.JLabel(location, javax.swing.SwingConstants.LEFT);
    lblLoc.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
    lblLoc.setBounds(320, 12, 110, 20);
    row.add(lblLoc);

    // 4. Time Submitted
    javax.swing.JLabel lblTime = new javax.swing.JLabel(time, javax.swing.SwingConstants.CENTER);
    lblTime.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
    lblTime.setForeground(java.awt.Color.DARK_GRAY);
    lblTime.setBounds(440, 12, 140, 20);
    row.add(lblTime);

    // 5. Set Priority Level (DISABLED IF RESOLVED)
    javax.swing.JComboBox<String> cmbLevel = new javax.swing.JComboBox<>(new String[]{"Low", "Medium", "High", "Emergency"});
    cmbLevel.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
    cmbLevel.setSelectedItem(priority);
    cmbLevel.setBounds(590, 9, 105, 26);
    if (isResolved) {
        cmbLevel.setEnabled(false); // Locked & non-editable when ticket is resolved
    } else {
        cmbLevel.addActionListener(e -> updateTicketPriority(ticketId, (String) cmbLevel.getSelectedItem()));
    }
    row.add(cmbLevel);

    // 6. Status Badge
    javax.swing.JLabel lblStatus = new javax.swing.JLabel(statusStr, javax.swing.SwingConstants.CENTER);
    lblStatus.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
    lblStatus.setForeground(java.awt.Color.WHITE);
    lblStatus.setOpaque(true);
    lblStatus.setBackground(getStatusColor(statusStr));
    lblStatus.setBounds(720, 10, 105, 24);
    row.add(lblStatus);

    // 7. Action Button ("View" if Resolved, otherwise "Assign Worker")
    javax.swing.JButton btnAction;
    if (isResolved) {
        btnAction = new javax.swing.JButton("View");
        btnAction.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        btnAction.setBounds(840, 9, 160, 26);
        btnAction.setFocusPainted(false);
        btnAction.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAction.addActionListener(e -> openResolvedDetailsDialog(ticketId, code));
    } else {
        String btnText = (assignedWorker != null && !assignedWorker.trim().isEmpty()) ? "Worker: " + assignedWorker : "Assign a Worker";
        btnAction = new javax.swing.JButton(btnText);
        btnAction.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        btnAction.setBounds(840, 9, 160, 26);
        btnAction.setFocusPainted(false);
        btnAction.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAction.addActionListener(e -> openAssignWorkerDialog(ticketId, code, assignedWorker));
    }
    row.add(btnAction);

    return row;
}
    private void openResolvedDetailsDialog(int ticketId, String code) {
    String assignedWorker = "Unassigned";
    String dateAssigned = "N/A";
    String dateCompleted = "N/A";
    String remarks = "None";
    String problem = "N/A";
    String location = "N/A";

    String sql = "SELECT * FROM tbl_tickets WHERE ticket_id = ?";
    try (Connection conn = phinma_coc_gsd_ticketing_system.connections.DBConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, ticketId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            assignedWorker = rs.getString("assigned_worker");
            if (assignedWorker == null || assignedWorker.trim().isEmpty()) {
                assignedWorker = "N/A";
            }
            
            String equipment = rs.getString("equipment");
            String others = rs.getString("others_specified");
            problem = ("Others".equalsIgnoreCase(equipment) && others != null && !others.isEmpty()) ? others : (equipment != null ? equipment : "General Repair");
            location = (rs.getString("room") != null ? rs.getString("room") : "") + ", " + (rs.getString("building") != null ? rs.getString("building") : "");

            java.sql.Timestamp tsCreated = rs.getTimestamp("created_at");
            if (tsCreated != null) {
                dateAssigned = new java.text.SimpleDateFormat("MM-dd-yy / hh:mm a").format(tsCreated);
            }

            java.sql.Timestamp tsUpdated = null;
            try { 
                tsUpdated = rs.getTimestamp("updated_at"); 
            } catch (Exception ignore) {}
            
            if (tsUpdated != null) {
                dateCompleted = new java.text.SimpleDateFormat("MM-dd-yy / hh:mm a").format(tsUpdated);
            } else {
                dateCompleted = dateAssigned;
            }

            String rem = rs.getString("remarks");
            if (rem != null && !rem.trim().isEmpty()) {
                remarks = rem;
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    // Modal Details Dialog Setup (Expanded bounds)
    JDialog dialog = new JDialog(this, "Resolved Ticket Details", true);
    dialog.setSize(520, 410);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(null);
    dialog.getContentPane().setBackground(Color.WHITE);

    // Header Banner
    JPanel headerPanel = new JPanel(null);
    headerPanel.setBackground(new Color(0, 102, 51));
    headerPanel.setBounds(0, 0, 520, 50);

    JLabel lblHeaderTitle = new JLabel("Resolved Ticket Details");
    lblHeaderTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
    lblHeaderTitle.setForeground(Color.WHITE);
    lblHeaderTitle.setBounds(20, 12, 260, 26);
    headerPanel.add(lblHeaderTitle);

    JLabel lblHeaderTag = new JLabel("[" + code + "]", SwingConstants.RIGHT);
    lblHeaderTag.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblHeaderTag.setForeground(new Color(255, 204, 51));
    lblHeaderTag.setBounds(350, 12, 140, 26);
    headerPanel.add(lblHeaderTag);

    dialog.add(headerPanel);

    // Content Display Label (Increased to 13pt Tahoma)
    JLabel lblDetails = new JLabel("<html><body style='font-family:Tahoma; font-size:13pt; line-height:1.6;'>"
            + "<b>Ticket Problem:</b> " + problem + "<br>"
            + "<b>Location:</b> " + location + "<br><br>"
            + "<b>Assigned Worker:</b> <font color='#006633'><b>" + assignedWorker + "</b></font><br>"
            + "<b>Date / Time Assigned:</b> " + dateAssigned + "<br>"
            + "<b>Completion Time:</b> " + dateCompleted + "<br><br>"
            + "<b>Completion Remarks:</b> " + remarks
            + "</body></html>");
    lblDetails.setVerticalAlignment(SwingConstants.TOP);
    lblDetails.setBounds(30, 68, 450, 240);
    dialog.add(lblDetails);

    // Footer Action Panel
    JPanel footerPanel = new JPanel(null);
    footerPanel.setBackground(new Color(245, 245, 245));
    footerPanel.setBounds(0, 310, 520, 70);
    footerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

    // Close Button styled matching primary green theme
    JButton btnClose = new JButton("Close");
    btnClose.setFont(new Font("Tahoma", Font.BOLD, 14));
    btnClose.setBounds(375, 14, 115, 36);
    btnClose.setBackground(new Color(0, 102, 51));
    btnClose.setForeground(Color.WHITE);
    btnClose.setFocusPainted(false);
    btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btnClose.addActionListener(e -> dialog.dispose());

    footerPanel.add(btnClose);
    dialog.add(footerPanel);

    dialog.setVisible(true);
}

    private java.awt.Color getStatusColor(String status) {
        if ("Pending".equalsIgnoreCase(status)) return new java.awt.Color(245, 158, 11);
        if ("Assigned".equalsIgnoreCase(status) || "Accepted".equalsIgnoreCase(status)) return new java.awt.Color(14, 165, 233);
        if ("In Progress".equalsIgnoreCase(status)) return new java.awt.Color(59, 130, 246);
        if ("Resolved".equalsIgnoreCase(status)) return new java.awt.Color(0, 204, 51);
        return java.awt.Color.GRAY;
    }

    private void updateTicketPriority(int ticketId, String newPriority) {
        String sql = "UPDATE tbl_tickets SET priority = ? WHERE ticket_id = ?";
        try (Connection conn = phinma_coc_gsd_ticketing_system.connections.DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPriority);
            pstmt.setInt(2, ticketId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to update level: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAssignWorkerDialog(int ticketId, String code, String currentWorker) {
    java.util.List<String> workers = new java.util.ArrayList<>();
    
    // Query available workers not currently working on an active ticket
    String sqlWorkers = "SELECT DISTINCT w.worker_name "
                      + "FROM tbl_workers w "
                      + "LEFT JOIN tbl_tickets t "
                      + "  ON w.worker_name = t.assigned_worker "
                      + " AND LOWER(t.status) IN ('assigned', 'in progress', 'accepted') "
                      + "WHERE LOWER(IFNULL(w.status, 'active')) NOT IN ('day off', 'inactive', 'off duty') "
                      + "  AND (t.assigned_worker IS NULL OR w.worker_name = ?) "
                      + "ORDER BY w.worker_name ASC";

    try (Connection conn = phinma_coc_gsd_ticketing_system.connections.DBConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sqlWorkers)) {
        pstmt.setString(1, currentWorker != null ? currentWorker : "");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String wName = rs.getString("worker_name");
            if (wName != null && !"unassigned".equalsIgnoreCase(wName.trim())) {
                workers.add(wName);
            }
        }
    } catch (SQLException ex) {
        workers.add("Eduardo Garcia");
        workers.add("Mark Joaquin");
        workers.add("Timotheo Cruz");
    }

    if (workers.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No available workers found (All workers are currently Assigned or on Day Off).", "No Workers Available", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Modal Dialog setup
    JDialog dialog = new JDialog(this, "Assign Worker Details", true);
    dialog.setSize(480, 340);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(null);
    dialog.getContentPane().setBackground(Color.WHITE);

    // Header Banner
    JPanel headerPanel = new JPanel(null);
    headerPanel.setBackground(new Color(0, 102, 51));
    headerPanel.setBounds(0, 0, 480, 45);

    JLabel lblHeaderTitle = new JLabel("Assign Worker Details");
    lblHeaderTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblHeaderTitle.setForeground(Color.WHITE);
    lblHeaderTitle.setBounds(20, 10, 220, 24);
    headerPanel.add(lblHeaderTitle);

    JLabel lblHeaderTag = new JLabel("[" + code + "]", SwingConstants.RIGHT);
    lblHeaderTag.setFont(new Font("Tahoma", Font.BOLD, 15));
    lblHeaderTag.setForeground(new Color(255, 204, 51));
    lblHeaderTag.setBounds(320, 10, 135, 24);
    headerPanel.add(lblHeaderTag);

    dialog.add(headerPanel);

    // Worker Name Dropdown Section
    JLabel lblWorkerName = new JLabel("Worker's Name:");
    lblWorkerName.setFont(new Font("Tahoma", Font.BOLD, 13));
    lblWorkerName.setBounds(35, 60, 200, 20);
    dialog.add(lblWorkerName);

    javax.swing.JComboBox<String> workerCombo = new javax.swing.JComboBox<>(workers.toArray(new String[0]));
    workerCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
    workerCombo.setBounds(35, 83, 400, 32);
    if (currentWorker != null && workers.contains(currentWorker)) {
        workerCombo.setSelectedItem(currentWorker);
    }
    dialog.add(workerCombo);

    // Dynamic Duty Status Section
    JLabel lblStatusTitle = new JLabel("Duty Status:");
    lblStatusTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
    lblStatusTitle.setBounds(35, 130, 200, 20);
    dialog.add(lblStatusTitle);

    JLabel lblDutyStatus = new JLabel(" Active / Unassigned", SwingConstants.LEFT);
    lblDutyStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
    lblDutyStatus.setOpaque(true);
    lblDutyStatus.setBackground(new Color(245, 245, 245));
    lblDutyStatus.setForeground(new Color(0, 102, 51));
    lblDutyStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
    lblDutyStatus.setBounds(35, 153, 400, 32);
    dialog.add(lblDutyStatus);

    // Listener to update Duty Status label on worker selection change
    workerCombo.addActionListener(e -> {
        String sel = (String) workerCombo.getSelectedItem();
        if (sel != null && sel.equalsIgnoreCase(currentWorker)) {
            lblDutyStatus.setText(" Active / Assigned");
        } else {
            lblDutyStatus.setText(" Active / Unassigned");
        }
    });
    if (workerCombo.getSelectedItem() != null && workerCombo.getSelectedItem().toString().equalsIgnoreCase(currentWorker)) {
        lblDutyStatus.setText(" Active / Assigned");
    }

    // Footer Action Panel
    JPanel footerPanel = new JPanel(null);
    footerPanel.setBackground(new Color(245, 245, 245));
    footerPanel.setBounds(0, 220, 480, 80);
    footerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

    JButton btnCancel = new JButton("Cancel");
    btnCancel.setFont(new Font("Tahoma", Font.BOLD, 13));
    btnCancel.setBounds(200, 16, 105, 36);
    btnCancel.setBackground(Color.WHITE);
    btnCancel.setForeground(Color.BLACK);
    btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
    btnCancel.setFocusPainted(false);
    btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btnCancel.addActionListener(e -> dialog.dispose());
    footerPanel.add(btnCancel);

    JButton btnAssign = new JButton("Assign Worker");
    btnAssign.setFont(new Font("Tahoma", Font.BOLD, 13));
    btnAssign.setBounds(315, 16, 130, 36);
    btnAssign.setBackground(new Color(0, 102, 51));
    btnAssign.setForeground(Color.WHITE);
    btnAssign.setFocusPainted(false);
    btnAssign.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    btnAssign.addActionListener(e -> {
        String selectedWorker = (String) workerCombo.getSelectedItem();
        if (selectedWorker == null || selectedWorker.trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please select a worker.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String updateTicketSql = "UPDATE tbl_tickets SET assigned_worker = ?, status = 'Assigned' WHERE ticket_id = ?";
        String updateWorkerSql = "UPDATE tbl_workers SET status = 'Active/Assigned' WHERE worker_name = ?";

        try (Connection conn = phinma_coc_gsd_ticketing_system.connections.DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(updateTicketSql)) {

            pstmt.setString(1, selectedWorker);
            pstmt.setInt(2, ticketId);
            pstmt.executeUpdate();

            try (PreparedStatement pstmtW = conn.prepareStatement(updateWorkerSql)) {
                pstmtW.setString(1, selectedWorker);
                pstmtW.executeUpdate();
            } catch (SQLException ignore) {}

            JOptionPane.showMessageDialog(this, "Ticket " + code + " successfully assigned to " + selectedWorker + "!", "Assigned", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
            loadTickets();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(dialog, "Error assigning worker: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    footerPanel.add(btnAssign);
    dialog.add(footerPanel);

    dialog.setVisible(true);
}
    
    
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
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

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
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 204, 51));
        jButton2.setText("View Tickets");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(2, 153, 78));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Worker List");
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jButton7.setBackground(new java.awt.Color(2, 153, 78));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Archives");
        jButton7.setContentAreaFilled(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/icons8-profile-48.png"))); // NOI18N
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(2, 153, 78));
        jButton10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("User Administration");
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
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
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9)
                .addGap(72, 72, 72)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(jButton2)
                .addGap(34, 34, 34)
                .addComponent(jButton4)
                .addGap(34, 34, 34)
                .addComponent(jButton7)
                .addGap(34, 34, 34)
                .addComponent(jButton10)
                .addGap(34, 34, 34)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(60, 60, 60))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "PENDING", "ASSIGNED", "RESOLVED" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PH", "MS", "MW", "MN", "SHS" }));

        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setText("Search a ticket...");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("TICKET CENTER");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CODE");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tickets");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Location");

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Set Level");

        jComboBox4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High", "Critical" }));
        jComboBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Assign Ticket");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Time");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Status");

        jButton8.setText("Assign a Worker");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                            .addComponent(jSeparator6)
                            .addComponent(jSeparator5)
                            .addComponent(jSeparator4)
                            .addComponent(jSeparator8))
                        .addGap(144, 144, 144))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8)))
        );

        jPanel5.setBackground(new java.awt.Color(222, 252, 232));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SYSTEM TICKET METRICS");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Pending Tickets:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Assigned Tickets:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Resolved Tickets:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Archived Tickets:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Total Tickets:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel13.setText("8");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel14.setText("0");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel15.setText("0");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel16.setText("0");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel17.setText("000000");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))))
        );

        jButton3.setText("Clear Search");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1031, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jButton3)
                            .addGap(27, 27, 27)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)))
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
        jButton4.setEnabled(false);
        new workerslist().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jButton6.setEnabled(false);
        new update().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UserSession.clear();
            new login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jButton7.setEnabled(false);
        new archives().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jButton10.setEnabled(false);
        new userAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jButton9.setEnabled(false); // Prevents opening duplicate profile windows
        
        profile prof = new profile();
        prof.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                jButton9.setEnabled(true); // Re-enables button when profile window is closed
            }
        });
        prof.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(viewtickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewtickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewtickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewtickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewtickets().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
