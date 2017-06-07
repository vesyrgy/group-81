package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Lars Ysla
 */
//@RunWith(MockitoJUnitRunner.class)
public class MapParserTest {
    LevelFactory lf;
    BoardFactory bf;
    Level lev;
    Board bd;
    NPC npc;
    List gl;
    Square sq;
    Pellet pel;
    List<NPC> gh;
    List<Square> sp;
    MapParser mp;
    Square[][] gr;

    @Mock private LevelFactory levelCreator;
    @Mock private BoardFactory boardCreator;
    @InjectMocks MapParser mpm;

    /**
     *  Set up the mock objects before each test.
     */
    @BeforeEach
    public void setupMocks() {
        lf = mock(LevelFactory.class);
        bf = mock(BoardFactory.class);
        lev = mock(Level.class);
        bd = mock(Board.class);
        npc = mock(NPC.class);
        //gl = mock(List.class);
        sq = mock(Square.class);
        pel = mock(Pellet.class);
        MockitoAnnotations.initMocks(this);

        gh = new ArrayList<>();
        sp = new ArrayList<>();

        //  Return mocked Squares from the mocked BoardFactory
        when(bf.createGround()).thenReturn(sq);
        when(bf.createWall()).thenReturn(sq);

        //  Return a mocked Level from the mocked LevelFactory
        when(lf.createLevel(any(Board.class), any(ArrayList.class), any(ArrayList.class))).thenReturn(lev);
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

        //  Return a mocked Board from the mocked BoardFactory
        when(bf.createBoard(any(gr.getClass()))).thenReturn(bd);

    }

    /**
     *  Test the MapParser constructor.
     */
    @Test
    public void testConstructor1() {
        mpm = new MapParser(lf, bf);
        assertThat(levelCreator).isInstanceOf(lf.getClass());
        assertThat(boardCreator).isInstanceOf(bf.getClass());
    }

    /**
     *  Test the parseMap method for a character array.
     */
    @Test
    public void testParseMapFromCharArray() {
        //  Spy on the method calls within MapParser object
        MapParser mp = Mockito.spy(new MapParser(lf,bf));

        //  Define a trivial map
        char [][] map = new char[1][1];
        map[0][0] = 'P';

        Level level = mp.parseMap(map);
        //  Check that the addSquare method gets called
        Mockito.verify(mp).addSquare(any(gr.getClass()), any(ArrayList.class), any(ArrayList.class),anyInt(),anyInt(),anyChar());

        assertThat(level).isEqualTo(lev);
    }


    /**
     *  Test the AddSquare method when there is empty ground.
     */
    @Test
    void testAddSquareGround() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0,0,' ');
        verify(bf).createGround();
        assertThat(gr[0][0]).isEqualTo(sq);

    }

    /**
     *  Test the AddSquare method when there is a wall.
     */
    @Test
    void testAddSquareWall() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0,0,'#');
        verify(bf).createWall();
        assertThat(gr[0][0]).isEqualTo(sq);

    }

    /**
     *  Test the AddSquare method when there is a Pellet.
     */
    @Test
    void testAddSquarePellet() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0,0,'.');
        verify(lf).createPellet();
        verify(pel).occupy(any(Square.class));
        assertThat(gr[0][0]).isEqualTo(npc.getSquare());

    }

    /**
     *  Test the AddSquare method when there is a Ghost.
     */
    @Test
    void testAddSquareGhost() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0,0,'G');

        //  Check that a ghost was added to the list
        assertThat(gh.contains(npc));
        //  Check if the the occupy() method has been called on the ghost
        verify(npc).occupy(gr[0][0]);

        assertThat(gr[0][0]).isEqualTo(sq);
    }

    /**
     *  Test the AddSquare method when there is a Player.
     */
    @Test
    void testAddSquarePlayer() {
        gr[0][0] = null;

        mp.addSquare(gr, gh, sp, 0,0,'P');

        //  Check if the crreateGround() method has been called
        verify(bf).createGround();

        //  Check if the square has been assigned to the grid
        assertThat(gr[0][0]).isEqualTo(sq);

        //  Check to see if the player position has been added to the list
        assertThat(sp.contains(sq));

    }

    /**
     *  Test the parseMap method when the input is a String list
     */
    @Test
    void testParseMapFromStringList() {
        //  Spy on the method calls within MapParser object
        MapParser mp = Mockito.spy(new MapParser(lf,bf));

        //  Define a trivial String List
        List<String> sl = new ArrayList<>(1);
        sl.add(0,"P");

        Level level = mp.parseMap(sl);

        //  check that the method calls the character array version of the method
        Mockito.verify(mp).parseMap(any(char[][].class));

        //  check that the level that has been created is the mocked level
        assertThat(level).isEqualTo(lev);

    }

    /**
     * Test the parseMap method when the input is an Input Stream.
     * @throws IOException
     */
    @Test
    void testParseMapFromInputStream() throws IOException {
        //  Spy on the method calls within MapParser object
        MapParser mp = Mockito.spy(new MapParser(lf,bf));

        //  Define a trivial InputStream
        InputStream is = new ByteArrayInputStream( "P".getBytes());

        Level level = mp.parseMap(is);

        //  check that the method calls the String List version of the method
        Mockito.verify(mp).parseMap(any(List.class));

        //  check that the level that has been created is the mocked level
        assertThat(level).isEqualTo(lev);

    }

    /**
     * Test the parseMap() method when the input is a filename.
     * @throws IOException
     */
    @Test
    void testParseMapFromString() throws IOException {
        //  Spy on the method calls within MapParser object
        MapParser mp = Mockito.spy(new MapParser(lf,bf));

        //  Define a trivial String
        String s = "/simplemap.txt";

        Level level = mp.parseMap(s);

        //  check that the method calls the IOStream version of the parseMap method
        Mockito.verify(mp).parseMap(any(InputStream.class));

        //  check that the level that has been created is the mocked level
        assertThat(level).isEqualTo(lev);

    }



}
