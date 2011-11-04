/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment.concrete;

import chluper2.environment.Bookshelf;
import chluper2.environment.Direction;
import chluper2.environment.ElementType;
import chluper2.environment.Position;
import chluper2.environment.Title;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa polki
 * @author damian
 */
public class ConcreteBookshelf implements Bookshelf, Copyable<ConcreteBookshelf> {

	private final Position position;
	private final Direction direction;
	// unikaln nazwa
	private final String name;
	// tytuly pasujace
	private final Set<Title> validTitles;

	/**
	 * Tworzy polke
	 * @param position
	 * @param direction
	 * @param name unikalna nazwa
	 * @param validTitles zbior dopuszczalnych tytulow
	 */
	public ConcreteBookshelf(Position position, Direction direction, String name, Set<Title> validTitles) {
		this.position = position;
		this.direction = direction;
		this.name = name;
		this.validTitles = validTitles;
	}

	public String getName() {
		return name;
	}

	public Set<Title> getValidTitles() {
		return validTitles;
	}

	public boolean isValidTitle(Title title) {
		return validTitles.contains(title);
	}

	public Position getRobotPadPosition() {
		return position.getPositionAfterMove(direction, 1);
	}

	public Direction getCorrectRobotDirection() {
		return direction.getOpposite();
	}

	public ElementType getElementType() {
		return ElementType.BOOKSHELF;
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "[" + Bookshelf.class.getSimpleName() + "|" + position + "|" + direction + "|" + name + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConcreteBookshelf other = (ConcreteBookshelf) obj;
		if (this.position != other.position && (this.position == null || !this.position.equals(other.position))) {
			return false;
		}
		if (this.direction != other.direction) {
			return false;
		}
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if (this.validTitles != other.validTitles && (this.validTitles == null || !this.validTitles.equals(other.validTitles))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 13 * hash + (this.position != null ? this.position.hashCode() : 0);
		hash = 13 * hash + this.direction.hashCode();
		hash = 13 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 13 * hash + (this.validTitles != null ? this.validTitles.hashCode() : 0);
		return hash;
	}

	public ConcreteBookshelf copy() {
		return new ConcreteBookshelf(position, direction, name, new HashSet<Title>(validTitles));
	}


}
