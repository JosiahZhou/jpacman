package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.Ghost;

import static org.mockito.Mockito.mock;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.board.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.*;

import static org.mockito.Mockito.mock;

/**
 * @author: Chenru Lin
 * @date 2018/5/15 21:29
 * @discription: Some tests for the movement of Clyde
 */
public class ClydeTest {
    /**
     * Map parser used to construct boards.
     */
    private GhostMapParser parser;

    private PlayerFactory playerfactory;

    private Level level;
    /**
     * Set up the map parser.
     */

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        playerfactory = new PlayerFactory(sprites);
        parser = GhostMapParser.create();

    }

    @Test
    void farAwayTest() {
        level = parser.parseMap(Lists.newArrayList("##############", "#P          C#", "##############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    @Test
    void NearTest() {
        level = parser.parseMap(Lists.newArrayList("############", "#P     C   #", "############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

    @Test
    void noPathTest() {
        level = parser.parseMap(Lists.newArrayList("############", "#P    #C   #", "############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.empty());
    }

    @Test
    void noPlayerTest() {
        level = parser.parseMap(Lists.newArrayList("#############", "#          C#", "#############"));
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.empty());
    }

    @Test
    void onPointTest() {
        level = parser.parseMap(Lists.newArrayList("############", "#P        C#", "############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    @Test
    void offPointTest() {
        level = parser.parseMap(Lists.newArrayList("#############", "#P         C#", "#############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }


}
