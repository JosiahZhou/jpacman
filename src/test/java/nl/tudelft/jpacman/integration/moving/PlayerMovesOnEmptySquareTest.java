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
        // Check if the square on the west if empty.

        getGame().move(player, Direction.WEST); //Move to the empty square
        Square newPlayerSquare = player.getSquare();

        assertThat(playerSquare.getSquareAt(Direction.WEST)).isEqualTo(newPlayerSquare);
        // Check if the player moved in the right direction.


        assertThat(player.getScore()).isEqualTo(score);
        // Check if the score remain the same.
    }

    private Game getGame() {
        return launcher.getGame();
    }
}
