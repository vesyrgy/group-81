package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.group81.MyExtension;
import org.junit.jupiter.api.Test;

/**
 * Created by basjenneboer on 6/21/17.
 */
public class SinglePlayerGameTest extends GameTest {

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
