### Vecty

fds side project

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

* **http://localhost:8080/index.html

Then upload a .pbm and watch the svg version in the browser

