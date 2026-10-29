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
import javax.swing.Box;
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
public class archives extends javax.swing.JFrame {

    private JPanel ticketContainer;
    private JPanel workerContainer;
    private JTextField txtSearch;
    private JComboBox<String> cmbFilter;
    private JButton btnClearSearch;
    private boolean isUpdatingPlaceholder = false;
    
    public archives() {
        initComponents();
        
        setupFullScreenWrapper();
        
        setActiveSidebarButton(jButton9);
        addGoldHoverEffect(jButton1);
        addGoldHoverEffect(jButton2);
        addGoldHoverEffect(jButton4);
        addGoldHoverEffect(jButton7);
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
        // Hide obsolete static labels & bottom details panel
        jLabel2.setVisible(false);
        jLabel4.setVisible(false);

        // --- SEARCH & FILTER CONTROLS (Top Right) ---
       jTextField1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        jTextField1.setForeground(new Color(153, 153, 153));

        jButton3.setFont(new Font("Tahoma", Font.BOLD, 12));
        jButton3.setFocusPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"All", "Old to New", "New to Old", "A > Z", "Z > A"}));
        jComboBox1.setFont(new Font("Tahoma", Font.PLAIN, 13));

        // Position Main Archives Title
        jLabel3.setFont(new Font("Tahoma", Font.BOLD, 22));

        // --- LEFT CONTAINER: TICKETS (jPanel4) ---
        jPanel4.removeAll();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.setPreferredSize(new Dimension(510, 560));
        jPanel4.setMinimumSize(new Dimension(510, 560));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 102, 51), 1));

        // Dark Green Header Banner for Tickets
        JPanel ticketHeaderBanner = new JPanel(new BorderLayout());
        ticketHeaderBanner.setBackground(new Color(0, 102, 51));
        ticketHeaderBanner.setPreferredSize(new Dimension(510, 35));
        JLabel lblTicketsTitle = new JLabel("TICKETS", SwingConstants.CENTER);
        lblTicketsTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTicketsTitle.setForeground(new Color(255, 204, 51)); // Yellow / Gold text
        ticketHeaderBanner.add(lblTicketsTitle, BorderLayout.CENTER);

        // Sub-Header Column Labels
        JPanel ticketSubHeader = new JPanel(null);
        ticketSubHeader.setPreferredSize(new Dimension(510, 30));
        ticketSubHeader.setBackground(new Color(245, 245, 245));
        ticketSubHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        String[] tHeaders = {"Ticket Code", "Status", "Date Archived", "Action"};
        int[] tX = {20, 145, 265, 420};
        int[] tW = {110, 110, 140, 70};
        for (int i = 0; i < tHeaders.length; i++) {
            JLabel lbl = new JLabel(tHeaders[i]);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
            lbl.setBounds(tX[i], 5, tW[i], 20);
            ticketSubHeader.add(lbl);
        }

        JPanel ticketTopContainer = new JPanel(new BorderLayout());
        ticketTopContainer.add(ticketHeaderBanner, BorderLayout.NORTH);
        ticketTopContainer.add(ticketSubHeader, BorderLayout.SOUTH);

        ticketContainer = new JPanel();
        ticketContainer.setLayout(new BoxLayout(ticketContainer, BoxLayout.Y_AXIS));
        ticketContainer.setBackground(Color.WHITE);

        JScrollPane ticketScroll = new JScrollPane(ticketContainer);
        ticketScroll.getVerticalScrollBar().setUnitIncrement(16);
        ticketScroll.setBorder(null);
        ticketScroll.setBackground(Color.WHITE);
        ticketScroll.getViewport().setBackground(Color.WHITE);

        jPanel4.setBackground(Color.WHITE);
        jPanel4.add(ticketTopContainer, BorderLayout.NORTH);
        jPanel4.add(ticketScroll, BorderLayout.CENTER);

        // --- RIGHT CONTAINER: WORKERS (jPanel7) ---
        jPanel7.removeAll();
        jPanel7.setLayout(new BorderLayout());
        jPanel7.setPreferredSize(new Dimension(510, 560));
        jPanel7.setMinimumSize(new Dimension(510, 560));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 102, 51), 1));

        // Dark Green Header Banner for Workers
        JPanel workerHeaderBanner = new JPanel(new BorderLayout());
        workerHeaderBanner.setBackground(new Color(0, 102, 51));
        workerHeaderBanner.setPreferredSize(new Dimension(510, 35));
        JLabel lblWorkersTitle = new JLabel("WORKERS", SwingConstants.CENTER);
        lblWorkersTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblWorkersTitle.setForeground(new Color(255, 204, 51)); // Yellow / Gold text
        workerHeaderBanner.add(lblWorkersTitle, BorderLayout.CENTER);

        // Sub-Header Column Labels
        JPanel workerSubHeader = new JPanel(null);
        workerSubHeader.setPreferredSize(new Dimension(510, 30));
        workerSubHeader.setBackground(new Color(245, 245, 245));
        workerSubHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        String[] wHeaders = {"Workers' Code", "Status", "Date Archived", "Action"};
        int[] wX = {20, 145, 265, 420};
        int[] wW = {110, 110, 140, 70};
        for (int i = 0; i < wHeaders.length; i++) {
            JLabel lbl = new JLabel(wHeaders[i]);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
            lbl.setBounds(wX[i], 5, wW[i], 20);
            workerSubHeader.add(lbl);
        }

        JPanel workerTopContainer = new JPanel(new BorderLayout());
        workerTopContainer.add(workerHeaderBanner, BorderLayout.NORTH);
        workerTopContainer.add(workerSubHeader, BorderLayout.SOUTH);

        workerContainer = new JPanel();
        workerContainer.setLayout(new BoxLayout(workerContainer, BoxLayout.Y_AXIS));
        workerContainer.setBackground(Color.WHITE);

        JScrollPane workerScroll = new JScrollPane(workerContainer);
        workerScroll.getVerticalScrollBar().setUnitIncrement(16);
        workerScroll.setBorder(null);
        workerScroll.setBackground(Color.WHITE);
        workerScroll.getViewport().setBackground(Color.WHITE);

        jPanel7.setBackground(Color.WHITE);
        jPanel7.add(workerTopContainer, BorderLayout.NORTH);
        jPanel7.add(workerScroll, BorderLayout.CENTER);

        // --- LISTENERS ---
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if ("Search Code Here...".equals(jTextField1.getText())) {
                    isUpdatingPlaceholder = true;
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK);
                    isUpdatingPlaceholder = false;
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jTextField1.getText().trim().isEmpty()) {
                    // Defer placeholder restore to allow button click ActionEvents to process first
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        if (jTextField1.getText().trim().isEmpty() && !jTextField1.hasFocus()) {
                            isUpdatingPlaceholder = true;
                            jTextField1.setForeground(new Color(153, 153, 153));
                            jTextField1.setText("Search Code Here...");
                            isUpdatingPlaceholder = false;
                        }
                    });
                }
            }
        });

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { 
                if (!isUpdatingPlaceholder) loadArchives(); 
            }
            public void removeUpdate(DocumentEvent e) { 
                if (!isUpdatingPlaceholder) loadArchives(); 
            }
            public void changedUpdate(DocumentEvent e) { 
                if (!isUpdatingPlaceholder) loadArchives(); 
            }
        });

        jComboBox1.addActionListener(e -> loadArchives());
        jButton3.addActionListener(e -> {
            isUpdatingPlaceholder = true;
            jTextField1.setText("Search Code Here...");
            jTextField1.setForeground(new Color(153, 153, 153));
            isUpdatingPlaceholder = false;
            jComboBox1.setSelectedIndex(0);
            loadArchives();
        });

        loadArchives();
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    
    public void loadArchives() {
        String queryText = jTextField1.getText().trim();
        if ("Search Code Here...".equals(queryText)) queryText = "";
        String sortOption = jComboBox1.getSelectedItem() != null ? jComboBox1.getSelectedItem().toString() : "All";

        ticketContainer.removeAll();
        workerContainer.removeAll();

        loadArchivedTickets(queryText, sortOption);
        loadArchivedWorkers(queryText, sortOption);

        ticketContainer.revalidate();
        ticketContainer.repaint();
        workerContainer.revalidate();
        workerContainer.repaint();
    }

    private void loadArchivedTickets(String keyword, String sortOption) {
        String sql = "SELECT archive_id, ticket_id, status, DATE_FORMAT(archived_at, '%Y-%m-%d') as arch_date FROM tbl_archived_tickets WHERE 1=1";
        
        // If searching a Worker code (starts with W-), do not wipe out tickets
        boolean isWorkerSearch = keyword.toUpperCase().startsWith("W-");
        
        if (!keyword.isEmpty() && !isWorkerSearch) {
            sql += " AND (CAST(ticket_id AS CHAR) LIKE ? OR LPAD(ticket_id, 4, '0') LIKE ? OR CONCAT('T-', LPAD(ticket_id, 4, '0')) LIKE ? OR LOWER(status) LIKE LOWER(?))";
        }

        switch (sortOption) {
            case "Old to New":
                sql += " ORDER BY archive_id ASC";
                break;
            case "A > Z":
                sql += " ORDER BY ticket_id ASC";
                break;
            case "Z > A":
                sql += " ORDER BY ticket_id DESC";
                break;
            case "New to Old":
            case "All":
            default:
                sql += " ORDER BY archive_id DESC";
                break;
        }

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (!keyword.isEmpty() && !isWorkerSearch) {
                String cleanDigits = keyword.replaceAll("[^0-9]", "");
                String digitPat = "%" + (cleanDigits.isEmpty() ? keyword : cleanDigits) + "%";
                String rawPat = "%" + keyword + "%";

                pstmt.setString(1, digitPat); // CAST(ticket_id AS CHAR)
                pstmt.setString(2, digitPat); // LPAD(ticket_id, 4, '0')
                pstmt.setString(3, rawPat);   // CONCAT('T-', LPAD(ticket_id, 4, '0'))
                pstmt.setString(4, rawPat);   // status
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int archiveId = rs.getInt("archive_id");
                int ticketId = rs.getInt("ticket_id");
                String code = String.format("T-%04d", ticketId);
                String status = rs.getString("status");
                String date = rs.getString("arch_date");
                ticketContainer.add(createTicketRowCard(archiveId, code, status, date != null ? date : "N/A"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadArchivedWorkers(String keyword, String sortOption) {
        String sql = "SELECT worker_code, status, DATE_FORMAT(removed_at, '%Y-%m-%d') as arch_date FROM tbl_archived_workers WHERE 1=1";
        
        // If searching a Ticket code (starts with T-), do not wipe out workers
        boolean isTicketSearch = keyword.toUpperCase().startsWith("T-");

        if (!keyword.isEmpty() && !isTicketSearch) {
            sql += " AND (LOWER(worker_code) LIKE LOWER(?) OR LOWER(status) LIKE LOWER(?))";
        }

        switch (sortOption) {
            case "Old to New":
                sql += " ORDER BY archive_id ASC";
                break;
            case "A > Z":
                sql += " ORDER BY worker_code ASC";
                break;
            case "Z > A":
                sql += " ORDER BY worker_code DESC";
                break;
            case "New to Old":
            case "All":
            default:
                sql += " ORDER BY archive_id DESC";
                break;
        }

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (!keyword.isEmpty() && !isTicketSearch) {
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String code = rs.getString("worker_code");
                String status = rs.getString("status");
                String date = rs.getString("arch_date");
                workerContainer.add(createWorkerRowCard(code, status != null ? status : "Inactive", date != null ? date : "N/A"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private JPanel createTicketRowCard(int archiveId, String code, String status, String date) {
        JPanel row = new JPanel(null);
        row.setBackground(Color.WHITE);
        row.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        row.setPreferredSize(new Dimension(490, 40));
        row.setMaximumSize(new Dimension(490, 40));
        row.setMinimumSize(new Dimension(490, 40));

        JLabel lblCode = new JLabel(code);
        lblCode.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCode.setBounds(20, 10, 110, 20); // 20px gap from left border

        JLabel lblStatus = new JLabel(status);
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblStatus.setBounds(145, 10, 110, 20);

        JLabel lblDate = new JLabel(date);
        lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDate.setForeground(Color.GRAY);
        lblDate.setBounds(265, 10, 140, 20);

        JButton btnView = new JButton("View");
        btnView.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnView.setBounds(415, 8, 60, 24);
        btnView.setFocusPainted(false);
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.addActionListener(e -> showArchivedTicketDialog(archiveId, code));

        row.add(lblCode);
        row.add(lblStatus);
        row.add(lblDate);
        row.add(btnView);
        return row;
    }

    private JPanel createWorkerRowCard(String code, String status, String date) {
        JPanel row = new JPanel(null);
        row.setBackground(Color.WHITE);
        row.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        row.setPreferredSize(new Dimension(490, 40));
        row.setMaximumSize(new Dimension(490, 40));
        row.setMinimumSize(new Dimension(490, 40));

        JLabel lblCode = new JLabel(code);
        lblCode.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCode.setBounds(20, 10, 110, 20); // 20px gap from left border

        JLabel lblStatus = new JLabel(status);
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblStatus.setBounds(145, 10, 110, 20);

        JLabel lblDate = new JLabel(date);
        lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDate.setForeground(Color.GRAY);
        lblDate.setBounds(265, 10, 140, 20);

        JButton btnView = new JButton("View");
        btnView.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnView.setBounds(415, 8, 60, 24);
        btnView.setFocusPainted(false);
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.addActionListener(e -> showArchivedWorkerDialog(code, status, date));

        row.add(lblCode);
        row.add(lblStatus);
        row.add(lblDate);
        row.add(btnView);
        return row;
    }
    
    private void showArchivedTicketDialog(int archiveId, String code) {
        JDialog dialog = new JDialog(this, "Archived Ticket Details", true);
        dialog.setSize(500, 430);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(Color.WHITE);

        // --- TOP HEADER BANNER ---
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(new Color(0, 102, 51));
        headerPanel.setBounds(0, 0, 500, 45);

        JLabel lblHeaderTitle = new JLabel("Archived Ticket Details");
        lblHeaderTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblHeaderTitle.setForeground(Color.WHITE);
        lblHeaderTitle.setBounds(20, 10, 260, 24);
        headerPanel.add(lblHeaderTitle);

        JLabel lblHeaderTag = new JLabel("[" + code + "]", SwingConstants.RIGHT);
        lblHeaderTag.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblHeaderTag.setForeground(new Color(255, 204, 51));
        lblHeaderTag.setBounds(340, 10, 135, 24);
        headerPanel.add(lblHeaderTag);

        dialog.add(headerPanel);

        // --- MAIN CONTENT DETAILS ---
        StringBuilder detailsHtml = new StringBuilder();
        String sql = "SELECT * FROM tbl_archived_tickets WHERE archive_id = ?";
        
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, archiveId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String reportedBy = rs.getString("username");
                String location = rs.getString("room") + ", " + rs.getString("building");
                String equipment = rs.getString("equipment");
                String priority = rs.getString("priority");
                String description = rs.getString("description");
                String status = rs.getString("status");
                
                String archivedBy = "Admin";
                try {
                    String val = rs.getString("archived_by");
                    if (val != null && !val.trim().isEmpty()) archivedBy = val;
                } catch (SQLException ignore) {
                    if (reportedBy != null && !reportedBy.trim().isEmpty()) archivedBy = reportedBy;
                }

                detailsHtml.append("<html><body style='font-family:Tahoma; font-size:12pt; line-height:1.4;'>")
                           .append("<b>Reported By:</b> ").append(reportedBy != null ? reportedBy : "N/A").append("<br><br>")
                           .append("<b>Location:</b> ").append(location).append("<br><br>")
                           .append("<b>Equipment:</b> ").append(equipment != null ? equipment : "N/A").append("<br><br>")
                           .append("<b>Priority:</b> ").append(priority != null ? priority : "Low").append("<br><br>")
                           .append("<b>Description:</b> ").append(description != null ? description : "None").append("<br><br>")
                           .append("<b>Status:</b> ").append(status != null ? status : "Archived").append("<br><br>")
                           .append("<b>Archived By:</b> <font color='#006633'><b>").append(archivedBy).append("</b></font>")
                           .append("</body></html>");
            }
        } catch (SQLException ex) {
            detailsHtml.append("<html><body style='font-family:Tahoma; color:red;'>Error retrieving details.</body></html>");
        }

        JLabel lblDetails = new JLabel(detailsHtml.toString());
        lblDetails.setVerticalAlignment(SwingConstants.TOP);
        lblDetails.setBounds(25, 58, 450, 245);
        dialog.add(lblDetails);

        // --- FOOTER ACTION BAR ---
        JPanel footerPanel = new JPanel(null);
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBounds(0, 310, 500, 65);
        footerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnClose.setBounds(365, 12, 110, 34);
        btnClose.setFocusPainted(false);
        btnClose.setBackground(Color.WHITE);
        btnClose.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> dialog.dispose());

        footerPanel.add(btnClose);
        dialog.add(footerPanel);

        dialog.setVisible(true);
    }

    private void showArchivedWorkerDialog(String workerCode, String status, String date) {
        JDialog dialog = new JDialog(this, "Archived Worker Details", true);
        dialog.setSize(500, 430);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(Color.WHITE);

        // --- TOP HEADER BANNER ---
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(new Color(0, 102, 51));
        headerPanel.setBounds(0, 0, 500, 45);

        JLabel lblHeaderTitle = new JLabel("Archived Worker Details");
        lblHeaderTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblHeaderTitle.setForeground(Color.WHITE);
        lblHeaderTitle.setBounds(20, 10, 260, 24);
        headerPanel.add(lblHeaderTitle);

        JLabel lblHeaderTag = new JLabel("[" + workerCode + "]", SwingConstants.RIGHT);
        lblHeaderTag.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblHeaderTag.setForeground(new Color(255, 204, 51));
        lblHeaderTag.setBounds(340, 10, 135, 24);
        headerPanel.add(lblHeaderTag);

        dialog.add(headerPanel);

        // --- RETRIEVE WORKER & ARCHIVED BY DATA ---
        String workerName = "N/A";
        String removedBy = "Admin";
        String reason = "N/A";

        String sql = "SELECT * FROM tbl_archived_workers WHERE worker_code = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, workerCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                try { if (rs.getString("worker_name") != null) workerName = rs.getString("worker_name"); } catch (SQLException ignore) {}
                try { if (rs.getString("removed_by") != null) removedBy = rs.getString("removed_by"); } catch (SQLException ignore) {}
                try { if (rs.getString("reason_for_removal") != null) reason = rs.getString("reason_for_removal"); } catch (SQLException ignore) {}
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JLabel lblDetails = new JLabel("<html><body style='font-family:Tahoma; font-size:12pt; line-height:1.4;'>"
                + "<b>Worker Code:</b> " + workerCode + "<br><br>"
                + "<b>Worker Name:</b> " + workerName + "<br><br>"
                + "<b>Status:</b> " + status + "<br><br>"
                + "<b>Date Archived:</b> " + date + "<br><br>"
                + "<b>Reason for Removal:</b> " + reason + "<br><br>"
                + "<b>Archived By:</b> <font color='#006633'><b>" + removedBy + "</b></font>"
                + "</body></html>");
        lblDetails.setVerticalAlignment(SwingConstants.TOP);
        lblDetails.setBounds(25, 58, 450, 245);
        dialog.add(lblDetails);

        // --- FOOTER ACTION BAR ---
        JPanel footerPanel = new JPanel(null);
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBounds(0, 310, 500, 65);
        footerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnCancel.setBounds(240, 12, 105, 34);
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());

        JButton btnUnarchive = new JButton("Unarchive");
        btnUnarchive.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUnarchive.setBackground(new Color(0, 102, 51));
        btnUnarchive.setForeground(Color.WHITE);
        btnUnarchive.setBounds(355, 12, 120, 34);
        btnUnarchive.setFocusPainted(false);
        btnUnarchive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUnarchive.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to unarchive worker " + workerCode + "?", "Confirm Unarchive", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                unarchiveWorker(workerCode);
                dialog.dispose();
                loadArchives();
            }
        });

        footerPanel.add(btnCancel);
        footerPanel.add(btnUnarchive);
        dialog.add(footerPanel);

        dialog.setVisible(true);
    }

    // Unarchive Worker Helper Method
    private void unarchiveWorker(String workerCode) {
        String restoreSql = "INSERT INTO tbl_workers (worker_code, status) SELECT worker_code, 'Active' FROM tbl_archived_workers WHERE worker_code = ?";
        String deleteSql = "DELETE FROM tbl_archived_workers WHERE worker_code = ?";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmtRestore = conn.prepareStatement(restoreSql);
                 PreparedStatement pstmtDelete = conn.prepareStatement(deleteSql)) {

                pstmtRestore.setString(1, workerCode);
                pstmtRestore.executeUpdate();

                pstmtDelete.setString(1, workerCode);
                pstmtDelete.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(this, "Worker " + workerCode + " successfully unarchived!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Failed to unarchive worker: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        jLabel12 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jButton11 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jButton12 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

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
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 204, 51));
        jButton9.setText("Archives");
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(2, 153, 78));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("User Administration");
        jButton7.setContentAreaFilled(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(60, 60, 60))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Ticket Code");
        jLabel10.setToolTipText("");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Status");
        jLabel11.setToolTipText("");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Date Archived");
        jLabel12.setToolTipText("");

        jButton11.setText("View");
        jButton11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addComponent(jSeparator5)
            .addComponent(jSeparator6)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(274, Short.MAX_VALUE))
        );

        jLabel2.setBackground(new java.awt.Color(0, 102, 51));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 204, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TICKETS");
        jLabel2.setOpaque(true);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Workers' Code");
        jLabel18.setToolTipText("");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Status");
        jLabel19.setToolTipText("");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Date Archived");
        jLabel20.setToolTipText("");

        jButton12.setText("View");
        jButton12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator7)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addContainerGap(132, Short.MAX_VALUE))
            .addComponent(jSeparator8)
            .addComponent(jSeparator9)
            .addComponent(jSeparator10)
            .addComponent(jSeparator11)
            .addComponent(jSeparator12)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("ARCHIVES");

        jLabel4.setBackground(new java.awt.Color(0, 102, 51));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 204, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("WORKERS");
        jLabel4.setOpaque(true);

        jTextField1.setText("Search Code Here...");

        jButton3.setText("Clear Search");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Old to New", "New to Old", "A > Z", "Z > A" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        }    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jButton6.setEnabled(false);
        new update().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jButton7.setEnabled(false);
        new userAdmin().setVisible(true);
        this.dispose();
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
            java.util.logging.Logger.getLogger(archives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(archives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(archives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(archives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new archives().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
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
