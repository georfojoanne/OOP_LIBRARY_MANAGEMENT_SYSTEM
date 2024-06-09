package userinterface;

import static java.awt.Color.BLACK;
import java.sql.*;
import java.time.LocalDate;
import static java.awt.Color.CYAN;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;





public class userHome extends javax.swing.JFrame  {
         
    public userHome() {
       
         
        initComponents();
              
              dbData();
              
              
              
               getTextFromNameColumn();
              
               
              
              populateBorrowsTableFromDatabase();
              populateReservationTableFromDatabase();
              
              fillHistoryTableFromDatabase(userName.getText());
           
               
               retrieveUserInfo(userName.getText());
                notifs();
                enableWordWrapForAllColumns(notifsTable);
               
                setProfile();
             
                
            
           
          
    }
    
    
    
    
      private void setProfile() {
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        String user = userName.getText(); // Assuming userName is a JTextField
        
        if (connection != null) {
            try {
                // Retrieve the image from the database
                String getImageQuery = "SELECT images FROM userinfo WHERE name = ?";
                PreparedStatement getImageStatement = connection.prepareStatement(getImageQuery);
                getImageStatement.setString(1, user);
                ResultSet resultSet = getImageStatement.executeQuery();
                
                if (resultSet.next()) {
                    // Check if the image value is null
                    Blob imageBlob = resultSet.getBlob("images");
                    if (imageBlob != null) {
                        // Get the image data from the result set
                        InputStream inputStream = imageBlob.getBinaryStream();
                        
                        // Convert the image data to a BufferedImage
                        BufferedImage image = ImageIO.read(inputStream);
                        
                        // Set the image to the JLabel
                        profile.setIcon(new ImageIcon(image));
                    } 
                } 
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error setting profile picture: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    
    
    private void setFrameIcon(String imageName) {
        try {
            // Load the icon image from resources within the same package
            URL imageUrl = getClass().getResource(imageName);
            if (imageUrl == null) {
                throw new IOException("Resource not found: " + imageName);
            }
            Image icon = ImageIO.read(imageUrl);
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    public static void makePanelMovable(JFrame frame, Panel panel2) {
        final Point[] mousePoint = {null}; // Declare mousePoint as an array

        panel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePoint[0] = e.getPoint(); // Get the point relative to jPanel1
            }
        });

        panel2.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mousePoint[0] != null) {
                    Point currentLocation = frame.getLocation();
                    Point newLocation = e.getLocationOnScreen();
                    int deltaX = newLocation.x - mousePoint[0].x - panel2.getLocationOnScreen().x;
                    int deltaY = newLocation.y - mousePoint[0].y - panel2.getLocationOnScreen().y;
                    frame.setLocation(currentLocation.x + deltaX, currentLocation.y + deltaY);
                }
            }
        });
    }
    
    
    
    
    public static void enableWordWrapForAllColumns(JTable table) {
        // Create a custom cell renderer
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            private JTextArea textArea = new JTextArea();

            {
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                // Adjust the text area appearance
                textArea.setFont(table.getFont());
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Set the value to the text area
                textArea.setText(value != null ? value.toString() : "");
                // Return the text area as the cell renderer component
                return textArea;
            }
        };

        // Set the custom cell renderer for each column
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
        }
    }
    
    
    
    
    
    
    
    
    private void notifs() {
        try {
            // Establish a database connection
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();
            
            if (connection != null) {
                try {
                    // Get the name of the user
                    String name = userName.getText();
                    
                    // Prepare the SQL query to get notifications where the name matches
                    String sql = "SELECT notif FROM notifs WHERE name = ?";
                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, name);
                    
                    // Execute the query
                    ResultSet rs = pstmt.executeQuery();
                    
                    // Clear the existing rows in the notifsTable
                    DefaultTableModel model = (DefaultTableModel) notifsTable.getModel();
                    model.setRowCount(0);
                    
                    // Iterate through the result set and add data to the notifsTable
                    while (rs.next()) {
                        String notif = rs.getString("notif");
                        model.addRow(new Object[]{notif});
                    }
                    
                    // Close the ResultSet and PreparedStatement
                    rs.close();
                    pstmt.close();
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    // Close the database connection
                    try {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }   } catch (SQLException ex) {
            Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
        }
}

    
    
    
    
    
    
    
   private void requestReturn() {
     try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        // Get the selected rows from the borrowsTable
        int[] selectedRows = borrowsTable.getSelectedRows();
        
        
        
    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(null, "Please select a book.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
    }
 

        // Get the username
        String name = userName.getText();

        try {
            // Prepare the SQL insert statement for returns table
            String sqlReturns = "INSERT INTO returns (title, dob, dor, name) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmtReturns = connection.prepareStatement(sqlReturns);

            // Prepare the SQL insert statement for history table
            String sqlHistory = "INSERT INTO history (title, status, date, name) VALUES (?, ?, CURDATE(), ?)";
            PreparedStatement pstmtHistory = connection.prepareStatement(sqlHistory);

            // Iterate through the selected rows
            for (int row : selectedRows) {
                // Get data from the borrowsTable columns
                String title = (String) borrowsTable.getValueAt(row, 0);
                String dob = (String) borrowsTable.getValueAt(row, 1);
                String dor = (String) borrowsTable.getValueAt(row, 2);

                // Set the parameters for the SQL statements
                pstmtReturns.setString(1, title);
                pstmtReturns.setString(2, dob);
                pstmtReturns.setString(3, dor);
                pstmtReturns.setString(4, name);

                pstmtHistory.setString(1, title);
                pstmtHistory.setString(2, "Request Return"); // Set status to "Request Return"
                pstmtHistory.setString(3, name);

                // Execute the insert statements
                pstmtReturns.executeUpdate();
                pstmtHistory.executeUpdate();
            }

            // Close the PreparedStatements
            pstmtReturns.close();
            pstmtHistory.close();

            // Optionally, close the connection
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
}
 
    
    
    
    
    
    
 
    
    
    
// Method to fill out the rows in the historyTable from the database

    // Method to fill out the rows in the historyTable from the database
    public void fillHistoryTableFromDatabase(String name) {
        try {
            // Establish a database connection
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();

            if (connection != null) {
                // Prepare and execute a query to retrieve rows from the "history" table where name matches
                PreparedStatement statement = connection.prepareStatement("SELECT title, date, status FROM history WHERE name = ?");
                statement.setString(1, name); // Set the parameter for the name
                ResultSet resultSet = statement.executeQuery();

                // Create a custom table model for historyTable
                DefaultTableModel model = new DefaultTableModel(
                        new Object[]{"Title", "Date", "Status"}, 0); // 0 for initial row count

                // Add fetched data to historyTable
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String date = resultSet.getString("date");
                    String status = resultSet.getString("status");

                    // Insert each row at index 0 to reverse the order
                    model.insertRow(0, new Object[]{title, date, status});
                }

                // Set the model to historyTable
                historyTable.setModel(model);

                // Close the connection
                connection.close();
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    
    
    
    public void retrieveUserInfo(String name) {
        try {
            // Get the database connection
            dbConnection conn = new dbConnection();
            Connection connection = conn.getConnection();
            
         
            
            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return;
            }
            
           
            
            String sql = "SELECT * FROM userinfo WHERE name = ?";
            
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();
                
                // If a row with the given name exists
                if (rs.next()) {
                     String Name = rs.getString("name");
                    String role = rs.getString("role");
                    String id = rs.getString("id");
                    String email = rs.getString("email");
                    String contact = rs.getString("Contact");
                    
                    // Assuming setRole, setId, setEmail, and setContact are methods to set text fields
                    setName.setText(Name);
                    setRole.setText(role); // Assuming setRole is a JTextField
                    setId.setText(id); // Assuming setId is a JTextField
                    setEmail.setText(email); // Assuming setEmail is a JTextField
                    setContact.setText(contact); // Assuming setContact is a JTextField
                } else {
                    // Handle case where no row with the given name exists
                    System.out.println("No user found with the name: " + name);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving user info: " + e.getMessage());
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
   
// Method to set the username to the actual username and update the "number" column to '1' for all rows
public void getTextFromNameColumn() {
    String name = null;

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
        // Get the database connection
        dbConnection conn = new dbConnection();
        connection = conn.getConnection();

        // Create a statement
        statement = connection.createStatement();

        // Execute the query to retrieve the text from the "name" column where "number" is equal to '2'
        resultSet = statement.executeQuery("SELECT name FROM userinfo WHERE number = '2'");

        // If a row with "number" equal to '2' is found, retrieve the text from the "name" column
        if (resultSet.next()) {
            name = resultSet.getString("name");
              
        } else {
            System.out.println("No row found with 'number' equal to '2'.");
        }
        
        // Update the "number" to '1' for all rows
        String updateQuery = "UPDATE userinfo SET number = '1' WHERE number = '2'";
        
        
        int rowsUpdated = statement.executeUpdate(updateQuery);
        

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  userName.setText(name);
    
}


    
    
    
    

// Method to fetch data from the database and populate the JTable
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
         tblModel.setRowCount(0);

        while (rs.next()) {
            String Title = rs.getString("Title");
            String Author = rs.getString("Author");
            String ISBN = rs.getString("ISBN");
            String Category = rs.getString("Category");
               String Status = rs.getString("Status");
                String nr = rs.getString("nr");
                 String nb = rs.getString("nb");

            String[] bookData = {Title, Author, ISBN, Category,Status,nr,nb };
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
        jButton2 = new javax.swing.JButton();
        leftPanel = new java.awt.Panel();
        borrowsButton = new javax.swing.JButton();
        editProfile = new javax.swing.JButton();
        profile = new javax.swing.JLabel();
        reservationButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        historyButton = new javax.swing.JButton();
        notificationsButton = new javax.swing.JButton();
        booksButton = new javax.swing.JButton();
        userName = new javax.swing.JLabel();
        logOutButton = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        books = new javax.swing.JPanel();
        borrowButton = new javax.swing.JButton();
        searchBook = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        searchBookTable = new javax.swing.JTable();
        searchButton = new javax.swing.JButton();
        reserveButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        borrows = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        borrowsTable = new javax.swing.JTable();
        returnButton = new javax.swing.JButton();
        reservations = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reservationTable = new javax.swing.JTable();
        cancelButton = new javax.swing.JButton();
        history = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        notifications = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        notifsTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        settings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        role = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        contact = new javax.swing.JLabel();
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
        setMinimumSize(new java.awt.Dimension(900, 700));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(900, 700));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(10, 29, 36));
        panel2.setPreferredSize(new java.awt.Dimension(1400, 20));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        xsuerButton.setForeground(new java.awt.Color(225, 232, 242));
        xsuerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/Xbang.png"))); // NOI18N
        xsuerButton.setToolTipText("");
        xsuerButton.setBorderPainted(false);
        xsuerButton.setContentAreaFilled(false);
        xsuerButton.setFocusable(false);
        xsuerButton.setRequestFocusEnabled(false);
        xsuerButton.setRolloverEnabled(false);
        xsuerButton.setVerifyInputWhenFocusTarget(false);
        xsuerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xsuerButtonActionPerformed(evt);
            }
        });
        panel2.add(xsuerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 10, 50, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/minimize.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jButton2.setFocusable(false);
        jButton2.setRequestFocusEnabled(false);
        jButton2.setRolloverEnabled(false);
        jButton2.setVerifyInputWhenFocusTarget(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, -1));

        makePanelMovable(this, panel2);

        getContentPane().add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 690, 60));

        leftPanel.setBackground(new java.awt.Color(10, 29, 36));
        leftPanel.setPreferredSize(new java.awt.Dimension(100, 1000));
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        setFrameIcon("libIcon.png");

        setTitle("Lib.IT");

        borrowsButton.setBackground(new java.awt.Color(26, 27, 27));
        borrowsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        borrowsButton.setForeground(java.awt.Color.white);
        borrowsButton.setText("Borrows");
        borrowsButton.setActionCommand("");
        borrowsButton.setBorder(null);
        borrowsButton.setBorderPainted(false);
        borrowsButton.setContentAreaFilled(false);
        borrowsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        borrowsButton.setFocusPainted(false);
        borrowsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(borrowsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 110, -1));

        editProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/edit.png"))); // NOI18N
        editProfile.setBorderPainted(false);
        editProfile.setContentAreaFilled(false);
        editProfile.setFocusPainted(false);
        editProfile.setFocusable(false);
        editProfile.setRequestFocusEnabled(false);
        editProfile.setRolloverEnabled(false);
        editProfile.setVerifyInputWhenFocusTarget(false);
        editProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileActionPerformed(evt);
            }
        });
        leftPanel.add(editProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 110, 70));

        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/whiteIcon.png"))); // NOI18N
        profile.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 3, true));
        leftPanel.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 66, 110, 110));

        reservationButton.setBackground(new java.awt.Color(26, 27, 27));
        reservationButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        reservationButton.setForeground(java.awt.Color.white);
        reservationButton.setText("Reservation");
        reservationButton.setBorder(null);
        reservationButton.setBorderPainted(false);
        reservationButton.setContentAreaFilled(false);
        reservationButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reservationButton.setFocusPainted(false);
        reservationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservationButtonActionPerformed(evt);
            }
        });
        leftPanel.add(reservationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, -1, -1));

        settingsButton.setBackground(new java.awt.Color(26, 27, 27));
        settingsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        settingsButton.setForeground(java.awt.Color.white);
        settingsButton.setText("Settings");
        settingsButton.setBorder(null);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        settingsButton.setFocusPainted(false);
        settingsButton.setMinimumSize(new java.awt.Dimension(68, 22));
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(settingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 540, -1, -1));

        jSeparator1.setBackground(java.awt.Color.white);
        jSeparator1.setPreferredSize(new java.awt.Dimension(70, 10));
        leftPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 90, 10));

        historyButton.setBackground(new java.awt.Color(26, 27, 27));
        historyButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        historyButton.setForeground(java.awt.Color.white);
        historyButton.setText("History");
        historyButton.setBorderPainted(false);
        historyButton.setContentAreaFilled(false);
        historyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        historyButton.setFocusPainted(false);
        historyButton.setPreferredSize(new java.awt.Dimension(95, 22));
        historyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButtonActionPerformed(evt);
            }
        });
        leftPanel.add(historyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, 20));

        notificationsButton.setBackground(new java.awt.Color(26, 27, 27));
        notificationsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        notificationsButton.setForeground(java.awt.Color.white);
        notificationsButton.setText("Notifications");
        notificationsButton.setBorder(null);
        notificationsButton.setBorderPainted(false);
        notificationsButton.setContentAreaFilled(false);
        notificationsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificationsButton.setFocusPainted(false);
        notificationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationsButtonActionPerformed(evt);
            }
        });
        leftPanel.add(notificationsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, -1, -1));

        booksButton.setBackground(new java.awt.Color(26, 27, 27));
        booksButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        booksButton.setForeground(java.awt.Color.white);
        booksButton.setText("Books");
        booksButton.setActionCommand("Borrowing");
        booksButton.setBorder(null);
        booksButton.setBorderPainted(false);
        booksButton.setContentAreaFilled(false);
        booksButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        booksButton.setFocusPainted(false);
        booksButton.setMaximumSize(null);
        booksButton.setMinimumSize(null);
        booksButton.setPreferredSize(new java.awt.Dimension(60, 20));
        booksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booksButtonActionPerformed(evt);
            }
        });
        leftPanel.add(booksButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, -1, 30));

        userName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        userName.setForeground(java.awt.Color.white);
        userName.setText("User Name");
        leftPanel.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, -1, -1));

        logOutButton.setBackground(new java.awt.Color(49, 98, 103));
        logOutButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        logOutButton.setForeground(new java.awt.Color(0, 255, 255));
        logOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/log out.png"))); // NOI18N
        logOutButton.setText("Log Out");
        logOutButton.setBorderPainted(false);
        logOutButton.setContentAreaFilled(false);
        logOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutButton.setFocusPainted(false);
        logOutButton.setRequestFocusEnabled(false);
        logOutButton.setRolloverEnabled(false);
        logOutButton.setVerifyInputWhenFocusTarget(false);
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });
        leftPanel.add(logOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 180, -1));

        getContentPane().add(leftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 800));

        tabs.setBackground(new java.awt.Color(45, 4, 45));

        books.setBackground(new java.awt.Color(28, 52, 62));

        borrowButton.setBackground(new java.awt.Color(10, 29, 36));
        borrowButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        borrowButton.setForeground(new java.awt.Color(0, 255, 255));
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
        searchBook.setForeground(new java.awt.Color(0, 142, 142));
        searchBook.setText("Search");
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

        searchBookTable.setBackground(new java.awt.Color(221, 221, 221));
        searchBookTable.setForeground(new java.awt.Color(0, 0, 0));
        searchBookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author", "ISBN", "Category", "Status", "#of Reservations", "#ofBooks"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchBookTable.setGridColor(new java.awt.Color(0, 255, 255));
        searchBookTable.setMaximumSize(null);
        searchBookTable.setRequestFocusEnabled(false);
        searchBookTable.setSelectionBackground(new java.awt.Color(191, 232, 232));
        searchBookTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        searchBookTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        searchBookTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(searchBookTable);
        if (searchBookTable.getColumnModel().getColumnCount() > 0) {
            searchBookTable.getColumnModel().getColumn(0).setPreferredWidth(200);
            searchBookTable.getColumnModel().getColumn(2).setPreferredWidth(60);
            searchBookTable.getColumnModel().getColumn(3).setPreferredWidth(40);
            searchBookTable.getColumnModel().getColumn(4).setPreferredWidth(50);
            searchBookTable.getColumnModel().getColumn(5).setPreferredWidth(30);
            searchBookTable.getColumnModel().getColumn(6).setPreferredWidth(40);
        }

        searchButton.setBackground(new java.awt.Color(49, 98, 103));
        searchButton.setFont(new java.awt.Font("Stylus BT", 1, 14)); // NOI18N
        searchButton.setForeground(new java.awt.Color(0, 255, 255));
        searchButton.setText("s e a r c h");
        searchButton.setFocusPainted(false);
        searchButton.setRequestFocusEnabled(false);
        searchButton.setRolloverEnabled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        reserveButton.setBackground(new java.awt.Color(10, 29, 36));
        reserveButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        reserveButton.setForeground(new java.awt.Color(0, 255, 255));
        reserveButton.setText("Reserve");
        reserveButton.setFocusPainted(false);
        reserveButton.setVerifyInputWhenFocusTarget(false);
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveButtonActionPerformed(evt);
            }
        });

        updateButton.setBackground(new java.awt.Color(10, 29, 36));
        updateButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        updateButton.setForeground(new java.awt.Color(0, 255, 255));
        updateButton.setText("Update");
        updateButton.setPreferredSize(new java.awt.Dimension(83, 28));
        updateButton.setRequestFocusEnabled(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout booksLayout = new javax.swing.GroupLayout(books);
        books.setLayout(booksLayout);
        booksLayout.setHorizontalGroup(
            booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(booksLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(booksLayout.createSequentialGroup()
                        .addGroup(booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(searchBook, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(39, Short.MAX_VALUE))
                    .addGroup(booksLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(reserveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(borrowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
        );
        booksLayout.setVerticalGroup(
            booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(booksLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(searchBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchButton)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reserveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        tabs.addTab("tab1", books);

        jPanel12.setBackground(new java.awt.Color(28, 52, 62));

        borrowsTable.setAutoCreateRowSorter(true);
        borrowsTable.setBackground(new java.awt.Color(221, 221, 221));
        borrowsTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
        borrowsTable.setFocusable(false);
        borrowsTable.setGridColor(new java.awt.Color(0, 255, 255));
        borrowsTable.setMaximumSize(null);
        borrowsTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        borrowsTable.setSelectionForeground(new java.awt.Color(6, 43, 43));
        borrowsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        borrowsTable.setShowGrid(true);
        borrowsTable.setShowVerticalLines(false);
        borrowsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(borrowsTable);
        if (borrowsTable.getColumnModel().getColumnCount() > 0) {
            borrowsTable.getColumnModel().getColumn(0).setPreferredWidth(400);
            borrowsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            borrowsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        returnButton.setBackground(new java.awt.Color(10, 29, 36));
        returnButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        returnButton.setForeground(new java.awt.Color(0, 255, 255));
        returnButton.setText("RETURN");
        returnButton.setRequestFocusEnabled(false);
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
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(returnButton)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout borrowsLayout = new javax.swing.GroupLayout(borrows);
        borrows.setLayout(borrowsLayout);
        borrowsLayout.setHorizontalGroup(
            borrowsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrowsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        borrowsLayout.setVerticalGroup(
            borrowsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrowsLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("tab2", borrows);

        jPanel13.setBackground(new java.awt.Color(28, 52, 62));

        reservationTable.setBackground(new java.awt.Color(221, 221, 221));
        reservationTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        reservationTable.setForeground(new java.awt.Color(0, 0, 0));
        reservationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Title", "Date of Reservation", "Reservation #"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reservationTable.setGridColor(new java.awt.Color(0, 255, 255));
        reservationTable.setSelectionBackground(new java.awt.Color(202, 248, 248));
        reservationTable.setSelectionForeground(new java.awt.Color(4, 34, 34));
        reservationTable.setShowGrid(true);
        reservationTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(reservationTable);
        if (reservationTable.getColumnModel().getColumnCount() > 0) {
            reservationTable.getColumnModel().getColumn(0).setPreferredWidth(400);
            reservationTable.getColumnModel().getColumn(1).setPreferredWidth(120);
            reservationTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        cancelButton.setBackground(new java.awt.Color(10, 29, 36));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(0, 255, 255));
        cancelButton.setText("Cancel");
        cancelButton.setRequestFocusEnabled(false);
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
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(214, 214, 214))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout reservationsLayout = new javax.swing.GroupLayout(reservations);
        reservations.setLayout(reservationsLayout);
        reservationsLayout.setHorizontalGroup(
            reservationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        reservationsLayout.setVerticalGroup(
            reservationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservationsLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("tab3", reservations);

        history.setBackground(new java.awt.Color(28, 52, 62));

        historyTable.setBackground(new java.awt.Color(221, 221, 221));
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Title", "Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        historyTable.setGridColor(new java.awt.Color(51, 255, 255));
        historyTable.setSelectionBackground(new java.awt.Color(201, 244, 244));
        historyTable.setSelectionForeground(new java.awt.Color(7, 30, 30));
        historyTable.setShowGrid(true);
        historyTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(historyTable);
        if (historyTable.getColumnModel().getColumnCount() > 0) {
            historyTable.getColumnModel().getColumn(0).setPreferredWidth(350);
            historyTable.getColumnModel().getColumn(1).setPreferredWidth(30);
            historyTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        javax.swing.GroupLayout historyLayout = new javax.swing.GroupLayout(history);
        history.setLayout(historyLayout);
        historyLayout.setHorizontalGroup(
            historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        historyLayout.setVerticalGroup(
            historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        tabs.addTab("tab4", history);

        notifications.setBackground(new java.awt.Color(28, 52, 62));

        notifsTable.setBackground(new java.awt.Color(221, 221, 221));
        notifsTable.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        notifsTable.setForeground(new java.awt.Color(0, 0, 0));
        notifsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOTIFICATIONS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        notifsTable.setGridColor(new java.awt.Color(0, 153, 153));
        notifsTable.setRowHeight(45);
        notifsTable.setSelectionBackground(new java.awt.Color(255, 255, 204));
        notifsTable.setShowGrid(true);
        jScrollPane4.setViewportView(notifsTable);

        jButton1.setBackground(new java.awt.Color(10, 29, 36));
        jButton1.setForeground(new java.awt.Color(102, 255, 255));
        jButton1.setText("R E F R E S H");
        jButton1.setRequestFocusEnabled(false);
        jButton1.setRolloverEnabled(false);
        jButton1.setVerifyInputWhenFocusTarget(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout notificationsLayout = new javax.swing.GroupLayout(notifications);
        notifications.setLayout(notificationsLayout);
        notificationsLayout.setHorizontalGroup(
            notificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationsLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, notificationsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(204, 204, 204))
        );
        notificationsLayout.setVerticalGroup(
            notificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        tabs.addTab("tab5", notifications);

        settings.setBackground(new java.awt.Color(28, 52, 62));
        settings.setPreferredSize(new java.awt.Dimension(800, 1000));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Stylus BT", 1, 48)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Profile Information");

        name.setBackground(new java.awt.Color(225, 225, 225));
        name.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name.setForeground(java.awt.Color.white);
        name.setText("NAME         :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);

        role.setBackground(new java.awt.Color(221, 221, 221));
        role.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        role.setForeground(java.awt.Color.white);
        role.setText("ROLE           :");

        id.setBackground(new java.awt.Color(225, 225, 225));
        id.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        id.setForeground(java.awt.Color.white);
        id.setText("ID #             :");

        email.setBackground(new java.awt.Color(225, 225, 225));
        email.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        email.setForeground(java.awt.Color.white);
        email.setText("EMAIL         :");

        contact.setBackground(new java.awt.Color(225, 225, 225));
        contact.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        contact.setForeground(java.awt.Color.white);
        contact.setText("CONTACT # :");

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

        editButton.setBackground(new java.awt.Color(10, 29, 36));
        editButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editButton.setForeground(new java.awt.Color(0, 255, 255));
        editButton.setText("Edit");
        editButton.setRequestFocusEnabled(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        saveButton.setBackground(new java.awt.Color(10, 29, 36));
        saveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        saveButton.setForeground(new java.awt.Color(0, 255, 255));
        saveButton.setText("Save");
        saveButton.setRequestFocusEnabled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingsLayout = new javax.swing.GroupLayout(settings);
        settings.setLayout(settingsLayout);
        settingsLayout.setHorizontalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 260, Short.MAX_VALUE))
                    .addGroup(settingsLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 498, Short.MAX_VALUE))
                    .addGroup(settingsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(role, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(setRole, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(setName, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(setId, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(setEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(setContact, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(saveButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(editButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        settingsLayout.setVerticalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(role, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("tab6", settings);

        getContentPane().add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 720, 760));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

   
    
    
    private void borrowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowsButtonActionPerformed
               tabs.setSelectedIndex(1); 
                     borrowsButton.setForeground(new Color(0,225,225));                     
                     reservationButton.setForeground(new Color(225,225,255));
                     booksButton.setForeground(new Color(225,225,255));
                     historyButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
                     
                     
                     
                     
                   
    }//GEN-LAST:event_borrowsButtonActionPerformed

    private void historyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButtonActionPerformed
                       tabs.setSelectedIndex(3);
                        historyButton.setForeground(new Color(0,225,225));
                     borrowsButton.setForeground(new Color(225,225,255));
                     reservationButton.setForeground(new Color(225,225,255));                    
                     booksButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
                  
    }//GEN-LAST:event_historyButtonActionPerformed

    private void booksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booksButtonActionPerformed
                     tabs.setSelectedIndex(0);
                     booksButton.setForeground(new Color(0,225,225));
                     borrowsButton.setForeground(new Color(225,225,255));
                     reservationButton.setForeground(new Color(225,225,255));                   
                     historyButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
                  
                     
                   
    }//GEN-LAST:event_booksButtonActionPerformed

    private void reservationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationButtonActionPerformed
                  tabs.setSelectedIndex(2);  
                   reservationButton.setForeground(new Color(0,225,225));
                     borrowsButton.setForeground(new Color(225,225,255));
                     booksButton.setForeground(new Color(225,225,255));                   
                     historyButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
                   
      
   
         
        
    }//GEN-LAST:event_reservationButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
                          tabs.setSelectedIndex(5); 
                           settingsButton.setForeground(new Color(0,225,225));                    
                     reservationButton.setForeground(new Color(225,225,255));
                     borrowsButton.setForeground(new Color(225,225,255));
                     historyButton.setForeground(new Color(225,225,255));
                     notificationsButton.setForeground(new Color(225,225,255));
                        booksButton.setForeground(new Color(225,225,255));
                      
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void notificationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationsButtonActionPerformed
                        
        tabs.setSelectedIndex(4);   
                           notificationsButton.setForeground(new Color(0,225,225));
                     borrowsButton.setForeground(new Color(225,225,255));
                     reservationButton.setForeground(new Color(225,225,255));
                     borrowsButton.setForeground(new Color(225,225,255));
                     historyButton.setForeground(new Color(225,225,255));
                     booksButton.setForeground(new Color(225,225,255));
                     settingsButton.setForeground(new Color(225,225,255));
                  
    }//GEN-LAST:event_notificationsButtonActionPerformed

    private void xsuerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xsuerButtonActionPerformed
                        dispose();      
    }//GEN-LAST:event_xsuerButtonActionPerformed

    
    
   
    
    
    
  
public void populateReservationTableFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            
            String name = userName.getText();
            // Prepare and execute a query to retrieve all rows from the "borrows" table
            PreparedStatement statement = connection.prepareStatement("SELECT title, dor, rn FROM reservation WHERE name=?");
             statement.setString(1, name); // Set the parameter for the name
            ResultSet resultSet = statement.executeQuery();
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) reservationTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to borrowsTable
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String dor = resultSet.getString("dor");
                String rn = resultSet.getString("rn");
                
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{title, dor, rn});
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
    
    
    
    
    
    
   
    
    



private void populateBorrowsTableFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            String name = userName.getText(); // Assuming userName is the text field that holds the name
            
           
            
            // Prepare and execute a query to retrieve rows from the "borrows" table where name matches
            PreparedStatement statement = connection.prepareStatement("SELECT title, dob, dor FROM borrows WHERE name = ?");
            statement.setString(1, name); // Set the parameter for the name
            ResultSet resultSet = statement.executeQuery();
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) borrowsTable.getModel();
            
          
            model.setRowCount(0);
            
            // Debugging: Print the result set
            System.out.println("Result Set:");
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String dob = resultSet.getString("dob");
                String dor = resultSet.getString("dor");
                
               
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{title, dob, dor}
                
               );
                
                
                
            }
            
            // Check if the result set has any data
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No data found for the name: " + name);
            }
            
            // Close result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}


    

 private void removeBorrowsSelectedRowsFromDatabase() {
    int[] selectedRows = borrowsTable.getSelectedRows();

    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(null, "Please select a book to return.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        if (connection != null) {
            // Iterate over the selected rows in borrowsTable
            for (int rowIndex : selectedRows) {
                String title = (String) borrowsTable.getValueAt(rowIndex, 0);
                String name = userName.getText();

                try {
                    // Check if the title and name match in the "borrows" table
                    PreparedStatement checkBorrow = connection.prepareStatement("SELECT * FROM borrows WHERE title = ? AND name = ?");
                    checkBorrow.setString(1, title);
                    checkBorrow.setString(2, name);
                    ResultSet borrowResult = checkBorrow.executeQuery();

                    if (borrowResult.next()) {
                        // Remove the row from the "borrows" table
                        PreparedStatement deleteBorrow = connection.prepareStatement("DELETE FROM borrows WHERE title = ? AND name = ?");
                        deleteBorrow.setString(1, title);
                        deleteBorrow.setString(2, name);
                        deleteBorrow.executeUpdate();

                        // Add to history as returned
                        String status = "Returned";
                        String date = LocalDate.now().toString();

                        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO history (title, date, status, name) VALUES (?, ?, ?, ?)");
                        pstmt.setString(1, title);
                        pstmt.setString(2, date);
                        pstmt.setString(3, status);
                        pstmt.setString(4, name);
                        pstmt.executeUpdate();

                        // Check the "reservation" table for the title
                        PreparedStatement checkReservation = connection.prepareStatement("SELECT * FROM reservation WHERE title = ?");
                        checkReservation.setString(1, title);
                        ResultSet reservationResult = checkReservation.executeQuery();

                        if (reservationResult.next()) {
                            // Count the number of reservations for this title
                            PreparedStatement countReservations = connection.prepareStatement("SELECT COUNT(*) AS count FROM reservation WHERE title = ?");
                            countReservations.setString(1, title);
                            ResultSet countResult = countReservations.executeQuery();

                            if (countResult.next()) {
                                int reservationCount = countResult.getInt("count");

                              
                                    // Get the data from the reservation row
                                    String reservedTitle = reservationResult.getString("title");
                                    String reservedName = reservationResult.getString("name");

                                    // Add the reservation to the "borrows" table
                                    String dob = LocalDate.now().toString();
                                    String dor = LocalDate.now().plusDays(7).toString();

                                    PreparedStatement addBorrow = connection.prepareStatement("INSERT INTO borrows (title, dob, dor, name) VALUES (?, ?, ?, ?)");
                                    addBorrow.setString(1, reservedTitle);
                                    addBorrow.setString(2, dob);
                                    addBorrow.setString(3, dor);
                                    addBorrow.setString(4, reservedName);
                                    addBorrow.executeUpdate();
                                    
                                    

                                    // Decrement the nr column value in the books table
                                    PreparedStatement getNrStatement = connection.prepareStatement("SELECT nr FROM books WHERE Title = ?");
                                    getNrStatement.setString(1, title);
                                    ResultSet rsNr = getNrStatement.executeQuery();

                                    if (rsNr.next()) {
                                        String nrString = rsNr.getString("nr");
                                        int nr = Integer.parseInt(nrString);

                                        if (nr > 0) {
                                            nr -= 1;
                                            String updatedNrString = Integer.toString(nr);

                                            PreparedStatement updateNrStatement = connection.prepareStatement("UPDATE books SET nr = ? WHERE Title = ?");
                                            updateNrStatement.setString(1, updatedNrString);
                                            updateNrStatement.setString(2, title);
                                            updateNrStatement.executeUpdate();
                                        }
                                    }

                                    // Remove the row from the "reservation" table
                                    PreparedStatement deleteReservation = connection.prepareStatement("DELETE FROM reservation WHERE title = ? AND name = ?");
                                    deleteReservation.setString(1, reservedTitle);
                                    deleteReservation.setString(2, reservedName);
                                    deleteReservation.executeUpdate();
                                    
                                    
                                    if (reservationCount == 1) {
                                    // If there's only one reservation left, set the book status to "Borrowed"
                                    PreparedStatement updateBookStatus = connection.prepareStatement("UPDATE books SET status = 'Borrowed' WHERE title = ?");
                                    updateBookStatus.setString(1, title);
                                    updateBookStatus.executeUpdate();
                                                                      
                                    
                                }
                                    
                                    
                        // Decrement rn values in the reservation table for all matching titles
                        PreparedStatement decrementRnStatement = connection.prepareStatement(
                            "UPDATE reservation SET rn = rn - 1 WHERE title = ?"
                        );
                        
                        
                        
                        decrementRnStatement.setString(1, title);
                        decrementRnStatement.executeUpdate();
                        

                                    // Add to history as borrowed
                                    String borrowedStatus = "Borrowed";
                                    PreparedStatement addHistory = connection.prepareStatement("INSERT INTO history (title, date, status, name) VALUES (?, ?, ?, ?)");
                                    addHistory.setString(1, reservedTitle);
                                    addHistory.setString(2, dob);
                                    addHistory.setString(3, borrowedStatus);
                                    addHistory.setString(4, reservedName);
                                    addHistory.executeUpdate();
                                
                            }
                        } else
                        
                        {
                            // If no match found in reservation table, update the book status to Available
                            PreparedStatement updateBookStatus = connection.prepareStatement("UPDATE books SET status = 'Available' WHERE title = ?");
                            updateBookStatus.setString(1, title);
                            updateBookStatus.executeUpdate();
                        }
                    }

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

    // Clear the selection and update the tables
    borrowsTable.clearSelection();
    DefaultTableModel model = (DefaultTableModel) borrowsTable.getModel();
    model.setRowCount(0);
    dbData();
    populateBorrowsTableFromDatabase();
    fillHistoryTableFromDatabase(userName.getText());
}

  
    

private void removeReservationSelectedRowsFromDatabase() {
    int[] selectedRows = reservationTable.getSelectedRows();

    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(null, "Please select a book to cancel reservation.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
    } else {
        try {
            // Establish a database connection
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();

            if (connection != null) {
                // Iterate over the selected rows
                for (int rowIndex : selectedRows) {
                    // Get the title from the selected row
                    String title = (String) reservationTable.getValueAt(rowIndex, 0);
                    String name = userName.getText();

                    try {
                        // Remove the row from the "reservation" table
                        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM reservation WHERE title = ? AND name = ?");
                        deleteStatement.setString(1, title);
                        deleteStatement.setString(2, name);
                        deleteStatement.executeUpdate();

                        // Decrement the rn column value in the reservation table if rn is greater than 0
                        PreparedStatement getRnStatement = connection.prepareStatement("SELECT rn FROM reservation WHERE title = ?");
                        getRnStatement.setString(1, title);
                        ResultSet rsRn = getRnStatement.executeQuery();

                        if (rsRn.next()) {
                            int rn = rsRn.getInt("rn");

                            if (rn > 0) {
                                rn -= 1;
                                PreparedStatement updateRnStatement = connection.prepareStatement("UPDATE reservation SET rn = ? WHERE title = ?");
                                updateRnStatement.setInt(1, rn);
                                updateRnStatement.setString(2, title);
                                updateRnStatement.executeUpdate();
                            }
                        }

                        // Insert cancellation into history table
                        String status = "Reservation Cancelled";
                        String date = LocalDate.now().toString();

                        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO history (title, date, status, name) VALUES (?, ?, ?, ?)");
                        pstmt.setString(1, title);
                        pstmt.setString(2, date);
                        pstmt.setString(3, status);
                        pstmt.setString(4, name);
                        pstmt.executeUpdate();

                        // Decrement the nr column value in the books table
                        PreparedStatement getNrStatement = connection.prepareStatement("SELECT nr FROM books WHERE title = ?");
                        getNrStatement.setString(1, title);
                        ResultSet rsNr = getNrStatement.executeQuery();

                        if (rsNr.next()) {
                            int nr = rsNr.getInt("nr");

                            if (nr > 0) {
                                nr -= 1;
                                PreparedStatement updateNrStatement = connection.prepareStatement("UPDATE books SET nr = ? WHERE title = ?");
                                updateNrStatement.setInt(1, nr);
                                updateNrStatement.setString(2, title);
                                updateNrStatement.executeUpdate();
                            }
                        }

                        // Check if there are any remaining reservations for the same title
                        PreparedStatement checkReservationStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM reservation WHERE title = ?");
                        checkReservationStatement.setString(1, title);
                        ResultSet rsReservationCount = checkReservationStatement.executeQuery();

                        boolean reservationExists = false;
                        if (rsReservationCount.next() && rsReservationCount.getInt("count") > 0) {
                            reservationExists = true;
                        }

                        if (reservationExists) {
                            // If there are remaining reservations, set status to "Reserved"
                            PreparedStatement updateStatusStatement = connection.prepareStatement("UPDATE books SET status = ? WHERE title = ?");
                            updateStatusStatement.setString(1, "Reserved");
                            updateStatusStatement.setString(2, title);
                            updateStatusStatement.executeUpdate();
                        } else {
                            // Check if there are any matching rows in the "borrows" table
                            PreparedStatement checkBorrowsStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM borrows WHERE title = ?");
                            checkBorrowsStatement.setString(1, title);
                            ResultSet rsBorrowsCount = checkBorrowsStatement.executeQuery();

                            boolean borrowExists = false;
                            if (rsBorrowsCount.next() && rsBorrowsCount.getInt("count") > 0) {
                                borrowExists = true;
                            }

                            if (borrowExists) {
                                // If there are matching rows in the "borrows" table, set status to "Borrowed"
                                PreparedStatement updateStatusStatement = connection.prepareStatement("UPDATE books SET status = ? WHERE title = ?");
                                updateStatusStatement.setString(1, "Borrowed");
                                updateStatusStatement.setString(2, title);
                                updateStatusStatement.executeUpdate();
                            } else {
                                // If there are no matching rows in either table, set status to "Available"
                                PreparedStatement updateStatusStatement = connection.prepareStatement("UPDATE books SET status = ? WHERE title = ?");
                                updateStatusStatement.setString(1, "Available");
                                updateStatusStatement.setString(2, title);
                                updateStatusStatement.executeUpdate();
                            }

                            // Close result sets and statements
                            rsBorrowsCount.close();
                            checkBorrowsStatement.close();
                        }

                        // Close result sets and statements
                        rsNr.close();
                        getNrStatement.close();
                        rsReservationCount.close();
                        checkReservationStatement.close();
                        rsRn.close();
                        getRnStatement.close();
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
}

    
    
    
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            setName.setEditable(false);
            setRole.setEditable(false);
            setId.setEditable(false);
            setEmail.setEditable(false);
            setContact.setEditable(false);

           
            logOutButton.setForeground(new Color(225,225,255));
            editButton.setForeground(new Color(225,225,255));
            saveButton.setForeground(new Color(0,225,255));

            String name = setName.getText();
            String role = setRole.getText();
            String id = setId.getText();
            String email =  setEmail.getText();
            String contact =  setContact.getText();

            // Get the database connection
            dbConnection conn = new dbConnection();
            Connection connection = conn.getConnection();

            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return;
            }

            String sql = "UPDATE userinfo SET name = ?, role = ?, id = ?, email = ?, Contact = ? WHERE name = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, role);
                pstmt.setString(3, id);
                pstmt.setString(4, email);
                pstmt.setString(5, contact);
                pstmt.setString(6, name);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User information updated successfully.");
                } else {
                    System.out.println("No user found with the name: " + name);
                }
            } catch (SQLException e) {
                System.out.println("Error updating user info: " + e.getMessage());
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_saveButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        setName.setEditable(false);
        setRole.setEditable(false);
        setId.setEditable(false);
        setEmail.setEditable(true);
        setContact.setEditable(true);

      
        logOutButton.setForeground(new Color(225,225,255));
        editButton.setForeground(new Color(0,225,255));
        saveButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_editButtonActionPerformed

    private void setNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setNameActionPerformed
                             
    }//GEN-LAST:event_setNameActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        dispose();
        JOptionPane.showMessageDialog(null, "Logged out successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        new LogIn().setVisible(true);

        logOutButton.setForeground(new Color(0,225,255));
        editButton.setForeground(new Color(225,225,255));
        saveButton.setForeground(new Color(225,225,255));

    }//GEN-LAST:event_logOutButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
       
       
            removeReservationSelectedRowsFromDatabase();
            populateBorrowsTableFromDatabase();
            populateReservationTableFromDatabase();
            fillHistoryTableFromDatabase(userName.getText());// update tables in realtime
            dbData();// update tables in realtime

    }//GEN-LAST:event_cancelButtonActionPerformed

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed

        
        
           requestReturn();
           populateBorrowsTableFromDatabase();
            populateReservationTableFromDatabase();
          fillHistoryTableFromDatabase(userName.getText()); // update tables in realtime
             dbData();// update tables in realtime

             
             
             
             
             
    }//GEN-LAST:event_returnButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
                
        reserveButton.setForeground(new Color(0,225,255));
        borrowButton.setForeground(new Color(0,225,255));
        searchButton.setForeground(new Color(225,225,255));
            updateButton.setForeground(new Color(0, 225, 255));
       
            searchBook.requestFocus();
        
        String searchText = searchBook.getText().trim();
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
        String query = "SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ? OR ISBN LIKE ? OR Category LIKE ?  OR status  LIKE ?  OR nr LIKE ?";
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
        DefaultTableModel model = (DefaultTableModel) searchBookTable.getModel();
        model.setRowCount(0);
        
        // Debug statement to check if the query returns any results
        boolean hasResults = false;
        
        // Populate the table with the search results
        while (resultSet.next()) {
            hasResults = true;
            String title = resultSet.getString("Title");
            String author = resultSet.getString("Author");
            String isbn = resultSet.getString("ISBN");
            String category = resultSet.getString("Category");
            String status = resultSet.getString("status");
             String nr = resultSet.getString("nr");
            
            
            // Add row to the table model
            model.addRow(new Object[]{title, author, isbn , category ,status, nr});
            
            // Print each result for debugging
            System.out.println("Title: " + title + ", Author : " +  author + ", ISBN : " + isbn + ", Category : " + category  + ", Status: " + status + ", Nr: " + nr );
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


    }//GEN-LAST:event_searchButtonActionPerformed
   
    
    
    private void searchBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBookActionPerformed
                             
    }//GEN-LAST:event_searchBookActionPerformed

    private void searchBookFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBookFocusLost
        // in completion with placeholder

        if (searchBook.getText().equals(""))   {
            searchBook.setText("Search");
            searchBook.setForeground(CYAN);
        }

    }//GEN-LAST:event_searchBookFocusLost

    private void searchBookFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBookFocusGained
        // set placeholder search a book titile here

        if (searchBook.getText().equals("Search"))   {
            searchBook.setText("");
            searchBook.setForeground(BLACK);
        }
    }//GEN-LAST:event_searchBookFocusGained

    
    
    
    
    
    public void addHistoryRecord(String title, String date, String status, String name) {
        try {
            // Add to JTable
            DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
            model.addRow(new Object[]{title, date, null, status});
            
            // Update database
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();
            
            String sql = "INSERT INTO history (title, date, status, name) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, date);
                pstmt.setString(3, status);
                pstmt.setString(4, name);
                
                pstmt.executeUpdate();
                
                
            } catch (SQLException e) {
                e.printStackTrace();
                // Optionally, handle the exception, e.g., show an error message
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    private void borrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowButtonActionPerformed

       updateButton.setForeground(new Color(0, 225, 255));
    borrowButton.setForeground(new Color(225, 225, 255));
    reserveButton.setForeground(new Color(0, 225, 225));
    searchButton.setForeground(new Color(0, 225, 225));

    int[] selectedRows = searchBookTable.getSelectedRows();

    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(null, "Please select a book.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
    } else {
        try {
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();

            if (connection != null) {
                for (int rowIndex : selectedRows) {
                    String title = (String) searchBookTable.getValueAt(rowIndex, 0);
                    String name = userName.getText();

                    try {
                        // Check if the user is currently borrowing the book
                        PreparedStatement checkBorrow = connection.prepareStatement(
                            "SELECT * FROM borrows WHERE name = ? AND title = ?"
                        );
                        checkBorrow.setString(1, name);
                        checkBorrow.setString(2, title);
                        ResultSet rsBorrow = checkBorrow.executeQuery();

                        if (rsBorrow.next()) {
                            JOptionPane.showMessageDialog(this, "You are currently borrowing the book.", "Error", JOptionPane.ERROR_MESSAGE);
                            continue; // Skip to the next selected book
                        }

                        // Check the status and nb of the book
                        PreparedStatement statusStatement = connection.prepareStatement(
                            "SELECT status, nb FROM books WHERE Title = ?"
                        );
                        statusStatement.setString(1, title);
                        ResultSet statusResult = statusStatement.executeQuery();

                        if (statusResult.next()) {
                            String status = statusResult.getString("status");
                            int nb = statusResult.getInt("nb");

                            if (status.equals("Borrowed")) {
                                JOptionPane.showMessageDialog(this, "The book \"" + title + "\" is already borrowed. We recommend you to reserve it.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else if (status.equals("Reserved")) {
                                JOptionPane.showMessageDialog(this, "The book \"" + title + "\" is already reserved. We recommend you to reserve it.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else if (status.equals("Available")) {
                                // Decrement the value of 'nb' by 1
                                if (nb > 1) {
                                    PreparedStatement decrementStmt = connection.prepareStatement(
                                        "UPDATE books SET nb = ? WHERE Title = ?"
                                    );
                                    decrementStmt.setInt(1, nb - 1);
                                    decrementStmt.setString(2, title);
                                    decrementStmt.executeUpdate();
                                } else if (nb == 1) {
                                    // Decrement 'nb' and update the status of the book to 'Borrowed'
                                    PreparedStatement updateStatement = connection.prepareStatement(
                                        "UPDATE books SET nb = ?, status = 'Borrowed' WHERE Title = ?"
                                    );
                                    updateStatement.setInt(1, nb - 1);
                                    updateStatement.setString(2, title);
                                    updateStatement.executeUpdate();
                                }

                                // Insert into 'borrows' table
                                PreparedStatement insertBorrowStmt = connection.prepareStatement(
                                    "INSERT INTO borrows (title, dob, dor, name) VALUES (?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), ?)"
                                );
                                insertBorrowStmt.setString(1, title);
                                insertBorrowStmt.setString(2, name);
                                insertBorrowStmt.executeUpdate();

                                String dob = LocalDate.now().toString();
                                String dor = LocalDate.now().plusDays(7).toString();
                                String status1 = "Borrowed";
                                String date = LocalDate.now().toString();

                                // Insert into history table
                                PreparedStatement pstmt = connection.prepareStatement(
                                    "INSERT INTO history (title, date, status, name) VALUES (?, ?, ?, ?)"
                                );
                                pstmt.setString(1, title);
                                pstmt.setString(2, date);
                                pstmt.setString(3, status1);
                                pstmt.setString(4, name);
                                pstmt.executeUpdate();

                                JOptionPane.showMessageDialog(this, "The book \"" + title + "\" has been successfully borrowed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                        // Close result sets and statements
                        rsBorrow.close();
                        statusResult.close();
                        checkBorrow.close();
                        statusStatement.close();
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

    // Clear the selection and update searchBookTable
    searchBookTable.clearSelection();
    DefaultTableModel model = (DefaultTableModel) searchBookTable.getModel();
    model.setRowCount(0);
    dbData();
    populateBorrowsTableFromDatabase();
    fillHistoryTableFromDatabase(userName.getText());
    dbData(); // to update table in real-time
        
    }//GEN-LAST:event_borrowButtonActionPerformed

    
    
    
    
    private void reserveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserveButtonActionPerformed

        borrowButton.setForeground(new Color(0, 225, 255));
    reserveButton.setForeground(new Color(225, 225, 225));
    searchButton.setForeground(new Color(0, 225, 225));
    updateButton.setForeground(new Color(0, 225, 255));

    try {
        // Get the selected rows from the searchBookTable
        int[] selectedRows = searchBookTable.getSelectedRows();
        
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a book.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
        } else {
            // Establish a database connection
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();
            
            if (connection != null) {
                // Iterate over the selected rows in searchBookTable
                for (int rowIndex : selectedRows) {
                    // Get the data from column 0 of the selected row in searchBookTable
                    String title = (String) searchBookTable.getValueAt(rowIndex, 0);
                    String name = userName.getText();

                    try {
                        // Check if the user is currently borrowing the book
                        PreparedStatement checkBorrow = connection.prepareStatement(
                            "SELECT * FROM borrows WHERE name = ? AND title = ?"
                        );
                        checkBorrow.setString(1, name);
                        checkBorrow.setString(2, title);
                        ResultSet rsBorrow = checkBorrow.executeQuery();

                        if (rsBorrow.next()) {
                            JOptionPane.showMessageDialog(this, "You are currently borrowing the book.", "Error", JOptionPane.ERROR_MESSAGE);
                            continue; // Skip to the next selected book
                        }

                        // Check if the user is currently reserving the book
                        PreparedStatement checkReserve = connection.prepareStatement(
                            "SELECT * FROM reservation WHERE name = ? AND title = ?"
                        );
                        checkReserve.setString(1, name);
                        checkReserve.setString(2, title);
                        ResultSet rsReserve = checkReserve.executeQuery();

                        if (rsReserve.next()) {
                            JOptionPane.showMessageDialog(this, "You are currently reserving the book.", "Error", JOptionPane.ERROR_MESSAGE);
                            continue; // Skip to the next selected book
                        }

                        // Check the status of the book
                        PreparedStatement statusStatement = connection.prepareStatement(
                            "SELECT status, nr FROM books WHERE Title = ?"
                        );
                        statusStatement.setString(1, title);
                        ResultSet statusResult = statusStatement.executeQuery();

                        if (statusResult.next()) {
                            String status = statusResult.getString("status");
                            String nrString = statusResult.getString("nr"); // Get the nr value as string
                            int nr = Integer.parseInt(nrString); // Convert nr value to integer

                            if (status.equals("Available")) {
                                JOptionPane.showMessageDialog(this, "We encourage you to borrow the book since it is available.", "Error", JOptionPane.ERROR_MESSAGE);
                                continue; // Skip to the next selected book
                            } else if (status.equals("Borrowed") || status.equals("Reserved")) {
                                // Update the status of the book to 'Reserved'
                                PreparedStatement updateStatement = connection.prepareStatement(
                                    "UPDATE books SET status = 'Reserved' WHERE Title = ?"
                                );
                                updateStatement.setString(1, title);
                                updateStatement.executeUpdate();

                                // Increment the nr column in the books table
                                nr = nr + 1; // Increment the nr value
                                String updatedNrString = Integer.toString(nr); // Convert back to string

                                PreparedStatement incrementNrStatement = connection.prepareStatement(
                                    "UPDATE books SET nr = ? WHERE Title = ?"
                                );
                                incrementNrStatement.setString(1, updatedNrString);
                                incrementNrStatement.setString(2, title);
                                incrementNrStatement.executeUpdate();
                                
                                String date = LocalDate.now().toString();
                                String status1 = "Reserved";

                                // Insert into reservation table
                                PreparedStatement updateReservation = connection.prepareStatement(
                                    "INSERT INTO reservation (title, dor, rn , name) VALUES (?, ?, ?, ?)"
                                );
                                updateReservation.setString(1, title);
                                updateReservation.setString(2, date);
                                updateReservation.setString(3, updatedNrString);
                                updateReservation.setString(4, name);
                                updateReservation.executeUpdate();

                                // Insert into history table
                                PreparedStatement pstmt = connection.prepareStatement(
                                    "INSERT INTO history (title, date, status, name) VALUES (?, ?, ?, ?)"
                                );
                                pstmt.setString(1, title);
                                pstmt.setString(2, date);
                                pstmt.setString(3, status1);
                                pstmt.setString(4, name);
                                pstmt.executeUpdate();

                                JOptionPane.showMessageDialog(this, "The book \"" + title + "\" has been successfully reserved.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                        // Close result sets and statements
                        rsReserve.close();
                        statusResult.close();
                        checkReserve.close();
                        statusStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                // Close the connection after all operations are done
                connection.close();
            } else {
                JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Clear the selection and update searchBookTable
    searchBookTable.clearSelection();
    DefaultTableModel model = (DefaultTableModel) searchBookTable.getModel();
    model.setRowCount(0);
    dbData();
    populateReservationTableFromDatabase();
    fillHistoryTableFromDatabase(userName.getText());
    dbData(); // update table in real-time
    
    }//GEN-LAST:event_reserveButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
      
        
        updateButton.setForeground(new Color(225, 225, 255));
        borrowButton.setForeground(new Color(0, 225, 255));
        reserveButton.setForeground(new Color(0,225,225));
        searchButton.setForeground(new Color(0,225,225));
        
        dbData(); // update table in real-time
       fillHistoryTableFromDatabase(userName.getText());
           populateReservationTableFromDatabase();
              populateBorrowsTableFromDatabase();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        notifs();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      setState(JFrame. ICONIFIED);
    }//GEN-LAST:event_jButton2ActionPerformed

     
    private void saveImageToDatabase(BufferedImage image) {
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        String user = userName.getText(); // Assuming userName is a JTextField

        if (connection != null) {
            try {
                // Check if the user exists in the database
                String checkUserQuery = "SELECT * FROM userinfo WHERE name = ?";
                PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery);
                checkUserStatement.setString(1, user);
                ResultSet resultSet = checkUserStatement.executeQuery();
                
                if (resultSet.next()) {
                    // Insert the image into the database
                    String updateImageQuery = "UPDATE userinfo SET images = ? WHERE name = ?";
                    PreparedStatement updateImageStatement = connection.prepareStatement(updateImageQuery);
                    
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", baos);
                    InputStream is = new ByteArrayInputStream(baos.toByteArray());
                    
                    updateImageStatement.setBlob(1, is);
                    updateImageStatement.setString(2, user);
                    
                    updateImageStatement.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Image saved to database for user: " + user);
                } else {
                    JOptionPane.showMessageDialog(null, "User does not exist: " + user);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving image to database: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    
    private void editProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileActionPerformed
         JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null); // Replace 'null' with your JFrame or JPanel instance

    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            // Read the selected image file
            BufferedImage originalImage = ImageIO.read(selectedFile);

            // Resize the image to fit the JLabel
            int labelWidth = profile.getWidth();
            int labelHeight = profile.getHeight();
            int diameter = Math.min(labelWidth, labelHeight);
            BufferedImage resizedImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(diameter, diameter, BufferedImage.SCALE_SMOOTH), 0, 0, null);

            // Save the resized image to the database
            saveImageToDatabase(resizedImage);

            // Set the resized image to the JLabel
            profile.setIcon(new ImageIcon(resizedImage));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error uploading image: " + ex.getMessage());
        }
    }
     
    }//GEN-LAST:event_editProfileActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel books;
    private javax.swing.JButton booksButton;
    private javax.swing.JButton borrowButton;
    private javax.swing.JPanel borrows;
    private javax.swing.JButton borrowsButton;
    private javax.swing.JTable borrowsTable;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel contact;
    private javax.swing.JButton editButton;
    private javax.swing.JButton editProfile;
    private javax.swing.JLabel email;
    private javax.swing.JPanel history;
    private javax.swing.JButton historyButton;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel4;
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
    private java.awt.Panel leftPanel;
    private javax.swing.JButton logOutButton;
    private javax.swing.JLabel name;
    private javax.swing.JPanel notifications;
    private javax.swing.JButton notificationsButton;
    private javax.swing.JTable notifsTable;
    private java.awt.Panel panel2;
    private javax.swing.JLabel profile;
    private javax.swing.JButton reservationButton;
    private javax.swing.JTable reservationTable;
    private javax.swing.JPanel reservations;
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
    private javax.swing.JPanel settings;
    private javax.swing.JButton settingsButton;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel userName;
    private javax.swing.JButton xsuerButton;
    // End of variables declaration//GEN-END:variables

}