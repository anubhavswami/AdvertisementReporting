Solution:

Please place the csv file in the location and map the directory to application.properties file.

The current port in which the application is running is 8080.

Start the server.

Run - http://localhost:8080/save - This will save the data from csv file to H2 Database.

The H2 database is available at - http://localhost:8080/h2-console (Table name - CSV_DATA )

Get all the details of the month and site http://localhost:8080/reports/January/iOS http://localhost:8080/reports/2/android

The API also provides the functionality for reports by month and site functionality. a. Report by month: http://localhost:8080/reports/month/1 or http://localhost:8080/reports/month/jan b. Report by site: http://localhost:8080/reports/iOS
