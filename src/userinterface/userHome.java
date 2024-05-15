package userinterface;

import static java.awt.Color.BLACK;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.awt.Color;
import userinterface.dbConnection;

public class userHome extends javax.swing.JFrame  {
         private String username;
   
    public userHome() {
       
         
        initComponents();
        
        
        
             addHoverEffect(borrowButton);
            addHoverEffect(reserveButton);        
              dbData();
              populateBorrowsTableFromDatabase();
              populateReservationTableFromDatabase();
             
              
        
              
           
          
    }
    
    
    
    
    public void setUsername(String username){
        this.username = username;
       
         userName.setText(username);
           
    }
    
    public String getUsername(){
         return username;
    }
    
    
    
    private void dbData(){
       
                 
                     try {
    dbConnection con = new dbConnection();
    Connection connection = con.getConnection();

    PreparedStatement ps1 = null;
    ResultSet rs = null;

    try {
        String query1 = "SELECT * FROM books";
        ps1 = connection.prepareStatement(query1);
        rs = ps1.executeQuery();

        DefaultTableModel tblModel = (DefaultTableModel) searchBookTable.getModel();

        while (rs.next()) {
            String Title = rs.getString("Title");
            String Author = rs.getString("Author");
            String ISBN = rs.getString("ISBN");
            String Category = rs.getString("Category");
               String Status = rs.getString("Status");

            String[] bookData = {Title, Author, ISBN, Category,Status };
            tblModel.addRow(bookData);
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
    
    
    
    
    private static void addMouseEnterListener(JButton button) {
     Color defaultColor = new Color(51,0,51);
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(45,4,45)); 
        }

        @Override
        public void mouseExited(MouseEvent e) {
              button.setBackground(defaultColor);
        }
    });
}
    
    
    
    public void addHoverEffect(JButton button) {
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                  button.setForeground(new Color(204,102,255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
              
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
        xsuerButton = new javax.swing.JButton();
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
        reserveButton = new javax.swing.JButton();
        borrowButton = new javax.swing.JButton();
        searchBook = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        searchBookTable = new javax.swing.JTable();
        searchButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        borrowsTable = new javax.swing.JTable();
        returnButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reservationTable = new javax.swing.JTable();
        cancelButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
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
        saveButton = new javax.swing.JButton();

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
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(900, 700));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(26, 27, 27));
        panel2.setPreferredSize(new java.awt.Dimension(1400, 20));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        xsuerButton.setForeground(new java.awt.Color(225, 232, 242));
        xsuerButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\Xicon.png")); // NOI18N
        xsuerButton.setToolTipText("");
        xsuerButton.setBorderPainted(false);
        xsuerButton.setContentAreaFilled(false);
        xsuerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xsuerButtonActionPerformed(evt);
            }
        });
        panel2.add(xsuerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, -1));

        getContentPane().add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1140, 40));

        leftPanel.setBackground(new java.awt.Color(26, 27, 27));
        leftPanel.setPreferredSize(new java.awt.Dimension(100, 1000));
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profile.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\whiteIcon.png")); // NOI18N
        leftPanel.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        borrowsButton.setBackground(new java.awt.Color(26, 27, 27));
        borrowsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        borrowsButton.setForeground(java.awt.Color.white);
        borrowsButton.setText("Borrows");
        borrowsButton.setActionCommand("");
        borrowsButton.setBorder(null);
        borrowsButton.setBorderPainted(false);
        borrowsButton.setContentAreaFilled(false);
        borrowsButton.setFocusPainted(false);
        borrowsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(borrowsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 110, -1));

        reservationButton.setBackground(new java.awt.Color(26, 27, 27));
        reservationButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        reservationButton.setForeground(java.awt.Color.white);
        reservationButton.setText("Reservation");
        reservationButton.setBorder(null);
        reservationButton.setBorderPainted(false);
        reservationButton.setContentAreaFilled(false);
        reservationButton.setFocusPainted(false);
        reservationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservationButtonActionPerformed(evt);
            }
        });
        leftPanel.add(reservationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        settingsButton.setBackground(new java.awt.Color(26, 27, 27));
        settingsButton.setFont(new java.awt.Font("Swis721 Lt BT", 1, 18)); // NOI18N
        settingsButton.setForeground(java.awt.Color.white);
        settingsButton.setText("Settings");
        settingsButton.setBorder(null);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setMinimumSize(new java.awt.Dimension(68, 22));
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(settingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, -1, -1));

        jSeparator1.setBackground(java.awt.Color.white);
        jSeparator1.setPreferredSize(new java.awt.Dimension(70, 10));
        leftPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 90, -1));

        historyButton.setBackground(new java.awt.Color(26, 27, 27));
        historyButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        historyButton.setForeground(java.awt.Color.white);
        historyButton.setText("History");
        historyButton.setBorderPainted(false);
        historyButton.setContentAreaFilled(false);
        historyButton.setFocusPainted(false);
        historyButton.setPreferredSize(new java.awt.Dimension(95, 22));
        historyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButtonActionPerformed(evt);
            }
        });
        leftPanel.add(historyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, 20));

        notificationsButton.setBackground(new java.awt.Color(26, 27, 27));
        notificationsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        notificationsButton.setForeground(java.awt.Color.white);
        notificationsButton.setText("Notifications");
        notificationsButton.setBorder(null);
        notificationsButton.setBorderPainted(false);
        notificationsButton.setContentAreaFilled(false);
        notificationsButton.setFocusPainted(false);
        notificationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(notificationsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, -1, -1));

        booksButton.setBackground(new java.awt.Color(26, 27, 27));
        booksButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        booksButton.setForeground(java.awt.Color.white);
        booksButton.setText("Books");
        booksButton.setActionCommand("Borrowing");
        booksButton.setBorder(null);
        booksButton.setBorderPainted(false);
        booksButton.setContentAreaFilled(false);
        booksButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        booksButton.setFocusPainted(false);
        booksButton.setMaximumSize(null);
        booksButton.setMinimumSize(null);
        booksButton.setPreferredSize(new java.awt.Dimension(60, 20));
        booksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booksButtonActionPerformed(evt);
            }
        });
        leftPanel.add(booksButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, 30));

        userName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        userName.setForeground(java.awt.Color.white);
        userName.setText("User Name");
        leftPanel.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        getContentPane().add(leftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 790));

        tabs.setBackground(new java.awt.Color(45, 4, 45));

        jPanel1.setBackground(new java.awt.Color(45, 4, 45));

        reserveButton.setBackground(new java.awt.Color(45, 4, 45));
        reserveButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        reserveButton.setForeground(new java.awt.Color(255, 153, 255));
        reserveButton.setText("Reserve");
        reserveButton.setFocusPainted(false);
        reserveButton.setVerifyInputWhenFocusTarget(false);
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveButtonActionPerformed(evt);
            }
        });

        borrowButton.setBackground(new java.awt.Color(45, 4, 45));
        borrowButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        borrowButton.setForeground(new java.awt.Color(255, 153, 255));
        borrowButton.setText("Borrow");
        borrowButton.setFocusPainted(false);
        borrowButton.setVerifyInputWhenFocusTarget(false);
        borrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowButtonActionPerformed(evt);
            }
        });

        searchBook.setBackground(new java.awt.Color(247, 247, 234));
        searchBook.setFont(new java.awt.Font("Stylus BT", 1, 14)); // NOI18N
        searchBook.setForeground(new java.awt.Color(153, 0, 153));
        searchBook.setText("Search book title");
        searchBook.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchBookFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchBookFocusLost(evt);
            }
        });
        searchBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBookActionPerformed(evt);
            }
        });

        searchBookTable.setBackground(new java.awt.Color(255, 255, 255));
        searchBookTable.setForeground(new java.awt.Color(0, 0, 0));
        searchBookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author", "ISBN", "Category", "Status"
            }
        ));
        searchBookTable.setGridColor(new java.awt.Color(204, 0, 255));
        searchBookTable.setMaximumSize(null);
        searchBookTable.setRequestFocusEnabled(false);
        searchBookTable.setSelectionBackground(new java.awt.Color(239, 209, 239));
        searchBookTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        searchBookTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        searchBookTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(searchBookTable);
        searchBookTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        searchButton.setBackground(new java.awt.Color(45, 4, 45));
        searchButton.setFont(new java.awt.Font("Stylus BT", 1, 14)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 153, 255));
        searchButton.setText("s e a r c h");
        searchButton.setFocusPainted(false);
        searchButton.setVerifyInputWhenFocusTarget(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        updateButton.setBackground(new java.awt.Color(45, 4, 45));
        updateButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 153, 255));
        updateButton.setText("UPDATE");
        updateButton.setFocusPainted(false);
        updateButton.setVerifyInputWhenFocusTarget(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(reserveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(borrowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchBook)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
                            .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(searchBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchButton)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reserveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        tabs.addTab("tab1", jPanel1);

        jPanel12.setBackground(new java.awt.Color(45, 4, 45));

        borrowsTable.setBackground(new java.awt.Color(255, 255, 255));
        borrowsTable.setForeground(new java.awt.Color(0, 0, 0));
        borrowsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Title", "Date of Borrow", "Date of Return"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        borrowsTable.setColumnSelectionAllowed(true);
        borrowsTable.setFocusable(false);
        borrowsTable.setGridColor(new java.awt.Color(232, 196, 240));
        borrowsTable.setMaximumSize(null);
        borrowsTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        borrowsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        borrowsTable.setShowGrid(true);
        borrowsTable.setShowVerticalLines(false);
        borrowsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(borrowsTable);
        borrowsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (borrowsTable.getColumnModel().getColumnCount() > 0) {
            borrowsTable.getColumnModel().getColumn(0).setPreferredWidth(400);
            borrowsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            borrowsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        returnButton.setBackground(new java.awt.Color(204, 255, 204));
        returnButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        returnButton.setForeground(new java.awt.Color(102, 0, 102));
        returnButton.setText("RETURN");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addGap(35, 35, 35)
                .addComponent(returnButton)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 59, Short.MAX_VALUE))
        );

        tabs.addTab("tab2", jPanel2);

        jPanel13.setBackground(new java.awt.Color(45, 4, 45));

        reservationTable.setBackground(new java.awt.Color(255, 255, 255));
        reservationTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        reservationTable.setForeground(new java.awt.Color(0, 0, 0));
        reservationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Title", "Date of Reservation", "Date Available"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reservationTable.setGridColor(new java.awt.Color(170, 127, 214));
        reservationTable.setSelectionBackground(new java.awt.Color(232, 210, 210));
        reservationTable.setShowGrid(true);
        reservationTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(reservationTable);
        if (reservationTable.getColumnModel().getColumnCount() > 0) {
            reservationTable.getColumnModel().getColumn(0).setPreferredWidth(400);
            reservationTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            reservationTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        cancelButton.setBackground(new java.awt.Color(204, 255, 204));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(51, 0, 51));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelButton)
                .addContainerGap(57, Short.MAX_VALUE))
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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 61, Short.MAX_VALUE))
        );

        tabs.addTab("tab3", jPanel3);

        jPanel5.setBackground(new java.awt.Color(45, 4, 45));

        historyTable.setAutoCreateRowSorter(true);
        historyTable.setBackground(new java.awt.Color(221, 221, 221));
        historyTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Title", "ISBN", "Date of Borrow", "Date of Return"
            }
        ));
        historyTable.setGridColor(new java.awt.Color(204, 153, 255));
        historyTable.setSelectionBackground(new java.awt.Color(204, 255, 204));
        historyTable.setShowGrid(true);
        jScrollPane3.setViewportView(historyTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        tabs.addTab("tab4", jPanel5);

        jPanel10.setBackground(new java.awt.Color(45, 4, 45));

        jTable2.setBackground(new java.awt.Color(255, 255, 255));
        jTable2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTable2.setForeground(new java.awt.Color(0, 0, 0));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOTIFICATIONS"
            }
        ));
        jTable2.setGridColor(new java.awt.Color(153, 51, 255));
        jTable2.setSelectionBackground(new java.awt.Color(255, 255, 204));
        jTable2.setShowGrid(true);
        jScrollPane4.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        tabs.addTab("tab5", jPanel10);

        jPanel11.setBackground(new java.awt.Color(219, 243, 219));

        helpButton.setBackground(new java.awt.Color(45, 4, 45));
        helpButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        helpButton.setForeground(new java.awt.Color(204, 255, 204));
        helpButton.setText("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        logOutButton.setBackground(new java.awt.Color(45, 4, 45));
        logOutButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logOutButton.setForeground(new java.awt.Color(204, 255, 204));
        logOutButton.setText("Log Out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Sitka Display", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(45, 4, 45));
        jLabel1.setText("Profile Information");

        name.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name.setForeground(new java.awt.Color(45, 4, 45));
        name.setText("NAME         :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);

        role.setBackground(new java.awt.Color(0, 0, 0));
        role.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        role.setForeground(new java.awt.Color(45, 4, 45));
        role.setText("ROLE           :");

        id.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        id.setForeground(new java.awt.Color(45, 4, 45));
        id.setText("ID #             :");

        email.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        email.setForeground(new java.awt.Color(45, 4, 45));
        email.setText("EMAIL         :");

        contact.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        contact.setForeground(new java.awt.Color(45, 4, 45));
        contact.setText("CONTACT # :");

        contact1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        contact1.setForeground(java.awt.Color.white);
        contact1.setText("PASSWORD        :");

        String t1 = "Jonna Bohol";
        setName.setText(t1);
        setName.setEditable(false);
        setName.setBackground(new java.awt.Color(204, 204, 204));
        setName.setForeground(new java.awt.Color(0, 0, 0));
        setName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setNameActionPerformed(evt);
            }
        });

        String t2 = "Student";
        setRole.setText(t2);
        setRole.setEditable(false);
        setRole.setBackground(new java.awt.Color(204, 204, 204));
        setRole.setForeground(new java.awt.Color(0, 0, 0));

        String t3 = "1350442";
        setId.setText(t3);
        setId.setEditable(false);
        setId.setBackground(new java.awt.Color(204, 204, 204));
        setId.setForeground(new java.awt.Color(0, 0, 0));

        String t4 = "jonnabohol43@gmail.com";
        setEmail.setText(t4);
        setEmail.setEditable(false);
        setEmail.setBackground(new java.awt.Color(204, 204, 204));
        setEmail.setForeground(new java.awt.Color(0, 0, 0));

        String t5 = "09533869211";
        setContact.setText(t5);
        setContact.setEditable(false);
        setContact.setBackground(new java.awt.Color(204, 204, 204));
        setContact.setForeground(new java.awt.Color(0, 0, 0));

        editButton.setBackground(new java.awt.Color(45, 4, 45));
        editButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editButton.setForeground(new java.awt.Color(204, 255, 204));
        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        saveButton.setBackground(new java.awt.Color(45, 4, 45));
        saveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        saveButton.setForeground(new java.awt.Color(204, 255, 204));
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
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
                                            .addComponent(editButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(saveButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
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
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(215, 215, 215)
                .addComponent(contact1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("tab6", jPanel11);

        getContentPane().add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 750, 740));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

   
    
    
    private void borrowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowsButtonActionPerformed
               tabs.setSelectedIndex(1); 
                     borrowsButton.setForeground(new Color(204,0,204));                     
                     reservationButton.setForeground(new Color(225,225,255));
                     booksButton.setForeground(new Color(225,225,255));
                     historyButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_borrowsButtonActionPerformed

    private void historyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButtonActionPerformed
                       tabs.setSelectedIndex(3);
                        historyButton.setForeground(new Color(204,0,204));
                     borrowsButton.setForeground(new Color(225,225,255));
                     reservationButton.setForeground(new Color(225,225,255));                    
                     booksButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));// TODO add your handling code here:
    }//GEN-LAST:event_historyButtonActionPerformed

    private void booksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booksButtonActionPerformed
                     tabs.setSelectedIndex(0);
                     booksButton.setForeground(new Color(204,0,204));
                     borrowsButton.setForeground(new Color(225,225,255));
                     reservationButton.setForeground(new Color(225,225,255));                   
                     historyButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
                     
                   
    }//GEN-LAST:event_booksButtonActionPerformed

    private void reservationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationButtonActionPerformed
                  tabs.setSelectedIndex(2);  
                   reservationButton.setForeground(new Color(204,0,204));
                     borrowsButton.setForeground(new Color(225,225,255));
                     booksButton.setForeground(new Color(225,225,255));                   
                     historyButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
      
   
         
        
    }//GEN-LAST:event_reservationButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
                          tabs.setSelectedIndex(5); 
                           settingsButton.setForeground(new Color(204,0,204));                    
                     reservationButton.setForeground(new Color(225,225,255));
                     borrowsButton.setForeground(new Color(225,225,255));
                     historyButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                        booksButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void notificationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationsButtonActionPerformed
                          tabs.setSelectedIndex(4);   
                           notificationsButton.setForeground(new Color(204,0,204));
                     borrowsButton.setForeground(new Color(225,225,255));
                     reservationButton.setForeground(new Color(225,225,255));
                     borrowsButton.setForeground(new Color(225,225,255));
                     historyButton.setForeground(new Color(225,225,255));
                     booksButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_notificationsButtonActionPerformed

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_helpButtonActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
          dispose();
JOptionPane.showMessageDialog(null, "Logged out successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);


        new LogIn().setVisible(true);
            
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
          setName.setEditable(true);
          setRole.setEditable(true);
          setId.setEditable(true);
          setEmail.setEditable(true);
          setContact.setEditable(true);
    }//GEN-LAST:event_editButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
                 setName.setEditable(false);
               setRole.setEditable(false);
           setId.setEditable(false);
           setEmail.setEditable(false);
             setContact.setEditable(false);
             
             
             
             
    }//GEN-LAST:event_saveButtonActionPerformed

    private void searchBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBookActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBookActionPerformed

    private void setNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setNameActionPerformed
                   
        
        
    }//GEN-LAST:event_setNameActionPerformed

    private void xsuerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xsuerButtonActionPerformed
                        dispose();      
    }//GEN-LAST:event_xsuerButtonActionPerformed

    
    
    
    
    
    
    
    
    
    
     
    
  // Function to add data from borrowsTable to the "borrows" database table
private void addDataToReservationTable() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        if (connection != null) {
            // Iterate over the rows of borrowsTable
            DefaultTableModel reservationTableModel = (DefaultTableModel) reservationTable.getModel();
            int rowCount = reservationTableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                // Get data from each row of borrowsTable
                String title = (String) reservationTableModel.getValueAt(i, 0);
                String dor = (String) reservationTableModel.getValueAt(i, 1);
                String dort = (String) reservationTableModel.getValueAt(i, 2);

                try {
                    // Prepare and execute a query to insert the data into the "borrows" table
                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO reservation (title, dor, dort) VALUES (?, ?, ?)");
                    insertStatement.setString(1, title);
                    insertStatement.setString(2, dor);
                    insertStatement.setString(3, dort);
                    insertStatement.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // Close the connection after all operations are done
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    
    
    
    
private void populateReservationTableFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            // Prepare and execute a query to retrieve all rows from the "borrows" table
            PreparedStatement statement = connection.prepareStatement("SELECT title, dor, dort FROM reservation");
            ResultSet resultSet = statement.executeQuery();
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) reservationTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to borrowsTable
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String dor = resultSet.getString("dor");
                String dort = resultSet.getString("dort");
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{title, dor, dort});
            }
            
            // Close the connection
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
}





    
    
    
    private void reserveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserveButtonActionPerformed
    
      updateButton.setForeground(new Color(225,153,255));
    borrowButton.setForeground(new Color(225, 225, 255));
    reserveButton.setForeground(new Color(225, 153, 255));
    searchButton.setForeground(new Color(225, 153, 255));

 try {
    // Get the selected rows from the searchBookTable
    int[] selectedRows = searchBookTable.getSelectedRows();
    
    // Establish a database connection
    dbConnection con = new dbConnection();
    Connection connection = con.getConnection();
    
    if (connection != null) {
        // Iterate over the selected rows in searchBookTable
        for (int rowIndex : selectedRows) {
            // Get the data from column 0 of the selected row in searchBookTable
            String title = (String) searchBookTable.getValueAt(rowIndex, 0);
            
            try {
                // Prepare and execute a query to check the status in the "books" table
                PreparedStatement statusStatement = connection.prepareStatement("SELECT status FROM books WHERE Title = ?");
                statusStatement.setString(1, title);
                ResultSet statusResult = statusStatement.executeQuery();
                
                // Check if the book is available
                if (statusResult.next()) {
                    String status = statusResult.getString("status");
                    if (status.equals("Reserved")  ) {
                        JOptionPane.showMessageDialog(this, "Only 1 reservation is allowed.", "Error", JOptionPane.ERROR_MESSAGE);
                        continue; // Skip to the next selected book
                    }
                    
                     if (status.equals("NotAvailable")  ) {
                        JOptionPane.showMessageDialog(this, "This book is already borrowed", "Error", JOptionPane.ERROR_MESSAGE);
                        continue; // Skip to the next selected book
                    }
                }
                
                // Prepare and execute a query to update the status in the "books" table
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE books SET status = 'Reserved' WHERE Title = ?");
                updateStatement.setString(1, title);
                updateStatement.executeUpdate();
                
                // If the status is updated successfully, add the data to borrowsTable
                DefaultTableModel borrowsTableModel = (DefaultTableModel) reservationTable.getModel();
                Object[] rowData = new Object[3]; // Three columns
                rowData[0] = title;
                rowData[1] = LocalDate.now().toString(); // Current date
                rowData[2] = LocalDate.now().plusDays(7).toString(); // Date after 7 days
                borrowsTableModel.addRow(rowData);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        
    } else {
        JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
    }
} catch (SQLException ex) {
    Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
}

// Clear the selection and update searchBookTable
searchBookTable.clearSelection();
DefaultTableModel model = (DefaultTableModel) searchBookTable.getModel();
model.setRowCount(0);
dbData();

addDataToReservationTable();
    
    }//GEN-LAST:event_reserveButtonActionPerformed

    
    
    
    
    
    
    
  // Function to add data from borrowsTable to the "borrows" database table
private void addDataToBorrowsTable() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        if (connection != null) {
            // Iterate over the rows of borrowsTable
            DefaultTableModel borrowsTableModel = (DefaultTableModel) borrowsTable.getModel();
            int rowCount = borrowsTableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                // Get data from each row of borrowsTable
                String title = (String) borrowsTableModel.getValueAt(i, 0);
                String dob = (String) borrowsTableModel.getValueAt(i, 1);
                String dor = (String) borrowsTableModel.getValueAt(i, 2);

                try {
                    // Prepare and execute a query to insert the data into the "borrows" table
                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO borrows (title, dob, dor) VALUES (?, ?, ?)");
                    insertStatement.setString(1, title);
                    insertStatement.setString(2, dob);
                    insertStatement.setString(3, dor);
                    insertStatement.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // Close the connection after all operations are done
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    



private void populateBorrowsTableFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            // Prepare and execute a query to retrieve all rows from the "borrows" table
            PreparedStatement statement = connection.prepareStatement("SELECT title, dob, dor FROM borrows");
            ResultSet resultSet = statement.executeQuery();
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) borrowsTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to borrowsTable
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String dob = resultSet.getString("dob");
                String dor = resultSet.getString("dor");
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{title, dob, dor});
            }
            
            // Close the connection
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    
    
    
    
    private void borrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowButtonActionPerformed
       
        updateButton.setForeground(new Color(225,153,255));         
          borrowButton.setForeground(new Color(225, 225, 255));
    reserveButton.setForeground(new Color(225, 153, 255));
    searchButton.setForeground(new Color(225, 153, 255));

     int[] selectedRows = searchBookTable.getSelectedRows();
    
 
     try {
    // Establish a database connection
    dbConnection con = new dbConnection();
    Connection connection = con.getConnection();
    
    if (connection != null) {
        // Iterate over the selected rows in searchBookTable
        for (int rowIndex : selectedRows) {
            // Get the data from column 0 of the selected row in searchBookTable
            String title = (String) searchBookTable.getValueAt(rowIndex, 0);
            
            try {
                // Prepare and execute a query to check the status in the "books" table
                PreparedStatement statusStatement = connection.prepareStatement("SELECT status FROM books WHERE Title = ?");
                statusStatement.setString(1, title);
                ResultSet statusResult = statusStatement.executeQuery();
                
                // Check if the book is available
                if (statusResult.next()) {
                    String status = statusResult.getString("status");
                    if (status.equals("NotAvailable")) {
                        JOptionPane.showMessageDialog(this, "The book \"" + title + "\" is already borrowed.", "Error", JOptionPane.ERROR_MESSAGE);
                        continue; // Skip to the next selected book
                    }
                    
                    if (status.equals("Reserved")) {
                        JOptionPane.showMessageDialog(this, "The book \"" + title + "\" is already reserved.", "Error", JOptionPane.ERROR_MESSAGE);
                        continue; // Skip to the next selected book
                    }
                }
                
                
                
                // Prepare and execute a query to update the status in the "books" table
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE books SET status = 'NotAvailable' WHERE Title = ?");
                updateStatement.setString(1, title);
                updateStatement.executeUpdate();
                
                // If the status is updated successfully, add the data to borrowsTable
                DefaultTableModel borrowsTableModel = (DefaultTableModel) borrowsTable.getModel();
                Object[] rowData = new Object[3]; // Three columns
                rowData[0] = title;
                rowData[1] = LocalDate.now().toString(); // Current date
                rowData[2] = LocalDate.now().plusDays(7).toString(); // Date after 7 days
                borrowsTableModel.addRow(rowData);
                
                
                
                
           
                
                
                
                
                
                
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        // Close the connection after all operations are done
        connection.close();
    } else {
        JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
    }
} catch (SQLException ex) {
    Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
}

// Clear the selection and update searchBookTable
searchBookTable.clearSelection();
DefaultTableModel model = (DefaultTableModel) searchBookTable.getModel();
model.setRowCount(0);
dbData();
  
  
  
addDataToBorrowsTable();

    
    }//GEN-LAST:event_borrowButtonActionPerformed

    private void searchBookFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBookFocusGained
                            // set placeholder search a book titile here
                            
                    if (searchBook.getText().equals("Search book title"))   {
                        searchBook.setText("");
                        searchBook.setForeground(BLACK);
                    }     
    }//GEN-LAST:event_searchBookFocusGained

    private void searchBookFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBookFocusLost
           // in completion with placeholder
           
            if (searchBook.getText().equals(""))   {
                        searchBook.setText("Search book title");
                        searchBook.setForeground(BLACK);
                    }     
                                      

           
           
           
    }//GEN-LAST:event_searchBookFocusLost

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
                    
                     reserveButton.setForeground(new Color(225,153,255));
                     borrowButton.setForeground(new Color(225,153,255));
                     searchButton.setForeground(new Color(225,225,255));  
                      updateButton.setForeground(new Color(225,153,255));
                        
       
           String searchText = searchBook.getText().trim(); // Get the text entered in the searchBook field

    // If the search text is empty, do nothing
    if (searchText.isEmpty()) {
        return;
    }

    DefaultTableModel model = (DefaultTableModel) searchBookTable.getModel();

    // Iterate over each row in the searchBookTable
    for (int i = 0; i < model.getRowCount(); i++) {
        // Iterate over each column in the current row
        for (int j = 0; j < model.getColumnCount(); j++) {
            Object cellValue = model.getValueAt(i, j);
            // Check if the cell value contains the search text
            if (cellValue != null && cellValue.toString().toLowerCase().contains(searchText.toLowerCase())) {
                // Move the row to the top (insert it at index 0)
                Object[] rowData = new Object[model.getColumnCount()];
                for (int k = 0; k < model.getColumnCount(); k++) {
                    rowData[k] = model.getValueAt(i, k);
                }
                model.removeRow(i);
                model.insertRow(0, rowData);
                return; // Stop searching after finding the first match
            }
        }
    }
                     
                     
                     
                     
                     
    }//GEN-LAST:event_searchButtonActionPerformed

    
    
  
    
    private void removeBorrowsSelectedRowsFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        if (connection != null) {
            // Get the selected rows from the borrowsTable
            int[] selectedRows = borrowsTable.getSelectedRows();
            
            // Iterate over the selected rows
            for (int rowIndex : selectedRows) {
                // Get the title from the selected row
                String title = (String) borrowsTable.getValueAt(rowIndex, 0);

                try {
                    // Remove the row from the "borrows" table
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM borrows WHERE title = ?");
                    deleteStatement.setString(1, title);
                    deleteStatement.executeUpdate();

                    // Update the "status" column in the "books" table to "Available" for the corresponding title
                    PreparedStatement updateStatement = connection.prepareStatement("UPDATE books SET status = 'Available' WHERE Title = ?");
                    updateStatement.setString(1, title);
                    updateStatement.executeUpdate();
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // Close the connection after all operations are done
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // Show success message
JOptionPane.showMessageDialog(null, "Book returned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
}

    
    
    
    
    
    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
           removeBorrowsSelectedRowsFromDatabase();
             populateBorrowsTableFromDatabase();



        

    }//GEN-LAST:event_returnButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
       
        updateButton.setForeground(new Color(225,225,255));         
          borrowButton.setForeground(new Color(225, 153, 255));
    reserveButton.setForeground(new Color(225, 153, 255));
    searchButton.setForeground(new Color(225, 153, 255));
      
      DefaultTableModel model = (DefaultTableModel) searchBookTable.getModel();
                       model.setRowCount(0);
    
                              dbData();

    }//GEN-LAST:event_updateButtonActionPerformed

    
    
    
    
    
    
    
    
    
    private void removeReservationSelectedRowsFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        if (connection != null) {
            // Get the selected rows from the borrowsTable
            int[] selectedRows = reservationTable.getSelectedRows();
            
            // Iterate over the selected rows
            for (int rowIndex : selectedRows) {
                // Get the title from the selected row
                String title = (String) reservationTable.getValueAt(rowIndex, 0);

                try {
                    // Remove the row from the "borrows" table
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM reservation WHERE title = ?");
                    deleteStatement.setString(1, title);
                    deleteStatement.executeUpdate();

                    // Update the "status" column in the "books" table to "Available" for the corresponding title
                    PreparedStatement updateStatement = connection.prepareStatement("UPDATE books SET status = 'Available' WHERE Title = ?");
                    updateStatement.setString(1, title);
                    updateStatement.executeUpdate();
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
                
            }

            // Close the connection after all operations are done
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // Show success message
JOptionPane.showMessageDialog(null, "Reservation cancelled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
}

    
    
    
    
    
   
    
    
    
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        removeReservationSelectedRowsFromDatabase();
        populateReservationTableFromDatabase();
       
    }//GEN-LAST:event_cancelButtonActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton booksButton;
    private javax.swing.JButton borrowButton;
    private javax.swing.JButton borrowsButton;
    private javax.swing.JTable borrowsTable;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel contact;
    private javax.swing.JLabel contact1;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel email;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable2;
    private java.awt.Panel leftPanel;
    private javax.swing.JButton logOutButton;
    private javax.swing.JLabel name;
    private javax.swing.JButton notificationsButton;
    private java.awt.Panel panel2;
    private javax.swing.JLabel profile;
    private javax.swing.JButton reservationButton;
    private javax.swing.JTable reservationTable;
    private javax.swing.JButton reserveButton;
    private javax.swing.JButton returnButton;
    private javax.swing.JLabel role;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField searchBook;
    private javax.swing.JTable searchBookTable;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField setContact;
    private javax.swing.JTextField setEmail;
    private javax.swing.JTextField setId;
    private javax.swing.JTextField setName;
    private javax.swing.JTextField setRole;
    private javax.swing.JButton settingsButton;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel userName;
    private javax.swing.JButton xsuerButton;
    // End of variables declaration//GEN-END:variables

}