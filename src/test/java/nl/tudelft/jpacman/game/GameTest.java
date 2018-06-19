package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;

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
public class GameTest {
    private Launcher launcher;
    private Game singlePlayerGame;

    /**
     * Set up single player game.
     */
    @BeforeEach
    void setUp() {
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
     * Conformance test suite.
     * Pairs are of <(state), (events)> pattern.
     */

    /**
     * A test case for <(ready to start), (Last pellet eaten)> pair.
     */
    @Test
    public void winTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        singlePlayerGame = getGame();
        singlePlayerGame.start();
        List<Player> players = singlePlayerGame.getPlayers();
        Player player = players.get(0);

        singlePlayerGame.move(player, Direction.EAST);
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * A test case for <(ready to start), (Last pellet eaten)> pair.
     */
    @Test
    public void loseTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        singlePlayerGame = getGame();
        singlePlayerGame.start();
        List<Player> players = singlePlayerGame.getPlayers();
        Player player = players.get(0);

        singlePlayerGame.move(player, Direction.EAST);
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * A test for <(ready to start), (start, stop, start)> pair.
     */
    @Test
    public void resumeTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        singlePlayerGame = getGame();
        singlePlayerGame.start();
        singlePlayerGame.stop();
        singlePlayerGame.start();

        assertThat(getGame().isInProgress()).isTrue();
    }

    /**
     * A test case for <(ready to start), (pellets left and player is alive)> pair.
     */
    @Test
    public void playingTest() {
        launcher.withMapFile("/playingMap.txt");
        launcher.launch();
        singlePlayerGame = getGame();
        singlePlayerGame.start();
        List<Player> players = singlePlayerGame.getPlayers();
        Player player = players.get(0);

        singlePlayerGame.move(player, Direction.EAST);
        assertThat(getGame().isInProgress()).isTrue();
        assertThat(player.isAlive()).isTrue();
    }

    /**
     * Sneak path test suite.
     * Pairs are of <(state), (events)> pattern.
     */

    /**
     * A test case for <(playing), (start)> pair.
     */
    @Test
    public void playingStartTest() {
        launcher.launch();
        getGame().start();
        getGame().start();

        assertThat(getGame().isInProgress()).isTrue();
    }

    /**
     * A test case for <(ready for playing), (pause)> pair.
     */
    @Test
    public void readyPauseTest() {
        launcher.launch();
        getGame().stop();
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * A test case for <(ready for playing), (eat last)> pair.
     */
    @Test
    public void readyEatLastTest() {
        launcher.withMapFile("/playingMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.EAST);
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * A test case for <(ready for playing), (meet ghost)> pair.
     */
    @Test
    public void readyMeetGhostTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        Square square = player.getSquare();

        getGame().move(player, Direction.EAST);
        Square newSquare = player.getSquare();
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(newSquare).isEqualTo(square); //Check if the player remain still.
    }

    /**
     * A test case for <(pausing), (pause)> pair.
     */
    @Test
    public void pausePauseTest() {
        launcher.withMapFile("/playingMap.txt");
        launcher.launch();

        getGame().start();
        getGame().stop();
        getGame().stop();

        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * A test case for <(pausing), (eat last)> pair.
     */
    @Test
    public void pauseEatLastTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();

        getGame().stop();
        int score = player.getScore();
        getGame().move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(score); // Check if the points remain the same
    }

    /**
     * A test case for <(pausing), (meet ghost)> pair.
     */
    @Test
    public void pauseMeetGhostTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();

        getGame().stop();
        getGame().move(player, Direction.EAST);
        assertThat(player.isAlive()).isTrue();
        // Check if the player is still alive (doesn't move to the ghost)
    }

    /**
     * A test case for <(pausing), (alive & pellet left)> pair.
     */
    @Test
    public void pauseMoveTest() {
        launcher.withMapFile("/playingMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().stop();
        getGame().move(player, Direction.EAST);
        assertThat(player.isAlive()).isTrue();
        // Check if the player is still alive (doesn't move to the ghost)
    }

    /**
     * A test case for <(winning), (start)> pair.
     */
    @Test
    public void winningStartTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().start();
        assertThat(getGame().isInProgress()).isFalse();
        // Check if the game cannot be start when winning.
    }

    /**
     * A test case for <(winning), (pause)> pair.
     */
    @Test
    public void winningPauseTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().stop();
        assertThat(getGame().isInProgress()).isFalse();
        // Check if the game cannot be start when winning.
    }

    /**
     * A test case for <(winning), (move)> pair.
     */
    @Test
    public void winningMoveTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        Square square = player.getSquare();
        getGame().move(player, Direction.EAST);
        Square newSquare = player.getSquare();
        assertThat(newSquare).isEqualTo(square);
        // Check if the player cannot move when winning.
    }

    /**
     * A test case for <(losing), (start)> pair.
     */
    @Test
    public void losingStartTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().start();
        assertThat(player.isAlive()).isFalse();
        // Check if the player is still dead.

        assertThat(getGame().isInProgress()).isFalse();
        // Check if the game cannot be start when winning.
    }

    /**
     * A test case for <(losing), (pause)> pair.
     */
    @Test
    public void losingPauseTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        assertThat(getGame().isInProgress()).isFalse();
        getGame().stop();
        assertThat(getGame().isInProgress()).isFalse();
        // Check if nothing happens when press stop when losing.
    }

    /**
     * A test case for <(losing), (move)> pair.
     */
    @Test
    public void losingMoveTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        Square square = player.getSquare();
        getGame().move(player, Direction.EAST);
        Square newSquare = player.getSquare();
        assertThat(newSquare).isEqualTo(square); // Check if the player cannot move when losing.
    }

    private Game getGame() {
        return launcher.getGame();
    }
}
