/**
 * Main
 */
public class Main {

    private static void experiment(Experiment.DBType type, String funcName, String pSQL, String cSQL) {
        // initialize db connection
        DBConnection connection = new DBConnection();
        // create experiment instance
        Experiment experiment = new Experiment(connection);
        // run experiment
        experiment.runExperiment(type, funcName, pSQL, cSQL);
        connection.closeConnection();
    }

    public static void main(String[] args) {
        System.out.println("------------- Peloton UDF Testing ------------");

        experiment(
            Experiment.DBType.YCSB,
            "concat_text",
            "explain analyze select concat_text_plpgsql(field1, field2) from \"USERTABLE\";",
            "explain analyze select concat_text_c(field1, field2) from \"USERTABLE\";"
        );

        experiment(
            Experiment.DBType.YCSB,
            "replace_vowel",
            "explain analyze select replace_vowel_plpgsql(field1) from \"USERTABLE\";",
            "explain analyze select replace_vowel_c(field1) from \"USERTABLE\";"
        );
        experiment(
            Experiment.DBType.TPCC,
            "calc_tax",
            "explain analyze select i_price, calc_tax_plpgsql(i_price) from item;",
            "explain analyze select i_price, calc_tax_c(i_price) from item;"
        );
        experiment(
            Experiment.DBType.TPCC,
            "integer_manipulate",
            "explain analyze select integer_manipulate_plpgsql(i_im_id) from item;",
            "explain analyze select integer_manipulate_c(i_im_id) from item;"
        );
        DBConnection connection = new DBConnection();
        Experiment experiment = new Experiment(connection);
        experiment.runExp(
            Experiment.DBType.TPCC
        );
        connection.closeConnection();

    }
}
