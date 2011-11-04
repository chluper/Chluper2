/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment;

/**
 * Typy elementow
 * @author damian
 */
public enum ElementType {

	/**
	 * Polka na ksiazki
	 */
	BOOKSHELF(true),
	DESK(true),
	WALL(true),
	ROBOT(true),
	STUDENT(true),
	MARKER(false);
	
	// element kolizyjny
	private boolean collisional;

	private ElementType(boolean collisional) {
		this.collisional = collisional;
	}

	/**
	 * Okresla czy elemet jest kolizyjny
	 * @return
	 */
	public boolean isCollisional() {
		return collisional;
	}
}
