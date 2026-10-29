/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.userUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;

public class userArchives extends javax.swing.JFrame {

    private JPanel archiveContainer;
    private JScrollPane scrollPane;
    private boolean isUpdatingPlaceholder = false;
    
    public userArchives() {
        initComponents();

        fixLayoutSpacing();
        setupTableHeaders();
        setupDynamicContainer();
        setupFullScreenWrapper();
        setupSearchAndFilters();
        setupNavigation();
        loadArchivedTickets();
    }

    private java.awt.Image bgImage;
    
    private void setupFullScreenWrapper() {
    this.setSize(1280, 750);
    this.setPreferredSize(new java.awt.Dimension(1280, 750));
    this.setMinimumSize(new java.awt.Dimension(1280, 750));
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
    dashboardContainer.setPreferredSize(new java.awt.Dimension(1280, 750));
    dashboardContainer.setMinimumSize(new java.awt.Dimension(1280, 750));
    dashboardContainer.setMaximumSize(new java.awt.Dimension(1280, 750));

    jPanel1.setBounds(0, 0, 1280, 750);
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
    
    private void fixLayoutSpacing() {
        // 1. Center the "ARCHIVES" title banner across full frame width
        jPanel2.removeAll();
        jPanel2.setLayout(new java.awt.BorderLayout());
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        // 2. Eliminate large empty gap between search bar, clear button, and dropdown
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 493, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
    }
    
    private void setupTableHeaders() {
        jPanel3.setLayout(null);
        jPanel3.setPreferredSize(new Dimension(1230, 32));
        jPanel3.setBackground(new Color(245, 247, 250));
        jPanel3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 102, 51)));
        jPanel3.removeAll();

        // 1. Ticket Code
        jLabel2.setText("Ticket Code");
        jLabel2.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel2.setBounds(15, 6, 100, 20);
        jPanel3.add(jLabel2);

        // 2. Problem
        jLabel3.setText("Problem");
        jLabel3.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel3.setBounds(135, 6, 230, 20);
        jPanel3.add(jLabel3);

        // 3. Location
        jLabel4.setText("Location");
        jLabel4.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel4.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel4.setBounds(385, 6, 200, 20);
        jPanel3.add(jLabel4);

        // 4. Time Archived
        jLabel5.setText("Time Archived");
        jLabel5.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setBounds(605, 6, 200, 20);
        jPanel3.add(jLabel5);

        // 5. Archived By
        jLabel6.setText("Archived By");
        jLabel6.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel6.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel6.setBounds(825, 6, 200, 20);
        jPanel3.add(jLabel6);
    }

    private void setupDynamicContainer() {
        archiveContainer = new JPanel();
        archiveContainer.setLayout(new javax.swing.BoxLayout(archiveContainer, javax.swing.BoxLayout.Y_AXIS));
        archiveContainer.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(archiveContainer);
        scrollPane.setBounds(25, 167, 1230, 480);
        scrollPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(0, 102, 51)));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    
    private void setupSearchAndFilters() {
        jTextField1.setText("Search Here...");
        jTextField1.setForeground(Color.GRAY);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().equals("Search Here...")) {
                    isUpdatingPlaceholder = true;
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK);
                    isUpdatingPlaceholder = false;
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().trim().isEmpty()) {
                    isUpdatingPlaceholder = true;
                    jTextField1.setText("Search Here...");
                    jTextField1.setForeground(Color.GRAY);
                    isUpdatingPlaceholder = false;
                }
            }
        });

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { triggerSearch(); }
            public void removeUpdate(DocumentEvent e) { triggerSearch(); }
            public void insertUpdate(DocumentEvent e) { triggerSearch(); }

            private void triggerSearch() {
                if (!isUpdatingPlaceholder) {
                    loadArchivedTickets();
                }
            }
        });

        jButton1.addActionListener(e -> {
            isUpdatingPlaceholder = true;
            jTextField1.setText("Search Here...");
            jTextField1.setForeground(Color.GRAY);
            isUpdatingPlaceholder = false;
            loadArchivedTickets();
        });

        jComboBox1.addActionListener(e -> loadArchivedTickets());
    }
    
    private void loadArchivedTickets() {
        if (archiveContainer == null) return;
        archiveContainer.removeAll();

        String keyword = jTextField1.getText().trim();
        if (keyword.equals("Search Here...")) keyword = "";

        StringBuilder sql = new StringBuilder("SELECT * FROM tbl_archived_tickets WHERE username = ?");
        if (!keyword.isEmpty()) {
            sql.append(" AND (equipment LIKE ? OR room LIKE ? OR building LIKE ? OR CAST(ticket_id AS CHAR) LIKE ?)");
        }
        sql.append(" ORDER BY archive_id DESC");

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            pstmt.setString(paramIndex++, UserSession.getFullName());

            if (!keyword.isEmpty()) {
                String pattern = "%" + keyword + "%";
                pstmt.setString(paramIndex++, pattern);
                pstmt.setString(paramIndex++, pattern);
                pstmt.setString(paramIndex++, pattern);
                pstmt.setString(paramIndex++, pattern);
            }

            ResultSet rs = pstmt.executeQuery();
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                int archiveId = rs.getInt("archive_id");
                int ticketId = rs.getInt("ticket_id");
                String equipment = rs.getString("equipment");
                String room = rs.getString("room");
                String building = rs.getString("building");
                String archivedBy = rs.getString("archived_by");
                if (archivedBy == null || archivedBy.isEmpty()) archivedBy = "System";

                String timeArchived = "N/A";
                try {
                    java.sql.Timestamp ts = rs.getTimestamp("archived_at");
                    if (ts != null) {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy / hh:mm a");
                        timeArchived = sdf.format(ts);
                    }
                } catch (Exception ex) {}

                String code = String.format("#%04d", ticketId);
                String problem = equipment != null ? equipment : "General Repair";
                String location = (room != null ? room : "") + ", " + (building != null ? building : "");

                JPanel card = createArchiveRowCard(archiveId, ticketId, code, problem, location, timeArchived, archivedBy);
                archiveContainer.add(card);
            }

            if (!hasData) {
                archiveContainer.add(javax.swing.Box.createRigidArea(new Dimension(0, 30)));
                JLabel noData = new JLabel("No archived tickets found.");
                noData.setFont(new Font("Tahoma", Font.BOLD, 16));
                noData.setForeground(Color.GRAY);
                noData.setAlignmentX(CENTER_ALIGNMENT);
                archiveContainer.add(noData);
            }
            
            } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading archives: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        archiveContainer.revalidate();
        archiveContainer.repaint();
    }
    
    private JPanel createArchiveRowCard(int archiveId, int ticketId, String code, String problem, String location, String time, String archivedBy) {
        JPanel row = new JPanel();
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        row.setPreferredSize(new Dimension(1230, 45));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        row.setLayout(null);

        JLabel lblCode = new JLabel(code, SwingConstants.LEFT);
        lblCode.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCode.setBounds(15, 12, 100, 20);
        row.add(lblCode);

        JLabel lblProb = new JLabel(problem, SwingConstants.LEFT);
        lblProb.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProb.setBounds(135, 12, 230, 20);
        row.add(lblProb);

        JLabel lblLoc = new JLabel(location, SwingConstants.LEFT);
        lblLoc.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblLoc.setBounds(385, 12, 200, 20);
        row.add(lblLoc);

        JLabel lblTime = new JLabel(time, SwingConstants.CENTER);
        lblTime.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblTime.setForeground(Color.DARK_GRAY);
        lblTime.setBounds(605, 12, 200, 20);
        row.add(lblTime);

        JLabel lblBy = new JLabel(archivedBy, SwingConstants.LEFT);
        lblBy.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblBy.setBounds(825, 12, 200, 20);
        row.add(lblBy);

        JButton btnRestore = new JButton("Restore");
        btnRestore.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRestore.setBounds(1080, 9, 110, 26);
        btnRestore.addActionListener(e -> restoreTicket(archiveId, ticketId));
        row.add(btnRestore);

        return row;
    }
    
    private void restoreTicket(int archiveId, int ticketId) {
        String selectSql = "SELECT * FROM tbl_archived_tickets WHERE archive_id = ?";
        String checkDuplicateSql = "SELECT COUNT(*) FROM tbl_tickets WHERE username = ? AND room = ? AND equipment = ? AND status IN ('Pending', 'Assigned', 'In Progress')";
        String insertSql = "INSERT INTO tbl_tickets (username, room, building, equipment, others_specified, priority, description, status) VALUES (?, ?, ?, ?, ?, ?, ?, 'Pending')";
        String deleteSql = "DELETE FROM tbl_archived_tickets WHERE archive_id = ?";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);
            
            // 1. Retrieve archived ticket
            try (PreparedStatement selStmt = conn.prepareStatement(selectSql)) {
                selStmt.setInt(1, archiveId);
                ResultSet rs = selStmt.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "This archived ticket record no longer exists.", "Restore Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String username = rs.getString("username");
                String room = rs.getString("room");
                String building = rs.getString("building");
                String equipment = rs.getString("equipment");
                String others = rs.getString("others_specified");
                String priority = rs.getString("priority");
                String description = rs.getString("description");

                // 2. Validation: Prevent restoring if active ticket for same item & room already exists
                try (PreparedStatement checkStmt = conn.prepareStatement(checkDuplicateSql)) {
                    checkStmt.setString(1, username);
                    checkStmt.setString(2, room);
                    checkStmt.setString(3, equipment);
                    ResultSet checkRs = checkStmt.executeQuery();
                    if (checkRs.next() && checkRs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, 
                            "An active ticket for '" + equipment + "' in " + room + " is already active in your Ticket List.\nCannot restore duplicate active ticket.", 
                            "Restore Validation Warning", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                // 3. User Confirmation
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Restore ticket " + String.format("#%04d", ticketId) + " (" + equipment + ") back to active list?", 
                    "Confirm Restore", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                // 4. Execute Restore Transaction
                try (PreparedStatement insStmt = conn.prepareStatement(insertSql);
                     PreparedStatement delStmt = conn.prepareStatement(deleteSql)) {

                    insStmt.setString(1, username);
                    insStmt.setString(2, room);
                    insStmt.setString(3, building);
                    insStmt.setString(4, equipment);
                    insStmt.setString(5, others);
                    insStmt.setString(6, priority);
                    insStmt.setString(7, description);
                    insStmt.executeUpdate();

                    delStmt.setInt(1, archiveId);
                    delStmt.executeUpdate();

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Ticket " + String.format("#%04d", ticketId) + " restored successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadArchivedTickets();
                }
            } catch (SQLException ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Failed to restore ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setupNavigation() {
        jButton4.addActionListener(e -> {
            new ticketlist().setVisible(true);
            this.dispose();
        });
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(222, 252, 232));

        jPanel2.setBackground(new java.awt.Color(0, 102, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ARCHIVES");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Ticket Code");

        jLabel3.setText("Problem");

        jLabel4.setText("Location");

        jLabel5.setText("Time Archived");

        jLabel6.setText("Archived By");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("Restore");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(33, 33, 33)
                .addComponent(jLabel3)
                .addGap(120, 120, 120)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(201, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(47, 47, 47))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(417, Short.MAX_VALUE))
        );

        jTextField1.setText("Search Here...");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "1-6 Days Ago", "1 Weeks Ago", "3 Weeks Ago", "1 Month Ago", "5 Months Ago", "1 Year Ago", "2-5 Years Ago" }));

        jButton1.setText("Clear Search");

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Back to Ticket List");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextField1)
                            .addGap(0, 0, 0)
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(userArchives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userArchives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userArchives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userArchives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userArchives().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
