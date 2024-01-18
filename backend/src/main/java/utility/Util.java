package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Util {

    private static final String TAG = "Util";

    public static <T>  List<T> toList(Set<T> set) {
        List<T> list = new ArrayList<>();
        for(T item: set) {
            list.add(item);
        }

        Log.d(TAG, "list: "+list);
        return  list;
    }
}
