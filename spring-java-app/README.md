### Basic java app template

A Maven template to startup a flat multimodule projects.

####Features:
* Compiler plugin usage and well formatted root pom
* Dependency management configured in root pom
* SpringBootApplication plugin configured, run on tomcat

####Dependencies already configured:
* Apache commons (IO, Configuration, Collections, StringUtils)
* Apache log4j
* JUnit4 and geotools testData for testing
* A minimalistic test to check that everything works
* Spring-boot-starter-web

####Build&Run

Install the app in the local repo

```
mvn clean install
```

Create eclipse project

```
mvn eclipse:clean eclipse:eclipse
```

Run / Debug

*Play (or Debug) the* **it.fds.toolbox.Start** *class in the endpoints module*

####Test URLs

Open the browser and go to the following URLs

* **http://localhost:8080/basicTextEndPoint** (3 times)

```
{"id":1,"content":"You got you endpoint running, Enjoy it Man!!!!"}
{"id":2,"content":"You got you endpoint running, Enjoy it Man!!!!"}
{"id":3,"content":"You got you endpoint running, Enjoy it Man!!!!"}
```

* **http://localhost:8080/basicTextEndPoint?msg=pomodori per tutti.** (4 times)

```
{"id":4,"content":"You got you endpoint running, pomodori per tutti.!"}
{"id":5,"content":"You got you endpoint running, pomodori per tutti.!"}
{"id":6,"content":"You got you endpoint running, pomodori per tutti.!"}
{"id":7,"content":"You got you endpoint running, pomodori per tutti.!"}
```

* **http://localhost:8080/machineryTextEndPoint** (3 times)

*The output is random, expect something like this*

```
'6', '3', '1'
'2', '1', '2', '0', '1', '0', '1', '2', '1', '0'
'3', '0', '1', '3', '3'
```