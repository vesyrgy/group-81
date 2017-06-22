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
public class MultiLevelGameTest extends GameTest {

    /**
     * Provides GameTest with a MultiLevelLauncher instance.
     * @return a new MultiLevelLauncher.
     */
    public Launcher getLauncher() {
        return new MultiLevelLauncher();
    }

    private void verifyLevelsWon(int levelsWon) {
        assertThat(getGame().isInProgress()).isTrue();
        verify(getObserver(), times(levelsWon)).levelWon();
        verify(getObserver(), times(0)).levelLost();
    }

    /**
     * Tests if a new level is started when the previous is won.
     */
    @Test
    void testT1MultiLevel() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();

        getGame().start();
        verifyPlaying();
        verifyLevelsWon(0);

        getLevel().move(getGame().getPlayers().get(0), Direction.EAST);
        verifyPlaying();
        verifyLevelsWon(1);
    }

    /**
     * Tests if the game is won if four levels are won.
     */
    @Test
    void testT4MultiLevel() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();

        getGame().start();
        verifyPlaying();

        //CHECK:OFF: MagicNumber
        for (int wonLevels = 0; wonLevels < 3; wonLevels++) {
            getGame().getLevel().move(getGame().getPlayers().get(0), Direction.EAST);
            getGame().getLevel().addObserver(getObserver());
        }
        verifyLevelsWon(3);
        //CHECK:ON: MagicNumber

        getGame().getLevel().move(getGame().getPlayers().get(0), Direction.EAST);
        verifyWon();
    }
}
