
package userinterface;

import java.awt.Color;
import static java.awt.Color.CYAN;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class userHome extends javax.swing.JFrame {

   
    public userHome() {
        initComponents();
         addMouseEnterListener(englishButton);
         addMouseEnterListener(scienceButton);
         addMouseEnterListener(mathButton);
        
       
    }
    
    
    
    private static void addMouseEnterListener(JButton button) {
     Color defaultColor = new Color(221, 221, 221);
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(86, 86, 86)); // Brown color
        }

        @Override
        public void mouseExited(MouseEvent e) {
              button.setBackground(defaultColor);
        }
    });
}
    
    
    
     
    
    
    
                

           
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jColorChooser1 = new javax.swing.JColorChooser();
        panel2 = new java.awt.Panel();
        leftPanel = new java.awt.Panel();
        profile = new javax.swing.JLabel();
        borrowsButton = new javax.swing.JButton();
        reservationButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        historyButton = new javax.swing.JButton();
        notificationsButton = new javax.swing.JButton();
        booksButton = new javax.swing.JButton();
        userName = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        englishButton = new javax.swing.JButton();
        scienceButton = new javax.swing.JButton();
        mathButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        borrowTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        helpButton = new javax.swing.JButton();
        logOutButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        role = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        contact = new javax.swing.JLabel();
        contact1 = new javax.swing.JLabel();
        setName = new javax.swing.JTextField();
        setRole = new javax.swing.JTextField();
        setId = new javax.swing.JTextField();
        setEmail = new javax.swing.JTextField();
        setContact = new javax.swing.JTextField();
        editButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab1", jPanel8);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 700));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(26, 27, 27));
        panel2.setPreferredSize(new java.awt.Dimension(1400, 20));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1140, 40));

        leftPanel.setBackground(new java.awt.Color(26, 27, 27));
        leftPanel.setPreferredSize(new java.awt.Dimension(100, 1000));
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profile.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\whiteIcon.png")); // NOI18N
        leftPanel.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        borrowsButton.setBackground(new java.awt.Color(26, 27, 27));
        borrowsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        borrowsButton.setForeground(java.awt.Color.white);
        borrowsButton.setText("Borrows");
        borrowsButton.setActionCommand("");
        borrowsButton.setBorder(null);
        borrowsButton.setBorderPainted(false);
        borrowsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(borrowsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 110, -1));

        reservationButton.setBackground(new java.awt.Color(26, 27, 27));
        reservationButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        reservationButton.setForeground(java.awt.Color.white);
        reservationButton.setText("Reservation");
        reservationButton.setBorder(null);
        reservationButton.setBorderPainted(false);
        reservationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservationButtonActionPerformed(evt);
            }
        });
        leftPanel.add(reservationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, -1, -1));

        settingsButton.setBackground(new java.awt.Color(26, 27, 27));
        settingsButton.setFont(new java.awt.Font("Swis721 Lt BT", 1, 18)); // NOI18N
        settingsButton.setForeground(java.awt.Color.white);
        settingsButton.setText("Settings");
        settingsButton.setBorder(null);
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(settingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, -1, -1));

        jSeparator1.setBackground(java.awt.Color.white);
        jSeparator1.setPreferredSize(new java.awt.Dimension(70, 10));
        leftPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 90, -1));

        historyButton.setBackground(new java.awt.Color(26, 27, 27));
        historyButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        historyButton.setForeground(java.awt.Color.white);
        historyButton.setText("History");
        historyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButtonActionPerformed(evt);
            }
        });
        leftPanel.add(historyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

        notificationsButton.setBackground(new java.awt.Color(26, 27, 27));
        notificationsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        notificationsButton.setForeground(java.awt.Color.white);
        notificationsButton.setText("Notifications");
        notificationsButton.setBorder(null);
        notificationsButton.setBorderPainted(false);
        notificationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(notificationsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

        booksButton.setBackground(new java.awt.Color(26, 27, 27));
        booksButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        booksButton.setForeground(java.awt.Color.white);
        booksButton.setText("Books");
        booksButton.setActionCommand("Borrowing");
        booksButton.setBorder(null);
        booksButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        booksButton.setFocusPainted(false);
        booksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booksButtonActionPerformed(evt);
            }
        });
        leftPanel.add(booksButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, 30));

        userName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        userName.setForeground(java.awt.Color.white);
        userName.setText("User Name");
        leftPanel.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        getContentPane().add(leftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 790));

        jPanel1.setBackground(new java.awt.Color(202, 187, 187));

        englishButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        englishButton.setText("ENGLISH");
        englishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                englishButtonActionPerformed(evt);
            }
        });

        scienceButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        scienceButton.setText("SCIENCE");

        mathButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mathButton.setText("MATHEMATICS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(englishButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scienceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(mathButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(englishButton)
                    .addComponent(scienceButton)
                    .addComponent(mathButton))
                .addContainerGap(553, Short.MAX_VALUE))
        );

        tabs.addTab("tab1", jPanel1);

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));

        borrowTable.setBackground(new java.awt.Color(26, 27, 27));
        borrowTable.setFont(new java.awt.Font("STFangsong", 1, 18)); // NOI18N
        borrowTable.setForeground(java.awt.Color.white);
        borrowTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Book Title", "Book #", "Date of Borrow", "Date of Return"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        borrowTable.setGridColor(new java.awt.Color(255, 255, 255));
        borrowTable.setSelectionBackground(new java.awt.Color(7, 7, 75));
        borrowTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(borrowTable);
        if (borrowTable.getColumnModel().getColumnCount() > 0) {
            borrowTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            borrowTable.getColumnModel().getColumn(1).setResizable(false);
            borrowTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            borrowTable.getColumnModel().getColumn(2).setResizable(false);
            borrowTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            borrowTable.getColumnModel().getColumn(3).setResizable(false);
            borrowTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabs.addTab("tab2", jPanel2);

        jPanel13.setBackground(new java.awt.Color(202, 187, 187));

        jTable1.setBackground(new java.awt.Color(247, 247, 206));
        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Book Title", "Book #", "Date of Reservation", "Date Available"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 204, 204));
        jTable1.setSelectionBackground(new java.awt.Color(232, 210, 210));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabs.addTab("tab3", jPanel3);

        jPanel5.setBackground(new java.awt.Color(234, 212, 212));

        historyTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Book Title", "Book #", "Date of Borrow", "Date of Return"
            }
        ));
        jScrollPane3.setViewportView(historyTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tabs.addTab("tab4", jPanel5);

        jPanel10.setBackground(new java.awt.Color(214, 214, 238));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 595, Short.MAX_VALUE)
        );

        tabs.addTab("tab5", jPanel10);

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));

        helpButton.setBackground(new java.awt.Color(255, 255, 255));
        helpButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        helpButton.setForeground(new java.awt.Color(0, 0, 0));
        helpButton.setText("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        logOutButton.setBackground(new java.awt.Color(255, 255, 255));
        logOutButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logOutButton.setForeground(new java.awt.Color(0, 0, 0));
        logOutButton.setText("Log Out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Sitka Display", 1, 48)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Profile Information");

        name.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name.setForeground(java.awt.Color.white);
        name.setText("NAME         :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);

        role.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        role.setForeground(java.awt.Color.white);
        role.setText("ROLE           :");

        id.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        id.setForeground(java.awt.Color.white);
        id.setText("ID #             :");

        email.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        email.setForeground(java.awt.Color.white);
        email.setText("EMAIL         :");

        contact.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        contact.setForeground(java.awt.Color.white);
        contact.setText("CONTACT # :");

        contact1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        contact1.setForeground(java.awt.Color.white);
        contact1.setText("PASSWORD        :");

        setName.setBackground(new java.awt.Color(204, 204, 204));

        setRole.setBackground(new java.awt.Color(204, 204, 204));

        setId.setBackground(new java.awt.Color(204, 204, 204));

        setEmail.setBackground(new java.awt.Color(204, 204, 204));

        setContact.setBackground(new java.awt.Color(204, 204, 204));

        editButton.setBackground(new java.awt.Color(255, 255, 255));
        editButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editButton.setForeground(new java.awt.Color(0, 0, 0));
        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(contact1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(role, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                        .addComponent(id, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(setName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(setEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(setRole, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(setId, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(setContact, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(editButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(logOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(logOutButton)
                            .addComponent(helpButton))
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(role, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(contact1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(setContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("tab6", jPanel11);

        getContentPane().add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 740, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    
    
    private void borrowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowsButtonActionPerformed
               tabs.setSelectedIndex(1);                                                  
    }//GEN-LAST:event_borrowsButtonActionPerformed

    private void historyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButtonActionPerformed
                       tabs.setSelectedIndex(3);           // TODO add your handling code here:
    }//GEN-LAST:event_historyButtonActionPerformed

    private void booksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booksButtonActionPerformed
                     tabs.setSelectedIndex(0);         
    }//GEN-LAST:event_booksButtonActionPerformed

    private void reservationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationButtonActionPerformed
                  tabs.setSelectedIndex(2);                     
      
    // Refresh the layout of grayPanel
    
         
        
    }//GEN-LAST:event_reservationButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
                          tabs.setSelectedIndex(5);           // TODO add your handling code here:
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void notificationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationsButtonActionPerformed
                          tabs.setSelectedIndex(4);           // TODO add your handling code here:
    }//GEN-LAST:event_notificationsButtonActionPerformed

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_helpButtonActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editButtonActionPerformed

    private void englishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_englishButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_englishButtonActionPerformed

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
            java.util.logging.Logger.getLogger(userHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton booksButton;
    private javax.swing.JTable borrowTable;
    private javax.swing.JButton borrowsButton;
    private javax.swing.JLabel contact;
    private javax.swing.JLabel contact1;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel email;
    private javax.swing.JButton englishButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JButton historyButton;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel id;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private java.awt.Panel leftPanel;
    private javax.swing.JButton logOutButton;
    private javax.swing.JButton mathButton;
    private javax.swing.JLabel name;
    private javax.swing.JButton notificationsButton;
    private java.awt.Panel panel2;
    private javax.swing.JLabel profile;
    private javax.swing.JButton reservationButton;
    private javax.swing.JLabel role;
    private javax.swing.JButton scienceButton;
    private javax.swing.JTextField setContact;
    private javax.swing.JTextField setEmail;
    private javax.swing.JTextField setId;
    private javax.swing.JTextField setName;
    private javax.swing.JTextField setRole;
    private javax.swing.JButton settingsButton;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables
}
