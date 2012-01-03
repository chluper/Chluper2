/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

/**
 * Klasa reprezentuje tytul ksiazki (lub isbn).
 * Kilka egzempalzy ksiazek moze posiadac ten sam tutul.
 * @author damian
 */
public final class Title {

	private final String title;

	/**
	 * Tworzy tutul (pozycje ksiazkowa)
	 * @param title reprezentacja tekstowa tytulu
	 */
	public Title(String title) {
		this.title = title;
	}

	/**
	 * Zwraca reprezentacje tekstowa tytolu
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Title other = (Title) obj;
		if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + (this.title != null ? this.title.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return title;
	}

}
