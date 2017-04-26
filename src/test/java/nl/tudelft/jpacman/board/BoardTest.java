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
	
	Square[][] basicSquare = {
	    { mock(Square.class) },
	    { mock(Square.class) },
	};
	
	Board board = new Board(basicSquare);
	
	assertTrue(board.invariant());
    }
    
    //	Check to see if the square returned by the 1x1 board is the same square 
    //	that was used to create the board.
    @Test
    void testSquareAt1() {
	Square square = mock(Square.class);
	
	Square[][] basicSquare = {
	    { square },
	    { square },
	};
	
	Board board = new Board(basicSquare);
	
	assertThat(board.squareAt(0, 0)).isEqualTo(square);
    }
    
    @Test
    void testSquareAtNull() {
	Square square = null;
	
	Square[][] basicSquare = {
	    { square },
	    { square },
	};
	
	Board board = new Board(basicSquare);
	
	assertThat(board.squareAt(0, 0)).isEqualTo(square);
    
    }
}
