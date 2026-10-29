
package phinma_coc_gsd_ticketing_system.userUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;
import phinma_coc_gsd_ticketing_system.frstframes.login;

public class homepage extends javax.swing.JFrame {
    
    public static class RoundedBadge extends javax.swing.JLabel {
        private java.awt.Color backgroundColor = new java.awt.Color(255, 0, 51);
        private int cornerRadius = 14;

        public RoundedBadge() {
            super();
            initBadge();
        }

        public RoundedBadge(String text) {
            super(text);
            initBadge();
        }

        private void initBadge() {
            setOpaque(false); // Allows custom rounded painting
            setForeground(java.awt.Color.WHITE);
            setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
            setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 12, 4, 12)); // padding ni(Dynamic)
        }

        public void setBadgeColor(java.awt.Color color) {
            this.backgroundColor = color;
            repaint();
        }

        public java.awt.Color getBadgeColor() {
            return this.backgroundColor;
        }
        
@Override
        protected void paintComponent(java.awt.Graphics g) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public homepage() {
        initComponents();
        
        setupCardLayouts();
        standardizeBadges();
    
        setupFullScreenWrapper();
        
        setActiveSidebarButton(jButton1);
        addGoldHoverEffect(jButton2);
        addGoldHoverEffect(jButton3);
        addGoldHoverEffect(jButton4);
        addGoldHoverEffect(jButton5);
        
        setupWindowStateListener();
        displayUserInfo();
        loadDashboardCounts();
        loadRecentTickets();
    }
    
    private java.awt.Image bgImage;

    private void setupFullScreenWrapper() {
    // 1. PINPOINT FIX: Set explicit size and center on screen FIRST for windowed/restored mode
    this.setSize(1310, 780);
    this.setPreferredSize(new java.awt.Dimension(1310, 780));
    this.setMinimumSize(new java.awt.Dimension(1310, 780));
    this.setLocationRelativeTo(null); // Centers window on screen when minimized/restored

    // 2. Maximize window to full-screen
    this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    
    // 3. Load background image
    try {
        java.net.URL imgUrl = getClass().getResource("/phinma_coc_gsd_ticketing_system/hand-drawn-school-icons-seamless-pattern-isolated-in-green-board-for-education-background-wallpaper-vector.jpg");
        if (imgUrl != null) {
            bgImage = new javax.swing.ImageIcon(imgUrl).getImage();
        }
    } catch (Exception e) {
        System.err.println("Error loading background image: " + e.getMessage());
    }

    // 4. Create full-screen background panel that paints the stretched JPG
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

    // 5. Lock main dashboard panel size at 1310x780
    jPanel1.setPreferredSize(new java.awt.Dimension(1310, 780));
    jPanel1.setMinimumSize(new java.awt.Dimension(1310, 780));
    jPanel1.setMaximumSize(new java.awt.Dimension(1310, 780));

    // 6. Wrap jPanel1 inside outerBackgroundPanel (GridBagLayout keeps it centered in full screen)
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

     private void setupWindowStateListener() {
        this.addWindowStateListener(e -> {
            revalidate();
            repaint();
        });
    }

        private void displayUserInfo() {
        if (!UserSession.isLoggedIn()) {
            jLabel4.setText("Hi, Guest!");
            return;
        }

        Connection conn = DBConnection.connect();
        if (conn == null) {
            // Fallback to session if database connection fails
            jLabel4.setText("Hi, " + UserSession.getFullName() + "!");
            return;
        }
        
        String sql = "SELECT first_name FROM tbl_users WHERE user_id = ?";

         try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, UserSession.getUserId());

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    if (firstName != null && !firstName.trim().isEmpty()) {
                        jLabel4.setText("Hi, " + firstName + "!");
                    } else {
                        jLabel4.setText("Hi, " + UserSession.getFullName() + "!");
                    }
                } else {
                    jLabel4.setText("Hi, " + UserSession.getFullName() + "!");
                }
            }
            
        } catch (Exception e) {
            // Fallback if column 'first_name' error occurs
            String fullName = UserSession.getFullName();
            String firstName = (fullName != null && !fullName.trim().isEmpty()) ? fullName.split(" ")[0] : "User";
            jLabel4.setText("Hi, " + firstName + "!");
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

        public void loadDashboardCounts() {
    Connection conn = DBConnection.connect();
    if (conn == null) {
        return;
    }

    int pendingCount = 0;
    int assignedCount = 0;
    int inProgressCount = 0;
    int resolvedCount = 0;

    if (!UserSession.isLoggedIn()) {
        jLabel2.setText("0");
        jLabel10.setText("0");
        jLabel9.setText("0");
        jLabel11.setText("0");
        return;
    }

    String sql = "SELECT status, COUNT(*) AS total FROM tbl_tickets WHERE username = ? GROUP BY status";

    try (PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setString(1, UserSession.getFullName());

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("total");

                if ("Pending".equalsIgnoreCase(status)) {
                    pendingCount = count;
                } else if ("Assigned".equalsIgnoreCase(status)) {
                    assignedCount = count;
                } else if ("In Progress".equalsIgnoreCase(status)) {
                    inProgressCount = count;
                } else if ("Resolved".equalsIgnoreCase(status)) {
                    resolvedCount = count;
                }
            }
        }

        jLabel2.setText(String.valueOf(pendingCount));     // Pending
        jLabel10.setText(String.valueOf(assignedCount));   // Assigned
        jLabel9.setText(String.valueOf(inProgressCount));  // In Progress
        jLabel11.setText(String.valueOf(resolvedCount));  // Resolved

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to load dashboard statistics: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
        
        public void loadRecentTickets() {
    Connection conn = DBConnection.connect();
    if (conn == null) {
        return;
    }

    // Hide all ticket panels initially
    jPanel4.setVisible(false);
    jPanel5.setVisible(false);
    jPanel6.setVisible(false);
    jPanel7.setVisible(false);
    jPanel8.setVisible(false);

    if (!UserSession.isLoggedIn()) {
        return;
    }

    // Query recent tickets for the logged-in user only
    String sql = "SELECT ticket_id, equipment, room, building, priority, status, created_at " 
            + "FROM tbl_tickets " 
            + "WHERE username = ? " 
            + "ORDER BY ticket_id DESC " 
            + "LIMIT 5";

    try (PreparedStatement pst = conn.prepareStatement(sql)) {
        // Set user_id parameter before executing the query
        pst.setString(1, UserSession.getFullName());

        try (ResultSet rs = pst.executeQuery()) {
            int index = 0;
            while (rs.next()) {
                index++;
                
                int ticketId = rs.getInt("ticket_id");
                String equipment = rs.getString("equipment");
                String room = rs.getString("room");
                String building = rs.getString("building");
                String priority = rs.getString("priority");
                String status = rs.getString("status");
                java.sql.Timestamp timestamp = rs.getTimestamp("created_at");
                String timeStr = "N/A";

                if (timestamp != null) {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm a");
                    timeStr = sdf.format(timestamp);
                }

                String formattedId = String.format("#%04d", ticketId);
                String problemText = (equipment != null ? equipment : "") 
                                   + " (" + (room != null ? room : "") 
                                   + ", " + (building != null ? building : "") + ")";
                String timeText = formattedId + "         Time: " + timeStr;
                    
                if (index == 1) {
                    jPanel4.setVisible(true);
                    jLabel17.setText(problemText);
                    jLabel13.setText(timeText);
                    jLabel25.setText(status);
                    setStatusColor(jLabel25, status);
                    updatePriorityBadge(jLabel26, priority);
                } else if (index == 2) {
                    jPanel5.setVisible(true);
                    jLabel14.setText(problemText);
                    jLabel18.setText(timeText);
                    jLabel22.setText(status);
                    setStatusColor(jLabel22, status);
                    updatePriorityBadge(jLabel27, priority);
                } else if (index == 3) {
                    jPanel6.setVisible(true);
                    jLabel15.setText(problemText);
                    jLabel19.setText(timeText);
                    jLabel23.setText(status);
                    setStatusColor(jLabel23, status);
                    updatePriorityBadge(jLabel28, priority);
                } else if (index == 4) {
                    jPanel7.setVisible(true);
                    jLabel16.setText(problemText);
                    jLabel20.setText(timeText);
                    jLabel24.setText(status);
                    setStatusColor(jLabel24, status);
                    updatePriorityBadge(jLabel29, priority);
                } else if (index == 5) {
                    jPanel8.setVisible(true);
                    jLabel21.setText(problemText);
                    jLabel30.setText(timeText);
                    jLabel31.setText(status);
                    setStatusColor(jLabel31, status);
                    updatePriorityBadge(jLabel32, priority);
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Error loading recent tickets: " + e.getMessage());
    } finally {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jPanel1.revalidate();
        jPanel1.repaint();
    }
}
    
        private void standardizeBadges() {
    javax.swing.JLabel[] badges = {
        jLabel22, jLabel23, jLabel24, jLabel25,
        jLabel26, jLabel27, jLabel28, jLabel29,
        jLabel31, jLabel32
    };
    
    java.awt.Font badgeFont = new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12);
    java.awt.Dimension badgeSize = new java.awt.Dimension(110, 24);

    for (javax.swing.JLabel badge : badges) {
        badge.setFont(badgeFont);
        badge.setPreferredSize(badgeSize);
        badge.setMinimumSize(badgeSize);
        badge.setMaximumSize(badgeSize);
        badge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        badge.setUI(new javax.swing.plaf.basic.BasicLabelUI());
        badge.setOpaque(true);
        }
    }

    private void setupCardLayouts() {
    fixSingleCard(jPanel4, jLabel17, jLabel13, jLabel26, jLabel25);
    fixSingleCard(jPanel5, jLabel14, jLabel18, jLabel27, jLabel22);
    fixSingleCard(jPanel6, jLabel15, jLabel19, jLabel28, jLabel23);
    fixSingleCard(jPanel7, jLabel16, jLabel20, jLabel29, jLabel24);
    fixSingleCard(jPanel8, jLabel21, jLabel30, jLabel32, jLabel31);

    if (jPanel1.getLayout() instanceof javax.swing.GroupLayout) {
        ((javax.swing.GroupLayout) jPanel1.getLayout()).setHonorsVisibility(false);
    }
}

    private void fixSingleCard(javax.swing.JPanel panel, javax.swing.JLabel lblTitle, javax.swing.JLabel lblSub, javax.swing.JLabel lblPriority, javax.swing.JLabel lblStatus) {
    panel.removeAll();
    panel.setLayout(new java.awt.BorderLayout(10, 0));
    panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 102), 1),
        javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)
    ));

    // Left section (Problem Title + Time Subtitle)
    javax.swing.JPanel leftPanel = new javax.swing.JPanel(new java.awt.GridLayout(2, 1, 0, 2));
    leftPanel.setOpaque(false);
    
    lblTitle.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
    lblSub.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13));
    lblSub.setForeground(new java.awt.Color(102, 102, 102));
    
    leftPanel.add(lblTitle);
    leftPanel.add(lblSub);

    // Right section (Fixed-Size Badges)
    javax.swing.JPanel rightPanel = new javax.swing.JPanel(new java.awt.GridBagLayout());
    rightPanel.setOpaque(false);
    
    java.awt.Dimension badgeSize = new java.awt.Dimension(110, 22);
    
    for (javax.swing.JLabel badge : new javax.swing.JLabel[]{lblPriority, lblStatus}) {
        badge.setPreferredSize(badgeSize);
        badge.setMinimumSize(badgeSize);
        badge.setMaximumSize(badgeSize);
        badge.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        badge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }

    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new java.awt.Insets(1, 0, 1, 0);
    gbc.anchor = java.awt.GridBagConstraints.EAST;
    rightPanel.add(lblPriority, gbc);

    gbc.gridy = 1;
    rightPanel.add(lblStatus, gbc);

    panel.add(leftPanel, java.awt.BorderLayout.CENTER);
    panel.add(rightPanel, java.awt.BorderLayout.EAST);
    
    panel.revalidate();
    panel.repaint();
}

    private void updatePriorityBadge(javax.swing.JLabel label, String priority) {
    if ("Emergency".equalsIgnoreCase(priority)) {
        label.setVisible(true);
        label.setText(priority);
        setPriorityColor(label, priority);
    } else {
        label.setVisible(false);
    }
    if (label.getParent() != null) {
        label.getParent().revalidate();
        label.getParent().repaint();
    }
}
    
    private void setStatusColor(javax.swing.JLabel label, String status) {
        java.awt.Color bg = new java.awt.Color(245, 158, 11); // Default Amber/Orange for Pending
    
    if ("Pending".equalsIgnoreCase(status)) {
        bg = new java.awt.Color(245, 158, 11); // Amber / Yellow-Orange (Dashboard Pending border)
    } else if ("Assigned".equalsIgnoreCase(status)) {
        bg = new java.awt.Color(14, 165, 233); // Sky Blue (Dashboard Assigned border)
    } else if ("In Progress".equalsIgnoreCase(status)) {
        bg = new java.awt.Color(59, 130, 246); // Bright Blue (Dashboard In Progress border)
    } else if ("Resolved".equalsIgnoreCase(status)) {
        bg = new java.awt.Color(0, 204, 51);   // Forest Green (Dashboard Resolved border)
    }

    if (label instanceof RoundedBadge) {
        ((RoundedBadge) label).setBadgeColor(bg);
    } else {
        label.setUI(new javax.swing.plaf.basic.BasicLabelUI());
        label.setOpaque(true);
        label.setBackground(bg);
    }
    label.repaint();
}
    private void setPriorityColor(javax.swing.JLabel label, String priority) {
        java.awt.Color bg = new java.awt.Color(210, 20, 50);
        if ("Normal".equalsIgnoreCase(priority) || "Low".equalsIgnoreCase(priority)) {
            bg = new java.awt.Color(180, 20, 70);
        } else if ("Medium".equalsIgnoreCase(priority)) {
            bg = new java.awt.Color(220, 120, 0);
        } else if ("High".equalsIgnoreCase(priority) || "Emergency".equalsIgnoreCase(priority)) {
            bg = new java.awt.Color(210, 20, 50);
        }

        if (label instanceof RoundedBadge) {
            ((RoundedBadge) label).setBadgeColor(bg);
        } else {
            label.setBackground(bg);
        }
    }

        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();

        jScrollPane2.setViewportView(jTextPane2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(222, 252, 232));

        jPanel2.setBackground(new java.awt.Color(0, 102, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GSD TICKETING SYSTEM");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 153, 0));
        jLabel4.setText("Hi, Student!");
        jLabel4.setToolTipText("");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("DASHBOARD");

        jPanel3.setBackground(new java.awt.Color(2, 153, 78));
        jPanel3.setMaximumSize(new java.awt.Dimension(208, 386));
        jPanel3.setMinimumSize(new java.awt.Dimension(208, 386));

        jButton1.setBackground(new java.awt.Color(2, 153, 78));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 204, 51));
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
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Ticket Management");
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
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/userUI/icons8-profile-48.png"))); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addGap(72, 72, 72)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(jButton2)
                .addGap(34, 34, 34)
                .addComponent(jButton3)
                .addGap(34, 34, 34)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(60, 60, 60))
        );

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("12");
        jLabel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 158, 11), 5), javax.swing.BorderFactory.createEmptyBorder(10, 5, 5, 5)), "Pending"));
        jLabel2.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("8");
        jLabel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(59, 130, 246), 5), javax.swing.BorderFactory.createEmptyBorder(10, 5, 5, 5)), "In Progress"));
        jLabel9.setOpaque(true);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("5");
        jLabel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(14, 165, 233), 5), javax.swing.BorderFactory.createEmptyBorder(10, 5, 5, 5)), "Assigned"));
        jLabel10.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("32");
        jLabel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 51), 5), javax.swing.BorderFactory.createEmptyBorder(10, 5, 5, 5)), "Resolved"));
        jLabel11.setOpaque(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("#0041         Time: 12:34PM / 09-23-26");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("PC Burnout (CL5, MS)");

        jLabel25.setBackground(new java.awt.Color(245, 158, 11));
        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Pending");
        jLabel25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel25.setOpaque(true);

        jLabel26.setBackground(new java.awt.Color(217, 21, 70));
        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Emergency");
        jLabel26.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel26.setOpaque(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 563, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel17))
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Leaking Faucet (Ground, PH)");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("#0040         Time: 09:48AM / 09-23-26");

        jLabel22.setBackground(new java.awt.Color(245, 158, 11));
        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Pending");
        jLabel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel22.setOpaque(true);

        jLabel27.setBackground(new java.awt.Color(217, 21, 70));
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Emergency");
        jLabel27.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel27.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(556, 556, 556)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel14))
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Projector Not Working (CITE OFFICE, MS)");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("#0039         Time: 07:03AM / 09-23-26");

        jLabel23.setBackground(new java.awt.Color(59, 130, 246));
        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("In Progress");
        jLabel23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel23.setOpaque(true);

        jLabel28.setBackground(new java.awt.Color(217, 21, 70));
        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Emergency");
        jLabel28.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel28.setOpaque(true);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19))
                .addGap(525, 525, 525)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel15))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("DCHP Comlab Configuration (CL3, MS)");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("#0038         Time: 04:10PM / 09-21-26");

        jLabel24.setBackground(new java.awt.Color(0, 204, 51));
        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Resolved");
        jLabel24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel24.setOpaque(true);

        jLabel29.setBackground(new java.awt.Color(217, 21, 70));
        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Emergency");
        jLabel29.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel29.setOpaque(true);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 529, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel16))
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Your Recent Tickets");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("DCHP Comlab Configuration (CL3, MS)");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("#0038         Time: 04:10PM / 09-21-26");

        jLabel31.setBackground(new java.awt.Color(0, 204, 51));
        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Resolved");
        jLabel31.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel31.setOpaque(true);

        jLabel32.setBackground(new java.awt.Color(217, 21, 70));
        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Emergency");
        jLabel32.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel32.setOpaque(true);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(530, 530, 530)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel32))
                .addGap(0, 0, 0)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jButton4.setEnabled(false);
        new status().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
//GEN-FIRST:event_jButton1ActionPerformed
        loadDashboardCounts(); // Refresh counters on click
        loadRecentTickets();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
//GEN-FIRST:event_jButton2ActionPerformed
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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jButton6.setEnabled(false); // Prevents opening duplicate profile windows
        
        profile prof = new profile();
        prof.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                jButton6.setEnabled(true); // Re-enables button when profile window is closed
            }
        });
        prof.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

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
       } catch (Exception ex) {
            java.util.logging.Logger.getLogger(homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homepage().setVisible(true);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane2;
    // End of variables declaration//GEN-END:variables
}
