Peloton UDF Experiment
=====

1. change DB configuration information in `DBConnection.java`

2. compile and run the program

```
mvn package

./runtest
```

Example Output
```
------------- Peloton UDF Testing ------------
DB Connection is made successfully.

Experiment 1 : SELECT * FROM A
Seq Scan on a  (cost=0.00..31.40 rows=2140 width=8) (actual time=0.014..0.016 rows=7 loops=1)
Total runtime: 0.057 ms

Experiment 2 : SELECT sum(x) FROM A
Aggregate  (cost=36.75..36.76 rows=1 width=4) (actual time=0.013..0.014 rows=1 loops=1)
  ->  Seq Scan on a  (cost=0.00..31.40 rows=2140 width=4) (actual time=0.003..0.003 rows=7 loops=1)
Total runtime: 0.038 ms
```
