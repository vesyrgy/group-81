package nl.tudelft.jpacman.group81;

import nl.tudelft.jpacman.group81.multilevel.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

import java.util.ArrayList;

/**
 *  @author Lars Ysla
 */
public class MultiLevelLauncher extends MyExtension {

    private MultiLevelGame multiGame;

    /**
     * Creates a new game using the level from {@link #makeLevel()}.
     *
     * @return a new Game.
     */
    @EnsuresNonNull("game")
    @Override
    public MultiLevelGame makeGame() {
        ArrayList<Level> levels = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        //Check:OFF: MagicNumber
        for (int i = 0; i < 4; i++) {
            levels.add(makeLevel());
            players.add(getPlayerFactory().createPacMan());
        }
        //Check:ON: MagicNumber
        multiGame = new MultiLevelGame(players, levels);
        return multiGame;
    }

    @Override
    public MultiLevelGame getGame() {
        return multiGame;
    }
}
