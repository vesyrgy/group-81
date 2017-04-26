package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 *  Test a basic board
 *  @author Lars Ysla
 */
class BoardTest {

    
    //	Check that a simple 1x1 board is valid
    @Test
    void testBasicSquare() {
	
	BasicSquare bs = new BasicSquare();
	
	Square[][] grid = {
	    { bs }
	};
	
	Board board = new Board(grid);
	
	assertTrue(board.invariant());
    }
    
    //	Check to see if the square returned by the 1x1 board is the same square 
    //	that was used to create the board.
    @Test
    void testSquareAt1() {
	BasicSquare bs = new BasicSquare();
	
	Square[][] grid = {
	    { bs }
	};
	
	Board board = new Board(grid);
	
	assertThat(board.squareAt(0, 0)).isEqualTo(bs);
    }
    
}
