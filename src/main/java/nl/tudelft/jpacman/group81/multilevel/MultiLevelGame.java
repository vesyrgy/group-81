package nl.tudelft.jpacman.group81.multilevel;

import nl.tudelft.jpacman.game.SinglePlayerGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;

/**
 *  @author Lars Ysla
 */
public class MultiLevelGame extends SinglePlayerGame {
    /**
     * Construct MultiLevelGame.
     * @param player
     *          The player.
     * @param level
     *          The level.
     */
    public MultiLevelGame(Player player, Level level) {
        super(player, level);
    }
}
