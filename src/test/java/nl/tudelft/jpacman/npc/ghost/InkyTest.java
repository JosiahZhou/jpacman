package nl.tudelft.jpacman.npc.ghost;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: Chenru Lin
 * @discription: Some tests for the movement of Inky
 */
public class InkyTest {

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
    }

    /**
     * Test when Inky is behind Blinky and Blinky is behind player.
     */
    @Test
    void followBlinkyTest() {
        level = parser.parseMap(
                Lists.newArrayList("##############", "#P        B I#",
                        "##############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Inky i = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(i.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * Test when Inky is ahead of Blinky and Blinky is behind player.
     */
    @Test
    void towardPlayerAwayFromBlinkyTest() {
        level = parser.parseMap(
                Lists.newArrayList("##############", "#        P IB#",
                        "##############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Inky i = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(i.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * Test when Inky is far ahead of player and Blinky is behind player.
     */
    @Test
    void towardPlayerTowardBlinkyTest() {
        level = parser.parseMap(
                Lists.newArrayList("###############", "#I         P B#",
                        "###############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Inky i = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(i.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }


    /**
     * Test when Inky is ahead of player and Blinky is far behind player.
     */
    @Test
    void awayFromPlayerAwayFromBlinkyTest() {
        level = parser.parseMap(
                Lists.newArrayList("###############", "#      I  P  B#",
                        "###############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Inky i = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(i.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * Test when no path between Blinky and Player.
     */
    @Test
    void noPathTest() {
        level = parser.parseMap(
                Lists.newArrayList("##############", "#P    #   B I#",
                        "##############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Inky i = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(i.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Test when the destination is wall.
     */
    @Test
    void destinationOutOfBoundaryTest() {
        level = parser.parseMap(
                Lists.newArrayList("#############", "#P        IB#",
                        "#############"));
        Player p = playerfactory.createPacMan();
        level.registerPlayer(p);
        p.setDirection(Direction.WEST);
        Inky i = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(i.nextAiMove()).isEqualTo(Optional.empty());
    }


    /**
     * Test when there is no player.
     */
    @Test
    void noPlayerTest() {
        level = parser.parseMap(
                Lists.newArrayList("#############", "#          I#",
                        "#############"));
        Inky i = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(i.nextAiMove()).isEqualTo(Optional.empty());
    }
}
