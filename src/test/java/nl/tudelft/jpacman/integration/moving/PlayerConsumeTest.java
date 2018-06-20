package nl.tudelft.jpacman.integration.moving;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
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
public class PlayerConsumeTest {
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
    public void consumeTest() {
        launcher.launch();
        getGame().start();
        players = getGame().getPlayers();
        Player player = players.get(0);
        int score = player.getScore();
        Square playerSquare = player.getSquare();
        Square pelletSquare = playerSquare.getSquareAt(Direction.EAST);
        List<Unit> units = pelletSquare.getOccupants();

        assertThat((units.get(0)) instanceof Pellet).isTrue();
        // Check if the square on the east contains a pellet.

        Pellet pellet = (Pellet) units.get(0);
        getGame().move(player, Direction.EAST);
        Square newPlayerSquare = player.getSquare();

        assertThat(playerSquare.getSquareAt(Direction.EAST)).isEqualTo(newPlayerSquare);
        // Check if the player moved in the right direction.

        assertThat(player.getScore()).isEqualTo(score + pellet.getValue());
        // Check if the player got the score.

        assertThat(newPlayerSquare.getOccupants().size()).isEqualTo(1);
        assertThat(newPlayerSquare.getOccupants().get(0)).isEqualTo(player);
        // Check if the pellet left the square.

    }

    private Game getGame() {
        return launcher.getGame();
    }
}
