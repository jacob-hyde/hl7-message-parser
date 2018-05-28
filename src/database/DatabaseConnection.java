package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.ResultSet;
public class DatabaseConnection
{
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/facility_1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String MAX_POOL = "250";

    private Connection connection;
    private Properties properties;
    private Statement statement;
    private ResultSet resultSet;
    
    public DatabaseConnection()
    {
    		this.connect();
    }
    
    private Properties getProperties()
    {
    		if(properties == null)
    		{
    			properties = new Properties();
    			properties.setProperty("user", USERNAME);
    			properties.setProperty("password", PASSWORD);
    			properties.setProperty("MaxPooledStatements", MAX_POOL);
    			properties.setProperty("autoReconnect", "true");
    			properties.setProperty("useSSL", "false");
    		}
    		return properties;
    }
    
    public void connect()
    {
    		if(this.connection == null)
    		{
    			try {
    				Class.forName(DATABASE_DRIVER);
    				this.connection = DriverManager.getConnection(DATABASE_URL, this.getProperties());
    			} catch(ClassNotFoundException | SQLException error) {
    				error.printStackTrace();
    			}
    		}
    }
    
    public ResultSet read(String query) throws Exception
    {
    		try {
    			this.connect();
    			this.statement = this.connection.createStatement();
    			this.resultSet = statement.executeQuery(query);
    		} catch (Exception e) {
    			throw e;
    		}
    		return this.resultSet;
    }
	
    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
}
