package features.stepdefinitions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import features.support.Application;
import org.apache.commons.io.IOUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class HttpRequestSteps {
    @Autowired
    Application app;

    @When("^I check the application health$")
    public void i_check_the_application_health() throws Throwable {
        app.getHealthCheck();
    }

    @When("^I GET \"([^\"]*)\"$")
    public void I_GET(String url) throws Throwable {
        app.getEndpoint(url);
    }

    @Then("^I see status \"([^\"]*)\" for \"([^\"]*)\"$")
    public void I_see_status(String expectedResponse, String property) throws Throwable {
        assertThat(app.getLastResponse().contains(String.format(",\"%s\":{\"status\":\"%s\",", property, expectedResponse)), is(true));
    }

    @Then("^it has status (\\d+)$")
    public void it_has_status(int expectedStatus) throws Throwable {
        assertThat(app.getLastResponseStatus(), is(expectedStatus));
    }

    @Then("^I see JSON like \"([^\"]*)\"$")
    public void I_see_JSON_like(String filename) throws Throwable {
        JSONAssert.assertEquals(expectedJSON(filename), app.getLastResponse(), false);
    }

    private String expectedJSON(String filename) {
        InputStream in = this.getClass().getResourceAsStream(String.format("/json/%s.json", filename));
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(in, writer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Can't read JSON file " + filename);
        }
        return writer.toString();
    }
}
