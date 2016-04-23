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
        experiment.runExperiment(
            Experiment.DBType.TPCC,
            "calc_tax",
            "explain analyze select i_price, calc_tax_plpgsql(i_price) from item;",
            "explain analyze select i_price, calc_tax_c(i_price) from item;"
        );

        connection.closeConnection();
    }
}
