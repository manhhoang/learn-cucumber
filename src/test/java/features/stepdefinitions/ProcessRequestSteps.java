package features.stepdefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import features.support.Application;
import features.support.Timer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProcessRequestSteps {
    @Autowired
    Timer timer;

    @Autowired
    Application app;

    @When("^I POST (\\d+) serial processes$")
    public void I_POST_serial_processes(int count) throws Throwable {
        timer.start();
        while (count-- > 0) {
            app.process();
        }
    }

    @Then("^I get a valid response for each one$")
    public void I_get_a_valid_response_for_each_one() throws Throwable {
        assertThat(app.getLastResponseStatus() > 199, is(true));
        assertThat(app.getLastResponseStatus() < 300, is(true));
    }

    @Then("^it takes less than (\\d+) second$")
    public void it_takes_less_than_second(int interval) throws Throwable {
        assertThat(timer.stop() < interval, is(true));
    }

    @When("^I POST (\\d+) concurrent requests$")
    public void i_POST_concurrent_requests(int count) throws Throwable {
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        while (count-- > 0) {
            executorService.submit(() -> app.concurrentProcess());
        }
        executorService.shutdown();
        executorService.awaitTermination(25, TimeUnit.SECONDS);
    }

    @Then("^I get the count (\\d+) back$")
    public void i_get_the_count_back(int count) throws Throwable {
        app.concurrentProcess();
        assertThat(Integer.parseInt(app.getLastResponse()) - 1, is(count));
    }
}
