package moe.pine.reactor.interruptible;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


@SuppressWarnings("ALL")
class ReactiveExceptionsTest {
    @Nested
    class Constructor {
        @Test
        void constructor() {
            assertThatNoException().isThrownBy(() -> {
                new ReactiveExceptions() {
                };
            });
        }
    }

    @Nested
    class IsInterrupted {
        @Test
        void interruptedException() {
            assertTrue(ReactiveExceptions.isInterrupted(new InterruptedException()));
        }

        @Test
        void ioException() {
            assertFalse(ReactiveExceptions.isInterrupted(new IOException()));
        }

        @Test
        void runtimeException() {
            assertFalse(ReactiveExceptions.isInterrupted(new RuntimeException()));
        }

        @Test
        void wrapped_interruptedException() {
            RuntimeException e = Exceptions.propagate(new InterruptedException());
            assertTrue(ReactiveExceptions.isInterrupted(e));
        }

        @Test
        void wrapped_IOException() {
            RuntimeException e = Exceptions.propagate(new IOException());
            assertFalse(ReactiveExceptions.isInterrupted(e));
        }

        @Test
        void multiple_interruptedException() {
            RuntimeException e =
                    Exceptions.multiple(
                            new RuntimeException(),
                            new InterruptedException()
                    );
            assertTrue(ReactiveExceptions.isInterrupted(e));
        }

        @Test
        void multiple_IOExceptions() {
            RuntimeException e =
                    Exceptions.multiple(
                            new RuntimeException(),
                            new IOException()
                    );
            assertFalse(ReactiveExceptions.isInterrupted(e));
        }
    }

    @Nested
    class UnwrapInterrupted {
        @Test
        void runtimeException() throws InterruptedException {
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
        void wrapped_interruptedException() throws InterruptedException {
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
}
