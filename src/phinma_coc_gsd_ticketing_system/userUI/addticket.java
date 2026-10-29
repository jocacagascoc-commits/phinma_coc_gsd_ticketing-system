/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.userUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import phinma_coc_gsd_ticketing_system.connections.DBConnection;
import phinma_coc_gsd_ticketing_system.connections.UserSession;
import phinma_coc_gsd_ticketing_system.frstframes.login;


/**
 *
 * @author ACER
 */
public class addticket extends javax.swing.JFrame {

     private String selectedImagePath = "";
    /**
     * Creates new form addticket
     */
    public addticket() {
        initComponents();
        
        setupFullScreenWrapper();
        
        setActiveSidebarButton(jButton2);
        addGoldHoverEffect(jButton1);
        addGoldHoverEffect(jButton3);
        addGoldHoverEffect(jButton4);
        addGoldHoverEffect(jButton5);
        
        if (UserSession.isLoggedIn()) {
        username.setText(UserSession.getFullName());
    }
        jLabel5.setText("Click to attach photo proof...");
        addPlaceholder(room, "e.g., Room 134", 0);
        addPlaceholder(description, "e.g. The DCHP is down...", 0);
        
        setupImageUploadListener();
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
    
    private void setupImageUploadListener() {
       jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    java.awt.event.ActionListener uploadAction = e -> requestAccessAndSelectImage();

    // Attach click listener directly to upload button
    jButton7.addActionListener(uploadAction);

    // Attach click listener to panel and text label as fallback hitboxes
    java.awt.event.MouseAdapter mouseAdapter = new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            requestAccessAndSelectImage();
        }
    };
    jPanel5.addMouseListener(mouseAdapter);
    jLabel5.addMouseListener(mouseAdapter);
}

private void requestAccessAndSelectImage() {
    // Permission dialog asking for access to user's photos/files
    int permission = JOptionPane.showConfirmDialog(
        this,
        "Allow GSD Ticketing System to access your device photos and files to attach image proof?",
        "File Permission Request",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );

    if (permission == JOptionPane.YES_OPTION) {
        selectProofImage();
    }
}

    private void selectProofImage() {
    try {
        // Enforce modern Native OS Look and Feel for File Explorer
        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {}

    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Select Evidence Image");
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setFileFilter(new FileNameExtensionFilter("Image Files (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"));
    
    int result = chooser.showOpenDialog(this);
    
    // Restore Nimbus L&F for main app components
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception ignored) {}

    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = chooser.getSelectedFile();
        selectedImagePath = selectedFile.getAbsolutePath();
        
        // Update upload box with modern active state UI
        updateUploadCardUI(true, selectedFile.getName());
    }
}
    
    private void updateUploadCardUI(boolean hasFile, String fileName) {
    if (hasFile) {
        jPanel5.setBackground(new java.awt.Color(235, 247, 238)); // Light mint active tint
        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 76), 2, true),
            javax.swing.BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        
        jLabel5.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
        jLabel5.setForeground(new java.awt.Color(0, 102, 51));
        jLabel5.setText("Attached: " + fileName);
    } else {
        jPanel5.setBackground(java.awt.Color.WHITE);
        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180), 1, true),
            javax.swing.BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        
        jLabel5.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13));
        jLabel5.setForeground(java.awt.Color.GRAY);
        jLabel5.setText("Click to attach photo proof...");
    }
    
    jPanel5.revalidate();
    jPanel5.repaint();
}
    
    private void addPlaceholder(javax.swing.JTextField textField, String placeholderText, int topPadding) {
        textField.setText(""); // Clear initial text from GUI builder
        
        javax.swing.JLabel prompt = new javax.swing.JLabel(placeholderText);
        prompt.setForeground(java.awt.Color.GRAY);
        prompt.setFont(textField.getFont());
        prompt.setVerticalAlignment(topPadding > 0 ? javax.swing.SwingConstants.TOP : javax.swing.SwingConstants.CENTER);
        prompt.setBorder(javax.swing.BorderFactory.createEmptyBorder(topPadding, 8, 0, 0));
        
        textField.setLayout(new java.awt.BorderLayout());
        textField.add(prompt, java.awt.BorderLayout.WEST);
        
        // Transfer click on the placeholder directly to focus the text field
        prompt.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                textField.requestFocusInWindow();
            }
        });

        // Hide prompt only when characters are present
        textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void check() {
                prompt.setVisible(textField.getText().isEmpty());
            }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { check(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { check(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { check(); }
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

        jSeparator1 = new javax.swing.JSeparator();
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
        jButton6 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        room = new javax.swing.JTextField();
        building = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        equipment = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        others = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        description = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        username = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();

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
        jLabel3.setText("Submit a Repair Request");

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
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 204, 51));
        jButton2.setText("Add Ticket");
        jButton2.setToolTipText("");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

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

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/userUI/icons8-profile-48.png"))); // NOI18N
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
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(60, 60, 60))
        );

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("You're logged in as:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Room or Location");

        room.setText("e.g., Room 134");

        building.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MS", "MW", "MN", "PH", "SHS" }));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Building");

        equipment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "TV", "Computer", "Electric Fan", "Outlet", "Faucet", "Lighting", "Others" }));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Equipment");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("If Others, please specify here:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Describe the Problem down below:");

        description.setText("e.g. The DCHP is down...");
        description.setToolTipText("");
        description.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(5, 5, 100, 50)));
        description.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        description.setMargin(new java.awt.Insets(5, 5, 5, 5));

        submitButton.setBackground(new java.awt.Color(0, 204, 0));
        submitButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        submitButton.setText("Submit");
        submitButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        cancelButton.setBackground(new java.awt.Color(255, 0, 0));
        cancelButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        username.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        username.setForeground(new java.awt.Color(51, 0, 255));
        username.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        username.setText("username");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Drop an Image as Proof:");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(51, 51, 255));

        jLabel5.setText("example.png");

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phinma_coc_gsd_ticketing_system/userUI/icons8-upload-48.png"))); // NOI18N
        jButton7.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(57, 57, 57)
                                    .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(room, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(35, 35, 35)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(description)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(equipment, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(41, 41, 41)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(others, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap(35, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1036, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(room, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(equipment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(others, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))))
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

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        String user = UserSession.isLoggedIn() ? UserSession.getFullName() : username.getText().trim();
        String roomLoc = room.getText().trim();
        String bldg = building.getSelectedItem().toString();
        String equip = equipment.getSelectedItem().toString();
        String otherEquip = others.getText().trim();
        String prio = "Normal";
        String desc = description.getText().trim();

        if (roomLoc.isEmpty() || roomLoc.equalsIgnoreCase("e.g., Room 134")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid room location.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (equip.equalsIgnoreCase("Others") && otherEquip.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please specify the equipment under 'If Others'.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (desc.isEmpty() || desc.equalsIgnoreCase("e.g. The DCHP is down...")) {
            JOptionPane.showMessageDialog(this, "Please describe the problem.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (checkAndShowExistingTicket(roomLoc, bldg, equip)) {
            return;
        }

        Connection conn = DBConnection.connect();
        if (conn == null) return;

        String sql = "INSERT INTO tbl_tickets (username, room, building, equipment, others_specified, image_proof, priority, description) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user);
            pstmt.setString(2, roomLoc);
            pstmt.setString(3, bldg);
            pstmt.setString(4, equip);

            if (equip.equalsIgnoreCase("Others")) {
                pstmt.setString(5, otherEquip);
            } else {
                pstmt.setNull(5, java.sql.Types.VARCHAR);
            }

            if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                pstmt.setString(6, selectedImagePath);
            } else {
                pstmt.setNull(6, java.sql.Types.VARCHAR);
            }

            pstmt.setString(7, prio);
            pstmt.setString(8, desc);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Ticket submitted successfully! Monitor your ticket to know its status.", "Success", JOptionPane.INFORMATION_MESSAGE);

                room.setText("");
                description.setText("");
                others.setText("");
                equipment.setSelectedIndex(0);
                building.setSelectedIndex(0);
                selectedImagePath = "";

                updateUploadCardUI(false, ""); 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving ticket: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private boolean checkAndShowExistingTicket(String roomLoc, String bldg, String equip) {
        String sql = "SELECT * FROM tbl_tickets WHERE room = ? AND building = ? AND equipment = ? "
               + "AND status IN ('Pending', 'Assigned', 'In Progress') ORDER BY ticket_id ASC LIMIT 1";

    try (Connection conn = DBConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, roomLoc);
        pstmt.setString(2, bldg);
        pstmt.setString(3, equip);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int existingId = rs.getInt("ticket_id");
            String code = String.format("#%04d", existingId);
            String submittedBy = rs.getString("username");
            String status = rs.getString("status");
            String desc = rs.getString("description");
            String assigned = rs.getString("assigned_worker");
            
            String timeSubmitted = "N/A";
            try {
                java.sql.Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    timeSubmitted = new java.text.SimpleDateFormat("MM-dd-yy / hh:mm a").format(ts);
                }
            } catch (Exception ignored) {}

            showDuplicateTicketDialog(code, submittedBy, equip, roomLoc + ", " + bldg, status, timeSubmitted, assigned, desc);
            return true; // Duplicate active ticket found
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return false; // No existing ticket
}

    private void showDuplicateTicketDialog(String code, String submittedBy, String problem, String location, String status, String time, String assigned, String desc) {
    JPanel mainPanel = new JPanel(new java.awt.BorderLayout());
    mainPanel.setPreferredSize(new java.awt.Dimension(500, 380));
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220, 38, 38), 2));

    JPanel headerPanel = new JPanel(new java.awt.BorderLayout());
    headerPanel.setBackground(new Color(220, 38, 38));
    headerPanel.setPreferredSize(new java.awt.Dimension(500, 45));
    headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

    javax.swing.JLabel lblTitle = new javax.swing.JLabel("⚠️ Duplicate Issue Already Reported");
    lblTitle.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
    lblTitle.setForeground(Color.WHITE);

    javax.swing.JLabel lblCode = new javax.swing.JLabel("[" + code + "]");
    lblCode.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 16));
    lblCode.setForeground(new Color(255, 204, 51));

    headerPanel.add(lblTitle, java.awt.BorderLayout.WEST);
    headerPanel.add(lblCode, java.awt.BorderLayout.EAST);

    JPanel bodyPanel = new JPanel();
    bodyPanel.setLayout(new javax.swing.BoxLayout(bodyPanel, javax.swing.BoxLayout.Y_AXIS));
    bodyPanel.setBackground(Color.WHITE);
    bodyPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));

    java.awt.Font labelFont = new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13);
    java.awt.Font valueFont = new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13);

    String[][] details = {
        {"Reported By:", submittedBy},
        {"Problem:", problem},
        {"Location:", location},
        {"Current Status:", status},
        {"Time Submitted:", time},
        {"Assigned Worker:", (assigned != null && !assigned.trim().isEmpty() ? assigned : "Unassigned")}
    };

    for (String[] detail : details) {
        JPanel row = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 3));
        row.setOpaque(false);
        row.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        javax.swing.JLabel lblKey = new javax.swing.JLabel(detail[0]);
        lblKey.setFont(labelFont);
        lblKey.setPreferredSize(new java.awt.Dimension(140, 20));

        javax.swing.JLabel lblVal = new javax.swing.JLabel(detail[1]);
        lblVal.setFont(valueFont);

        row.add(lblKey);
        row.add(lblVal);
        bodyPanel.add(row);
    }

    bodyPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 8)));

    javax.swing.JLabel lblDescHead = new javax.swing.JLabel("Problem Description:");
    lblDescHead.setFont(labelFont);
    lblDescHead.setAlignmentX(JPanel.LEFT_ALIGNMENT);
    bodyPanel.add(lblDescHead);
    bodyPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 4)));

    javax.swing.JTextArea txtDescArea = new javax.swing.JTextArea(desc != null && !desc.trim().isEmpty() ? desc : "N/A");
    txtDescArea.setFont(valueFont);
    txtDescArea.setEditable(false);
    txtDescArea.setLineWrap(true);
    txtDescArea.setWrapStyleWord(true);
    txtDescArea.setBackground(new Color(248, 249, 250));

    javax.swing.JScrollPane descScroll = new javax.swing.JScrollPane(txtDescArea);
    descScroll.setPreferredSize(new java.awt.Dimension(450, 65));
    descScroll.setAlignmentX(JPanel.LEFT_ALIGNMENT);
    bodyPanel.add(descScroll);

    mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
    mainPanel.add(bodyPanel, java.awt.BorderLayout.CENTER);

    JOptionPane.showMessageDialog(this, mainPanel, "Existing Ticket Found", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_submitButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel submitting this ticket?", "Cancel Ticket", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new homepage().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton1.setEnabled(false);
        new homepage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UserSession.clear();
            new login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton3.setEnabled(false);
        new ticketlist().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jButton4.setEnabled(false);
        new status().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(addticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addticket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> building;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField description;
    private javax.swing.JComboBox<String> equipment;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField others;
    private javax.swing.JTextField room;
    private javax.swing.JButton submitButton;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}
