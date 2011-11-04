/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

/**
 * Klasa reprezentujaca ksiazke
 * @author damian
 */
public final class Book {

	private final Title title;
	// niepowtarzalny kod ksiazki
	private final long uniqueCode;

	/**
	 * Tworzy egzemplaz ksiazki
	 * @param title tutul
	 * @param uniqueCode niepowtarzalny kod ksiazki
	 */
	public Book(Title title, long uniqueCode) {
		this.title = title;
		this.uniqueCode = uniqueCode;
	}

	/**
	 * Zwraca niepowtarzalny kod ksiazki
	 * @return
	 */
	public long getUniqueCode() {
		return uniqueCode;
	}

	/**
	 * Zwraca tytul ksiazki
	 * @return
	 */
	public Title getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "|" + title + "|" + uniqueCode + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Book other = (Book) obj;
		if (this.title != other.title && (this.title == null || !this.title.equals(other.title))) {
			return false;
		}
		if (this.uniqueCode != other.uniqueCode) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 71 * hash + (this.title != null ? this.title.hashCode() : 0);
		hash = 71 * hash + (int) (this.uniqueCode ^ (this.uniqueCode >>> 32));
		return hash;
	}

	
}
