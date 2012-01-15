/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.simulation;

import chluper2.algorithm.Action;
import chluper2.algorithm.Algorithm;
import chluper2.algorithm.AlgorithmManager;
import chluper2.algorithm.Coordinator;
import chluper2.algorithm.util.CoordinatorAdapter;
import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.Desk;
import chluper2.environment.Direction;
import chluper2.environment.Element;
import chluper2.environment.Environment;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Robot;
import chluper2.environment.Student;
import chluper2.environment.Title;
import chluper2.environment.Turn;
import chluper2.environment.concrete.ConcretEnvironment;
import chluper2.environment.concrete.ConcreteDesk;
import chluper2.environment.concrete.ConcreteRobot;
import chluper2.environment.concrete.ConcreteStudent;
import chluper2.environment.creator.EnvironmentConfigurator;
import chluper2.environment.creator.EnvironmentCreator;
import chluper2.student.DefaultStudentManager;
import chluper2.student.StudentAction;
import chluper2.student.StudentActionSimplifier;
import chluper2.student.StudentAlgorithm;
import chluper2.student.StudentManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa symulacji
 * @author damian
 */
public class Simulation {

	// srodowisko
	private final ConcretEnvironment environment;
	// algorytmy robotow
	private final Map<Robot, Algorithm> algorithms = new HashMap<Robot, Algorithm>();
	// koordynator
	private final Coordinator coordinator;
	// algorytmy studentow
	private final Map<Student, StudentAlgorithm> studentAlgorithms = new HashMap<Student, StudentAlgorithm>();
	// licznik taktow
	private int stepCounter = 0;
	// aktualny kod ksiazki ksiazki pobierane z polek beda mialy kody dodanie
	private int currebtBookCode = 1;
	// manager studentow
	private final StudentManager studentManager = new DefaultStudentManager();
        
        //liczba usatysfakcjonowanych studentow
        private int satisfiedStudents = 0;
	

	/**
	 * Metoda tworzy symulacje
	 * @param configurator konfigurator srodowiska
	 * @param creator kreator algorytmow
	 */
	public Simulation(EnvironmentConfigurator configurator, AlgorithmManager algorithmManager) {
		// tworzenie srodowiska
		EnvironmentCreator environmentCreator = new EnvironmentCreator(configurator);
		environment = environmentCreator.getEnvironment();
		// tworzenie algorytmow
		algorithmManager.init(environment, environment);
		// uzyskiwanie algorytmow
		for (Robot robot : environment.getRobots()) {
			algorithms.put(robot, algorithmManager.createAlgorithm(robot, environment));
		}
		// uzyskiwanie koordynatora
		Coordinator c = algorithmManager.createCoordinator(environment);
		coordinator = (c != null) ? c : new CoordinatorAdapter() {};
		// init koordynatora
		coordinator.init(environment, environment);
		// init studentow
		studentManager.init(environment);
	}

	/**
	 * Metoda zwraca aktualny widok srodowiska
	 * @return
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * Kolejny takt symulacji
	 */
	public void step() {
		// rozpoczecie taktu
		// przepisanie do srodowiska
		environment.setSimulationStep(stepCounter);
		// informowanie koordynatora
		coordinator.newStep(environment, environment);

		// uruchamianie algorytmu dla poszczegolnych robotow
		for (Robot robot : environment.getRobots()) {
			// obsluga robota
			// wyluskiwanie algorytmu
			Algorithm algorithm = algorithms.get(robot);
			// informowanie koordynatora
			coordinator.robotBeforeAction(robot, algorithm, environment);
			// pobieranie rozkazu
			Action action = algorithm.decide(robot, environment, coordinator);
			// wykonywanie rozkazu
			execute(action, robot);
		}

		// uruchamianie algorytmu dla poszczegolnych studentow
		for (Student student : environment.getStudents()) {
			// inkrementacja czasu obslugi
			((ConcreteStudent) student).setServiceTime(student.getServiceTime() + 1);
			// frustracja
			if (student.getServiceTime() >= environment.getMaxServiceTime()) {
				throw new SimulationException(stepCounter, "Student: " + student + " nie zostal obsluzony przez : " + student.getServiceTime() + " Krokow");
			}
			// wyluskiwanie algorytmu
			StudentAlgorithm algorithm = studentAlgorithms.get(student);
			// pobieranie rozkazu
			StudentAction action = algorithm.decide(student, environment);
			// wykonywanie rozkazu
			execute(action, student);
		}

		// wejscie
		final Position entry = environment.getMarkersByType(Marker.STUDENT_ENTRY).get(0).getPosition();
		// sprawdzanie czy mozna pobrac studenta
		boolean freeEntry = true;
		for (Element element : environment.getElementsAt(entry)) {
			if (element.getElementType().isCollisional()) {
				freeEntry = false;
				break;
			}
		}
		// obsluga ewentualnych nowych studentow
		ConcreteStudent student = null;
		if (freeEntry) {
			student = studentManager.newStudent(environment);
		}
		// jesli powstal student
		if (student != null) {
			// przypisywanie pozycji
			student.setPosition(entry);
			student.setDirection(Direction.SOUTH);
			// dodawanie do srodowiska
			environment.addStudent(student);
			// przypisywanie algorytmu
			studentAlgorithms.put(student, studentManager.createAlgorithm(student, environment));
		}

		// zmiana licznika
		stepCounter++;
	}

	/**
	 * Wykonywanie decyzji robota
	 * @param action
	 * @param robot
	 */
	private void execute(Action action, Robot robot) {
		// inkrementaca czasu akcji
		((ConcreteRobot) robot).setActionTime(robot.getActionTime() + 1);
		// kykrywanie zakleszczen
		if (robot.getActionTime() >= environment.getMaxActionTime()) {
			throw new SimulationException(stepCounter, "Wykryto zakleszczenie, robot: " + robot + " nie wykonal akcji przez: " + robot.getActionTime() + " Krokow");
		}
		// upraszczanie akcji
		ActionSimplifier simplifier = new DefaultActionSimplifier();
		action = simplifier.simplify(action, robot, environment);
		switch (action.getActionType()) {
			case WAIT:
				// nic sie nie dzieje
				return;
			case FORWARD:
				executeForward((ConcreteRobot) robot);
				return;
			case TURN:
				executeTurn((ConcreteRobot) robot, (Turn) action.getArg0());
				return;
			case PUT:
				executePut((ConcreteRobot) robot, (Book) action.getArg0());
				// zerowanie czasu akcji
				((ConcreteRobot) robot).setActionTime(0);
				return;
			case TAKE:
				if (action.getArg0() instanceof Title) {
					executeTake((ConcreteRobot) robot, (Title) action.getArg0());
				} else {
					executeTake((ConcreteRobot) robot, (Book) action.getArg0());
				}
				// zerowanie czasu akcji
				((ConcreteRobot) robot).setActionTime(0);
				return;
			default:
				throw new SimulationException(stepCounter, "Nieobslugiwany rozkaz: " + action);
		}
	}

	/**
	 * Wykonywanie rozkazu forward
	 * @param robot
	 */
	private void executeForward(ConcreteRobot robot) {
		// pobieranie starej pozycji
		final Position target = robot.getPosition().getPositionAfterMove(robot.getDirection(), 1);
		// sprawdzanie czy nie nastapi kolizaj
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to element kolizyjny
			if (element.getElementType().isCollisional()) {
				throw new CollisionException(stepCounter, element, robot, target);
			}
		}
		// przemieszczenie
		robot.setPosition(target);
	}

	/**
	 * Wykonywanie rozkazu turn
	 * @param robot
	 */
	private void executeTurn(ConcreteRobot robot, Turn turn) {
		robot.setDirection(robot.getDirection().getDirectionAfterTurn(turn));
	}

	/**
	 * Wykonywanie rozkazu put
	 * @param robot
	 * @param book
	 */
	private void executePut(ConcreteRobot robot, Book book) {
		// sprawdzamy czy mamy gdzie klasc
		final Position target = robot.getPosition().getPositionAfterMove(robot.getDirection(), 1);
		// sprawdzamy czy cos tam jest
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to polka
			if (element instanceof Bookshelf) {
				Bookshelf bookshelf = (Bookshelf) element;
				// jesli ksiazka jest w kieszeni
				if (robot.getCache().contains(book)) {
					// jesli tytul jest prawidlowy
					if (bookshelf.isValidTitle(book.getTitle())) {
						// odkladanie ksiazki
						robot.removeFromCache(book);
						// informowanie koordynatora
						coordinator.robotPutBook(robot, bookshelf, book, environment);
					} else {
						// nie pasuje tytul do polki
						throw new SimulationException(stepCounter, "Proba odlozenia nieprawidlowej ksiazki:" + book + " na polke: " + bookshelf);
					}
				} else {
					// nie ma ksiazki w kieszeni
					throw new SimulationException(stepCounter, "Proba odlozenia ksiazki:" + book + " ktorej nie ma w kieszeni robota: " + robot);
				}
				return;
			}
			// jesli jest to biurko
			if (element instanceof Desk) {
				ConcreteDesk desk = (ConcreteDesk) element;
				// jesli ksiazka jest w kieszeni
				if (robot.getCache().contains(book)) {
					// sprawdzanie czy na biurku jest miejsce
					if (desk.getBooks().size() < desk.getBooksLimit()) {
						// usuwanie z kieszeni i dokladanie do biurka
						robot.removeFromCache(book);
						desk.addBook(book);
						// informowanie koordynatora
						coordinator.robotPutBook(robot, desk, book, environment);
					} else {
						// nie ma ksiazki w kieszeni
						throw new SimulationException(stepCounter, "Proba odlozenia ksiazki:" + book + " na pelne biurko: " + desk);
					}
				} else {
					// nie ma ksiazki w kieszeni
					throw new SimulationException(stepCounter, "Proba odlozenia ksiazki:" + book + " ktorej nie ma w kieszeni robota: " + robot);
				}
				return;
			}
		}
		throw new SimulationException(stepCounter, "Proba odlozenia ksiazki:" + book + " w powietrze przez robota: " + robot);
	}

	/**
	 * wykonywanie rozkazu take na biurku
	 * @param robot
	 * @param book
	 */
	private void executeTake(ConcreteRobot robot, Book book) {
		// sprawdzamy czy mamy gdzie klasc
		final Position target = robot.getPosition().getPositionAfterMove(robot.getDirection(), 1);
		// sprawdzamy czy cos tam jest
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to biurko
			if (element instanceof Desk) {
				ConcreteDesk desk = (ConcreteDesk) element;
				// jesli w kieszeni jest miejsce
				if (robot.getCache().size() < robot.getCacheSize()) {
					// sprawdzanie czy na biurku lezy ta ksiazka
					if (desk.getBooks().contains(book)) {
						// usuwanie z biurka i dokladanie do kieszeni
						desk.removeBook(book);
						robot.addToCache(book);
						// informowanie koordynatora
						coordinator.robotTakenBook(robot, desk, book, environment);
					} else {
						// nie ma ksiazki w kieszeni
						throw new SimulationException(stepCounter, "Proba pobrania ksiazki:" + book + " ktorej nie ma w na biurku: " + desk);
					}
				} else {
					// nie ma ksiazki w kieszeni
					throw new SimulationException(stepCounter, "Proba poprania ksiazki:" + book + " do pelnej kieszeni robota: " + robot);
				}
				return;
			}
		}
		throw new SimulationException(stepCounter, "Proba odlozenia ksiazki:" + book + " w powietrze przez robota: " + robot);
	}

	/**
	 * wykonywanie rozkazu take na polce
	 * @param robot
	 * @param book
	 */
	private void executeTake(ConcreteRobot robot, Title title) {
		// sprawdzamy czy mamy gdzie klasc
		final Position target = robot.getPosition().getPositionAfterMove(robot.getDirection(), 1);
		// sprawdzamy czy cos tam jest
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to polka
			if (element instanceof Bookshelf) {
				Bookshelf bookshelf = (Bookshelf) element;
				// jesli ksiazka jest w kieszeni
				if (robot.getCache().size() < robot.getCacheSize()) {
					// jesli tytul jest prawidlowy
					if (bookshelf.isValidTitle(title)) {
						// odkladanie ksiazki
						Book book = createBook(title);
						robot.addToCache(book);
						// informowanie koordynatora
						coordinator.robotTakenBook(robot, bookshelf, book, environment);
					} else {
						// nie pasuje tytul do polki
						throw new SimulationException(stepCounter, "Proba pobrania nieprawidlowej ksiazki:" + title + " z polki: " + bookshelf);
					}
				} else {
					// nie ma ksiazki w kieszeni
					throw new SimulationException(stepCounter, "Proba poprania ksiazki:" + title + " do pelnej kieszeni robota: " + robot);
				}
				return;
			}
		}
		throw new SimulationException(stepCounter, "Proba odlozenia ksiazki:" + title + " w powietrze przez robota: " + robot);
	}

	/**
	 * Wykonywanie decyzji robota
	 * @param action
	 * @param robot
	 */
	private void execute(StudentAction action, Student student) {
		// upraszczanie akcji
		StudentActionSimplifier simplifier = new StudentActionSimplifier();
		action = simplifier.simplify(action, student, environment);
		switch (action.getActionType()) {
			case WAIT:
				// nic sie nie dzieje
				return;
			case FORWARD:
				executeForward((ConcreteStudent) student);
				return;
			case TURN:
				executeTurn((ConcreteStudent) student, (Turn) action.getArg0());
				return;
			case PUT:
				executePut((ConcreteStudent) student, (Book) action.getArg0());
				return;
			case TAKE:
				executeTake((ConcreteStudent) student, (Book) action.getArg0());
				return;
			case LOGIN:
				executeLogin((ConcreteStudent) student);
				return;
			case LOGOUT:
				executeLogout((ConcreteStudent) student);
				return;
			case LEAVE:
				executeLeave((ConcreteStudent) student);
				return;
			default:
				throw new SimulationException(stepCounter, "Nieobslugiwany rozkaz: " + action);
		}
	}

	/**
	 * Wykonywanie rozkazu forward
	 * @param robot
	 */
	private void executeForward(ConcreteStudent student) {
		// pobieranie starej pozycji
		final Position target = student.getPosition().getPositionAfterMove(student.getDirection(), 1);
		// sprawdzanie czy nie nastapi kolizaj
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to element kolizyjny
			if (element.getElementType().isCollisional()) {
				throw new CollisionException(stepCounter, element, student, target);
			}
		}
		// przemieszczenie
		student.setPosition(target);
	}

	/**
	 * Wykonywanie rozkazu turn
	 * @param robot
	 */
	private void executeTurn(ConcreteStudent student, Turn turn) {
		student.setDirection(student.getDirection().getDirectionAfterTurn(turn));
	}

	/**
	 * Wykonywanie rozkazu put
	 * @param robot
	 * @param book
	 */
	private void executePut(ConcreteStudent student, Book book) {
		// sprawdzamy czy mamy gdzie klasc
		final Position target = student.getPosition().getPositionAfterMove(student.getDirection(), 1);
		// sprawdzamy czy cos tam jest
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to biurko
			if (element instanceof Desk) {
				ConcreteDesk desk = (ConcreteDesk) element;
				// jesli ksiazka jest w torbie
				if (student.getBag().contains(book)) {
					// sprawdzanie czy na biurku jest miejsce
					if (desk.getBooks().size() < desk.getBooksLimit()) {
						// usuwanie z kieszeni i dokladanie do biurka
						student.removeFromBag(book);
						desk.addBook(book);
						// informowanie koordynatora
						coordinator.studentPutBook(student, desk, book, environment);
					} else {
						// nie ma ksiazki w kieszeni
						throw new SimulationException(stepCounter, "Proba odlozenia ksiazki:" + book + " na pelne biurko: " + desk);
					}
				} else {
					// nie ma ksiazki w kieszeni
					throw new SimulationException(stepCounter, "Proba odlozenia ksiazki:" + book + " ktorej nie ma w torbie studenta: " + student);
				}
				return;
			}
		}
		throw new SimulationException(stepCounter, "Proba odlozenia ksiazki: " + book + " w powietrze przez studenta: " + student);
	}

	/**
	 * wykonywanie rozkazu take na biurku
	 * @param robot
	 * @param book
	 */
	private void executeTake(ConcreteStudent student, Book book) {
		// sprawdzamy czy mamy gdzie klasc
		final Position target = student.getPosition().getPositionAfterMove(student.getDirection(), 1);
		// sprawdzamy czy cos tam jest
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to biurko
			if (element instanceof Desk) {
				ConcreteDesk desk = (ConcreteDesk) element;
				// sprawdzanie czy na biurku lezy ta ksiazka
				if (desk.getBooks().contains(book)) {
					// usuwanie z biurka i dokladanie do kieszeni
					desk.removeBook(book);
					student.addToBag(book);
					// jesli ta ksiazka byla na liscie zyczen, to usuwani jej
					if (desk.getWishList().contains(book.getTitle())) {
						desk.removeFromWishList(book.getTitle());
					}
					// informowanie koordynatora
					coordinator.studentTakenBook(student, desk, book, environment);
				} else {
					// nie ma ksiazki w kieszeni
					throw new SimulationException(stepCounter, "Proba pobrania ksiazki:" + book + " ktorej nie ma w na biurku: " + desk);
				}
				return;
			}
		}
		throw new SimulationException(stepCounter, "Proba odlozenia ksiazki: " + book + " w powietrze przez studenta: " + student);
	}

	/**
	 * wykonywanie rozkazu take na biurku
	 * @param robot
	 * @param book
	 */
	private void executeLogin(ConcreteStudent student) {
		// sprawdzamy czy mamy gdzie klasc
		final Position target = student.getPosition().getPositionAfterMove(student.getDirection(), 1);
		// sprawdzamy czy cos tam jest
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to biurko
			if (element instanceof Desk) {
				ConcreteDesk desk = (ConcreteDesk) element;
				// odkladanie listy zyczen
				List<Title> wishlist = student.getWishList();
				desk.addAllToWishList(wishlist);
				// informowanie koordynatora
				coordinator.studentPutWishlist(student, desk, wishlist, environment);
				return;
			}
		}
		throw new SimulationException(stepCounter, "Proba zalogowania bez podejscia do biurka przez studenta studenta: " + student);
	}

	/**
	 * wykonywanie rozkazu take na biurku
	 * @param robot
	 * @param book
	 */
	private void executeLogout(ConcreteStudent student) {
		// sprawdzamy czy mamy gdzie klasc
		final Position target = student.getPosition().getPositionAfterMove(student.getDirection(), 1);
		// sprawdzamy czy cos tam jest
		for (Element element : environment.getElementsAt(target)) {
			// jesli jest to biurko
			if (element instanceof Desk) {
				ConcreteDesk desk = (ConcreteDesk) element;
				// odkladanie listy zyczen
				desk.clearWishList();
				return;
			}
		}
		throw new SimulationException(stepCounter, "Proba wylogowania bez podejscia do biurka przez studenta studenta: " + student);
	}

	/**
	 * wykonywanie rozkazu take na biurku
	 * @param robot
	 * @param book
	 */
	private void executeLeave(ConcreteStudent student) {
		// sprawdzamy czy cos tam jest
		for (Element element : environment.getElementsAt(student.getPosition())) {
			// jesli jest to biurko
			if (element instanceof Marker) {
				Marker marker = (Marker) element;
				if (marker.getType().equals(Marker.STUDENT_EXIT)) {
					if (!student.isSatisfied()) {
						throw new SimulationException(stepCounter, "Proba wyjscia nieusatysfakcjonowanego studenta: " + student);
					}
					// zapis na potrzeby statystyk
					environment.addStudentServiceTime(student.getServiceTime());
					// usuwanie algorytmu
					studentAlgorithms.remove(student);
					// usuwanie studenta ze srodowiska
					environment.removeStudent(student);
					// zwracanie managerowi
					studentManager.studentLeave(student, environment);
					return;
				}
			}
		}
		throw new SimulationException(stepCounter, "Proba wyjscia w niewlasciwym miejscu przez studenta: " + student);
	}

	/**
	 * Metoda tworzy ksiazke
	 * @param title
	 * @return
	 */
	private Book createBook(Title title) {
		Book book = new Book(title, currebtBookCode);
		currebtBookCode++;
		return book;
	}

}
