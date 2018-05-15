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
 * @discription:
 */
public class ClydeTest {
    /**
     * Map parser used to construct boards.
     */
    private GhostMapParser parser;

    private PlayerFactory playerfactory;
    /**
     * The String used to generate the level
     */
//    private List<String> map = new ArrayList<>(Arrays.asList("############", "#P        C#", "############"));

    private Level level;
    /**
     * Set up the map parser.
     */

    private Navigation nav;
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        playerfactory = new PlayerFactory(sprites);
        parser = GhostMapParser.create();

    }

    @Test
    void farAwayTest() {
        level = parser.parseMap(Lists.newArrayList("############", "#P        C#", "############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        System.out.println(p.getDirection());
//        Board b = level.getBoard();
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.getDirection()).isEqualTo(Direction.EAST);
    }

    @Test
    void onPointTest() {
        level = parser.parseMap(Lists.newArrayList("##############", "#P          C#", "##############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Board b = level.getBoard();
        Player x = parser.findUnit(Player.class, level);
        System.out.println(x);
        Clyde c = parser.findUnit(Clyde.class, level);
        Optional<Direction> d = c.nextAiMove();
        assertThat(d).isEqualTo(Optional.of(Direction.WEST));
    }
}
