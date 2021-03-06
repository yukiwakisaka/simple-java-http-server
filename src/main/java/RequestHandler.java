import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by yuki.wakisaka on 2017/03/21.
 */
public class RequestHandler {

    private final String DOC_ROOT = "src/main/resources/";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    HashMap header = new HashMap();

    RequestHandler() {
        this.header.put("Date", LocalDateTime.now());
    }

    public Response handle(Request request) {
        StringBuilder resSb = new StringBuilder();
        LocalDateTime ct = LocalDateTime.now();
        try {
            switch (request.method) {
                case GET:
                    byte[] body = Files.readAllBytes(Paths.get(this.DOC_ROOT + request.file));

                    // TODO MIMEをいい感じに取得するメソッド的なものが欲しいよね
                    if (request.file.endsWith("jpg")) {
                        this.header.put("Content-Type", "image/jpeg");
                    } else if (request.file.endsWith("html")) {
                        this.header.put("Content-Type", "text/html; chatset=UTF-8");
                    }
                    this.header.put("Content-Length", body.length);

                    return new Response(200, this.header, body);

                default:
                    return new Response(405, this.header, null);
            }
        } catch (FileNotFoundException | NoSuchFileException e) {
            logger.info(e.toString());
            return new Response(404, this.header, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(500, this.header, null);
        }
    }

//    private File getFile(String path) {
//        return new File(this.DOC_ROOT + path);
//    }
}
