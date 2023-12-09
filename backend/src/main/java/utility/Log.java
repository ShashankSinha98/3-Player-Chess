package utility;

public class Log {

    public static void d(String tag, Object message) {
        System.out.println(tag+" : " + message);
    }

    public static void e(String tag, Object message) {
        System.err.println(tag+" : " + message);
    }
}
