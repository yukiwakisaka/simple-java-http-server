package enums;

/**
 * Created by yuki.wakisaka on 2017/03/22.
 */
public enum Status {
    OK(200, "OK"),
    NotFound(404, "Not Found"),
    MethodNotAllowed(405, "Method Not Allowed"),
    InternalServerError(500, "Internal Server Error");

    public int code;
    private String _head;

    Status(int code, String status) {
        this.code = code;
        this._head = "HTTP/1.1 " + code + " " + status;
    }

    public String getHead() {
        return this._head;
    }

    public static Status getStatus(int code) throws NoSuchStatusException {
        for (Status s : Status.values()) {
            if (code == s.code) {
                return s;
            }
        }
        throw new NoSuchStatusException(code);
    }

    public static class NoSuchStatusException extends Exception {
        NoSuchStatusException(int code) {
            super("Status " + code + "is not defined.");
        }
    }
}
