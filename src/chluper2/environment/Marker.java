/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.environment;

/**
 * Znacznik w srodowisku
 * @author damian
 */
public final class Marker implements Element {

	/**
	 * Znacznik ktorym oznaczane sa pola po ktorych moze poruszac sie robot
	 */
	public static final String ROBOT_AREA = "ROBOT_AREA";

	/**
	 * Znacznik ktorym koordynator moze oznaczyc kierunek,
	 * w ktorym z tego pola moze poruszac sie robot
	 */
	public static final String POSSIBLE_DIRECTION = "ROBOT_POSSIBLE_DIRECTION";

	/**
	 * Znacznik ktorym oznaczane sa pola po ktorych moze poruszac sie student
	 */
	public static final String STUDENT_AREA = "STUDENT_AREA";

	/**
	 * Znacznik ktorym oznaczane sa wejscia do biblioteki
	 */
	public static final String STUDENT_ENTRY = "STUDENT_ENTRY";

	/**
	 * Znacznik ktorym oznaczane sa wyjscia z biblioteki
	 */
	public static final String STUDENT_EXIT = "STUDENT_EXIT";

	private final Position position;
	private final Direction direction;
	private final String type;

	/**
	 * Tworzy znacznik
	 * @param position polozenie
	 * @param direction kierunek
	 * @param type typ znacznika
	 */
	public Marker(Position position, Direction direction, String type) {
		this.position = position;
		this.direction = direction;
		this.type = type;
	}

	/**
	 * Metoda zwraca typ znacznika
	 * @return
	 */
	public String getType() {
		return type;
	}

	public ElementType getElementType() {
		return ElementType.MARKER;
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "|" + position + "|" + direction + "|" + type + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Marker other = (Marker) obj;
		if (this.position != other.position && (this.position == null || !this.position.equals(other.position))) {
			return false;
		}
		if (this.direction != other.direction) {
			return false;
		}
		if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + (this.position != null ? this.position.hashCode() : 0);
		hash = 29 * hash + this.direction.hashCode();
		hash = 29 * hash + (this.type != null ? this.type.hashCode() : 0);
		return hash;
	}
}
