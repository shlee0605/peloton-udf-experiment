/**
 * Experiments
 */
public class Experiment {

    DBConnection connection;

    public Experiment(DBConnection conn) {
        connection = conn;
    }

    public void runExperimentOne() {
        System.out.println();
        String sql = "SELECT * FROM \"USERTABLE\"";
        System.out.println("Experiment 1 : " + sql);
        connection.runQuery("EXPLAIN ANALYZE " + sql);
    }

    public void runExperimentTwo() {
        System.out.println();
        String sql = "SELECT sum(x) FROM A";
        System.out.println("Experiment 2 : " + sql);
        connection.runQuery("EXPLAIN ANALYZE " + sql );
    }

}
