Solution:

1. Please place the csv file in the location and map the directory to application.properties file.
2. The current port in which the application is running is 8080.
3. Start the server. 
4. Run - http://localhost:8080/save - This will save the data from csv file to H2 Database.
5. The H2 database is available at -  http://localhost:8080/h2-console (Table name - CSV_DATA )
6. Get all the details of the month and site
    http://localhost:8080/reports/January/iOS
    http://localhost:8080/reports/2/android

7. The API also provides the functionality for reports by month and site functionality.
    a. Report by month:
        http://localhost:8080/reports/month/1   or http://localhost:8080/reports/month/jan 
    b. Report by site:
        http://localhost:8080/reports/iOS
        