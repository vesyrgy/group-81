package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.npc.ghost.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by basjenneboer on 6/7/17.
 */
public abstract class CollisionMapTest {
    CollisionMap map;
    Player playerMock;
    Pellet pelletMock;
    Ghost ghostMock;

    abstract CollisionMap createMap();

    @BeforeEach
    void setup() {
        map = createMap();
        playerMock = mock(Player.class);
        pelletMock = mock(Pellet.class);
        ghostMock = mock(Ghost.class);
    }

    @Test
    void playerMovesOnGhost() {
        map.collide(playerMock, ghostMock);

        verify(playerMock).setAlive(false);
    }

    @Test
    void playerMovesOnPellet() {
        map.collide(playerMock, pelletMock);

        verify(playerMock).addPoints(anyInt());
        verify(playerMock, times(0)).setAlive(false);
        verify(pelletMock).leaveSquare();
    }

    @Test
    void ghostMovesOnPlayer() {
        map.collide(ghostMock, playerMock);

        verify(playerMock).setAlive(false);
    }

    @Test
    void ghostMovesOnPellet() {
        map.collide(ghostMock, pelletMock);

        verify(pelletMock, times(0)).leaveSquare();
        verify(ghostMock, times(0)).leaveSquare();
    }
}
