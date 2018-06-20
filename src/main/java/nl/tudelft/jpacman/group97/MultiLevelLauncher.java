package nl.tudelft.jpacman.group97;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Creates and launches the JPacMan UI for Multi-Level Game.
 *
 * @author Shijie Zhou
 */
public class MultiLevelLauncher extends Launcher {

    private MultiLevelGame multiGame;
    private PacManSprites sprites = new PacManSprites();
    private final PlayerFactory playerFactory = new PlayerFactory(sprites);

    @Override
    public Game makeGame() {

        Player player = playerFactory.createPacMan();
        Level level1 = makeLevel();
        Level level2 = makeLevel();
        Level level3 = makeLevel();
        multiGame = new MultiLevelGame(player, level1, level2, level3);
        return multiGame;
    }

    @Override
    public MultiLevelGame getGame() {
        return multiGame;
    }
}
