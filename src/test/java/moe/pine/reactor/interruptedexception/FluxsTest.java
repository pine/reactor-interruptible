package moe.pine.reactor.interruptedexception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Flux.class)
public class FluxsTest {
    @Rule
    public Timeout timeout = new Timeout(30L, TimeUnit.SECONDS);

    @Test
    @SuppressWarnings("unchecked")
    public void blockFirstTest() throws InterruptedException {
        Flux<Integer> flux = mock(Flux.class);
        when(flux.blockFirst()).thenReturn(1);

        assertEquals(Integer.valueOf(1), Fluxs.blockFirst(flux));

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
            Fluxs.blockFirst(flux);
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

        assertEquals(Integer.valueOf(1), Fluxs.blockFirst(flux, duration));

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
            Fluxs.blockFirst(flux, duration);
            fail();
        } catch (InterruptedException e2) {
            assertSame(e1, e2);
        }

        verify(flux).blockFirst(duration);
        verifyNoMoreInteractions(flux);
    }
}
