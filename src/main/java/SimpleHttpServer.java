/**
 * Created by yuki.wakisaka on 2017/03/20.
 */

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
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
            StringBuilder sb = new StringBuilder();

            /* HeaderをOutput(Response)に書き込む */
            sb.append("HTTP/1.1 200 Success\n");
            sb.append("Content-Type: text/html; charset=UTF-8\n");
            LocalDateTime ct = LocalDateTime.now();
            sb.append("Date: ");
            sb.append(ct.toString());
            sb.append("\n\n");

            /* Input(Request)を読み込み、そのままOutput(Response)に書き込む */
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