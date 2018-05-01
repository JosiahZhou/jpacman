<<<<<<< HEAD
package nl.tudelft.jpacman.board;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * Basic implementation of square.
 *
 * @author Chenru Lin
 */
public class BasicSquare extends Square {
    /**
     * Creates a new basic square.
     */
    BasicSquare() {
        super();
    }

    @Override
    public boolean isAccessibleTo(Unit unit) {
        return true;
    }

    @Override
    @SuppressWarnings("return.type.incompatible")
    public Sprite getSprite() {
        return null;
    }
}

=======
package nl.tudelft.jpacman.board;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * Basic implementation of square.
 *
 * @author Chenru Lin
 */
public class BasicSquare extends Square {
    /**
     * Creates a new basic square.
     */
    BasicSquare() {
        super();
    }

    @Override
    public boolean isAccessibleTo(Unit unit) {
        return true;
    }

    @Override
    @SuppressWarnings("return.type.incompatible")
    public Sprite getSprite() {
        return null;
    }
}

>>>>>>> 5fcb5b42e3292beedc995c5d03908289d45f6bec
