package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;


import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;


/**
 * @author: Chenru Lin
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

    /**
     * Test if the Player is far away from Clyde.
     */
    @Test
    void farAwayTest() {
        level = parser.parseMap(
                Lists.newArrayList("##############", "#P          C#", "##############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * Test if the Player is near Clyde.
     */
    @Test
    void nearTest() {
        level = parser.parseMap(
                Lists.newArrayList("############", "#P     C   #", "############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

    /**
     * Test if Clyde has no path to go.
     */
    @Test
    void noPathTest() {
        level = parser.parseMap(
                Lists.newArrayList("############", "#P    #C   #", "############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Test if there's no player.
     */
    @Test
    void noPlayerTest() {
        level = parser.parseMap(
                Lists.newArrayList("#############", "#          C#", "#############"));
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Test for the on point distance.
     */
    @Test
    void onPointTest() {
        level = parser.parseMap(
                Lists.newArrayList("############", "#P        C#", "############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * Test for the off point distance.
     */
    @Test
    void offPointTest() {
        level = parser.parseMap(
                Lists.newArrayList("#############", "#P         C#", "#############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Clyde c = parser.findUnit(Clyde.class, level);
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }


}
