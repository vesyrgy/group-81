package nl.tudelft.jpacman.level;

/**
 * Class that extends the CollisionMap tests
 * in order to run the tests on a PlayerCollisions map.
 * Created by basjenneboer on 6/6/17.
 */
class PlayerCollisionsTest extends CollisionMapTest {

    /**
     * Implements the createMap() signature of CollisionMapTest.
     * @return PlayerCollisions instance of CollisionMap.
     */
    CollisionMap createMap() {
        return new PlayerCollisions();
    }
}
