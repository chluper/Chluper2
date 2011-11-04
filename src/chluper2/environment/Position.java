/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

/**
 * Klasa polozenia elementu w srodowisku
 * @author damian
 */
public final class Position {

	// skladowe
	private final int x;
	private final int y;

	/**
	 * Konstruktor polozenia elementu
	 * @param x skladowa pozioma
	 * @param y skladowa pionowa
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Zwraca skladowa pozioma
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Zwraca skladowa pionowa
	 * @return
	 */
	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Position other = (Position) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 79 * hash + this.x;
		hash = 79 * hash + this.y;
		return hash;
	}

	@Override
	public String toString() {
		return "[" + x + ":" + y + "]";
	}

	/**
	 * Metoda zwraca polozenie po przebyciu ilosci krokow w okreslonym kierunku
	 * @param direction kierunek ruchu
	 * @param steps ilosc krokow
	 * @return nowe polozenie
	 */
	public Position getPositionAfterMove(Direction direction, int steps) {
		switch (direction) {
			case NORTH:
				return new Position(x, y - steps);
			case SOUTH:
				return new Position(x, y + steps);
			case WEST:
				return new Position(x - steps, y);
			case EAST:
				return new Position(x + steps, y);
		}
		// to sie nie zdarzy, bo wszystkie kierunki zostaly obsluzone
		return null;
	}

	/**
	 * Jsli dana pozycja jest w okreslonym jednym kierunku to ten kierunek jest zwracany
	 * @param position
	 * @return kierunek lub null
	 */
	public Direction getDirctionToPosition(Position position) {
		// jesli to to samo miejsce
		if (position.equals(this)) {
			return null;
		}
		// sprawdzanie poziome
		if (position.y == y) {
			if (position.x > x){
				return Direction.EAST;
			} else {
				return Direction.WEST;
			}
		}
		// sprawdzanie pionowe
		if (position.x == x) {
			if (position.y > y){
				return Direction.SOUTH;
			} else {
				return Direction.NORTH;
			}
		}
		// gdzies po skosie
		return null;
	}

}
