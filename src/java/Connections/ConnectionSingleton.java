package Connections;

import java.sql.*;

public class ConnectionSingleton {

    private ConnectionSingleton() {
    }

    public static ConnectionSingleton createConnection() {
        if (con == null) {
            con = new ConnectionSingleton();
        }
        return con;
    }
    private static ConnectionSingleton con;
    private static Connection connection;

    // lab computer
    /*
     *     private final String connectionURL = "jdbc:postgresql://db:5432/g1027104_u";
     *     private final String DataBaseDriver = "org.postgresql.Driver";
     *     private final String username = "g1027104_u";
     *     private final String dbPassword = "EldyDqOUXi";
     */

    
    // private computer
    private final String connectionURL = "jdbc:postgresql://localhost:5432/postgres";
    private final String DataBaseDriver = "org.postgresql.Driver";
    private final String username = "postgres";
    private final String dbPassword = "postgres";
    

    public static final String idname = "idname";
    public static final String password = "pass";
    public static final String loggedIn = "loggedin";
    public static final String gameName = "/TheDepartment";
    public static final String welcomePage = gameName + "/faces/WelcomePage.jsp";
    public static final String indexPage = gameName + "/faces/index.jsp";

    public Statement getStatement() {

        Statement statement = null;
        try {
            if (connection == null) {
                Class.forName(DataBaseDriver);
                connection = DriverManager.getConnection(connectionURL, username, dbPassword);
            }

            statement = connection.createStatement();

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return statement;
    }
}
