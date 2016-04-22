/**
 * Experiments
 */


public class Experiment {
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

}
