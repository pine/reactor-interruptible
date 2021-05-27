package moe.pine.reactor.interruptible;

import reactor.core.Exceptions;

import java.util.List;
import java.util.function.Supplier;

class ReactiveExceptions {
    static boolean isInterrupted(Throwable t) {
        if (Exceptions.isMultiple(t)) {
            List<Throwable> unwrappedExceptions = Exceptions.unwrapMultiple(t);
            for (Throwable unwrapped : unwrappedExceptions) {
                if (unwrapped instanceof InterruptedException) {
                    return true;
                }
            }
        }

        return Exceptions.unwrap(t) instanceof InterruptedException;
    }

    static <T> T unwrapInterrupted(Supplier<T> supplier) throws InterruptedException {
        try {
            return supplier.get();
        } catch (RuntimeException e) {
            if (isInterrupted(e)) {
                InterruptedException interruptedException = new InterruptedException();
                interruptedException.initCause(e);
                throw interruptedException;
            }
            throw e;
        }
    }
}