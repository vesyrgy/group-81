package nl.tudelft.jpacman.e2e.group81.multilevel;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.GameTest;
import nl.tudelft.jpacman.group81.MultiLevelLauncher;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Provides GameTest with a MultiLevelLauncher instance.
 * Created by basjenneboer on 6/21/17.
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class MultiLevelGameTest extends GameTest {

    /**
     * Provides GameTest with a MultiLevelLauncher instance.
     * @return a new MultiLevelLauncher.
     */
    public Launcher getLauncher() {
        return new MultiLevelLauncher();
    }

    void verifyLevelsWon(int levelsWon) {
        assertThat(game.isInProgress()).isTrue();
        verify(levelObserverMock, times(levelsWon)).levelWon();
        verify(levelObserverMock, times(0)).levelLost();
    }

    @Test
    void testT1MultiLevel() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();

        game.start();
        verifyPlaying();
        verifyLevelsWon(0);

        game.getLevel().move(game.getPlayers().get(0), Direction.EAST);
        verifyPlaying();
        verifyLevelsWon(1);
    }

    @Test
    void testT4MultiLevel() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();

        game.start();
        verifyPlaying();

        for (int wonLevels = 0; wonLevels < 3; wonLevels++) {
            game.getLevel().move(game.getPlayers().get(0), Direction.EAST);
            game.getLevel().addObserver(levelObserverMock);
        }
        verifyLevelsWon(3);

        game.getLevel().move(game.getPlayers().get(0), Direction.EAST);
        verifyWon();
    }
}
