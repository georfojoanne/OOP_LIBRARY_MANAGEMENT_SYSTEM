package userinterface;


import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class Payment extends javax.swing.JFrame {

   
    
    
    public Payment () {
        initComponents();
        rnField.setEditable(false);
         amountField.setEditable(false);
         userAccount.setEditable(false);
          
           
           
    }

    
    
    
    // gets the amount Payable in the database table userinfo column loan and displays in the amountField
    
    public void retrieveLoanAmount(String name, JTextField amountField) {
        
        
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            String query = "SELECT loan FROM userinfo WHERE name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                int loanAmount = resultSet.getInt("loan");
                amountField.setText(String.valueOf(loanAmount));
                
                
            } 
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            amountField.setText("Error retrieving loan information");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    
    
    
    
}
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new java.awt.Panel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        tab = new javax.swing.JTabbedPane();
        panel2 = new java.awt.Panel();
        jLabel1 = new javax.swing.JLabel();
        gcashNumber = new javax.swing.JLabel();
        rnField = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        gnField = new javax.swing.JTextField();
        referenceNumber = new javax.swing.JLabel();
        userField = new javax.swing.JTextField();
        user = new javax.swing.JLabel();
        payment = new java.awt.Panel();
        jLabel2 = new javax.swing.JLabel();
        amount = new javax.swing.JLabel();
        userAccount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();
        payButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(10, 29, 36));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userinterface/Xbang.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);

        jButton2.setText("minimize");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(390, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(93, 93, 93))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 40));

        panel2.setBackground(new java.awt.Color(38, 51, 60));

        gcashNumber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gcashNumber.setForeground(new java.awt.Color(102, 255, 255));
        gcashNumber.setText("Gcash Number :");
        gcashNumber.setToolTipText("");

        rnField.setBackground(new java.awt.Color(187, 226, 226));
        rnField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rnField.setForeground(new java.awt.Color(0, 0, 0));
        rnField.setText("09653789997");
        rnField.setSelectionColor(new java.awt.Color(0, 204, 204));
        rnField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rnFieldActionPerformed(evt);
            }
        });

        confirmButton.setBackground(new java.awt.Color(48, 173, 173));
        confirmButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(0, 0, 0));
        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        gnField.setBackground(new java.awt.Color(187, 226, 226));
        gnField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        gnField.setForeground(new java.awt.Color(0, 0, 0));
        gnField.setSelectionColor(new java.awt.Color(0, 204, 204));
        gnField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gnFieldActionPerformed(evt);
            }
        });

        referenceNumber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        referenceNumber.setForeground(new java.awt.Color(102, 255, 255));
        referenceNumber.setText("Reference Number :");
        referenceNumber.setToolTipText("");

        userField.setBackground(new java.awt.Color(187, 226, 226));
        userField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        userField.setForeground(new java.awt.Color(0, 0, 0));
        userField.setSelectionColor(new java.awt.Color(0, 204, 204));
        userField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userFieldActionPerformed(evt);
            }
        });

        user.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        user.setForeground(new java.awt.Color(102, 255, 255));
        user.setText("Account Username :");
        user.setToolTipText("");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(referenceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gcashNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gnField, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rnField, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referenceNumber)
                    .addComponent(rnField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gcashNumber)
                    .addComponent(gnField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(user)
                    .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmButton)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        tab.addTab("tab2", panel2);

        payment.setBackground(new java.awt.Color(38, 51, 60));
        payment.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        payment.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 0, 192, -1));

        amount.setBackground(new java.awt.Color(102, 255, 255));
        amount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        amount.setForeground(new java.awt.Color(102, 255, 255));
        amount.setText("Loan Amount :");
        payment.add(amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        userAccount.setBackground(new java.awt.Color(187, 226, 226));
        payment.add(userAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 226, -1));

        jLabel4.setBackground(new java.awt.Color(102, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 255, 255));
        jLabel4.setText("User Account :");
        payment.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        amountField.setBackground(new java.awt.Color(187, 226, 226));
        payment.add(amountField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 226, -1));

        payButton.setBackground(new java.awt.Color(48, 173, 173));
        payButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        payButton.setForeground(new java.awt.Color(0, 0, 0));
        payButton.setText("Pay");
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });
        payment.add(payButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 100, 30));

        tab.addTab("tab2", payment);

        getContentPane().add(tab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, -1, 410));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rnFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rnFieldActionPerformed
                            
    }//GEN-LAST:event_rnFieldActionPerformed

    private void gnFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gnFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gnFieldActionPerformed

     
            
    
    
    
    
    
    
    
    
    
    
    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
    try {                                              
        // Get the database connection
        dbConnection conn = new dbConnection();
        Connection connection = conn.getConnection();
        
        // Get the text from the gnField and userField
        String gnText = gnField.getText();
        String userText = userField.getText();
        
        // Validate gnField text
        if (gnText == null || !gnText.matches("09\\d{9}")) {
            JOptionPane.showMessageDialog(this, "Invalid Input.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate userField text against the database
        boolean userExists = false;
        String query = "SELECT COUNT(*) FROM userinfo WHERE name = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userText);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                userExists = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        
        if (!userExists) {
            JOptionPane.showMessageDialog(this, "Username does not exist.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // If both conditions are met, perform the action
        tab.setSelectedIndex(1);
        
        
        
        
        
    } catch (SQLException ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
    }
                       
              retrieveLoanAmount(userField.getText(), amountField);  
              userAccount.setText(userField.getText());
              
              System.out.println(userField.getText());
                       
                       
    
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void userFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userFieldActionPerformed

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
try {
    // Get the database connection
    dbConnection conn = new dbConnection();
    Connection connection = conn.getConnection();

    // Get the text from the userField and amountField
    String userText = userField.getText();
    String amountText = amountField.getText();
    String librarian = "Mikaela";
    String admin = "Joanne";
    int totalAmount = Integer.parseInt(amountText); // Assuming amountField is for entering a numerical value

    if (connection != null) {
        // SQL query to select overdue books for the user
        String selectOverdueBooksQuery = "SELECT * FROM borrows WHERE dor < CURDATE() AND name = ?";
        PreparedStatement selectStmt = connection.prepareStatement(selectOverdueBooksQuery);
        selectStmt.setString(1, userText);
        ResultSet overdueBooksResultSet = selectStmt.executeQuery();

        int overdueBooksCount = 0;

        // Prepare statements for later use
        String insertHistoryQuery = "INSERT INTO history (title, date, status, name, amount) VALUES (?, CURDATE(), ?, ?, ?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertHistoryQuery);

        String updateBorrowsQuery = "UPDATE borrows SET last_updated = NULL, overdueDays = 0, dor = DATE_ADD(CURDATE(), INTERVAL 7 DAY) WHERE title = ? AND name = ?";
        PreparedStatement updateBorrowsStmt = connection.prepareStatement(updateBorrowsQuery);

        String deleteLoanQuery = "UPDATE borrows SET loan = 0 WHERE title = ? AND name = ?";
        PreparedStatement deleteLoanStmt = connection.prepareStatement(deleteLoanQuery);

        while (overdueBooksResultSet.next()) {
            overdueBooksCount++;
            String title = overdueBooksResultSet.getString("title");
            int amount = overdueBooksResultSet.getInt("loan");
            String status = "Overdue Paid";

            // Insert into history table
            insertStmt.setString(1, title);
            insertStmt.setString(2, status);
            insertStmt.setString(3, userText);
            insertStmt.setInt(4, amount);
            insertStmt.executeUpdate();

            // Update the borrows table
            updateBorrowsStmt.setString(1, title);
            updateBorrowsStmt.setString(2, userText);
            updateBorrowsStmt.executeUpdate();

            // Remove the loan from borrows table
            deleteLoanStmt.setString(1, title);
            deleteLoanStmt.setString(2, userText);
            deleteLoanStmt.executeUpdate();
        }

        if (overdueBooksCount > 0) {
            // Remove the loan from userinfo table
            String updateQueryUserInfo = "UPDATE userinfo SET loan = 0 WHERE name = ?";
            PreparedStatement preparedStatementUserInfo = connection.prepareStatement(updateQueryUserInfo);
            preparedStatementUserInfo.setString(1, userText);
            int rowsAffectedUserInfo = preparedStatementUserInfo.executeUpdate();

            // Update the librarian earnings
            String updateLibrarianEarningsQuery = "UPDATE librarian SET earnings = earnings + ? WHERE User = ?";
            PreparedStatement updateLibrarianEarningsStmt = connection.prepareStatement(updateLibrarianEarningsQuery);
            updateLibrarianEarningsStmt.setInt(1, totalAmount);
            updateLibrarianEarningsStmt.setString(2, librarian); 

            int librarianRowsAffected = updateLibrarianEarningsStmt.executeUpdate();
            updateLibrarianEarningsStmt.close();

            // Update the admin earnings
            String updateAdminEarningsQuery = "UPDATE admin SET earnings = earnings + ? WHERE name = ?";
            PreparedStatement updateAdminEarningsStmt = connection.prepareStatement(updateAdminEarningsQuery);
            updateAdminEarningsStmt.setInt(1, totalAmount);
            updateAdminEarningsStmt.setString(2, admin); 

            int adminRowsAffected = updateAdminEarningsStmt.executeUpdate();
            updateAdminEarningsStmt.close();

            if (rowsAffectedUserInfo > 0) {
                JOptionPane.showMessageDialog(this, "Payment successful. Please log in to your account.", "Success", JOptionPane.INFORMATION_MESSAGE);
                new LogIn().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "User not found or no changes made to userinfo.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(this, "Borrow date extended successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No overdue books found for this user.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        // Close the statements and result set
        insertStmt.close();
        updateBorrowsStmt.close();
        deleteLoanStmt.close();
        overdueBooksResultSet.close();
        selectStmt.close();

        // Close the connection
        connection.close();
    } else {
        JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
    }
} catch (SQLException ex) {
    Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(this, "Invalid amount entered. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
}

    dispose();
    }//GEN-LAST:event_payButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setState(JFrame. ICONIFIED);
    }//GEN-LAST:event_jButton2ActionPerformed

    
    
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amount;
    private javax.swing.JTextField amountField;
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel gcashNumber;
    private javax.swing.JTextField gnField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private javax.swing.JButton payButton;
    private java.awt.Panel payment;
    private javax.swing.JLabel referenceNumber;
    private javax.swing.JTextField rnField;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JLabel user;
    private javax.swing.JTextField userAccount;
    public javax.swing.JTextField userField;
    // End of variables declaration//GEN-END:variables
}
