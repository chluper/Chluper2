/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment.concrete;

import java.util.ArrayList;
import java.util.List;

/**
 * Interfejs klas ktore pozwalaja sie kopiowac,
 * Dziala podobnie do Cloneable, z tym ze nie rzuca wyjatkiem,
 * a metoda do kopiowania jest publiczna
 * @author damian
 */
public interface Copyable<T> {

	/**
	 * Zwraca kopie obiektu
	 * @return
	 */
	public T copy();

}
