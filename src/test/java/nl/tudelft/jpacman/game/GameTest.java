package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.Test;

/**
 * Created by basjenneboer on 6/14/17.
 */
@SuppressWarnings("PMD.TooManyMethods")
public abstract class GameTest {
    private Launcher launcher;
    private Game game;
    private Level level;
    private Level.LevelObserver levelObserverMock;

    /**
     * @return the relevant launcher.
     */
    public abstract Launcher getLauncher();

    /**
     * Start the GUI.
     * @param mapFile
     */
    void startGUI(String mapFile) {
        launcher = getLauncher();
        if (mapFile != null) {
            launcher = launcher.withMapFile(mapFile);
        }
        launcher.launch();
        game = launcher.getGame();
        level = game.getLevel();
        levelObserverMock = mock(Level.LevelObserver.class);
        level.addObserver(levelObserverMock);
    }

    /**
     *  Check that the GUI has started.
     */
    void verifyGUIStarted() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    /**
     *  Make sure the game is in the playing state.
     */
    void verifyPlaying() {
        assertThat(game.isInProgress());
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    /**
     *  Check if the game is paused.
     */
    void verifyPaused() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    /**
     *  Test if the level has been won.
     */
    void verifyWon() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock).levelWon();
        verify(levelObserverMock, times(0)).levelLost();
    }

    /**
     *  Test if the level has been lost.
     */
    void verifyLost() {
        assertThat(level.isInProgress()).isFalse();
        verify(levelObserverMock).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    /**
     *  Conformance test T1.
     */
    @Test
    void testT1Conformance() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        level.move(game.getPlayers().get(0), Direction.EAST);
        verifyWon();
    }

    /**
     *  Conformance test T2.
     */
    @Test
    void testT2Conformance() {
        startGUI("/testMap1.txt");
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        level.move(game.getPlayers().get(0), Direction.SOUTH);
        verifyLost();
    }

    /**
     *  Conformance test T3.
     */
    @Test
    void testT3Conformance() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        game.stop();
        verifyPaused();
        game.start();
        verifyPlaying();

        assertThat(level.isInProgress()).isTrue();
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    /**
     *  A sneak path test.
     */
    @Test
    void testSneakPathGUIStartedStop() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();

        game.stop();
        verifyGUIStarted();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathGUIStartedWin() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();
        game.move(game.getPlayers().get(0), Direction.EAST);
        verifyGUIStarted();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathGUIStartedLoose() {
        startGUI("/testMap3.txt");
        verifyGUIStarted();

        game.move(game.getPlayers().get(0), Direction.SOUTH);
        verifyGUIStarted();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathPlayingStart() {
        startGUI("/testMap2.txt");
        game.start();
        verifyPlaying();

        game.start();
        verifyPlaying();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathPausedStop() {
        startGUI("/testMap2.txt");
        game.start();
        game.stop();
        verifyPaused();

        game.stop();
        verifyPaused();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathPausedWin() {
        startGUI("/testMap2.txt");
        game.start();
        game.stop();
        verifyPaused();

        game.move(game.getPlayers().get(0), Direction.EAST);
        verifyPaused();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathPausedLoose() {
        startGUI("/testMap3.txt");
        game.start();
        game.stop();
        verifyPaused();

        game.move(game.getPlayers().get(0), Direction.SOUTH);
        verifyPaused();
    }
}
