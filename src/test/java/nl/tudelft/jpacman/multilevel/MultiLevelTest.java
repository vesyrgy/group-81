package nl.tudelft.jpacman.multilevel;

import nl.tudelft.jpacman.group81.MultiLevelLauncher;
import nl.tudelft.jpacman.group81.multilevel.MultiLevelGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *  @author Lars Ysla.
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class MultiLevelTest {

    private MultiLevelGame mLGame;
    private MultiLevelLauncher mLLauncher;
    //private Level currentLevel;

    /**
     *  Stuff to do before each test, like launching the GUI and starting the game.
     */
    @BeforeEach
    void setUp() {
        mLLauncher = new MultiLevelLauncher();
        mLLauncher.launch();
        mLGame = mLLauncher.getGame();
        mLGame.start();
    }

    /**
     *  Multilevel adaptation of Conformance Test 1.
     */
    @Test
    void multilevelTestCaseT1() {

        //  get the current level
        //currentLevel = mLGame.getLevel();

        //  win the level
        mLGame.levelWon();

        //  verify that the next level has started


    }

    /**
     *  Adapted test case T2.
     */
    @Test
    void multilevelTestCaseT2() {
        //  start the game with a map where Pacman will lose immediately

        //  lose the level
        mLGame.levelLost();

        //  verify that the game has been lost


    }

    /**
     *  Adapted test case T3.
     */
    @Test
    void multilevelTestCaseT3() {

        //  start the game

        //  pause the level

        //  resume the level

        //  verify that the level has been resumed and not restarted

    }

    /**
     *  Conformance test T4.
     */
    @Test
    void multilevelTestCaseT4() {

        //  start the game

        //  win the level

        //  verify that the game has been won

    }


}
