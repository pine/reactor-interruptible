package moe.pine.reactor.interruptible;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class InterruptibleMono {
    public static <T> T block(Mono<T> mono) throws InterruptedException {
        return mono.block(); // TODO
    }

    public static <T> T block(Mono<T> mono, Duration timeout) throws InterruptedException {
        return mono.block(timeout); // TODO
    }
}
