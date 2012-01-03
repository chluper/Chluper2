/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.student;

import chluper2.algorithm.util.Graph;
import chluper2.environment.Direction;
import chluper2.environment.Element;
import chluper2.environment.Environment;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Student;
import chluper2.environment.Turn;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa ktora upraszcza rozkazy do postaci najprostrzej
 * @author damian
 */
public class StudentActionSimplifier {

	/**
	 * Metoda zamienia roznakzy na najprostrza postac
	 * @param action akcja ktora trzeba zamienic
	 * @param student student ktory ma podiac dana akcje
	 * @param environment srodowisko
	 * @return
	 */
	public StudentAction simplify(StudentAction action, Student student, Environment environment) {
		// w zaleznosci od akcji
		switch (action.getActionType()) {
			// akcje proste
			case WAIT:
			case FORWARD:
			case PUT:
			case TAKE:
			case LOGIN:
			case LOGOUT:
			case LEAVE:
				// przekazywanie dalej
				return action;
			case TURN:
				// jesli to lewo lub prawo
				if (action.getArg0() instanceof Turn) {
					return action;
				} else {
					return simplifyTurn((Direction) action.getArg0(), student, environment);
				}
			case MOVE:
				return simplifyMove((Direction) action.getArg0(), student, environment);
			case GO_TO:
				return simplifyGoTo((Position) action.getArg0(), student, environment);
			default:
				throw new RuntimeException("Pojawil sie nieobslugiwany rozkaz: " + action);
		}

	}

	/**
	 * Metoda upraszcza rozkaz turn
	 * @param targetDirection docelowy kierunek
	 * @param student
	 * @param environment
	 * @return
	 */
	private StudentAction simplifyTurn(Direction targetDirection, Student student, Environment environment) {
		// aktualny kierunek studenta
		Direction currentDirection = student.getDirection();
		// wybor akcji
		if (currentDirection.equals(targetDirection)) {
			// jesli to ten sam kierunek, to nie obracamy sie
			return new StudentAction(StudentActionType.WAIT);
		} else if (currentDirection.getDirectionAfterTurn(Turn.LEFT).equals(targetDirection)) {
			// jesli po obrocie w lewo jest ok
			return new StudentAction(StudentActionType.TURN, Turn.LEFT);
		}
		if (currentDirection.getDirectionAfterTurn(Turn.RIGNT).equals(targetDirection)) {
			// jesli po obrocie w prawo jest ok
			return new StudentAction(StudentActionType.TURN, Turn.RIGNT);
		} else {
			// to znaczy ze potrzebny jest obrot o 180 stopni
			return new StudentAction(StudentActionType.TURN, Turn.LEFT);
		}

	}

	/**
	 * Metoda upraszcza rozkaz move
	 * @param targetDirection kierunek ruchu
	 * @param student
	 * @param environment
	 * @return
	 */
	private StudentAction simplifyMove(Direction targetDirection, Student student, Environment environment) {
		// czy student jest zwrocony w kierunku w jakim ma sie poruszac
		if (student.getDirection().equals(targetDirection)) {
			// idziemy do przodu
			return new StudentAction(StudentActionType.FORWARD);
		} else {
			// obracamy sie
			return simplifyTurn(targetDirection, student, environment);
		}
	}

	/**
	 * Metoda upraszcza rozkaz goto
	 * @param position
	 * @param student
	 * @param environment
	 * @return
	 */
	private StudentAction simplifyGoTo(Position position, Student student, Environment environment) {
		// jesli juz jestesmy na miejscu to czekamy
		if (student.getPosition().equals(position)) {
			return new StudentAction(StudentActionType.WAIT);
		}
		// tworzenie zbioru wezlow grafu
		Set<Position> nodes = new HashSet<Position>();
		// dodawanie markerow
		for (Element element : environment.getMarkersByType(Marker.STUDENT_AREA)) {
			nodes.add(element.getPosition());
		}
		// usuwanie przeszkod poza soba samym
		for (Element element : environment.getStudents()) {
			if (!student.equals(element)) {
				nodes.remove(element.getPosition());
			}
		}
		// tworzenie grafu
		Graph graph = new Graph(environment.getWidth(), environment.getHeight(), nodes, student.getPosition());
		// jesli mozna dojsc do celu
		if (graph.getPathTo(position) != null) {
			// okreslanie kierunku w ktorym musimy podazac do nastepnego pola w sciezce
			Direction direction = student.getPosition().getDirctionToPosition(graph.getPathTo(position).get(0));
			// zwracanie wyniku
			return simplifyMove(direction, student, environment);
		} else {
			// czekamy moze sie pojawi mozliwosc
			return new StudentAction(StudentActionType.WAIT);
		}
	}
}
