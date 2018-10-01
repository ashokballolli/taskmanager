# Integration Test

These Integration tests are limited to verifying the response of /list API by comapring with the database records.


## Configuration

### Database configuration

Please modify the file **"/src/main/resources/application.properties"** for database configuration

### Run

To run the integration tests, please run the following maven command from the current directory

mvn clean install

mvn clean test
