package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Lars Ysla
 */
//@RunWith(MockitoJUnitRunner.class)
class MapParserTest {
    private LevelFactory lf = mock(LevelFactory.class);
    private BoardFactory bf = mock(BoardFactory.class);
    private Level lev = mock(Level.class);
    private NPC npc = mock(NPC.class);
    private Square sq = mock(Square.class);
    private Board bd = mock(Board.class);
    private Pellet pel = mock(Pellet.class);
    private List<NPC> gh = new ArrayList<>();
    private List<Square> sp = new ArrayList<>();
    private MapParser mp;
    private Square[][] gr;

    @Mock private LevelFactory levelCreator;
    @Mock private BoardFactory boardCreator;
    @InjectMocks private MapParser mpm;

    /**
     *  Set up the mock objects before each test.
     */
    @SuppressWarnings({"unchecked", "checkstyle:linelength"})
    @BeforeEach
    void setupMocks() {
        MockitoAnnotations.initMocks(this);

        //  Return mocked Squares from the mocked BoardFactory
        when(bf.createGround()).thenReturn(sq);
        when(bf.createWall()).thenReturn(sq);

        //  Return a mocked Level from the mocked LevelFactory
        when(lf.createLevel(any(Board.class), any(gh.getClass()), any(sp.getClass()))).thenReturn(lev);
        when(lf.createPellet()).thenReturn(pel);

        //  Do nothing when occupy() is called on a mocked Pellet
        doNothing().when(pel).occupy(any(Square.class));

        //  Return a mocked ghost from the mocked LevelFactory
        when(lf.createGhost()).thenReturn(npc);

        //  Return a mocked square from the mocked Ghost
        when(npc.getSquare()).thenReturn(sq);

        mp = Mockito.spy(new MapParser(lf, bf));
        gr = new Square[1][1];
        gr[0][0] = sq;

        mpm = spy(new MapParser(lf, bf));

        //  Return a mocked Board from the mocked BoardFactory
        when(bf.createBoard(any(gr.getClass()))).thenReturn(bd);
    }

    /**
     *  Test the MapParser constructor (nice weather).
     */
    @Test
    void testConstructor1() {
        mpm = spy(new MapParser(lf, bf));
        assertThat(levelCreator).isInstanceOf(lf.getClass());
        assertThat(boardCreator).isInstanceOf(bf.getClass());
    }

    /**
     *  Test the parseMap method for a character array (nice weather).
     */
    @SuppressWarnings({"unchecked", "checkstyle:linelength"})
    @Test

    void testParseMapFromCharArray() {
        //  Spy on the method calls within MapParser object
        MapParser mp = Mockito.spy(new MapParser(lf, bf));

        //  Define a trivial map
        char [][] map = new char[1][1];
        map[0][0] = 'P';

        Level level = mp.parseMap(map);
        //  Check that the addSquare method gets called
        Mockito.verify(mp).addSquare(any(gr.getClass()), any(gh.getClass()), any(sp.getClass()), anyInt(), anyInt(), anyChar());

        assertThat(level).isEqualTo(lev);
    }

    /**
     *  Test the parseMap method for a character array (bad weather).
     *  Bad character
     */
    @Test
    void testParseMapFromCharArrayBadChar() {
        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            //  Define a trivial map
            char [][] map = new char[1][1];
            map[0][0] = 'Q';

            mp.parseMap(map);

        });
        assertEquals("Invalid character at 0,0: Q", ex.getMessage());

    }

    /**
     *  Test the parseMap method for a character array (bad weather).
     *  Empty Map
     */
    @Test
    void testParseMapFromCharArrayNull() {
        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            //  Define a trivial map
            char [][] map = new char[1][1];

            mp.parseMap(map);

        });
        assertEquals("Invalid character at 0,0: \u0000", ex.getMessage());
    }


    /**
     *  Test the AddSquare method when there is empty ground (nice weather).
     */
    @Test
    void testAddSquareGround() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0, 0, ' ');
        verify(bf).createGround();
        assertThat(gr[0][0]).isEqualTo(sq);

    }

    /**
     *  Test the AddSquare method when there is a wall (nice weather).
     */
    @Test
    void testAddSquareWall() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0, 0, '#');
        verify(bf).createWall();
        assertThat(gr[0][0]).isEqualTo(sq);

    }

    /**
     *  Test the AddSquare method when there is a Pellet (nice weather).
     */
    @Test
    void testAddSquarePellet() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0, 0, '.');
        verify(lf).createPellet();
        verify(pel).occupy(any(Square.class));
        assertThat(gr[0][0]).isEqualTo(npc.getSquare());

    }

    /**
     *  Test the AddSquare method when there is a Ghost (nice weather).
     */
    @Test
    void testAddSquareGhost() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0, 0, 'G');

        //  Check that a ghost was added to the list
        assertThat(gh).contains(npc);
        //  Check if the the occupy() method has been called on the ghost
        verify(npc).occupy(gr[0][0]);

        assertThat(gr[0][0]).isEqualTo(sq);
    }

    /**
     *  Test the AddSquare method when there is a Player (nice weather).
     */
    @Test
    void testAddSquarePlayer() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0, 0, 'P');

        //  Check if the crreateGround() method has been called
        verify(bf).createGround();

        //  Check if the square has been assigned to the grid
        assertThat(gr[0][0]).isEqualTo(sq);

        //  Check to see if the player position has been added to the list
        assertThat(sp).contains(sq);

    }

    /**
     *  Test the addSquare method for invalid character (bad weather).
     */
    @Test
    void testaddSquareInvalidChar() {
        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mp.addSquare(gr, gh, sp, 0, 0, 'Q');

        });
        assertEquals("Invalid character at 0,0: Q", ex.getMessage());
    }

    /**
     *  Test the parseMap method when the input is a String list (nice weather).
     */
    @Test
    void testParseMapFromStringList() {
        //  Define a trivial String List
        List<String> sl = new ArrayList<>(1);
        sl.add(0, "P");

        Level level = mp.parseMap(sl);

        //  check that the method calls the character array version of the method
        Mockito.verify(mp).parseMap(any(char[][].class));

        //  check that the level that has been created is the mocked level
        assertThat(level).isEqualTo(lev);

    }

    /**
     *  Test the parseMap method when the input is a String list (bad weather).
     *  Invalid character
     */
    @Test
    void testParseMapFromStringListInvalid() {
        //  Define a trivial String List
        List<String> sl = new ArrayList<>(1);
        sl.add(0, "Q");

        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mp.parseMap(sl);

        });
        assertEquals("Invalid character at 0,0: Q", ex.getMessage());

    }

    /**
     *  Test the parseMap method when the input is a String list (bad weather).
     *  List is Null
     */
    @Test
    void testParseMapFromStringListNull() {
        //  Define a trivial String List
        List<String> sl = null;

        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mp.parseMap(sl);

        });
        assertEquals("Input text cannot be null.", ex.getMessage());

    }

    /**
     *  Test the parseMap method when the input is a String list (bad weather).
     *  Empty list
     */
    @Test
    void testParseMapFromStringListEmpty() {
        //  Define a trivial String List
        List<String> sl = new ArrayList<>();

        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mp.parseMap(sl);

        });
        assertEquals("Input text must consist of at least 1 row.", ex.getMessage());

    }

    /**
     *  Test the parseMap method when the input is a String list (bad weather).
     *  Empty Lines
     */
    @Test
    void testParseMapFromStringListEmptyLines() {
        //  Define a trivial String List
        List<String> sl = new ArrayList<>();
        sl.add("");

        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mp.parseMap(sl);

        });
        assertEquals("Input text lines cannot be empty.", ex.getMessage());

    }

    /**
     *  Test the parseMap method when the input is a String list (bad weather).
     *  Lines With unequal width
     */
    @Test
    void testParseMapFromStringListUnequalLineWidth() {
        //  Define a trivial String List
        List<String> sl = new ArrayList<>(10);
        sl.add(" ");
        sl.add("  ");

        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mp.parseMap(sl);

        });
        assertEquals("Input text lines are not of equal width.", ex.getMessage());

    }



    /**
     * Test the parseMap method when the input is an Input Stream (nice weather).
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @Test
    void testParseMapFromInputStream() throws IOException {
        //  Define a trivial InputStream
        InputStream is = new ByteArrayInputStream("P".getBytes("UTF8"));

        Level level = mp.parseMap(is);

        //  check that the method calls the String List version of the method
        Mockito.verify(mp).parseMap(any(List.class));

        //  check that the level that has been created is the mocked level
        assertThat(level).isEqualTo(lev);

    }

    /**
     *  Test the parseMap method when the input is an InputStream (bad weather).
     */
    @Test
    void testParseMapFromInputStreamException() {
        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {

            //  Mock the InputStream so that it throws an exception
            InputStream mis = mock(InputStream.class);
            when(mis.read()).thenThrow(new IOException());

            mp.parseMap(mis);

        });
        assertThat(ex.getMessage()).contains("Input text must consist of at least 1 row.");
    }

    /**
     * Test the parseMap() method when the input is a filename (nice weather).
     * @throws IOException
     */
    @Test
    void testParseMapFromString() throws IOException {
        //  Define a trivial String
        String s = "/simplemap.txt";

        Level level = mp.parseMap(s);

        //  check that the method calls the IOStream version of the parseMap method
        Mockito.verify(mp).parseMap(any(InputStream.class));

        //  check that the level that has been created is the mocked level
        assertThat(level).isEqualTo(lev);

    }

    /**
     *  Test the parseMap method when the input is an a filename (bad weather).
     */
    @Test
    void testParseMapFromStringException() {
        String s = "nonexistent.txt";

        //  Check to see that the right exception is thrown
        Throwable ex = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mp.parseMap(s);
        });
        assertThat(ex.getMessage()).contains("Could not get resource for: ");
    }



}
