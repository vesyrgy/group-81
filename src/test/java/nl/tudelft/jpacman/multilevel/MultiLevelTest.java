package nl.tudelft.jpacman.multilevel;

import nl.tudelft.jpacman.group81.MultiLevelLauncher;
import nl.tudelft.jpacman.group81.multilevel.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *  @author Lars Ysla.
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class MultiLevelTest {

    private MultiLevelGame ml_game;
    private MultiLevelLauncher ml_launcher;
    private Level current_level;

    /**
     *  Stuff to do before each test, like launching the GUI and starting the game.
     */
    @BeforeEach
    void setUp() {
        ml_launcher = new MultiLevelLauncher();
        ml_launcher.launch();
        ml_game = ml_launcher.getGame();
        ml_game.start();
    }

    /**
     *  Multilevel adaptation of Conformance Test 1.
     */
    @Test
    void multilevelTestCaseT1() {

        //  get the current level
        current_level = ml_game.getLevel();

        //  win the level
        ml_game.levelWon();

        //  verify that the next level has started


    }

    @Test
    void multilevelTestCaseT2() {
        //  start the game with a map where Pacman will lose immediately

        //  lose the level
        ml_game.levelLost();

        //  verify that the game has been lost


    }

    @Test
    void multilevelTestCaseT3() {

        //  start the game

        //  pause the level

        //  resume the level

        //  verify that the level has been resumed and not restarted

    }

    @Test
    void multilevelTestCaseT4() {

        //  start the game

        //  win the level

        //  verify that the game has been won

    }


}
