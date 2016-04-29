import java.sql.*;

/**
 *
 * DB Wrapper Class
 *
 */
public class DBConnection {

    public static final String DB_NAME_YCSB = "ycsb";
    public static final String DB_NAME_TPCC = "tpcc";
    public static final String DB_PORT = "57721";
    public static final String USER_NAME = "vagrant";
    public static final String USER_PASSWORD = "password";

    Connection connYCSB;
    Connection connTPCC;

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
            connYCSB = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:" + DB_PORT + "/" + DB_NAME_YCSB,
                    USER_NAME, USER_PASSWORD);
            connTPCC = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:" + DB_PORT + "/" + DB_NAME_TPCC,
                    USER_NAME, USER_PASSWORD);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connYCSB != null && connTPCC != null) {
            System.out.println("DB Connection is made successfully.");
        } else {
            System.out.println("Failed to make connection.");
        }
    }

    // runQuery returns execution time of that query.
    public double runQuery(String sql, Experiment.DBType type) {
        Connection conn;
        double executionTime = 0;

        if (type == Experiment.DBType.YCSB) {
            conn = connYCSB;
        } else if (type == Experiment.DBType.TPCC) {
            conn = connTPCC;
        } else {
            System.out.println("Wrong input type.");
            return 0;
        }

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

    public void runUpdate(String sql, Experiment.DBType type) {
        Connection conn;
        if (type == Experiment.DBType.YCSB) {
            conn = connYCSB;
        } else if (type == Experiment.DBType.TPCC) {
            conn = connTPCC;
        } else {
            System.out.println("Wrong input type.");
            return;
        }

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
            connTPCC.close();
            connYCSB.close();
        } catch(SQLException e) {
            System.out.println("Connection is not closed correctly.");
            e.printStackTrace();
            return;
        }
    }
}
