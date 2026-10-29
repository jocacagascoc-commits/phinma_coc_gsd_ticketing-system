/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.adminUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;
import phinma_coc_gsd_ticketing_system.frstframes.login;
import phinma_coc_gsd_ticketing_system.userUI.profile;

/**
 *
 * @author ACER
 */
public class userAdmin extends javax.swing.JFrame {

    private JPanel userContainer;
    private boolean isUpdatingPlaceholder = false;
    
    private static class UserData {
        int id;
        String fullName;
        String dateSignedIn;
        String role;
        String status;

        public UserData(int id, String fullName, String dateSignedIn, String role, String status) {
            this.id = id;
            this.fullName = fullName;
            this.dateSignedIn = dateSignedIn;
            this.role = role;
            this.status = status;
        }
    }
    
    public userAdmin() {
        initComponents();
        
        setupFullScreenWrapper();

        setActiveSidebarButton(jButton7);
        addGoldHoverEffect(jButton1);
        addGoldHoverEffect(jButton2);
        addGoldHoverEffect(jButton4);
        addGoldHoverEffect(jButton9);
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
    // Auto-scale font to 18pt for "User Administration" to prevent text clipping
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
        // --- SEARCH & FILTER CONTROLS ---
        jTextField1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        jTextField1.setForeground(new Color(153, 153, 153));
        jTextField1.setText("Search Here...");

        jButton29.setFont(new Font("Tahoma", Font.BOLD, 12));
        jButton29.setFocusPainted(false);
        jButton29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Combo 2: Role Filter
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"All Users", "Normal Users", "Admin"}));
        jComboBox2.setFont(new Font("Tahoma", Font.PLAIN, 13));

        // Combo 1: Status Filter
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"All", "Active", "Promoted", "Unpromoted"}));
        jComboBox1.setFont(new Font("Tahoma", Font.PLAIN, 13));

        // Title & Account Archives Button
        jLabel3.setFont(new Font("Tahoma", Font.BOLD, 22));
        
        jButton17.setFont(new Font("Tahoma", Font.BOLD, 13));
        jButton17.setFocusPainted(false);
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton17.addActionListener(e -> openAccArchModal());{
        }

        // --- MAIN CONTAINER TABLE (jPanel4) ---
        jPanel4.removeAll();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.setPreferredSize(new Dimension(1030, 520));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 102, 51), 1));

        // Sub-Header Column Labels
        JPanel tableSubHeader = new JPanel(null);
        tableSubHeader.setPreferredSize(new Dimension(1030, 35));
        tableSubHeader.setBackground(new Color(245, 245, 245));
        tableSubHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        String[] headers = {"User ID", "Full Name", "Date Signed In", "Role", "Status"};
            int[] hX = {10, 85, 255, 440, 545};
            int[] hW = {60, 160, 175, 95, 95};
            int[] hAlign = {SwingConstants.CENTER, SwingConstants.LEFT, SwingConstants.LEFT, SwingConstants.LEFT, SwingConstants.CENTER};

        for (int i = 0; i < headers.length; i++) {
            JLabel lbl = new JLabel(headers[i], hAlign[i]);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 13));
            lbl.setBounds(hX[i], 8, hW[i], 20);
            tableSubHeader.add(lbl);
        }

        userContainer = new JPanel();
        userContainer.setLayout(new BoxLayout(userContainer, BoxLayout.Y_AXIS));
        userContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(userContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);

        jPanel4.setBackground(Color.WHITE);
        jPanel4.add(tableSubHeader, BorderLayout.NORTH);
        jPanel4.add(scrollPane, BorderLayout.CENTER);

        // --- LISTENERS ---
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if ("Search Here...".equals(jTextField1.getText())) {
                    isUpdatingPlaceholder = true;
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK);
                    isUpdatingPlaceholder = false;
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jTextField1.getText().trim().isEmpty()) {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        if (jTextField1.getText().trim().isEmpty() && !jTextField1.hasFocus()) {
                            isUpdatingPlaceholder = true;
                            jTextField1.setForeground(new Color(153, 153, 153));
                            jTextField1.setText("Search Here...");
                            isUpdatingPlaceholder = false;
                        }
                    });
                }
            }
        });

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { if (!isUpdatingPlaceholder) loadUsers(); }
            public void removeUpdate(DocumentEvent e) { if (!isUpdatingPlaceholder) loadUsers(); }
            public void changedUpdate(DocumentEvent e) { if (!isUpdatingPlaceholder) loadUsers(); }
        });

        jComboBox1.addActionListener(e -> loadUsers());
        jComboBox2.addActionListener(e -> loadUsers());

        jButton29.addActionListener(e -> {
            isUpdatingPlaceholder = true;
            jTextField1.setText("Search Here...");
            jTextField1.setForeground(new Color(153, 153, 153));
            isUpdatingPlaceholder = false;
            jComboBox1.setSelectedIndex(0);
            jComboBox2.setSelectedIndex(0);
            loadUsers();
        });

        loadUsers();
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private void openAccArchModal() {
    // 1. Semi-transparent dark background overlay
    JPanel glassPane = new JPanel() {
        @Override
        protected void paintComponent(java.awt.Graphics g) {
            java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 110));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    };
    glassPane.setOpaque(false);
    this.setGlassPane(glassPane);
    glassPane.setVisible(true);

    Color lightGreen = new Color(222, 252, 232);

    // 2. Create Modal Dialog
    JDialog archDialog = new JDialog(this, "Archived Accounts", true);
    archDialog.setUndecorated(true);
    
    // Instantiate panel
    accArch archPanel = new accArch();
    archPanel.setParentDialog(archDialog);

    // 3. Extract content pane and force background color across all levels
    java.awt.Container contentPane = archPanel.getContentPane();
    contentPane.setBackground(lightGreen);
    
    // Force exact size matching the interior content height
    archDialog.setContentPane(contentPane);
    archDialog.pack(); // Automatically shrinks window to fit content exactly, removing gray padding
    archDialog.setLocationRelativeTo(this);

    archDialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            glassPane.setVisible(false);
            loadUsers();
        }
    });

    archDialog.setVisible(true);
}
    
    public void loadUsers() {
        userContainer.removeAll();

        String keyword = jTextField1.getText().trim();
        if ("Search Here...".equals(keyword)) keyword = "";

        String roleFilter = jComboBox2.getSelectedItem() != null ? jComboBox2.getSelectedItem().toString() : "All Users";
        String statusFilter = jComboBox1.getSelectedItem() != null ? jComboBox1.getSelectedItem().toString() : "All";

        List<UserData> users = fetchUsers(keyword, roleFilter, statusFilter);

        if (users.isEmpty()) {
            JPanel emptyPanel = new JPanel(new java.awt.GridBagLayout());
            emptyPanel.setBackground(Color.WHITE);
            emptyPanel.setPreferredSize(new Dimension(1010, 180));
            emptyPanel.setMaximumSize(new Dimension(1010, 180));

            JLabel lblEmpty = new JLabel("No matching user records found.");
            lblEmpty.setFont(new Font("Tahoma", Font.ITALIC, 15));
            lblEmpty.setForeground(new Color(120, 120, 120));
            
            emptyPanel.add(lblEmpty);
            userContainer.add(emptyPanel);
        } else {
            for (UserData u : users) {
                userContainer.add(createUserRowCard(u));
            }
        }

        userContainer.revalidate();
        userContainer.repaint();
    }

    private List<UserData> fetchUsers(String keyword, String roleFilter, String statusFilter) {
    List<UserData> list = new ArrayList<>();

    String resetExpiredStatusSql = "UPDATE tbl_users SET status = 'Active' "
                                 + "WHERE status IN ('Promoted', 'Unpromoted') "
                                 + "AND status_updated_at IS NOT NULL "
                                 + "AND status_updated_at < NOW() - INTERVAL 1 DAY";

    try (Connection conn = DBConnection.connect();
         PreparedStatement resetStmt = conn.prepareStatement(resetExpiredStatusSql)) {
        resetStmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    String sql = "SELECT * FROM tbl_users WHERE 1=1";

    if (!keyword.isEmpty()) {
        sql += " AND (CAST(id AS CHAR) LIKE ? OR LOWER(full_name) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?))";
    }
    
    // Filter Normal Users (Includes 'student', 'normal user', or non-admin)
    if (!"All Users".equalsIgnoreCase(roleFilter)) {
        if ("Normal Users".equalsIgnoreCase(roleFilter)) {
            sql += " AND (LOWER(role) = 'normal user' OR LOWER(role) = 'student' OR LOWER(role) != 'admin')";
        } else if ("Admin".equalsIgnoreCase(roleFilter)) {
            sql += " AND LOWER(role) = 'admin'";
        }
    }
    if (!"All".equalsIgnoreCase(statusFilter)) {
        sql += " AND LOWER(status) = LOWER(?)";
    }
    
    sql += " ORDER BY id ASC";

    try (Connection conn = DBConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        int paramIndex = 1;
        if (!keyword.isEmpty()) {
            String pat = "%" + keyword + "%";
            pstmt.setString(paramIndex++, pat);
            pstmt.setString(paramIndex++, pat);
            pstmt.setString(paramIndex++, pat);
        }
        if (!"All".equalsIgnoreCase(statusFilter)) {
            pstmt.setString(paramIndex++, statusFilter);
        }
        
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("full_name") != null ? rs.getString("full_name") : "N/A";
            String email = rs.getString("email") != null ? rs.getString("email") : "N/A";
            
            String rawRole = rs.getString("role");
            String role = ("Admin".equalsIgnoreCase(rawRole)) ? "Admin" : "Normal User";
            
            String status = rs.getString("status") != null ? rs.getString("status") : "Active";

            list.add(new UserData(id, name, email, role, status));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching users: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    return list;
}

    private JPanel createUserRowCard(UserData u) {
    JPanel row = new JPanel(null);
    row.setBackground(Color.WHITE);
    row.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
    row.setPreferredSize(new Dimension(1010, 45));
    row.setMaximumSize(new Dimension(1010, 45));
    row.setMinimumSize(new Dimension(1010, 45));

    // User ID
    JLabel lblId = new JLabel(String.valueOf(u.id), SwingConstants.CENTER);
    lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
    lblId.setBounds(10, 12, 60, 20);

    // Full Name
    JLabel lblName = new JLabel(u.fullName);
    lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
    lblName.setBounds(85, 12, 160, 20);

    // Email / Date Signed In
    JLabel lblDate = new JLabel(u.dateSignedIn);
    lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
    lblDate.setBounds(255, 12, 175, 20);

    // Role
    JLabel lblRole = new JLabel(u.role);
    lblRole.setFont(new Font("Tahoma", Font.PLAIN, 13));
    lblRole.setBounds(440, 12, 95, 20);

    // Status Badge
    JLabel lblStatus = new JLabel(u.status, SwingConstants.CENTER);
    lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblStatus.setOpaque(true);
    lblStatus.setBounds(545, 10, 95, 24);

    if ("Promoted".equalsIgnoreCase(u.status)) {
        lblStatus.setBackground(new Color(255, 204, 0)); // Gold / Yellow
        lblStatus.setForeground(Color.BLACK);
    } else if ("Unpromoted".equalsIgnoreCase(u.status)) {
        lblStatus.setBackground(new Color(235, 130, 0)); // Orange
        lblStatus.setForeground(Color.WHITE);
    } else {
        lblStatus.setBackground(new Color(0, 204, 51)); // Active Green
        lblStatus.setForeground(Color.WHITE);
    }

    // Action Buttons Panel
    JPanel actionPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 0));
    actionPanel.setOpaque(false);
    actionPanel.setBounds(650, 8, 345, 30);

    // View Button
    JButton btnView = createActionButton("View");
    btnView.addActionListener(e -> showUserDetailsDialog(u));
    actionPanel.add(btnView);

    // Promote / Unpromote Button
    if ("Admin".equalsIgnoreCase(u.role)) {
        JButton btnUnpromote = createActionButton("Unpromote");
        btnUnpromote.addActionListener(e -> updateUserRoleStatus(u.id, "Normal User", "Unpromoted"));
        actionPanel.add(btnUnpromote);
    } else {
        JButton btnPromote = createActionButton("Promote");
        btnPromote.addActionListener(e -> updateUserRoleStatus(u.id, "Admin", "Promoted"));
        actionPanel.add(btnPromote);
    }

    // Archive Button
    JButton btnArchive = createActionButton("Archive");
    btnArchive.addActionListener(e -> archiveUserAccount(u.id, u.fullName));
    actionPanel.add(btnArchive);

    row.add(lblId);
    row.add(lblName);
    row.add(lblDate);
    row.add(lblRole);
    row.add(lblStatus);
    row.add(actionPanel);

    return row;
}

    private JButton createActionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        btn.setPreferredSize(new Dimension(95, 26));
        return btn;
    }

    private JButton createActionButton(String text, boolean primary) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        btn.setPreferredSize(new Dimension(85, 26));
        return btn;
    }

    private void showUserDetailsDialog(UserData u) {
        JDialog dialog = new JDialog(this, "User Account Details", true);
        dialog.setSize(480, 360);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(new Color(0, 102, 51));
        headerPanel.setBounds(0, 0, 480, 45);

        JLabel lblHeaderTitle = new JLabel("User Details");
        lblHeaderTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblHeaderTitle.setForeground(Color.WHITE);
        lblHeaderTitle.setBounds(20, 10, 200, 24);
        headerPanel.add(lblHeaderTitle);

        JLabel lblHeaderTag = new JLabel("[User #" + u.id + "]", SwingConstants.RIGHT);
        lblHeaderTag.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblHeaderTag.setForeground(new Color(255, 204, 51));
        lblHeaderTag.setBounds(320, 10, 135, 24);
        headerPanel.add(lblHeaderTag);

        dialog.add(headerPanel);

        JLabel lblDetails = new JLabel("<html><body style='font-family:Tahoma; font-size:12pt; line-height:1.5;'>"
                + "<b>User ID:</b> " + u.id + "<br><br>"
                + "<b>Full Name:</b> " + u.fullName + "<br><br>"
                + "<b>Date Signed In:</b> " + u.dateSignedIn + "<br><br>"
                + "<b>Role:</b> " + u.role + "<br><br>"
                + "<b>Status:</b> " + u.status + "</body></html>");
        lblDetails.setVerticalAlignment(SwingConstants.TOP);
        lblDetails.setBounds(25, 60, 430, 200);
        dialog.add(lblDetails);

        // Footer
        JPanel footerPanel = new JPanel(null);
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBounds(0, 255, 480, 65);
        footerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnClose.setBounds(345, 12, 105, 34);
        btnClose.setBackground(Color.WHITE);
        btnClose.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(e -> dialog.dispose());

        footerPanel.add(btnClose);
        dialog.add(footerPanel);

        dialog.setVisible(true);
    }

    private void archiveUserAccount(int userId, String targetUserName) {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to archive account for " + targetUserName + "?", 
            "Confirm Archive", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Default active performing admin
            String currentAdminName = "Admin";

            String archiveSql = "INSERT INTO tbl_archived_users (id, full_name, email, password, role, id_number, department, year_level, archived_by) "
                              + "SELECT id, full_name, email, password, role, id_number, department, year_level, ? FROM tbl_users WHERE id = ?";
            String deleteSql = "DELETE FROM tbl_users WHERE id = ?";

            try (Connection conn = DBConnection.connect()) {
                conn.setAutoCommit(false);

                try (PreparedStatement stmtArchive = conn.prepareStatement(archiveSql);
                     PreparedStatement stmtDelete = conn.prepareStatement(deleteSql)) {

                    stmtArchive.setString(1, currentAdminName);
                    stmtArchive.setInt(2, userId);
                    stmtArchive.executeUpdate();

                    stmtDelete.setInt(1, userId);
                    stmtDelete.executeUpdate();

                    conn.commit();
                    JOptionPane.showMessageDialog(
                        this, 
                        "Account for " + targetUserName + " successfully archived!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (SQLException ex) {
                    conn.rollback();
                    throw ex;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error archiving account: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            loadUsers();
        }
    }
    
    private void updateUserRoleStatus(int userId, String newRole, String newStatus) {
    int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Are you sure you want to change user #" + userId + " role to " + newRole + "?", 
        "Confirm Action", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        String sql = "UPDATE tbl_users SET role = ?, status = ?, status_updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newRole);
            pstmt.setString(2, newStatus);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "User successfully updated to " + newRole + " (" + newStatus + ")!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating user role: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        loadUsers();
    }
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
        jButton9 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton29 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();

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

        jPanel3.setBackground(new java.awt.Color(2, 153, 78));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        jButton9.setBackground(new java.awt.Color(2, 153, 78));
        jButton9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Archives");
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(2, 153, 78));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 204, 51));
        jButton7.setText("User Administration");
        jButton7.setContentAreaFilled(false);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/icons8-profile-48.png"))); // NOI18N
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10)
                .addGap(72, 72, 72)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(jButton2)
                .addGap(34, 34, 34)
                .addComponent(jButton4)
                .addGap(34, 34, 34)
                .addComponent(jButton9)
                .addGap(34, 34, 34)
                .addComponent(jButton7)
                .addGap(34, 34, 34)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(60, 60, 60))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("User ID");
        jLabel10.setToolTipText("");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Role");
        jLabel11.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Full Name");
        jLabel13.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Date Signed In");
        jLabel14.setToolTipText("");

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton11.setText("Archive");

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton12.setText("Promote");

        jLabel9.setText("1");

        jLabel15.setText("Juan Cruz");

        jLabel16.setText("09-21-26 | 07:35PM");

        jLabel17.setText("Normal User");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("View");

        jLabel12.setText("2");

        jLabel18.setText("Steven He");

        jLabel19.setText("09-24-26 | 10:46AM");

        jLabel20.setText("Admin");

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton8.setText("View");

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton14.setText("Unpromote");

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton15.setText("Archive");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Status");
        jLabel21.setToolTipText("");

        jLabel22.setBackground(new java.awt.Color(0, 204, 51));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText(" Active");
        jLabel22.setOpaque(true);

        jLabel23.setBackground(new java.awt.Color(0, 204, 51));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText(" Active");
        jLabel23.setOpaque(true);

        jLabel24.setText("3");

        jLabel25.setText("Stacy Magsaysay");

        jLabel26.setText("09-24-26 | 11:30AM");

        jLabel27.setText("Normal User");

        jLabel29.setText("4");

        jLabel30.setText("Alice Go");

        jLabel31.setText("09-27-26 | 01:47PM");
        jLabel31.setToolTipText("");

        jLabel32.setText("Admin");

        jLabel33.setBackground(new java.awt.Color(255, 255, 0));
        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel33.setText(" Promoted");
        jLabel33.setOpaque(true);

        jButton22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton22.setText("Archive");

        jButton23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton23.setText("Unpromote");

        jButton24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton24.setText("View");

        jLabel34.setText("5");

        jLabel35.setText("Donald Trump");

        jLabel36.setText("09-27-26 | 01:47PM");
        jLabel36.setToolTipText("");

        jLabel37.setText("Normal User");

        jLabel38.setBackground(new java.awt.Color(153, 153, 153));
        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Unpromoted");
        jLabel38.setOpaque(true);

        jButton26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton26.setText("Archive");

        jButton27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton27.setText("Promote");

        jButton28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton28.setText("View");

        jLabel39.setBackground(new java.awt.Color(0, 204, 51));
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText(" Active");
        jLabel39.setOpaque(true);

        jButton30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton30.setText("Archive");

        jButton31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton31.setText("Promote");

        jButton32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton32.setText("View");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addComponent(jSeparator5)
            .addComponent(jSeparator6)
            .addComponent(jSeparator1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel36)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton28)
                        .addGap(18, 18, 18)
                        .addComponent(jButton27)
                        .addGap(18, 18, 18)
                        .addComponent(jButton26)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel16)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton12)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel19)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addGap(18, 18, 18)
                        .addComponent(jButton14)
                        .addGap(18, 18, 18)
                        .addComponent(jButton15)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel26)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton32)
                        .addGap(18, 18, 18)
                        .addComponent(jButton31)
                        .addGap(18, 18, 18)
                        .addComponent(jButton30)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel31)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton24)
                        .addGap(18, 18, 18)
                        .addComponent(jButton23)
                        .addGap(18, 18, 18)
                        .addComponent(jButton22)
                        .addGap(27, 27, 27))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jLabel9)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jButton3)
                    .addComponent(jLabel22))
                .addGap(7, 7, 7)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8)
                            .addComponent(jButton14)
                            .addComponent(jButton15)
                            .addComponent(jLabel23))
                        .addGap(16, 16, 16)))
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel39)
                    .addComponent(jButton30)
                    .addComponent(jButton31)
                    .addComponent(jButton32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jButton22)
                    .addComponent(jButton23)
                    .addComponent(jButton24))
                .addGap(8, 8, 8)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34)
                        .addComponent(jLabel35)
                        .addComponent(jLabel36)
                        .addComponent(jLabel37))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton26)
                            .addComponent(jButton27)
                            .addComponent(jButton28))
                        .addGap(1, 1, 1)))
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("USER ADMINISTRATION");

        jButton17.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton17.setText("Account Archives");
        jButton17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField1.setText("Search Here...");

        jButton29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton29.setText("Clear Search");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Active", "Promoted", "Unpromoted", "Blocked" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Users", "Normal Users", "Admin" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(584, 584, 584))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1)
                                .addGap(0, 0, 0)
                                .addComponent(jButton29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        new homepageadmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jButton2.setEnabled(false);
        new viewtickets().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jButton4.setEnabled(false);
        new workerslist().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UserSession.clear();
            new login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jButton6.setEnabled(false);
        new update().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jButton9.setEnabled(false);
        new archives().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jButton10.setEnabled(false); // Prevents opening duplicate profile windows
        
        profile prof = new profile();
        prof.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                jButton10.setEnabled(true); // Re-enables button when profile window is closed
            }
        });
        prof.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

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
            java.util.logging.Logger.getLogger(userAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
