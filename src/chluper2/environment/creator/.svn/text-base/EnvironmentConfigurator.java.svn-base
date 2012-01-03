/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment.creator;

/**
 * Klasa opowiedzialna za przechowywanie konfiguracji srodowiska
 * @author damian
 */
public class EnvironmentConfigurator {

	// ilosc rzedow z polkami
	private int bookshefRowNumber = 2;
	// ilosc kolumn z polkami
	private int bookshelfColumnNumber = 7;
	// ilosc polek w rzedzie
	private int bookshelfInRow = 5;
	// ilosc biurek
	private int deskNumber;
	// ilosc robotow
	private int robotNumber;
	// maksymalna ilosc ksiazek na biurku
	private int deskBooksLimit = 5;
	// ilosc miejsc w kieszeni robota
	private int robotCacheSize = 1;
	// ilosc tytulow ksiazek
	private int titleNumber = 1000;
	// maksymalny czas obslugi studenta
	private int maxServiceTime = 1000;
	// maksymalny czas akcji robota
	private int maxActionTime = 300;

	/**
	 * Tworzy domyslna konfiguracje srodowiska
	 * @param deskNumber ilosc biurek
	 * @param robotNumber ilosc robotow
	 */
	public EnvironmentConfigurator(int deskNumber, int robotNumber) {
		this.deskNumber = deskNumber;
		this.robotNumber = robotNumber;
	}

	/**
	 * Zwraca ilosc rzedow z polkami
	 * @return
	 */
	public int getBookshefRowNumber() {
		return bookshefRowNumber;
	}
/**
	 * Ustawia ilosc rzedow z polkami
	 * @return
	 */
	public void setBookshefRowNumber(int bookshefRowNumber) {
		this.bookshefRowNumber = bookshefRowNumber;
	}

	/**
	 * Zwraca ilosc kolumn z polkami
	 * @return
	 */
	public int getBookshelfColumnNumber() {
		return bookshelfColumnNumber;
	}

	/**
	 * Ustawia ilosc kolumn z polkami
	 * @return
	 */
	public void setBookshelfColumnNumber(int bookshelfColumnNumber) {
		this.bookshelfColumnNumber = bookshelfColumnNumber;
	}

	/**
	 * Zwraca ilosc polek w rzedzie
	 * @return
	 */
	public int getBookshelfInRow() {
		return bookshelfInRow;
	}

	/**
	 * Ustawia ilosc polek w rzedzie
	 * @return
	 */
	public void setBookshelfInRow(int bookshelfInRow) {
		this.bookshelfInRow = bookshelfInRow;
	}

	/**
	 * Zwraca maksymalna ilosc ksiazek na biurku
	 * @return
	 */
	public int getDeskBooksLimit() {
		return deskBooksLimit;
	}

	/**
	 * Ustawia maksymalna ilosc ksiazek na biurku
	 * @return
	 */
	public void setDeskBooksLimit(int deskBooksLimit) {
		this.deskBooksLimit = deskBooksLimit;
	}

	/**
	 * Zwraca ilosc biurek
	 * @return
	 */
	public int getDeskNumber() {
		return deskNumber;
	}

	/**
	 * Ustawia ilosc biurek
	 * @return
	 */
	public void setDeskNumber(int deskNumber) {
		this.deskNumber = deskNumber;
	}

	/**
	 * Zwraca ilosc miejsc w kieszeni robota
	 * @return
	 */
	public int getRobotCacheSize() {
		return robotCacheSize;
	}
	/**
	 * Ustawia ilosc miejsc w kieszeni robota
	 * @param robotCacheSize
	 */
	public void setRobotCacheSize(int robotCacheSize) {
		this.robotCacheSize = robotCacheSize;
	}


	/**
	 * Zwraca ilosc robotow
	 * @return
	 */
	public int getRobotNumber() {
		return robotNumber;
	}

	/**
	 * Ustawia ilosc robotow
	 * @return
	 */
	public void setRobotNumber(int robotNumber) {
		this.robotNumber = robotNumber;
	}

	/**
	 * Zwraca ilosc tytulow ksiazek
	 * @return
	 */
	public int getTitleNumber() {
		return titleNumber;
	}

	/**
	 * Ustawia ilosc tytulow ksiazek 
	 * @return
	 */
	public void setTitleNumber(int titleNumber) {
		this.titleNumber = titleNumber;
	}

	/**
	 * Metoda zwraca maksymalny czas (ilosc krokow) akcji robota, 
	 * czyli czas od pobrania lub odlozenia ksiazki.
	 * Sluzy to do wykrywania zakleszczen.
	 * @return
	 */
	public int getMaxActionTime() {
		return maxActionTime;
	}

	/**
	 * Ustawia maksymalny czas (ilosc krokow) akcji robota,
	 * czyli czas od pobrania lub odlozenia ksiazki.
	 * Sluzy to do wykrywania zakleszczen.
	 * @param maxActionTime
	 */
	public void setMaxActionTime(int maxActionTime) {
		this.maxActionTime = maxActionTime;
	}

	/**
	 * Zwraca maksymalny czas (ilosc krokow) obslugi studenta
	 * @return
	 */
	public int getMaxServiceTime() {
		return maxServiceTime;
	}

	/**
	 * Ustawia maksymalny czas (ilosc krokow) obslugi studenta
	 * @param maxServiceTime
	 */
	public void setMaxServiceTime(int maxServiceTime) {
		this.maxServiceTime = maxServiceTime;
	}
}
