package testutils;

import java.util.List;
import java.util.function.Predicate;

public class ListUtils {
    public static <T> boolean existsItemMatchingCondition(List<T> list, Predicate<? super T> predicate) {
        return list.stream().anyMatch(predicate);
    }
}
