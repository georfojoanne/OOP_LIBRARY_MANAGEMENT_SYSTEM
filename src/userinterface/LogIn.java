
package userinterface;

import java.sql.SQLException;
import java.sql.*;
import javax.swing.JOptionPane;

public class LogIn extends javax.swing.JFrame {
             
       
       public  LogIn (){
                     
           initComponents();
          this.setVisible(true);
          
           
             
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

        panel2.setBackground(new java.awt.Color(38, 0, 66));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userLogin.setBackground(java.awt.Color.white);
        userLogin.setForeground(new java.awt.Color(0, 0, 0));
        userLogin.setMaximumSize(null);
        userLogin.setMinimumSize(null);
        userLogin.setPreferredSize(new java.awt.Dimension(70, 23));
        userLogin.setSelectionColor(new java.awt.Color(255, 0, 255));
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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(216, 208, 208));
        jLabel4.setText("YOUR DIGITAL GATEWAY");
        jLabel4.setToolTipText("");
        panel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 173, -1));

        logInButton.setBackground(new java.awt.Color(255, 255, 255));
        logInButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logInButton.setForeground(new java.awt.Color(102, 0, 102));
        logInButton.setText("Log In");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });
        panel2.add(logInButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 267, 112, 19));

        eyeButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\eye.png")); // NOI18N
        eyeButton.setBorderPainted(false);
        eyeButton.setContentAreaFilled(false);
        eyeButton.setFocusPainted(false);
        eyeButton.setVerifyInputWhenFocusTarget(false);
        eyeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eyeButtonActionPerformed(evt);
            }
        });
        panel2.add(eyeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 20, 20));

        passwordLogIn.setBackground(java.awt.Color.white);
        passwordLogIn.setForeground(new java.awt.Color(0, 0, 0));
        passwordLogIn.setText("jPasswordField1");
        passwordLogIn.setText(null);
        passwordLogIn.setPreferredSize(new java.awt.Dimension(70, 23));
        passwordLogIn.setSelectedTextColor(new java.awt.Color(240, 207, 240));
        passwordLogIn.setSelectionColor(new java.awt.Color(255, 0, 255));
        panel2.add(passwordLogIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 248, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(216, 208, 208));
        jLabel6.setText("TO INFINITE KNOWLEDGE");
        panel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 150, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\libit.png")); // NOI18N
        panel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 177, -1));

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\Xicon.png")); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        panel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 420, 400));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\OneDrive\\JavaPractice\\UserInterface\\icons\\lights.jpg")); // NOI18N
        panel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
                          
        
       
        
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
               
            userHome user = new userHome();
             
               user.setUsername(userName);
               
            
             
                    
         
            String password = passwordLogIn.getText().trim();
            
            
            String query1 = "SELECT * FROM userinfo WHERE name=? AND Password=?";
            ps1 = connection.prepareStatement(query1);
            ps1.setString(1, userName);
            ps1.setString(2, password);
            rs1 = ps1.executeQuery();
            
          

            
            int x=0;
            if (rs1.next()) {
                
                JOptionPane.showMessageDialog(null, "Login Successful.", "Login", JOptionPane.INFORMATION_MESSAGE);
                  
                  
                new userHome().setVisible(true);
                
               
                    
              
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
                       
                       x++;
                    
                       
                    
                }
                
                
                if ( x==0) {
                
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login", JOptionPane.ERROR_MESSAGE);
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

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton eyeButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
