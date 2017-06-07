package nl.tudelft.jpacman.level;

/**
 * Class that extends the CollisionMap tests
 * in order to run the tests on a DefaultPlayerInteractionMap map.
 * Created by basjenneboer on 6/6/17.
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    /**
     * Implements the createMap() signature of CollisionMapTest.
     * @return DefaultPlayerInteractionMap instance of CollisionMap.
     */
    CollisionMap createMap() {
        return new DefaultPlayerInteractionMap();
    }
}
