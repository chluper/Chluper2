/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

/**
 * Interfejs kazdego elementu srodowiska
 * @author damian
 */
public interface Element {

	/**
	 * Metoda zwraca typ elementu
	 * @return
	 */
	public ElementType getElementType();

	/**
	 * Metoda zwraca polozenie elementu
	 * @return
	 */
	public Position getPosition();

	/**
	 * Metoda zwraca kierunek w ktorym jest zwrocony element
	 * @return
	 */
	public Direction getDirection();

}
