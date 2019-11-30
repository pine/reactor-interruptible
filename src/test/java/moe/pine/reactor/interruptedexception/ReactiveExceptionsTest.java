package moe.pine.reactor.interruptedexception;

import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReactiveExceptionsTest {
    @Test
    public void isInterruptedTest_interruptedException() {
        assertTrue(ReactiveExceptions.isInterrupted(new InterruptedException()));
    }

    @Test
    public void isInterruptedTest_IOException() {
        assertFalse(ReactiveExceptions.isInterrupted(new IOException()));
    }

    @Test
    public void isInterruptedTest_runtimeException() {
        assertFalse(ReactiveExceptions.isInterrupted(new RuntimeException()));
    }

    @Test
    public void isInterruptedTest_wrapped_interruptedException() {
        final RuntimeException e = Exceptions.propagate(new InterruptedException());
        assertTrue(ReactiveExceptions.isInterrupted(e));
    }

    @Test
    public void isInterruptedTest_wrapped_IOException() {
        final RuntimeException e = Exceptions.propagate(new IOException());
        assertFalse(ReactiveExceptions.isInterrupted(e));
    }

    @Test
    public void isInterruptedTest_multiple_interruptedException() {
        final RuntimeException e = Exceptions.multiple(
            new RuntimeException(),
            new InterruptedException()
        );
        assertTrue(ReactiveExceptions.isInterrupted(e));
    }

    @Test
    public void isInterruptedTest_multiple_IOExceptions() {
        final RuntimeException e = Exceptions.multiple(
            new RuntimeException(),
            new IOException()
        );
        assertFalse(ReactiveExceptions.isInterrupted(e));
    }
}
