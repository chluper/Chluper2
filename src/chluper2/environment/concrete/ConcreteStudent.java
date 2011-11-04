/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment.concrete;

import chluper2.environment.Book;
import chluper2.environment.Direction;
import chluper2.environment.ElementType;
import chluper2.environment.Position;
import chluper2.environment.Student;
import chluper2.environment.Title;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author damian
 */
public class ConcreteStudent implements Student, Copyable<ConcreteStudent> {

	private Position position;
	private Direction direction;
	// unikalna nazwa
	private final String name;
	// ksiazki do wypozyczenia
	private final List<Book> bag;
	// lista zyczen
	private final List<Title> wishList;
	// czas obslugi
	private int serviceTime = 0;

	/**
	 * Tworzy studenta
	 * @param position
	 * @param direction
	 * @param name
	 * @param booksToReturn
	 * @param wishList
	 */
	public ConcreteStudent(Position position, Direction direction, String name, List<Book> bag, List<Title> wishList) {
		this.position = position;
		this.direction = direction;
		this.name = name;
		this.bag = bag;
		this.wishList = wishList;
	}

	public String getName() {
		return name;
	}

	public List<Book> getBag() {
		return bag;
	}

	public List<Title> getWishList() {
		return wishList;
	}

	public boolean isSatisfied() {
		// tworzenie listy pomocniczej
		List<Title> checkList = new ArrayList<Title>(wishList);
		// jesli ksiazka jest w plecaku to powinna byc na liscie
		for (Book book : bag) {
			if (checkList.contains(book.getTitle())) {
				checkList.remove(book.getTitle());
			} else {
				return false;
			}
		}
		// jesli na liscie jest cos czego nie ma w plecaku
		return checkList.isEmpty();
	}

	public ElementType getElementType() {
		return ElementType.STUDENT;
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	/**
	 * Ustawia nowy kierunek studenta
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Ustaiwa nowe polozenie studenta
	 * @param position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Ustawia czas obslugi studenta
	 * @param serviceTime
	 */
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	/**
	 * Wkladanie ksiazki do torby
	 * @param book
	 */
	public void addToBag(Book book) {
		bag.add(book);
	}

	/**
	 * Wyjmowanie ksiazki z torby
	 * @param book
	 */
	public void removeFromBag(Book book) {
		bag.remove(book);
	}

	@Override
	public String toString() {
		return "[" + Student.class.getSimpleName() + "|" + position + "|" + direction + "|" + name + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConcreteStudent other = (ConcreteStudent) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if (this.wishList != other.wishList && (this.wishList == null || !this.wishList.equals(other.wishList))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 67 * hash + (this.wishList != null ? this.wishList.hashCode() : 0);
		return hash;
	}

	public ConcreteStudent copy() {
		ConcreteStudent copy = new ConcreteStudent(position, direction, name, new ArrayList<Book>(bag), new ArrayList<Title>(wishList));
		copy.serviceTime = serviceTime;
		return copy;
	}
}
