package moe.pine.reactor.interruptible;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("ALL")
class FluxUtilsTest {
    @Nested
    class BlockFirst {
        @Test
        void interrupted() {
            InterruptedException exception = new InterruptedException();
            Flux<Integer> flux = mock(Flux.class);
            when(flux.blockFirst()).thenThrow(Exceptions.propagate(exception));

            assertThatThrownBy(() -> FluxUtils.blockFirst(flux)).isSameAs(exception);

            verify(flux).blockFirst();
            verify(flux, never()).blockLast();
        }

        @Test
        void notInterrupted() throws InterruptedException {
            Flux<Integer> flux = mock(Flux.class);
            when(flux.blockFirst()).thenReturn(1);

            assertThat(Integer.valueOf(1)).isEqualTo(FluxUtils.blockFirst(flux));

            verify(flux).blockFirst();
            verify(flux, never()).blockLast();
        }

        @Test
        void interrupted_withDuration() {
            Duration duration = Duration.ofSeconds(3L);
            InterruptedException exception = new InterruptedException();
            Flux<Integer> flux = mock(Flux.class);
            when(flux.blockFirst(any())).thenThrow(Exceptions.propagate(exception));

            assertThatThrownBy(() -> FluxUtils.blockFirst(flux, duration)).isSameAs(exception);

            verify(flux).blockFirst(duration);
            verify(flux, never()).blockLast(any());
        }

        @Test
        void notInterrupted_withDuration() throws InterruptedException {
            Duration duration = Duration.ofSeconds(3L);
            Flux<Integer> flux = mock(Flux.class);
            when(flux.blockFirst(any())).thenReturn(1);

            assertThat(Integer.valueOf(1)).isEqualTo(FluxUtils.blockFirst(flux, duration));

            verify(flux).blockFirst(duration);
            verify(flux, never()).blockLast(any());
        }
    }

    @Nested
    class BlockLast {
        @Test
        void interrupted() {
            InterruptedException exception = new InterruptedException();
            Flux<Integer> flux = mock(Flux.class);
            when(flux.blockLast()).thenThrow(Exceptions.propagate(exception));

            assertThatThrownBy(() -> FluxUtils.blockLast(flux)).isSameAs(exception);

            verify(flux, never()).blockFirst();
            verify(flux).blockLast();
        }

        @Test
        void notInterrupted() throws InterruptedException {
            Flux<Integer> flux = mock(Flux.class);
            when(flux.blockLast()).thenReturn(1);

            assertThat(Integer.valueOf(1)).isEqualTo(FluxUtils.blockLast(flux));

            verify(flux, never()).blockFirst();
            verify(flux).blockLast();
        }

        @Test
        void interrupted_withDuration() {
            Duration duration = Duration.ofSeconds(3L);
            InterruptedException exception = new InterruptedException();
            Flux<Integer> flux = mock(Flux.class);
            when(flux.blockLast(any())).thenThrow(Exceptions.propagate(exception));

            assertThatThrownBy(() -> FluxUtils.blockLast(flux, duration)).isSameAs(exception);

            verify(flux, never()).blockFirst(any());
            verify(flux).blockLast(duration);
        }

        @Test
        void notInterrupted_withDuration() throws InterruptedException {
            Duration duration = Duration.ofSeconds(3L);
            Flux<Integer> flux = mock(Flux.class);
            when(flux.blockLast(any())).thenReturn(1);

            assertThat(Integer.valueOf(1)).isEqualTo(FluxUtils.blockLast(flux, duration));

            verify(flux, never()).blockFirst(any());
            verify(flux).blockLast(duration);
        }
    }
}
