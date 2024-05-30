package userinterface;


import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class Librarian extends javax.swing.JFrame {
    
    public Librarian() {
        
        initComponents();
        
        Color col=new Color(28,52,62);
        getContentPane().setBackground(col);
        
       populateBooksTableFromDatabase();
       populateReturns();
       populateBorrowsTableFromDatabase();
       holds();
       populateReservationTitleTableFromDatabase();
       bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       checkAndCancelOverdueReservations();
       populateLoansTable();
       populateLoansManagementTableForOverdueBooks();
       totalEarnings();
       
              
          
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
    
    
    private void totalEarnings() {
        try {
            // Establish a database connection
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();
            
            if (connection != null) {
                String sumQuery = "SELECT SUM(amount) AS totalEarnings FROM history WHERE status = 'Overdue Paid'";
                
                try (PreparedStatement sumStmt = connection.prepareStatement(sumQuery);
                        ResultSet resultSet = sumStmt.executeQuery()) {
                    
                    if (resultSet.next()) {
                        double totalEarnings = resultSet.getDouble("totalEarnings");
                        
                        // Display the total earnings in the JTextField
                        earnings.setText(String.valueOf(totalEarnings));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Failed to establish a database connection.");
            }   } catch (SQLException ex) {
            Logger.getLogger(admins.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    
    
    
      public static void makePanelMovable(JFrame frame, JPanel jPanel1) {
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



    private void populateLoansManagementTableForOverdueBooks() {
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        String query = "SELECT title, dor, name, loan, overdueDays FROM borrows WHERE dor < CURDATE()";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        
        DefaultTableModel model = (DefaultTableModel) loansManagementTable.getModel();
            model.setRowCount(0);

        while (resultSet.next()) {
            String title = resultSet.getString("title");
            Date dor = resultSet.getDate("dor");
            String name = resultSet.getString("name");
            int fee = resultSet.getInt("loan");
            int overdueDays = resultSet.getInt("overdueDays");

            if (overdueDays > 0) {
                model.addRow(new Object[]{name, title, dor, fee});
            }
        }

        resultSet.close();
        statement.close();
        connection.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   
    public void populateLoansTable(){
        try {
        // Get the database connection
        dbConnection conn = new dbConnection();
        Connection connection = conn.getConnection();
        
        if (connection != null) {
            // SQL query to select records with status "Overdue Paid"
            String selectQuery = "SELECT name, title, date, amount FROM history WHERE status = 'Overdue Paid'";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();
            
            // Clear existing rows from loansTable
            DefaultTableModel model = (DefaultTableModel) loansTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to loansTable
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String title = resultSet.getString("title");
                String date = resultSet.getString("date");
                int amount = resultSet.getInt("amount");
                
                // Add a row to the loansTable
                model.addRow(new Object[]{name, title, date, amount});
            }
            
            // Close the connection
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
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
            java.sql.Date currentDate = new java.sql.Date(new Date().getTime());

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
                Date schedDate = resultSet.getDate("sched");

                // Notify the user
                String notificationMessage = "Dear " + name + ", you have failed to claim your book yesterday. "
                        + "With this, to ensure the book circulation of our beloved library, your reservation is automatically cancelled. - Librarian";
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
    


private void showReservationDetails() {
try {
    // Get the selected row index
    int selectedRow = reservationTitleTable.getSelectedRow();

    if (selectedRow >= 0) {
        // Get the title of the selected reservation
        String title = (String) reservationTitleTable.getValueAt(selectedRow, 0);

        // Establish a connection to the database
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        // Prepare the query to retrieve reservation details
        String query = "SELECT name, rn, sched FROM reservation WHERE title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        ResultSet resultSet = statement.executeQuery();

        // Clear existing rows from requestTable
        DefaultTableModel model = (DefaultTableModel) requestTable.getModel();
        model.setRowCount(0);

        // Populate the requestTable with data from the result set
        while (resultSet.next()) {
            String userName = resultSet.getString("name");
            int reserveNumber = resultSet.getInt("rn");
            java.sql.Date sched = resultSet.getDate("sched");

            // Add the user name, reservation number, and schedule date to requestTable
            model.addRow(new Object[]{userName, reserveNumber, sched});
        }

        // Close the resources
        resultSet.close();
        statement.close();
        connection.close(); // Close the connection
    } else {
        JOptionPane.showMessageDialog(this, "Please select a title from the table.");
    }
} catch (SQLException ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
}
    
    private void populateReservationTitleTableFromDatabase() {
        try {
            // Establish a database connection
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();

            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement("SELECT title FROM reservation");
                ResultSet resultSet = statement.executeQuery();

                // Clear existing rows from reservationTitleTable
                DefaultTableModel model = (DefaultTableModel) reservationTitleTable.getModel();
                model.setRowCount(0);
                
                // Use a set to keep track of titles already added
                Set<String> addedTitles = new HashSet<>();
                
                // Add fetched data to reservationTitleTable
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    
                    // If title is not already added, add it to the table and set
                    if (!addedTitles.contains(title)) {
                        model.addRow(new Object[]{title});
                        addedTitles.add(title);
                    }
                }

                // Close the connection
                connection.close();
            } else {
                JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void holds() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        connection = con.getConnection();

        // Prepare the SQL query to retrieve the "title" column from the "reservation" table
        String query = "SELECT title FROM reservation";
        preparedStatement = connection.prepareStatement(query);

        // Execute the query
        resultSet = preparedStatement.executeQuery();

        // Get the model of the holdsTable (JTable)
        DefaultTableModel model = (DefaultTableModel) reservationTitleTable.getModel();

        // Clear the existing rows in the model (optional, if you want to reset the table each time)
        model.setRowCount(0);

        // Iterate through the result set and add each "title" value to the holdsTable
        while (resultSet.next()) {
            String title = resultSet.getString("title");
            model.addRow(new Object[]{title});
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

  private void populateBorrowsTableFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            

            PreparedStatement statement = connection.prepareStatement("SELECT title, dob, dor, name FROM borrows");
            ResultSet resultSet = statement.executeQuery();
            
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) borrowsTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to borrowsTable
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String dob = resultSet.getString("dob");
                String dor = resultSet.getString("dor");
                String name = resultSet.getString("name");
                
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{title, dob, dor, name});
            }
            
            // Close the connection
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    
   
  private void populateReturns() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            

            PreparedStatement statement = connection.prepareStatement("SELECT name, title, dob, dor FROM returns");
            ResultSet resultSet = statement.executeQuery();
            
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) returnsTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to borrowsTable
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String title = resultSet.getString("title");
                String dob = resultSet.getString("dob");
                String dor = resultSet.getString("dor");
                
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{title, dob, dor, name});
            }
            
            // Close the connection
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    
    

  
  private void populateBooksTableFromDatabase() {
                
                     try {
    dbConnection con = new dbConnection();
    Connection connection = con.getConnection();

    PreparedStatement ps1 = null;
    ResultSet rs = null;

    try {
        String query1 = "SELECT * FROM books";
        ps1 = connection.prepareStatement(query1);
        rs = ps1.executeQuery();

        DefaultTableModel tblModel = (DefaultTableModel)bookTable.getModel();
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
  
  
 
                     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        booksPanel = new javax.swing.JPanel();
        updateButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        bookTable = new javax.swing.JTable();
        searchButtonBooks = new javax.swing.JButton();
        searchBookTitle = new javax.swing.JTextField();
        removeButton = new javax.swing.JButton();
        increment = new javax.swing.JButton();
        returnsPanel = new javax.swing.JPanel();
        confirmReturnButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        returnsTable = new javax.swing.JTable();
        borrowsSearch = new javax.swing.JTextField();
        refreshReturnsButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        reservationPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        reservationTitleTable = new javax.swing.JTable();
        reserveButton = new javax.swing.JButton();
        reservationTitleSearch = new javax.swing.JTextField();
        refreshReserveButton = new javax.swing.JButton();
        reservationTitleSearchButton = new javax.swing.JButton();
        addBookPanel = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        titleField = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        authorAdd = new javax.swing.JTextField();
        isbnAdd = new javax.swing.JTextField();
        categoryComboBox = new javax.swing.JComboBox<>();
        reservePanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        requestTable = new javax.swing.JTable();
        confirmBorrow = new javax.swing.JButton();
        requestSearch = new javax.swing.JTextField();
        notifyButton = new javax.swing.JButton();
        refreshBorrowButton = new javax.swing.JButton();
        reserveSearch = new javax.swing.JButton();
        loansPanel = new javax.swing.JPanel();
        managePaymentsButton = new javax.swing.JButton();
        refreshPaymentsButton = new javax.swing.JButton();
        loansSearch = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        loansTable = new javax.swing.JTable();
        paymentSearch = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        earnings = new javax.swing.JTextField();
        loanManagementPanel = new javax.swing.JPanel();
        confirmPayment = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        loansManagementTable = new javax.swing.JTable();
        loanManagement = new javax.swing.JTextField();
        loanManagementSearchButton = new javax.swing.JButton();
        updatePanel = new javax.swing.JPanel();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        authorUpdate = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        isbnUpdate = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        categoryUpdate = new javax.swing.JComboBox<>();
        jButton21 = new javax.swing.JButton();
        titleUpdate = new javax.swing.JTextField();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        borrowsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        borrowsTable = new javax.swing.JTable();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        borrowsSearchButton = new javax.swing.JButton();
        bSearch = new javax.swing.JTextField();
        rightmostPanel = new javax.swing.JPanel();
        returnsButton = new javax.swing.JButton();
        addBooksButton = new javax.swing.JButton();
        booksButton = new javax.swing.JButton();
        paymentsButton = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        reservationsButton = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        borrowsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(72, 96, 105));
        setForeground(new java.awt.Color(28, 52, 62));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(10, 29, 36));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/Xbang.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.setRequestFocusEnabled(false);
        jButton1.setRolloverEnabled(false);
        jButton1.setVerifyInputWhenFocusTarget(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/LIB.ITT.png"))); // NOI18N

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/minimize.png"))); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.setFocusable(false);
        jButton6.setRequestFocusEnabled(false);
        jButton6.setRolloverEnabled(false);
        jButton6.setVerifyInputWhenFocusTarget(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 704, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        makePanelMovable(this, jPanel1);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 60));

        jTabbedPane1.setBackground(new java.awt.Color(204, 185, 174));
        jTabbedPane1.setForeground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setName("Book"); // NOI18N
        jTabbedPane1.setEnabled(false);

        booksPanel.setBackground(new java.awt.Color(28, 52, 62));
        booksPanel.setForeground(new java.awt.Color(159, 212, 179));

        updateButton.setBackground(new java.awt.Color(10, 29, 36));
        updateButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        updateButton.setForeground(new java.awt.Color(0, 255, 255));
        updateButton.setText("UPDATE");
        updateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateButton.setFocusPainted(false);
        updateButton.setFocusable(false);
        updateButton.setRequestFocusEnabled(false);
        updateButton.setRolloverEnabled(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        refreshButton.setBackground(new java.awt.Color(10, 29, 36));
        refreshButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(0, 255, 255));
        refreshButton.setText("REFRESH");
        refreshButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshButton.setFocusPainted(false);
        refreshButton.setFocusable(false);
        refreshButton.setRequestFocusEnabled(false);
        refreshButton.setRolloverEnabled(false);
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        bookTable.setBackground(new java.awt.Color(221, 221, 221));
        bookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author", "ISBN", "Category", "Status", "# of Reservations", "# of Books"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bookTable.getTableHeader().setReorderingAllowed(false);
        bookTable.setDefaultEditor(Object.class, null);
        bookTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        bookTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(bookTable);
        if (bookTable.getColumnModel().getColumnCount() > 0) {
            bookTable.getColumnModel().getColumn(6).setPreferredWidth(40);
        }

        searchButtonBooks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/searchIcon.png"))); // NOI18N
        searchButtonBooks.setBorderPainted(false);
        searchButtonBooks.setContentAreaFilled(false);
        searchButtonBooks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchButtonBooks.setFocusPainted(false);
        searchButtonBooks.setFocusable(false);
        searchButtonBooks.setRequestFocusEnabled(false);
        searchButtonBooks.setRolloverEnabled(false);
        searchButtonBooks.setVerifyInputWhenFocusTarget(false);
        searchButtonBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonBooksActionPerformed(evt);
            }
        });

        searchBookTitle.setBackground(new java.awt.Color(255, 255, 255));
        searchBookTitle.setText("Search");
        searchBookTitle = new PlaceholderTextField("Search");

        removeButton.setBackground(new java.awt.Color(10, 29, 36));
        removeButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        removeButton.setForeground(new java.awt.Color(0, 255, 255));
        removeButton.setText("REMOVE");
        removeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeButton.setFocusPainted(false);
        removeButton.setRequestFocusEnabled(false);
        removeButton.setRolloverEnabled(false);
        removeButton.setVerifyInputWhenFocusTarget(false);
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        increment.setBackground(new java.awt.Color(10, 29, 36));
        increment.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        increment.setForeground(new java.awt.Color(0, 255, 255));
        increment.setText("ADD");
        increment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        increment.setFocusPainted(false);
        increment.setFocusable(false);
        increment.setRequestFocusEnabled(false);
        increment.setRolloverEnabled(false);
        increment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incrementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout booksPanelLayout = new javax.swing.GroupLayout(booksPanel);
        booksPanel.setLayout(booksPanelLayout);
        booksPanelLayout.setHorizontalGroup(
            booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(booksPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(booksPanelLayout.createSequentialGroup()
                        .addComponent(searchBookTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButtonBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(booksPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(booksPanelLayout.createSequentialGroup()
                                .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(increment, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(25, 25, 25))))
        );
        booksPanelLayout.setVerticalGroup(
            booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(booksPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchButtonBooks)
                    .addComponent(searchBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(increment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        jTabbedPane1.addTab("tab1", booksPanel);

        returnsPanel.setBackground(new java.awt.Color(28, 52, 62));
        returnsPanel.setName("Book"); // NOI18N
        returnsPanel.setNextFocusableComponent(booksButton);

        confirmReturnButton.setBackground(new java.awt.Color(10, 29, 36));
        confirmReturnButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        confirmReturnButton.setForeground(new java.awt.Color(0, 255, 255));
        confirmReturnButton.setText("CONFIRM RETURN");
        confirmReturnButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmReturnButton.setFocusPainted(false);
        confirmReturnButton.setFocusable(false);
        confirmReturnButton.setRequestFocusEnabled(false);
        confirmReturnButton.setRolloverEnabled(false);
        confirmReturnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmReturnButtonActionPerformed(evt);
            }
        });

        returnsTable.setBackground(new java.awt.Color(221, 221, 221));
        returnsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Date of Borrow", "Due Date", "User Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        returnsTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        returnsTable.getTableHeader().setReorderingAllowed(false);
        returnsTable.setDefaultEditor(Object.class, null);
        jScrollPane2.setViewportView(returnsTable);

        borrowsSearch.setBackground(new java.awt.Color(255, 255, 255));
        borrowsSearch.setText("Search");
        borrowsSearch = new PlaceholderTextField("Search");
        borrowsSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowsSearchActionPerformed(evt);
            }
        });

        refreshReturnsButton.setBackground(new java.awt.Color(10, 29, 36));
        refreshReturnsButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        refreshReturnsButton.setForeground(new java.awt.Color(0, 255, 255));
        refreshReturnsButton.setText("REFRESH");
        refreshReturnsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshReturnsButton.setFocusPainted(false);
        refreshReturnsButton.setFocusable(false);
        refreshReturnsButton.setRequestFocusEnabled(false);
        refreshReturnsButton.setRolloverEnabled(false);
        refreshReturnsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshReturnsButtonActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/searchIcon.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        javax.swing.GroupLayout returnsPanelLayout = new javax.swing.GroupLayout(returnsPanel);
        returnsPanel.setLayout(returnsPanelLayout);
        returnsPanelLayout.setHorizontalGroup(
            returnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(returnsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(returnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(returnsPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(confirmReturnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(refreshReturnsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(returnsPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(returnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(returnsPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
                            .addGroup(returnsPanelLayout.createSequentialGroup()
                                .addComponent(borrowsSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))))))
        );
        returnsPanelLayout.setVerticalGroup(
            returnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, returnsPanelLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(returnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borrowsSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(returnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmReturnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshReturnsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", returnsPanel);

        reservationPanel.setBackground(new java.awt.Color(28, 52, 62));
        reservationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reservationTitleTable.setBackground(new java.awt.Color(221, 221, 221));
        reservationTitleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reservationTitleTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        reservationTitleTable.getTableHeader().setReorderingAllowed(false);
        reservationTitleTable.setDefaultEditor(Object.class, null);
        jScrollPane3.setViewportView(reservationTitleTable);

        reservationPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 680, 510));

        reserveButton.setBackground(new java.awt.Color(10, 29, 36));
        reserveButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        reserveButton.setForeground(new java.awt.Color(0, 255, 255));
        reserveButton.setText("RESERVES");
        reserveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reserveButton.setFocusPainted(false);
        reserveButton.setFocusable(false);
        reserveButton.setRequestFocusEnabled(false);
        reserveButton.setRolloverEnabled(false);
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveButtonActionPerformed(evt);
            }
        });
        reservationPanel.add(reserveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, 287, 42));

        reservationTitleSearch.setBackground(new java.awt.Color(255, 255, 255));
        reservationTitleSearch.setText("Search");
        reservationTitleSearch = new PlaceholderTextField("Search");
        reservationPanel.add(reservationTitleSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 630, -1));

        refreshReserveButton.setBackground(new java.awt.Color(10, 29, 36));
        refreshReserveButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        refreshReserveButton.setForeground(new java.awt.Color(0, 255, 255));
        refreshReserveButton.setText("REFRESH");
        refreshReserveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshReserveButton.setFocusPainted(false);
        refreshReserveButton.setFocusable(false);
        refreshReserveButton.setRequestFocusEnabled(false);
        refreshReserveButton.setRolloverEnabled(false);
        refreshReserveButton.setVerifyInputWhenFocusTarget(false);
        refreshReserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshReserveButtonActionPerformed(evt);
            }
        });
        reservationPanel.add(refreshReserveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 582, 287, 42));

        reservationTitleSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/searchIcon.png"))); // NOI18N
        reservationTitleSearchButton.setBorderPainted(false);
        reservationTitleSearchButton.setContentAreaFilled(false);
        reservationTitleSearchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reservationTitleSearchButton.setFocusPainted(false);
        reservationTitleSearchButton.setFocusable(false);
        reservationTitleSearchButton.setRequestFocusEnabled(false);
        reservationTitleSearchButton.setRolloverEnabled(false);
        reservationTitleSearchButton.setVerifyInputWhenFocusTarget(false);
        reservationTitleSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservationTitleSearchButtonActionPerformed(evt);
            }
        });
        reservationPanel.add(reservationTitleSearchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        jTabbedPane1.addTab("tab3", reservationPanel);

        addBookPanel.setBackground(new java.awt.Color(28, 52, 62));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(131, 157, 167));
        jTextField5.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(10, 29, 36));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("TITLE");
        jTextField5.setAutoscrolls(false);
        jTextField5.setEditable(false);
        jTextField5.setFocusable(false);
        jTextField5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        addButton.setBackground(new java.awt.Color(10, 29, 36));
        addButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        addButton.setForeground(new java.awt.Color(0, 255, 255));
        addButton.setText("ADD");
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.setFocusPainted(false);
        addButton.setFocusable(false);
        addButton.setRequestFocusEnabled(false);
        addButton.setRolloverEnabled(false);
        addButton.setVerifyInputWhenFocusTarget(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(131, 157, 167));
        jTextField6.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(10, 29, 36));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setText("AUTHOR");
        jTextField6.setAutoscrolls(false);
        jTextField6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField6.setEditable(false);
        jTextField6.setFocusable(false);

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(131, 157, 167));
        jTextField7.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(10, 29, 36));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setText("ISBN");
        jTextField7.setAutoscrolls(false);
        jTextField7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jTextField7.setEditable(false);
        jTextField7.setFocusable(false);

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(131, 157, 167));
        jTextField8.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(10, 29, 36));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField8.setText("CATEGORY");
        jTextField8.setAutoscrolls(false);
        jTextField8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        jTextField8.setFocusable(false);

        titleField.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        titleField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleFieldActionPerformed(evt);
            }
        });
        titleField = new PlaceholderTextField("Enter title here");

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(131, 157, 167));
        jTextField10.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(10, 29, 36));
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField10.setText("ADD BOOKS");
        jTextField10.setAutoscrolls(false);
        jTextField10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jTextField10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField10.setEditable(false);
        jTextField10.setFocusable(false);

        authorAdd.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        authorAdd = new PlaceholderTextField("Enter author here");

        isbnAdd.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        isbnAdd = new PlaceholderTextField("Enter ISBN here");

        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Science", "Math" }));
        categoryComboBox.setSelectedItem(categoryComboBox.getSelectedItem());
        categoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addBookPanelLayout = new javax.swing.GroupLayout(addBookPanel);
        addBookPanel.setLayout(addBookPanelLayout);
        addBookPanelLayout.setHorizontalGroup(
            addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBookPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField10)
                    .addGroup(addBookPanelLayout.createSequentialGroup()
                        .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isbnAdd)
                            .addComponent(authorAdd)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addBookPanelLayout.createSequentialGroup()
                                .addGap(0, 11, Short.MAX_VALUE)
                                .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(categoryComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(addBookPanelLayout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        addBookPanelLayout.setVerticalGroup(
            addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addBookPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titleField)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isbnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(categoryComboBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("tab5", addBookPanel);

        reservePanel.setBackground(new java.awt.Color(28, 52, 62));
        reservePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        requestTable.setBackground(new java.awt.Color(221, 221, 221));
        requestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Reservation Number", "Scheduled Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        requestTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        jScrollPane4.setViewportView(requestTable);
        requestTable.getTableHeader().setReorderingAllowed(false);
        requestTable.setDefaultEditor(Object.class, null);

        reservePanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 55, 678, 500));

        confirmBorrow.setBackground(new java.awt.Color(10, 29, 36));
        confirmBorrow.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        confirmBorrow.setForeground(new java.awt.Color(0, 255, 255));
        confirmBorrow.setText("CONFIRM BORROW");
        confirmBorrow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmBorrow.setFocusPainted(false);
        confirmBorrow.setFocusable(false);
        confirmBorrow.setRequestFocusEnabled(false);
        confirmBorrow.setRolloverEnabled(false);
        confirmBorrow.setVerifyInputWhenFocusTarget(false);
        confirmBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBorrowActionPerformed(evt);
            }
        });
        reservePanel.add(confirmBorrow, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 573, 242, 48));

        requestSearch.setBackground(new java.awt.Color(255, 255, 255));
        requestSearch.setText("Search");
        requestSearch = new PlaceholderTextField("Search");
        reservePanel.add(requestSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 634, -1));

        notifyButton.setBackground(new java.awt.Color(10, 29, 36));
        notifyButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        notifyButton.setForeground(new java.awt.Color(0, 255, 255));
        notifyButton.setText("NOTIFY");
        notifyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notifyButton.setFocusPainted(false);
        notifyButton.setFocusable(false);
        notifyButton.setRequestFocusEnabled(false);
        notifyButton.setRolloverEnabled(false);
        notifyButton.setVerifyInputWhenFocusTarget(false);
        notifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notifyButtonActionPerformed(evt);
            }
        });
        reservePanel.add(notifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 573, 162, 48));

        refreshBorrowButton.setBackground(new java.awt.Color(10, 29, 36));
        refreshBorrowButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        refreshBorrowButton.setForeground(new java.awt.Color(0, 255, 255));
        refreshBorrowButton.setText("REFRESH");
        refreshBorrowButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshBorrowButton.setFocusPainted(false);
        refreshBorrowButton.setFocusable(false);
        refreshBorrowButton.setRequestFocusEnabled(false);
        refreshBorrowButton.setRolloverEnabled(false);
        refreshBorrowButton.setVerifyInputWhenFocusTarget(false);
        refreshBorrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBorrowButtonActionPerformed(evt);
            }
        });
        reservePanel.add(refreshBorrowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 570, 162, 48));

        reserveSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/searchIcon.png"))); // NOI18N
        reserveSearch.setBorderPainted(false);
        reserveSearch.setContentAreaFilled(false);
        reserveSearch.setFocusPainted(false);
        reserveSearch.setFocusable(false);
        reserveSearch.setRequestFocusEnabled(false);
        reserveSearch.setRolloverEnabled(false);
        reserveSearch.setVerifyInputWhenFocusTarget(false);
        reserveSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveSearchActionPerformed(evt);
            }
        });
        reservePanel.add(reserveSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        jTabbedPane1.addTab("tab6", reservePanel);

        loansPanel.setBackground(new java.awt.Color(28, 52, 62));
        loansPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        managePaymentsButton.setBackground(new java.awt.Color(10, 29, 36));
        managePaymentsButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        managePaymentsButton.setForeground(new java.awt.Color(0, 255, 255));
        managePaymentsButton.setText("MANAGE PAYMENTS");
        managePaymentsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        managePaymentsButton.setFocusPainted(false);
        managePaymentsButton.setFocusable(false);
        managePaymentsButton.setRequestFocusEnabled(false);
        managePaymentsButton.setRolloverEnabled(false);
        managePaymentsButton.setVerifyInputWhenFocusTarget(false);
        managePaymentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managePaymentsButtonActionPerformed(evt);
            }
        });
        loansPanel.add(managePaymentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 575, 406, 48));

        refreshPaymentsButton.setBackground(new java.awt.Color(10, 29, 36));
        refreshPaymentsButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        refreshPaymentsButton.setForeground(new java.awt.Color(0, 255, 255));
        refreshPaymentsButton.setText("REFRESH");
        refreshPaymentsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshPaymentsButton.setFocusPainted(false);
        refreshPaymentsButton.setFocusable(false);
        refreshPaymentsButton.setRequestFocusEnabled(false);
        refreshPaymentsButton.setRolloverEnabled(false);
        refreshPaymentsButton.setVerifyInputWhenFocusTarget(false);
        refreshPaymentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPaymentsButtonActionPerformed(evt);
            }
        });
        loansPanel.add(refreshPaymentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 575, 260, 48));

        loansSearch.setBackground(new java.awt.Color(255, 255, 255));
        loansSearch.setText("Search");
        loansSearch = new PlaceholderTextField("Search");
        loansPanel.add(loansSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 630, -1));

        loansTable.setBackground(new java.awt.Color(221, 221, 221));
        loansTable.setForeground(new java.awt.Color(0, 0, 0));
        loansTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Book", "Paid Date", "Paid Fee"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        loansTable.setFocusable(false);
        loansTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        jScrollPane7.setViewportView(loansTable);
        loansTable.getTableHeader().setReorderingAllowed(false);
        loansTable.setDefaultEditor(Object.class, null);

        loansPanel.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 680, 470));

        paymentSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/searchIcon.png"))); // NOI18N
        paymentSearch.setBorderPainted(false);
        paymentSearch.setContentAreaFilled(false);
        paymentSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        paymentSearch.setFocusPainted(false);
        paymentSearch.setFocusable(false);
        paymentSearch.setRequestFocusEnabled(false);
        paymentSearch.setRolloverEnabled(false);
        paymentSearch.setVerifyInputWhenFocusTarget(false);
        paymentSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentSearchActionPerformed(evt);
            }
        });
        loansPanel.add(paymentSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        jTextField1.setBackground(new java.awt.Color(221, 221, 221));
        jTextField1.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(28, 52, 62));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("EARNINGS:");
        jTextField1.setFocusable(false);
        jTextField1.setRequestFocusEnabled(false);
        jTextField1.setEditable(false);
        jTextField1.setFocusable(false);
        loansPanel.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 536, 180, 30));

        earnings.setBackground(new java.awt.Color(49, 98, 103));
        earnings.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        earnings.setForeground(new java.awt.Color(255, 255, 255));
        earnings.setEditable(false);
        earnings.setFocusable(false);
        loansPanel.add(earnings, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 536, 480, 30));

        jTabbedPane1.addTab("tab7", loansPanel);

        loanManagementPanel.setBackground(new java.awt.Color(28, 52, 62));
        loanManagementPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        confirmPayment.setBackground(new java.awt.Color(10, 29, 36));
        confirmPayment.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        confirmPayment.setForeground(new java.awt.Color(0, 255, 255));
        confirmPayment.setText("CONFIRM PAYMENT");
        confirmPayment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmPayment.setFocusPainted(false);
        confirmPayment.setFocusable(false);
        confirmPayment.setRequestFocusEnabled(false);
        confirmPayment.setRolloverEnabled(false);
        confirmPayment.setVerifyInputWhenFocusTarget(false);
        confirmPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmPaymentActionPerformed(evt);
            }
        });
        loanManagementPanel.add(confirmPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 260, 48));

        jButton26.setBackground(new java.awt.Color(10, 29, 36));
        jButton26.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton26.setForeground(new java.awt.Color(0, 255, 255));
        jButton26.setText("REFRESH");
        jButton26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton26.setFocusPainted(false);
        jButton26.setFocusable(false);
        jButton26.setRequestFocusEnabled(false);
        jButton26.setRolloverEnabled(false);
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        loanManagementPanel.add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 580, 260, 48));

        loansManagementTable.setBackground(new java.awt.Color(221, 221, 221));
        loansManagementTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Book", "Due Date", "Fee"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        loansManagementTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        loansManagementTable.getTableHeader().setReorderingAllowed(false);
        loansManagementTable.setDefaultEditor(Object.class, null);
        jScrollPane6.setViewportView(loansManagementTable);

        loanManagementPanel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 680, 526));

        loanManagement.setBackground(new java.awt.Color(255, 255, 255));
        loanManagement.setText("Search");
        loanManagement = new PlaceholderTextField("Search");
        loanManagementPanel.add(loanManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 630, -1));

        loanManagementSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/searchIcon.png"))); // NOI18N
        loanManagementSearchButton.setBorderPainted(false);
        loanManagementSearchButton.setContentAreaFilled(false);
        loanManagementSearchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loanManagementSearchButton.setFocusPainted(false);
        loanManagementSearchButton.setFocusable(false);
        loanManagementSearchButton.setRequestFocusEnabled(false);
        loanManagementSearchButton.setRolloverEnabled(false);
        loanManagementSearchButton.setVerifyInputWhenFocusTarget(false);
        loanManagementSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanManagementSearchButtonActionPerformed(evt);
            }
        });
        loanManagementPanel.add(loanManagementSearchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        jTabbedPane1.addTab("tab8", loanManagementPanel);

        updatePanel.setBackground(new java.awt.Color(28, 52, 62));

        jTextField13.setEditable(false);
        jTextField13.setBackground(new java.awt.Color(131, 157, 167));
        jTextField13.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(10, 29, 36));
        jTextField13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField13.setText("UPDATE BOOK");
        jTextField13.setAutoscrolls(false);
        jTextField13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jTextField13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });
        jTextField13.setFocusable(false);

        jTextField14.setEditable(false);
        jTextField14.setBackground(new java.awt.Color(131, 157, 167));
        jTextField14.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(10, 29, 36));
        jTextField14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField14.setText("TITLE");
        jTextField14.setAutoscrolls(false);
        jTextField14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField14.setFocusable(false);

        jTextField16.setEditable(false);
        jTextField16.setBackground(new java.awt.Color(131, 157, 167));
        jTextField16.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(10, 29, 36));
        jTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField16.setText("AUTHOR");
        jTextField16.setAutoscrolls(false);
        jTextField16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField16.setFocusable(false);

        authorUpdate.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        authorUpdate.setText("Author");
        authorUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorUpdateActionPerformed(evt);
            }
        });
        authorUpdate.setEditable(false);
        authorUpdate.setFocusable(false);
        authorUpdate.setForeground(Color.GRAY);

        jTextField18.setEditable(false);
        jTextField18.setBackground(new java.awt.Color(131, 157, 167));
        jTextField18.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(10, 29, 36));
        jTextField18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField18.setText("ISBN");
        jTextField18.setAutoscrolls(false);
        jTextField18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });
        jTextField18.setFocusable(false);

        isbnUpdate.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        isbnUpdate.setText("ISBN");
        isbnUpdate.setEditable(false);
        isbnUpdate.setFocusable(false);
        isbnUpdate.setForeground(Color.GRAY);

        jTextField20.setEditable(false);
        jTextField20.setBackground(new java.awt.Color(131, 157, 167));
        jTextField20.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField20.setForeground(new java.awt.Color(10, 29, 36));
        jTextField20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField20.setText("CATEGORY");
        jTextField20.setAutoscrolls(false);
        jTextField20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20ActionPerformed(evt);
            }
        });
        jTextField20.setFocusable(false);

        categoryUpdate.setBackground(new java.awt.Color(10, 29, 36));
        categoryUpdate.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 14)); // NOI18N
        categoryUpdate.setForeground(new java.awt.Color(0, 255, 255));
        categoryUpdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Science", "Math" }));
        categoryUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryUpdateActionPerformed(evt);
            }
        });
        categoryUpdate.setEditable(false);
        categoryUpdate.setFocusable(false);
        categoryUpdate.setEnabled(false);
        categoryUpdate.setForeground(Color.CYAN);

        jButton21.setBackground(new java.awt.Color(10, 29, 36));
        jButton21.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton21.setForeground(new java.awt.Color(0, 255, 255));
        jButton21.setText("UPDATE");
        jButton21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        titleUpdate.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        titleUpdate.setText("Title");
        titleUpdate.setEditable(false);
        titleUpdate.setFocusable(false);
        titleUpdate.setForeground(Color.GRAY);
        titleUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleUpdateActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(10, 29, 36));
        jButton23.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton23.setForeground(new java.awt.Color(0, 255, 255));
        jButton23.setText("EDIT");
        jButton23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setBackground(new java.awt.Color(10, 29, 36));
        jButton24.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton24.setForeground(new java.awt.Color(0, 255, 255));
        jButton24.setText("SAVE");
        jButton24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout updatePanelLayout = new javax.swing.GroupLayout(updatePanel);
        updatePanel.setLayout(updatePanelLayout);
        updatePanelLayout.setHorizontalGroup(
            updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePanelLayout.createSequentialGroup()
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updatePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField13)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updatePanelLayout.createSequentialGroup()
                                .addComponent(jTextField20, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(categoryUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updatePanelLayout.createSequentialGroup()
                                .addComponent(jTextField14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(titleUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(updatePanelLayout.createSequentialGroup()
                                .addComponent(jTextField16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(authorUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updatePanelLayout.createSequentialGroup()
                                .addComponent(jTextField18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(isbnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(updatePanelLayout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(updatePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        updatePanelLayout.setVerticalGroup(
            updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isbnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        jTabbedPane1.addTab("tab4", updatePanel);

        borrowsPanel.setBackground(new java.awt.Color(28, 52, 62));
        borrowsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        borrowsTable.setBackground(new java.awt.Color(221, 221, 221));
        borrowsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title", "Date of Borrow", "Date of Return", "User Name"
            }
        ));
        borrowsTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        borrowsTable.getTableHeader().setReorderingAllowed(false);
        borrowsTable.setDefaultEditor(Object.class, null);
        jScrollPane1.setViewportView(borrowsTable);

        borrowsPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 61, 670, 497));

        jButton30.setBackground(new java.awt.Color(10, 29, 36));
        jButton30.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton30.setForeground(new java.awt.Color(0, 255, 255));
        jButton30.setText("REFRESH");
        jButton30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton30.setFocusPainted(false);
        jButton30.setFocusable(false);
        jButton30.setRequestFocusEnabled(false);
        jButton30.setRolloverEnabled(false);
        jButton30.setVerifyInputWhenFocusTarget(false);
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        borrowsPanel.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 576, 162, 48));

        jButton31.setBackground(new java.awt.Color(10, 29, 36));
        jButton31.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton31.setForeground(new java.awt.Color(0, 255, 255));
        jButton31.setText("OVERDUES");
        jButton31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton31.setFocusPainted(false);
        jButton31.setFocusable(false);
        jButton31.setRequestFocusEnabled(false);
        jButton31.setRolloverEnabled(false);
        jButton31.setVerifyInputWhenFocusTarget(false);
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        borrowsPanel.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 576, 162, 48));

        borrowsSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/searchIcon.png"))); // NOI18N
        borrowsSearchButton.setBorderPainted(false);
        borrowsSearchButton.setContentAreaFilled(false);
        borrowsSearchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        borrowsSearchButton.setFocusPainted(false);
        borrowsSearchButton.setFocusable(false);
        borrowsSearchButton.setRequestFocusEnabled(false);
        borrowsSearchButton.setRolloverEnabled(false);
        borrowsSearchButton.setVerifyInputWhenFocusTarget(false);
        borrowsSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowsSearchButtonActionPerformed(evt);
            }
        });
        borrowsPanel.add(borrowsSearchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, -1, 40));

        bSearch.setBackground(new java.awt.Color(255, 255, 255));
        bSearch.setText("Search");
        bSearch.setRequestFocusEnabled(false);
        bSearch = new PlaceholderTextField("Search");
        bSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSearchActionPerformed(evt);
            }
        });
        borrowsPanel.add(bSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 620, 30));

        jTabbedPane1.addTab("tab9", borrowsPanel);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 710, 680));

        rightmostPanel.setBackground(new java.awt.Color(10, 29, 36));
        setFrameIcon("libIcon.png");

        setTitle("Lib.IT - Librarian");

        returnsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        returnsButton.setForeground(new java.awt.Color(255, 255, 255));
        returnsButton.setText("Returns");
        returnsButton.setBorder(null);
        returnsButton.setBorderPainted(false);
        returnsButton.setContentAreaFilled(false);
        returnsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        returnsButton.setFocusPainted(false);
        returnsButton.setFocusable(false);
        returnsButton.setRequestFocusEnabled(false);
        returnsButton.setRolloverEnabled(false);
        returnsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnsButtonActionPerformed(evt);
            }
        });

        addBooksButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        addBooksButton.setForeground(new java.awt.Color(255, 255, 255));
        addBooksButton.setText("Add Books");
        addBooksButton.setBorder(null);
        addBooksButton.setBorderPainted(false);
        addBooksButton.setContentAreaFilled(false);
        addBooksButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addBooksButton.setFocusPainted(false);
        addBooksButton.setFocusable(false);
        addBooksButton.setRequestFocusEnabled(false);
        addBooksButton.setRolloverEnabled(false);
        addBooksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBooksButtonActionPerformed(evt);
            }
        });

        booksButton.setBackground(new java.awt.Color(26, 27, 27));
        booksButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        booksButton.setForeground(new java.awt.Color(255, 255, 255));
        booksButton.setText("Books");
        booksButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        booksButton.setBorderPainted(false);
        booksButton.setContentAreaFilled(false);
        booksButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        booksButton.setFocusable(false);
        booksButton.setRequestFocusEnabled(false);
        booksButton.setRolloverEnabled(false);
        booksButton.setVerifyInputWhenFocusTarget(false);
        booksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booksButtonActionPerformed(evt);
            }
        });

        paymentsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        paymentsButton.setForeground(new java.awt.Color(255, 255, 255));
        paymentsButton.setText("Payments");
        paymentsButton.setBorder(null);
        paymentsButton.setBorderPainted(false);
        paymentsButton.setContentAreaFilled(false);
        paymentsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        paymentsButton.setFocusPainted(false);
        paymentsButton.setFocusable(false);
        paymentsButton.setRequestFocusEnabled(false);
        paymentsButton.setRolloverEnabled(false);
        paymentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsButtonActionPerformed(evt);
            }
        });

        jTextField2.setBackground(new java.awt.Color(10, 29, 36));
        jTextField2.setFont(new java.awt.Font("Stylus BT", 0, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("You are logged in as");
        jTextField2.setBorder(null);
        jTextField2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField2.setEditable(false);
        jTextField2.setFocusable(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField21.setEditable(false);
        jTextField21.setBackground(new java.awt.Color(28, 52, 62));
        jTextField21.setFont(new java.awt.Font("Stylus BT", 0, 18)); // NOI18N
        jTextField21.setForeground(new java.awt.Color(255, 255, 255));
        jTextField21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField21.setText("LIBRARIAN");
        jTextField21.setBorder(null);
        jTextField21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField21.setEditable(false);
        jTextField21.setFocusable(false);

        reservationsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        reservationsButton.setForeground(new java.awt.Color(255, 255, 255));
        reservationsButton.setText("Reservations");
        reservationsButton.setBorder(null);
        reservationsButton.setBorderPainted(false);
        reservationsButton.setContentAreaFilled(false);
        reservationsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reservationsButton.setFocusPainted(false);
        reservationsButton.setFocusable(false);
        reservationsButton.setRequestFocusEnabled(false);
        reservationsButton.setRolloverEnabled(false);
        reservationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservationsButtonActionPerformed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        jButton17.setForeground(new java.awt.Color(0, 255, 255));
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_files/log out.png"))); // NOI18N
        jButton17.setText("LOG OUT");
        jButton17.setBorderPainted(false);
        jButton17.setContentAreaFilled(false);
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        borrowsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        borrowsButton.setForeground(new java.awt.Color(255, 255, 255));
        borrowsButton.setText("Borrows");
        borrowsButton.setBorder(null);
        borrowsButton.setBorderPainted(false);
        borrowsButton.setContentAreaFilled(false);
        borrowsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        borrowsButton.setFocusPainted(false);
        borrowsButton.setFocusable(false);
        borrowsButton.setRequestFocusEnabled(false);
        borrowsButton.setRolloverEnabled(false);
        borrowsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rightmostPanelLayout = new javax.swing.GroupLayout(rightmostPanel);
        rightmostPanel.setLayout(rightmostPanelLayout);
        rightmostPanelLayout.setHorizontalGroup(
            rightmostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightmostPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rightmostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightmostPanelLayout.createSequentialGroup()
                        .addGap(0, 42, Short.MAX_VALUE)
                        .addGroup(rightmostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(rightmostPanelLayout.createSequentialGroup()
                                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGap(75, 75, 75))
                    .addGroup(rightmostPanelLayout.createSequentialGroup()
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(rightmostPanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(rightmostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(reservationsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(paymentsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addBooksButton, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(borrowsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(returnsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(booksButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        rightmostPanelLayout.setVerticalGroup(
            rightmostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightmostPanelLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addGap(63, 63, 63)
                .addComponent(booksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borrowsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addBooksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paymentsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reservationsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );

        getContentPane().add(rightmostPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 57, -1, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void returnsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnsButtonActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        
        returnsButton.setForeground(new Color(0,225,225));
                     booksButton.setForeground(new Color(225,225,255));
                     borrowsButton.setForeground(new Color(225,225,255));                   
                     addBooksButton.setForeground(new Color(225,225,255));
                     paymentsButton.setForeground(new Color(225,225,255));
                     reservationsButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_returnsButtonActionPerformed

    private void addBooksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBooksButtonActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        
        addBooksButton.setForeground(new Color(0,225,225));
                     booksButton.setForeground(new Color(225,225,255));
                     returnsButton.setForeground(new Color(225,225,255));                   
                     borrowsButton.setForeground(new Color(225,225,255));
                     paymentsButton.setForeground(new Color(225,225,255));
                     reservationsButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_addBooksButtonActionPerformed

    
    private void booksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booksButtonActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        
        booksButton.setForeground(new Color(0,225,225));
                     returnsButton.setForeground(new Color(225,225,255));
                     borrowsButton.setForeground(new Color(225,225,255));                   
                     addBooksButton.setForeground(new Color(225,225,255));
                     paymentsButton.setForeground(new Color(225,225,255));
                     reservationsButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_booksButtonActionPerformed

            
    private void paymentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentsButtonActionPerformed
        jTabbedPane1.setSelectedIndex(5);
        
        paymentsButton.setForeground(new Color(0,225,225));
                     booksButton.setForeground(new Color(225,225,255));
                     returnsButton.setForeground(new Color(225,225,255));                   
                     borrowsButton.setForeground(new Color(225,225,255));
                     addBooksButton.setForeground(new Color(225,225,255));
                     reservationsButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_paymentsButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    removeButton.setForeground(new Color(0, 225, 255));
    updateButton.setForeground(new Color(225, 225, 225));
    refreshButton.setForeground(new Color(0, 225, 255));

        jTabbedPane1.setSelectedIndex(7);
           int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the values from the selected row and columns
            String title = (String) bookTable.getValueAt(selectedRow, 0);
            String author = (String) bookTable.getValueAt(selectedRow, 1);
            String isbn = (String) bookTable.getValueAt(selectedRow, 2);
            String category = (String) bookTable.getValueAt(selectedRow, 3);

            // Set the values to the text fields and combo box
            titleUpdate.setText(title);
            authorUpdate.setText(author);
            isbnUpdate.setText(isbn);
            categoryUpdate.setSelectedItem(category);
        }

    }//GEN-LAST:event_updateButtonActionPerformed
 
    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    private void jTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField20ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void managePaymentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managePaymentsButtonActionPerformed
    refreshPaymentsButton.setForeground(new Color(0, 225, 255));
    managePaymentsButton.setForeground(new Color(225, 225, 225));   

        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_managePaymentsButtonActionPerformed

    private void authorUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorUpdateActionPerformed

    private void titleUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleUpdateActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        populateBooksTableFromDatabase();
        
    removeButton.setForeground(new Color(0, 225, 255));
    refreshButton.setForeground(new Color(225, 225, 225));
    updateButton.setForeground(new Color(0, 225, 255));
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed

    refreshButton.setForeground(new Color(0, 225, 255));
    removeButton.setForeground(new Color(225, 225, 225));
    updateButton.setForeground(new Color(0, 225, 255));
        
        try {
    // Get selected rows
    int[] selectedRows = bookTable.getSelectedRows();

    // For the connection
    dbConnection con = new dbConnection();
    Connection connection = con.getConnection();

    if (connection != null) {
        for (int rowIndex : selectedRows) {
            if (rowIndex >= 0 && rowIndex < bookTable.getRowCount()) { //this validates the index of the row
                // Get the title from the selected row
                String title = (String) bookTable.getValueAt(rowIndex, 0);

                // Delete from borrows table
                String borrowsQuery = "DELETE FROM borrows WHERE title = ?";
                try (PreparedStatement borrowsPstmt = connection.prepareStatement(borrowsQuery)) {
                    borrowsPstmt.setString(1, title);
                    borrowsPstmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Delete from reservation table
                String reservationQuery = "DELETE FROM reservation WHERE title = ?";
                try (PreparedStatement reservationPstmt = connection.prepareStatement(reservationQuery)) {
                    reservationPstmt.setString(1, title);
                    reservationPstmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Delete from reserves table
                String reservesQuery = "DELETE FROM reserves WHERE title = ?";
                try (PreparedStatement reservesPstmt = connection.prepareStatement(reservesQuery)) {
                    reservesPstmt.setString(1, title);
                    reservesPstmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Delete from books table
                String booksQuery = "DELETE FROM books WHERE title = ?";
                try (PreparedStatement booksPstmt = connection.prepareStatement(booksQuery)) {
                    booksPstmt.setString(1, title);
                    booksPstmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.err.println("Invalid row index: " + rowIndex);
            }
        }
        // this automatically refresh the table after deletion
        populateBooksTableFromDatabase();
    }
} catch (SQLException ex) {
    ex.printStackTrace();
}
    }//GEN-LAST:event_removeButtonActionPerformed

    private void titleFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleFieldActionPerformed

    }//GEN-LAST:event_titleFieldActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed

    try {                                          
        
        String title = titleField.getText();
        String author = authorAdd.getText();
        String isbn = isbnAdd.getText();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        
        if (selectedCategory == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid category.");
            return;
        }
        
        // Check if the input matches the placeholder text
        if ("Enter title here".equals(title) || "Enter author here".equals(author) || "Enter ISBN here".equals(isbn)) {
            JOptionPane.showMessageDialog(this, "Please enter valid input for all fields.");
            return;
        }
        
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
            return;
        }
        
        // Use dbConnection to establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        // Check if a book with the same ISBN or title already exists
        String checkQuery = "SELECT COUNT(*) FROM books WHERE ISBN = ? OR title = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, isbn);
            checkStmt.setString(2, title);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // If count is greater than 0, data already exists
                JOptionPane.showMessageDialog(this, "Similar data already exists.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                return; // Exit the method without adding the book
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle SQL exceptions appropriately
        }
        
        // If no similar data found, proceed to add the book to the database
        addBookToDatabase(title, author, isbn, selectedCategory);
        
        // Clear the textfields
        titleField.setText("Enter title here");
        authorAdd.setText("Enter author here");
        isbnAdd.setText("Enter ISBN here");
        
        // Close the connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle SQL exceptions appropriately
        }
        
    } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }//GEN-LAST:event_addButtonActionPerformed

    private void reservationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationsButtonActionPerformed
        jTabbedPane1.setSelectedIndex(2);
        
        reservationsButton.setForeground(new Color(0,225,225));
                     booksButton.setForeground(new Color(225,225,255));
                     returnsButton.setForeground(new Color(225,225,255));                   
                     borrowsButton.setForeground(new Color(225,225,255));
                     addBooksButton.setForeground(new Color(225,225,255));
                     paymentsButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_reservationsButtonActionPerformed

    private void categoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryComboBoxActionPerformed
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        System.out.println(selectedCategory);
    }//GEN-LAST:event_categoryComboBoxActionPerformed

    private void categoryUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryUpdateActionPerformed
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
    }//GEN-LAST:event_categoryUpdateActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
 // Get the selected row index from the bookTable
    int selectedRow = bookTable.getSelectedRow();
    
    // Check if a row is selected
    if (selectedRow >= 0) {
        // Get the original title from the selected row in the bookTable
        String originalTitle = bookTable.getValueAt(selectedRow, 0).toString();
        
        // Get the updated information from the text fields
        String title = titleUpdate.getText();
        String author = authorUpdate.getText();
        String isbn = isbnUpdate.getText();
        String category = (String) categoryUpdate.getSelectedItem();
        
        // Debugging: Print the original ISBN and updated title
        System.out.println("Original Title: " + originalTitle);
        System.out.println("Updated Title: " + title);
        
        // Pass the original title and updated information to the updateDatabase method
        updateDatabase(originalTitle, title, author, isbn, category);
        
        // Clear the text fields
        titleUpdate.setText("");
        authorUpdate.setText("");
        isbnUpdate.setText("");
    } else {
        JOptionPane.showMessageDialog(this, "Please select a book from the table.");
    }
   
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        titleUpdate.setEditable(true);
        titleUpdate.setFocusable(true);
        titleUpdate.setForeground(Color.BLACK);
        
        authorUpdate.setEditable(true);
        authorUpdate.setFocusable(true);
        authorUpdate.setForeground(Color.BLACK);
        
        categoryUpdate.setEditable(true);
        categoryUpdate.setEnabled(true);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        titleUpdate.setEditable(false);
        titleUpdate.setFocusable(false);
        titleUpdate.setForeground(Color.GRAY);
        
        authorUpdate.setEditable(false);
        authorUpdate.setFocusable(false);
        authorUpdate.setForeground(Color.GRAY);
        
        isbnUpdate.setEditable(false);
        isbnUpdate.setFocusable(false);
        isbnUpdate.setForeground(Color.GRAY);
        
        categoryUpdate.setEditable(false);
        categoryUpdate.setFocusable(false);
        categoryUpdate.setEnabled(false);
    }//GEN-LAST:event_jButton24ActionPerformed

    private void confirmReturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmReturnButtonActionPerformed
   
     refreshReturnsButton.setForeground(new Color(0, 225, 255));
    confirmReturnButton.setForeground(new Color(225, 225, 225));
        
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();

        // Get the selected rows from the returnsTable
        int[] selectedRows = returnsTable.getSelectedRows();

        // Check if any row is selected
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Please select a book.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
            return; // Exit the method if no row is selected
        }

        try {
            // Iterate through the selected rows
            for (int rowIndex : selectedRows) {
                String title = (String) returnsTable.getValueAt(rowIndex, 0); // Get Title from column 0
                String name = (String) returnsTable.getValueAt(rowIndex, 3); // Get Name from column 3

                // Delete matching rows from the 'returns' table
                try (PreparedStatement deleteReturnsStmt = connection.prepareStatement("DELETE FROM returns WHERE Title = ? AND name = ?")) {
                    deleteReturnsStmt.setString(1, title);
                    deleteReturnsStmt.setString(2, name);
                    deleteReturnsStmt.executeUpdate();
                }

                // Delete matching rows from the 'borrows' table
                try (PreparedStatement deleteBorrowsStmt = connection.prepareStatement("DELETE FROM borrows WHERE Title = ? AND name = ?")) {
                    deleteBorrowsStmt.setString(1, title);
                    deleteBorrowsStmt.setString(2, name);
                    deleteBorrowsStmt.executeUpdate();
                }

                // Insert into 'history' table
                try (PreparedStatement insertHistoryStmt = connection.prepareStatement("INSERT INTO history (Title, status, date, name) VALUES (?, 'Returned', CURDATE(), ?)")) {
                    insertHistoryStmt.setString(1, title);
                    insertHistoryStmt.setString(2, name);
                    insertHistoryStmt.executeUpdate();
                }

                // Increment 'nb' in the 'books' table
                try (PreparedStatement incrementNbStmt = connection.prepareStatement("UPDATE books SET nb = nb + 1 WHERE Title = ?")) {
                    incrementNbStmt.setString(1, title);
                    incrementNbStmt.executeUpdate();
                }

                // Count rows in 'reservation' table where title matches
                try (PreparedStatement countReservationStmt = connection.prepareStatement("SELECT COUNT(*) AS count FROM reservation WHERE Title = ?")) {
                    countReservationStmt.setString(1, title);
                    ResultSet rs = countReservationStmt.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        // Get nb from books table
                        int nb = 0;
                        try (PreparedStatement selectBookStmt = connection.prepareStatement("SELECT nb FROM books WHERE Title = ?")) {
                            selectBookStmt.setString(1, title);
                            ResultSet bookResultSet = selectBookStmt.executeQuery();
                            if (bookResultSet.next()) {
                                nb = bookResultSet.getInt("nb");
                            }
                        }
                        // Compare nb and count
                        String newStatus = (nb > count) ? "Available" : "Reserved";
                        // Update status in the books table
                        try (PreparedStatement updateBookStmt = connection.prepareStatement("UPDATE books SET status = ? WHERE Title = ?")) {
                            updateBookStmt.setString(1, newStatus);
                            updateBookStmt.setString(2, title);
                            updateBookStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }

    populateReturns();
   
    }//GEN-LAST:event_confirmReturnButtonActionPerformed

    private void reserveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserveButtonActionPerformed
    refreshReserveButton.setForeground(new Color(0, 225, 255));
    reserveButton.setForeground(new Color(225, 225, 225));

        jTabbedPane1.setSelectedIndex(4);
        
        showReservationDetails();
    }//GEN-LAST:event_reserveButtonActionPerformed

    private void borrowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowsButtonActionPerformed
        jTabbedPane1.setSelectedIndex(8);
        
        borrowsButton.setForeground(new Color(0,225,225));
                     booksButton.setForeground(new Color(225,225,255));
                     returnsButton.setForeground(new Color(225,225,255));                   
                     addBooksButton.setForeground(new Color(225,225,255));
                     paymentsButton.setForeground(new Color(225,225,255));
                     reservationsButton.setForeground(new Color(225,225,255));
    }//GEN-LAST:event_borrowsButtonActionPerformed

    private void notifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notifyButtonActionPerformed
    confirmBorrow.setForeground(new Color(0, 225, 255));
    notifyButton.setForeground(new Color(225, 225, 225));
    refreshBorrowButton.setForeground(new Color(0, 225, 255));
   try {
    // Get the selected row index from reservationTitleTable
    int selectedRow = reservationTitleTable.getSelectedRow();

    if (selectedRow >= 0) {
        // Get the title of the selected reservation from reservationTitleTable
        String title = reservationTitleTable.getValueAt(selectedRow, 0).toString(); // Assuming title is a String
        System.out.println("Selected Title: " + title);

        // Get the selected row index from requestTable
        int requestSelectedRow = requestTable.getSelectedRow();

        if (requestSelectedRow >= 0) {
            // Get the name of the selected reservation from requestTable
            String name = requestTable.getValueAt(requestSelectedRow, 0).toString(); // Assuming userName is a String
            System.out.println("Selected User: " + name);

            // Establish a connection to the database
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();

            
                // Book is available for scheduling
                // Calculate tomorrow's date
                java.sql.Date tomorrow = new java.sql.Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);

                // Update the reservation table with the scheduled date if it's not null
                String updateQuery = "UPDATE reservation SET sched = ? WHERE title = ? AND name = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setDate(1, tomorrow);
                updateStatement.setString(2, title);
                updateStatement.setString(3, name);
                int rowsUpdated = updateStatement.executeUpdate();
                updateStatement.close();

                if (rowsUpdated > 0) {
                    // Display success message
                    JOptionPane.showMessageDialog(this, "Scheduled date updated successfully for " + name);

                    // Send notification to the user
                    String notificationMessage = "Hello, " + name + ", the book \"" + title + "\" is already available for you to borrow. "
                            + "Please come to the library as soon as possible. Your schedule to claim the book is on \"" + tomorrow
                            + "\". If you fail to come, your reservation will be cancelled. Thank you and more power. - Librarian";

                    // Insert the notification into the database
                    String insertNotificationQuery = "INSERT INTO notifs (name, notif) VALUES (?, ?)";
                    PreparedStatement insertNotificationStatement = connection.prepareStatement(insertNotificationQuery);
                    insertNotificationStatement.setString(1, name);
                    insertNotificationStatement.setString(2, notificationMessage);
                    int notificationsInserted = insertNotificationStatement.executeUpdate();
                    insertNotificationStatement.close();

                    if (notificationsInserted > 0) {
                        System.out.println("Notification sent successfully to: " + name);
                    } else {
                        System.err.println("Failed to send notification to: " + name);
                    }

                    // Refresh the reservation details display
                    showReservationDetails();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update scheduled date for " + name);
                }
            

            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a reservation from the table.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a title from the table.");
    }
} catch (SQLException ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
}
    }//GEN-LAST:event_notifyButtonActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        dispose();
        new LogIn().setVisible(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void confirmBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBorrowActionPerformed
         notifyButton.setForeground(new Color(0, 225, 255));
    confirmBorrow.setForeground(new Color(225, 225, 225));
    refreshBorrowButton.setForeground(new Color(0, 225, 255));

    try {
        // Database connection variables
        dbConnection con = new dbConnection();
        Connection conn = con.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Ensure the connection is established
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Failed to connect to the database.");
                return;
            }

            // Get the selected row from the reservationTitleTable
            int selectedRowTitleTable = reservationTitleTable.getSelectedRow();
            if (selectedRowTitleTable == -1) {
                JOptionPane.showMessageDialog(this, "Please select a title from the reservation title table.");
                return;
            }

            // Get the title of the selected reservation from reservationTitleTable
            String title = reservationTitleTable.getValueAt(selectedRowTitleTable, 0).toString(); // Assuming title is a String

           

            // Count the number of reservations for the selected title
            String countReservationSql = "SELECT COUNT(*) AS count FROM reservation WHERE title = ?";
            pstmt = conn.prepareStatement(countReservationSql);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();
            int reservationCount = 0;
            if (rs.next()) {
                reservationCount = rs.getInt("count");
            }

            // Get the current nb value from the books table
            int nb = 0;
            String selectNbSql = "SELECT nb FROM books WHERE title = ?";
            pstmt = conn.prepareStatement(selectNbSql);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                nb = rs.getInt("nb");
            }

            // Determine the new status based on the comparison between nb and reservationCount
            String newStatus = (nb > reservationCount) ? "Available" : "Reserved";

            // Update the books table (decrement nr and update status if necessary)
            String updateBooksSql = "UPDATE books SET nr = nr - 1, nb = nb - 1, status = ? WHERE title = ?";
            pstmt = conn.prepareStatement(updateBooksSql);
            pstmt.setString(1, newStatus);
            pstmt.setString(2, title);
            pstmt.executeUpdate();

            // Continue with the rest of the borrowing process...

            // Get the selected row from the requestTable
            int selectedRowRequestTable = requestTable.getSelectedRow();
            if (selectedRowRequestTable == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to confirm borrow.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) requestTable.getModel();
            String borrowerName = model.getValueAt(selectedRowRequestTable, 0).toString(); // Assuming the fourth column is the borrower's name

            // Retrieve the necessary data from the reservation table
            String sql = "SELECT title, name FROM reservation WHERE title = ? AND name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, borrowerName);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "No reservation found with the provided title and borrower name.");
                return;
            }

            // Insert the data into the borrows table
            String dateOfBorrow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 7);
            String dateOfReturn = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

            sql = "INSERT INTO borrows (title, dob, dor, name) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, dateOfBorrow);
            pstmt.setString(3, dateOfReturn);
            pstmt.setString(4, borrowerName);
            pstmt.executeUpdate();

            // Decrement rn for all rows with the same title in the reservation table
            sql = "UPDATE reservation SET rn = rn - 1 WHERE title = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.executeUpdate();

            // Insert into the history table
            sql = "INSERT INTO history (title, status, date, name) VALUES (?, 'Borrowed', ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, dateOfBorrow);
            pstmt.setString(3, borrowerName);
            pstmt.executeUpdate();

            // Remove the selected row from the reservation table
            sql = "DELETE FROM reservation WHERE title = ? AND name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, borrowerName);
            pstmt.executeUpdate();

            // Remove the row from the requestTable
            model.removeRow(selectedRowRequestTable);

            JOptionPane.showMessageDialog(this, "Borrow confirmed and data updated successfully.", "Borrow Confirmation", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while processing the borrow: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }//GEN-LAST:event_confirmBorrowActionPerformed

    private void refreshReturnsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshReturnsButtonActionPerformed
    confirmReturnButton.setForeground(new Color(0, 225, 255));
    refreshReturnsButton.setForeground(new Color(225, 225, 225));

        populateReturns();
    }//GEN-LAST:event_refreshReturnsButtonActionPerformed

    private void refreshReserveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshReserveButtonActionPerformed
    reserveButton.setForeground(new Color(0, 225, 255));
    refreshReserveButton.setForeground(new Color(225, 225, 225));

        populateReservationTitleTableFromDatabase();
    }//GEN-LAST:event_refreshReserveButtonActionPerformed

    private void refreshBorrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBorrowButtonActionPerformed
    notifyButton.setForeground(new Color(0, 225, 255));
    refreshBorrowButton.setForeground(new Color(225, 225, 225));
    confirmBorrow.setForeground(new Color(0, 225, 255));

        showReservationDetails();
        checkAndCancelOverdueReservations();
    }//GEN-LAST:event_refreshBorrowButtonActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
jButton31.setForeground(new Color(0, 225, 255));
    jButton30.setForeground(new Color(225, 225, 225)); 

        populateBorrowsTableFromDatabase();
    }//GEN-LAST:event_jButton30ActionPerformed

    private void searchButtonBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonBooksActionPerformed
        
        searchBookTitle.requestFocus();
        
        String searchText = searchBookTitle.getText().trim();
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
        String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ? OR category LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        
        // Use the search text in the query with wildcards for advanced search
        String searchQuery = "%" + searchText + "%";
        statement.setString(1, searchQuery);
        statement.setString(2, searchQuery);
        statement.setString(3, searchQuery);
        statement.setString(4, searchQuery);

        // Print the query for debugging
        System.out.println("Executing query: " + statement.toString());
        
        // Execute the query
        ResultSet resultSet = statement.executeQuery();
        
        // Clear the table before adding new rows
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
        model.setRowCount(0);
        
        // Debug statement to check if the query returns any results
        boolean hasResults = false;
        
        // Populate the table with the search results
        while (resultSet.next()) {
            hasResults = true;
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String isbn = resultSet.getString("isbn");
            String category = resultSet.getString("category");
            String status = resultSet.getString("status");
            int nr = resultSet.getInt("nr");
            
            // Add row to the table model
            model.addRow(new Object[]{title, author, isbn, category, status, nr});
            
            // Print each result for debugging
            System.out.println("Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Category: " + category + ", Status: " + status + ", Nr: " + nr);
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
    }//GEN-LAST:event_searchButtonBooksActionPerformed

    private void borrowsSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowsSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrowsSearchActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
jButton30.setForeground(new Color(0, 225, 255));
    jButton31.setForeground(new Color(225, 225, 225)); 

        try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            // Prepare the SQL query to select only overdue books
            String query = "SELECT title, dob, dor, name FROM borrows WHERE dor < CURRENT_DATE";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) borrowsTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to borrowsTable
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String dob = resultSet.getString("dob");
                String dor = resultSet.getString("dor");
                String name = resultSet.getString("name");
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{title, dob, dor, name});
            }
            
            // Close the connection
            connection.close();
        } else {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
confirmPayment.setForeground(new Color(0, 225, 255));
    jButton26.setForeground(new Color(225, 225, 225));  

        populateLoansManagementTableForOverdueBooks();
    }//GEN-LAST:event_jButton26ActionPerformed
//i have issue with the userinfo again huhu
    private void confirmPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPaymentActionPerformed
    jButton26.setForeground(new Color(0, 225, 255));
    confirmPayment.setForeground(new Color(225, 225, 225));  
        
        int selectedRow = loansManagementTable.getSelectedRow();
    if (selectedRow != -1) {
        String title = (String) loansManagementTable.getValueAt(selectedRow, 1);
        String name = (String) loansManagementTable.getValueAt(selectedRow, 0);
        int feePaid = (int) loansManagementTable.getValueAt(selectedRow, 3); 

        try {
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();

            // Clear loan, overdueDays, and last_updated, and set dor to 7 days from the current date
            String updateBorrowsQuery = "UPDATE borrows SET loan = 0, overdueDays = 0, last_updated = NULL, dor = DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY) WHERE title = ? AND name = ?";
            PreparedStatement updateBorrowsStatement = connection.prepareStatement(updateBorrowsQuery);
            updateBorrowsStatement.setString(1, title);
            updateBorrowsStatement.setString(2, name);
            updateBorrowsStatement.executeUpdate();

            // Update history table
            String insertHistoryQuery = "INSERT INTO history (title, status, date, name, amount) VALUES (?, 'Overdue Paid', ?, ?, ?)";
            PreparedStatement insertHistoryStatement = connection.prepareStatement(insertHistoryQuery);
            insertHistoryStatement.setString(1, title);
            insertHistoryStatement.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            insertHistoryStatement.setString(3, name);
            insertHistoryStatement.setInt(4, feePaid);
            insertHistoryStatement.executeUpdate();

            // Decrement fee paid from the loan column in userinfo table
            String updateUserInfoQuery = "UPDATE userinfo SET loan = loan - ? WHERE name = ?";
            PreparedStatement updateUserInfoStatement = connection.prepareStatement(updateUserInfoQuery);
            updateUserInfoStatement.setInt(1, feePaid);
            updateUserInfoStatement.setString(2, name);
            updateUserInfoStatement.executeUpdate();

            // Increment earnings for the admin (Joanne)
            String updateAdminEarningsQuery = "UPDATE admin SET earnings = earnings + ? WHERE name = 'Joanne'";
            PreparedStatement updateAdminEarningsStatement = connection.prepareStatement(updateAdminEarningsQuery);
            updateAdminEarningsStatement.setInt(1, feePaid);
            updateAdminEarningsStatement.executeUpdate();

            // Increment earnings for the librarian (Mikaela)
            String updateLibrarianEarningsQuery = "UPDATE librarian SET earnings = earnings + ? WHERE User = 'Mikaela'";
            PreparedStatement updateLibrarianEarningsStatement = connection.prepareStatement(updateLibrarianEarningsQuery);
            updateLibrarianEarningsStatement.setInt(1, feePaid);
            updateLibrarianEarningsStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Payment confirmed successfully!");

            // Refresh loansManagementTable
            populateLoansManagementTableForOverdueBooks();

            // Close resources
            updateBorrowsStatement.close();
            insertHistoryStatement.close();
            updateUserInfoStatement.close();
            updateAdminEarningsStatement.close();
            updateLibrarianEarningsStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select a row from the table first.");
    }            
    }//GEN-LAST:event_confirmPaymentActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        borrowsSearch.requestFocus();
        
        String searchText = borrowsSearch.getText().trim();
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
        String query = "SELECT * FROM returns WHERE name LIKE ? OR title LIKE ? OR dob LIKE ? OR dor LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        
        // Use the search text in the query with wildcards for advanced search
        String searchQuery = "%" + searchText + "%";
        statement.setString(1, searchQuery);
        statement.setString(2, searchQuery);
        statement.setString(3, searchQuery);
        statement.setString(4, searchQuery);

        // Print the query for debugging
        System.out.println("Executing query: " + statement.toString());
        
        // Execute the query
        ResultSet resultSet = statement.executeQuery();
        
        // Clear the table before adding new rows
        DefaultTableModel model = (DefaultTableModel) returnsTable.getModel();
        model.setRowCount(0);
        
        // Debug statement to check if the query returns any results
        boolean hasResults = false;
        
        // Populate the table with the search results
        while (resultSet.next()) {
            hasResults = true;
            String name = resultSet.getString("name");
            String title = resultSet.getString("title");
            String dob = resultSet.getString("dob");
            String dor = resultSet.getString("dor");

            
            // Add row to the table model
            model.addRow(new Object[]{title, dob, dor, name});

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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        setState(JFrame. ICONIFIED);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void refreshPaymentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPaymentsButtonActionPerformed
    managePaymentsButton.setForeground(new Color(0, 225, 255));
    refreshPaymentsButton.setForeground(new Color(225, 225, 225));  

        populateLoansTable();
    }//GEN-LAST:event_refreshPaymentsButtonActionPerformed

    private void bSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bSearchActionPerformed
//this action performed for button reservation title search sorts the data and presents the requested search
    private void reservationTitleSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationTitleSearchButtonActionPerformed
        
        reservationTitleSearch.requestFocus(); //this is a debugging measure because the placeholder could interfere with the value passed to the searchText
        
        String searchText = reservationTitleSearch.getText().trim(); //from the text field named reservationTitleSearch, the value is passed to searchText

    if (searchText.isEmpty()||searchText.equals("Search")) { //debug to make sure that the text field has a value in it
        JOptionPane.showMessageDialog(this, "Please enter text to search.");
        return;
    }
    
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection(); //connection to database
        
        String query = "SELECT * FROM reservation WHERE title LIKE ?"; //query to retrieve the data which is 'LIKE' the searchText
        PreparedStatement statement = connection.prepareStatement(query);
        
        String searchQuery = "%" + searchText + "%"; //these are called wildcards for advanced searching
        statement.setString(1, searchQuery);

        ResultSet resultSet = statement.executeQuery(); //this executes the query

        DefaultTableModel model = (DefaultTableModel) reservationTitleTable.getModel();
        model.setRowCount(0); //clears the table
        
        boolean hasResults = false;
        
        while (resultSet.next()) {
            hasResults = true;
            String title = resultSet.getString("title");
            
            model.addRow(new Object[]{title}); //here it populates the table based on the result set

        }
        
        if (!hasResults) {
            JOptionPane.showMessageDialog(this, "No results found."); //if no results found, JOption Pane presents
        }
        
        // closes the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
    }//GEN-LAST:event_reservationTitleSearchButtonActionPerformed

    private void reserveSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserveSearchActionPerformed

        requestSearch.requestFocus(); //this is a debugging measure because the placeholder could interfere with the value passed to the searchText

        String searchText = requestSearch.getText().trim(); //from the text field named reservationTitleSearch, the value is passed to searchText

        if (searchText.isEmpty()||searchText.equals("Search")) { //debug to make sure that the text field has a value in it
            JOptionPane.showMessageDialog(this, "Please enter text to search.");
            return;
        }

        try {
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection(); //connection to database

            String query = "SELECT * FROM reservation WHERE name LIKE ? or rn LIKE = ? or sched LIKE = ?"; //query to retrieve the data which is 'LIKE' the searchText
            PreparedStatement statement = connection.prepareStatement(query);

            String searchQuery = "%" + searchText + "%"; //these are called wildcards for advanced searching
            statement.setString(1, searchQuery);
            statement.setString(2, searchQuery);
            statement.setString(3, searchQuery);

            ResultSet resultSet = statement.executeQuery(); //this executes the query

            DefaultTableModel model = (DefaultTableModel) requestTable.getModel();
            model.setRowCount(0); //clears the table

            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                String name = resultSet.getString("name");
                String rn = resultSet.getString("rn");
                String sched = resultSet.getString("sched");

                model.addRow(new Object[]{name, rn, sched}); //here it populates the table based on the result set

            }

            if (!hasResults) {
                JOptionPane.showMessageDialog(this, "No results found."); //if no results found, JOption Pane presents
            }

            // closes the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }//GEN-LAST:event_reserveSearchActionPerformed

    private void borrowsSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowsSearchButtonActionPerformed
              
        bSearch.requestFocus(); //this is a debugging measure because the placeholder could interfere with the value passed to the searchText
        
        String searchText = bSearch.getText().trim(); //from the text field named reservationTitleSearch, the value is passed to searchText

    if (searchText.isEmpty()||searchText.equals("Search")) { //debug to make sure that the text field has a value in it
        JOptionPane.showMessageDialog(this, "Please enter text to search.");
        return;
    }
    
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection(); //connection to database
        
        String query = "SELECT * FROM borrows WHERE title LIKE ? or dob LIKE ? or dor LIKE ? or name LIKE ?"; //query to retrieve the data which is 'LIKE' the searchText
        PreparedStatement statement = connection.prepareStatement(query);
        
        String searchQuery = "%" + searchText + "%"; //these are called wildcards for advanced searching
        statement.setString(1, searchQuery);
        statement.setString(2, searchQuery);
        statement.setString(3, searchQuery);
        statement.setString(4, searchQuery);

        ResultSet resultSet = statement.executeQuery(); //this executes the query

        DefaultTableModel model = (DefaultTableModel) borrowsTable.getModel();
        model.setRowCount(0); //clears the table
        
        boolean hasResults = false;
        
        while (resultSet.next()) {
            hasResults = true;
            String title = resultSet.getString("title");
            String dob = resultSet.getString("dob");
            String dor = resultSet.getString("dor");
            String name = resultSet.getString("name");
            
            
            model.addRow(new Object[]{title, dob, dor, name}); //here it populates the table based on the result set

        }
        
        if (!hasResults) {
            JOptionPane.showMessageDialog(this, "No results found."); //if no results found, JOption Pane presents
        }
        
        // closes the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
    }//GEN-LAST:event_borrowsSearchButtonActionPerformed

    private void paymentSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentSearchActionPerformed
              
        loansSearch.requestFocus(); //this is a debugging measure because the placeholder could interfere with the value passed to the searchText
        
        String searchText = loansSearch.getText().trim(); //from the text field named reservationTitleSearch, the value is passed to searchText

    if (searchText.isEmpty()||searchText.equals("Search")) { //debug to make sure that the text field has a value in it
        JOptionPane.showMessageDialog(this, "Please enter text to search.");
        return;
    }
    
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection(); //connection to database
        
        String query = "SELECT * FROM history WHERE title LIKE ? or date LIKE ? or name LIKE ? or amount LIKE ? WHERE status = 'Overdue Paid' "; //query to retrieve the data which is 'LIKE' the searchText
        PreparedStatement statement = connection.prepareStatement(query);
        
        String searchQuery = "%" + searchText + "%"; //these are called wildcards for advanced searching
        statement.setString(1, searchQuery);
        statement.setString(2, searchQuery);
        statement.setString(3, searchQuery);
        statement.setString(4, searchQuery);

        ResultSet resultSet = statement.executeQuery(); //this executes the query

        DefaultTableModel model = (DefaultTableModel) loansTable.getModel();
        model.setRowCount(0); //clears the table
        
        boolean hasResults = false;
        
        while (resultSet.next()) {
            hasResults = true;
            String name = resultSet.getString("name");
            String title = resultSet.getString("title");
            String date = resultSet.getString("date");
            String amount = resultSet.getString("amount");
            
            
            model.addRow(new Object[]{name, title, date, amount}); //here it populates the table based on the result set

        }
        
        if (!hasResults) {
            JOptionPane.showMessageDialog(this, "No results found."); //if no results found, JOption Pane presents
        }
        
        // closes the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
    }//GEN-LAST:event_paymentSearchActionPerformed

    private void loanManagementSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanManagementSearchButtonActionPerformed
              
        loanManagement.requestFocus(); //this is a debugging measure because the placeholder could interfere with the value passed to the searchText
        
        String searchText = loanManagement.getText().trim(); //from the text field named reservationTitleSearch, the value is passed to searchText

    if (searchText.isEmpty()||searchText.equals("Search")) { //debug to make sure that the text field has a value in it
        JOptionPane.showMessageDialog(this, "Please enter text to search.");
        return;
    }
    
    try {
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection(); //connection to database
        
        String query = "SELECT * FROM borrows WHERE title LIKE ? or dor LIKE ? or name LIKE ? or loan LIKE ? WHERE dor < CURDATE()"; //query to retrieve the data which is 'LIKE' the searchText
        PreparedStatement statement = connection.prepareStatement(query);
        
        String searchQuery = "%" + searchText + "%"; //these are called wildcards for advanced searching
        statement.setString(1, searchQuery);
        statement.setString(2, searchQuery);
        statement.setString(3, searchQuery);
        statement.setString(4, searchQuery);

        ResultSet resultSet = statement.executeQuery(); //this executes the query

        DefaultTableModel model = (DefaultTableModel) loansManagementTable.getModel();
        model.setRowCount(0); //clears the table
        
        boolean hasResults = false;
        
        while (resultSet.next()) {
            hasResults = true;
            String title = resultSet.getString("name");
            String dor = resultSet.getString("dor");
            String name = resultSet.getString("name");
            String loan = resultSet.getString("loan");
            
            
            model.addRow(new Object[]{name, title, dor, loan}); //here it populates the table based on the result set

        }
        
        if (!hasResults) {
            JOptionPane.showMessageDialog(this, "No results found."); //if no results found, JOption Pane presents
        }
        
        // closes the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
    }//GEN-LAST:event_loanManagementSearchButtonActionPerformed

    private void incrementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incrementActionPerformed
    try {                                          
        // Get the selected rows from the bookTable
        int[] selectedRows = bookTable.getSelectedRows();
        
        // Check if no rows are selected
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Please select a data from the table.");
            return;
        }
        
        // Establish a connection to the database
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        // Prepare the SQL statement to update the "nb" column in the "books" table
        String updateQuery = "UPDATE books SET nb = nb + 1 WHERE title = ?";
        
        try {
            // Iterate through the selected rows
            for (int selectedRow : selectedRows) {
                // Get the title from the selected row in the bookTable
                String title = bookTable.getValueAt(selectedRow, 0).toString();
                
                // Check if the title exists in the books table
                String checkTitleQuery = "SELECT title FROM books WHERE title = ?";
                PreparedStatement checkTitleStatement = connection.prepareStatement(checkTitleQuery);
                checkTitleStatement.setString(1, title);
                ResultSet resultSet = checkTitleStatement.executeQuery();
                
                if (resultSet.next()) {
                    // Title exists in the books table, proceed with incrementing "nb"
                    // Create a prepared statement for the update query
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, title);
                    
                    // Execute the update statement
                    int rowsUpdated = updateStatement.executeUpdate();
                    
                    // Close the statement
                    updateStatement.close();
                    
                    // Check if rows were updated
                    if (rowsUpdated > 0) {
                        System.out.println("Incremented nb for title: " + title);
                    } else {
                        System.out.println("Failed to increment nb for title: " + title);
                    }
                } else {
                    System.out.println("Title not found in the database: " + title);
                }
                
                // Close the result set and statement
                resultSet.close();
                checkTitleStatement.close();
            }
            
            // Close the connection
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_incrementActionPerformed

    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addBookPanel;
    private javax.swing.JButton addBooksButton;
    private javax.swing.JButton addButton;
    private javax.swing.JTextField authorAdd;
    private javax.swing.JTextField authorUpdate;
    private javax.swing.JTextField bSearch;
    private javax.swing.JTable bookTable;
    private javax.swing.JButton booksButton;
    private javax.swing.JPanel booksPanel;
    private javax.swing.JButton borrowsButton;
    private javax.swing.JPanel borrowsPanel;
    private javax.swing.JTextField borrowsSearch;
    private javax.swing.JButton borrowsSearchButton;
    private javax.swing.JTable borrowsTable;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JComboBox<String> categoryUpdate;
    private javax.swing.JButton confirmBorrow;
    private javax.swing.JButton confirmPayment;
    private javax.swing.JButton confirmReturnButton;
    private javax.swing.JTextField earnings;
    private javax.swing.JButton increment;
    private javax.swing.JTextField isbnAdd;
    private javax.swing.JTextField isbnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField loanManagement;
    private javax.swing.JPanel loanManagementPanel;
    private javax.swing.JButton loanManagementSearchButton;
    private javax.swing.JTable loansManagementTable;
    private javax.swing.JPanel loansPanel;
    private javax.swing.JTextField loansSearch;
    private javax.swing.JTable loansTable;
    private javax.swing.JButton managePaymentsButton;
    private javax.swing.JButton notifyButton;
    private javax.swing.JButton paymentSearch;
    private javax.swing.JButton paymentsButton;
    private javax.swing.JButton refreshBorrowButton;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton refreshPaymentsButton;
    private javax.swing.JButton refreshReserveButton;
    private javax.swing.JButton refreshReturnsButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JTextField requestSearch;
    private javax.swing.JTable requestTable;
    private javax.swing.JPanel reservationPanel;
    private javax.swing.JTextField reservationTitleSearch;
    private javax.swing.JButton reservationTitleSearchButton;
    private javax.swing.JTable reservationTitleTable;
    private javax.swing.JButton reservationsButton;
    private javax.swing.JButton reserveButton;
    private javax.swing.JPanel reservePanel;
    private javax.swing.JButton reserveSearch;
    private javax.swing.JButton returnsButton;
    private javax.swing.JPanel returnsPanel;
    private javax.swing.JTable returnsTable;
    private javax.swing.JPanel rightmostPanel;
    private javax.swing.JTextField searchBookTitle;
    private javax.swing.JButton searchButtonBooks;
    private javax.swing.JTextField titleField;
    private javax.swing.JTextField titleUpdate;
    private javax.swing.JButton updateButton;
    private javax.swing.JPanel updatePanel;
    // End of variables declaration//GEN-END:variables

    private void addBookToDatabase(String title, String author, String isbn, String category) {
        try {
            // Establish a connection to your database
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();
            
            // Create a PreparedStatement to execute the INSERT query
            String query = "INSERT INTO books (Title, Author, ISBN, Category) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, isbn);
            statement.setString(4, category);
            
            
            // Execute the INSERT query
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Book added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add book.");
            }
            
            // Close the connection and statement
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }
    private void updateDatabase(String originalTitle, String title, String author, String isbn, String category) {
 try {
    // Establish a connection to your database
    dbConnection con = new dbConnection();
    Connection connection = con.getConnection();
    
    // Create a PreparedStatement to execute the UPDATE query for the books table
    String query = "UPDATE books SET title = ?, author = ?, category = ? WHERE isbn = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, title);
    statement.setString(2, author);
    statement.setString(3, category);
    statement.setString(4, isbn);
    
    // Execute the UPDATE query for the books table
    int booksUpdated = statement.executeUpdate();
    
    // If the book is updated in the books table, update other related tables
    if (booksUpdated > 0) {
        // Update the borrows table
        String borrowsQuery = "UPDATE borrows SET title = ? WHERE title = ?";
        PreparedStatement borrowsStatement = connection.prepareStatement(borrowsQuery);
        borrowsStatement.setString(1, title);
        borrowsStatement.setString(2, originalTitle); // Provide the old title here
        borrowsStatement.executeUpdate();
        borrowsStatement.close();
        
        // Update the reservation table
        String reservationQuery = "UPDATE reservation SET title = ? WHERE title = ?";
        PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery);
        reservationStatement.setString(1, title);
        reservationStatement.setString(2, originalTitle); // Provide the old title here
        reservationStatement.executeUpdate();
        reservationStatement.close();
        
        // Update the reserves table
        String reservesQuery = "UPDATE reserves SET title = ? WHERE title = ?";
        PreparedStatement reservesStatement = connection.prepareStatement(reservesQuery);
        reservesStatement.setString(1, title);
        reservesStatement.setString(2, originalTitle); // Provide the old title here
        reservesStatement.executeUpdate();
        reservesStatement.close();
        
        // Update the returns table
        String returnsQuery = "UPDATE returns SET title = ? WHERE title = ?";
        PreparedStatement returnsStatement = connection.prepareStatement(returnsQuery);
        returnsStatement.setString(1, title);
        returnsStatement.setString(2, originalTitle); // Provide the old title here
        returnsStatement.executeUpdate();
        returnsStatement.close();
        
        JOptionPane.showMessageDialog(null, "Book updated successfully!");
    } else {
        JOptionPane.showMessageDialog(null, "Failed to update book. No book found with the given ISBN.");
    }
    
    // Close the connection and statement
    statement.close();
    connection.close();
} catch (SQLException ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
}

    }
}



