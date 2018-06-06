package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author: Chenru Lin
 * @discription:
 */
public abstract class CollisionMapTest {
    private Player player = mock(Player.class);
    private Pellet pellet = mock(Pellet.class);
    private Ghost ghost = mock(Ghost.class);
    private CollisionMap camp = null;

    /**
     * @param camp
     *          The CollisionMap type
     */
    public void setCamp(CollisionMap camp) {
        this.camp = camp;
    }

    /**
     * An abstract method to set camp.
     */
    abstract void set();


    /**
     * When player meets a ghost, player dies.
     */
    @Test
    void playerCollideGhost() {
        camp.collide(player, ghost);
        verify(player).setAlive(false);
    }

    /**
     * When player meets a pellet, player eats it.
     */
    @Test
    void playerCollidePellet() {
        camp.collide(player, pellet);
        verify(player).addPoints(pellet.getValue());
        verify(pellet).leaveSquare();
    }

    /**
     * When ghost catches a player, player dies.
     */
    @Test
    void ghostCollidePlayer() {
        camp.collide(ghost, player);
        verify(player).setAlive(false);
    }
    /**
     * When a pellet meets a player, player eats it.
     */
    @Test
    void pelletCollidePlayer() {
        camp.collide(pellet, player);
        verify(player).addPoints(pellet.getValue());
        verify(pellet).leaveSquare();
    }
}
