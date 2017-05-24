package nl.tudelft.jpacman.level;

import com.sun.xml.internal.fastinfoset.util.CharArray;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
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

    //List<> gh;
    @Mock private LevelFactory levelCreator;
    @Mock private BoardFactory boardCreator;
    //@Mock protected void addSquare();
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
        gl = mock(List.class);
        sq = mock(Square.class);

        //when(lf.createLevel(bd, ))
    }

    /**
     *  Test the MapParser constructor for nice-weather situations.
     */
    @Test
    public void testConstructor1() {
        mpm = new MapParser(lf, bf);
        assertThat(levelCreator).isEqualTo(lf);
        assertThat(boardCreator).isEqualTo(bf);
    }

    /**
     *  Test if the ParseMap method for nice-weather conditions.
     */
    @Test
    public void testParseMapFromCharArray() {
        //@Spy
        MapParser mp = Mockito.spy(new MapParser(lf,bf));
        //MapParser mpm = mock(MapParser.class);

        //  Define a trivial map
        char [][] map = new char[1][1];
        map[0][0] = 'P';
        List<Square> sp = new ArrayList<>();
        sp.add(sq);
        Square[][] gr = new Square[1][1];
        gr[0][0] = sq;


        //  Return a mocked Board from the mocked BoardFactory
        when(bf.createBoard(new Square[1][1])).thenReturn(bd);
        when(bf.createGround()).thenReturn(sq);
        when(bf.createWall()).thenReturn(sq);

        //  Return a mocked Level from the mocked LevelFactory
        when(lf.createLevel(bd, new ArrayList<>(), new ArrayList<>())).thenReturn(lev);

        //  Prevent the addSquare method from trying to do stuff.
        //doNothing().when(mpm).addSquare(new Square[1][1], new ArrayList<>(), new ArrayList<>(),0,0,'P');

        Level level = mp.parseMap(map);
        Mockito.verify(mp).addSquare(gr, new ArrayList<>(), sp,0,0,'P');

        assertThat(level).isEqualTo(lev);
    }

    @Test
    void testMakeGrid() {}

    @Test
    void testAddSquare() {}

    @Test
    void testMakeMakeGhostSquare() {}

    @Test
    void testParseMapFromStringList() {}

    @Test
    void testCheckMapFormat() {}

    @Test
    void testParseMapFromInputStream() {}

    @Test
    void testParseMapFromString() {}


}
