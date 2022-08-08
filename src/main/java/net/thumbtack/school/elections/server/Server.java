package net.thumbtack.school.elections.server;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.rest.mappers.MyExceptionMapper;
import net.thumbtack.school.elections.service.*;
import net.thumbtack.school.elections.settings.MyResourceConfig;
import net.thumbtack.school.elections.settings.Settings;
import net.thumbtack.school.elections.utils.MyBatisUtils;
import net.thumbtack.school.elections.settings.Mode;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;


import javax.ws.rs.core.UriBuilder;
import java.io.*;
import java.net.URI;

public class Server {
    private Mode mode = Mode.RAM;
    private UserService userService = new UserService(mode);
    private CandidateService candidateService = new CandidateService(mode);
    private VoterService voterService = new VoterService(mode);
    private VoteService voteService = new VoteService(mode);
    private DebugService debugService = new DebugService(mode);
    private static org.eclipse.jetty.server.Server jettyServer;

    private static void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                stopServer();
            }
        });
    }

    public static void createServer() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(Settings.getRestHTTPPort()).build();
        MyResourceConfig config = new MyResourceConfig();
        config.register(MyExceptionMapper.class);
        jettyServer = JettyHttpContainerFactory.createServer(baseUri, config);
    }

    public static void stopServer() {
        try {
            jettyServer.stop();
            jettyServer.destroy();
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        attachShutDownHook();
        createServer();
    }
/*
    public void startServer(String savedDataFileName) throws IOException, ClassNotFoundException {
        if (savedDataFileName == null && mode.equals(Mode.SQL)) {
            MyBatisUtils.initSqlSessionFactory();
        } else if (savedDataFileName == null && mode.equals(Mode.RAM)) {
            DataBase.createDataBase();
        } else if (savedDataFileName != null && mode.equals(Mode.SQL)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(savedDataFileName))) {
                MyBatisUtils.getSqlSessionFactory().openSession();
            }
        } else if (savedDataFileName != null && mode.equals(Mode.RAM)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(savedDataFileName))) {
                DataBase.readFile((DataBase) objectInputStream.readObject());
            }
        }
    }

    public void stopServer(String saveDataFileName) throws IOException {
        if (saveDataFileName != null && mode.equals(Mode.SQL)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(saveDataFileName))) {
                objectOutputStream.writeObject(MyBatisUtils.getSqlSessionFactory());
            }
        } else if (saveDataFileName != null && mode.equals(Mode.RAM)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(saveDataFileName))) {
                objectOutputStream.writeObject(DataBase.getDataBase());
            }
        }
    }

    public void clearDataBase() {
        debugService.clearDataBase();
    }*/
}
