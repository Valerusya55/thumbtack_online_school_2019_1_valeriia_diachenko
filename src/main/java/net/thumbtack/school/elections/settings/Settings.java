package net.thumbtack.school.elections.settings;

public class Settings {
    private Mode mode;
    private static int restHttpPort = 8080;

    public static int getRestHTTPPort() {
        return restHttpPort;
    }
}
