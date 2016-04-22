import java.sql.*;

/**
 *
 * DB Wrapper Class
 *
 */
public class DBConnection {

    public static final String DB_NAME = "ycsb";
    public static final String DB_PORT = "57721";
    public static final String USER_NAME = "vagrant";
    public static final String USER_PASSWORD = "password";

    Connection connection;

    public DBConnection() {
        connectToPeloton();
    }

    public void connectToPeloton() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Postgres driver is not registered correctly");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:" + DB_PORT + "/" + DB_NAME,
                                                     USER_NAME, USER_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("DB Connection is made successfully.");
        } else {
            System.out.println("Failed to make connection.");
        }
    }

    public void runQuery(String sql) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error in executing SQL query: " + sql);
            e.printStackTrace();
            return;
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch(SQLException e) {
            System.out.println("Connection is not closed correctly.");
            e.printStackTrace();
            return;
        }
    }


}
