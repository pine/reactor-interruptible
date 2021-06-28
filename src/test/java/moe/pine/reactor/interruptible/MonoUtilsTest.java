package moe.pine.reactor.interruptible;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("ALL")
class MonoUtilsTest {
    @Nested
    class Block {
        @Test
        void interrupted() {
            InterruptedException exception = new InterruptedException();
            Mono<Integer> mono = mock(Mono.class);
            when(mono.block()).thenThrow(Exceptions.propagate(exception));

            assertThatThrownBy(() -> MonoUtils.block(mono)).isSameAs(exception);

            verify(mono).block();
            verify(mono, never()).blockOptional();
        }

        @Test
        void notInterrupted() throws InterruptedException {
            Mono<Integer> mono = mock(Mono.class);
            when(mono.block()).thenReturn(1);

            assertThat(Integer.valueOf(1)).isEqualTo(MonoUtils.block(mono));

            verify(mono).block();
            verify(mono, never()).blockOptional();
        }

        @Test
        void interrupted_withDuration() {
            InterruptedException exception = new InterruptedException();
            Duration duration = Duration.ofSeconds(3L);
            Mono<Integer> mono = mock(Mono.class);
            when(mono.block(any())).thenThrow(Exceptions.propagate(exception));

            assertThatThrownBy(() -> MonoUtils.block(mono, duration)).isSameAs(exception);

            verify(mono).block(duration);
            verify(mono, never()).blockOptional(any());
        }

        @Test
        void notInterrupted_withDuration() throws InterruptedException {
            Duration duration = Duration.ofSeconds(3L);
            Mono<Integer> mono = mock(Mono.class);
            when(mono.block(any())).thenReturn(1);

            assertThat(Integer.valueOf(1)).isEqualTo(MonoUtils.block(mono, duration));

            verify(mono).block(duration);
            verify(mono, never()).blockOptional(any());
        }
    }
}
