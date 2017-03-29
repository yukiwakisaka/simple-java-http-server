import enums.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuki.wakisaka on 2017/03/22.
 */
public class Request {

    public Method method;
    public String file;
    public String version;

    public Map<String, String> header = new HashMap<String, String>();

    Request(List<String> reqArr) throws Method.NoSuchMethodException {
        String[] requestLine = reqArr.get(0).split(" ");
        this.method = Method.valueOf(requestLine[0]);
        this.file = requestLine[1];
        this.version = requestLine[2];

        for (int i = 1; i < reqArr.size(); i++) {
            String[] strHeader = reqArr.get(i).split(":");
            this.header.put(strHeader[0].trim(), strHeader[1].trim());
        }
    }
}
