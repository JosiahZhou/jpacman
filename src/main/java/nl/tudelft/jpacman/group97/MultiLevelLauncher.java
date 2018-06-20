package nl.tudelft.jpacman.group97;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

import java.io.IOException;

/**
 * Creates and launches the JPacMan UI for Multi-Level Game.
 *
 * @author Shijie Zhou
 */
public class MultiLevelLauncher extends Launcher {

    private MultiLevelGame multiGame;
    private PacManSprites sprites = new PacManSprites();
    private final PlayerFactory playerFactory = new PlayerFactory(sprites);
    private String levelMap1 = DEFAULT_MAP;
    private String levelMap2 = DEFAULT_MAP;
    private String levelMap3 = DEFAULT_MAP;

    @Override
    public MultiLevelGame makeGame() {

        Player player = playerFactory.createPacMan();
        Level level1 = makeLevel(levelMap1);
        Level level2 = makeLevel(levelMap2);
        Level level3 = makeLevel(levelMap3);
        multiGame = new MultiLevelGame(player, level1, level2, level3);
        return multiGame;
    }

    @Override
    public Launcher withMapFile(String fileName) {
        levelMap1 = fileName;
        levelMap2 = fileName;
        levelMap3 = fileName;
        return this;
    }

    /**
     *
     * @param fileName1 map1
     * @param fileName2 map2
     * @param fileName3 map3
     * @return the launcher with different maps
     */
    public Launcher withMapFile(String fileName1, String fileName2, String fileName3) {
        levelMap1 = fileName1;
        levelMap2 = fileName2;
        levelMap3 = fileName3;
        return this;
    }

    private Level makeLevel(String s) {
        try {
            return getMapParser().parseMap(s);
        } catch (IOException e) {
            throw new PacmanConfigurationException(
                    "Unable to create level, name = " + getLevelMap(), e);
        }
    }

    @Override
    public MultiLevelGame getGame() {
        return multiGame;
    }
}
