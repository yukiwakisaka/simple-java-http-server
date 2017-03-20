/**
 * Created by yuki.wakisaka on 2017/03/20.
 */

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class SimpleHttpServer {

    private static final int PORT = 8000;
    private static final String HTML_DIR = "src/main/resources/html/";

    public static void main(String[] args) {

        Logger logger = Logger.getLogger("main");

        try {
            ServerSocket socket = new ServerSocket(PORT);
            logger.info("serving HTTP on " + socket.getInetAddress() + ":" + socket.getLocalPort() + " ...");
            Socket sock = socket.accept();

            /* Reader, Writerを取得 */
            InputStream in = sock.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(streamReader);

            OutputStream out = sock.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(out);
            BufferedWriter writer = new BufferedWriter(streamWriter);
            StringBuilder reqSb = new StringBuilder();

            /* Input(Request)を読み込む */
            while (true) {
                String line = reader.readLine();
                if (line.isEmpty()) {
                    break;
                }
                reqSb.append(line);
                reqSb.append("\n");
            }
            logger.info(reqSb.toString());

            StringBuilder resSb = new StringBuilder();

            /* HeaderをOutput(Response)に書き込む */
            resSb.append("HTTP/1.1 200 Success\n");
            resSb.append("Content-Type: text/html; charset=UTF-8\n");
            LocalDateTime ct = LocalDateTime.now();
            resSb.append("Date: ");
            resSb.append(ct.toString());
            resSb.append("\n\n");

            /* Body(HTML)を読み込み、Output(Response)に書き込む */
            try {
                File htmlFile = getHtmlFile("sample.html");
                logger.info(htmlFile.toString());
                FileReader fileReader = new FileReader(htmlFile);
                BufferedReader fileBufferedReader = new BufferedReader(fileReader);
                while (true) {
                    String line = fileBufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    logger.info(line);
                    resSb.append(line);
                    resSb.append("\n");
                }
                fileBufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                logger.warning(e.toString());
                resSb.append("File Not Found");
            }

            writer.write(resSb.toString());

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

    private static File getHtmlFile(String path) {
        return new File(HTML_DIR + path);
    }
}