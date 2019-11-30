package moe.pine.reactor.interruptedexception;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class Monos {
    public static <T> T block(Mono<T> mono) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(mono::block);
    }

    public static <T> T block(Mono<T> mono, Duration timeout) throws InterruptedException {
        return ReactiveExceptions.unwrapInterrupted(() -> mono.block(timeout));
    }
}
