package nl.tudelft.jpacman.group97;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A game with one player and a single level.
 *
 * @author Shijie Zhou
 */
public class MultiLevelGame extends Game {

    /**
     * The player of this game.
     */
    private final Player player;

    /**
     * The level of this game.
     */
    private final List<Level> levels = new ArrayList<>();

    private int levelNumber = 0;
    private static final int MAX_LEVEL = 3;

    @Override
    public Level getLevel() {
        return this.levels.get(levelNumber);
    }

    @Override
    public void levelWon() {
        this.levelNumber++;

        if (levelNumber >= MAX_LEVEL) {
            levelNumber = 2;
        }
        else {
            this.levels.get(levelNumber).registerPlayer(player);
        }
        stop();
    }


    @Override
    public List<Player> getPlayers() {
        return ImmutableList.of(player);
    }

    /**
     * Create a new game for the provided levels and player.
     *
     * @param player
     *            The player.
     * @param level1
     *            The level1.
     * @param level2
     *            The level2.
     * @param level3
     *            The level3.
     */
    public MultiLevelGame(Player player, Level level1, Level level2, Level level3) {
        assert player != null;
        assert level1 != null;
        assert level2 != null;
        assert level3 != null;

        this.player = player;
        this.levels.add(level1);
        this.levels.get(0).registerPlayer(player);
        this.levels.add(level2);

        this.levels.add(level3);

    }

    /**
     *
     * @return the current level number.
     */
    public int getLevelNumber() {
        return levelNumber;
    }
}
