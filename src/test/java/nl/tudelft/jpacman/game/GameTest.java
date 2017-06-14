package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.group81.MyExtension;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.Test;
import sun.launcher.resources.launcher;

/**
 * Created by basjenneboer on 6/14/17.
 */
public class GameTest {
    Launcher launcher;
    Game game;
    Level level;
    Level.LevelObserver levelObserverMock;


    void startGUI(String mapFile) {
        launcher = new MyExtension();
        if (mapFile != null) {
            launcher = launcher.withMapFile(mapFile);
        }
        launcher.launch();
        game = launcher.getGame();
        level = game.getLevel();
        levelObserverMock = mock(Level.LevelObserver.class);
        level.addObserver(levelObserverMock);
    }

    void verifyGUIStarted() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    void verifyPlaying() {
        assertThat(game.isInProgress());
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    void verifyPaused() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock, times(0)).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    void verifyWon() {
        assertThat(game.isInProgress()).isFalse();
        verify(levelObserverMock).levelWon();
        verify(levelObserverMock, times(0)).levelLost();
    }

    void verifyLost() {
        assertThat(level.isInProgress()).isFalse();
        verify(levelObserverMock).levelLost();
        verify(levelObserverMock, times(0)).levelWon();
    }

    @Test
    void testT1Conformance() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        level.move(game.getPlayers().get(0), Direction.EAST);
        verifyWon();
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
}
