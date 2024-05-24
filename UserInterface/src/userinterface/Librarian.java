package userinterface;


import java.awt.Color;
import static java.awt.Color.GRAY;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author USER
 */
public class Librarian extends javax.swing.JFrame {
    
    public Librarian() {
        
        initComponents();
        
        Color col=new Color(28,52,62);
        getContentPane().setBackground(col);
        
        dbData();
       populateBooksTableFromDatabase();
       populateReturns();
       populateLoansTableFromDatabase();
       holds();
       
       bookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && bookTable.getSelectedRow() != -1) {
                    // Get selected row index
                    int selectedRow = bookTable.getSelectedRow();

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
            }
        });

              bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        DefaultTableModel model = (DefaultTableModel) holdsTable.getModel();

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

        DefaultTableModel tableModel = (DefaultTableModel) bookTable.getModel();

        while (rs.next()) {
            String Title = rs.getString("Title");
            String Author = rs.getString("Author");
            String ISBN = rs.getString("ISBN");
            String Category = rs.getString("Category");

            String[] bookData = {Title, Author, ISBN, Category};
            tableModel.addRow(bookData);
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
    Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
}
    }
  
  private void populateBooksTableFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            

            PreparedStatement statement = connection.prepareStatement("SELECT Title, Author, ISBN, Category FROM books");
            ResultSet resultSet = statement.executeQuery();
            
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to borrowsTable
            while (resultSet.next()) {
                String Title = resultSet.getString("Title");
                String Author = resultSet.getString("Author");
                String ISBN = resultSet.getString("ISBN");
                String Category = resultSet.getString("Category");
                
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{Title, Author, ISBN, Category});
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
  
  
    private void populateLoansTableFromDatabase() {
    try {
        // Establish a database connection
        dbConnection con = new dbConnection();
        Connection connection = con.getConnection();
        
        if (connection != null) {
            

            PreparedStatement statement = connection.prepareStatement("SELECT title,dor,rn,name FROM reservation");
            ResultSet resultSet = statement.executeQuery();
            
            
            // Clear existing rows from borrowsTable
            DefaultTableModel model = (DefaultTableModel) loansTable.getModel();
            model.setRowCount(0);
            
            // Add fetched data to borrowsTable
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String dor = resultSet.getString("dor");
                String rn = resultSet.getString("rn");
                String name = resultSet.getString("name");
                
                
                // Add a row to the borrowsTable
                model.addRow(new Object[]{title, dor, rn, name});
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        booksPanel = new javax.swing.JPanel();
        searchBooks = new javax.swing.JTextField();
        removeButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        bookTable = new javax.swing.JTable();
        borrowsPanel = new javax.swing.JPanel();
        confirmButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        returnsTable = new javax.swing.JTable();
        borrowsSearch = new javax.swing.JTextField();
        reservationPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        holdsTable = new javax.swing.JTable();
        reserveButton = new javax.swing.JButton();
        holdsSearch = new javax.swing.JTextField();
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
        jButton20 = new javax.swing.JButton();
        requestSearch = new javax.swing.JTextField();
        jButton22 = new javax.swing.JButton();
        loansPanel = new javax.swing.JPanel();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        loansSearch = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        loansTable = new javax.swing.JTable();
        loanManagementPanel = new javax.swing.JPanel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        loansManagementTable = new javax.swing.JTable();
        jTextField25 = new javax.swing.JTextField();
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
        rightmostPanel = new javax.swing.JPanel();
        returnsButton = new javax.swing.JButton();
        addBooksButton = new javax.swing.JButton();
        booksButton = new javax.swing.JButton();
        loansButton = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        holdsButton = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        borrowsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(72, 96, 105));
        setForeground(new java.awt.Color(28, 52, 62));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(10, 29, 36));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userinterface/Xbang.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userinterface/LIB.ITT.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 736, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 60));

        jTabbedPane1.setBackground(new java.awt.Color(204, 185, 174));
        jTabbedPane1.setForeground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setName("Book"); // NOI18N

        booksPanel.setBackground(new java.awt.Color(28, 52, 62));
        booksPanel.setForeground(new java.awt.Color(159, 212, 179));

        searchBooks.setBackground(new java.awt.Color(255, 255, 255));
        searchBooks.setText("Search");
        searchBooks = new PlaceholderTextField("Search");

        removeButton.setBackground(new java.awt.Color(49, 98, 103));
        removeButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        removeButton.setForeground(new java.awt.Color(0, 255, 255));
        removeButton.setText("REMOVE");
        removeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        updateButton.setBackground(new java.awt.Color(49, 98, 103));
        updateButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        updateButton.setForeground(new java.awt.Color(0, 255, 255));
        updateButton.setText("UPDATE");
        updateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        refreshButton.setBackground(new java.awt.Color(49, 98, 103));
        refreshButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(0, 255, 255));
        refreshButton.setText("REFRESH");
        refreshButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        bookTable.setBackground(new java.awt.Color(220, 220, 250));
        bookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author", "ISBN", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bookTable.getTableHeader().setReorderingAllowed(false);
        bookTable.setDefaultEditor(Object.class, null);
        jScrollPane5.setViewportView(bookTable);

        javax.swing.GroupLayout booksPanelLayout = new javax.swing.GroupLayout(booksPanel);
        booksPanel.setLayout(booksPanelLayout);
        booksPanelLayout.setHorizontalGroup(
            booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(booksPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchBooks)
                    .addGroup(booksPanelLayout.createSequentialGroup()
                        .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        booksPanelLayout.setVerticalGroup(
            booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(booksPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(searchBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(booksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("tab1", booksPanel);

        borrowsPanel.setBackground(new java.awt.Color(28, 52, 62));
        borrowsPanel.setName("Book"); // NOI18N
        borrowsPanel.setNextFocusableComponent(booksButton);

        confirmButton.setBackground(new java.awt.Color(49, 98, 103));
        confirmButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(0, 255, 255));
        confirmButton.setText("CONFIRM RETURN");
        confirmButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

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
        returnsTable.getTableHeader().setReorderingAllowed(false);
        returnsTable.setDefaultEditor(Object.class, null);
        jScrollPane2.setViewportView(returnsTable);

        borrowsSearch.setBackground(new java.awt.Color(255, 255, 255));
        borrowsSearch.setText("Search");
        borrowsSearch = new PlaceholderTextField("Search");

        javax.swing.GroupLayout borrowsPanelLayout = new javax.swing.GroupLayout(borrowsPanel);
        borrowsPanel.setLayout(borrowsPanelLayout);
        borrowsPanelLayout.setHorizontalGroup(
            borrowsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrowsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(borrowsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borrowsSearch)
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borrowsPanelLayout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
        );
        borrowsPanelLayout.setVerticalGroup(
            borrowsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borrowsPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(borrowsSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("tab2", borrowsPanel);

        reservationPanel.setBackground(new java.awt.Color(28, 52, 62));

        holdsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        holdsTable.getTableHeader().setReorderingAllowed(false);
        holdsTable.setDefaultEditor(Object.class, null);
        jScrollPane3.setViewportView(holdsTable);

        reserveButton.setBackground(new java.awt.Color(49, 98, 103));
        reserveButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        reserveButton.setForeground(new java.awt.Color(0, 255, 255));
        reserveButton.setText("RESERVES");
        reserveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveButtonActionPerformed(evt);
            }
        });

        holdsSearch.setBackground(new java.awt.Color(255, 255, 255));
        holdsSearch.setText("Search");
        holdsSearch = new PlaceholderTextField("Search");

        javax.swing.GroupLayout reservationPanelLayout = new javax.swing.GroupLayout(reservationPanel);
        reservationPanel.setLayout(reservationPanelLayout);
        reservationPanelLayout.setHorizontalGroup(
            reservationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reservationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reserveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
            .addGroup(reservationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(reservationPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(holdsSearch)
                    .addContainerGap()))
        );
        reservationPanelLayout.setVerticalGroup(
            reservationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservationPanelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reserveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(reservationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(reservationPanelLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(holdsSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(607, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("tab3", reservationPanel);

        addBookPanel.setBackground(new java.awt.Color(28, 52, 62));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(131, 157, 167));
        jTextField5.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(54, 61, 155));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("TITLE");
        jTextField5.setAutoscrolls(false);
        jTextField5.setEditable(false);
        jTextField5.setFocusable(false);
        jTextField5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        addButton.setBackground(new java.awt.Color(49, 98, 103));
        addButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        addButton.setForeground(new java.awt.Color(0, 255, 255));
        addButton.setText("ADD");
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(131, 157, 167));
        jTextField6.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(54, 61, 155));
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
        jTextField7.setForeground(new java.awt.Color(54, 61, 155));
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
        jTextField8.setForeground(new java.awt.Color(54, 61, 155));
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
        jTextField10.setForeground(new java.awt.Color(54, 61, 155));
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

        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ENGLISH", "SCIENCE ", "MATH" }));
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
                            .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isbnAdd)
                            .addComponent(authorAdd)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addBookPanelLayout.createSequentialGroup()
                                .addGap(0, 1, Short.MAX_VALUE)
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

        requestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Reservation Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(requestTable);
        requestTable.getTableHeader().setReorderingAllowed(false);
        requestTable.setDefaultEditor(Object.class, null);

        jButton20.setBackground(new java.awt.Color(49, 98, 103));
        jButton20.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton20.setForeground(new java.awt.Color(0, 255, 255));
        jButton20.setText("CONFIRM BORROW");
        jButton20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        requestSearch.setBackground(new java.awt.Color(255, 255, 255));
        requestSearch.setText("Search");
        requestSearch = new PlaceholderTextField("Search");

        jButton22.setBackground(new java.awt.Color(49, 98, 103));
        jButton22.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton22.setForeground(new java.awt.Color(0, 255, 255));
        jButton22.setText("NOTIFY");
        jButton22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout reservePanelLayout = new javax.swing.GroupLayout(reservePanel);
        reservePanel.setLayout(reservePanelLayout);
        reservePanelLayout.setHorizontalGroup(
            reservePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservePanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(reservePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(reservePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(reservePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(requestSearch)
                    .addContainerGap()))
        );
        reservePanelLayout.setVerticalGroup(
            reservePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservePanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(reservePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
            .addGroup(reservePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(reservePanelLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(requestSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(607, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("tab6", reservePanel);

        loansPanel.setBackground(new java.awt.Color(28, 52, 62));

        jButton27.setBackground(new java.awt.Color(49, 98, 103));
        jButton27.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton27.setForeground(new java.awt.Color(0, 255, 255));
        jButton27.setText("MANAGE");
        jButton27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setBackground(new java.awt.Color(49, 98, 103));
        jButton28.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton28.setForeground(new java.awt.Color(0, 255, 255));
        jButton28.setText("CANCEL");
        jButton28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        loansSearch.setBackground(new java.awt.Color(255, 255, 255));
        loansSearch.setText("Search");
        loansSearch = new PlaceholderTextField("Search");

        loansTable.setForeground(new java.awt.Color(0, 0, 0));
        loansTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(loansTable);
        loansTable.getTableHeader().setReorderingAllowed(false);
        loansTable.setDefaultEditor(Object.class, null);

        javax.swing.GroupLayout loansPanelLayout = new javax.swing.GroupLayout(loansPanel);
        loansPanel.setLayout(loansPanelLayout);
        loansPanelLayout.setHorizontalGroup(
            loansPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loansPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loansPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loansPanelLayout.createSequentialGroup()
                        .addComponent(jButton27, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(loansSearch, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        loansPanelLayout.setVerticalGroup(
            loansPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loansPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(loansSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(loansPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("tab7", loansPanel);

        loanManagementPanel.setBackground(new java.awt.Color(28, 52, 62));

        jButton25.setBackground(new java.awt.Color(49, 98, 103));
        jButton25.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton25.setForeground(new java.awt.Color(0, 255, 255));
        jButton25.setText("CONFIRM PAYMENT");
        jButton25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton26.setBackground(new java.awt.Color(49, 98, 103));
        jButton26.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton26.setForeground(new java.awt.Color(0, 255, 255));
        jButton26.setText("NOTIFY USER");
        jButton26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        loansManagementTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
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
        loansManagementTable.getTableHeader().setReorderingAllowed(false);
        loansManagementTable.setDefaultEditor(Object.class, null);
        jScrollPane6.setViewportView(loansManagementTable);

        jTextField25.setBackground(new java.awt.Color(255, 255, 255));
        jTextField25.setText("Search");

        javax.swing.GroupLayout loanManagementPanelLayout = new javax.swing.GroupLayout(loanManagementPanel);
        loanManagementPanel.setLayout(loanManagementPanelLayout);
        loanManagementPanelLayout.setHorizontalGroup(
            loanManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loanManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loanManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(loanManagementPanelLayout.createSequentialGroup()
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                        .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(loanManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(loanManagementPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTextField25)
                    .addContainerGap()))
        );
        loanManagementPanelLayout.setVerticalGroup(
            loanManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loanManagementPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(loanManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
            .addGroup(loanManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(loanManagementPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(606, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("tab8", loanManagementPanel);

        updatePanel.setBackground(new java.awt.Color(28, 52, 62));

        jTextField13.setEditable(false);
        jTextField13.setBackground(new java.awt.Color(131, 157, 167));
        jTextField13.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(71, 54, 155));
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
        jTextField14.setForeground(new java.awt.Color(71, 54, 155));
        jTextField14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField14.setText("TITLE");
        jTextField14.setAutoscrolls(false);
        jTextField14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jTextField14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField14.setFocusable(false);

        jTextField16.setEditable(false);
        jTextField16.setBackground(new java.awt.Color(131, 157, 167));
        jTextField16.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(71, 54, 155));
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
        jTextField18.setForeground(new java.awt.Color(71, 54, 155));
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
        jTextField20.setForeground(new java.awt.Color(71, 54, 155));
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

        categoryUpdate.setBackground(new java.awt.Color(156, 153, 255));
        categoryUpdate.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 14)); // NOI18N
        categoryUpdate.setForeground(new java.awt.Color(0, 0, 0));
        categoryUpdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ENGLISH", "SCIENCE ", "MATH" }));
        categoryUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryUpdateActionPerformed(evt);
            }
        });
        categoryUpdate.setEditable(false);
        categoryUpdate.setFocusable(false);
        categoryUpdate.setEnabled(false);
        categoryUpdate.setForeground(Color.CYAN);

        jButton21.setBackground(new java.awt.Color(49, 98, 103));
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

        jButton23.setBackground(new java.awt.Color(49, 98, 103));
        jButton23.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jButton23.setForeground(new java.awt.Color(0, 255, 255));
        jButton23.setText("EDIT");
        jButton23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setBackground(new java.awt.Color(49, 98, 103));
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
                                .addComponent(jTextField20, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
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

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 690, 680));

        rightmostPanel.setBackground(new java.awt.Color(10, 29, 36));

        returnsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        returnsButton.setForeground(new java.awt.Color(255, 255, 255));
        returnsButton.setText("Returns");
        returnsButton.setBorder(null);
        returnsButton.setBorderPainted(false);
        returnsButton.setContentAreaFilled(false);
        returnsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        booksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booksButtonActionPerformed(evt);
            }
        });

        loansButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        loansButton.setForeground(new java.awt.Color(255, 255, 255));
        loansButton.setText("Loans");
        loansButton.setBorder(null);
        loansButton.setBorderPainted(false);
        loansButton.setContentAreaFilled(false);
        loansButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loansButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loansButtonActionPerformed(evt);
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

        jTextField15.setBackground(new java.awt.Color(10, 29, 36));
        jTextField15.setFont(new java.awt.Font("Stylus BT", 1, 14)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(255, 255, 255));
        jTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField15.setText("USERNAME");
        jTextField15.setBorder(null);
        jTextField15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        jTextField15.setEditable(false);
        jTextField15.setFocusable(false);

        holdsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        holdsButton.setForeground(new java.awt.Color(255, 255, 255));
        holdsButton.setText("Reservations");
        holdsButton.setBorder(null);
        holdsButton.setBorderPainted(false);
        holdsButton.setContentAreaFilled(false);
        holdsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        holdsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holdsButtonActionPerformed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        jButton17.setForeground(new java.awt.Color(0, 255, 255));
        jButton17.setText("LOG OUT");
        jButton17.setBorderPainted(false);
        jButton17.setContentAreaFilled(false);
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        borrowsButton.setFont(new java.awt.Font("Stylus BT", 1, 18)); // NOI18N
        borrowsButton.setForeground(new java.awt.Color(255, 255, 255));
        borrowsButton.setText("Borrows");
        borrowsButton.setBorder(null);
        borrowsButton.setBorderPainted(false);
        borrowsButton.setContentAreaFilled(false);
        borrowsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                    .addGroup(rightmostPanelLayout.createSequentialGroup()
                        .addGroup(rightmostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rightmostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(holdsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                .addComponent(loansButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addBooksButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(returnsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(booksButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(borrowsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightmostPanelLayout.createSequentialGroup()
                        .addGap(0, 42, Short.MAX_VALUE)
                        .addGroup(rightmostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(rightmostPanelLayout.createSequentialGroup()
                                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGap(75, 75, 75))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(booksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borrowsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addBooksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loansButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(holdsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        getContentPane().add(rightmostPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 57, -1, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void returnsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnsButtonActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_returnsButtonActionPerformed

    private void addBooksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBooksButtonActionPerformed
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_addBooksButtonActionPerformed

    
    private void booksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booksButtonActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        
    }//GEN-LAST:event_booksButtonActionPerformed

            
    private void loansButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loansButtonActionPerformed
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_loansButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        jTabbedPane1.setSelectedIndex(7);
        
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

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void authorUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorUpdateActionPerformed

    private void titleUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleUpdateActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        populateBooksTableFromDatabase();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
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

                String query = "DELETE FROM books WHERE title = ?"; //removes book
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.setString(1, title);
                    pstmt.executeUpdate();
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
        // TODO add your handling code here:
    }//GEN-LAST:event_titleFieldActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed

                String title = titleField.getText();
                String author = authorAdd.getText();
                String isbn = isbnAdd.getText();
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                
                if (selectedCategory == null) { // of there is no category selected
        JOptionPane.showMessageDialog(this, "Please select a valid category.");
        return;
    }
                if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
            return;
        }
                // adding the data to the database
                addBookToDatabase(title, author, isbn, selectedCategory);
                
                //clear the textfields
                titleField.setText("Enter title here");
                authorAdd.setText("Enter author here");
                isbnAdd.setText("Enter ISBN here");
    }//GEN-LAST:event_addButtonActionPerformed

    private void holdsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holdsButtonActionPerformed
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_holdsButtonActionPerformed

    private void categoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryComboBoxActionPerformed
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        System.out.println(selectedCategory);
    }//GEN-LAST:event_categoryComboBoxActionPerformed

    private void categoryUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryUpdateActionPerformed
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
    }//GEN-LAST:event_categoryUpdateActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed

        String title = titleUpdate.getText();
        String author = authorUpdate.getText();
        String isbn = isbnUpdate.getText();
        String category = (String) categoryUpdate.getSelectedItem();
        
        //to pass the data in the text field to the variables
        
            updateDatabase(title, author, isbn, category); // updates to the database
            
            System.out.println("New Title: " + title); //for debugging
            
        titleUpdate.setText("");
        authorUpdate.setText("");
        isbnUpdate.setText("");
            
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

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
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

                // Decrement 'nr' column in 'books' table
                try (PreparedStatement decrementNrStmt = connection.prepareStatement("UPDATE books SET nr = nr - 1 WHERE Title = ?")) {
                    decrementNrStmt.setString(1, title);
                    decrementNrStmt.executeUpdate();
                }

                // Update 'status' column in 'books' table based on 'nr' value
                try (PreparedStatement updateStatusStmt = connection.prepareStatement("UPDATE books SET status = CASE WHEN nr >= 2 THEN 'Reserved' WHEN nr = 1 THEN 'Borrowed' ELSE 'Available' END WHERE Title = ?")) {
                    updateStatusStmt.setString(1, title);
                    updateStatusStmt.executeUpdate();
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
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void reserveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserveButtonActionPerformed
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_reserveButtonActionPerformed

    private void borrowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrowsButtonActionPerformed

    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addBookPanel;
    private javax.swing.JButton addBooksButton;
    private javax.swing.JButton addButton;
    private javax.swing.JTextField authorAdd;
    private javax.swing.JTextField authorUpdate;
    private javax.swing.JTable bookTable;
    private javax.swing.JButton booksButton;
    private javax.swing.JPanel booksPanel;
    private javax.swing.JButton borrowsButton;
    private javax.swing.JPanel borrowsPanel;
    private javax.swing.JTextField borrowsSearch;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JComboBox<String> categoryUpdate;
    private javax.swing.JButton confirmButton;
    private javax.swing.JButton holdsButton;
    private javax.swing.JTextField holdsSearch;
    private javax.swing.JTable holdsTable;
    private javax.swing.JTextField isbnAdd;
    private javax.swing.JTextField isbnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JPanel loanManagementPanel;
    private javax.swing.JButton loansButton;
    private javax.swing.JTable loansManagementTable;
    private javax.swing.JPanel loansPanel;
    private javax.swing.JTextField loansSearch;
    private javax.swing.JTable loansTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JTextField requestSearch;
    private javax.swing.JTable requestTable;
    private javax.swing.JPanel reservationPanel;
    private javax.swing.JButton reserveButton;
    private javax.swing.JPanel reservePanel;
    private javax.swing.JButton returnsButton;
    private javax.swing.JTable returnsTable;
    private javax.swing.JPanel rightmostPanel;
    private javax.swing.JTextField searchBooks;
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
    private void updateDatabase(String title, String author, String isbn, String category) {
  try {
            // Establish a connection to your database
            dbConnection con = new dbConnection();
            Connection connection = con.getConnection();
            
            // Create a PreparedStatement to execute the UPDATE query
            String query = "UPDATE books SET title = ?, author = ?, category = ? WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, category);
            statement.setString(4, isbn);
            
            // Execute the UPDATE query
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
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


