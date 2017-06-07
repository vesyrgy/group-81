package nl.tudelft.jpacman.level;

/**
 * Created by basjenneboer on 6/6/17.
 */
public class PlayerCollisionsTest extends CollisionMapTest {

    CollisionMap createMap() {
        return new PlayerCollisions();
    }
}
