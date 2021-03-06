package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: Chenru Lin
 * @discription:
 */
public class RestartTest {
    private Launcher launcher;
    /**
     ** Start a launcher, which can display the user interface.
     */
    @BeforeEach
    public void before() {
        launcher = new Launcher();
    }

    /**
     ** Close the user interface.
     */
    @AfterEach
    public void after() {
        launcher.dispose();
    }
    /**
     * The simplest test that just starts the
     * game and checks it is indeed in progress.
     */
    @Test
    public void gameIsRunning() {
        launcher.launch();

        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
    }

    /**
     * Test whether the game can start again when the start button
     * is pressed and the game given to be paused.
     */
    @Test
    public void isResumed() {
        launcher.launch();
        getGame().start();
        getGame().stop();
        // The game is suspended as tested in Suspension Test.

        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
        // The game is start again.
    }
    private Game getGame() {
        return launcher.getGame();
    }
}

