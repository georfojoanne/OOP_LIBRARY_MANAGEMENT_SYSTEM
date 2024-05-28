
package userinterface;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.*;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;







public class admins extends javax.swing.JFrame {


    public admins() {
        initComponents();
        userTable() ;
         librarianTable();
        
        
    }
    
    
      

// Method to fetch data from the database and populate the librarianTable

     private void librarianTable(){
       
                 
                     try {
    dbConnection con = new dbConnection();
    Connection connection = con.getConnection();

    PreparedStatement ps1 = null;
    ResultSet rs = null;

    try {
        String query1 = "SELECT * FROM librarian";
        ps1 = connection.prepareStatement(query1);
        rs = ps1.executeQuery();

        DefaultTableModel tblModel = (DefaultTableModel) librarianTable.getModel();
         tblModel.setRowCount(0);

        while (rs.next()) {
            String name = rs.getString("User");
            String id = rs.getString("Id");
            String email = rs.getString("Email");
            String password = rs.getString("Password");            
                String contact = rs.getString("Contact");

            String[] userData = {name, id, email, password,contact };
            tblModel.addRow(userData);
        }
    } finally {
        // Close ResultSet, PreparedStatement, and Connection in a finally block
        if (rs != null) {
            rs.close();
        }
        if (ps1 != null) {
            ps1.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
} catch (SQLException ex) {
    Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
}                
        
        
                    
                     
    }
    
   
      

// Method to fetch data from the database and populate the userTable

     private void userTable() {
       
                 
                     try {
    dbConnection con = new dbConnection();
    Connection connection = con.getConnection();

    PreparedStatement ps1 = null;
    ResultSet rs = null;

    try {
        String query1 = "SELECT * FROM userinfo";
        ps1 = connection.prepareStatement(query1);
        rs = ps1.executeQuery();

        DefaultTableModel tblModel = (DefaultTableModel) userTable.getModel();
         tblModel.setRowCount(0);

        while (rs.next()) {
            String name = rs.getString("name");
            String id = rs.getString("id");
            String email = rs.getString("email");
            String password = rs.getString("Password");
               String role = rs.getString("role");
                String contact = rs.getString("Contact");

            String[] userData = {name, id, email, password,role,contact };
            tblModel.addRow(userData);
        }
    } finally {
        // Close ResultSet, PreparedStatement, and Connection in a finally block
        if (rs != null) {
            rs.close();
        }
        if (ps1 != null) {
            ps1.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
} catch (SQLException ex) {
    Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
}                
                        
                     
    }
    
   


    @SuppressWarnings("unchecked")
                          


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        librarianButton = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        remButton1 = new javax.swing.JButton();
        searchUserField = new javax.swing.JTextField();
        searchUser = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        addUser = new javax.swing.JButton();
        refreshUser = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        librarianTable = new javax.swing.JTable();
        addLibrarian = new javax.swing.JButton();
        removeLibrarian = new javax.swing.JButton();
        jTextField29 = new javax.swing.JTextField();
        jButton28 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        cancelUser = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        updateUser = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        updateLibrarian = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        libName = new javax.swing.JTextField();
        libId = new javax.swing.JTextField();
        libEmail = new javax.swing.JTextField();
        libContact = new javax.swing.JTextField();
        cancelLibrarian = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        libPassword = new javax.swing.JTextField();
        panel1 = new java.awt.Panel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 700));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(10, 29, 36));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userinterface/Untitled design (3).png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 100));

        jLabel2.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 255, 255));
        jLabel2.setText("LIB.IT");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("YOUR DIGITAL GATEWAY");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TO INFINITE KNOWLEDGE");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, -1, -1));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 20, 50, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 100));

        librarianButton.setBackground(new java.awt.Color(10, 29, 36));
        librarianButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setFont(new java.awt.Font("Stylus BT", 1, 22)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("DASHBOARD");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 150, -1));

        jButton3.setFont(new java.awt.Font("Stylus BT", 1, 22)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("BOOKS");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 150, -1));

        jButton4.setFont(new java.awt.Font("Stylus BT", 1, 22)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("USER");
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 150, -1));

        jButton5.setFont(new java.awt.Font("Stylus BT", 1, 22)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("LIBRARIAN");
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 150, -1));

        jLabel6.setFont(new java.awt.Font("Stylus BT", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("You are logged in as:");
        librarianButton.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabel7.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ADMIN");
        librarianButton.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 70, 30));
        librarianButton.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 190, 10));

        getContentPane().add(librarianButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 190, 600));

        jTabbedPane1.setBackground(new java.awt.Color(28, 68, 74));

        jPanel5.setBackground(new java.awt.Color(28, 68, 74));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        remButton1.setBackground(new java.awt.Color(49, 98, 103));
        remButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        remButton1.setForeground(new java.awt.Color(0, 255, 255));
        remButton1.setText("REMOVE");
        remButton1.setBorderPainted(false);
        remButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(remButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 530, 200, 30));

        searchUserField.setBackground(new java.awt.Color(97, 137, 137));
        searchUserField.setFont(new java.awt.Font("Stylus BT", 0, 14)); // NOI18N
        searchUserField.setForeground(new java.awt.Color(0, 255, 255));
        searchUserField.setText("Search username here");
        jPanel5.add(searchUserField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 690, 30));

        searchUser.setBackground(new java.awt.Color(49, 98, 103));
        searchUser.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        searchUser.setForeground(new java.awt.Color(0, 255, 255));
        searchUser.setText("SEARCH");
        searchUser.setBorderPainted(false);
        searchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserActionPerformed(evt);
            }
        });
        jPanel5.add(searchUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 490, 20));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User Name", "ID #", "Email", "Password", "Role", "Contact#"
            }
        ));
        jScrollPane6.setViewportView(userTable);

        jPanel5.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 710, 440));

        addUser.setBackground(new java.awt.Color(49, 98, 103));
        addUser.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 14)); // NOI18N
        addUser.setForeground(new java.awt.Color(0, 255, 255));
        addUser.setText("ADD");
        addUser.setBorderPainted(false);
        addUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserActionPerformed(evt);
            }
        });
        jPanel5.add(addUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 210, 30));

        refreshUser.setBackground(new java.awt.Color(49, 98, 103));
        refreshUser.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        refreshUser.setForeground(new java.awt.Color(0, 255, 255));
        refreshUser.setText("REFRESH");
        refreshUser.setBorderPainted(false);
        refreshUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshUserActionPerformed(evt);
            }
        });
        jPanel5.add(refreshUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 530, 210, 30));

        jTabbedPane1.addTab("tab3", jPanel5);

        jPanel6.setBackground(new java.awt.Color(28, 68, 74));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        librarianTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Librarian User Name", "ID #", "Email", "Password", "Contact#"
            }
        ));
        jScrollPane3.setViewportView(librarianTable);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 690, 420));

        addLibrarian.setBackground(new java.awt.Color(49, 98, 103));
        addLibrarian.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        addLibrarian.setForeground(new java.awt.Color(0, 255, 255));
        addLibrarian.setText("ADD");
        addLibrarian.setBorderPainted(false);
        addLibrarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLibrarianActionPerformed(evt);
            }
        });
        jPanel6.add(addLibrarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 510, 100, 30));

        removeLibrarian.setBackground(new java.awt.Color(49, 98, 103));
        removeLibrarian.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        removeLibrarian.setForeground(new java.awt.Color(0, 255, 255));
        removeLibrarian.setText("REMOVE");
        removeLibrarian.setBorderPainted(false);
        removeLibrarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeLibrarianActionPerformed(evt);
            }
        });
        jPanel6.add(removeLibrarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 510, 100, 30));

        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 690, 30));

        jButton28.setBackground(new java.awt.Color(49, 98, 103));
        jButton28.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jButton28.setForeground(new java.awt.Color(0, 255, 255));
        jButton28.setText("SEARCH");
        jButton28.setBorderPainted(false);
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 610, 20));

        jTabbedPane1.addTab("tab4", jPanel6);

        jPanel10.setBackground(new java.awt.Color(28, 68, 74));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Stylus BT", 1, 36)); // NOI18N
        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setText("ADD  USER");
        jPanel10.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));

        jLabel27.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel27.setForeground(java.awt.Color.white);
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("USER NAME");
        jLabel27.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel10.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 130, 40));

        jLabel28.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel28.setForeground(java.awt.Color.white);
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("ID #");
        jLabel28.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 130, 40));

        jLabel29.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel29.setForeground(java.awt.Color.white);
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("EMAIL");
        jLabel29.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel10.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 130, 40));

        jLabel30.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel30.setForeground(java.awt.Color.white);
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("PASSWORD");
        jLabel30.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel10.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 130, 40));
        jPanel10.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 460, 40));

        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });
        jPanel10.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 460, 40));
        jPanel10.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 460, 40));
        jPanel10.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 460, 40));

        jLabel33.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel33.setForeground(java.awt.Color.white);
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("ROLE");
        jLabel33.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel10.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 130, 40));

        cancelUser.setBackground(new java.awt.Color(49, 98, 103));
        cancelUser.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        cancelUser.setForeground(new java.awt.Color(0, 255, 255));
        cancelUser.setText("CANCEL");
        cancelUser.setBorderPainted(false);
        cancelUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelUserActionPerformed(evt);
            }
        });
        jPanel10.add(cancelUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 470, 120, 40));

        jComboBox4.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Student", "Faculty" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel10.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 460, 40));

        updateUser.setBackground(new java.awt.Color(49, 98, 103));
        updateUser.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        updateUser.setForeground(new java.awt.Color(0, 255, 255));
        updateUser.setText("UPDATE");
        updateUser.setBorderPainted(false);
        updateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateUserActionPerformed(evt);
            }
        });
        jPanel10.add(updateUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 470, 120, 40));

        jTabbedPane1.addTab("tab8", jPanel10);

        jPanel11.setBackground(new java.awt.Color(28, 68, 74));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Stylus BT", 1, 36)); // NOI18N
        jLabel13.setForeground(java.awt.Color.white);
        jLabel13.setText("ADD LIBRARIAN");
        jPanel11.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        updateLibrarian.setBackground(new java.awt.Color(49, 98, 103));
        updateLibrarian.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        updateLibrarian.setForeground(new java.awt.Color(0, 255, 255));
        updateLibrarian.setText("UPDATE");
        updateLibrarian.setBorderPainted(false);
        updateLibrarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateLibrarianActionPerformed(evt);
            }
        });
        jPanel11.add(updateLibrarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 470, 120, 40));

        jLabel31.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel31.setForeground(java.awt.Color.white);
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("USER NAME");
        jLabel31.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel11.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 110, 30));

        jLabel34.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel34.setForeground(java.awt.Color.white);
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("ID #");
        jLabel34.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel11.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 110, 30));

        jLabel35.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel35.setForeground(java.awt.Color.white);
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("EMAIL");
        jLabel35.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel11.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 110, -1));

        jLabel36.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel36.setForeground(java.awt.Color.white);
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("PASSWORD");
        jLabel36.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel11.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 110, -1));

        libName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                libNameActionPerformed(evt);
            }
        });
        jPanel11.add(libName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 460, 40));
        jPanel11.add(libId, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 460, 40));

        libEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                libEmailActionPerformed(evt);
            }
        });
        jPanel11.add(libEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 460, 40));
        jPanel11.add(libContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 460, 40));

        cancelLibrarian.setBackground(new java.awt.Color(49, 98, 103));
        cancelLibrarian.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        cancelLibrarian.setForeground(new java.awt.Color(0, 255, 255));
        cancelLibrarian.setText("CANCEL");
        cancelLibrarian.setBorderPainted(false);
        cancelLibrarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelLibrarianActionPerformed(evt);
            }
        });
        jPanel11.add(cancelLibrarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 470, 120, 40));

        jLabel5.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("CONTACT");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel11.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 90, 30));
        jPanel11.add(libPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 460, 40));

        jTabbedPane1.addTab("tab9", jPanel11);

        panel1.setBackground(new java.awt.Color(28, 68, 74));
        jTabbedPane1.addTab("tab7", panel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 720, 620));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
     
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
                     jTabbedPane1.setSelectedIndex(1);                                  
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cancelLibrarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelLibrarianActionPerformed
       
    }//GEN-LAST:event_cancelLibrarianActionPerformed

    private void libEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_libEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_libEmailActionPerformed

    private void libNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_libNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_libNameActionPerformed

    
    private void addLibririan() {                                          
    try {                                          
      
        // Retrieve data from text fields
        String name = libName.getText();
        String id = libId.getText();
        String email = libEmail.getText();
        String password = libPassword.getText();
        String contact = libContact.getText();
        
        // Use dbConnection to establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        // Execute SQL statement to insert data into the librarian table
        String query = "INSERT INTO librarian (User, Id, Email, Password, Contact) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, contact);
            
            // Execute the update
            pstmt.executeUpdate();
            
            // Once data is inserted, refresh the librarianTable
            librarianTable();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle SQL exceptions appropriately
        } finally {
            // Close the connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle SQL exceptions appropriately
            }
        }
    } catch (SQLException ex) {
            Logger.getLogger(admins.class.getName()).log(Level.SEVERE, null, ex);
    }
} 

    
    
    
    
    private void updateLibrarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateLibrarianActionPerformed
       
        
        addLibririan();
        
    }//GEN-LAST:event_updateLibrarianActionPerformed

    private void cancelUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelUserActionPerformed
     
    }//GEN-LAST:event_cancelUserActionPerformed

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void updateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateUserActionPerformed
       
    }//GEN-LAST:event_updateUserActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
      
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29ActionPerformed

    
    private void removeLibrarian() {                                                
    try {                                                
        // Check if a row is selected
        int selectedRow = librarianTable.getSelectedRow();
        if (selectedRow == -1) { // No row selected
            JOptionPane.showMessageDialog(this, "Please select a row to remove.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Retrieve data from the selected row
        String username = (String) librarianTable.getValueAt(selectedRow, 0);
        String password = (String) librarianTable.getValueAt(selectedRow, 3);
        
        // Use dbConnection to establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        // Construct and execute the SQL DELETE statement
        String query = "DELETE FROM librarian WHERE User = ? AND Password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            // Execute the update
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Row removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Refresh the librarianTable after removal
                librarianTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove row.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while removing the row.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close the connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle SQL exceptions appropriately
            }
        }
    } catch (SQLException ex) {
            Logger.getLogger(admins.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    
    private void removeLibrarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeLibrarianActionPerformed
       removeLibrarian() ;
        
        
        
    }//GEN-LAST:event_removeLibrarianActionPerformed

    private void addLibrarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLibrarianActionPerformed
     
    }//GEN-LAST:event_addLibrarianActionPerformed

    private void searchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserActionPerformed
                       searchUserField.requestFocus();
        
        String searchText = searchUserField.getText().trim();
                System.out.println(searchText);

    // Check if the search text is the placeholder
    if (searchText.isEmpty()||searchText.equals("Search username here")) {
        JOptionPane.showMessageDialog(this, "Please enter text to search.");
        return;
    }
    
    try {
        // Establish a connection to your database
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        // Create a SQL query to search for books
        String query = "SELECT * FROM userinfo WHERE name LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        
        // Use the search text in the query with wildcards for advanced search
        String searchQuery = "%" + searchText + "%";
        statement.setString(1, searchQuery);

        
        // Execute the query
        ResultSet resultSet = statement.executeQuery();
        
        // Clear the table before adding new rows
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);
        
        // Debug statement to check if the query returns any results
        boolean hasResults = false;
        
        // Populate the table with the search results
        while (resultSet.next()) {
            hasResults = true;
            String name = resultSet.getString("name");
            
            // Add row to the table model
            model.addRow(new Object[]{name});

        }
        
        // Check if results were found
        if (!hasResults) {
            JOptionPane.showMessageDialog(this, "No results found.");
        }
        
        // Close the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
    
    
    
    }//GEN-LAST:event_searchUserActionPerformed

    private void remButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remButton1ActionPerformed
        
    }//GEN-LAST:event_remButton1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void refreshUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshUserActionPerformed
        userTable() ;
    }//GEN-LAST:event_refreshUserActionPerformed

    private void addUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserActionPerformed

    }//GEN-LAST:event_addUserActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addLibrarian;
    private javax.swing.JButton addUser;
    private javax.swing.JButton cancelLibrarian;
    private javax.swing.JButton cancelUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField libContact;
    private javax.swing.JTextField libEmail;
    private javax.swing.JTextField libId;
    private javax.swing.JTextField libName;
    private javax.swing.JTextField libPassword;
    private javax.swing.JPanel librarianButton;
    private javax.swing.JTable librarianTable;
    private java.awt.Panel panel1;
    private javax.swing.JButton refreshUser;
    private javax.swing.JButton remButton1;
    private javax.swing.JButton removeLibrarian;
    private javax.swing.JButton searchUser;
    private javax.swing.JTextField searchUserField;
    private javax.swing.JButton updateLibrarian;
    private javax.swing.JButton updateUser;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
