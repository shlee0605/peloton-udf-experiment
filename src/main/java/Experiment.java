/**
 * Experiments
 */
import java.util.Date;


public class Experiment {

    public enum DBType {
        YCSB, TPCC;
    }

    DBConnection connection;

    public Experiment(DBConnection conn) {
        connection = conn;
    }

    public double runQuery(DBType type, String qry) {
        return connection.runQuery(qry, type);
    }

    public void runUpdate(DBType type, String qry) {
        connection.runUpdate(qry, type);
    }

    public ExperimentResult runBasicExperiment(DBType type, String experimentName, String pSQL, String cSQL) {
        double psqlExecution = connection.runQuery(pSQL, type);
        double cExecution = connection.runQuery(cSQL, type);
        return new ExperimentResult(experimentName, psqlExecution, cExecution);
    }

    public ExperimentResult runStoredProcedureExperimentOne(DBType type) {

        long startTime = System.currentTimeMillis();
        connection.runUpdate("DROP TABLE IF EXISTS A;", type);
        connection.runUpdate("CREATE TABLE A(test INT);", type);
        connection.runQuery("select insert_table_plpgsql(1000);", type);
        long storedProcedureTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        connection.runUpdate("DROP TABLE IF EXISTS A;", type);
        connection.runUpdate("CREATE TABLE A(test INT);", type);
        for (int i = 0; i < 1000; i++) {
            connection.runUpdate("INSERT INTO A VALUES (1);", type);
        }
        long queryTime = System.currentTimeMillis() - startTime;

        connection.runUpdate("DROP TABLE IF EXISTS A;", type);
        return new ExperimentResult("insert test", queryTime, storedProcedureTime);
    }

    public ExperimentResult runStoredProcedureExperimentTwo() {
        return null;
    }
}

class ExperimentResult {
    String experimentName;
    double resultOne;
    double resultTwo;

    public ExperimentResult(String name, double one, double two) {
        experimentName = name;
        resultOne = one;
        resultTwo = two;
    }

    public void printResult() {
        System.out.println(experimentName + "\t| " + resultOne + "ms\t| " + resultTwo + "ms");
    }
}
