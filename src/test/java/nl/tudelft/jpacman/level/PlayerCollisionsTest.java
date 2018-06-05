package nl.tudelft.jpacman.level;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.Test;

/**
 * @author: Chenru Lin
 * @discription:
 */
public class PlayerCollisionsTest {
    private Player player = mock(Player.class);
    private Pellet pellet = mock(Pellet.class);
    private Ghost ghost = mock(Ghost.class);
    private PlayerCollisions playerCollisions = new PlayerCollisions();

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
