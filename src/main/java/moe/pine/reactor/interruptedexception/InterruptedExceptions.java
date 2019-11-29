package moe.pine.reactor.interruptedexception;

import reactor.core.Exceptions;

import java.util.List;

public final class InterruptedExceptions {
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
}
