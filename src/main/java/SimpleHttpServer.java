/**
 * Created by yuki.wakisaka on 2017/03/20.
 */

// https://docs.oracle.com/javase/jp/8/docs/api/

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class SimpleHttpServer {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger("main");

        final int PORT = 8000;

        try {
            final InetAddress HOST = InetAddress.getLocalHost();
            ServerSocket socket = new ServerSocket(PORT, 10, HOST);
            logger.info("serving HTTP on " + socket.getInetAddress() + ":" + socket.getLocalPort() + " ...");

            Socket sock = socket.accept();
            OutputStream out = sock.getOutputStream();
            out.write("Hello Client!\n".getBytes());
            out.close();
            sock.close();

            logger.info("Hello Request!");
        } catch (IOException e) {
            logger.warning(e.toString());
        }
    }
}