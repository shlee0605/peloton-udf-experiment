/**
 * Experiments
 */


public class Experiment {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public enum DBType {
        YCSB, TPCC;
    }

    DBConnection connection;

    public Experiment(DBConnection conn) {
        connection = conn;
    }

    public void runExperimentOne() {
        System.out.println();
        String sql = "SELECT * FROM \"USERTABLE\"";
        System.out.println("Experiment 1 : " + sql);
        connection.runQuery("EXPLAIN ANALYZE " + sql, DBType.YCSB);
    }

    public void runExperimentTwo() {
        System.out.println();
        String sql = "SELECT * FROM customer";
        System.out.println("Experiment 2 : " + sql);
        connection.runQuery("EXPLAIN ANALYZE " + sql, DBType.TPCC);
    }

    public void runExperiment(DBType type, String funcName, String pSQL, String cSQL) {
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("Experiment on " + funcName + " function:");
        System.out.println("----------------------------------------------");
        System.out.println("C UDF:");
        System.out.println(pSQL);
        System.out.println("\nResult:");
        connection.runQuery(pSQL, type);
        System.out.println("----------------------------------------------");
        System.out.println("plpgsql UDF:");
        System.out.println(cSQL);
        System.out.println("\nResult:");
        connection.runQuery(cSQL, type);
        System.out.println("----------------------------------------------");
    }

}
