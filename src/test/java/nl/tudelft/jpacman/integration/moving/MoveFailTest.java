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
     * to the player.The default map is used.
     */
    @Test
    public void moveFailTest() {
        launcher.withMapFile("/simpleMap.txt");
        launcher.launch();
        getGame().start();
        players = getGame().getPlayers();
        Player player = players.get(0);

        Square playerSquare = player.getSquare();

        /**
         * Check if the square next to the player is a wall.
         */
        assertThat(playerSquare.getSquareAt(Direction.EAST).isAccessibleTo(player)).isFalse();

        getGame().move(player, Direction.EAST);
        Square newPlayerSquare = player.getSquare();

        /**
         * Check if the player remained in the same square, which means
         * the movement is not conducted.
         */
        assertThat(newPlayerSquare).isEqualTo(playerSquare);


    }

    private Game getGame() {
        return launcher.getGame();
    }
}
