/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.algorithm;

/**
 * Rozkazy jakie
 * @author damian
 */
public enum ActionType {

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
	 * Odkladanie ksiazki, robot musi byc zwrocony w kierunku bourka/polki,
	 * na ktora ma polozyc ksiazke. Ksiazka musi sie znajdowac w kieszeni robota
	 * <br> arg0 (Book) - ksiazka do odlozenia
	 */
	PUT,
	/**
	 * Pobieranie ksiazki z biurka/polki i umieszczanie jej w kieszeni robota.
	 * Robot musi byc zwrucony w kierunku polki/biurka.
	 * <br> arg0 (Book) - ksiazka do pobrania z biurka, lub
	 * <br> arg0 (Title) - tytul ksiazki do pobrania z polki
	 */
	TAKE,
	/**
	 * Przemieszczenie sie o jedno pole w wybranym kierunku.
	 * Rozkaz moze byc wykonany w kilku cyklach jesli wymaga obracania robota
	 * <br> arg0 (Direction) - kierunek w ktorym ma sie przemiescic robot
	 */
	MOVE,
	/**
	 * Przemieszczenie sie do okreslonej lokalizacji.
	 * Rozkaz moze byc wykonany w kilku cyklach. Jesli nie bedzie mozna sie poruszyc,
	 * lub nie bedzie mozna wyznaczyc sciezki, to zostanie wykonana akcja WAIT.
	 * <br> arg0 (Position) - polozenie do ktorego ma dazyc robot
	 */
	GO_TO,
	/**
	 * Dostarczenie okreslonej ksiazki do biurka lub polki.
	 * Rozkaz moze byc wykonany w kilku cyklach.
	 * <br> arg0 (Book) - ksiaka do dostarczenia
	 * <br> arg1 (Desk) - biurko, lub
	 * <br> arg1 (Bookshelf) - polka
	 */
	DELIVER_BOOK,
	/**
	 * Odbiernie okreslonej ksiazki z biurka lub polki.
	 * Rozkaz moze byc wykonany w kilku cyklach.
	 * <br> arg0 (Book) - ksiaka do odebrania z biurka, lub
	 * <br> arg0 (Title) - ksiaka do odebrania z polki
	 * <br> arg1 (Desk) - biurko, lub
	 * <br> arg1 (Bookshelf) - polka
	 */
	GET_BOOK,

}
