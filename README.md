# Intilery Java Test

## TL;DR
1. Init a git repository, commit often
2. Run `gradle verify`
3. Read through the code, understand the structure
4. Make the health check pass
5. Make the e-commerce tests pass using the Graph repository for the data
6. Make the process tests pass
7. Ensure all changed are committed
8. `gradle clean`
9. Zip the contents of the directory
10. Send back

## Getting going

The following project requires that you have a Java 8 SDK installed, with gradle and git.

Use git (there is a .gitignore file included), get started with `git init; git add .; git commit -m "Initial commit"`.
Commit as often as you see fit.

You can then start building and running the project using: `gradle verify`.

This will download all the dependencies, build the jar file, run any acceptance tests. There is one set up
that checks that the `greeter` component works.

Tests are written using cucumber tests in the `src/test/resources/features` directory. Make all the tests pass.
Look at the example `greeter` and follow the package structure for your classes.
 
You can run a single test by annotating the feature with `@wip` and running `gradle wip`.

The first test to make pass is already marked as `@wip`.

When the test passes, remove the `@wip` tag and run `gradle verify` to make sure everything still works.

Then find the next test and change the `@pending` to `@wip` for that test.

## Finding tests and requirements...

Each requirement is described using a cucumber feature.

* Each feature is described in the `src/test/resources/features` directory
* Expected responses are in `src/test/resources/json`

## Running...

You run the application by calling the `com.intilery.exercise.web.ExerciseApplication#main`.

You can build a jar file with `gradle build`, then run it with `java -jar build/libs/com.intilery.exercise-0.1.0.jar`,
alternatively use `gradle bootRun` to use the spring boot runner (the terminal will hang whilst this runs, stop with `CTRL-C`).

You can see the running application at `http://localhost:8080/hello-world`.

## Using an IDE
### IntelliJ
Create an IntelliJ project by running `gradle idea` then opening the directory in IntelliJ.

To run the application, run the main method listed above.

Install the __cucumber__ plugin to enable navigation through the features or running the features easier from within the IDE.

### Eclipse
Create an Eclipse project by running `gradle eclipse` then opening the workspace in Eclipse.


## The tests

### Add a health check
Add a health check as described in (this documentation)[http://www.baeldung.com/spring-boot-actuators] for the greeter,
thinking about how you would find, catch and handle any exceptions, and checking that the output passed the expected health check test.

### E-commerce
The ecommerce package defines a `UserGraphRepository` with a method `getForUser`

This will return a graph with the following Vertex and Edges:
* Customer --> Visit --[add to basket]--> Product
* Customer --> Visit --[check out] --> Order

The code is arranged in the `UserGraphRepository` in the order that the events happen so that
a check-out will purchase all the items that have been added to a basket since the last transaction.

Each of the actions (`add to basket` and `check out`) has a timestamp for when the event happened in the `createdAt` property.

Do not change the `UserGraphRepository`, only call the method `getForUser` to get the data for the user.

1. Order Details
Add domain, usecase and facades for the ecommerce package to get the order details as described by the json example file.

2. Abandoned Baskets
Add domain, usecase and facades for the ecommerce package to get the abandoned basket feature to pass.

## Tests for senior roles and adventurous others

### Handle Concurrent Requests

The `DoProcessing#doMultiThreadedProcess` uses a **synchronizedMap** but still doesn't return
the correct number of executions. Fix the code so that is does count the correct number of calls.

### Handle Many Requests

We can't keep a client waiting. Without changing the `LongRunningLockedProcess` class
make the `handle-many-requests.feature` pass.

Create a solution that **will allow** the work to be handed off to another process (not thread)
as we don't want to be keeping resources tied up on the web server, but for this exercise keep it
running within the same process.

## Returning the Completed Tests
Run `gradle clean` before archiving the directory with your answers in.

Create a zip of the work you have done.

Return the completed zip file to Intilery.

## Questions?

Send them to tom.mcmillen@intilery.com

## FAQ

* Can I use any additional frameworks or does it need to be hand coded?
_if you want to use other libraries and frameworks, go for it._



