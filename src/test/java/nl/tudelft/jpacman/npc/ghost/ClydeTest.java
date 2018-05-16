package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.Level;

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

    private PlayerFactory playerfactory;
    private LevelFactory levelfactory;
    private GhostFactory ghostFactory;
    private BoardFactory boardfactory;
    private GhostMapParser parser;
    private Level level;
    /**
     * Set up the map parser.
     */

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        playerfactory = new PlayerFactory(sprites);
        ghostFactory = new GhostFactory(sprites);
        levelfactory = new LevelFactory(sprites, ghostFactory);
        boardfactory = new BoardFactory(sprites);
        parser = new GhostMapParser(levelfactory, boardfactory, ghostFactory);
//        parser.
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
        Clyde c = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
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
        Clyde c = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
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
        Clyde c = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(c.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Test if there's no player.
     */
    @Test
    void noPlayerTest() {
        level = parser.parseMap(
                Lists.newArrayList("#############", "#          C#", "#############"));
        Clyde c = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
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
        Clyde c = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
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
        Clyde c = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(c.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }


}
