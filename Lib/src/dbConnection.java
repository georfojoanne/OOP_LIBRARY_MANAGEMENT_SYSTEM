import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    
    
    private final Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/librarian";
    private final String hostUsername = "root";
    private final String hostPassword = "";
    
    public dbConnection() throws SQLException {
        
        this.connection = DriverManager.getConnection(url, hostUsername, hostPassword);
        
    }
    
    
    public Connection getConnection(){ //can be used to get connection to database
        return connection;
    }
    
    
    public static void main(String [] args) throws SQLException, ClassNotFoundException{
          dbConnection  data = new dbConnection();
          data.getConnection();
    }
    
    
}
