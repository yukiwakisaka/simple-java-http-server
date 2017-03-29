import enums.Method;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by yuki.wakisaka on 2017/03/21.
 */
public class SocketHandler {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Socket socket;

    SocketHandler(Socket socket) {
        this.socket = socket;
    }

    public void handle() throws IOException {
        /* Reader, Writerを取得 */
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        List<String> reqArr = new ArrayList();
        while (true) {
            String line = reader.readLine();
            if (line.isEmpty()) {
                break;
            }
            reqArr.add(line);
        }
        logger.info(reqArr.toString());

        try {
            Response res = new RequestHandler().handle(new Request(reqArr));
            OutputStream out = this.socket.getOutputStream();
            out.write(res.getBytes());
        } catch (Method.NoSuchMethodException e) {
            e.printStackTrace();
        }

        this.socket.close();
    }
}
