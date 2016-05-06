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
        connection.runUpdate("delete from A where test>=0;");
        long startTime = System.currentTimeMillis();
        connection.runQuery("select insert_table_plpgsql(10000);");
        long storedProcedureTime = System.currentTimeMillis() - startTime;

        connection.runUpdate("delete from A where test>=0;");
        startTime = System.currentTimeMillis();
        connection.runPrepareStmtOne("INSERT INTO A VALUES(?);", 10000);
        long queryTime = System.currentTimeMillis() - startTime;

        return new ExperimentResult("insert test", queryTime, storedProcedureTime);
    }

    public ExperimentResult runStoredProcedureExperimentTwo() {
        connection.runUpdate("delete from A where test>=0;");
        connection.runPrepareStmtOne("INSERT INTO A VALUES(?);", 1000);
        long startTime = System.currentTimeMillis();
        connection.runQuery("select modify_table_plpgsql();");
        long storedProcedureTime = System.currentTimeMillis() - startTime;

        connection.runUpdate("delete from A where test>=0;");
        connection.runPrepareStmtOne("INSERT INTO A VALUES(?);", 1000);
        startTime = System.currentTimeMillis();
        connection.runPrepareStmtTwo("UPDATE A SET test=99999 where test=?;", 500);
        long queryTime = System.currentTimeMillis() - startTime;

        return new ExperimentResult("update test", queryTime, storedProcedureTime);
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
