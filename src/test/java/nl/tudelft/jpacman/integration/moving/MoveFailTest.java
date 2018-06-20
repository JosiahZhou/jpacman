package nl.tudelft.jpacman.integration.moving;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: Chenru Lin
 * @discription:
 */
public class MoveFailTest {
    private Launcher launcher;
    private List<Player> players;
    /**
     * Start a launcher, which can display the user interface.
     */
    @BeforeEach
    public void before() {
        launcher = new Launcher();
    }

    /**
     * Close the user interface.
     */
    @AfterEach
    public void after() {
        launcher.dispose();
    }

    /**
     * A test for player consume a pellet in the square next
     * to the player.The smallMap is used.
     */
    @Test
    public void moveFailTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        getGame().start();
        players = getGame().getPlayers();
        Player player = players.get(0);

        Square playerSquare = player.getSquare();

        /**
         * Check if the square next to the player is a wall
         * or other things that are not accessible to
         * the player.
         */
        assertThat(playerSquare.getSquareAt(Direction.NORTH).isAccessibleTo(player)).isFalse();

        getGame().move(player, Direction.NORTH);
        // Move the player towards the not accessible square.

        Square newPlayerSquare = player.getSquare();
        assertThat(getGame().isInProgress()).isTrue();
        // Check if the game is in progress.
        assertThat(newPlayerSquare).isEqualTo(playerSquare);
        // Check if the movement is not conducted.




    }

    private Game getGame() {
        return launcher.getGame();
    }
}
