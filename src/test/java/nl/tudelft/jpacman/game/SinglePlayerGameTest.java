package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.group81.MyExtension;
import org.junit.jupiter.api.Test;

/**
 * {@inheritDoc}
 * Created by basjenneboer on 6/21/17.
 */
public class SinglePlayerGameTest extends GameTest {

    /**
     * {@inheritDoc}
     * @return a Launcher.
     */
    public Launcher getLauncher() {
        return new MyExtension();
    }

    /**
     * Tests path: start, win.
     */
    @Test
    void testT1Conformance() {
        startGUI("/testMap2.txt");
        verifyGUIStarted();
        getGame().start();
        verifyPlaying();
        getLevel().move(getGame().getPlayers().get(0), Direction.EAST);
        verifyWon();
    }
}
