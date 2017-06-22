package nl.tudelft.jpacman.group81;

import nl.tudelft.jpacman.group81.multilevel.MultiLevelGame;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/**
 *  @author Lars Ysla
 */
public class MultiLevelLauncher extends MyExtension {

    private MultiLevelGame multiGame;

    /**
     * Start a Multi-Level version of Pacman
     *
     * @param argv Ignored
     */
    public static void main(String[] argv) {
        (new MultiLevelLauncher()).launch();
    }

    /**
     * Creates a new game using the level from {@link #makeLevel()}.
     *
     * @return a new Game.
     */
    @EnsuresNonNull("game")
    @Override
    public MultiLevelGame makeGame() {
        Level level = makeLevel();
        multiGame = new MultiLevelGame(getPlayerFactory().createPacMan(), level);

        return multiGame;
    }

    @Override
    public MultiLevelGame getGame() {
        return multiGame;
    }
}
