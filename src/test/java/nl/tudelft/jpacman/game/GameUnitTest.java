package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Use mockito mocking framework to unit test game.Game.
 * Created by basjenneboer on 5/24/17.
 */
@SuppressWarnings({"PMD.TooManyStaticImports", "PMD.TestClassWithoutTestCases"})
class GameUnitTest {
    private Game game;
    private Player mockPlayer;
    private Level mockLevel;

    /**
     * Create a game by using mocks for player and level.
     */
    @BeforeEach
    void initialize() {
        mockPlayer = mock(Player.class);
        mockLevel = mock(Level.class);

        game = new SinglePlayerGame(mockPlayer, mockLevel);
    }

    /**
     * Test whether game resumes the level,
     * when there are pellets left and the player is alive.
     */
    @Test
    void testPlayerAliveAndPelletsRemaining() {
        when(mockLevel.isAnyPlayerAlive()).thenReturn(true);
        //Check:OFF: MagicNumber
        when(mockLevel.remainingPellets()).thenReturn(5);
        //Check:ON: MagicNumber
        game.start();

        assertThat(game.isInProgress()).isTrue();
        verify(mockLevel).start();
    }

    /**
     * Test whether the level is no longer resumed,
     * when there are no pellets left.
     */
    @Test
    void testPlayerAliveAndNoPelletsRemaining() {
        when(mockLevel.isAnyPlayerAlive()).thenReturn(true);
        when(mockLevel.remainingPellets()).thenReturn(0);

        game.start();

        assertThat(game.isInProgress()).isFalse();
        verify(mockLevel, never()).start();
    }

    /**
     * Test whether the level is no longer resumed,
     * when the player is dead.
     */
    @Test
    void testPlayerDeadAndPelletsRemaining() {
        when(mockLevel.isAnyPlayerAlive()).thenReturn(false);
        //Check:OFF: MagicNumber
        when(mockLevel.remainingPellets()).thenReturn(5);
        //Check:ON: MagicNumber

        game.start();

        assertThat(game.isInProgress()).isFalse();
        verify(mockLevel, never()).start();
    }

    /**
     * Check that Game.start() returns directly,
     * when it was already started before.
     */
    @Test
    void testGameAlreadyInProgress() {
        when(mockLevel.isAnyPlayerAlive()).thenReturn(true);
        //Check:OFF: MagicNumber
        when(mockLevel.remainingPellets()).thenReturn(5);
        //Check:ON: MagicNumber
        game.start();

        game.start();
        //check that Level.start() is not invoked a second time
        verify(mockLevel, times(1)).start();
    }
}
