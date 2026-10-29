/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.adminUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;
import phinma_coc_gsd_ticketing_system.frstframes.login;
import phinma_coc_gsd_ticketing_system.userUI.profile;


/**
 *
 * @author ACER
 */
public class update extends javax.swing.JFrame {

    private ButtonGroup statusActionGroup;
    private String currentLoadedTicketCode = null;

    
    public update() {
        initComponents();
        
        setupFullScreenWrapper();
        
        setActiveSidebarButton(jButton6);
        addGoldHoverEffect(jButton1);
        addGoldHoverEffect(jButton2);
        addGoldHoverEffect(jButton4);
        addGoldHoverEffect(jButton9);
        addGoldHoverEffect(jButton10);
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
    // Auto-scale font to 18pt for long text ("Ticket Confirmation") to prevent clipping
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
        statusActionGroup = new ButtonGroup();
        statusActionGroup.add(jRadioButton3);
        statusActionGroup.add(jRadioButton1);
        statusActionGroup.add(jRadioButton2);
        
        jRadioButton3.setSelected(true);
        updateRemarksState();
        
        java.awt.event.ActionListener radioListener = e -> updateRemarksState();
        jRadioButton1.addActionListener(radioListener);
        jRadioButton2.addActionListener(radioListener);
        jRadioButton3.addActionListener(radioListener);

        //  Search Placeholder Listeners
        jTextField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("Enter Ticket Code...".equalsIgnoreCase(jTextField1.getText().trim())) {
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField1.getText().trim().isEmpty()) {
                    jTextField1.setForeground(new Color(153, 153, 153));
                    jTextField1.setText("Enter Ticket Code...");
                }
            }
        });

        // Search Action
        jButton8.addActionListener(e -> performTicketSearch());
        jTextField1.addActionListener(e -> performTicketSearch());

        // Save & Complete Action
        jButton3.addActionListener(e -> performTicketCompletion());

        // Reset UI to default blank state
        resetTicketForm();
    }
    
    private void updateRemarksState() {
        // Remarks field is ONLY enabled if "Mark as Resolved" (jRadioButton1) is selected
        boolean isResolvedSelected = jRadioButton1.isSelected();
        jTextArea1.setEnabled(isResolvedSelected);
        if (!isResolvedSelected) {
            jTextArea1.setText(""); // Clear remarks when switching away from Resolved
        }
    }
    
    private void performTicketSearch() {
        String inputCode = jTextField1.getText().trim();
        if (inputCode.isEmpty() || "Enter Ticket Code...".equalsIgnoreCase(inputCode)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 4-digit Ticket Code to search.", "Search Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String numericOnly = inputCode.replaceAll("[^0-9]", "");
        String formattedCode = numericOnly.isEmpty() ? inputCode : String.format("%04d", Integer.parseInt(numericOnly));

        boolean foundInDB = searchTicketInDB(inputCode, formattedCode);

        if (!foundInDB) {
            searchTicketFallbackDemo(inputCode, formattedCode);
        }
    }
    
    private boolean searchTicketInDB(String rawCode, String formattedCode) {
        
        String sql = "SELECT * FROM tbl_tickets WHERE ticket_id = ? OR CAST(ticket_id AS CHAR) LIKE ?";
        
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int targetId = -1;
            try {
                targetId = Integer.parseInt(formattedCode);
            } catch (Exception ignored) {}

            pstmt.setInt(1, targetId);
            pstmt.setString(2, "%" + formattedCode + "%");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ticket_id");
                String code = String.format("%04d", id);

                String status = rs.getString("status") != null ? rs.getString("status") : "Pending";
                String submittedBy = rs.getString("username") != null ? rs.getString("username") : "N/A";
                String userId = "N/A";
                try { userId = rs.getString("user_id"); } catch (Exception ignored) {}
                String dept = "N/A";
                try { dept = rs.getString("department"); } catch (Exception ignored) {}
                String year = "N/A";
                try { year = rs.getString("year_level"); } catch (Exception ignored) {}

                String equip = rs.getString("equipment");
                String others = rs.getString("others_specified");
                String problem = ("Others".equalsIgnoreCase(equip) && others != null && !others.isEmpty()) ? others : (equip != null ? equip : "General Repair");

                String rm = rs.getString("room");
                String bldg = rs.getString("building");
                String location = (rm != null ? rm : "") + (bldg != null && !bldg.isEmpty() ? ", " + bldg : "");

                String priority = rs.getString("priority") != null ? rs.getString("priority") : "Normal";
                String assignedTo = rs.getString("assigned_worker") != null ? rs.getString("assigned_worker") : "Unassigned";
                if (assignedTo.trim().isEmpty()) assignedTo = "Unassigned";

                String subDate = "N/A";
                String assignDate = "N/A";
                try {
                    if (rs.getTimestamp("created_at") != null) {
                        subDate = new SimpleDateFormat("MM-dd-yyyy / hh:mm a").format(rs.getTimestamp("created_at"));
                        assignDate = subDate;
                    }
                } catch (Exception ignored) {}

                handleTicketSearchResult(code, status, submittedBy, userId, dept, year, problem, location, priority, assignedTo, subDate, assignDate);
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    private void searchTicketFallbackDemo(String rawCode, String formattedCode) {
        if ("0001".contains(formattedCode) || "1".equalsIgnoreCase(formattedCode)) {
            handleTicketSearchResult("0001", "In Progress", "Juan Cruz", "21-0982-11", "College of Information Technology", "3rd Year", 
                                     "Busted LED Light Fixture & Wiring", "CIT Building - Room 302", "High", 
                                     "John Maintenance (Electrician)", "10-10-2026 / 08:30 AM", "10-11-2026 / 09:15 AM");
        } else if ("0002".contains(formattedCode) || "2".equalsIgnoreCase(formattedCode)) {
            handleTicketSearchResult("0002", "Pending", "Maria Santos", "22-1104-33", "College of Education", "2nd Year", 
                                     "Air Conditioner Not Cooling", "Educ Building - Rm 105", "Medium", 
                                     "Unassigned", "10-12-2026 / 07:45 AM", "N/A");
        } else if ("0003".contains(formattedCode) || "3".equalsIgnoreCase(formattedCode)) {
            handleTicketSearchResult("0003", "Resolved", "Alex Smith", "20-0012-99", "Engineering Dept", "4th Year", 
                                     "Broken Door Handle", "Main Hallway", "Low", 
                                     "Alex Smith (GSD Staff)", "10-08-2026 / 01:00 PM", "10-08-2026 / 02:30 PM");
        } else if ("0004".contains(formattedCode) || "4".equalsIgnoreCase(formattedCode)) {
            handleTicketSearchResult("0004", "Assigned", "Juan Cruz", "21-0982-11", "Senior High School", "Grade 12", 
                                     "AC (Sensitive outlet, luag.)", "CL2, SHS", "Mid", 
                                     "Mark Magdadaro", "10-12-2026 / 08:00 AM", "10-12-2026 / 08:30 AM");
        } else if ("0005".contains(formattedCode) || "5".equalsIgnoreCase(formattedCode)) {
            handleTicketSearchResult("0005", "Pending", "John Smith", "22-5511-22", "College of Information Technology", "1st Year", 
                                     "Computer (Student PC not working)", "MW 504, MW", "Emergency", 
                                     "Unassigned", "10-12-2026 / 08:15 AM", "N/A");
        } else if ("0006".contains(formattedCode) || "6".equalsIgnoreCase(formattedCode)) {
            handleTicketSearchResult("0006", "Pending", "Juan Cruz", "21-0982-11", "College of Information Technology", "3rd Year", 
                                     "Faucet (Faucet leaking)", "MW 304, MW", "Low", 
                                     "Unassigned", "10-12-2026 / 08:32 AM", "N/A");
        } else {
            JOptionPane.showMessageDialog(this, "Ticket Code '" + rawCode + "' not found. Please verify the code and try again.", "Ticket Not Found", JOptionPane.ERROR_MESSAGE);
            resetTicketForm();
        }
    }
    
    private void handleTicketSearchResult(String code, String status, String submittedBy, String userId, String dept, 
                                          String year, String problem, String location, String priority, 
                                          String assignedTo, String subDate, String assignDate) {
        
        // --- GUARDRAIL 1: Reject Unassigned Pending Tickets ---
        if ("Unassigned".equalsIgnoreCase(assignedTo) || ("Pending".equalsIgnoreCase(status) && "Unassigned".equalsIgnoreCase(assignedTo))) {
            JOptionPane.showMessageDialog(
                this, 
                "Ticket #" + code + " has NOT been assigned to a worker yet.\n\nPlease assign a worker in [View Tickets] first before updating.", 
                "Worker Assignment Required", 
                JOptionPane.WARNING_MESSAGE
            );
            resetTicketForm();
            return;
        }

        // --- GUARDRAIL 2: Reject Already Resolved Tickets ---
        if ("Resolved".equalsIgnoreCase(status) || "Closed".equalsIgnoreCase(status) || "Completed".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(
                this, 
                "Ticket #" + code + " is already RESOLVED and closed.", 
                "Ticket Already Closed", 
                JOptionPane.INFORMATION_MESSAGE
            );
            resetTicketForm();
            return;
        }

        // --- VALID ASSIGNED / IN PROGRESS TICKET: Load Data into UI ---
        this.currentLoadedTicketCode = code;

        // Left Card Data
        jLabel3.setText("Ticket # " + code);
        jLabel10.setText("Submission Date: " + subDate);
        jLabel9.setText("Submitted By: " + submittedBy);
        jLabel13.setText("User ID / School ID: " + userId);
        jLabel14.setText("Department: " + dept);
        jLabel15.setText("Year Level: " + year);

        jLabel4.setText("Ticket Problem: " + problem);
        jLabel5.setText("Location: " + location);
        jLabel6.setText("Priority Level: " + priority);
        jLabel7.setText("Status: " + status);
        jLabel8.setText("Assigned To: " + assignedTo);
        jLabel11.setText("Date Assigned: " + assignDate);

        // Right Card Data
        jLabel23.setText(assignDate);
        String currentTimeStamp = new SimpleDateFormat("MM-dd-yyyy / hh:mm a").format(new Date());
        jLabel21.setText(currentTimeStamp);

        // Enable radio options and action button
        jRadioButton1.setEnabled(true);
        jRadioButton2.setEnabled(true);
        jRadioButton3.setEnabled(true);
        jButton3.setEnabled(true);

        // Select "Mark as In Progress" by default upon loading an assigned ticket
        if ("In Progress".equalsIgnoreCase(status)) {
            jRadioButton3.setSelected(true);
        } else {
            jRadioButton3.setSelected(true);
        }

        // Apply remarks field enabled/disabled state based on current selection
        updateRemarksState();

        JOptionPane.showMessageDialog(this, "Ticket #" + code + " loaded successfully!", "Ticket Found", JOptionPane.INFORMATION_MESSAGE);
    }

    private void performTicketCompletion() {
        if (currentLoadedTicketCode == null) {
            JOptionPane.showMessageDialog(this, "No active ticket loaded. Please search for a valid ticket first.", "No Ticket Loaded", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (jRadioButton3.isSelected()) {
            // MARK AS IN PROGRESS
            updateDBStatus(currentLoadedTicketCode, "In Progress", "Status updated to In Progress by GSD Admin.");

            JOptionPane.showMessageDialog(
                this, 
                "Ticket #" + currentLoadedTicketCode + " status successfully updated to [In Progress]!", 
                "Status Updated", 
                JOptionPane.INFORMATION_MESSAGE
            );
        } else if (jRadioButton1.isSelected()) {
            // MARK AS RESOLVED
            String remarks = jTextArea1.getText().trim();
            if (remarks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter completion remarks/notes before resolving the ticket.", "Remarks Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            updateDBStatus(currentLoadedTicketCode, "Resolved", remarks);

            JOptionPane.showMessageDialog(
                this, 
                "Ticket #" + currentLoadedTicketCode + " successfully marked as RESOLVED!\n\nStatus: Closed & Logged\nCompletion Time: " + jLabel21.getText(), 
                "Ticket Completed", 
                JOptionPane.INFORMATION_MESSAGE
            );
        } else if (jRadioButton2.isSelected()) {
            // REASSIGN WORKER
            updateDBStatus(currentLoadedTicketCode, "Pending", "Worker reassignment requested by GSD Admin.");

            JOptionPane.showMessageDialog(
                this, 
                "Ticket #" + currentLoadedTicketCode + " status updated to [Reassign / Pending].\n\nYou can now assign a new worker in View Tickets.", 
                "Reassignment Requested", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        resetTicketForm();
    }
    
    private void updateDBStatus(String code, String newStatus, String remarks) {
    int idVal = -1;
    try {
        idVal = Integer.parseInt(code.replaceAll("[^0-9]", ""));
    } catch (Exception ignored) {}

    String sqlGetWorker = "SELECT assigned_worker FROM tbl_tickets WHERE ticket_id = ?";
    String sqlUpdateTicket = "UPDATE tbl_tickets SET status = ?, remarks = ?" 
                           + ("Pending".equalsIgnoreCase(newStatus) ? ", assigned_worker = 'Unassigned'" : "") 
                           + " WHERE ticket_id = ?";
    String sqlUpdateWorker = "UPDATE tbl_workers SET status = 'Active/Unassigned' WHERE worker_name = ?";

    try (Connection conn = DBConnection.connect()) {
        conn.setAutoCommit(false);
        
        // 1. Fetch assigned worker before updating ticket
        String assignedWorker = null;
        try (PreparedStatement pstmtGet = conn.prepareStatement(sqlGetWorker)) {
            pstmtGet.setInt(1, idVal);
            ResultSet rs = pstmtGet.executeQuery();
            if (rs.next()) {
                assignedWorker = rs.getString("assigned_worker");
            }
        }

        // 2. Update ticket status and remarks in tbl_tickets
        try (PreparedStatement pstmtTicket = conn.prepareStatement(sqlUpdateTicket)) {
            pstmtTicket.setString(1, newStatus);
            pstmtTicket.setString(2, remarks);
            pstmtTicket.setInt(3, idVal);
            pstmtTicket.executeUpdate();
        }

        // 3. Automatically unassign/release worker if ticket is Resolved or Reassigned (Pending)
        if (("Resolved".equalsIgnoreCase(newStatus) || "Pending".equalsIgnoreCase(newStatus)) 
                && assignedWorker != null && !assignedWorker.trim().isEmpty() 
                && !"Unassigned".equalsIgnoreCase(assignedWorker.trim())) {
            try (PreparedStatement pstmtWorker = conn.prepareStatement(sqlUpdateWorker)) {
                pstmtWorker.setString(1, assignedWorker);
                pstmtWorker.executeUpdate();
            }
        }

        conn.commit();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    
    private void resetTicketForm() {
        currentLoadedTicketCode = null;

        // Reset Left Card Labels
        jLabel3.setText("Ticket # ----");
        jLabel10.setText("Submission Date: mm-dd-yyyy / 00:00 AM/PM");
        jLabel9.setText("Submitted By: --------------------");
        jLabel13.setText("User ID / School ID: --------------------");
        jLabel14.setText("Department: --------------------");
        jLabel15.setText("Year Level: --------------------");

        jLabel4.setText("Ticket Problem: --------------------");
        jLabel5.setText("Location: --------------------");
        jLabel6.setText("Priority Level: --------------------");
        jLabel7.setText("Status: --------------------");
        jLabel8.setText("Assigned To: --------------------");
        jLabel11.setText("Date Assigned: --------------------");

        // Reset Right Card & Radio Controls
        jLabel23.setText("mm-dd-yyyy / 00:00 AM/PM");
        jLabel21.setText("mm-dd-yyyy / 00:00 AM/PM");
        jTextArea1.setText("");
        jTextArea1.setEnabled(false);
        
        jRadioButton1.setEnabled(false);
        jRadioButton2.setEnabled(false);
        jRadioButton3.setEnabled(false);
        jRadioButton3.setSelected(true);
        
        jButton3.setEnabled(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
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
        jButton10 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();

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
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 204, 51));
        jButton6.setText("Ticket Confirmation");
        jButton6.setContentAreaFilled(false);
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

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/icons8-profile-48.png"))); // NOI18N
        jButton7.setContentAreaFilled(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7)
                .addGap(72, 72, 72)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(jButton2)
                .addGap(34, 34, 34)
                .addComponent(jButton4)
                .addGap(34, 34, 34)
                .addComponent(jButton9)
                .addGap(34, 34, 34)
                .addComponent(jButton10)
                .addGap(34, 34, 34)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(60, 60, 60))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("TICKET CONFIRMATION & UPDATE");

        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setText("Enter Ticket Code...");

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton8.setText("Search");
        jButton8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel6.setBackground(new java.awt.Color(0, 102, 51));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 204, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Ticket # 0001");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Ticket Problem:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Location:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Priority Level:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Status:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Assigned To:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel9.setText("Submitted By:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel10.setText("Submission Date:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Date Assigned:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setText("User ID / School ID:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel14.setText("Department:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel15.setText("Year Level:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(304, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(14, 14, 14)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(94, 94, 94))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 51)));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("RESOLUTION & COMPLETION LOG");

        jLabel16.setText("Update Ticket Status:");

        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton1.setText("Mark as Resolved");
        jRadioButton1.setContentAreaFilled(false);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton2.setText("Reassign");
        jRadioButton2.setToolTipText("");
        jRadioButton2.setContentAreaFilled(false);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel19.setText("Remarks:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(5, 10, 1, 1)));
        jScrollPane1.setViewportView(jTextArea1);

        jLabel20.setText("Completion Time:");
        jLabel20.setToolTipText("");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel21.setText("mm-dd-yyyy / 00:00 AM/PM");

        jButton3.setBackground(new java.awt.Color(0, 102, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("COMPLETE & CLOSE TICKET");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel22.setText("Time Assigned:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel23.setText("mm-dd-yyyy / 00:00 AM/PM");
        jLabel23.setToolTipText("");

        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton3.setText("Mark as In Progress");
        jRadioButton3.setToolTipText("");
        jRadioButton3.setContentAreaFilled(false);
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jRadioButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel17))
                                    .addComponent(jRadioButton1)
                                    .addComponent(jRadioButton3))
                                .addGap(0, 197, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addGap(31, 31, 31)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(53, Short.MAX_VALUE))))
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
        // TODO add your handling code here:
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

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jButton9.setEnabled(false);
        new archives().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jButton10.setEnabled(false);
        new userAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        updateRemarksState();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jButton7.setEnabled(false); // Prevents opening duplicate profile windows
        
        profile prof = new profile();
        prof.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                jButton7.setEnabled(true); // Re-enables button when profile window is closed
            }
        });
        prof.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        updateRemarksState();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        updateRemarksState();
    }//GEN-LAST:event_jRadioButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new update().setVisible(true);
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
