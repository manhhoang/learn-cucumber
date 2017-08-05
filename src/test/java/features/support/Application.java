package features.support;

import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
public class Application {
    private String baseUrl;
    private String adminUrl;
    private Client client;
    private ResponseWrapper lastResponse;

    public void initialise() {
        baseUrl = String.format("http://localhost:%d", 8080);
        adminUrl = String.format("http://localhost:%d", 8081);
        client = ClientBuilder.newClient();
    }

    public void shutdown() {
        client.close();
    }

    public String getLastResponse() {
        return lastResponse.getContent();
    }

    public int getLastResponseStatus() {
        return lastResponse.getStatus();
    }

    public void getEndpoint(String path) {
        get(baseUrl + path);
    }

    public void getHealthCheck() {
        get(adminUrl + "/health");
    }

    public void process() {
        post(baseUrl + "/process/single", null);
    }

    public void concurrentProcess() {
        post(baseUrl + "/process/concurrent/fred", null);
    }

    private void post(String url, Entity entity) {
        getResponse(client.target(url).request(MediaType.APPLICATION_JSON_TYPE).buildPost(entity));
    }

    private void get(String url) {
        getResponse(client.target(url).request(MediaType.APPLICATION_JSON_TYPE).buildGet());
    }

    private void getResponse(Invocation invocation) {
        lastResponse = new ResponseWrapper(invocation.invoke());
    }

    private class ResponseWrapper {
        private final int status;
        private final String content;

        ResponseWrapper(Response response) {
            status = response.getStatus();
            content = response.readEntity(String.class);
        }

        int getStatus() {
            return status;
        }

        String getContent() {
            return content;
        }
    }
}
