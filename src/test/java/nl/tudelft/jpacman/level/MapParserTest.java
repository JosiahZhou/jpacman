package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;


/**
 * @author: Chenru Lin
 * @date 2018/6/4 23:24
 * @discription:
 */
public class MapParserTest {
    MapParser mapParser;

    Square wall = mock(Square.class);
    Square ground = mock(Square.class);
    LevelFactory levelCreator = mock(LevelFactory.class);
    BoardFactory boardCreator = mock(BoardFactory.class);
    @BeforeEach
    void setUp() {

        mapParser= new MapParser(levelCreator, boardCreator);
        when(boardCreator.createGround()).thenReturn(ground);
        when(boardCreator.createWall()).thenReturn(wall);
    }
    @Test
    void niceWeather() {

        Level level = mapParser.parseMap(Lists.newArrayList("# #", "   "));
        verify(boardCreator, times(4)).createGround();
        verify(boardCreator, times(2)).createWall();

    }
}
