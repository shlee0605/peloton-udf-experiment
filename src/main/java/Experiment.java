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

    public double runQuery(DBType type, String qry) {
        return connection.runQuery(qry, type);
    }

    public ExperimentResult runBasicExperiment(DBType type, String experimentName, String pSQL, String cSQL) {
        double psqlExecution = connection.runQuery(pSQL, type);
        double cExecution = connection.runQuery(cSQL, type);
        return new ExperimentResult(experimentName, psqlExecution, cExecution);
    }

    public ExperimentResult runStoredProcedureExperimentOne() {
        return null;
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
