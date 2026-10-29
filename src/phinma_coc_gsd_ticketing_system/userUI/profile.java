/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.userUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;

public class profile extends javax.swing.JDialog {

    private JTextField txtName;
    private JTextField txtIdNumber;
    private JTextField txtDepartment;
    private JTextField txtYear;
    private BufferedImage blurredBg;

    public profile() {
        this(null);
    }
    
    public profile(java.awt.Frame parent) {
        super(parent, true); // Modal dialog over parent window
    setUndecorated(true); // Removes standard OS window titlebar
    setBackground(new java.awt.Color(0, 0, 0, 0)); // Enables window transparency

    if (parent != null) {
        this.blurredBg = captureBlurredSnapshot(parent);
    }
    initComponents();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jLabel1.setVisible(false);
    this.setResizable(false);

    setupCleanCardLayout(); 
    setupEditableFields();
    loadUserProfile();
    setupListeners();

    if (parent != null) {
        setSize(parent.getSize());
        setLocationRelativeTo(parent);
    } else {
        setSize(1310, 780);
        setLocationRelativeTo(null);
    }
}

    private BufferedImage captureBlurredSnapshot(java.awt.Frame parent) {
        int w = parent.getWidth();
        int h = parent.getHeight();
        if (w <= 0 || h <= 0) return null;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        parent.paint(g2);
        g2.dispose();
        return applyBlurAndDarken(img, 14);
    }
    
    private BufferedImage applyBlurAndDarken(BufferedImage src, int radius) {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[] pixels = src.getRGB(0, 0, width, height, null, 0, width);
        int[] temp = new int[width * height];

        boxBlur(pixels, temp, width, height, radius);
        boxBlur(temp, pixels, height, width, radius);

        dest.setRGB(0, 0, width, height, pixels, 0, width);

        // Apply a subtle 35% semi-transparent dark overlay
        Graphics2D g2 = dest.createGraphics();
        g2.setColor(new Color(0, 0, 0, 90));
        g2.fillRect(0, 0, width, height);
        g2.dispose();

        return dest;
    }

    private void boxBlur(int[] in, int[] out, int width, int height, int radius) {
        int divide = radius + radius + 1;
        for (int y = 0; y < height; y++) {
            int a = 0, r = 0, g = 0, b = 0;
            int offset = y * width;
            for (int i = -radius; i <= radius; i++) {
                int p = in[offset + Math.min(Math.max(i, 0), width - 1)];
                a += (p >> 24) & 0xff;
                r += (p >> 16) & 0xff;
                g += (p >> 8) & 0xff;
                b += p & 0xff;
            }
            for (int x = 0; x < width; x++) {
                out[offset + x] = ((a / divide) << 24) | ((r / divide) << 16) | ((g / divide) << 8) | (b / divide);
                int p1 = in[offset + Math.min(x + radius + 1, width - 1)];
                int p2 = in[offset + Math.max(x - radius, 0)];
                a += ((p1 >> 24) & 0xff) - ((p2 >> 24) & 0xff);
                r += ((p1 >> 16) & 0xff) - ((p2 >> 16) & 0xff);
                g += ((p1 >> 8) & 0xff) - ((p2 >> 8) & 0xff);
                b += (p1 & 0xff) - (p2 & 0xff);
            }
        }
    }
    
    private void setupCleanCardLayout() {
        getContentPane().removeAll();
    
    // Full-screen blurred background overlay (100% of frame window)
        JPanel overlayPanel = new JPanel() {
    @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
        if (blurredBg != null) {
            g2.drawImage(blurredBg, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2.setColor(new Color(0, 0, 0, 110)); // Translucent dark overlay over parent frame
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
            g2.dispose();
        }
    };
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(new GridBagLayout());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(overlayPanel, BorderLayout.CENTER);
    
    // Centered Profile Card
    jPanel1.removeAll();
    jPanel1.setPreferredSize(new Dimension(520, 580));
    jPanel1.setMinimumSize(new Dimension(520, 580));
    jPanel1.setBackground(new Color(222, 252, 232)); // Light mint green background
    jPanel1.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(0, 153, 78), 2, true),
        BorderFactory.createEmptyBorder(20, 25, 25, 25)
    ));
    
    jPanel1.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    
    // Top Exit Button Row
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setOpaque(false);
    jButton6.setFocusPainted(false);
    headerPanel.add(jButton6, BorderLayout.WEST);

    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    gbc.insets = new Insets(0, 0, 10, 0);
    jPanel1.add(headerPanel, gbc);
    
    // Title Header
    jLabel6.setFont(new Font("Tahoma", Font.BOLD, 20));
    jLabel6.setHorizontalAlignment(JLabel.CENTER);
    gbc.gridy = 1;
    gbc.insets = new Insets(0, 0, 25, 0);
    jPanel1.add(jLabel6, gbc);
    
    // Form Input Rows (Name, ID, Department, Year)
    JLabel[] labels = {jLabel2, jLabel3, jLabel5, jLabel4};
    JPanel[] valuePanels = {jPanel2, jPanel3, jPanel4, jPanel5};
    String[] defaultTexts = {"Name:", "ID Number:", "Department:", "Year:"};
    
    for (int i = 0; i < labels.length; i++) {
        JLabel lbl = labels[i];
        JPanel valPanel = valuePanels[i];

        lbl.setText(defaultTexts[i]);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbl.setPreferredSize(new Dimension(110, 35)); // Uniform width for perfect alignment

        valPanel.setPreferredSize(new Dimension(320, 35));
        valPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2 + i;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(8, 0, 8, 10); // Equal row spacing
        jPanel1.add(lbl, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(8, 0, 8, 0);
        jPanel1.add(valPanel, gbc);
    }
    
    // "Update Profile" Button
    jButton5.setFont(new Font("Tahoma", Font.BOLD, 16));
    jButton5.setBackground(new Color(0, 153, 78));
    jButton5.setForeground(Color.WHITE);
    jButton5.setFocusPainted(false);
    jButton5.setPreferredSize(new Dimension(200, 42));

    gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
    gbc.weightx = 0.0;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    gbc.insets = new Insets(30, 0, 10, 0);
    jPanel1.add(jButton5, gbc);

    // Center card inside overlay panel
    overlayPanel.add(jPanel1);
}
    
    private void setupEditableFields() {
        txtName = createProfileTextField();
        txtIdNumber = createProfileTextField();
        txtDepartment = createProfileTextField();
        txtYear = createProfileTextField();

        setupRowPanel(jPanel2, txtName, jButton1);
        setupRowPanel(jPanel3, txtIdNumber, jButton2);
        setupRowPanel(jPanel4, txtDepartment, jButton3);
        setupRowPanel(jPanel5, txtYear, jButton4);
    }
    
    private void setupRowPanel(JPanel panel, JTextField field, JButton editBtn) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.add(field, BorderLayout.CENTER);
        
        editBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        editBtn.setFocusPainted(false);
        editBtn.setPreferredSize(new Dimension(65, 35));
        panel.add(editBtn, BorderLayout.EAST);
    }
    
    private JTextField createProfileTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tf.setEditable(false);
        tf.setBackground(Color.WHITE);
        tf.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return tf;
    }
    
    private void setupListeners() {
        jButton1.addActionListener(e -> toggleEdit(txtName));
        jButton2.addActionListener(e -> toggleEdit(txtIdNumber));
        jButton3.addActionListener(e -> toggleEdit(txtDepartment));
        jButton4.addActionListener(e -> toggleEdit(txtYear));

        jButton5.addActionListener(e -> saveUserProfile());

        // Close overlay and return to parent window without page reload
        jButton6.addActionListener(e -> this.dispose());
}
    
    private void handleSwitchAccess() {
        if (!UserSession.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "You must be logged in to switch access.", "Access Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Query user role and admin approval status
        String sql = "SELECT role, is_admin_approved FROM tbl_users WHERE user_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, UserSession.getUserId());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    boolean isApproved = false;

                    try {
                        isApproved = rs.getBoolean("is_admin_approved");
                    } catch (Exception ex) {
                        // Fallback check if approval column isn't boolean
                        if ("Admin".equalsIgnoreCase(role)) {
                            isApproved = true;
                        }
                    }

                    // Approved Admin Access Check
                    if ("Admin".equalsIgnoreCase(role) || isApproved) {
                        JLabel lblGranted = new JLabel("Access granted! Switching to Administration Dashboard...");
                        lblGranted.setFont(new Font("Tahoma", Font.BOLD, 18));
                        JOptionPane.showMessageDialog(this, lblGranted, "Admin Access Approved", JOptionPane.INFORMATION_MESSAGE);
                        
                        this.dispose(); // Close current profile dialog
                        
                        // Example: Open your Admin Frame here
                        // new phinma_coc_gsd_ticketing_system.adminUI.adminDashboard().setVisible(true);
                    } else {
                        // Restricted Access Card Popup (Size 18 Font)
                        JPanel cardPanel = new JPanel();
                        cardPanel.setLayout(new javax.swing.BoxLayout(cardPanel, javax.swing.BoxLayout.Y_AXIS));
                        cardPanel.setPreferredSize(new Dimension(500, 160));
                        cardPanel.setBackground(Color.WHITE);
                        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(217, 21, 70), 2),
                            BorderFactory.createEmptyBorder(15, 20, 15, 20)
                        ));

                        JLabel lblTitle = new JLabel("Admin Access Restricted");
                        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblTitle.setForeground(new Color(217, 21, 70));
                        lblTitle.setAlignmentX(LEFT_ALIGNMENT);

                        JLabel lblMsg = new JLabel("<html>Your account has not been approved for Admin privileges.<br>Please contact the Head Administrator to approve your access request.</html>");
                        lblMsg.setFont(new Font("Tahoma", Font.PLAIN, 18));
                        lblMsg.setAlignmentX(LEFT_ALIGNMENT);

                        cardPanel.add(lblTitle);
                        cardPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 12)));
                        cardPanel.add(lblMsg);

                        JOptionPane.showMessageDialog(this, cardPanel, "Access Denied", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database verification error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void toggleEdit(JTextField field) {
        boolean isEditable = !field.isEditable();
        field.setEditable(isEditable);
        if (isEditable) {
            field.requestFocus();
            field.setBackground(new Color(255, 255, 204)); // Highlight editable state
        } else {
            field.setBackground(Color.WHITE);
        }
    }

    private void loadUserProfile() {
    if (!UserSession.isLoggedIn()) {
        txtName.setText("Guest User");
        return;
    }
    
    // Updated 'user_id' to 'id' to match your tbl_users primary key
    String sql = "SELECT full_name, id_number, department, year_level FROM tbl_users WHERE id = ?";

    try (Connection conn = DBConnection.connect();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, UserSession.getUserId());
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                txtName.setText(rs.getString("full_name") != null ? rs.getString("full_name") : UserSession.getFullName());
                txtIdNumber.setText(rs.getString("id_number") != null ? rs.getString("id_number") : "02-****-******");
                txtDepartment.setText(rs.getString("department") != null ? rs.getString("department") : "Department");
                txtYear.setText(rs.getString("year_level") != null ? rs.getString("year_level") : "Your Year Level");
            }
        }
    } catch (Exception ex) {
        txtName.setText(UserSession.getFullName());
        txtIdNumber.setText("02-****-******");
        txtDepartment.setText("Department Name");
        txtYear.setText("Your Year Level");
    }
}
    
    private void saveUserProfile() {
        String name = txtName.getText().trim();
        String idNum = txtIdNumber.getText().trim();
        String dept = txtDepartment.getText().trim();
        String yr = txtYear.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Validation Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE tbl_users SET full_name = ?, id_number = ?, department = ?, year_level = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, idNum);
            pst.setString(3, dept);
            pst.setString(4, yr);
            pst.setInt(5, UserSession.getUserId());

            int updated = pst.executeUpdate();
            if (updated > 0) {
                UserSession.setFullName(name); // Refresh session data
                JOptionPane.showMessageDialog(this, "Profile successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Relock all input fields
                txtName.setEditable(false);
                txtIdNumber.setEditable(false);
                txtDepartment.setEditable(false);
                txtYear.setEditable(false);
                
                txtName.setBackground(Color.WHITE);
                txtIdNumber.setBackground(Color.WHITE);
                txtDepartment.setBackground(Color.WHITE);
                txtYear.setBackground(Color.WHITE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database update error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(222, 252, 232));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/icons8-profile-48.png"))); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Edit");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 271, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Edit");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Edit");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton3)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Edit");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton4))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton4)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jLabel2.setText("Name:");

        jLabel3.setText("ID Number:");

        jLabel4.setText("Year:");

        jLabel5.setText("Department:");

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton5.setText("Update Profile");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/userUI/icons8-exit-20.png"))); // NOI18N
        jButton6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/userUI/icons8-profile-48.png"))); // NOI18N
        jLabel6.setText("PROFILE");
        jLabel6.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(291, 291, 291))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(92, 92, 92)
                .addComponent(jButton5)
                .addGap(136, 136, 136))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new profile().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
