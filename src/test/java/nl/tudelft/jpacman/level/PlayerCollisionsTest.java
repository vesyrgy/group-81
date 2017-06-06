package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.npc.ghost.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by basjenneboer on 6/6/17.
 */
public class PlayerCollisionsTest {
    PlayerCollisions map;
    Player playerMock;
    Pellet pelletMock;
    Ghost ghostMock;

    @BeforeEach
    void setup() {
        map = new PlayerCollisions();
        playerMock = mock(Player.class);
        pelletMock = mock(Pellet.class);
        ghostMock = mock(Ghost.class);
    }

    @Test
    void playerMovesOnGhostAndPellet() {
        map.collide(playerMock, ghostMock);
        map.collide(playerMock, pelletMock);

        verify(playerMock).addPoints(anyInt());
        verify(playerMock).setAlive(false);
        verify(pelletMock).leaveSquare();
    }

    @Test
    void playerMovesOnPelletAndGhost() {
        map.collide(playerMock, pelletMock);
        map.collide(playerMock, ghostMock);

        verify(playerMock).addPoints(anyInt());
        verify(playerMock).setAlive(false);
        verify(pelletMock).leaveSquare();
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

    @Test
    void pelletMovesOnGhost() {
        map.collide(pelletMock, ghostMock);

        verify(pelletMock, times(0)).leaveSquare();
        verify(ghostMock, times(0)).leaveSquare();
    }

    @Test
    void pelletMovesOnPlayer() {
        map.collide(pelletMock, playerMock);

        verify(playerMock).addPoints(anyInt());
        verify(playerMock, times(0)).setAlive(false);
        verify(pelletMock).leaveSquare();
    }

    @Test
    void ghostMovesOnGhost() {
        Ghost otherGhostMock = mock(Ghost.class);

        map.collide(ghostMock, otherGhostMock);

        verify(ghostMock, times(0)).leaveSquare();
        verify(otherGhostMock, times(0)).leaveSquare();
    }

    @Test
    void playerMovesOnNull() {
        map.collide(playerMock, null);

        verify(playerMock, times(0)).addPoints(anyInt());
        verify(playerMock, times(0)).setAlive(false);
    }

    @Test
    void nullMovesOnPlayer() {
        map.collide(null, playerMock);

        verify(playerMock, times(0)).addPoints(anyInt());
        verify(playerMock, times(0)).setAlive(false);
    }
}
