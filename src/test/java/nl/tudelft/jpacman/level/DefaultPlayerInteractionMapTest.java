package nl.tudelft.jpacman.level;


import org.junit.jupiter.api.BeforeEach;


/**
 * @author: Chenru Lin
 * @discription:
 */
public class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    @Override
    void set() {
        CollisionMap camp = new DefaultPlayerInteractionMap();
        setCamp(camp);
    }

    /**
     * Set camp to be an instance of DefaultPlayerInteractionMap().
     */
    @BeforeEach
    void setUp1() {
        set();
    }

}
