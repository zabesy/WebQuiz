# WebQuiz
Engine for creating and solving quizzes through HTTP APIs

Using Spring Boot and H2 in memory file database.

# Configuration
Using H2 in memory file database

Properties file /src/main/resources/application.properties

- Build

```
gradlew build
```

- Executing app

```
java -jar build/libs/*.jar
```
- Stopping execution (Windows)

```
jps

taskill -f /PID <Id of process> 
```

By default, it's running on port ```8889``` and ```quizdb``` will be in the folder where jar is executed from.

# Authorization

To perform any actions with quizzes a user has to be registered and then authorized via HTTP Basic Auth. Otherwise, the service returns the ```HTTP 401 (Unauthorized)``` code.

To register a user has to send a ```POST``` request with ```Email``` and ```Password``` to ```http://localhost:<PortNo>/api/register``` with the following json format:
```
{
  "email":"user@mail.com"
  "password":"something"
}
```


