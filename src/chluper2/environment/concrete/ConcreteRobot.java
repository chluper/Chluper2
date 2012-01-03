/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment.concrete;

import chluper2.environment.Book;
import chluper2.environment.Direction;
import chluper2.environment.ElementType;
import chluper2.environment.Position;
import chluper2.environment.Robot;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa konkretnego robota
 * @author damian
 */
public class ConcreteRobot implements Robot, Copyable<ConcreteRobot> {

	private Position position;
	private Direction direction;
	// unikalna nazwa
	private final String name;
	// wielkosc kieszeni
	private final int cacheSize;
	// kieszen
	private final List<Book> cache = new ArrayList<Book>();
	// czas wykonywanej akcji
	private int actionTime = 0;

	/**
	 * Tworzy robota
	 * @param position
	 * @param direction
	 * @param name
	 * @param cacheSize
	 */
	public ConcreteRobot(Position position, Direction direction, String name, int cacheSize) {
		this.position = position;
		this.direction = direction;
		this.name = name;
		this.cacheSize = cacheSize;
	}

	public String getName() {
		return name;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public List<Book> getCache() {
		return cache;
	}

	public ElementType getElementType() {
		return ElementType.ROBOT;
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getActionTime() {
		return actionTime;
	}

	/**
	 * Ustawia nowy kierunek robota
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Ustaiwa nowe polozenie robota
	 * @param position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Ustawia czas wykonywanej akcji
	 * @param actionTime
	 */
	public void setActionTime(int actionTime) {
		this.actionTime = actionTime;
	}

	@Override
	public String toString() {
		return "[" + Robot.class.getSimpleName() + "|" + position + "|" + direction + "|" + name + "|" + cacheSize + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConcreteRobot other = (ConcreteRobot) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if (this.cacheSize != other.cacheSize) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 53 * hash + this.cacheSize;
		return hash;
	}

	/**
	 * Dodawanie ksiazki do kieszeni
	 * @param book
	 */
	public void addToCache(Book book) {
		cache.add(book);
	}

	/**
	 * Usuwanie ksiazki z kieszeni
	 * @param book
	 */
	public void removeFromCache(Book book) {
		cache.remove(book);
	}

	public ConcreteRobot copy() {
		ConcreteRobot copy = new ConcreteRobot(position, direction, name, cacheSize);
		copy.cache.addAll(cache);
		copy.actionTime = actionTime;
		return copy;
	}
}
