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
public class PlayerWinTest {
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
     * to the player.The playerWinTestMap map is used.
     */
    @Test
    public void playerWinTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        getGame().start();
        players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.EAST);
        // Press the arrow to the last pellet

        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        // Game stops, player alive, and no pellets left
        // implies player win.

    }

    private Game getGame() {
        return launcher.getGame();
    }
}
