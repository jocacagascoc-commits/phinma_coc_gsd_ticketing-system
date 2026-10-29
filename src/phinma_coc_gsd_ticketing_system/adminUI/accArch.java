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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;


public class accArch extends javax.swing.JFrame {

    private JTable archiveTable;
    private DefaultTableModel tableModel;
    private boolean isUpdatingPlaceholder = false;
    private JDialog parentDialog;

    public static class ArchivedUserData {
        int id;
        String fullName;
        String email;
        String role;
        String dateArchived;
        String archivedBy;

        public ArchivedUserData(int id, String fullName, String email, String role, String dateArchived, String archivedBy) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.role = role;
            this.dateArchived = dateArchived;
            this.archivedBy = archivedBy;
        }
    }
    
    public accArch() {
        initComponents();
        this.setPreferredSize(new Dimension(1020, 650));
        this.setSize(1020, 650);
        this.getContentPane().setBackground(new java.awt.Color(222, 252, 232));
        this.setLocationRelativeTo(null);
        customInit();
    }

    public void setParentDialog(JDialog dialog) {
        this.parentDialog = dialog;
    }
    private void customInit() {
    
        Color lightGreen = new Color(222, 252, 232);
        
        this.getContentPane().removeAll();
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(222, 252, 232));

        jPanel1.removeAll();
        jPanel1.setLayout(new BorderLayout());
        jPanel1.setBackground(new Color(222, 252, 232));

    // 2. Dark Green Header Bar (Full Width Top Banner)
        jPanel2.removeAll();
        jPanel2.setLayout(new BorderLayout());
        jPanel2.setBackground(new Color(0, 102, 51));
        jPanel2.setPreferredSize(new Dimension(0, 65));

        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 22));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Archived Accounts");
        jPanel2.add(jLabel1, BorderLayout.CENTER);

        jPanel1.add(jPanel2, BorderLayout.NORTH);

        // 3. Main Center Wrapper (Uniform Light Green Background)
        JPanel bodyWrapper = new JPanel(new BorderLayout(0, 12));
        bodyWrapper.setBackground(new Color(222, 252, 232));
        bodyWrapper.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // 4. Search Bar & Sort Dropdown Panel (Top Controls)
        JPanel searchGroup = new JPanel(new BorderLayout(0, 0));
        searchGroup.setOpaque(false);

        jTextField1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        jTextField1.setForeground(new Color(153, 153, 153));
        jTextField1.setText("Search Here...");

        jButton1.setFont(new Font("Tahoma", Font.BOLD, 12));
        jButton1.setText("Clear Search");
        jButton1.setFocusPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setPreferredSize(new Dimension(120, 32));

        searchGroup.add(jTextField1, BorderLayout.CENTER);
        searchGroup.add(jButton1, BorderLayout.EAST);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Sort", "A > Z", "Z < A", "Old to New", "New to Old"}));
        jComboBox1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        jComboBox1.setPreferredSize(new Dimension(130, 32));

        JPanel searchBarPanel = new JPanel(new BorderLayout(12, 0));
        searchBarPanel.setOpaque(false);
        searchBarPanel.setPreferredSize(new Dimension(0, 32));
        searchBarPanel.add(searchGroup, BorderLayout.CENTER);
        searchBarPanel.add(jComboBox1, BorderLayout.EAST);

        bodyWrapper.add(searchBarPanel, BorderLayout.NORTH);

        // 5. Table Container Panel (White Card)
        // REPLACE Section 5 inside customInit() with:
        jPanel4.removeAll();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.setPreferredSize(new Dimension(960, 440));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 102, 51), 1));
        jPanel4.setBackground(Color.WHITE);

        // Column Names
        String[] columns = {"User ID", "Name", "Date Archived", "Archived By", "Actions"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the Actions column button is clickable
            }
        };

        archiveTable = new JTable(tableModel);
        archiveTable.setFont(new Font("Tahoma", Font.PLAIN, 13));
        archiveTable.setRowHeight(36);
        archiveTable.setSelectionBackground(new Color(222, 252, 232));
        archiveTable.setSelectionForeground(Color.BLACK);
        archiveTable.setGridColor(new Color(230, 230, 230));
        archiveTable.setShowGrid(true);

        // Header Styling
        JTableHeader header = archiveTable.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 13));
        header.setBackground(new Color(245, 245, 245));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(0, 35));
        header.setReorderingAllowed(false);

        // Alignment & Green Highlight Renderers
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        DefaultTableCellRenderer archByRenderer = new DefaultTableCellRenderer();
        archByRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        archByRenderer.setFont(new Font("Tahoma", Font.BOLD, 12));
        archByRenderer.setForeground(new Color(0, 102, 51));

        archiveTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // User ID
        archiveTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);   // Name
        archiveTable.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);   // Date Archived
        archiveTable.getColumnModel().getColumn(3).setCellRenderer(archByRenderer); // Archived By

        // Column Widths
        archiveTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // User ID
        archiveTable.getColumnModel().getColumn(1).setPreferredWidth(220); // Name
        archiveTable.getColumnModel().getColumn(2).setPreferredWidth(180); // Date Archived
        archiveTable.getColumnModel().getColumn(3).setPreferredWidth(160); // Archived By
        archiveTable.getColumnModel().getColumn(4).setPreferredWidth(130); // Actions

        // Button Renderer & Click Listener for Column 4
        archiveTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        archiveTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(archiveTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(null);

        jPanel4.add(scrollPane, BorderLayout.CENTER);


        bodyWrapper.add(jPanel4, BorderLayout.CENTER);

        // 6. Bottom Panel - Left-Aligned Exit Button
        JPanel exitBtnPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        exitBtnPanel.setBackground(lightGreen);

        jButton4.setFont(new Font("Tahoma", Font.BOLD, 12));
        jButton4.setText("Exit Archives");
        jButton4.setFocusPainted(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setPreferredSize(new Dimension(160, 34));

        exitBtnPanel.add(jButton4);
        bodyWrapper.add(exitBtnPanel, BorderLayout.SOUTH);

        jPanel1.add(bodyWrapper, BorderLayout.CENTER);

        // Attach to Frame Content Pane
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();

        // Event Listeners
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
                    isUpdatingPlaceholder = true;
                    jTextField1.setForeground(new Color(153, 153, 153));
                    jTextField1.setText("Search Here...");
                    isUpdatingPlaceholder = false;
                }
            }
        });

    jTextField1.getDocument().addDocumentListener(new DocumentListener() {
        public void insertUpdate(DocumentEvent e) { if (!isUpdatingPlaceholder) loadArchivedUsers(); }
        public void removeUpdate(DocumentEvent e) { if (!isUpdatingPlaceholder) loadArchivedUsers(); }
        public void changedUpdate(DocumentEvent e) { if (!isUpdatingPlaceholder) loadArchivedUsers(); }
    });

    jComboBox1.addActionListener(e -> loadArchivedUsers());

    jButton1.addActionListener(e -> {
        isUpdatingPlaceholder = true;
        jTextField1.setText("Search Here...");
        jTextField1.setForeground(new Color(153, 153, 153));
        isUpdatingPlaceholder = false;
        jComboBox1.setSelectedIndex(0);
        loadArchivedUsers();
    });

    jButton4.addActionListener(e -> {
        if (parentDialog != null) {
            parentDialog.dispose();
        } else {
            this.dispose();
        }
    });

    loadArchivedUsers();
}
    
    public void loadArchivedUsers() {
    tableModel.setRowCount(0); // Clear table rows

    String keyword = jTextField1.getText().trim();
    if ("Search Here...".equals(keyword)) keyword = "";

    String sortOption = jComboBox1.getSelectedItem() != null ? jComboBox1.getSelectedItem().toString() : "Sort";

    StringBuilder sql = new StringBuilder("SELECT * FROM tbl_archived_users WHERE 1=1");

    if (!keyword.isEmpty()) {
        sql.append(" AND (CAST(id AS CHAR) LIKE ? OR LOWER(full_name) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?))");
    }

    if ("A > Z".equalsIgnoreCase(sortOption)) {
        sql.append(" ORDER BY full_name ASC");
    } else if ("Z < A".equalsIgnoreCase(sortOption)) {
        sql.append(" ORDER BY full_name DESC");
    } else if ("Old to New".equalsIgnoreCase(sortOption)) {
        sql.append(" ORDER BY id ASC");
    } else {
        sql.append(" ORDER BY id DESC");
    }

    try (Connection conn = DBConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

        if (!keyword.isEmpty()) {
            String pat = "%" + keyword + "%";
            pstmt.setString(1, pat);
            pstmt.setString(2, pat);
            pstmt.setString(3, pat);
        }

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("full_name") != null ? rs.getString("full_name") : "N/A";

            String dateArch = "N/A";
            try {
                java.sql.Timestamp ts = rs.getTimestamp("archived_at");
                if (ts != null) {
                    dateArch = new SimpleDateFormat("MM-dd-yy / hh:mm a").format(ts);
                }
            } catch (Exception ignore) {}

            String archBy = "Admin";
            try {
                String colArch = rs.getString("archived_by");
                if (colArch != null && !colArch.trim().isEmpty()) {
                    archBy = colArch;
                }
            } catch (Exception ignore) {}

            // Add row directly to standard JTable model
            tableModel.addRow(new Object[]{id, name, dateArch, archBy, "Unarchive"});
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    private List<ArchivedUserData> fetchArchivedUsers(String keyword, String sortOption) {
        List<ArchivedUserData> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM tbl_archived_users WHERE 1=1");

        if (!keyword.isEmpty()) {
            sql.append(" AND (CAST(id AS CHAR) LIKE ? OR LOWER(full_name) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?))");
        }

        // Sorting Logic
        if ("A > Z".equalsIgnoreCase(sortOption)) {
            sql.append(" ORDER BY full_name ASC");
        } else if ("Z < A".equalsIgnoreCase(sortOption)) {
            sql.append(" ORDER BY full_name DESC");
        } else if ("Old to New".equalsIgnoreCase(sortOption)) {
            sql.append(" ORDER BY id ASC");
        } else if ("New to Old".equalsIgnoreCase(sortOption)) {
            sql.append(" ORDER BY id DESC");
        } else {
            sql.append(" ORDER BY id DESC");
        }

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            if (!keyword.isEmpty()) {
                String pat = "%" + keyword + "%";
                pstmt.setString(1, pat);
                pstmt.setString(2, pat);
                pstmt.setString(3, pat);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("full_name") != null ? rs.getString("full_name") : "N/A";
                String email = rs.getString("email") != null ? rs.getString("email") : "N/A";
                String role = rs.getString("role") != null ? rs.getString("role") : "Normal User";
                
                String dateArch = "N/A";
                try {
                    java.sql.Timestamp ts = rs.getTimestamp("archived_at");
                    if (ts != null) {
                        dateArch = new SimpleDateFormat("MM-dd-yy / hh:mm a").format(ts);
                    }
                } catch (Exception ignore) {}

                String archBy = "Admin";
                try {
                    String colArch = rs.getString("archived_by");
                    if (colArch != null && !colArch.trim().isEmpty()) {
                        archBy = colArch;
                    }
                } catch (Exception ignore) {}

                list.add(new ArchivedUserData(id, name, email, role, dateArch, archBy));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
    
    // Render the Unarchive Button inside the JTable cell
private class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
        setFont(new Font("Tahoma", Font.BOLD, 11));
        setBackground(new Color(0, 102, 51));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Unarchive" : value.toString());
        return this;
    }
}

// Handle Unarchive Button clicks inside the JTable
private class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setFont(new Font("Tahoma", Font.BOLD, 11));
        button.setBackground(new Color(0, 102, 51));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "Unarchive" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = archiveTable.getSelectedRow();
            if (row >= 0) {
                int modelRow = archiveTable.convertRowIndexToModel(row);
                int userId = Integer.parseInt(archiveTable.getModel().getValueAt(modelRow, 0).toString());
                String userName = archiveTable.getModel().getValueAt(modelRow, 1).toString();
                unarchiveUserAccount(userId, userName);
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
    
    private void unarchiveUserAccount(int userId, String targetUserName) {
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to unarchive and restore the account for \"" + targetUserName + "\"?",
        "Confirm Unarchive",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
        String restoreSql = "INSERT INTO tbl_users (id, full_name, email, password, role, id_number, department, year_level, status) "
                          + "SELECT id, full_name, email, password, role, id_number, department, year_level, 'Active' "
                          + "FROM tbl_archived_users WHERE id = ?";
        String deleteSql = "DELETE FROM tbl_archived_users WHERE id = ?";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtRestore = conn.prepareStatement(restoreSql);
                 PreparedStatement stmtDelete = conn.prepareStatement(deleteSql)) {

                stmtRestore.setInt(1, userId);
                stmtRestore.executeUpdate();

                stmtDelete.setInt(1, userId);
                stmtDelete.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(this, "Account for \"" + targetUserName + "\" successfully restored to User Administration!", "Account Unarchived", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error unarchiving user: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        loadArchivedUsers();
    }
}
    private void showArchivedDetailsDialog(ArchivedUserData u) {
        JDialog dialog = new JDialog(this, "Archived Account Details", true);
        dialog.setSize(480, 360);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(new Color(0, 102, 51));
        headerPanel.setBounds(0, 0, 480, 45);

        JLabel lblHeaderTitle = new JLabel("Archived Account Details");
        lblHeaderTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblHeaderTitle.setForeground(Color.WHITE);
        lblHeaderTitle.setBounds(20, 10, 250, 24);
        headerPanel.add(lblHeaderTitle);

        JLabel lblHeaderTag = new JLabel("[ID #" + u.id + "]", SwingConstants.RIGHT);
        lblHeaderTag.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblHeaderTag.setForeground(new Color(255, 204, 51));
        lblHeaderTag.setBounds(320, 10, 135, 24);
        headerPanel.add(lblHeaderTag);

        dialog.add(headerPanel);

        JLabel lblDetails = new JLabel("<html><body style='font-family:Tahoma; font-size:11pt; line-height:1.5;'>"
                + "<b>User ID:</b> " + u.id + "<br><br>"
                + "<b>Full Name:</b> " + u.fullName + "<br><br>"
                + "<b>Email Address:</b> " + u.email + "<br><br>"
                + "<b>Role:</b> " + u.role + "<br><br>"
                + "<b>Date Archived:</b> " + u.dateArchived + "<br><br>"
                + "<b>Archived By:</b> <font color='#006633'>" + u.archivedBy + "</font></body></html>");
        lblDetails.setVerticalAlignment(SwingConstants.TOP);
        lblDetails.setBounds(30, 60, 420, 200);
        dialog.add(lblDetails);

        // Footer Action Panel
        JPanel footerPanel = new JPanel(null);
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBounds(0, 260, 480, 65);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(222, 252, 232));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("User ID");
        jLabel10.setToolTipText("");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Full Name");
        jLabel11.setToolTipText("");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Date Archived");
        jLabel12.setToolTipText("");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton2.setText("Unarchive");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton3.setText("View");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addComponent(jSeparator5)
            .addComponent(jSeparator6)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 439, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1)
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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Archived Accounts");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTextField1.setText("Search Here...");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Clear Search");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort", "A > Z", "Z < A", "Old to New", "New to Old" }));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton4.setText("Exit Archives");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jButton1)
                            .addGap(18, 18, 18)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(accArch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(accArch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(accArch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(accArch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new accArch().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
