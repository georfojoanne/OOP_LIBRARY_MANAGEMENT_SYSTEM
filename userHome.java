
package userinterface;

public class userHome extends javax.swing.JFrame {

   
    public userHome() {
        initComponents();
        
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel2 = new java.awt.Panel();
        panel1 = new java.awt.Panel();
        profile = new javax.swing.JLabel();
        borrowsButton = new javax.swing.JButton();
        reservationButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        historyButton = new javax.swing.JButton();
        notificationsButton = new javax.swing.JButton();
        booksButton = new javax.swing.JButton();
        userName = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Books = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(null);
        setPreferredSize(new java.awt.Dimension(940, 750));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(26, 27, 27));
        panel2.setPreferredSize(new java.awt.Dimension(1400, 20));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1140, 20));

        panel1.setBackground(new java.awt.Color(26, 27, 27));
        panel1.setPreferredSize(new java.awt.Dimension(100, 1000));
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profile.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\whiteIcon.png")); // NOI18N
        panel1.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

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
        panel1.add(borrowsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 110, -1));

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
        panel1.add(reservationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, -1, -1));

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
        panel1.add(settingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, -1, -1));

        jSeparator1.setBackground(java.awt.Color.white);
        jSeparator1.setPreferredSize(new java.awt.Dimension(70, 10));
        panel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 90, -1));

        historyButton.setBackground(new java.awt.Color(26, 27, 27));
        historyButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        historyButton.setForeground(java.awt.Color.white);
        historyButton.setText("History");
        historyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButtonActionPerformed(evt);
            }
        });
        panel1.add(historyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

        notificationsButton.setBackground(new java.awt.Color(26, 27, 27));
        notificationsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        notificationsButton.setForeground(java.awt.Color.white);
        notificationsButton.setText("Notifications");
        notificationsButton.setBorder(null);
        notificationsButton.setBorderPainted(false);
        panel1.add(notificationsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

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
        panel1.add(booksButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, 30));

        userName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        userName.setForeground(java.awt.Color.white);
        userName.setText("User Name");
        panel1.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        getContentPane().add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 790));

        jTabbedPane1.setBackground(new java.awt.Color(255, 153, 153));
        jTabbedPane1.addTab("tab1", Books);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 730, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    
    
    private void borrowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowsButtonActionPerformed
                                 
    }//GEN-LAST:event_borrowsButtonActionPerformed

    private void historyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_historyButtonActionPerformed

    private void booksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booksButtonActionPerformed
       
    }//GEN-LAST:event_booksButtonActionPerformed

    private void reservationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationButtonActionPerformed
    
        
        reservationPanel rsv = new reservationPanel();
    
             grayPanel.removeAll();
             
    // Add reservationPanelInstance to grayPanel
    grayPanel.add(rsv);
    grayPanel.revalidate();
    grayPanel.repaint();

    // Refresh the layout of grayPanel
    
         
        
    }//GEN-LAST:event_reservationButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settingsButtonActionPerformed

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
    private javax.swing.JTabbedPane Books;
    private javax.swing.JButton booksButton;
    private javax.swing.JButton borrowsButton;
    private javax.swing.JButton historyButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton notificationsButton;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private javax.swing.JLabel profile;
    private javax.swing.JButton reservationButton;
    private javax.swing.JButton settingsButton;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables
}
