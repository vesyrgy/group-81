package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.group81.MyExtension;
import org.junit.jupiter.api.Test;

/**
 * Created by basjenneboer on 6/21/17.
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class SinglePlayerGameTest extends GameTest {

    /**
     * @return a launcher for a single-level game.
     */
    public Launcher getLauncher() {
        return new MyExtension();
    }

    @Test
    void testT1Conformance() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();
        game.start();
        verifyPlaying();
        getLevel().move(game.getPlayers().get(0), Direction.EAST);
        verifyWon();
    }
}
