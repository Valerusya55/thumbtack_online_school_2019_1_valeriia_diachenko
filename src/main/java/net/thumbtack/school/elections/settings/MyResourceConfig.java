package net.thumbtack.school.elections.settings;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        packages("net.thumbtack.school.elections.resources",
                "net.thumbtack.school.elections.mappers");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
}
