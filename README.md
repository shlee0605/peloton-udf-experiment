Peloton UDF Experiment
=====

1. Download oltpbench and compile
  ```
  git clone https://github.com/shlee0605/oltpbench.git
  
  cd oltpbench
  
  ant
  ```
2. Prepare Data for Experiment. Run the following command while peloton server is running. (TPCC loading takes long time so I think it will be better to turn off all the logs)
  ```
  ./oltpbenchmark -b ycsb \
  -c config/peloton_ycsb_config.xml \
  --create=true --load=true --execute=false \
  -s 5 \
  -o outputfile
  
  ./oltpbenchmark -b tpcc \
  -c config/peloton_tpcc_config.xml \
  --create=true --load=true --execute=false \
  -s 5 \
  -o outputfile
  ```

3. compile and run the program
  ```
  git clone https://github.com/shlee0605/peloton-udf-experiment.git
  
  cd peloton-udf-experiment
  
  mvn package
  
  ./runtest
  ```

Example Output
```
------------- Peloton UDF Testing ------------
DB Connection is made successfully.

Experiment 1 : SELECT * FROM "USERTABLE"
Seq Scan on "USERTABLE"  (cost=0.00..12.30 rows=230 width=324) (never executed)
Planning time: 0.295 ms
Execution time: 11.216 ms

Experiment 2 : SELECT * FROM customer
Seq Scan on customer  (cost=0.00..10.70 rows=70 width=1014) (never executed)
Planning time: 0.272 ms
Execution time: 1.607 ms
```
