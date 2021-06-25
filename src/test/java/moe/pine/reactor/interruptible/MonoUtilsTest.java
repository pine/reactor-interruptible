package moe.pine.reactor.interruptible;

import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class MonoUtilsTest {
    @Test
    void blockTest_interrupted() {
        InterruptedException exception = new InterruptedException();
        Mono<Integer> mono = mock(Mono.class);
        when(mono.block()).thenThrow(Exceptions.propagate(exception));

        assertThatThrownBy(() -> MonoUtils.block(mono)).isSameAs(exception);

        verify(mono).block();
        verifyNoMoreInteractions(mono);
    }

    @Test
    void blockTest_notInterrupted() throws InterruptedException {
        Mono<Integer> mono = mock(Mono.class);
        when(mono.block()).thenReturn(1);

        assertThat(Integer.valueOf(1)).isEqualTo(MonoUtils.block(mono));

        verify(mono).block();
        verifyNoMoreInteractions(mono);
    }
}
