/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment;

import java.util.List;

/**
 * Srodowisko
 * @author damian
 */
public interface Environment {

	/**
	 * Zwraca szerokosc srodowiska
	 * @return
	 */
	public int getWidth();

	/**
	 * Zwraca wysokosc srodowiska
	 * @return
	 */
	public int getHeight();

	/**
	 * Zwraca wszystkie elementy srodowiska
	 * @return
	 */
	public List<Element> getAllElements();

	/**
	 * Zwraca elementy w okreslonym polozeniu
	 * @param position polozenie
	 * @return
	 */
	public List<Element> getElementsAt(Position position);

	/**
	 * Zwraca polki
	 * @return
	 */
	public List<Bookshelf> getBookshelfs();

	/**
	 * Zwraca polke o podanej nazwie
	 * @param name nazwa
	 * @return polka lub null jesli takiej nie ma
	 */
	public Bookshelf getBookshelfByName(String name);

	/**
	 * Zwraca polke na ktorej sa ksiazki o podanym tytule
	 * @param title tytul
	 * @return polka lub null jesli takiej nie ma
	 */
	public Bookshelf getBookshelfByTitle(Title title);

	/**
	 * Zwraca biurka
	 * @return
	 */
	public List<Desk> getDesks();

	/**
	 * Zwraca polke o podanej nazwie
	 * @param name nazwa
	 * @return polka lub null jesli takiej nie ma
	 */
	public Desk getDeskByName(String name);

	/**
	 * Zwraca sciany
	 * @return
	 */
	public List<Wall> getWalls();

	/**
	 * Zwraca roboty
	 * @return
	 */
	public List<Robot> getRobots();

	/**
	 * Zwraca robota o podanej nazwie
	 * @param name nazwa
	 * @return robot lub null jesli takiej nie ma
	 */
	public Robot getRobotByName(String name);

	/**
	 * Zwraca studentow
	 * @return
	 */
	public List<Student> getStudents();

	/**
	 * Zwraca studenta o podanej nazwie
	 * @param name nazwa
	 * @return student lub null jesli takiej nie ma
	 */
	public Student getStudentByName(String name);

	/**
	 * Zwraca znaczniki
	 * @return
	 */
	public List<Marker> getMarkers();

	/**
	 * Zwraca znaczniki w okreslonym polozeniu
	 * @param position polozenie
	 * @return
	 */
	public List<Marker> getMarkersAt(Position position);

	/**
	 * Zwraca znaczniki okreslonego typu
	 * @param type
	 * @return
	 */
	public List<Marker> getMarkersByType(String type);

	/**
	 * Metoda zwraca takt symulacji
	 * @return
	 */
	public int getSimulationStep();


	/**
	 * Zwraca maksymalny czas (ilosc krokow) obslugi studenta
	 * @return
	 */
	public int getMaxServiceTime();

	/**
	 * Metoda zwraca maksymalny czas (ilosc krokow) akcji robota,
	 * czyli czas od pobrania lub odlozenia ksiazki.
	 * Sluzy to do wykrywania zakleszczen.
	 * @return
	 */
	public int getMaxActionTime();

	/**
	 * Zwraca ilosc obsluzonych studentow
	 * @return
	 */
	public int getServicedStudentNumber();

	/**
	 * Zwraca sredni czas obslugi studenta
	 * @return
	 */
	public double getAverageStudentServiceTime();

	/**
	 * Zwraca maksymalny czas obslugi studenta
	 * @return
	 */
	public int getMaximalStudentServiceTime();
}
