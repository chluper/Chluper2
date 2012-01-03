/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment.concrete;

import chluper2.environment.Direction;
import chluper2.environment.ElementType;
import chluper2.environment.Position;
import chluper2.environment.Wall;

/**
 * Klasa konkretnej sciany
 * @author damian
 */
public class ConcreteWall implements Wall, Copyable<ConcreteWall> {

	private final Position position;
	private final Direction direction;

	/**
	 * Tworzy kawalek sciany
	 * @param position
	 * @param direction
	 */
	public ConcreteWall(Position position, Direction direction) {
		this.position = position;
		this.direction = direction;
	}

	public ElementType getElementType() {
		return ElementType.WALL;
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "[" + Wall.class.getSimpleName() + "|" + position + "|" + direction + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConcreteWall other = (ConcreteWall) obj;
		if (this.position != other.position && (this.position == null || !this.position.equals(other.position))) {
			return false;
		}
		if (this.direction != other.direction) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 29 * hash + (this.position != null ? this.position.hashCode() : 0);
		hash = 29 * hash + this.direction.hashCode();
		return hash;
	}

	public ConcreteWall copy() {
		return new ConcreteWall(position, direction);
	}
}
