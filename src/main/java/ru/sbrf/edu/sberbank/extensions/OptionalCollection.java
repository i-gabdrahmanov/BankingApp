package ru.sbrf.edu.sberbank.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class OptionalCollection<T extends Collection<?>> {

    private static final OptionalCollection<?> EMPTY = new OptionalCollection<>(null);
    private static final OptionalCollection<?> EMPTY_LIST = new OptionalCollection<>(new ArrayList<>());


    final T collecion;

    public OptionalCollection(T collection) {
        this.collecion = collection;
    }

    public static <T extends Collection<?>> OptionalCollection<T> of(T value) {
        return new OptionalCollection<>(Objects.requireNonNull(value));
    }

    /**
     * Выполняет действие, если значение не пустое, иначе выполняет другое действие.
     *
     * @param action      Действие, которое следует выполнить, если значение не пустое.
     * @param emptyAction Действие, которое следует выполнить, если значение пустое.
     */

    public void ifNotEmptyOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (collecion != null && !collecion.isEmpty()) {
            action.accept(collecion);
        } else {
            emptyAction.run();
        }
    }

    /**
     * Выполняет действие, если значение не пустое, иначе выбрасывает исключение.
     *
     * @param action      Действие, которое следует выполнить, если значение не пустое.
     * @param exceptionSupplier Исключение, которое требуется выбросить.
     */
    public <X extends Throwable> void ifNotEmptyOrThrow(
            Consumer<? super T> action,
            Supplier<? extends X> exceptionSupplier) throws X {
        if (collecion != null && !collecion.isEmpty()) {
            action.accept(collecion);
        } else {
            throw exceptionSupplier.get();
        }

    }
}