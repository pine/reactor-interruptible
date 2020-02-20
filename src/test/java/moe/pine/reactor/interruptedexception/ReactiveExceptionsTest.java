package moe.pine.reactor.interruptedexception;

import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        RuntimeException e = Exceptions.propagate(new InterruptedException());
        assertTrue(ReactiveExceptions.isInterrupted(e));
    }

    @Test
    public void isInterruptedTest_wrapped_IOException() {
        RuntimeException e = Exceptions.propagate(new IOException());
        assertFalse(ReactiveExceptions.isInterrupted(e));
    }

    @Test
    public void isInterruptedTest_multiple_interruptedException() {
        RuntimeException e =
            Exceptions.multiple(
                new RuntimeException(),
                new InterruptedException()
            );
        assertTrue(ReactiveExceptions.isInterrupted(e));
    }

    @Test
    public void isInterruptedTest_multiple_IOExceptions() {
        RuntimeException e =
            Exceptions.multiple(
                new RuntimeException(),
                new IOException()
            );
        assertFalse(ReactiveExceptions.isInterrupted(e));
    }

    @Test
    public void unwrapInterruptedTest_runtimeException() throws InterruptedException {
        RuntimeException e1 = new RuntimeException();
        try {
            ReactiveExceptions.unwrapInterrupted(() -> {
                throw e1;
            });
            fail();
        } catch (RuntimeException e2) {
            assertSame(e1, e2);
        }
    }

    @Test
    public void unwrapInterruptedTest_wrapped_interruptedException() throws InterruptedException {
        InterruptedException e1 = new InterruptedException();
        try {
            ReactiveExceptions.unwrapInterrupted(() -> {
                throw Exceptions.propagate(e1);
            });
            fail();
        } catch (InterruptedException e2) {
            assertNotSame(e1, e2);
            assertTrue(e2.getCause() instanceof RuntimeException);
            assertSame(e1, e2.getCause().getCause());
        }
    }
}
