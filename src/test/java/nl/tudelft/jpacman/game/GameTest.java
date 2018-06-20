package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;

import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: Chenru Lin
 * @discription:
 */
public class GameTest {
    private Launcher launcher;

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
     * Pairs are of <(state), (events)> patterns.
     */

    /**
     * A test case for <(ready to start), (start, Last pellet eaten)> pair.
     */
    @Test
    public void winTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        getGame().start(); // start
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);

        getGame().move(player, Direction.EAST); // Last pellet eaten

        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * A test case for <(ready to start), (start, meet ghost)> pair.
     * In the condition that the player collides with a ghost.
     */
    @Test
    public void pcgLoseTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        getGame().start();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.EAST);
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * Another test case for <(ready to start), (start, meet ghost)> pair.
     * In the condition that a ghost catches the player.
     */
    @Test
    public void gcpLoseTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        getGame().start();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        List<Unit> units = player.getSquare().getSquareAt(Direction.EAST).getOccupants();
        assertThat(units.get(0) instanceof Ghost).isTrue();
        Ghost ghost = (Ghost) units.get(0);

        getGame().getLevel().move(ghost, Direction.WEST);
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * A test for <(ready to start), (start, stop, start)> pair.
     */
    @Test
    public void resumeTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        getGame().start();
        getGame().stop();
        assertThat(getGame().isInProgress()).isFalse();

        getGame().start();

        assertThat(getGame().isInProgress()).isTrue();
    }

    /**
     * A test case for <(ready to start), (pellets left and player is alive)> pair.
     * This is a test for regular movement.
     */
    @Test
    public void playingTest() {
        launcher.withMapFile("/playingMap.txt");
        launcher.launch();
        getGame().start();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);

        getGame().move(player, Direction.EAST);
        assertThat(getGame().isInProgress()).isTrue();
        assertThat(player.isAlive()).isTrue();
    }

    /**
     * Sneak path test suite.
     * Pairs are of <(state), (events)> patterns.
     */

    /**
     * A test case for <(playing), (start)> pair.
     */
    @Test
    public void playingStartTest() {
        launcher.launch();
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
    }

    /**
     * A test case for <(ready for playing), (pause)> pair.
     */
    @Test
    public void readyPauseTest() {
        launcher.launch();
        Mockito.spy(getGame());
        assertThat(getGame().isInProgress()).isFalse();

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
        Square square = player.getSquare();
        getGame().move(player, Direction.EAST);
        Square newSquare = player.getSquare();

        assertThat(newSquare).isEqualTo(square);
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
        assertThat(newSquare).isEqualTo(square); //Check if the player cannot move.
    }

    /**
     * Another test case for <(ready to start), (meet ghost)> pair.
     * This is to test the ghost cannot move in the ready to start state.
     */
    @Test
    public void readyGhostCatchPlayerTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        List<Unit> units = player.getSquare().getSquareAt(Direction.EAST).getOccupants();
        assertThat(units.get(0) instanceof Ghost).isTrue();
        Ghost ghost = (Ghost) units.get(0);

        Square square = ghost.getSquare();
        getGame().getLevel().move(ghost, Direction.WEST);
        Square newSquare = ghost.getSquare();

        assertThat(square).isEqualTo(newSquare);
        // Check if the ghost cannot move.

        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().isInProgress()).isFalse();
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
        assertThat(getGame().isInProgress()).isFalse();

        getGame().stop();
        assertThat(getGame().isInProgress()).isFalse();
        // Check if nothing happened.
    }

    /**
     * We combine events with player movement in the pausing state.
     * The player cannot move in the pausing state and
     * it cannot consume, collide with a ghost, etc.
     * A test case for <(pausing), (move)> pair.
     */
    @Test
    public void pauseMoveTest() {
        launcher.withMapFile("/playerWinTestMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().stop(); // Pause
        int score = player.getScore();
        Square square = player.getSquare();
        getGame().move(player, Direction.EAST);
        Square newSquare = player.getSquare();

        assertThat(newSquare).isEqualTo(square);
        // Check if the player cannot move.
        assertThat(player.isAlive()).isTrue();
        // Check if the player is still alive
        assertThat(player.getScore()).isEqualTo(score);
        // Check if the points remain the same.
    }


    /**
     * Another test case for <(pausing), (meet ghost)> pair.
     * This is to test the ghost cannot move in the ready to start state.
     */
    @Test
    public void pauseGhostCatchPlayerTest() {
        launcher.withMapFile("/playerLostMap.txt");
        launcher.launch();
        getGame().start();
        getGame().stop();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        List<Unit> units = player.getSquare().getSquareAt(Direction.EAST).getOccupants();
        assertThat(units.get(0) instanceof Ghost).isTrue();
        Ghost ghost = (Ghost) units.get(0);

        Square square = ghost.getSquare();
        getGame().getLevel().move(ghost, Direction.WEST);
        Square newSquare = ghost.getSquare();

        assertThat(square).isEqualTo(newSquare);
        // Check if the ghost cannot move.

        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().isInProgress()).isFalse();
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
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        // Check if the player wins

        getGame().start();
        assertThat(getGame().isInProgress()).isFalse();
        // Check if the game cannot be started when winning.
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
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        // Check if the player wins

        getGame().stop();
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        // Check if nothing changed.
    }

    /**
     * We combine all the events with movement in the winning state.
     * (last pellet eaten, meet ghost, eat not the last pellet and player alive)
     * Player cannot move means it cannot consume pellet, meet ghost, etc.
     *
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
     * Another test case for <(pausing), (meet ghost)> pair.
     * This is to test the ghost cannot move in the ready to start state.
     */
    @Test
    public void winGhostCatchPlayerTest() {
        launcher.withMapFile("/winGCPMap.txt");
        launcher.launch();
        getGame().start();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.EAST);

        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        //Check if the game is in the winning state.

        List<Unit> units = player.getSquare().getSquareAt(Direction.EAST).getOccupants();
        assertThat(units.get(0) instanceof Ghost).isTrue();
        Ghost ghost = (Ghost) units.get(0);

        Square square = ghost.getSquare();
        getGame().getLevel().move(ghost, Direction.WEST);
        Square newSquare = ghost.getSquare();

        assertThat(square).isEqualTo(newSquare);
        // Check if the ghost cannot move.

        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().isInProgress()).isFalse();
        // Check the state of the game did'n change.
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
        assertThat(player.isAlive()).isFalse();
        assertThat(getGame().isInProgress()).isFalse();
        // Check if the game has lost.

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
        assertThat(player.isAlive()).isFalse();
        assertThat(getGame().isInProgress()).isFalse();
        //Check if the player lost.

        getGame().stop();
        assertThat(player.isAlive()).isFalse();
        assertThat(getGame().isInProgress()).isFalse();
        // Check if nothing happens when pressing stop when losing.
    }

    /**
     * We combine all the events with player movement in the losing state.
     * Player cannot move in the losing state.
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

        assertThat(newSquare).isEqualTo(square);
        // Check if the player cannot move when losing.
    }

    private Game getGame() {
        return launcher.getGame();
    }
}
