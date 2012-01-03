/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

/**
 * Obrot elementu
 * @author damian
 */
public enum Turn {
	RIGNT,
	LEFT;

	/**
	 * Metoda zwraca kierunek jaki uzyskujemy w wyniku obrotu
	 * <br> Np jesli objekt jest zwrocony na polnoc w wyniku obrotu w lewo
	 * bedzie zwrocony na zachod
	 * @param original poczatkowy kieunek
	 * @return kierunek jaki usyskujemy w wyniku obrotu
	 */
	public Direction getDirectionAfterTurn(Direction original) {
		switch (original) {
			case NORTH:
				return (this.equals(LEFT) ? Direction.WEST : Direction.EAST);
			case SOUTH:
				return (this.equals(LEFT) ? Direction.EAST : Direction.WEST);
			case WEST:
				return (this.equals(LEFT) ? Direction.SOUTH : Direction.NORTH);
			case EAST:
				return (this.equals(LEFT) ? Direction.NORTH : Direction.SOUTH);
		}
		// to sie nie zdarzy, bo wszystkie kierunki zostaly obsluzone
		return null;
	}

}
