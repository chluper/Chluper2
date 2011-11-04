/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.algorithm.util;

import chluper2.environment.Bookshelf;
import chluper2.environment.Desk;
import chluper2.environment.Title;

/**
 * Klasa zadania dla robota.
 * Zadanie polega na transporcie ksiazki o podanym tytule
 * @author damian
 */
public class Task {

	// zrodlo
	private final Desk sourceDesk;
	private final Bookshelf sourceBookshelf;
	// cel
	private final Desk targetDesk;
	private final Bookshelf targetBookshelf;
	// tytul ksiazki
	private final Title title;

	/**
	 * Tworzenie zadania transportu ksiazki z biurka na polke
	 * @param sourceDesk biurko
	 * @param targetBookshelf polka
	 * @param title tytul ksiazki do przewiezienia
	 */
	public Task(Desk sourceDesk, Bookshelf targetBookshelf, Title title) {
		this.sourceDesk = sourceDesk;
		this.sourceBookshelf = null;
		this.targetDesk = null;
		this.targetBookshelf = targetBookshelf;
		this.title = title;
	}

	/**
	 * Tworzenie zadania transportu ksiazki z polki na biurko
	 * @param sourceBookshelf biurko
	 * @param targetDesk polka
	 * @param title tytul ksiazki do przewiezienia
	 */
	public Task(Bookshelf sourceBookshelf, Desk targetDesk, Title title) {
		this.sourceDesk = null;
		this.sourceBookshelf = sourceBookshelf;
		this.targetDesk = targetDesk;
		this.targetBookshelf = null;
		this.title = title;
	}

	/**
	 * Tworzenie zadania transportu ksiazki z biurka na biurko
	 * @param sourceDesk
	 * @param targetDesk
	 * @param title
	 */
	public Task(Desk sourceDesk, Desk targetDesk, Title title) {
		this.sourceDesk = sourceDesk;
		this.sourceBookshelf = null;
		this.targetDesk = targetDesk;
		this.targetBookshelf = null;
		this.title = title;
	}

	/**
	 * Zwraca polke z ktorej ma byc zabrana ksiazka
	 * @return polka lub null jesli ksiazka ma byc zabrana z biurka
	 */
	public Bookshelf getSourceBookshelf() {
		return sourceBookshelf;
	}

	/**
	 * Zwraca biurko z ktorego ma byc zabrana ksiazka
	 * @return biurko lub null jesli ksiazka ma byc zabrana z polki
	 */
	public Desk getSourceDesk() {
		return sourceDesk;
	}

	/**
	 * Zwraca polke przeznaczenia
	 * @return polka lub null jesli ksiazka ma byc odtransportowana na biurko
	 */
	public Bookshelf getTargetBookshelf() {
		return targetBookshelf;
	}

	/**
	 * Zwraca biurko przeznaczenia
	 * @return biurko lub null jesli ksiazka ma byc odtransportowana na polke
	 */
	public Desk getTargetDesk() {
		return targetDesk;
	}

	/**
	 * Zwraca tytul ksiazki do transportu
	 * @return
	 */
	public Title getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "|" + ((sourceDesk != null) ? sourceDesk : sourceBookshelf) + "|" + ((targetDesk != null) ? targetDesk : targetBookshelf) + "|" + title + "]";
	}


}
