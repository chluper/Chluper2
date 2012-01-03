/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

import java.util.Set;

/**
 * Interfejs polki z ksiazkami
 * @author damian
 */
public interface Bookshelf extends Element {

	/**
	 * Zwraca unikalna nazwe polki
	 * @return nazwa
	 */
	public String getName();

	/**
	 * Zwraca tutoly ksiazek jakie sie znajduja na tej polce
	 * @return
	 */
	public Set<Title> getValidTitles();

	/**
	 * Metoda okresla czy podany tytul jest poprawny dla tej polki
	 * @param title tytul
	 * @return
	 */
	public boolean isValidTitle(Title title);

	/**
	 * Metoda zwraca polozenie miejsca gdzie powinien znajdowac sie robot,
	 * aby mogl korzystac z polki
	 * @return
	 */
	public Position getRobotPadPosition();

	/**
	 * Metoda zwraca kierunek w jakim powinien byc zwrocony robot,
	 * aby mogl korzystac z polki
	 * @return
	 */
	public Direction getCorrectRobotDirection();
}
