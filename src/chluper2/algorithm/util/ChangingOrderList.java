/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.algorithm.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Klasa dekorujaca kolekcje (liste),
 * cylkicznie zmieniajaca kolejnosc elementow przy kazdym przegladaniu
 * @author damian
 */
public class ChangingOrderList<T> implements Iterable<T> {

	// lista z elementami
	private final LinkedList<T> list;

	/**
	 * Konstruktor kolekcji cyklicznie zmieniajacej kolejnosc
	 * @param elements elementy przechowywane w kolekcji
	 */
	public ChangingOrderList(Collection<T> elements) {
		this.list = new LinkedList<T>(elements);
	}

	/**
	 * Zmienia kolejnosc i zwraca iterator
	 * @return
	 */
	public Iterator<T> iterator() {
		if (!list.isEmpty()) {
			list.addLast(list.removeFirst());
		}
		return list.iterator();
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
