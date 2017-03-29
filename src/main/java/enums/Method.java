package enums;

/**
 * Created by yuki.wakisaka on 2017/03/22.
 */
public enum Method {
    OPTIONS("OPTIONS"),
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    TRACE("TRACE"),
    CONNECT("CONNECT");

    private String method;

    Method(String method) {
        this.method = method;
    }

    public static Method getMethod(String method) throws NoSuchMethodException {
        for (Method m : Method.values()) {
            if (m.method.equals(method.toUpperCase())) {
                return m;
            }
        }
        throw new NoSuchMethodException(method);
    }

    public static class NoSuchMethodException extends Exception {
        NoSuchMethodException(String method) {
            super("Method \"" + method.toUpperCase() + "\"is not defined.");
        }
    }
}
