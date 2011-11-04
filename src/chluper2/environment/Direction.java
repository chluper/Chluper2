/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment;

/**
 * Kierunki w ktorych moze byc obrocony element
 * @author damian
 */
public enum Direction {

	NORTH,
	SOUTH,
	WEST,
	EAST;

	/**
	 * Metoda zwraca kierunek jaki uzyskujemy w wyniku obrotu
	 * <br> Np jesli objekt jest zwrocony na polnoc w wyniku obrotu w lewo
	 * bedzie zwrocony na zachod
	 * @param turn lewo lub prawo
	 * @return kierunek jaki usyskujemy w wyniku obrotu
	 */
	public Direction getDirectionAfterTurn(Turn turn) {
		return turn.getDirectionAfterTurn(this);
	}

	/**
	 * Metoda zwraca polozenie po przebyciu ilosci krokow w okreslonym kierunku
	 * @param original polozenie poczatkowe
	 * @param steps ilosc krokow
	 * @return nowe polozenie
	 */
	public Position getPositionAfterMove(Position original, int steps) {
		return original.getPositionAfterMove(this, steps);
	}

	/**
	 * Zwraca odwrotny kierunek
	 * @return
	 */
	public Direction getOpposite() {
		switch (this) {
			case NORTH:
				return SOUTH;
			case SOUTH:
				return NORTH;
			case WEST:
				return EAST;
			case EAST:
				return WEST;
		}
		// to sie nie zdarzy, bo wszystkie kierunki zostaly obsluzone
		return null;
	}
}
