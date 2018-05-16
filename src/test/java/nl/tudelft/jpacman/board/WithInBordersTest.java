package nl.tudelft.jpacman.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author: Chenru Lin
 * @discription: Boundary test
 */
public class WithInBordersTest {


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
            "-1, 1",
            "0, -1",
            "2, 0",
            "1, 2"
    })
    void testOutOfBoard(int x, int y) {
        assertThat(board.withinBorders(x, y)).isFalse();
    }
}
