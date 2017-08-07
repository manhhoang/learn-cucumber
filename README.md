# Learn Cucumber 

## Running...

You can build a jar file with `gradle build`, then run it with `java -jar build/libs/com.jd.gradle-0.1.0.jar`,
alternatively use `gradle bootRun` to use the spring boot runner (the terminal will hang whilst this runs, stop with `CTRL-C`).

You can see the running application at `http://localhost:8080/hello-world`.

You can run a single test by annotating the feature with `@wip` and running `gradle wip`.

Run `gradle verify` to make sure everything still works.
