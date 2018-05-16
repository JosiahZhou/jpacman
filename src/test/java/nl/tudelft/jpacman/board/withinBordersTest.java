package nl.tudelft.jpacman.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author: Chenru Lin
 * @date 2018/5/16 11:18
 * @discription: Boundary test
 */
public class withinBordersTest {


    private static final int WIDTH = 2;
    private static final int HEIGHT = 2;


    private final Square[][] grid = {
            { mock(Square.class), mock(Square.class) },
            { mock(Square.class), mock(Square.class) },
    };
    private final Board board = new Board(grid);


    /**
     * Verify that the square is within board(on-point).
     * @param x Horizontal coordinate of relevant cell.
     * @param y Vertical coordinate of relevant cell.
     */
    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0, 1",
            "1, 0",
            "1, 1"
    })
    void testWithInBoard(int x, int y) {
        assertThat(board.withinBorders(x, y)).isTrue();
    }
    /**
     * Verify that the square is just out of board(off-point).
     * @param x Horizontal coordinate of relevant cell.
     * @param y Vertical coordinate of relevant cell.
     */
    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "0, -1",
            "2, 0",
            "0, 2"
    })
    void testOutOfBoard(int x, int y) {
        assertThat(board.withinBorders(x, y)).isFalse();
    }
}
