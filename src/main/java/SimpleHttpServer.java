import java.io.*;
import java.net.*;
import java.util.logging.Logger;

/**
 * Created by yuki.wakisaka on 2017/03/20.
 */
public class SimpleHttpServer {

    private static final int PORT = 8000;

    private static Logger logger = Logger.getLogger("main");

    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(PORT);
            logger.info("serving HTTP on " + socket.getInetAddress() + ":" + socket.getLocalPort() + " ...");
            new SocketHandler(socket.accept()).handle();
        } catch (IOException e) {
            logger.warning(e.toString());
        }
    }
}