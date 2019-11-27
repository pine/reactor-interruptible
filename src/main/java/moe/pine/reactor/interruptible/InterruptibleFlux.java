package moe.pine.reactor.interruptible;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class InterruptibleFlux {
    public static <T> T blockFirst(Flux<T> flux) throws InterruptedException {
        return flux.blockFirst(); // TODO
    }

    public static <T> T blockFirst(Flux<T> flux, Duration timeout) throws InterruptedException {
        return flux.blockFirst(timeout); // TODO
    }
}
