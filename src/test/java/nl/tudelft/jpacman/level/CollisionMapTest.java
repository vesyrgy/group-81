package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.ghost.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Class that tests whether all possible collisions
 * give the required result.
 * Created by basjenneboer on 6/7/17.
 */
abstract class CollisionMapTest {
    private CollisionMap map;
    private Player playerMock;
    private Pellet pelletMock;
    private Ghost ghostMock;

    /**
     * Every implementation of CollisionMapTest should
     * provide an instance of their implementation of CollisionMap.
     * @return Instance of CollisionMap
     */
    abstract CollisionMap createMap();

    /**
     * Acquire a CollisionMap instance and mock all
     * collision participants.
     */
    @BeforeEach
    void init() {
        map = createMap();
        playerMock = mock(Player.class);
        pelletMock = mock(Pellet.class);
        ghostMock = mock(Ghost.class);
    }
    /**
     * When a player moves on a ghost, the player
     * should be dead.
     */
    @Test
    void playerMovesOnGhost() {
        map.collide(playerMock, ghostMock);

        verify(playerMock).setAlive(false);
    }

    /**
     * When a player moves on a pellet, the player
     * should earn points, should still be alive and
     * the pellet should disappear.
     */
    @Test
    void playerMovesOnPellet() {
        map.collide(playerMock, pelletMock);

        verify(playerMock).addPoints(anyInt());
        verify(playerMock, times(0)).setAlive(false);
        verify(pelletMock).leaveSquare();
    }

    /**
     * When a ghost moves on a player, the player
     * should be dead.
     */
    @Test
    void ghostMovesOnPlayer() {
        map.collide(ghostMock, playerMock);

        verify(playerMock).setAlive(false);
    }

    /**
     * When a ghost moves on a pellet, the ghost
     * and pellet should remain on the board.
     */
    @Test
    void ghostMovesOnPellet() {
        map.collide(ghostMock, pelletMock);

        verify(pelletMock, times(0)).leaveSquare();
        verify(ghostMock, times(0)).leaveSquare();
    }
}
