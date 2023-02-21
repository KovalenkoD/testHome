Preconditions:
Java: 17
Docker

1) Build the project with gradle
./gradlew build

3) To build the docker image need to execute:
docker build -t takehome/takehome .

4) To run (create a container from image)
docker run -p 8080:8080 takehome/takehome


USERNAME and PASSWORD:
name: test
password: test

THE url for get countries details:
http://localhost:8080/api/continents?countries=US