/**
 * Main
 */
public class Main {

    static DBConnection connection;
    static Experiment experiment;
    private static void experiment(String funcName, String pSQL, String cSQL) {
        // initialize db connection
        // create experiment instance
        // run experiment
        ExperimentResult result = experiment.runBasicExperiment(funcName, pSQL, cSQL);
        result.printResult();
    }

    private static void experimentStoredProcedureOne() {
        DBConnection connection = new DBConnection();
        Experiment experiment = new Experiment(connection);

        ExperimentResult result = experiment.runStoredProcedureExperimentOne();
        result.printResult();
        connection.closeConnection();
    }

    public static void main(String[] args) throws Exception {
        connection = new DBConnection();
        experiment = new Experiment(connection);
        System.out.println("------------- Peloton UDF Comparison Testing ------------");
        System.out.println("function name\t| plpgsql time \t | c time");
        experiment(
            "concat_text",
            "explain analyze select concat_text_plpgsql(field1, field2) from \"USERTABLE\";",
            "explain analyze select concat_text_c(field1, field2) from \"USERTABLE\";"
        );

        experiment(
            "replace_vowel",
            "explain analyze select replace_vowel_plpgsql(field1) from \"USERTABLE\";",
            "explain analyze select replace_vowel_c(field1) from \"USERTABLE\";"
        );


        experiment(
            "calc_tax",
            "explain analyze select i_price, calc_tax_plpgsql(i_price) from item;",
            "explain analyze select i_price, calc_tax_c(i_price) from item;"
        );

        experiment(
            "integer_manipulate",
            "explain analyze select integer_manipulate_plpgsql(i_im_id) from item;",
            "explain analyze select integer_manipulate_c(i_im_id) from item;"
        );

        experiment(
                "item_sales_sum",
                "explain analyze select i_id, item_sales_sum_plpgsql(i_id) from item where i_id " +
                        "in (100, 14232, 22352, 53421, 99322, 82312, 2214)",
                "explain analyze select i_id, item_sales_sum_c(i_id) from item where i_id " +
                        "in (100, 14232, 22352, 53421, 99322, 82312, 2214)"
        );

        experiment(
                "countdown",
                "explain analyze select countdown_plpgsql(i_id) from item where i_id " +
                        "in (100, 14232, 22352, 53421, 99322, 82312, 2214);",
                "explain analyze select countdown_c(i_id) from item where i_id " +
                        "in (100, 14232, 22352, 53421, 99322, 82312, 2214);"
        );

        experiment(
                "fib",
                "explain analyze select fib_plpgsql(i_id) from item where i_id <= 30;",
                "explain analyze select fib_c(i_id) from item where i_id <= 30;"
        );

        // Stored Procedure Test
        System.out.println();
        System.out.println("------------- Stored Procedure Testing ------------");
        System.out.println("experiment name\t| sql time \t | stored proc time");

        experimentStoredProcedureOne();
        connection.closeConnection();

    }
}
