/**
 * Experiments
 */
import java.util.Date;


public class Experiment {

    DBConnection connection;

    public Experiment(DBConnection conn) {
        connection = conn;
    }

    public double runQuery(String qry) {
        return connection.runQuery(qry);
    }

    public void runUpdate(String qry) {
        connection.runUpdate(qry);
    }

    public ExperimentResult runBasicExperiment(String experimentName, String pSQL, String cSQL) {
        double psqlExecution = connection.runQuery(pSQL);
        double cExecution = connection.runQuery(cSQL);
        return new ExperimentResult(experimentName, psqlExecution, cExecution);
    }

    public ExperimentResult runStoredProcedureExperimentOne() {

        connection.runUpdate("delete from A where test=1;");
        long startTime = System.currentTimeMillis();
        connection.runQuery("select insert_table_plpgsql(1000);");
        long storedProcedureTime = System.currentTimeMillis() - startTime;

        connection.runUpdate("delete from A where test=1;");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            connection.runUpdate("INSERT INTO A VALUES (1);");
        }
        long queryTime = System.currentTimeMillis() - startTime;

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
