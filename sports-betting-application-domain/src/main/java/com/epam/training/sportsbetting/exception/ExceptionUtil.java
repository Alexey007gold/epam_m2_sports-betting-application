package com.epam.training.sportsbetting.exception;

public class ExceptionUtil {

    private ExceptionUtil() {}

    public static <T, R, E extends Exception> R uncheck(ThrowingFunction<T, R, E> r, T arg) {
        try {
            return r.apply(arg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
