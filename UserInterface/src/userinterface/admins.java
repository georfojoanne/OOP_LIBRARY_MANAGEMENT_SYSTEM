
package userinterface;


import static java.awt.Color.BLACK;
import static java.awt.Color.CYAN;
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
         books();
         dashBoard();
        
        
    }
    
    
    
    private void dashBoard() {
    try {
        // Use the database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Count the number of users
            String query = "SELECT COUNT(*) AS count FROM userinfo";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                int userCount = rs.getInt("count");
                users.setText(String.valueOf(userCount));
            }
            rs.close();
            ps.close();
            
            // Count the number of students
            query = "SELECT COUNT(*) AS count FROM userinfo WHERE role = 'Student'";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                int studentCount = rs.getInt("count");
                students.setText(String.valueOf(studentCount));
            }
            rs.close();
            ps.close();
            
            // Count the number of faculties
            query = "SELECT COUNT(*) AS count FROM userinfo WHERE role = 'Faculty'";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                int facultyCount = rs.getInt("count");
                faculties.setText(String.valueOf(facultyCount));
            }
            rs.close();
            ps.close();
            
            // Count the number of books
            query = "SELECT COUNT(*) AS count FROM books";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                int bookCount = rs.getInt("count");
                books.setText(String.valueOf(bookCount));
            }
            rs.close();
            ps.close();
            
            // Count the number of books in the Mathematics category
            query = "SELECT COUNT(*) AS count FROM books WHERE Category = 'Mathematics'";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                int mathCount = rs.getInt("count");
                mcategory.setText(String.valueOf(mathCount));
            }
            rs.close();
            ps.close();
            
            // Count the number of books in the Science category
            query = "SELECT COUNT(*) AS count FROM books WHERE Category = 'Science'";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                int scienceCount = rs.getInt("count");
                scategory.setText(String.valueOf(scienceCount));
            }
            rs.close();
            ps.close();
            
            // Count the number of books in the English category
            query = "SELECT COUNT(*) AS count FROM books WHERE Category = 'English'";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                int englishCount = rs.getInt("count");
                ecategory.setText(String.valueOf(englishCount));
            }
            rs.close();
            ps.close();
            
            // Count the number of librarians
            query = "SELECT COUNT(*) AS count FROM librarian";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                int librarianCount = rs.getInt("count");
                librarians.setText(String.valueOf(librarianCount));
            }
        } catch (SQLException ex) {
            Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                // Close ResultSet and PreparedStatement
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                // Close Connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(admins.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    
    
    
    
   // Method to fetch data from the database and populate the JTable
private void books() {
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        PreparedStatement ps1 = null, ps2 = null, ps3 = null;
        ResultSet rs1 = null, rs2 = null;

        try {
            // Fetch all book titles from the books table
            String query1 = "SELECT * FROM books";
            ps1 = connection.prepareStatement(query1);
            rs1 = ps1.executeQuery();

            DefaultTableModel tblModel = (DefaultTableModel) booksTable.getModel();
            tblModel.setRowCount(0);

            while (rs1.next()) {
                String title = rs1.getString("Title");
                String author = rs1.getString("Author");
                String isbn = rs1.getString("ISBN");
                String category = rs1.getString("Category");

                // Count the number of times the book has been borrowed from the history table
                String query2 = "SELECT COUNT(*) AS borrowCount FROM history WHERE Title = ? AND Status = 'Borrowed'";
                ps2 = connection.prepareStatement(query2);
                ps2.setString(1, title);
                rs2 = ps2.executeQuery();

                int borrowCount = 0;
                if (rs2.next()) {
                    borrowCount = rs2.getInt("borrowCount");
                }

                // Update the borrows column in the books table
                String query3 = "UPDATE books SET borrows = ? WHERE Title = ?";
                ps3 = connection.prepareStatement(query3);
                ps3.setString(1, String.valueOf(borrowCount));
                ps3.setString(2, title);
                ps3.executeUpdate();

                // Add book data to the table model
                String[] bookData = {title, author, isbn, category, String.valueOf(borrowCount)};
                tblModel.addRow(bookData);
            }
        } finally {
            // Close ResultSet, PreparedStatement, and Connection in a finally block
            if (rs1 != null) {
                rs1.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (ps1 != null) {
                ps1.close();
            }
            if (ps2 != null) {
                ps2.close();
            }
            if (ps3 != null) {
                ps3.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
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

        jSeparator2 = new javax.swing.JSeparator();
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
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        remButton1 = new javax.swing.JButton();
        searchUserField = new javax.swing.JTextField();
        searchUser = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        addUser = new javax.swing.JButton();
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
        user = new javax.swing.JTextField();
        id = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        password = new javax.swing.JTextField();
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
        panel2 = new java.awt.Panel();
        numUser = new javax.swing.JLabel();
        numStudents = new javax.swing.JLabel();
        numFaculties = new javax.swing.JLabel();
        numLibrarians = new javax.swing.JLabel();
        numBooks = new javax.swing.JLabel();
        math = new javax.swing.JLabel();
        english = new javax.swing.JLabel();
        science = new javax.swing.JLabel();
        users = new javax.swing.JTextField();
        students = new javax.swing.JTextField();
        faculties = new javax.swing.JTextField();
        books = new javax.swing.JTextField();
        mcategory = new javax.swing.JTextField();
        scategory = new javax.swing.JTextField();
        ecategory = new javax.swing.JTextField();
        librarians = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        booksTable = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 730));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(930, 700));
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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 100));

        librarianButton.setBackground(new java.awt.Color(10, 29, 36));
        librarianButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setFont(new java.awt.Font("Stylus BT", 1, 22)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("DASHBOARD");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setDefaultCapable(false);
        jButton2.setFocusPainted(false);
        jButton2.setVerifyInputWhenFocusTarget(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 150, -1));

        jButton3.setFont(new java.awt.Font("Stylus BT", 1, 19)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("USER INTERFACE");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setFocusable(false);
        jButton3.setRequestFocusEnabled(false);
        jButton3.setRolloverEnabled(false);
        jButton3.setVerifyInputWhenFocusTarget(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 200, -1));

        jButton4.setFont(new java.awt.Font("Stylus BT", 1, 22)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("USER");
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setRequestFocusEnabled(false);
        jButton4.setRolloverEnabled(false);
        jButton4.setVerifyInputWhenFocusTarget(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 150, -1));

        jButton5.setFont(new java.awt.Font("Stylus BT", 1, 22)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("LIBRARIAN");
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusPainted(false);
        jButton5.setFocusable(false);
        jButton5.setRequestFocusEnabled(false);
        jButton5.setRolloverEnabled(false);
        jButton5.setVerifyInputWhenFocusTarget(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 150, -1));

        jLabel6.setFont(new java.awt.Font("Stylus BT", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("You are logged in as:");
        librarianButton.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabel7.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ADMIN");
        librarianButton.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 70, 30));
        librarianButton.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 190, 10));

        jButton7.setFont(new java.awt.Font("Stylus BT", 1, 17)); // NOI18N
        jButton7.setForeground(java.awt.Color.white);
        jButton7.setText("LIBRARIAN INTERFACE");
        jButton7.setBorderPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton7.setFocusPainted(false);
        jButton7.setFocusable(false);
        jButton7.setRequestFocusEnabled(false);
        jButton7.setRolloverEnabled(false);
        jButton7.setVerifyInputWhenFocusTarget(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 340, 270, -1));

        jButton8.setBackground(new java.awt.Color(0, 73, 73));
        jButton8.setFont(new java.awt.Font("Stylus BT", 1, 24)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 255, 255));
        jButton8.setText("LOG OUT");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        librarianButton.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, -1, -1));

        getContentPane().add(librarianButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 190, 620));

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
        jPanel5.add(remButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 540, 200, 30));

        searchUserField.setBackground(new java.awt.Color(97, 137, 137));
        searchUserField.setFont(new java.awt.Font("Stylus BT", 0, 14)); // NOI18N
        searchUserField.setForeground(new java.awt.Color(0, 255, 255));
        searchUserField.setText("Search");
        searchUserField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchUserFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchUserFieldFocusLost(evt);
            }
        });
        searchUserField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserFieldActionPerformed(evt);
            }
        });
        jPanel5.add(searchUserField, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 740, 30));

        searchUser.setBackground(new java.awt.Color(49, 98, 103));
        searchUser.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        searchUser.setForeground(new java.awt.Color(0, 255, 255));
        searchUser.setText("SEARCH");
        searchUser.setBorderPainted(false);
        searchUser.setFocusPainted(false);
        searchUser.setRequestFocusEnabled(false);
        searchUser.setRolloverEnabled(false);
        searchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserActionPerformed(evt);
            }
        });
        jPanel5.add(searchUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 590, 30));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User Name", "ID #", "Email", "Password", "Role", "Contact#"
            }
        ));
        jScrollPane6.setViewportView(userTable);

        jPanel5.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 740, 440));

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
        jPanel5.add(addUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 540, 210, 30));

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

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 720, 440));

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
        jPanel6.add(addLibrarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 539, 180, -1));

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
        jPanel6.add(removeLibrarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 540, 180, 30));

        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 710, 30));

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
        jPanel6.add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 610, 20));

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
        jPanel10.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 460, 40));

        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });
        jPanel10.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 460, 40));
        jPanel10.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 460, 40));
        jPanel10.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 460, 40));

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
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(94, 130, 130));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        numUser.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        numUser.setText("Number of Users :");
        panel2.add(numUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        numStudents.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        numStudents.setText("Number of Students :");
        panel2.add(numStudents, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        numFaculties.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        numFaculties.setText("Number of Faculties :");
        panel2.add(numFaculties, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        numLibrarians.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        numLibrarians.setText("Number of Librarians :");
        panel2.add(numLibrarians, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 180, 30));

        numBooks.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        numBooks.setText("Number of  books  :");
        panel2.add(numBooks, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, 20));

        math.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        math.setText("Mathematics category : ");
        panel2.add(math, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, -1, 20));

        english.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        english.setText("English category :");
        panel2.add(english, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        science.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        science.setText("Science category :");
        panel2.add(science, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, -1, 20));
        panel2.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 70, -1));
        panel2.add(students, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 70, -1));
        panel2.add(faculties, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 70, -1));
        panel2.add(books, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 60, 20));
        panel2.add(mcategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 60, 20));
        panel2.add(scategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 60, 20));
        panel2.add(ecategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 60, 20));
        panel2.add(librarians, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 60, 80, 30));

        panel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 130));

        booksTable.setBackground(new java.awt.Color(204, 204, 204));
        booksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author", "ISBN", "Category", "# of Borrows"
            }
        ));
        booksTable.setGridColor(new java.awt.Color(51, 255, 255));
        booksTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        booksTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(booksTable);

        panel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 720, 410));

        jButton6.setBackground(new java.awt.Color(0, 51, 51));
        jButton6.setForeground(new java.awt.Color(102, 255, 255));
        jButton6.setText("R E F R E S H");
        jButton6.setRequestFocusEnabled(false);
        jButton6.setRolloverEnabled(false);
        jButton6.setVerifyInputWhenFocusTarget(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        panel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 270, -1));

        jTabbedPane1.addTab("tab7", panel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 750, 630));

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                  jTabbedPane1.setSelectedIndex(4);    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                  
        new LogIn().setVisible(true);
                
        
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
            JOptionPane.showMessageDialog(null, "Librarian added successfully.", "Librarian Added", JOptionPane.WARNING_MESSAGE);
            
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

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed

    
    
    
    
    
    
    
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
                          jTabbedPane1.setSelectedIndex(3);             
    }//GEN-LAST:event_addLibrarianActionPerformed

    private void searchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserActionPerformed
                      
        searchUserField.requestFocus();
        
        String searchText = searchUserField.getText().trim();
                System.out.println(searchText);
    
    // Check if the search text is the placeholder
    if (searchText.isEmpty()||searchText.equals("Search")) {
        JOptionPane.showMessageDialog(this, "Please enter text to search.");
        return;
    }
    
    try {
        // Establish a connection to your database
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        // Create a SQL query to search for books
        String query = "SELECT * FROM userinfo WHERE name LIKE ? OR role LIKE ? OR email LIKE ? OR password LIKE ?  OR id LIKE ?  OR Contact LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        
        // Use the search text in the query with wildcards for advanced search
        String searchQuery = "%" + searchText + "%";
        statement.setString(1, searchQuery);
        statement.setString(2, searchQuery);
        statement.setString(3, searchQuery);
        statement.setString(4, searchQuery);
        statement.setString(5, searchQuery);
        statement.setString(6, searchQuery);

        
        
        // Print the query for debugging
        System.out.println("Executing query: " + statement.toString());
        
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
            String role  = resultSet.getString("role");
            String email = resultSet.getString("email");
            String password = resultSet.getString("Password");
            String id = resultSet.getString("id");
             String contact = resultSet.getString("Contact");
            
            
            // Add row to the table model
            model.addRow(new Object[]{name, id, email, password, role, contact});
            
            // Print each result for debugging
            System.out.println("Name: " + name + ", Role: " +  role + ", Email: " + email+ ", Password : " + password + ", Id: " + id+ ", Contact: " + contact);
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
    
    private void addUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserActionPerformed
                  jTabbedPane1.setSelectedIndex(2);      
    }//GEN-LAST:event_addUserActionPerformed

    private void searchUserFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserFieldActionPerformed
       
    }//GEN-LAST:event_searchUserFieldActionPerformed

    private void searchUserFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchUserFieldFocusGained
      // set placeholder search a book titile here

        if (searchUserField.getText().equals("Search"))   {
            searchUserField.setText("");
            searchUserField.setForeground(BLACK);
        }
    }//GEN-LAST:event_searchUserFieldFocusGained

    private void searchUserFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchUserFieldFocusLost
        // in completion with placeholder

        if (searchUserField.getText().equals(""))   {
            searchUserField.setText("Search");
            searchUserField.setForeground(CYAN);
        }
    }//GEN-LAST:event_searchUserFieldFocusLost

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      books();
      dashBoard();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
      
        new LogIn().setVisible(true);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        dispose();
        JOptionPane.showMessageDialog(null, "Logged out successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        new LogIn().setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addLibrarian;
    private javax.swing.JButton addUser;
    private javax.swing.JTextField books;
    private javax.swing.JTable booksTable;
    private javax.swing.JButton cancelLibrarian;
    private javax.swing.JButton cancelUser;
    private javax.swing.JTextField ecategory;
    private javax.swing.JTextField email;
    private javax.swing.JLabel english;
    private javax.swing.JTextField faculties;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField libContact;
    private javax.swing.JTextField libEmail;
    private javax.swing.JTextField libId;
    private javax.swing.JTextField libName;
    private javax.swing.JTextField libPassword;
    private javax.swing.JPanel librarianButton;
    private javax.swing.JTable librarianTable;
    private javax.swing.JTextField librarians;
    private javax.swing.JLabel math;
    private javax.swing.JTextField mcategory;
    private javax.swing.JLabel numBooks;
    private javax.swing.JLabel numFaculties;
    private javax.swing.JLabel numLibrarians;
    private javax.swing.JLabel numStudents;
    private javax.swing.JLabel numUser;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private javax.swing.JTextField password;
    private javax.swing.JButton remButton1;
    private javax.swing.JButton removeLibrarian;
    private javax.swing.JTextField scategory;
    private javax.swing.JLabel science;
    private javax.swing.JButton searchUser;
    private javax.swing.JTextField searchUserField;
    private javax.swing.JTextField students;
    private javax.swing.JButton updateLibrarian;
    private javax.swing.JButton updateUser;
    private javax.swing.JTextField user;
    private javax.swing.JTable userTable;
    private javax.swing.JTextField users;
    // End of variables declaration//GEN-END:variables
}
