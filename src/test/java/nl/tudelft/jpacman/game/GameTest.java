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
        launcher.withMapFile("/smallMap.txt");
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
        launcher.withMapFile("/SmallMap.txt");
        launcher.launch();
        getGame().start();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.WEST);
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * Another test case for <(ready to start), (start, meet ghost)> pair.
     * In the condition that a ghost catches the player.
     */
    @Test
    public void gcpLoseTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        getGame().start();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        List<Unit> units = player.getSquare().getSquareAt(Direction.WEST).getOccupants();
        assertThat(units.get(0) instanceof Ghost).isTrue();
        Ghost ghost = (Ghost) units.get(0);

        getGame().getLevel().move(ghost, Direction.EAST);
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * A test for <(ready to start), (start, stop, start)> pair.
     * The default map is used.
     */
    @Test
    public void resumeTest() {
        launcher.launch();
        getGame().start();
        getGame().stop();
        assertThat(getGame().isInProgress()).isFalse();

        getGame().start();

        assertThat(getGame().isInProgress()).isTrue();
    }

    /**
     * A test case for <(ready to start), (start, pellets left and player is alive)> pair.
     * This is a test for regular movement that doesn't lead to a
     * winning or losing state.
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
     * A test case for <(ready for playing), (pause)> pair.
     */
    @Test
    public void readyPauseTest() {
        launcher.launch();
        assertThat(getGame().isInProgress()).isFalse();

        getGame().stop();
        assertThat(getGame().isInProgress()).isFalse();

    }

    /**
     * We combine events with player movement in the (ready to start) state.
     * The player cannot move in the (ready to start) state and
     * it cannot consume, collide with a ghost, etc.
     * A test case for <(ready to start), (move)> pair.
     */
    @Test
    public void readyMoveTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);

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
     * Another test case for <(ready to start), (meet ghost)> pair.
     * This is to test the ghost cannot move in the ready to start state.
     */
    @Test
    public void readyGhostCatchPlayerTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        List<Unit> units = player.getSquare().getSquareAt(Direction.WEST).getOccupants();
        assert (units.get(0) instanceof Ghost);
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
     * A test case for <(playing), (start)> pair.
     * The default map is used.
     * This test if to test the game is not restarted after
     * pressing start button when playing.
     */
    @Test
    public void playingStartTest() {
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();

        getGame().move(player, Direction.EAST);

        Square square = player.getSquare();
        int score = player.getScore();
        assertThat(getGame().isInProgress()).isTrue();
        // Check if the game is in playing state.

        getGame().start();
        assertThat(player.getSquare()).isEqualTo(square);
        assertThat(player.getScore()).isEqualTo(score);
        assertThat(getGame().isInProgress()).isTrue();
        // Check if the state of the game remain the same
        // and the state of the player doesn't change.
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
        launcher.withMapFile("/smallMap.txt");
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
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        getGame().start();
        getGame().stop();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        List<Unit> units = player.getSquare().getSquareAt(Direction.WEST).getOccupants();
        assert (units.get(0) instanceof Ghost);
        Ghost ghost = (Ghost) units.get(0);

        Square square = ghost.getSquare();
        getGame().getLevel().move(ghost, Direction.EAST);
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
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();

        getGame().move(player, Direction.EAST);
        // This leads to the winning state as tested in winTest().

        getGame().start();
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        // Check if the game cannot be started again when winning,
        // and the state of the game remain the same.
    }

    /**
     * A test case for <(winning), (pause)> pair.
     */
    @Test
    public void winningPauseTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        // This leads to the winning state as tested in winTest().


        getGame().stop();

        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        // Check if the state doesn't changed.
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
        launcher.withMapFile("/smallMap.txt");
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
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        getGame().start();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.EAST);
        // This leads to the winning state as tested in winTest().

        List<Unit> units = player.getSquare().getSquareAt(Direction.EAST).getOccupants();
        assert (!units.isEmpty());
        assert (units.get(0) instanceof Ghost);
        Ghost ghost = (Ghost) units.get(0);

        Square square = ghost.getSquare();
        getGame().getLevel().move(ghost, Direction.WEST);
        Square newSquare = ghost.getSquare();

        assertThat(square).isEqualTo(newSquare);
        // Check if the ghost cannot move.

        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().isInProgress()).isFalse();
        // Check the state of the game didn't change.
    }

    /**
     * A test case for <(losing), (start)> pair.
     */
    @Test
    public void losingStartTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.WEST);

        // This leads to the losing state as tested in loseTest().

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
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.WEST);
        // This leads to the losing state as tested in loseTest().

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
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.WEST);
        // This leads to the losing state as tested in loseTest().

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
