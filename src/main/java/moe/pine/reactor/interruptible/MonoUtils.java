package moe.pine.reactor.interruptible;

import moe.pine.reactor.interruptible.annotation.Nullable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

public abstract class MonoUtils {
    @Nullable
    public static <T> T block(Mono<T> mono) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(mono::block);
    }

    @Nullable
    public static <T> T block(Mono<T> mono, Duration timeout) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(() -> mono.block(timeout));
    }

    @Nullable
    public static <T> Optional<T> blockOptional(Mono<T> mono) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(mono::blockOptional);
    }

    @Nullable
    public static <T> Optional<T> blockOptional(Mono<T> mono, Duration timeout) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(() -> mono.blockOptional(timeout));
    }
}
