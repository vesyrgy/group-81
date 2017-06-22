package nl.tudelft.jpacman.group81.multilevel;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import java.util.List;

/**
 *  @author Lars Ysla
 */
public class MultiLevelGame extends Game {
    private final List<Player> players;
    private final List<Level> levels;
    private int levelNumber;
    /**
     * Construct MultiLevelGame.
     * @param players
     *          The players.
     * @param levels
     *          The levels.
     */
    public MultiLevelGame(List<Player> players, List<Level> levels) {
        this.levels = levels;
        this.players = players;
        this.levelNumber = 0;
        getLevel().registerPlayer(players.get(levelNumber));
    }

    /**
     * @return list of players.
     */
    public List<Player> getPlayers() {
        return ImmutableList.of(players.get(levelNumber));
    }

    /**
     * @return current level.
     */
    public Level getLevel() {
        return levels.get(levelNumber);
    }

    /**
     * Win levels.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Override
    public void levelWon() {
        if (levelNumber < 3) {
            levelNumber++;
            getLevel().registerPlayer(players.get(levelNumber));
            getLevel().addObserver(this);
            getLevel().start();
            return;
        }
        stop();
    }
}

