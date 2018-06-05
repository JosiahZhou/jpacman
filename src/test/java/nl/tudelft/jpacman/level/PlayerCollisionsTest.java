package nl.tudelft.jpacman.level;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import com.google.common.collect.Lists;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.tudelft.jpacman.board.BoardFactory;

import java.util.List;
/**
 * @author: Chenru Lin
 * @discription:
 */
public class PlayerCollisionsTest {
    private Player player = mock(Player.class);
    private Pellet pellet = mock(Pellet.class);
    private Ghost ghost = mock(Ghost.class);
    PlayerCollisions playerCollisions = new PlayerCollisions();

    /**
     * When player meets a ghost, player dies.
     */
    @Test
    void playerCollideGhost() {
        playerCollisions.collide(player, ghost);
        verify(player).setAlive(false);
    }

    /**
     * When player meets a pellet, player eats it.
     */
    @Test
    void playerCollidePellet() {
        playerCollisions.collide(player, pellet);
        verify(player).addPoints(pellet.getValue());
        verify(pellet).leaveSquare();
    }

    /**
     * When ghost catches a player, player dies.
     */
    @Test
    void ghostCollidePlayer() {
        playerCollisions.collide(ghost, player);
        verify(player).setAlive(false);
    }
    /**
     * When player meets a ghost, player dies.
     */
    @Test
    void pelletCollidePlayer() {
        playerCollisions.collide(pellet, player);
        verify(player).addPoints(pellet.getValue());
        verify(pellet).leaveSquare();
    }


}
