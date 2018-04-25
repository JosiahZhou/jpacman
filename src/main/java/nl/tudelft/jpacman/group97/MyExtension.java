package nl.tudelft.jpacman.groupNUMBER;

import nl.tudelft.jpacman.Launcher;

/**
 * Extend the JPacman framework by subclassing relevant classes.
 * Creation of the subclass instances requires adjusting the factory,
 * and injecting the relevant instances at the right places.
 */

public class MyExtension extends Launcher {

    /**
     * Start the pacman user interface.
     *
     * @param argv Ignored
     */
    public static void main(String[] argv) {
        (new MyExtension()).launch();
    }
}
