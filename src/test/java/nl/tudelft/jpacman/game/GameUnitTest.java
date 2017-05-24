package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by basjenneboer on 5/24/17.
 */
class GameUnitTest {
    Game game;
    Player mockPlayer;
    Level mockLevel;

    @BeforeEach
    void initialize() {
        mockPlayer = mock(Player.class);
        mockLevel = mock(Level.class);

        game = new SinglePlayerGame(mockPlayer, mockLevel);
    }

    @Test
    void testPlayerAliveAndPelletsRemaining() {
        boolean playerAlive = true;
        int pelletsLeft = 5;
        when(mockLevel.isAnyPlayerAlive()).thenReturn(playerAlive);
        when(mockLevel.remainingPellets()).thenReturn(pelletsLeft);

        game.start();

        assertThat(game.isInProgress()).isTrue();
        verify(mockLevel).start();
    }

    @Test
    void testPlayerAliveAndNoPelletsRemaining() {
        boolean playerAlive = true;
        int pelletsLeft = 0;
        when(mockLevel.isAnyPlayerAlive()).thenReturn(playerAlive);
        when(mockLevel.remainingPellets()).thenReturn(pelletsLeft);

        game.start();

        assertThat(game.isInProgress()).isFalse();
        verify(mockLevel, never()).start();
    }

    @Test
    void testPlayerDeadAndPelletsRemaining() {
        boolean playerAlive = false;
        int pelletsLeft = 1;
        when(mockLevel.isAnyPlayerAlive()).thenReturn(playerAlive);
        when(mockLevel.remainingPellets()).thenReturn(pelletsLeft);

        game.start();

        assertThat(game.isInProgress()).isFalse();
        verify(mockLevel, never()).start();
    }

    @Test
    void testGameAlreadyInProgress() {
        boolean playerAlive = true;
        int pelletsLeft = 5;
        when(mockLevel.isAnyPlayerAlive()).thenReturn(playerAlive);
        when(mockLevel.remainingPellets()).thenReturn(pelletsLeft);
        game.start();

        game.start();
        verify(mockLevel, times(1)).start();
    }
}
