package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.group97.MultiLevelGame;
import nl.tudelft.jpacman.group97.MultiLevelLauncher;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: Chenru Lin
 * @discription: Multilevel game tests.
 */
public class MultiLevelGameTest extends GameTest {
    private MultiLevelLauncher launcher = new MultiLevelLauncher();

    /**
     * Set up multilevel game.
     */
    @BeforeEach
    void setUp() {
        set(launcher);
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
     * Correspond to the filled entries in the transition table.
     * Pairs are of <(state), (events)> patterns.
     */

    /**
     * A test case for <(ready to start), (start, Last pellet eaten)> pair
     * in the multilevel game for the first two levels.
     * Player should enter the next level and be in the state of
     * ready to start after level won.
     */
    @Test
    @Override
    public void winTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        getGame().start(); // start
        int level = getGame().getLevelNumber();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.EAST);
        // Last pellet eaten, level won
        int newLevel = getGame().getLevelNumber();

        assertThat(player.isAlive()).isTrue();
        // Player alive
        assertThat(newLevel).isEqualTo(level + 1);
        // Player at the next level
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getGame().getLevel().remainingPellets()).isGreaterThan(0);
        // The game is ready to play
    }


    /**
     * A test case for the <(ready to play), (start, last pellet eaten)> pair
     * on the third level.
     * The game ends after level won on the third level
     * and the player cannot do anything in this state.
     * This state is the same as the winning state for the single
     * level game.
     */
    @Test
    public void thirdLevelWinTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        getGame().start(); // start from level 0
        //int level = getGame().getLevelNumber();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().move(player, Direction.EAST); // Last pellet eaten
        // This leads the player to level 1, as tested in winTest()

        getGame().start();
        getGame().move(player, Direction.EAST);
        assertThat(getGame().getLevelNumber()).isEqualTo(2);
        // This leads the player to level 2.

        getGame().start();
        getGame().move(player, Direction.EAST);
        // player win

        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        assertThat(getGame().getLevelNumber()).isEqualTo(2);
        // We remained in the same level and the game ends.
    }

    /**
     * A test case for <(ready to start), (start)> pair for
     * the first 2 levels, in which player can enter the
     * next level after winning and can press start to start
     * the next level.
     */
    @Test
    public void startNextLevelTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        // Level won and the player is at the next level
        // as tested winTest()

        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isGreaterThan(0);
        // Check if the next level can be start.
    }

    /**
     * Sneak path test suite.
     * Correspond to the empty entries in the transition table.
     */

    /**
     * To test press start button in the winning state
     * won't do anything.
     */
    @Test
    @Override
    public void winningStartTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().start();
        getGame().move(player, Direction.EAST);

        // This leads to the final winning state as tested in thirdLevelWinTest().

        getGame().start();
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        // Check if the game cannot be restarted in the winning state.
    }


    /**
     * A test case for <(winning), (meet ghost)> pair.
     * This is to test the ghost cannot move in the ready to start state.
     */
    @Test
    @Override
    public void winGhostCatchPlayerTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().start();
        getGame().move(player, Direction.EAST);
        // This leads to the winning state as tested in thirdLevelWinTest().

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
     * A test case for <(winning), (pause)> pair.
     */
    @Test
    @Override
    public void winningPauseTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().start();
        getGame().move(player, Direction.EAST);
        getGame().start();
        getGame().move(player, Direction.EAST);
        // This leads to the final winning state as tested in thirdLevelWinTest().

        getGame().stop();

        assertThat(getGame().isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
        // Check if the state doesn't changed.
    }



    /**
     * To test if the player cannot move
     * when the player just enter the next level and hasn't
     * press start.
     */
    @Test
    public void nextLevelMoveTest() {
        launcher.withMapFile("/smallMap.txt");
        launcher.launch();
        List<Player> players = getGame().getPlayers();
        Player player = players.get(0);
        getGame().start();
        getGame().move(player, Direction.EAST);
        // Now the player is at level 1 as tested in winTest()

        Square square = player.getSquare();
        int score = player.getScore();
        getGame().move(player, Direction.EAST);
        Square newSquare = player.getSquare();

        assertThat(newSquare).isEqualTo(square);
        assertThat(player.getScore()).isEqualTo(score);
        assertThat(getGame().isInProgress()).isFalse();
    }



    private MultiLevelGame getGame() {
        return launcher.getGame();
    }

}
