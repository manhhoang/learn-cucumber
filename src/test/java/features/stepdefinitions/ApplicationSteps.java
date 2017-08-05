package features.stepdefinitions;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import features.support.Application;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationSteps {
    @Autowired
    Application app;

    @After
    public void shutdown() {
        app.shutdown();
    }

    @Given("^a running application$")
    public void a_running_application() throws Throwable {
        app.initialise(); //TODO could pass in a config file
    }
}
