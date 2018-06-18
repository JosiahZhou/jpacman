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
public class PlayerMovesOnEmptySquareTest {
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
    public void moveToEmptySquareTest() {
        launcher.launch();
        getGame().start();
        players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.EAST); //Move the player to the east.
        int score = player.getScore();

        /**
         * Now the previous square is empty, and the player is on its east.
         */
        Square playerSquare = player.getSquare(); //The current square
        assertThat(playerSquare.getSquareAt(Direction.WEST).getOccupants()).isEmpty();

        getGame().move(player, Direction.WEST); //Move to the empty square
        Square newPlayerSquare = player.getSquare();

        /**
         * Check if the player moved in the right direction.
         */
        assertThat(newPlayerSquare.getSquareAt(Direction.EAST)).isEqualTo(playerSquare);

        /**
         * Check if the player's score remain the same.
         */
        assertThat(player.getScore()).isEqualTo(score);
    }

    private Game getGame() {
        return launcher.getGame();
    }
}
