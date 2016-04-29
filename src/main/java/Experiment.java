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

    public void runQuery(DBType type, String qry) {
        connection.runQuery(qry, type);
    }

    public ExperimentResult runExperiment(DBType type, String funcName, String pSQL, String cSQL) {
//        System.out.println("----------------------------------------------");
//        System.out.println("Experiment on " + funcName + " function:");
//        System.out.println("----------------------------------------------");
//        System.out.println("PLPGSQL UDF:");
//        System.out.println(pSQL);
//        System.out.println("\nResult:");
//        double psqlExecution = connection.runQuery(pSQL, type);
//        System.out.println("----------------------------------------------");
//        System.out.println("C UDF:");
//        System.out.println(cSQL);
//        System.out.println("\nResult:");
//        double cExecution = connection.runQuery(cSQL, type);
//        System.out.println("----------------------------------------------");
//        System.out.println();

        long startTime = System.currentTimeMillis();
        double psqlExecution = connection.runQuery(pSQL, type);
        double cExecution = connection.runQuery(cSQL, type);
        long totalTime = System.currentTimeMillis() - startTime;

        return new ExperimentResult(funcName, psqlExecution, cExecution, (double)totalTime);
    }


}

class ExperimentResult {
    String functionName;
    double psqlExecutionTime;
    double csqlExecutionTime;
    double clientTotalExecutionTime;

    public ExperimentResult(String name, double ptime, double ctime, double total) {
        functionName = name;
        psqlExecutionTime = ptime;
        csqlExecutionTime = ctime;
        clientTotalExecutionTime = total;
    }

    public void printResult() {
        System.out.println("TEST:::" + functionName + ": " + psqlExecutionTime + " ms (plpgsql)" + " : " +
                csqlExecutionTime + " ms (c) " + clientTotalExecutionTime + " ms (total)");
    }
}
