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
public abstract class GameTest {
    public Launcher launcher;
    public Game game;
    public Level level;
    public Level.LevelObserver levelObserverMock;

    public abstract Launcher getLauncher();

    public void startGUI(String mapFile) {
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

    public void verifyGUIStarted() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, never()).levelLost();
        verify(levelObserverMock, never()).levelWon();
    }

    public void verifyPlaying() {
        assertThat(game.isInProgress());
    }

    public void verifyPaused() {
        assertThat(game.isInProgress()).isFalse();
    }

    public void verifyWon() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, atLeastOnce()).levelWon();
        verify(levelObserverMock, never()).levelLost();
    }

    public void verifyLost() {
        assertThat(level.isInProgress()).isFalse();
        verify(levelObserverMock).levelLost();
    }

    @Test
    void testT2Conformance() {
        startGUI("/testMap1.txt");
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        level.move(game.getPlayers().get(0), Direction.SOUTH);
        verifyLost();
    }

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

    @Test
    void testSneakPathGUIStartedStop() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();

        game.stop();
        verifyGUIStarted();
    }

    @Test
    void testSneakPathGUIStartedWin() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();
        game.move(game.getPlayers().get(0), Direction.EAST);
        verifyGUIStarted();
    }

    @Test
    void testSneakPathGUIStartedLoose() {
        startGUI("/testMap3.txt");
        verifyGUIStarted();

        game.move(game.getPlayers().get(0), Direction.SOUTH);
        verifyGUIStarted();
    }

    @Test
    void testSneakPathPlayingStart() {
        startGUI("/testMap2.txt");
        game.start();
        verifyPlaying();

        game.start();
        verifyPlaying();
    }

    @Test
    void testSneakPathPausedStop() {
        startGUI("/testMap2.txt");
        game.start();
        game.stop();
        verifyPaused();

        game.stop();
        verifyPaused();
    }

    @Test
    void testSneakPathPausedWin() {
        startGUI("/testMap2.txt");
        game.start();
        game.stop();
        verifyPaused();

        game.move(game.getPlayers().get(0), Direction.EAST);
        verifyPaused();
    }

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
