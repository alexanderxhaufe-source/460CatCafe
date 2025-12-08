# 460CatCafe

Compilation and execution instructions

Add the Oracle JDBC driver to your CLASSPATH environment variable:
   export CLASSPATH=/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar:${CLASSPATH}

The code can be compiled by compiling all java files in the directory with;
javac ./*.java

The “Main” code to execute is Prog4.java, it takes no arguments when ran and can be ran with;
java ./Prog4

When running instructions will be on the terminal and most required inputs are done through a number selection.


workload distribution (who did what):
For Workload distribution, everyone helped with planning, ER-Diagram, and some form of code testing. 
- Alexander Haufe coded the initial schema and csv reader to populate the tables. He made queries 3 and 4. He also made the logic that deletes from the database.
- Aleksei Weinberg did code for the main method in Prog4.java, as well as query 1, modifytable, and all the methods in common.java
- Jessica McManus primarily did the code for AddToTable, runQuery(), and query 2, as well as working on the schema in the design pdf.

