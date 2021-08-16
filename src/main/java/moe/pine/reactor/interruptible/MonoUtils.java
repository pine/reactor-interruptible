package moe.pine.reactor.interruptible;

import moe.pine.reactor.interruptible.annotation.Nullable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

import static moe.pine.reactor.interruptible.ReactiveExceptions.unwrapInterrupted;

public abstract class MonoUtils {
    @Nullable
    public static <T> T block(Mono<T> mono) throws InterruptedException {
        return unwrapInterrupted(mono::block);
    }

    @Nullable
    public static <T> T block(Mono<T> mono, Duration timeout) throws InterruptedException {
        return unwrapInterrupted(() -> mono.block(timeout));
    }

    public static <T> Optional<T> blockOptional(Mono<T> mono) throws InterruptedException {
        return Objects.requireNonNull(unwrapInterrupted(mono::blockOptional));
    }

    public static <T> Optional<T> blockOptional(Mono<T> mono, Duration timeout) throws InterruptedException {
        return Objects.requireNonNull(unwrapInterrupted(() -> mono.blockOptional(timeout)));
    }
}
