This project imports transactions from the records.csv and records.xml resources via two batch jobs configured using springboot and spring-batch.
The batch jobs read the files, validate the contents, saves the transactions to an in memory database and reports the import results in /log/csv_import_report.log for csv import and xml_import_report.log for the xml import job when it is done the program will close.

The project can be build on the command line using maven: mvn clean package
and executed using the command java -jar ./target/CustomerStatementProcessor-0.0.1-SNAPSHOT.jar

NOTE:
The project uses Lombok to auto-generate boiler plate code, when using an IDE like Eclipse the plugin will need to be installed from https://projectlombok.org/ before the IDE will recognize the auto-generated methods. 