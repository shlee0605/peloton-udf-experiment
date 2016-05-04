import java.sql.*;

/**
 *
 * DB Wrapper Class
 *
 */
public class DBConnection {

    public static final String DB_NAME = "postgres";
    public static final String DB_PORT = "57721";
    public static final String USER_NAME = "vagrant";
    public static final String USER_PASSWORD = "password";

    Connection conn;

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
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:" + DB_PORT + "/" + DB_NAME, USER_NAME, USER_PASSWORD);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }

    // runQuery returns execution time of that query.
    public double runQuery(String sql) {
        double executionTime = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String rowString = rs.getString(1);
                if(rowString.contains("Execution")) {
                    executionTime = Double.valueOf(rowString.replaceAll("[^.0-9]", ""));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in executing SQL query: " + sql);
            e.printStackTrace();
            return 0;
        }

        return executionTime;
    }

    public void runUpdate(String sql) {

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error in executing SQL query: " + sql);
            e.printStackTrace();
            return;
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch(SQLException e) {
            System.out.println("Connection is not closed correctly.");
            e.printStackTrace();
            return;
        }
    }
}
