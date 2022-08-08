package net.thumbtack.school.elections.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.thumbtack.school.elections.exeption.ErrorResponse;
import net.thumbtack.school.elections.exeption.ServerException;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MyClient {
    private static final Gson GSON = new GsonBuilder().create();

    private static Client createClient() {
        return ClientBuilder.newClient().register(JacksonFeature.class).
                register(new LoggingFeature(
                        Logger.getLogger(MyClient.class.getName()), Level.INFO, null, null));
    }

    public Object get(String url, Class<?> classResponse) {
        Client client = createClient();
        WebTarget myResource = client.target(url);
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
        Response response = builder.get();
        String body = response.readEntity(String.class);
        int httpCode = response.getStatus();
        Object obj;
        if(httpCode == Response.Status.OK.getStatusCode())
            obj = GSON.fromJson(body, classResponse);
        else {
            obj = GSON.fromJson(body, ErrorResponse.class);
        }
        client.close();
        return obj;
    }

    public Object post(String url, Object object, Class<?> classResponse,String token) {
        Client client = createClient();
        WebTarget myResource = client.target(url);
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON).header("token",token);
        Response response = builder.post(Entity.json(object));
        String body = response.readEntity(String.class);
        int httpCode = response.getStatus();
        Object obj;
        if(httpCode == Response.Status.OK.getStatusCode()) {
            obj = GSON.fromJson(body, classResponse);
        }
        else {
            obj = GSON.fromJson(body, ErrorResponse.class);
        }
        client.close();
        return obj;
    }

    public Object put(String url, Object object,Class<?> classResponse,String token) {
        Client client = createClient();
        WebTarget myResource = client.target(url);
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON).header("token",token);
        Response response = builder.put(Entity.json(object));

        String body = response.readEntity(String.class);
        int httpCode = response.getStatus();
        Object obj;
        if(httpCode == Response.Status.OK.getStatusCode()) {
            obj = GSON.fromJson(body, classResponse);
        }
        else {
            obj = GSON.fromJson(body, ErrorResponse.class);
        }
        client.close();
        return obj;
    }

    public Object delete(String url, Class<?> classResponse, String token) {
        Client client = createClient();
        WebTarget myResource = client.target(url);
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON).header("token",token);
        Response response = builder.delete();
        String body = response.readEntity(String.class);
        int httpCode = response.getStatus();
        Object obj;
        if(httpCode == Response.Status.OK.getStatusCode())
            obj = GSON.fromJson(body, classResponse);
        else {
            obj = GSON.fromJson(body, ErrorResponse.class);
        }
        client.close();
        return obj;
    }
}
