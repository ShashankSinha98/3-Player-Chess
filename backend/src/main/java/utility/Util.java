package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Util class for converting from set datatype to list
 **/
public class Util {

    private static final String TAG = "Util";

    /**
     * method to convert from set datatype to list
     **/
    public static <T>  List<T> toList(Set<T> set) {
        List<T> list = new ArrayList<>(set);

        Log.d(TAG, "list: "+list);
        return  list;
    }
}
