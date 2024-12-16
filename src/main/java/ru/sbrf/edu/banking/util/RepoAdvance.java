package ru.sbrf.edu.banking.util;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public class RepoAdvance {
    public static <T, R> boolean checkAndSet(Supplier<T> c, RepoObjectWrapper<R> target) {
        T t = c.get();
        if (t instanceof Optional) {
            Optional<T> r = (Optional<T>) c.get();
            if (r.isEmpty()) {
                return false;
            } else {
                target.setValue((R) r.get());
                return true;
            }
        } else if (t instanceof Collection<?>) {
            return false; // todo допилить
        } else {
            if (t == null) {
                return false;
            } else {
                target.setValue((R) t);
                return true;
            }
        }
    }
}
