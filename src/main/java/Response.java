import enums.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by yuki.wakisaka on 2017/03/22.
 */
public class Response {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public Status status;
    public Map<String, String> header = new HashMap<String, String>();
    public byte[] body = {};

    Response(int status, Map<String, String> header, byte[] body) {
        try {
            this.status = Status.getStatus(status);
            if (header != null) {
                this.header = header;
            }
            if (body != null) {
                this.body = body;
            }
        } catch (Status.NoSuchStatusException e) {
            logger.info(e.toString());
            this.status = Status.InternalServerError;
            this.body = "500: Internal Server Error".getBytes();
        }
    }

    public byte[] getBytes() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.status.getHead());
        sb.append("\n");

        for (Map.Entry e : this.header.entrySet()) {
            sb.append(e.getKey());
            sb.append(": ");
            sb.append(e.getValue());
            sb.append("\n");
        }
        sb.append("\n");

        byte[] head = sb.toString().getBytes();
        byte[] res = new byte[head.length + this.body.length];

        System.arraycopy(head, 0, res, 0, head.length);
        System.arraycopy(this.body, 0, res, head.length, this.body.length);

        return res;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}
