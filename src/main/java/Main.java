/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("------------- Peloton UDF Testing ------------");

        // initialize db connection
        DBConnection connection = new DBConnection();

        // create experiment instance
        Experiment experiment = new Experiment(connection);

        // run experiments
        experiment.runExperimentOne();
        experiment.runExperimentTwo();

        connection.closeConnection();
    }
}
