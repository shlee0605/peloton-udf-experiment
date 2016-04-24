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
            Experiment.DBType.YCSB,
            "concat_text",
            "explain analyze select concat_text_plpgsql(field1, field2) from \"USERTABLE\";",
            "explain analyze select concat_text_c(field1, field2) from \"USERTABLE\";"
        );
        experiment.runExperiment(
            Experiment.DBType.TPCC,
            "calc_tax",
            "explain analyze select i_price, calc_tax_plpgsql(i_price) from item;",
            "explain analyze select i_price, calc_tax_c(i_price) from item;"
        );
        experiment.runExperiment(
                Experiment.DBType.TPCC,
                "calc_tax",
                "explain analyze select weighted_mean_plpgsql(ol_amount, ol_quantity) from order_line;",
                "explain analyze select weighted_mean_c(ol_amount, ol_quantity) from order_line;"
        );


        connection.closeConnection();
    }
}
