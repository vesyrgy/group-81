package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 *  Test a basic board
 *  @author Lars Ysla
 */
class BoardTest {

    
    //	check that a simple 1x1 board is valid
    @Test
    void testBasicSquare() {
	
	Square[][] basicSquare = {
	    { mock(Square.class) },
	    { mock(Square.class) },
	};
	
	Board board = new Board(basicSquare);
	
	assertTrue(board.invariant());
    }
    
}
