/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.simulation;

import chluper2.algorithm.Action;
import chluper2.algorithm.ActionType;
import chluper2.algorithm.util.Graph;
import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.Desk;
import chluper2.environment.Direction;
import chluper2.environment.Element;
import chluper2.environment.Environment;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Robot;
import chluper2.environment.Title;
import chluper2.environment.Turn;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author damian
 */
public class DefaultActionSimplifier implements ActionSimplifier {

	public Action simplify(Action action, Robot robot, Environment environment) {
		// w zaleznosci od akcji
		switch (action.getActionType()) {
			// akcje proste
			case WAIT:
			case FORWARD:
			case PUT:
			case TAKE:
				// przekazywanie dalej
				return action;
			case TURN:
				// jesli to lewo lub prawo
				if (action.getArg0() instanceof Turn) {
					return action;
				} else {
					return simplifyTurn((Direction) action.getArg0(), robot, environment);
				}
			case MOVE:
				return simplifyMove((Direction) action.getArg0(), robot, environment);
			case GO_TO:
				return simplifyGoTo((Position) action.getArg0(), robot, environment);
			case DELIVER_BOOK:
				if (action.getArg1() instanceof Desk) {
					return simplifyDeliverBook((Book) action.getArg0(), (Desk) action.getArg1(), robot, environment);
				} else {
					return simplifyDeliverBook((Book) action.getArg0(), (Bookshelf) action.getArg1(), robot, environment);
				}
			case GET_BOOK:
				if (action.getArg1() instanceof Desk) {
					return simplifyGetBook((Book) action.getArg0(), (Desk) action.getArg1(), robot, environment);
				} else {
					return simplifyGetBook((Title) action.getArg0(), (Bookshelf) action.getArg1(), robot, environment);
				}
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
	private Action simplifyTurn(Direction targetDirection, Robot robot, Environment environment) {
		// aktualny kierunek studenta
		Direction currentDirection = robot.getDirection();
		// wybor akcji
		if (currentDirection.equals(targetDirection)) {
			// jesli to ten sam kierunek, to nie obracamy sie
			return new Action(ActionType.WAIT);
		} else if (currentDirection.getDirectionAfterTurn(Turn.LEFT).equals(targetDirection)) {
			// jesli po obrocie w lewo jest ok
			return new Action(ActionType.TURN, Turn.LEFT);
		}
		if (currentDirection.getDirectionAfterTurn(Turn.RIGNT).equals(targetDirection)) {
			// jesli po obrocie w prawo jest ok
			return new Action(ActionType.TURN, Turn.RIGNT);
		} else {
			// to znaczy ze potrzebny jest obrot o 180 stopni
			return new Action(ActionType.TURN, Turn.LEFT);
		}

	}

	/**
	 * Metoda upraszcza rozkaz move
	 * @param targetDirection kierunek ruchu
	 * @param student
	 * @param environment
	 * @return
	 */
	private Action simplifyMove(Direction targetDirection, Robot robot, Environment environment) {
		// czy student jest zwrocony w kierunku w jakim ma sie poruszac
		if (robot.getDirection().equals(targetDirection)) {
			// idziemy do przodu
			return new Action(ActionType.FORWARD);
		} else {
			// obracamy sie
			return simplifyTurn(targetDirection, robot, environment);
		}
	}

	/**
	 * Metoda upraszcza rozkaz goto
	 * @param position
	 * @param student
	 * @param environment
	 * @return
	 */
	private Action simplifyGoTo(Position position, Robot robot, Environment environment) {
		// jesli juz jestesmy na miejscu to czekamy
		if (robot.getPosition().equals(position)) {
			return new Action(ActionType.WAIT);
		}
		// tworzenie zbioru wezlow grafu
		Set<Position> nodes = new HashSet<Position>();
		// dodawanie markerow
		for (Element element : environment.getMarkersByType(Marker.ROBOT_AREA)) {
			nodes.add(element.getPosition());
		}
		// usuwanie przeszkod poza soba samym
		for (Element element : environment.getRobots()) {
			if (!robot.equals(element)) {
				nodes.remove(element.getPosition());
			}
		}
		// tworzenie grafu
		Graph graph = new Graph(environment.getWidth(), environment.getHeight(), nodes, robot.getPosition());
		// jesli mozna dojsc do celu
		if (graph.getPathTo(position) != null) {
			// okreslanie kierunku w ktorym musimy podazac do nastepnego pola w sciezce
			Direction direction = robot.getPosition().getDirctionToPosition(graph.getPathTo(position).get(0));
			// zwracanie wyniku
			return simplifyMove(direction, robot, environment);
		} else {
			// czekamy moze sie pojawi mozliwosc
			return new Action(ActionType.WAIT);
		}
	}

	/**
	 * Metoda upraszczajaca odkladanie ksiazki na biurko
	 * @param book
	 * @param desk
	 * @param robot
	 * @param environment
	 * @return
	 */
	private Action simplifyDeliverBook(Book book, Desk desk, Robot robot, Environment environment) {
		// sprawdzamy czy dojechalismy
		if (!robot.getPosition().equals(desk.getRobotPadPosition())) {
			// nie, jedziemy
			return simplifyGoTo(desk.getRobotPadPosition(), robot, environment);
		}
		// sprawdzamy czy robot jest dobrze obrucony
		if (!robot.getDirection().equals(desk.getCorrectRobotDirection())) {
			// nie, obracamy
			return simplifyTurn(desk.getCorrectRobotDirection(), robot, environment);
		}
		// sprawdzamy, czy na biurku jest wolne miejsce
		if (desk.getBooksLimit() <= desk.getBooks().size()) {
			// czekamy
			return new Action(ActionType.WAIT);
		}
		// kladziemy ksiazke
		return new Action(ActionType.PUT, book);
	}

	/**
	 * Metoda upraszczajaca odkladanie ksiazki na polke
	 * @param book
	 * @param desk
	 * @param robot
	 * @param environment
	 * @return
	 */
	private Action simplifyDeliverBook(Book book, Bookshelf bookshelf, Robot robot, Environment environment) {
		// sprawdzamy czy dojechalismy
		if (!robot.getPosition().equals(bookshelf.getRobotPadPosition())) {
			// nie, jedziemy
			return simplifyGoTo(bookshelf.getRobotPadPosition(), robot, environment);
		}
		// sprawdzamy czy robot jest dobrze obrucony
		if (!robot.getDirection().equals(bookshelf.getCorrectRobotDirection())) {
			// nie, obracamy
			return simplifyTurn(bookshelf.getCorrectRobotDirection(), robot, environment);
		}
		// kladziemy ksiazke
		return new Action(ActionType.PUT, book);
	}

	/**
	 * Metoda upraszczajaca pobieranie ksiazki z biurka
	 * @param book
	 * @param desk
	 * @param robot
	 * @param environment
	 * @return
	 */
	private Action simplifyGetBook(Book book, Desk desk, Robot robot, Environment environment) {
		// sprawdzamy czy dojechalismy
		if (!robot.getPosition().equals(desk.getRobotPadPosition())) {
			// nie, jedziemy
			return simplifyGoTo(desk.getRobotPadPosition(), robot, environment);
		}
		// sprawdzamy czy robot jest dobrze obrucony
		if (!robot.getDirection().equals(desk.getCorrectRobotDirection())) {
			// nie, obracamy
			return simplifyTurn(desk.getCorrectRobotDirection(), robot, environment);
		}
		// pobieranie ksiazki
		return new Action(ActionType.TAKE, book);
	}

	/**
	 * Metoda upraszczajaca pobieranie ksiazki z polki
	 * @param book
	 * @param desk
	 * @param robot
	 * @param environment
	 * @return
	 */
	private Action simplifyGetBook(Title title, Bookshelf bookshelf, Robot robot, Environment environment) {
		// sprawdzamy czy dojechalismy
		if (!robot.getPosition().equals(bookshelf.getRobotPadPosition())) {
			// nie, jedziemy
			return simplifyGoTo(bookshelf.getRobotPadPosition(), robot, environment);
		}
		// sprawdzamy czy robot jest dobrze obrucony
		if (!robot.getDirection().equals(bookshelf.getCorrectRobotDirection())) {
			// nie, obracamy
			return simplifyTurn(bookshelf.getCorrectRobotDirection(), robot, environment);
		}
		// pobieranie ksiazki
		return new Action(ActionType.TAKE, title);
	}
}
