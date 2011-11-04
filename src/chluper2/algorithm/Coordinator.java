/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.algorithm;

import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.CustomMarkersManager;
import chluper2.environment.Desk;
import chluper2.environment.Environment;
import chluper2.environment.Robot;
import chluper2.environment.Student;
import chluper2.environment.Title;
import java.util.List;

/**
 * Interfejs koordynatora
 * @author damian
 */
public interface Coordinator {

	/**
	 * Metoda na poczatku symulacji
	 * @param environment srodowisko
	 * @param markersManager manager markerow - pozwala modyfikowac markery
	 */
	public void init(Environment environment, CustomMarkersManager markersManager);

	/**
	 * Metoda wywolywana na poczatku kazdego kroku
	 * @param environment srodowisko
	 * @param markersManager manager markerow - pozwala modyfikowac markery
	 */
	public void newStep(Environment environment, CustomMarkersManager markersManager);

	/**
	 * Metoda wywolywana przed spytaniem o akcje algirytmu robota
	 * @param robot robo dla ktorego bedzie podejmowana akcja
	 * @param algorithm algorytm robota
	 * @param environment srodowisko
	 */
	public void robotBeforeAction(Robot robot, Algorithm algorithm, Environment environment);

	/**
	 * Informacja o tym ze robot podniosl ksiazke z biurka
	 * @param robot robot ktory podniosl ksiazke
	 * @param desk biurko z ktorego zostala podniesiona ksiazka
	 * @param book podniesiona ksiazka
	 * @param environment srodowisko
	 */
	public void robotTakenBook(Robot robot, Desk desk, Book book, Environment environment);

	/**
	 * Informacja o tym ze robot wzial ksiazke z polki
	 * @param robot robot ktory wzial ksiazke
	 * @param bookshelf polka z ktorej zostala wzieta ksiazka
	 * @param book zabrana ksiazka
	 * @param environment srodowisko
	 */
	public void robotTakenBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment);

	/**
	 * Informacja o tym ze robot polozyl ksiazke na biurku
	 * @param robot robot ktory polozyl ksiazke
	 * @param desk biurko na ktore zostala polozona ksiazka
	 * @param book polozona ksiazka
	 * @param environment srodowisko
	 */
	public void robotPutBook(Robot robot, Desk desk, Book book, Environment environment);

	/**
	 * Informacja o tym ze robot odlozyl ksiazke na polke
	 * @param robot robot ktory odlozyl ksiazke
	 * @param bookshelf polka na ktora zostala odlozona ksiazka
	 * @param book odlozona ksiazka
	 * @param environment srodowisko
	 */
	public void robotPutBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment);

	/**
	 * Informacja o tym ze student podniosl ksiazke z biurka
	 * @param student student ktory podniosl ksiazke
	 * @param desk biurko z ktorego zostala podniesiona ksiazka
	 * @param book podniesiona ksiazka
	 * @param environment srodowisko
	 */
	public void studentTakenBook(Student student, Desk desk, Book book, Environment environment);

	/**
	 * Informacja o tym ze student polozyl ksiazke na biurku
	 * @param student student ktory polozyl ksiazke
	 * @param desk biurko na ktore zostala polozona ksiazka
	 * @param book polozona ksiazka
	 * @param environment srodowisko
	 */
	public void studentPutBook(Student student, Desk desk, Book book, Environment environment);

	/**
	 * Informacja o tym ze student polozyl liste zyczen na biurku
	 * @param student student ktory polozyl liste
	 * @param desk biurko na ktore zostala polozona lista
	 * @param wishlist lista ksiazek do wypozyczenia
	 * @param environment srodowisko
	 */
	public void studentPutWishlist(Student student, Desk desk, List<Title> wishlist, Environment environment);
}
