package moe.pine.reactor.interruptible;

import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class FluxUtilsTest {
    @Test
    @SuppressWarnings("unchecked")
    public void blockFirstTest() throws InterruptedException {
        Flux<Integer> flux = mock(Flux.class);
        when(flux.blockFirst()).thenReturn(1);

        assertEquals(Integer.valueOf(1), FluxUtils.blockFirst(flux));

        verify(flux).blockFirst();
        verifyNoMoreInteractions(flux);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void blockFirstTest_interrupted() {
        InterruptedException e1 = new InterruptedException();
        Flux<Integer> flux = mock(Flux.class);
        when(flux.blockFirst()).thenThrow(Exceptions.propagate(e1));

        try {
            FluxUtils.blockFirst(flux);
            fail();
        } catch (InterruptedException e2) {
            assertSame(e1, e2);
        }

        verify(flux).blockFirst();
        verifyNoMoreInteractions(flux);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void blockFirstTest_withDuration() throws InterruptedException {
        Duration duration = Duration.ofSeconds(3L);
        Flux<Integer> flux = mock(Flux.class);
        when(flux.blockFirst(duration)).thenReturn(1);

        assertEquals(Integer.valueOf(1), FluxUtils.blockFirst(flux, duration));

        verify(flux).blockFirst(duration);
        verifyNoMoreInteractions(flux);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void blockFirstTest_interrupted_withDuration() {
        Duration duration = Duration.ofSeconds(3L);
        InterruptedException e1 = new InterruptedException();
        Flux<Integer> flux = mock(Flux.class);
        when(flux.blockFirst(duration)).thenThrow(Exceptions.propagate(e1));

        try {
            FluxUtils.blockFirst(flux, duration);
            fail();
        } catch (InterruptedException e2) {
            assertSame(e1, e2);
        }

        verify(flux).blockFirst(duration);
        verifyNoMoreInteractions(flux);
    }
}
