package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;


import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.Test;

/**
 * Tests the state transitions.
 * Created by basjenneboer on 6/14/17.
 */
@SuppressWarnings({"PMD.TooManyStaticImports", "PMD.TooManyMethods"})
public abstract class GameTest {
    private Game game;
    private Level.LevelObserver levelObserverMock;
    private static String ghostLessMap = "/testMap1.txt";

    /**
     * returns the current level.
     * @return The level
     */
    public Level getLevel() {
        return game.getLevel();
    }

    /**
     * returns the LevelObserver mock.
     * @return The LevelObser mock.
     */
    protected Level.LevelObserver getObserver() {
        return levelObserverMock;
    }

    /**
     * returns the game.
     * @return The game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Should be implemented by the different
     * game types that should be tested.
      * @return a Launcher.
     */
    public abstract Launcher getLauncher();

    /**
     * Starts a launcher with the given map file
     * to enter the GUI Started state.
     * @param mapFile custom map if not null.
     */
    protected void startGUI(String mapFile) {
        Launcher launcher = getLauncher();
        if (mapFile != null) {
            launcher = launcher.withMapFile(mapFile);
        }
        launcher.launch();
        game = launcher.getGame();
        levelObserverMock = mock(Level.LevelObserver.class);
        getLevel().addObserver(levelObserverMock);
    }

    /**
     * Checks if in GUI Started state.
     */
    protected void verifyGUIStarted() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, never()).levelLost();
        verify(levelObserverMock, never()).levelWon();
    }

    /**
     * Checks if in Playing state.
     */
    protected void verifyPlaying() {
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * Checks if in Paused state.
     */
    private void verifyPaused() {
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Checks if in Game Won state.
     */
    protected void verifyWon() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, atLeastOnce()).levelWon();
        verify(levelObserverMock, never()).levelLost();
    }

    /**
     * Checks if in Game Lost state.
     */
    private void verifyLost() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock).levelLost();
    }

    /**
     * Tests path: start, loose.
     */
    @Test
    void testT2Conformance() {
        startGUI("/testMap1.txt");
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        getLevel().move(game.getPlayers().get(0), Direction.SOUTH);
        verifyLost();
    }

    /**
     * Tests path: start, stop, start.
     */
    @Test
    void testT3Conformance() {
        startGUI(ghostLessMap);
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        game.stop();
        verifyPaused();
        game.start();
        verifyPlaying();

        assertThat(game.isInProgress()).isTrue();
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    /**
     * Tests sneaky path: Stop when not playing yet.
     */
    @Test
    void testSneakPathGUIStartedStop() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();

        game.stop();
        verifyGUIStarted();
    }

    /**
     * Tests sneaky path: Do a winning move when not playing yet.
     */
    @Test
    void testSneakPathGUIStartedWin() {
        startGUI(ghostLessMap);
        verifyGUIStarted();
        game.move(game.getPlayers().get(0), Direction.EAST);
        verifyGUIStarted();
    }

    /**
     * Tests sneaky path: Do a losing move when not playing yet.
     */
    @Test
    void testSneakPathGUIStartedLoose() {
        startGUI("/testMap3.txt");
        verifyGUIStarted();

        game.move(game.getPlayers().get(0), Direction.SOUTH);
        verifyGUIStarted();
    }

    /**
     * Tests sneaky path: Starting when already playing.
     */
    @Test
    void testSneakPathPlayingStart() {
        startGUI(ghostLessMap);
        game.start();
        verifyPlaying();

        game.start();
        verifyPlaying();
    }

    /**
     * Tests sneaky path: Stopping when already paused.
     */
    @Test
    void testSneakPathPausedStop() {
        startGUI(ghostLessMap);
        game.start();
        game.stop();
        verifyPaused();

        game.stop();
        verifyPaused();
    }

    /**
     * Tests sneaky path: Make a winning move when paused.
     */
    @Test
    void testSneakPathPausedWin() {
        startGUI(ghostLessMap);
        game.start();
        game.stop();
        verifyPaused();

        game.move(game.getPlayers().get(0), Direction.EAST);
        verifyPaused();
    }

    /**
     * Tests sneaky path; Do a losing move when paused.
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
