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

            /* Reader, Writerを取得 */
            InputStream in = sock.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(streamReader);

            OutputStream out = sock.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(out);
            BufferedWriter writer = new BufferedWriter(streamWriter);

            /* Input(Request)を読み込み、そのままOutput(Response)に書き込む */
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line.isEmpty()) {
                    break;
                }
                sb.append(line);
                sb.append("\n");
            }
            String req = sb.toString();
            logger.info(req);
            writer.write(req);

            /* Writer, Socket, Readerの順にcloseする */
            writer.close();
            streamWriter.close();
            out.close();

            sock.close();

            reader.close();
            streamReader.close();
            in.close();
        } catch (IOException e) {
            logger.warning(e.toString());
        }
    }
}