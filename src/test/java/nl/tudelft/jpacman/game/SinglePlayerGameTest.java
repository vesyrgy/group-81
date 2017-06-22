package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.group81.MyExtension;

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
}
