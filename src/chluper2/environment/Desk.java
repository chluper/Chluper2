/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

import java.util.List;

/**
 * Interfejs biurka
 * @author damian
 */
public interface Desk extends Element {

	/**
	 * Zwraca unikalna nazwe robota
	 * @return
	 */
	public String getName();

	/**
	 * Metoda zwraca ilosc ksiazek ktore moga sie jednoczesnie znajdowac na biurku
	 * @return
	 */
	public int getBooksLimit();

	/**
	 * Zwraca liste ksiazeklezocych na biurku,
	 * Mozna je podnosic i opuszczac
	 * @return
	 */
	public List<Book> getBooks();

	/**
	 * Zwraca liste ksiazek jakie nalezy dostarczyc na biurko
	 * @return lista lub null jesli takiej nie ma
	 */
	public List<Title> getWishList();

	/**
	 * Metoda zwraca polozenie miejsca gdzie powinien znajdowac sie robot,
	 * aby mogl korzystac z biurka
	 * @return
	 */
	public Position getRobotPadPosition();

	/**
	 * Metoda zwraca polozenie miejsca gdzie powinien znajdowac sie student,
	 * aby mogl korzystac z biurka
	 * @return
	 */
	public Position getStudentPadPosition();

	/**
	 * Metoda zwraca kierunek w jakim powinien byc zwrocony robot,
	 * aby mogl korzystac z biurka
	 * @return
	 */
	public Direction getCorrectRobotDirection();

	/**
	 * Metoda zwraca kierunek w jakim powinien byc zwrocony student,
	 * aby mogl korzystac z biurka
	 * @return
	 */
	public Direction getCorrectStudentDirection();

}
