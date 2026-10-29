/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.adminUI;

import phinma_coc_gsd_ticketing_system.adminUI.workerslist;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;


/**
 *
 * @author ACER
 */
public class workerDel extends javax.swing.JFrame {
    
    private String targetName = "";
    private String targetPhone = "";
    private String targetStatus = "";
    private workerslist parentFrame; // Holds reference to workerslist

    // Constructor that accepts parent workerslist
    public workerDel(workerslist parent) {
        this.parentFrame = parent;
        initComponents();
        customInit();
    }

    // Default constructor fallback
    public workerDel() {
        initComponents();
        customInit();
    }
    
    private void customInit() {
        this.setLocationRelativeTo(parentFrame);
    this.setTitle("Remover Portal");
    this.setAlwaysOnTop(true);

    // Prevent default automatic closing so the confirmation dialog controls the exit
    this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

    // Window 'X' button listener — triggers confirmation prompt
    this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            confirmAndExit();
        }
    });

    // Placeholder focus handling for worker code input
    jTextField1.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent evt) {
            if (jTextField1.getText().equals("e.g., Maintenance B, Personel #005")) {
                jTextField1.setText("");
                jTextField1.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent evt) {
            if (jTextField1.getText().trim().isEmpty()) {
                jTextField1.setForeground(new Color(153, 153, 153));
                jTextField1.setText("e.g., Maintenance B, Personel #005");
            }
        }
    });

    jTextField1.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            fetchWorkerInfo(jTextField1.getText().trim());
        }
    });
    
    jButton1.addActionListener(e -> handleArchiveWorker());
}
    
    private void confirmAndExit() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the Archive Portal?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            closePortal();
        }
    }

    // Helper method to remove blur overlay from workerslist and close portal
    private void closePortal() {
        if (parentFrame != null) {
            parentFrame.setBlurOverlay(false);
        }
        this.dispose();
    }
    
    private void fetchWorkerInfo(String code) {
        if (code.isEmpty() || code.equals("e.g., Maintenance B, Personel #005")) {
            resetInfoFields();
            return;
        }

        String query = "SELECT worker_name, phone_number, status FROM tbl_workers WHERE LOWER(worker_code) = LOWER(?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                targetName = rs.getString("worker_name");
                targetPhone = rs.getString("phone_number");
                String dbStatus = rs.getString("status");

                // Map "Day Off" / "Off Duty" to "Inactive", otherwise "Active"
                if (dbStatus != null && ("Day Off".equalsIgnoreCase(dbStatus) || "Off Duty".equalsIgnoreCase(dbStatus) || "Day-Off".equalsIgnoreCase(dbStatus) || "Inactive".equalsIgnoreCase(dbStatus))) {
                    targetStatus = "Inactive";
                } else {
                    targetStatus = "Active";
                }

                jLabel3.setText("Name: " + targetName);
                jLabel4.setText("Contact #: " + targetPhone);
                jLabel5.setText("Status: " + targetStatus);
            } else {
                resetInfoFields();
            }

        } catch (SQLException ex) {
            resetInfoFields();
        }
    }
    
    private void resetInfoFields() {
        targetName = "";
        targetPhone = "";
        targetStatus = "";
        jLabel3.setText("Name:");
        jLabel4.setText("Contact #:");
        jLabel5.setText("Status:"); 
    }
    
    private void handleArchiveWorker() {
        String code = jTextField1.getText().trim();
        String reason = jTextArea1.getText().trim();

        if (targetName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Worker Code!", "Worker Not Found", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide a reason to archive this worker!", "Reason Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get active admin username for accountability tracking
        String currentAdmin = UserSession.getFullName();
        if (currentAdmin == null || currentAdmin.trim().isEmpty()) {
            currentAdmin = "System Admin";
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to archive and remove worker " + code + " (" + targetName + ")?\n" +
            "This action will be recorded under admin: " + currentAdmin,
            "Confirm Archival",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        String insertArchiveSql = "INSERT INTO tbl_archived_workers (worker_code, worker_name, phone_number, status, reason_for_removal, removed_by, removed_at) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        String deleteWorkerSql = "DELETE FROM tbl_workers WHERE LOWER(worker_code) = LOWER(?)";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement insertStmt = conn.prepareStatement(insertArchiveSql);
                 PreparedStatement deleteStmt = conn.prepareStatement(deleteWorkerSql)) {

                // 1. Write record to archive table
                insertStmt.setString(1, code);
                insertStmt.setString(2, targetName);
                insertStmt.setString(3, targetPhone);
                insertStmt.setString(4, targetStatus);
                insertStmt.setString(5, reason);
                insertStmt.setString(6, currentAdmin);
                insertStmt.executeUpdate();

                // 2. Remove record from active workers table
                deleteStmt.setString(1, code);
                deleteStmt.executeUpdate();

                // Commit both operations
                conn.commit();

                // Send SMS Notice simulation
                sendRemovalSMS(targetPhone, targetName, reason);

                JOptionPane.showMessageDialog(this, "Worker " + targetName + " (" + code + ") was successfully archived and removed from active list.");

                if (parentFrame != null) {
                parentFrame.loadWorkersFromDatabase(); // Automatically refreshes workerslist table & dashboard counts
            }

            closePortal();

            } catch (SQLException ex) {
                conn.rollback(); // Undo operations if any step fails
                JOptionPane.showMessageDialog(this, "Transaction Failed: " + ex.getMessage(), "Error Archiving Worker", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Connection Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void sendRemovalSMS(String phoneNumber, String name, String reason) {
        String message = "Dear " + name + ", your account in PHINMA COC GSD System has been archived.\nReason: " + reason;

        System.out.println("=== SMS DISPATCH SIMULATION ===");
        System.out.println("To: " + phoneNumber);
        System.out.println("Message: " + message);
        System.out.println("===============================");
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
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 61, 31));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ARCHIVE PORTAL");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setText("e.g., Maintenance B, Personel #005");
        jTextField1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Worker's Code");

        jLabel3.setText("Name:");

        jLabel4.setText("Contact #:");

        jLabel5.setText("Status:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Reason to Archive:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setBackground(new java.awt.Color(204, 0, 51));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Archive Worker");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton2.setText("EXIT ↦");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(45, Short.MAX_VALUE))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        confirmAndExit();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(workerDel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(workerDel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(workerDel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(workerDel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new workerDel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
