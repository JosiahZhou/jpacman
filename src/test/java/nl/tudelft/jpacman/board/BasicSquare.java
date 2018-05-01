package nl.tudelft.jpacman.board;
import nl.tudelft.jpacman.sprite.Sprite;

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

