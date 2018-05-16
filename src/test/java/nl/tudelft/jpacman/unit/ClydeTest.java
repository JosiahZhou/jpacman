package nl.tudelft.jpacman.unit;


import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;

/**
 * A test for unit Clyde.
 *@author: Chenru Lin
 *@date 2018/5/15 20:42
 *
 */

public class ClydeTest {
    /**
     * Map parser used to construct boards.
     */
    private MapParser parser;

    /**
     * The level under test.
     */
    private Level level;

    /**
     * Set up the map parser.
     */
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        parser = new MapParser(new LevelFactory(sprites, new GhostFactory(
                sprites)), new BoardFactory(sprites));
    }
}