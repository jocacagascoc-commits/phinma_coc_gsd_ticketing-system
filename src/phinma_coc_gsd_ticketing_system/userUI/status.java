/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.userUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;
import phinma_coc_gsd_ticketing_system.frstframes.login;

/**
 *
 * @author ACER
 */
public class status extends javax.swing.JFrame {
    
    private JPanel ticketContainer;
    private JScrollPane scrollPane;
    
    public status() {
        initComponents();
        
        setActiveSidebarButton(jButton4);
        addGoldHoverEffect(jButton1);
        addGoldHoverEffect(jButton2);
        addGoldHoverEffect(jButton3);
        addGoldHoverEffect(jButton5);
        
        setupTableHeaders();
        setupDynamicContainer();
        setupFullScreenWrapper();
        setupSearchAndFilters();
        setupNavigation();
        loadTickets();

    }
    
    private java.awt.Image bgImage;
    
    private void setupFullScreenWrapper() {
    this.setSize(1310, 780);
    this.setPreferredSize(new java.awt.Dimension(1310, 780));
    this.setMinimumSize(new java.awt.Dimension(1310, 780));
    this.setLocationRelativeTo(null); // Centers window in normal mode

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

    javax.swing.JLayeredPane dashboardContainer = new javax.swing.JLayeredPane();
    dashboardContainer.setPreferredSize(new java.awt.Dimension(1310, 780));
    dashboardContainer.setMinimumSize(new java.awt.Dimension(1310, 780));
    dashboardContainer.setMaximumSize(new java.awt.Dimension(1310, 780));

    jPanel1.setBounds(0, 0, 1310, 780);
    dashboardContainer.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

    if (scrollPane != null) {
        dashboardContainer.add(scrollPane, javax.swing.JLayeredPane.PALETTE_LAYER);
    }

    this.getContentPane().removeAll();
    outerBackgroundPanel.add(dashboardContainer);
    this.setContentPane(outerBackgroundPanel);

    this.revalidate();
    this.repaint();
}
    
    private void setActiveSidebarButton(javax.swing.JButton btn) {
    Color activeBg = new Color(255, 204, 51); // Gold background
    Color activeFg = new Color(0, 102, 51);   // Dark Green text

    float normalSize = btn.getFont().getSize2D();
    float activeSize = btn.getText().trim().length() > 15 ? 16.0f : normalSize;

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
        float hoverSize = btn.getText().trim().length() > 16 ? 17.0f : normalSize;

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
    
    private void setupTableHeaders() {
        jPanel6.setLayout(null);
        jPanel6.setPreferredSize(new Dimension(1032, 30));
        jPanel6.setBackground(new Color(235, 238, 242)); // Table header gray
        jPanel6.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180, 180, 180)));
        jPanel6.removeAll();

        // 1. Ticket Code (Matches x=15, width=80)
        jLabel9.setText("Ticket Code");
        jLabel9.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel9.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel9.setBounds(15, 5, 80, 20);
        jPanel6.add(jLabel9);

        // 2. Problem (Matches x=105, width=190)
        jLabel10.setText("Problem");
        jLabel10.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel10.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel10.setBounds(105, 5, 190, 20);
        jPanel6.add(jLabel10);

        // 3. Time & Date (Matches x=300, width=150)
        jLabel11.setText("Time & Date");
        jLabel11.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel11.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel11.setBounds(300, 5, 150, 20);
        jPanel6.add(jLabel11);

        // 4. Location (Matches x=460, width=130)
        jLabel12.setText("Location");
        jLabel12.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel12.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel12.setBounds(460, 5, 130, 20);
        jPanel6.add(jLabel12);

        // 5. Status (Matches x=600, width=100)
        jLabel13.setText("Status");
        jLabel13.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel13.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel13.setBounds(600, 5, 100, 20);
        jPanel6.add(jLabel13);
    }
    
        private void setupDynamicContainer() {
        ticketContainer = new JPanel();
        ticketContainer.setLayout(new javax.swing.BoxLayout(ticketContainer, javax.swing.BoxLayout.Y_AXIS));
        ticketContainer.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(ticketContainer);
        scrollPane.setBounds(233, 155, 1032, 555);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 51), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setColumnHeaderView(jPanel6);
    }
    
    private void setupSearchAndFilters() {
        jTextField1.setText("Search Your Ticket...");
        jTextField1.setForeground(Color.GRAY);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().equals("Search Your Ticket...")) {
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().trim().isEmpty()) {
                    jTextField1.setText("Search Your Ticket...");
                    jTextField1.setForeground(Color.GRAY);
                }
            }
        });
        
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { loadTickets(); }
            public void removeUpdate(DocumentEvent e) { loadTickets(); }
            public void insertUpdate(DocumentEvent e) { loadTickets(); }
        });

        jComboBox1.addActionListener(e -> loadTickets());
        jComboBox2.addActionListener(e -> loadTickets());
    }
    
    private void loadTickets() {
    if (ticketContainer == null) return;
    ticketContainer.removeAll();

    String keyword = jTextField1.getText().trim();
    if (keyword.equals("Search Your Ticket...")) keyword = "";

    String selectedStatus = jComboBox1.getSelectedItem() != null ? jComboBox1.getSelectedItem().toString() : "All";
    String selectedSort = jComboBox2.getSelectedItem() != null ? jComboBox2.getSelectedItem().toString() : "Sort";

    StringBuilder sql = new StringBuilder("SELECT * FROM tbl_tickets WHERE username = ?");

    // Clean keyword for pinpoint ticket ID matching (removes '#' prefix)
    String cleanKeyword = keyword.replace("#", "").trim();
    int exactId = -1;
    try {
        if (!cleanKeyword.isEmpty() && cleanKeyword.matches("\\d+")) {
            exactId = Integer.parseInt(cleanKeyword);
        }
    } catch (NumberFormatException ignored) {}

    if (!keyword.isEmpty()) {
        sql.append(" AND (equipment LIKE ? OR room LIKE ? OR building LIKE ? ")
           .append("OR CAST(ticket_id AS CHAR) LIKE ? OR LPAD(ticket_id, 4, '0') LIKE ? OR ticket_id = ?)");
    }
    if (!selectedStatus.equals("All")) {
        sql.append(" AND status = ?");
    }

    // Sorting options
    if ("Old to New".equalsIgnoreCase(selectedSort)) {
        sql.append(" ORDER BY ticket_id ASC");
    } else if ("A > Z".equalsIgnoreCase(selectedSort)) {
        sql.append(" ORDER BY equipment ASC");
    } else if ("Z < A".equalsIgnoreCase(selectedSort)) {
        sql.append(" ORDER BY equipment DESC");
    } else {
        sql.append(" ORDER BY ticket_id DESC"); // Default: New to Old
    }
    
    try (Connection conn = DBConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

        int paramIndex = 1;
        pstmt.setString(paramIndex++, UserSession.getFullName());

        if (!keyword.isEmpty()) {
            String pattern = "%" + keyword + "%";
            String cleanPattern = "%" + cleanKeyword + "%";

            pstmt.setString(paramIndex++, pattern);      // equipment
            pstmt.setString(paramIndex++, pattern);      // room
            pstmt.setString(paramIndex++, pattern);      // building
            pstmt.setString(paramIndex++, cleanPattern); // CAST(ticket_id AS CHAR)
            pstmt.setString(paramIndex++, cleanPattern); // LPAD(ticket_id, 4, '0') (e.g. 0005)
            pstmt.setInt(paramIndex++, exactId);         // Exact integer ID match
        }

        if (!selectedStatus.equals("All")) {
            pstmt.setString(paramIndex++, selectedStatus);
        }

        ResultSet rs = pstmt.executeQuery();
        boolean hasData = false;
        
        while (rs.next()) {
            hasData = true;
            int id = rs.getInt("ticket_id");
            String equipment = rs.getString("equipment");
            String room = rs.getString("room");
            String building = rs.getString("building");
            String status = rs.getString("status");
            String description = rs.getString("description");
            String proof = rs.getString("image_proof");
            String assigned = rs.getString("assigned_worker");
            String others = rs.getString("others_specified");

            // Safely extract Admin Remarks
            String remarks = "";
            try {
                remarks = rs.getString("remarks");
            } catch (Exception ex) {
                try { remarks = rs.getString("admin_remarks"); } catch (Exception ignored) {}
            }

            String timeCreated = "N/A";
            try {
                java.sql.Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy / hh:mm a");
                    timeCreated = sdf.format(ts);
                }
            } catch (Exception ex) {}

            String code = String.format("#%04d", id);
            String problem = ("Others".equalsIgnoreCase(equipment) && others != null && !others.isEmpty()) ? others : (equipment != null ? equipment : "General Repair");
            String location = (room != null ? room : "") + ", " + (building != null ? building : "");

            // Pass remarks to row card
            JPanel card = createTableRowCard(id, code, problem, timeCreated, location, status, description, proof, assigned, room, building, equipment, others, remarks);
            ticketContainer.add(card);
        }
        
        if (!hasData) {
            ticketContainer.add(javax.swing.Box.createRigidArea(new Dimension(0, 30)));
            JLabel noData = new JLabel("No tickets found in management.");
            noData.setFont(new Font("Tahoma", Font.BOLD, 16));
            noData.setForeground(Color.GRAY);
            noData.setAlignmentX(CENTER_ALIGNMENT);
            ticketContainer.add(noData);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error Loading Management List", JOptionPane.ERROR_MESSAGE);
    }

    ticketContainer.revalidate();
    ticketContainer.repaint();
}
    
    private JPanel createTableRowCard(int ticketId, String code, String problem, String time, String location, String statusStr, String desc, String proofPath, String assigned, String room, String building, String equipment, String others, String remarks) {
        JPanel row = new JPanel();
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        row.setPreferredSize(new Dimension(1030, 45));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        row.setLayout(null);

        JLabel lblCode = new JLabel(code, SwingConstants.LEFT);
        lblCode.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCode.setBounds(15, 12, 80, 20);
        row.add(lblCode);

        JLabel lblProb = new JLabel(problem, SwingConstants.LEFT);
        lblProb.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProb.setBounds(105, 12, 190, 20);
        row.add(lblProb);

        JLabel lblTime = new JLabel(time, SwingConstants.CENTER);
        lblTime.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblTime.setForeground(Color.DARK_GRAY);
        lblTime.setBounds(300, 12, 150, 20);
        row.add(lblTime);

        JLabel lblLoc = new JLabel(location, SwingConstants.LEFT);
        lblLoc.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblLoc.setBounds(460, 12, 130, 20);
        row.add(lblLoc);
        
        JLabel lblStatus = new JLabel(statusStr, SwingConstants.CENTER);
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setOpaque(true);
        lblStatus.setBackground(getStatusColor(statusStr));
        lblStatus.setBounds(600, 10, 100, 24);
        row.add(lblStatus);

        // --- View Button ---
        JButton btnView = new JButton("View");
        btnView.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnView.setBounds(740, 9, 75, 26);
        btnView.addActionListener(e -> showTicketDetails(code, problem, location, statusStr, time, desc, proofPath, assigned, remarks));
        row.add(btnView);

        // --- Edit Button ---
        JButton btnEdit = new JButton("Edit");
        btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnEdit.setBounds(825, 9, 75, 26);
        btnEdit.addActionListener(e -> handleEditTicket(ticketId, statusStr, room, building, equipment, others, proofPath, desc));
        row.add(btnEdit);

        // --- Archive Button ---
        JButton btnArchive = new JButton("Archive");
        btnArchive.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnArchive.setBounds(910, 9, 85, 26);
        btnArchive.addActionListener(e -> archiveTicket(ticketId, code));
        row.add(btnArchive);

        return row;
    }
    
    private Color getStatusColor(String status) {
        if ("Pending".equalsIgnoreCase(status)) return new Color(245, 158, 11);
        if ("Assigned".equalsIgnoreCase(status)) return new Color(14, 165, 233);
        if ("In Progress".equalsIgnoreCase(status)) return new Color(59, 130, 246);
        if ("Resolved".equalsIgnoreCase(status)) return new Color(0, 204, 51);
        return Color.GRAY;
    }
    
    private void handleEditTicket(int ticketId, String statusStr, String room, String building, String equipment, String others, String proofPath, String desc) {
    if (!"Pending".equalsIgnoreCase(statusStr)) {
        JLabel lblErr = new JLabel("You cannot edit this ticket because it has already been " + statusStr.toLowerCase() + " by GSD.");
        lblErr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        JOptionPane.showMessageDialog(this, lblErr, "Edit Restricted", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Font labelFont = new Font("Tahoma", Font.BOLD, 13);
    Font inputFont = new Font("Tahoma", Font.PLAIN, 14);

    // Form Field Components
    JTextField txtRoom = new JTextField(room != null ? room : "");
    txtRoom.setFont(inputFont);

    javax.swing.JComboBox<String> cmbBuilding = new javax.swing.JComboBox<>(new String[]{"MS", "MW", "MN", "PH", "SHS"});
    cmbBuilding.setFont(inputFont);
    if (building != null) cmbBuilding.setSelectedItem(building);

    javax.swing.JComboBox<String> cmbEquip = new javax.swing.JComboBox<>(new String[]{"AC", "TV", "Computer", "Electric Fan", "Outlet", "Faucet", "Lighting", "Others"});
    cmbEquip.setFont(inputFont);
    if (equipment != null) cmbEquip.setSelectedItem(equipment);

    JTextField txtOthers = new JTextField(others != null ? others : "");
    txtOthers.setFont(inputFont);
    txtOthers.setEnabled("Others".equalsIgnoreCase((String) cmbEquip.getSelectedItem()));

    cmbEquip.addActionListener(e -> {
        boolean isOthers = "Others".equalsIgnoreCase((String) cmbEquip.getSelectedItem());
        txtOthers.setEnabled(isOthers);
        if (!isOthers) txtOthers.setText("");
    });

    final String[] updatedProofPath = {proofPath != null ? proofPath : ""};

    JButton btnPhoto = new JButton(updatedProofPath[0].isEmpty() ? "Click to attach photo proof..." : "Attached: " + new java.io.File(updatedProofPath[0]).getName());
    btnPhoto.setFont(new Font("Tahoma", Font.PLAIN, 13));
    btnPhoto.addActionListener(e -> {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Select Evidence Image");
        int choice = chooser.showOpenDialog(this);

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        if (choice == javax.swing.JFileChooser.APPROVE_OPTION) {
            updatedProofPath[0] = chooser.getSelectedFile().getAbsolutePath();
            btnPhoto.setText("Attached: " + chooser.getSelectedFile().getName());
        }
    });

    JTextArea txtDesc = new JTextArea(desc != null ? desc : "", 4, 20);
    txtDesc.setFont(inputFont);
    txtDesc.setLineWrap(true);
    txtDesc.setWrapStyleWord(true);
    JScrollPane descScroll = new JScrollPane(txtDesc);
    descScroll.setPreferredSize(new Dimension(480, 90));

    // Main Card Panel matching Admin UI Dialog
    JPanel mainPanel = new JPanel(new java.awt.BorderLayout());
    mainPanel.setPreferredSize(new Dimension(540, 440));
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 51), 2));

    // Green Header Bar with Gold Code Badge
    JPanel headerPanel = new JPanel(new java.awt.BorderLayout());
    headerPanel.setBackground(new Color(0, 102, 51));
    headerPanel.setPreferredSize(new Dimension(540, 45));
    headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

    JLabel lblTitle = new JLabel("Edit Ticket Details");
    lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
    lblTitle.setForeground(Color.WHITE);

    JLabel lblCode = new JLabel(String.format("[#%04d]", ticketId));
    lblCode.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblCode.setForeground(new Color(255, 204, 51)); // Gold Badge

    headerPanel.add(lblTitle, java.awt.BorderLayout.WEST);
    headerPanel.add(lblCode, java.awt.BorderLayout.EAST);

    // Form Layout
    JPanel bodyPanel = new JPanel();
    bodyPanel.setLayout(new javax.swing.BoxLayout(bodyPanel, javax.swing.BoxLayout.Y_AXIS));
    bodyPanel.setBackground(Color.WHITE);
    bodyPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JPanel row1 = new JPanel(new java.awt.GridLayout(1, 2, 10, 0));
    row1.setOpaque(false);
    JPanel pRoom = new JPanel(new java.awt.BorderLayout(0, 4));
    pRoom.setOpaque(false);
    JLabel lblRoom = new JLabel("Room or Location:");
    lblRoom.setFont(labelFont);
    pRoom.add(lblRoom, java.awt.BorderLayout.NORTH);
    pRoom.add(txtRoom, java.awt.BorderLayout.CENTER);

    JPanel pBldg = new JPanel(new java.awt.BorderLayout(0, 4));
    pBldg.setOpaque(false);
    JLabel lblBldg = new JLabel("Building:");
    lblBldg.setFont(labelFont);
    pBldg.add(lblBldg, java.awt.BorderLayout.NORTH);
    pBldg.add(cmbBuilding, java.awt.BorderLayout.CENTER);

    row1.add(pRoom);
    row1.add(pBldg);

    JPanel row2 = new JPanel(new java.awt.GridLayout(1, 2, 10, 0));
    row2.setOpaque(false);
    JPanel pEquip = new JPanel(new java.awt.BorderLayout(0, 4));
    pEquip.setOpaque(false);
    JLabel lblEquip = new JLabel("Equipment:");
    lblEquip.setFont(labelFont);
    pEquip.add(lblEquip, java.awt.BorderLayout.NORTH);
    pEquip.add(cmbEquip, java.awt.BorderLayout.CENTER);

    JPanel pOthers = new JPanel(new java.awt.BorderLayout(0, 4));
    pOthers.setOpaque(false);
    JLabel lblOthers = new JLabel("If Others, specify:");
    lblOthers.setFont(labelFont);
    pOthers.add(lblOthers, java.awt.BorderLayout.NORTH);
    pOthers.add(txtOthers, java.awt.BorderLayout.CENTER);

    row2.add(pEquip);
    row2.add(pOthers);

    JPanel row3 = new JPanel(new java.awt.BorderLayout(0, 4));
    row3.setOpaque(false);
    JLabel lblPhoto = new JLabel("Image Proof:");
    lblPhoto.setFont(labelFont);
    row3.add(lblPhoto, java.awt.BorderLayout.NORTH);
    row3.add(btnPhoto, java.awt.BorderLayout.CENTER);

    JPanel row4 = new JPanel(new java.awt.BorderLayout(0, 4));
    row4.setOpaque(false);
    JLabel lblDesc = new JLabel("Description:");
    lblDesc.setFont(labelFont);
    row4.add(lblDesc, java.awt.BorderLayout.NORTH);
    row4.add(descScroll, java.awt.BorderLayout.CENTER);

    bodyPanel.add(row1);
    bodyPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 10)));
    bodyPanel.add(row2);
    bodyPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 10)));
    bodyPanel.add(row3);
    bodyPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 10)));
    bodyPanel.add(row4);

    mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
    mainPanel.add(bodyPanel, java.awt.BorderLayout.CENTER);

    int result = JOptionPane.showConfirmDialog(this, mainPanel, "Edit Ticket", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String newRoom = txtRoom.getText().trim();
        String newBldg = (String) cmbBuilding.getSelectedItem();
        String newEquip = (String) cmbEquip.getSelectedItem();
        String newOth = txtOthers.getText().trim();
        String newDesc = txtDesc.getText().trim();

        if (newRoom.isEmpty() || newDesc.isEmpty()) {
            JLabel lblWarn = new JLabel("Room location and description cannot be empty.");
            lblWarn.setFont(new Font("Tahoma", Font.PLAIN, 16));
            JOptionPane.showMessageDialog(this, lblWarn, "Validation Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("Others".equalsIgnoreCase(newEquip) && newOth.isEmpty()) {
            JLabel lblWarn = new JLabel("Please specify the equipment name under 'If Others'.");
            lblWarn.setFont(new Font("Tahoma", Font.PLAIN, 16));
            JOptionPane.showMessageDialog(this, lblWarn, "Validation Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE tbl_tickets SET room = ?, building = ?, equipment = ?, others_specified = ?, image_proof = ?, description = ? WHERE ticket_id = ? AND status = 'Pending'";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newRoom);
            pstmt.setString(2, newBldg);
            pstmt.setString(3, newEquip);
            pstmt.setString(4, newOth);
            pstmt.setString(5, updatedProofPath[0]);
            pstmt.setString(6, newDesc);
            pstmt.setInt(7, ticketId);

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                JLabel lblSuccess = new JLabel("Ticket updated successfully!");
                lblSuccess.setFont(new Font("Tahoma", Font.PLAIN, 16));
                JOptionPane.showMessageDialog(this, lblSuccess, "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTickets();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
    private void showTicketDetails(String code, String problem, String location, String status, String time, String desc, String proof, String assigned, String remarks) {
        boolean isResolved = "Resolved".equalsIgnoreCase(status);
        boolean hasRemarks = remarks != null && !remarks.trim().isEmpty();

        int modalHeight = (isResolved || hasRemarks) ? 540 : 460;

        JPanel mainPanel = new JPanel(new java.awt.BorderLayout());
        mainPanel.setPreferredSize(new Dimension(520, modalHeight));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 51), 2));

        // 1. Dark Green Header Bar
        JPanel headerPanel = new JPanel(new java.awt.BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 51));
        headerPanel.setPreferredSize(new Dimension(520, 45));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitle = new JLabel("Ticket Details");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblCode = new JLabel("[" + code + "]");
        lblCode.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblCode.setForeground(new Color(255, 204, 51)); // Gold Badge

        headerPanel.add(lblTitle, java.awt.BorderLayout.WEST);
        headerPanel.add(lblCode, java.awt.BorderLayout.EAST);

        // 2. Body Panel
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new javax.swing.BoxLayout(bodyPanel, javax.swing.BoxLayout.Y_AXIS));
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        Font labelFont = new Font("Tahoma", Font.BOLD, 14);
        Font valueFont = new Font("Tahoma", Font.PLAIN, 14);

        String[][] details = {
            {"Problem:", problem},
            {"Location:", location},
            {"Status:", status},
            {"Time Submitted:", time},
            {"Assigned Worker:", (assigned != null && !assigned.trim().isEmpty() ? assigned : "Unassigned")}
        };

        for (String[] detail : details) {
            JPanel row = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 4));
            row.setOpaque(false);
            row.setAlignmentX(LEFT_ALIGNMENT);

            JLabel lblKey = new JLabel(detail[0]);
            lblKey.setFont(labelFont);
            lblKey.setPreferredSize(new Dimension(150, 22));

            JLabel lblVal = new JLabel(detail[1]);
            lblVal.setFont(valueFont);

            if ("Status:".equalsIgnoreCase(detail[0])) {
                lblVal.setFont(new Font("Tahoma", Font.BOLD, 14));
                lblVal.setForeground(getStatusColor(status));
            }

            row.add(lblKey);
            row.add(lblVal);
            bodyPanel.add(row);
        }

        // Image Proof Clickable Row
        JPanel proofRow = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 4));
        proofRow.setOpaque(false);
        proofRow.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblProofKey = new JLabel("Image Proof:");
        lblProofKey.setFont(labelFont);
        lblProofKey.setPreferredSize(new Dimension(150, 22));

        JButton btnViewProof = new JButton(proof != null && !proof.trim().isEmpty() ? "📷 View Photo Proof" : "No Photo Attached");
        btnViewProof.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnViewProof.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewProof.addActionListener(e -> openImagePreviewDialog(proof));

        proofRow.add(lblProofKey);
        proofRow.add(btnViewProof);
        bodyPanel.add(proofRow);

        bodyPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblDescHead = new JLabel("Description:");
        lblDescHead.setFont(labelFont);
        lblDescHead.setAlignmentX(LEFT_ALIGNMENT);
        bodyPanel.add(lblDescHead);
        bodyPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

        JTextArea txtDescArea = new JTextArea(desc != null && !desc.trim().isEmpty() ? desc : "N/A");
        txtDescArea.setFont(valueFont);
        txtDescArea.setEditable(false);
        txtDescArea.setLineWrap(true);
        txtDescArea.setWrapStyleWord(true);
        txtDescArea.setBackground(new Color(248, 249, 250));

        JScrollPane descScroll = new JScrollPane(txtDescArea);
        descScroll.setPreferredSize(new Dimension(470, 75));
        descScroll.setAlignmentX(LEFT_ALIGNMENT);
        bodyPanel.add(descScroll);

        // --- Admin Resolution Remarks Section ---
        if (isResolved || hasRemarks) {
            bodyPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 10)));

            JLabel lblRemarksHead = new JLabel("Admin Remarks / Completion Log:");
            lblRemarksHead.setFont(labelFont);
            lblRemarksHead.setForeground(new Color(0, 102, 51));
            lblRemarksHead.setAlignmentX(LEFT_ALIGNMENT);
            bodyPanel.add(lblRemarksHead);
            bodyPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

            String remarksText = hasRemarks ? remarks : "Repair completed and verified by GSD Administration.";
            JTextArea txtRemarksArea = new JTextArea(remarksText);
            txtRemarksArea.setFont(valueFont);
            txtRemarksArea.setEditable(false);
            txtRemarksArea.setLineWrap(true);
            txtRemarksArea.setWrapStyleWord(true);
            txtRemarksArea.setBackground(new Color(235, 247, 238)); // Light mint highlight
            txtRemarksArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 153, 76), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            JScrollPane remarksScroll = new JScrollPane(txtRemarksArea);
            remarksScroll.setPreferredSize(new Dimension(470, 70));
            remarksScroll.setAlignmentX(LEFT_ALIGNMENT);
            bodyPanel.add(remarksScroll);
        }

        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
        mainPanel.add(bodyPanel, java.awt.BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, mainPanel, "Ticket Details", JOptionPane.PLAIN_MESSAGE);
    }

    private void openImagePreviewDialog(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No photo proof attached to this ticket.", "Image Proof", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        java.io.File imgFile = new java.io.File(imagePath);
        if (!imgFile.exists()) {
            JOptionPane.showMessageDialog(this, "Photo proof file not found at path:\n" + imagePath, "File Not Found", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imagePath);
            java.awt.Image img = icon.getImage();
            
            int origW = icon.getIconWidth();
            int origH = icon.getIconHeight();
            if (origW <= 0 || origH <= 0) {
                JOptionPane.showMessageDialog(this, "Unable to load image proof.", "Image Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int maxW = 600;
            int maxH = 500;
            double scale = Math.min((double) maxW / origW, (double) maxH / origH);
            int newW = (int) (origW * scale);
            int newH = (int) (origH * scale);

            java.awt.Image scaledImg = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new javax.swing.ImageIcon(scaledImg));
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JScrollPane imgScroll = new JScrollPane(imgLabel);
            imgScroll.setPreferredSize(new Dimension(newW + 20, newH + 20));

            JOptionPane.showMessageDialog(this, imgScroll, "Photo Proof Viewer", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening photo proof: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void archiveTicket(int ticketId, String code) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to archive ticket " + code + "?", "Archive Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String selectSql = "SELECT * FROM tbl_tickets WHERE ticket_id = ?";
        String insertSql = "INSERT INTO tbl_archived_tickets (ticket_id, username, room, building, equipment, others_specified, priority, description, status, assigned_worker, archived_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Archived', ?, ?)";
        String deleteSql = "DELETE FROM tbl_tickets WHERE ticket_id = ?";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement selStmt = conn.prepareStatement(selectSql);
                 PreparedStatement insStmt = conn.prepareStatement(insertSql);
                 PreparedStatement delStmt = conn.prepareStatement(deleteSql)) {

                selStmt.setInt(1, ticketId);
                ResultSet rs = selStmt.executeQuery();
                
                if (rs.next()) {
                    insStmt.setInt(1, rs.getInt("ticket_id"));
                    insStmt.setString(2, rs.getString("username"));
                    insStmt.setString(3, rs.getString("room"));
                    insStmt.setString(4, rs.getString("building"));
                    insStmt.setString(5, rs.getString("equipment"));
                    insStmt.setString(6, rs.getString("others_specified"));
                    insStmt.setString(7, rs.getString("priority"));
                    insStmt.setString(8, rs.getString("description"));
                    insStmt.setString(9, rs.getString("assigned_worker"));
                    insStmt.setString(10, UserSession.getFullName());
                    insStmt.executeUpdate();

                    delStmt.setInt(1, ticketId);
                    delStmt.executeUpdate();

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Ticket successfully archived!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadTickets();
                }
            } catch (SQLException ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Failed to archive ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void setupNavigation() {
        jButton4.addActionListener(e -> loadTickets());
       }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel24 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel24.setText("CITE Office, MS Building, Carmen Campus");
        jLabel24.setToolTipText("");
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Ticket Management");

        jPanel3.setBackground(new java.awt.Color(2, 153, 78));

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
        jButton2.setText("Add Ticket");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(2, 153, 78));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Ticket List");
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(2, 153, 78));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 204, 51));
        jButton4.setText("Ticket Management");
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

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

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/icons8-profile-48.png"))); // NOI18N
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jButton3)
                .addGap(34, 34, 34)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(60, 60, 60))
        );

        jTextField1.setText("Search Your Ticket...");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Pending", "Assigned", "In Progress", "Resolved" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort", "Old to New", "New to Old", "A > Z", "Z < A", "Low to Critical", "Critical to Low" }));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Ticket Code");

        jLabel10.setText("Problem");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Time & Date");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Location");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Status");

        jButton6.setText("View");

        jButton7.setText("Archive");

        jButton8.setText("Edit");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(344, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(47, 47, 47)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addContainerGap(449, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(jComboBox1)
                            .addComponent(jComboBox2))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
        new homepage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jButton2.setEnabled(false);
        new addticket().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton3.setEnabled(false);
        new ticketlist().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UserSession.clear();
            new login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(status.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(status.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(status.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(status.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new status().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
