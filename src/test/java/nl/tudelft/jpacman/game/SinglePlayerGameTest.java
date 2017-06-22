package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.group81.MyExtension;

/**
 * Created by basjenneboer on 6/21/17.
 */
public class SinglePlayerGameTest extends GameTest {

    public Launcher getLauncher() {
        return new MyExtension();
    }
}
