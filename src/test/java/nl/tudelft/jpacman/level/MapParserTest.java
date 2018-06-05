package nl.tudelft.jpacman.level;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import com.google.common.collect.Lists;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.tudelft.jpacman.board.BoardFactory;
import org.junit.rules.ExpectedException;

import java.util.List;


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
    private final List<String> string = null;
    private ExpectedException thrown = ExpectedException.none();
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
        mapParser.parseMap(Lists.newArrayList("##", "  "));
        verify(boardCreator, times(2)).createGround();
        verify(boardCreator, times(2)).createWall();
    }

    /**
     * Test for mapParser with bad weather situation.
     */
    @Test
    void badWeather() {
        try {
            mapParser.parseMap(Lists.newArrayList("##", "   "));
        }
        catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).contains("Input text lines are not of equal width.");
        }
    }

    /**
     * Test for mapParser with empty input.
     */
    @Test
    void atLeastOneRow() {
        try {
            mapParser.parseMap(Lists.newArrayList());
        }
        catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).contains("Input text must consist of at least 1 row.");
        }
    }

    /**
     * Test for mapParser with null input.
     */
    @Test
    void nullInput() {
        try {
            mapParser.parseMap(string);
        }
        catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).contains("Input text cannot be null.");
        }
    }
    /**
     * Test for mapParser with 0 width.
     */
    @Test
    void zeroWidth() {
        try {
            mapParser.parseMap(Lists.newArrayList(""));
        }
        catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).contains("Input text lines cannot be empty.");
        }
    }
}
