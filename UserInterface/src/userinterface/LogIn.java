
package userinterface;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class LogIn extends javax.swing.JFrame {
             
       
       public  LogIn (){
                     
           initComponents();
          this.setVisible(true);
          checkForOverdueFees();
        updateUserInfoLoans();
        checkAndCancelOverdueReservations();
          
          
           
             
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
       
       
       
       
       
       
 
       
      
       
    public void setUsername(String userInput) {
        try {
            // Get the database connection
            dbConnection conn = new dbConnection();
            Connection connection = conn.getConnection();
            
            // Check if the user exists in the database
            PreparedStatement statement = connection.prepareStatement("UPDATE userinfo SET number = (CASE WHEN name = ? THEN 2 ELSE 1 END)");
            statement.setString(1, userInput);
            
            // Execute the update query
            int rowsAffected = statement.executeUpdate();
            
           
            
            // Close resources
            statement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panel1 = new java.awt.Panel();
        panel2 = new java.awt.Panel();
        userLogin = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        logInButton = new javax.swing.JButton();
        eyeButton = new javax.swing.JButton();
        passwordLogIn = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        jLabel7.setText("jLabel7");

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(234, 219, 219));
        jLabel3.setText("YOUR ONLINE LIBRARY");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(28, 52, 62));
        setFrameIcon("libIcon.png");

        setTitle("Lib.IT");
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userLogin.setBackground(java.awt.Color.white);
        userLogin.setForeground(new java.awt.Color(0, 0, 0));
        userLogin.setMaximumSize(null);
        userLogin.setMinimumSize(null);
        userLogin.setPreferredSize(new java.awt.Dimension(70, 23));
        userLogin.setSelectionColor(new java.awt.Color(204, 255, 255));
        panel2.add(userLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 248, -1));

        jLabel8.setBackground(new java.awt.Color(38, 0, 66));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.white);
        jLabel8.setText("User name");
        panel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 75, -1));

        jLabel9.setBackground(new java.awt.Color(38, 0, 66));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(java.awt.Color.white);
        jLabel9.setText("Password");
        panel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 69, -1));

        jLabel4.setFont(new java.awt.Font("Stylus BT", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(216, 208, 208));
        jLabel4.setText("YOUR DIGITAL GATEWAY");
        jLabel4.setToolTipText("");
        panel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 173, -1));

        logInButton.setBackground(new java.awt.Color(255, 255, 255));
        logInButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logInButton.setForeground(new java.awt.Color(102, 0, 102));
        logInButton.setText("Log In");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });
        panel2.add(logInButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 112, 19));

        eyeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/eyenew.png"))); // NOI18N
        eyeButton.setBorderPainted(false);
        eyeButton.setContentAreaFilled(false);
        eyeButton.setFocusPainted(false);
        eyeButton.setVerifyInputWhenFocusTarget(false);
        eyeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eyeButtonActionPerformed(evt);
            }
        });
        panel2.add(eyeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 20, -1));

        passwordLogIn.setBackground(java.awt.Color.white);
        passwordLogIn.setForeground(new java.awt.Color(0, 0, 0));
        passwordLogIn.setText("jPasswordField1");
        passwordLogIn.setText(null);
        passwordLogIn.setPreferredSize(new java.awt.Dimension(70, 23));
        passwordLogIn.setSelectedTextColor(new java.awt.Color(240, 207, 240));
        passwordLogIn.setSelectionColor(new java.awt.Color(148, 215, 215));
        panel2.add(passwordLogIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 248, -1));

        jLabel6.setFont(new java.awt.Font("Stylus BT", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(216, 208, 208));
        jLabel6.setText("TO INFINITE KNOWLEDGE");
        panel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 150, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/libitz.png"))); // NOI18N
        panel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        jPanel1.setBackground(new java.awt.Color(28, 52, 62));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/minimize.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jButton2.setFocusable(false);
        jButton2.setVerifyInputWhenFocusTarget(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, 30));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/Xbang.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setRequestFocusEnabled(false);
        jButton1.setVerifyInputWhenFocusTarget(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 30, 30));

        makePanelMovable(this, jPanel1);

        panel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 40));

        panel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 440, 400));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/libitnice2.gif"))); // NOI18N
        panel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 400));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
       
    public static void checkAndCancelOverdueReservations() {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;
        PreparedStatement updateRnStmt = null;
        PreparedStatement insertHistoryStmt = null;
        PreparedStatement insertNotifStmt = null;
        PreparedStatement updateNrStmt = null;
        PreparedStatement checkAndUpdateStatusStmt = null;
        
        try {
            // Establish a connection to the database
            dbConnection con = new dbConnection();
            connection = con.getConnection();
            
            // Get current date
            java.sql.Date currentDate = new java.sql.Date(new java.util.Date().getTime());
            LocalDate yesterday = LocalDate.now().minusDays(1);
            

            // Query to select all overdue reservations
            String selectQuery = "SELECT title, dor, rn, name, sched FROM reservation WHERE sched < ?";
            selectStmt = connection.prepareStatement(selectQuery);
            selectStmt.setDate(1, currentDate);

            ResultSet resultSet = selectStmt.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String dor = resultSet.getString("dor");
                int rn = resultSet.getInt("rn");
                String name = resultSet.getString("name");
                java.util.Date schedDate = resultSet.getDate("sched");

                // Notify the user
                String notificationMessage = "Dear " + name + ", you have failed to claim your book yesterday. \""
                        + yesterday + "\"With this, to ensure the book circulation of our beloved library, your reservation is automatically cancelled. - Librarian";
                // JOptionPane.showMessageDialog(null, notificationMessage); // Commented out to push the notification to the database instead

                // Insert notification into the notifs table
                String insertNotifQuery = "INSERT INTO notifs (notif, name) VALUES (?, ?)";
                insertNotifStmt = connection.prepareStatement(insertNotifQuery);
                insertNotifStmt.setString(1, notificationMessage);
                insertNotifStmt.setString(2, name);
                insertNotifStmt.executeUpdate();

                // Delete the reservation
                String deleteQuery = "DELETE FROM reservation WHERE title = ? AND name = ? AND sched = ?";
                deleteStmt = connection.prepareStatement(deleteQuery);
                deleteStmt.setString(1, title);
                deleteStmt.setString(2, name);
                deleteStmt.setDate(3, new java.sql.Date(schedDate.getTime()));
                deleteStmt.executeUpdate();

                // Update rn for other reservations of the same book
                String updateRnQuery = "UPDATE reservation SET rn = rn - 1 WHERE title = ? AND rn > ?";
                updateRnStmt = connection.prepareStatement(updateRnQuery);
                updateRnStmt.setString(1, title);
                updateRnStmt.setInt(2, rn);
                updateRnStmt.executeUpdate();

                // Insert into history table
                String insertHistoryQuery = "INSERT INTO history (title, status, date, name) VALUES (?, ?, ?, ?)";
                insertHistoryStmt = connection.prepareStatement(insertHistoryQuery);
                insertHistoryStmt.setString(1, title);
                insertHistoryStmt.setString(2, "Reservation Cancelled");
                insertHistoryStmt.setDate(3, currentDate);
                insertHistoryStmt.setString(4, name);
                insertHistoryStmt.executeUpdate();

                String updateNrQuery = "UPDATE books SET nr = nr - 1 WHERE title = ?";
                updateNrStmt = connection.prepareStatement(updateNrQuery);
                updateNrStmt.setString(1, title);
                updateNrStmt.executeUpdate();

                // Check and update status in books table if nr is 0
                String checkAndUpdateStatusQuery = "UPDATE books SET status = 'Available' WHERE title = ? AND nr = 0";
                checkAndUpdateStatusStmt = connection.prepareStatement(checkAndUpdateStatusQuery);
                checkAndUpdateStatusStmt.setString(1, title);
                checkAndUpdateStatusStmt.executeUpdate();
            }

            // Close the result set
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        } finally {
            try {
                // Close the statements and connection
                if (selectStmt != null) selectStmt.close();
                if (deleteStmt != null) deleteStmt.close();
                if (updateRnStmt != null) updateRnStmt.close();
                if (insertHistoryStmt != null) insertHistoryStmt.close();
                if (insertNotifStmt != null) insertNotifStmt.close();
                if (updateNrStmt != null) updateNrStmt.close();
                if (checkAndUpdateStatusStmt != null) checkAndUpdateStatusStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    
    
  // Method to check for overdue borrows and update userinfo table
private static void checkForOverdueAndUpdateUserInfo(String username) {
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        // Check if the user has any loan amount greater than 0 in the userinfo table
        String checkLoanQuery = "SELECT loan FROM userinfo WHERE name = ?";
        PreparedStatement checkLoanStatement = connection.prepareStatement(checkLoanQuery);
        checkLoanStatement.setString(1, username);
        ResultSet loanResultSet = checkLoanStatement.executeQuery();

        if (loanResultSet.next()) {
            int loanAmount = loanResultSet.getInt("loan");

            if (loanAmount > 0) {
                int choice = JOptionPane.showConfirmDialog(null, "You have an outstanding loan. Do you want to pay online now? If you do not have a gcash bank, you can proceed to the Library and pay to the Librarian", "Outstanding Loan", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    new Payment().setVisible(true);
                } else {
                    new LogIn().setVisible(true);
                }
            } else {
                new userHome().setVisible(true);
            }
        } else {
            new userHome().setVisible(true);
        }

        // Close the statements and result set
        loanResultSet.close();
        checkLoanStatement.close();

        // Close the connection
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    
    private static void checkForOverdueFees() { //checks for all overdues and puts fines automatically
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        String query = "SELECT title, dob, dor, name, DATEDIFF(CURDATE(), dor) AS overdue_days, loan, last_updated FROM borrows WHERE dor < CURDATE()";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        String updateBorrowsQuery = "UPDATE borrows SET loan = ?, overdueDays = ?, last_updated = CURDATE() WHERE title = ? AND dob = ? AND name = ?";
        PreparedStatement updateBorrowsStatement = connection.prepareStatement(updateBorrowsQuery);

        while (resultSet.next()) {
            String title = resultSet.getString("title");
            java.util.Date dob = resultSet.getDate("dob");
            String name = resultSet.getString("name");
            int overdueDays = resultSet.getInt("overdue_days");
            int loan = resultSet.getInt("loan");

            // Calculate fine based on overdue duration (10 per 7 days, including the first overdue period)
            int fine = 10;
            if (overdueDays > 0) {
                fine += ((overdueDays / 7) * 10);
            }

            // Update loan amount and overdue days for the book in borrows table
            updateBorrowsStatement.setInt(1, fine);
            updateBorrowsStatement.setInt(2, overdueDays);
            updateBorrowsStatement.setString(3, title);
            updateBorrowsStatement.setDate(4, new java.sql.Date(dob.getTime()));
            updateBorrowsStatement.setString(5, name);
            updateBorrowsStatement.executeUpdate();
        }

        // Close resources
        resultSet.close();
        statement.close();
        updateBorrowsStatement.close();
        connection.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
        
    private static void updateUserInfoLoans() {
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        // Query to get the sum of loans for each user from the borrows table
        String sumLoansQuery = "SELECT name, SUM(loan) as totalLoan FROM borrows GROUP BY name";
        PreparedStatement sumLoansStatement = connection.prepareStatement(sumLoansQuery);
        ResultSet resultSet = sumLoansStatement.executeQuery();

        // Query to update the loan amount for each user in the userinfo table
        String updateUserInfoQuery = "UPDATE userinfo SET loan = ? WHERE name = ?";
        PreparedStatement updateUserInfoStatement = connection.prepareStatement(updateUserInfoQuery);

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int totalLoan = resultSet.getInt("totalLoan");

            updateUserInfoStatement.setInt(1, totalLoan);
            updateUserInfoStatement.setString(2, name);
            int updatedRows = updateUserInfoStatement.executeUpdate();
            if (updatedRows == 0) {
                // If no rows were updated, print a warning
                System.out.println("Warning: No rows updated in userinfo table for user: " + name);
            }
        }

        // Close resources
        resultSet.close();
        sumLoansStatement.close();
        updateUserInfoStatement.close();
        connection.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
                          
        String username = userLogin.getText().trim();  
       
        
        try {
            
          

            dbConnection conn = new dbConnection();
            Connection connection = conn.getConnection();

            PreparedStatement ps1;
            ResultSet rs1;
            
            PreparedStatement ps2;
            ResultSet rs2;
             PreparedStatement ps3;
            ResultSet rs3;
            
            String userName = userLogin.getText().trim(); 
              
                   
            
               
            
             
                    
         
            String password = passwordLogIn.getText().trim();
            
            
            String query1 = "SELECT * FROM userinfo WHERE name=? AND Password=?";
            ps1 = connection.prepareStatement(query1);
            ps1.setString(1, userName);
            ps1.setString(2, password);
            rs1 = ps1.executeQuery();
            
          

            
            int x=0;
            if (rs1.next()) {
                   setUsername(username);
                
                JOptionPane.showMessageDialog(null, "Login Successful.", "Login", JOptionPane.INFORMATION_MESSAGE);
                
                    checkForOverdueAndUpdateUserInfo( userLogin.getText().trim());
                       
                          
                          
                          
                           
                
               
                    
              
                x++;
                
                     
            }
            
                  
            
             rs1.close();
              ps1.close();
            
               
            String query2 = "SELECT * FROM librarian WHERE User=? AND Password=?";
            ps2 = connection.prepareStatement(query2);
            ps2.setString(1, userName);
            ps2.setString(2, password);
            rs2 = ps2.executeQuery();
            
                if (rs2.next()){
                    JOptionPane.showMessageDialog(null, "Login Successful.", "Login", JOptionPane.INFORMATION_MESSAGE);
                          new Librarian().setVisible(true);
        
                       x++;
                    
                }
                
                
               
            
           
            rs2.close();
            ps2.close();
            
            
            
               
            String query3 = "SELECT * FROM admin WHERE name=? AND Password=?";
            ps3 = connection.prepareStatement(query3);
            ps3.setString(1, userName);
            ps3.setString(2, password);
            rs3 = ps3.executeQuery();
            
                if (rs3.next()){
                    JOptionPane.showMessageDialog(null, "Login Successful.", "Login", JOptionPane.INFORMATION_MESSAGE);
                          new admins().setVisible(true);
        
                       x++;
                    
                       
                    
                }
                
                
                if ( x==0) {
                
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login", JOptionPane.ERROR_MESSAGE);
                 new LogIn().setVisible(true);
            }
            
           
            rs3.close();
            ps3.close();
            
            
            
            
            
            
            connection.close();
        } 
        
        
        
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
       
       
        
        
     
        
        
        dispose();
        
    }//GEN-LAST:event_logInButtonActionPerformed

    
    
    
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed



     private boolean visible = false;
    private void eyeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eyeButtonActionPerformed
              if (visible) {
        passwordLogIn.setEchoChar('\u2022'); // Hide the password
    } else {
        passwordLogIn.setEchoChar((char)0); // Show the password
    }
    
    visible = !visible; // Toggle the visibility

    
                 
    }//GEN-LAST:event_eyeButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       setState(JFrame. ICONIFIED);
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void makePanelMovable(JFrame frame, JPanel jPanel1) { //not implemented yet, can't open payment design
        final Point[] mousePoint = {null}; // Declare mousePoint as an array

        jPanel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePoint[0] = e.getPoint(); // Get the point relative to jPanel1
            }
        });

        jPanel1.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mousePoint[0] != null) {
                    Point currentLocation = frame.getLocation();
                    Point newLocation = e.getLocationOnScreen();
                    int deltaX = newLocation.x - mousePoint[0].x - jPanel1.getLocationOnScreen().x;
                    int deltaY = newLocation.y - mousePoint[0].y - jPanel1.getLocationOnScreen().y;
                    frame.setLocation(currentLocation.x + deltaX, currentLocation.y + deltaY);
                }
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton eyeButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton logInButton;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private javax.swing.JPasswordField passwordLogIn;
    private javax.swing.JTextField userLogin;
    // End of variables declaration//GEN-END:variables



public void testLogIn (){

 try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogIn().setVisible(true);
            }
        });


}
}
