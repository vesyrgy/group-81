package nl.tudelft.jpacman.e2e.group81.multilevel;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.GameTest;
import nl.tudelft.jpacman.group81.MultiLevelLauncher;

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
}
