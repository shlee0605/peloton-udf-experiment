/**
 * Experiments
 */
import java.util.Date;


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

    public void runUpdate(DBType type, String qry) {
        connection.runUpdate(qry, type);
    }

    public void runBasicExperiment(DBType type, String funcName, String pSQL, String cSQL) {
//        System.out.println("----------------------------------------------");
//        System.out.println("Experiment on " + funcName + " function:");
//        System.out.println("----------------------------------------------");
//        System.out.println("C UDF:");
//        System.out.println(pSQL);
//        System.out.println("\nResult:");
//        connection.runQuery(pSQL, type);
//        System.out.println("----------------------------------------------");
//        System.out.println("plpgsql UDF:");
//        System.out.println(cSQL);
//        System.out.println("\nResult:");
//        connection.runQuery(cSQL, type);
//        System.out.println("----------------------------------------------");
//        System.out.println();

        long startTime = (new Date()).getTime();
        connection.runQuery(pSQL, type);
        long execTimePL = (new Date()).getTime() - startTime;

        startTime =  (new Date()).getTime();
        connection.runQuery(cSQL, type);
        long execTimeC = (new Date()).getTime() - startTime;

        printResult(funcName, execTimePL, execTimeC);
    }

    public void runStoredProcedureExperiment(DBType type) {
//        System.out.println("----------------------------------------------");
        
        long startTime = (new Date()).getTime();
        connection.runUpdate("DROP TABLE IF EXISTS A;", type);
        connection.runUpdate("CREATE TABLE A(test INT);", type);
        connection.runQuery("select insert_table_plpgsql(100000);", type);
        long execTimeStored = (new Date()).getTime() - startTime;
//        System.out.println(execTime + "ms");
        connection.runQuery("select count(*) from A;", type);

//        System.out.println("----------------------------------------------");
        startTime = (new Date()).getTime();
        connection.runUpdate("DROP TABLE IF EXISTS A;", type);
        connection.runUpdate("CREATE TABLE A(test INT);", type);
        for (int i = 0; i < 100000; i++) {
            connection.runUpdate("INSERT INTO A VALUES (1);", type);
        }
        long execTimeSQL = (new Date()).getTime() - startTime;
//        System.out.println(execTimeSQL + "ms");
        connection.runQuery("select count(*) from A;", type);
        
//        System.out.println("----------------------------------------------");
        System.out.println();
        connection.runUpdate("DROP TABLE IF EXISTS A;", type);
        printResult("insert test", execTimeSQL, execTimeStored);
    }

    public void printResult(String experimentName, double timeOne, double timeTwo) {
        System.out.println(experimentName + "\t|" + timeOne + "\t|" + timeTwo);
    }

}