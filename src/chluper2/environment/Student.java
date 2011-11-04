/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

import java.util.List;

/**
 * Interfejs studenta
 * @author damian
 */
public interface Student extends Element {

	/**
	 * Zwraca unikalne nazwisko studenta
	 * @return
	 */
	public String getName();

	/**
	 * Metoda zrwaca liste ksiazek (w plecaku)
	 * @return lista ksiazek w plecaku
	 */
	public List<Book> getBag();

	/**
	 * Metoda zwraca liste tytolow do wypozyczenia
	 * @return lista zyczen
	 */
	public List<Title> getWishList();

	/**
	 * Metoda okresla czy student jest usatysfakcjonowany,
	 * tzn, czy nie ma nic do oddania i ma w plecaku wszystko to co chcial wypozyczyc
	 * @return true jesli student jest usatysfakcjonowany
	 */
	public boolean isSatisfied();

	/**
	 * Metoda zwraca czas (ilosc krokow) obslugi studenta
	 * @return 
	 */
	public int getServiceTime();
}
