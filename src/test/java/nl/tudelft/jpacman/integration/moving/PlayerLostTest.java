package nl.tudelft.jpacman.integration.moving;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
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
public class PlayerLostTest {
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
    public void playerLostTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        getGame().start();
        players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.WEST);

        assertThat(player.isAlive()).isFalse();
        assertThat(getGame().isInProgress()).isFalse();
        // Player died and the game ends means player lost

    }
    private Game getGame() {
        return launcher.getGame();
    }
}
