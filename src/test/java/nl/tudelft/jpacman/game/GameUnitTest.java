package nl.tudelft.jpacman.game;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * @author: Chenru Lin
 * @discription:
 */
public class GameUnitTest {
    private Game singlePlayerGame;
    private final Level level = mock(Level.class);
    private final Player player = mock(Player.class);
    private final PlayerFactory playerFactory = mock(PlayerFactory.class);

    private final GameFactory gameFactory = new GameFactory(playerFactory);

    /**
     *Set up the singlePlayerGame.
     */
    @BeforeEach
    void setUp() {
        when(playerFactory.createPacMan()).thenReturn(player);
        singlePlayerGame = gameFactory.createSinglePlayerGame(level);
    }

    /**
     * Branch with false -> true.
     */
    @Test
    void gameStart() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(2);
        singlePlayerGame.start();
        assertThat(singlePlayerGame.isInProgress()).isTrue();
    }

    /**
     * Branch with true -> true.
     */
    @Test
    void inProgress() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(2);
        singlePlayerGame.start(); //change isInProgress to true

        singlePlayerGame.start();
        assertThat(singlePlayerGame.isInProgress()).isTrue();
    }

    /**
     * Branch with false -> false.
     */
    @Test
    void gameOver() {
        when(level.isAnyPlayerAlive()).thenReturn(false);
        when(level.remainingPellets()).thenReturn(2);
        singlePlayerGame.start();
        assertThat(singlePlayerGame.isInProgress()).isFalse();
    }



}
