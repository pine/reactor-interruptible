package moe.pine.reactor.interruptible;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxUtils {
    public FluxUtils() {
    }

    public static <T> T blockFirst(Flux<T> flux) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(flux::blockFirst);
    }

    public static <T> T blockFirst(Flux<T> flux, Duration timeout) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(() -> flux.blockFirst(timeout));
    }

    public static <T> T blockLast(Flux<T> flux) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(flux::blockLast);
    }

    public static <T> T blockLast(Flux<T> flux, Duration duration) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(() -> flux.blockLast(duration));
    }
}
