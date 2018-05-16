package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.Ghost;

import java.util.List;

/**
 * A test helper utility for writing the ghost unit tests.
 * A useful method for retrieving ghosts from your map would be:
 * findUnitInBoard in the Navigation class.
 */
public final class GhostMapParser extends MapParser {
    private final GhostFactory ghostFactory;

    /**
     * Creates a new enhanced map parser.
     * This map parser allows users to specify which ghost should be placed
     * at an exact location.
     *
     * @param levelFactory The factory providing the NPC objects and the level.
     * @param boardFactory The factory to create board elements.
     * @param ghostFactory the factory to create the ghosts.
     */
    public GhostMapParser(LevelFactory levelFactory, BoardFactory boardFactory,
                           GhostFactory ghostFactory) {
        super(levelFactory, boardFactory);
        this.ghostFactory = ghostFactory;
    }

    //This method only supports clyde for now
    //You should add extra cases for ghosts you need.
    @Override
    protected void addSquare(Square[][] grid, List<Ghost> ghosts,
                             List<Square> startPositions, int x, int y, char c) {
        switch (c) {
            case 'C':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createClyde());
                break;
            case 'I':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createInky());
                break;
            case 'B':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createBlinky());
                break;
            default:
                super.addSquare(grid, ghosts, startPositions, x, y, c);
        }
    }

    /**
     *  Finds a subtype of Unit in a level.
     *  This method is very useful for finding the ghosts in the parsed map.
     *  
     * @param clazz the type to search for.
     * @param level the level to search in.
     * @param <T> the return type, same as the type in clazz.
     *           
     * @return the first unit found of type clazz, or null.
     */
    public <T extends Unit> T findUnit(Class<T> clazz, Level level) {
        final Board board = level.getBoard();
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                final T ghost = Navigation.findUnit(clazz, board.squareAt(x, y));
                if (ghost != null) {
                    return ghost;
                }
            }
        }

        return null;
    }

    /**
     *
     * @param ghosts the list of ghosts
     * @param ghost the ghost want to add to the list
     * @return ghostSquare
     */
    protected Square makeGhostSquare(List<Ghost> ghosts, Ghost ghost) {
        Square ghostSquare = this.boardFactory.createGround();
        ghosts.add(ghost);
        ghost.occupy(ghostSquare);
        return ghostSquare;
    }
}
