package moe.pine.reactor.interruptedexception;

import reactor.core.Exceptions;

import java.util.List;
import java.util.function.Supplier;

public final class ReactiveExceptions {
    public static boolean isInterrupted(Throwable t) {
        if (Exceptions.isMultiple(t)) {
            final List<Throwable> unwrappedExceptions = Exceptions.unwrapMultiple(t);
            for (final Throwable unwrapped : unwrappedExceptions) {
                if (unwrapped instanceof InterruptedException) {
                    return true;
                }
            }
        }

        return Exceptions.unwrap(t) instanceof InterruptedException;
    }

    public static <T> T unwrapInterrupted(Supplier<T> supplier) throws InterruptedException {
        try {
            return supplier.get();
        } catch (RuntimeException e) {
            if (ReactiveExceptions.isInterrupted(e)) {
                final InterruptedException interruptedException = new InterruptedException();
                interruptedException.initCause(e);
                throw interruptedException;
            }
            throw e;
        }
    }
}
