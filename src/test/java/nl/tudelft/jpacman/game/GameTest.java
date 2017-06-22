package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
    private String mapfile1 = "/testMap1.txt";
    private String mapfile2 = "/testMap2.txt";
    private String mapfile3 = "/testMap3.txt";

    /**
     * @return the relevant launcher.
     */
    public abstract Launcher getLauncher();

    /**
     * @param mapFile
     * Start the GUI.
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
        verify(levelObserverMock, never()).levelLost();
        verify(levelObserverMock, never()).levelWon();
    }


    /**
     *  Make sure the game is in the playing state.
     */
    void verifyPlaying() {
        assertThat(game.isInProgress()).isTrue();
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    /**
     *  Check if the game is paused.
     */
    void verifyPaused() {

        assertThat(game.isInProgress()).isFalse();
    }


    /**
     *  Test if the level has been won.
     */
    void verifyWon() {

        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, atLeastOnce()).levelWon();
        verify(levelObserverMock, never()).levelLost();
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
        startGUI(mapfile2);
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
        startGUI(mapfile1);
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        game.getLevel().move(game.getPlayers().get(0), Direction.SOUTH);
        verifyLost();
    }

    /**
     *  Conformance test T3.
     */
    @Test
    void testT3Conformance() {
        startGUI(mapfile2);
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
     *  A sneak path test.
     */
    @Test
    void testSneakPathGUIStartedStop() {
        startGUI(mapfile2);
        verifyGUIStarted();

        game.stop();
        verifyGUIStarted();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathGUIStartedWin() {
        startGUI(mapfile2);
        verifyGUIStarted();
        game.move(game.getPlayers().get(0), Direction.EAST);
        verifyGUIStarted();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathGUIStartedLoose() {
        startGUI(mapfile3);
        verifyGUIStarted();

        game.move(game.getPlayers().get(0), Direction.SOUTH);
        verifyGUIStarted();
    }

    /**
     *  Another sneak path test.
     */
    @Test
    void testSneakPathPlayingStart() {
        startGUI(mapfile2);
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
        startGUI(mapfile2);
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
        startGUI(mapfile2);
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
        startGUI(mapfile3);
        game.start();
        game.stop();
        verifyPaused();

        game.move(game.getPlayers().get(0), Direction.SOUTH);
        verifyPaused();
    }
}
