#### SpotHero Rate Schedule Service

Determines the applicable rate from a file of hourly rates for a date/time range.

#### How to build

Use maven to build the project:

`$ mvn compile`

Running the unit/integration tests:

`$ mvn test`

Packaging the project into a single JAR:

`$ mvn package`

Running the service JAR:

`$ java -jar ./target/spothero-interview-service-0.0.1-SNAPSHOT-jar-with-dependencies.jar`

Using curl to request JSON:

`$ curl -H "Accept: application/json" "http://localhost:8080/rate?start=2017-11-15T09:47:00Z&end=2017-11-15T09:47:00Z" `

Using curl to request XML:

`$ curl -H "Accept: application/xml" "http://localhost:8080/rate?start=2017-11-15T09:47:00Z&end=2017-11-15T09:47:00Z"`