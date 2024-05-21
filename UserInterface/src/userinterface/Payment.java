package userinterface;


import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(500, 310));

        panel2.setBackground(new java.awt.Color(38, 51, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\libitz.png")); // NOI18N

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
                .addContainerGap(167, Short.MAX_VALUE))
        );

        tab.addTab("tab2", panel2);

        payment.setBackground(new java.awt.Color(38, 51, 60));
        payment.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\libitz.png")); // NOI18N
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );

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
        
        // Get the text from the userField
        String userText = userField.getText();
        
        // Update the loan column in the userinfo table
        String updateQuery = "UPDATE userinfo SET loan = 0 WHERE name = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, userText);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                // Show success message if the update is successful
                JOptionPane.showMessageDialog(this, "Payment successful. Please log in to your account.", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                 new LogIn().setVisible(true);
                 
                
            } else {
                // Show error message if no rows were updated
                JOptionPane.showMessageDialog(this, "User not found or no changes made.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            // Show error message if there is a database error
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        
    } catch (SQLException ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
    }//GEN-LAST:event_payButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amount;
    private javax.swing.JTextField amountField;
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel gcashNumber;
    private javax.swing.JTextField gnField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
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
