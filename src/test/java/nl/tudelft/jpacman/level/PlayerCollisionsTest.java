package nl.tudelft.jpacman.level;


import org.junit.jupiter.api.BeforeEach;


/**
 * @author: Chenru Lin
 * @discription:
 */
public class PlayerCollisionsTest extends CollisionMapTest {

    @Override
    void set() {
        CollisionMap camp = new PlayerCollisions();
        setCamp(camp);
    }

    /**
     * set camp an instance of PlayerCollisions().
     */
    @BeforeEach
    void setUp() {
        set();
    }







}
