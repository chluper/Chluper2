/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

import java.util.List;

/**
 * Interfejs reobta
 * @author damian
 */
public interface Robot extends Element {

	/**
	 * Zwraca unikalna nazwe robota
	 * @return
	 */
	public String getName();

	/**
	 * Zwraca wielkosc (ilosc miejsc) w kieszeni robota
	 * @return
	 */
	public int getCacheSize();

	/**
	 * Metoda zwraca zawartosc kieszeni robota
	 * @return
	 */
	public List<Book> getCache();

	/**
	 * Metoda zwraca czas wykonywania aktualnyej akcji, 
	 * czyli czas od ostatniego odlozenia lub pobrania ksiazki.
	 * Czas ten sluzy do wykrywania zakleszczen
	 * @return
	 */
	public int getActionTime();
}
