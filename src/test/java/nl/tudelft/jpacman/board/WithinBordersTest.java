package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.annotations.ParameterizedAssignment;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Lars Ysla
 */

/**
 * A marker annotation for the auto-grader to identify a boundary testing solution.
 *
 * A Students Solution class for the boundary testing exercise should have the following:
 *
 * <pre class="code"><code class="java">
 * &#064;ParameterizedAssignment
 * public class WithinBordersTest {
 *     //tests
 * }
 * </code></pre>
 */


@ParameterizedAssignment
public class WithinBordersTest {

    Square square = new BasicSquare();

    private final Square[][] grid = {
            { square, square, square, square, square, square },
            { square, square, square, square, square, square },
            { square, square, square, square, square, square },
            { square, square, square, square, square, square }
    };


    private final Board board = new Board(grid);

    //  The values for the parameterized test have been hard-coded from the division calculations in the domain matrix.
    //  For example, height/2 has been hard-coded as 3.
    @ParameterizedTest
    @CsvSource({
            "0, 3, true",
            "-1, 0, false",
            "4, 2, false",
            "3, 0, true",
            "1, 0, true",
            "0, -1, false",
            "0, 6, false",
            "2, 5, true"
    })
    void testWithinBorders(int x, int y, boolean z) {
        assertThat(board.withinBorders(x,y)).isEqualTo(z);

    }

}
