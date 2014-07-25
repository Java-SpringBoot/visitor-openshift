package org.lababidy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.sql.DataSource;

public class ConnectionHelper
{
	private String url;
	private static ConnectionHelper instance;
    private static DataSource ds = null;
	private ConnectionHelper()
	{
    	String driver = null;
		try {
                    //Class.forName("com.mysql.jdbc.Driver");
                    //url = "jdbc:mysql://localhost/cellar?user=root&password=000000";
                    //InitialContext ctx = new InitialContext();
                    //DataSource ds
                    //       = (DataSource) ctx.lookup("java:jboss/datasources/MySQLD");
                    /*String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
                     String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
                    String appName = System.getenv("OPENSHIFT_APP_NAME");
                    String dbName = "cellar";
                    String dbUser = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
                    String dbPassWord = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
                     url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?user=" + dbUser + "&password=" + dbPassWord;
                    */
                    ResourceBundle bundle = ResourceBundle.getBundle("database");
            driver = bundle.getString("jdbc.driver");
            Class.forName(driver);
                            url = bundle.getString("jdbc.url");
                    // System.out.println("connection test");
		} catch (Exception e) {
                    //logger.info("");
                    e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			instance = new ConnectionHelper();
		}
		try {
                    return DriverManager.getConnection(instance.url);
                    //return ds.getConnection();
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void close(Connection connection)
	{
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
