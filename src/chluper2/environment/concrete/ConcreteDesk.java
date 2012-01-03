/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment.concrete;

import chluper2.environment.Book;
import chluper2.environment.Desk;
import chluper2.environment.Direction;
import chluper2.environment.ElementType;
import chluper2.environment.Position;
import chluper2.environment.Title;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Klasa biurka
 * @author damian
 */
public class ConcreteDesk implements Desk, Copyable<ConcreteDesk> {

	private final Position position;
	private final Direction direction;
	// unikalna nazwa
	private final String name;
	// limit ksiazek
	private final int booksLimit;
	// ksiazki na biurku
	private final List<Book> books = new ArrayList<Book>();
	// lista zyczen
	private final List<Title> wishList = new ArrayList<Title>();

	/**
	 * Tworzy biurko
	 * @param position
	 * @param direction
	 * @param name
	 * @param booksLimit
	 */
	public ConcreteDesk(Position position, Direction direction, String name, int booksLimit) {
		this.position = position;
		this.direction = direction;
		this.name = name;
		this.booksLimit = booksLimit;
	}

	public String getName() {
		return name;
	}

	public int getBooksLimit() {
		return booksLimit;
	}

	public List<Book> getBooks() {
		return books;
	}

	public List<Title> getWishList() {
		return wishList;
	}

	public Position getRobotPadPosition() {
		// z tylu
		return position.getPositionAfterMove(direction.getOpposite(), 1);
	}

	public Position getStudentPadPosition() {
		// z przodu
		return position.getPositionAfterMove(direction, 1);
	}

	public Direction getCorrectRobotDirection() {
		return direction;
	}

	public Direction getCorrectStudentDirection() {
		return direction.getOpposite();
	}

	public ElementType getElementType() {
		return ElementType.DESK;
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "[" + Desk.class.getSimpleName() + "|" + position + "|" + direction + "|" + name + "|" + booksLimit + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConcreteDesk other = (ConcreteDesk) obj;
		if (this.position != other.position && (this.position == null || !this.position.equals(other.position))) {
			return false;
		}
		if (this.direction != other.direction) {
			return false;
		}
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if (this.booksLimit != other.booksLimit) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
	}

	/**
	 * Kladzie ksiazke na bourko
	 * @param book
	 */
	public void addBook(Book book) {
		books.add(book);
	}

	/**
	 * Odbiera ksiazke z biurka
	 * @param book
	 */
	public void removeBook(Book book) {
		books.remove(book);
	}

	/**
	 * Usuwa pozycje z listy zyczen
	 * @param title
	 */
	public void removeFromWishList(Title title) {
		wishList.remove(title);
	}

	/**
	 * Dodaje pozycje z listy zyczen
	 * @param title
	 */
	public void addToWishList(Title title) {
		wishList.add(title);
	}

	/**
	 * Dodaje pozycje z listy zyczen
	 * @param title
	 */
	public void addAllToWishList(Collection<Title> titles) {
		wishList.addAll(titles);
	}

	/**
	 * Czysci liste
	 */
	public void clearWishList() {
		wishList.clear();
	}

	public ConcreteDesk copy() {
		ConcreteDesk copy = new ConcreteDesk(position, direction, name, booksLimit);
		copy.books.addAll(books);
		copy.wishList.addAll(wishList);
		return copy;
	}
}
