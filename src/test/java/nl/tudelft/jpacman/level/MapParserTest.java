package nl.tudelft.jpacman.level;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.tudelft.jpacman.board.BoardFactory;


/**
 * @author: Chenru Lin
 * @discription:
 */
public class MapParserTest {
    private MapParser mapParser;

    private final Square wall = mock(Square.class);
    private final Square ground = mock(Square.class);
    private final LevelFactory levelCreator = mock(LevelFactory.class);
    private final BoardFactory boardCreator = mock(BoardFactory.class);
    /**
     * Sets up the mapParser with the default factories.
     */
    @BeforeEach
    void setUp() {
        mapParser = new MapParser(levelCreator, boardCreator);
        when(boardCreator.createGround()).thenReturn(ground);
        when(boardCreator.createWall()).thenReturn(wall);
    }

    /**
     * Test for mapParser with nice weather situation.
     */
    @Test
    void niceWeather() {

        Level level = mapParser.parseMap(Lists.newArrayList("##", "  "));
        verify(boardCreator, times(2)).createGround();
        verify(boardCreator, times(2)).createWall();

    }
}
