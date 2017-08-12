package features;

import com.jd.tinkerpop.web.Application;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;

import javax.annotation.PostConstruct;

@RunWith(Cucumber.class)
public class RunCucumberTests {

    @PostConstruct
    public void bootApplication() {
        EmbeddedWebApplicationContext webApplicationContext = (EmbeddedWebApplicationContext) SpringApplication.run(Application.class);

        // retrieve the actual webserver port
        int webServerPort = webApplicationContext.getEmbeddedServletContainer().getPort();
    }
}
