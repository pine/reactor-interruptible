package moe.pine.reactor.interruptible;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

public class MonoUtils {
    public MonoUtils() {
    }

    public static <T> T block(Mono<T> mono) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(mono::block);
    }

    public static <T> T block(Mono<T> mono, Duration timeout) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(() -> mono.block(timeout));
    }

    public static <T> Optional<T> blockOptional(Mono<T> mono) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(mono::blockOptional);
    }

    public static <T> Optional<T> blockOptional(Mono<T> mono, Duration timeout) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(() -> mono.blockOptional(timeout));
    }
}
