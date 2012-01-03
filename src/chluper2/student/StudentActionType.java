/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.student;

/**
 *
 * @author damian
 */
public enum StudentActionType {

	/**
	 * Oczekiwanie jednej tury
	 */
	WAIT,
	/**
	 * Przesuniecie sie o jedno pole do przodu
	 */
	FORWARD,
	/**
	 * Skret w lewo lub prawo
	 * <br> arg0 (Turn) - lewo lub prawo, lub
	 * <br> arg0 (Direction) - obrot w okreslonym kierunku - moze zajac wiecej niz 1 cykl
	 */
	TURN,
	/**
	 * Odkladanie ksiazki, student musi byc zwrocony w kierunku bourka,
	 * na ktora ma polozyc ksiazke. Ksiazka musi sie znajdowac w kieszeni studenta
	 * <br> arg0 (Book) - ksiazka do odlozenia
	 */
	PUT,
	/**
	 * Pobieranie ksiazki z biurka i umieszczanie jej w kieszeni studenta.
	 * Student musi byc zwrucony w kierunku \biurka.
	 * <br> arg0 (Book) - ksiazka do pobrania z biurka, lub
	 */
	TAKE,
	/**
	 * Logowanie sie i zamawianie ksiazek
	 */
	LOGIN,
	/**
	 * Odchodzenie od biurka
	 */
	LOGOUT,
	/**
	 * Wyjscie studenta z biblioteki, musi stac na markerze wyjscia
	 */
	LEAVE(),
	/**
	 * Przemieszczenie sie o jedno pole w wybranym kierunku.
	 * Rozkaz moze byc wykonany w kilku cyklach jesli wymaga obracania robota
	 * <br> arg0 (Direction) - kierunek w ktorym ma sie przemiescic student
	 */
	MOVE,
	/**
	 * Przemieszczenie sie do okreslonej lokalizacji.
	 * Rozkaz moze byc wykonany w kilku cyklach. Jesli nie bedzie mozna sie poruszyc,
	 * lub nie bedzie mozna wyznaczyc sciezki, to zostanie wykonana akcja WAIT.
	 * <br> arg0 (Position) - polozenie do ktorego ma dazyc student
	 */
	GO_TO,

}
