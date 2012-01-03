/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment.creator;

import chluper2.environment.Direction;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Title;
import chluper2.environment.concrete.ConcretEnvironment;
import chluper2.environment.concrete.ConcreteBookshelf;
import chluper2.environment.concrete.ConcreteDesk;
import chluper2.environment.concrete.ConcreteRobot;
import chluper2.environment.concrete.ConcreteWall;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasa tworzaca srodowisko
 * @author damian
 */
public class EnvironmentCreator {

	// konfigurator
	private final EnvironmentConfigurator configurator;
	// rozmiary srodowiska
	private int width;
	private int height;
	// zbiory elementow srodowiska
	private final Set<ConcreteWall> walls = new HashSet<ConcreteWall>();
	private final Set<ConcreteBookshelf> bookshelfs = new HashSet<ConcreteBookshelf>();
	private final Set<ConcreteDesk> desks = new HashSet<ConcreteDesk>();
	private final Set<ConcreteRobot> robots = new HashSet<ConcreteRobot>();
	private final Set<Marker> markers = new HashSet<Marker>();
	// ilosc polek
	private int bookshelfNUmber;
	// aktualnie obslugiwana polka
	private int currentShelf = 0;
	//
	private ConcretEnvironment environment = null;

	/**
	 * Konstruktor
	 * @param configurator konfigurator srodowiska
	 */
	public EnvironmentCreator(EnvironmentConfigurator configurator) {
		this.configurator = configurator;
		// wyznaczanie wartosci stalych
		calculateConsts();
		// ewentualne problemy
		if (bookshelfNUmber > configurator.getTitleNumber()) {
			throw new RuntimeException("Ilosc polek jest wieksza od ilosci tytulow");
		}
		// sprawdzanie ilosci biurek
		if (configurator.getDeskNumber() > (width - 2) / 2) {
			throw new RuntimeException("Zbyt duza ilosc biurek");
		}
		// sprawdzanie ilosci robotow
		if (configurator.getDeskNumber() > (width - 2) / 2) {
			throw new RuntimeException("Zbyt duza ilosc robotow");
		}
		// dodawanie elementow
		addAll();
		// twozrenie srodowiska
		createEnvironment();
	}

	/**
	 * Zwraca stworzone srodowisko
	 * @return
	 */
	public ConcretEnvironment getEnvironment() {
		return environment;
	}

	/**
	 * Metoda wyznacza wartosci stale na podstawie konfiguracji
	 * @return
	 */
	private void calculateConsts() {
		// ilosc wierszy + obramowanie
		width = configurator.getBookshelfColumnNumber() * 3 + 2;
		// obramowanie + strefa studentow + lady + rzedy + przejscie
		height = 2 + 2 + 1 + (configurator.getBookshefRowNumber() * (configurator.getBookshelfInRow() + 1)) + 1;
		// ilosc ksiazek
		bookshelfNUmber = configurator.getBookshefRowNumber() * configurator.getBookshelfInRow() * configurator.getBookshelfColumnNumber() * 2;

	}

	private void createEnvironment() {
		List<ConcreteWall> wallList = new ArrayList<ConcreteWall>(walls);
		List<ConcreteBookshelf> bookshelfList = new ArrayList<ConcreteBookshelf>(bookshelfs);
		List<ConcreteDesk> deskList = new ArrayList<ConcreteDesk>(desks);
		List<ConcreteRobot> robotList = new ArrayList<ConcreteRobot>(robots);
		List<Marker> markerList = new ArrayList<Marker>(markers);
		environment = new ConcretEnvironment(width, height, bookshelfList, deskList, wallList, robotList, markerList, configurator.getMaxServiceTime(), configurator.getMaxActionTime());
	}

	/**
	 * dodaje wszystkie elementy
	 */
	private void addAll() {
		int startY = 1;
		// obramowanie
		addBorder();
		// wiersze
		for (int i = 0; i < configurator.getBookshefRowNumber(); i++) {
			addRobotPassage(startY);
			startY++;
			addBokshelfRow(startY);
			startY += configurator.getBookshelfInRow();
		}
		// dodawanie ostatniego wiersza
		addRobotPassage(startY);
		// dodawanie robotow
		addRobots(startY);
		startY++;
		// dodawanie biurek
		addDesks(startY);
		startY++;
		// strefa studentow
		addStudentArea(startY);
	}

	/**
	 * Dodaje zewnetrzne obramowanie
	 */
	private void addBorder() {
		// poziome
		for (int i = 0; i < width; i++) {
			walls.add(new ConcreteWall(new Position(i, 0), Direction.SOUTH));
			walls.add(new ConcreteWall(new Position(i, height - 1), Direction.SOUTH));
		}
		// pionowe
		for (int i = 1; i < height - 1; i++) {
			walls.add(new ConcreteWall(new Position(0, i), Direction.SOUTH));
			walls.add(new ConcreteWall(new Position(width - 1, i), Direction.SOUTH));
		}
	}

	/**
	 * Doklada sciezke dla robotow w podanej lokalizacji
	 * @param startY polozenie w pionie
	 */
	private void addRobotPassage(int startY) {
		for (int i = 1; i < width - 1; i++) {
			markers.add(new Marker(new Position(i, startY), Direction.SOUTH, Marker.ROBOT_AREA));
		}
	}

	/**
	 * Doklada rzad ksiazek
	 * @param polozenie w pionie polek najbardziej na polnoc
	 */
	private void addBokshelfRow(int startY) {
		// ilosc wierszy
		for (int i = 0; i < configurator.getBookshelfInRow(); i++) {
			// polozenie kolumn
			for (int j = 1; j < width - 1; j += 3) {
				addBookshelf(new Position(j, startY + i), Direction.EAST);
				markers.add(new Marker(new Position(j + 1, startY + i), Direction.SOUTH, Marker.ROBOT_AREA));
				addBookshelf(new Position(j + 2, startY + i), Direction.WEST);
			}
		}
	}

	/**
	 * Metoda dodaje polke w okreslonej lokalizacji,
	 * automatycznie przypisuje nazwe i zakres tytulow
	 * @param position
	 * @param direction
	 */
	private void addBookshelf(Position position, Direction direction) {
		// tworzenie tytulow
		Set<Title> titles = new HashSet<Title>();
		for (int i = currentShelf; i < configurator.getTitleNumber(); i += bookshelfNUmber) {
			titles.add(new Title("Title-" + i));
		}
		// nazwa
		String name = "Shelf-" + currentShelf;
		// dodawnie
		bookshelfs.add(new ConcreteBookshelf(position, direction, name, titles));
		// zwiekszanie licznika
		currentShelf++;
	}

	/**
	 * Doklada przestrzen studentow
	 * @param polozenie w pionie punktu najbardziej na polnoc
	 */
	private void addStudentArea(int startY) {
		for (int i = 1; i < width - 1; i++) {
			// przestrzen o poruszania sie
			markers.add(new Marker(new Position(i, startY), Direction.SOUTH, Marker.STUDENT_AREA));
			markers.add(new Marker(new Position(i, startY + 1), Direction.SOUTH, Marker.STUDENT_AREA));
		}
		// wejscie i wyjscie
		markers.add(new Marker(new Position(1, startY + 1), Direction.SOUTH, Marker.STUDENT_ENTRY));
		markers.add(new Marker(new Position(width - 2, startY + 1), Direction.SOUTH, Marker.STUDENT_EXIT));
	}

	/**
	 * Dodawanie robotow
	 * @param startY polozenie w pionie gdzie maja byc dolozone
	 */
	private void addRobots(int startY) {
		final int passageWidth = width - 2;
		final int distance = passageWidth / (configurator.getRobotNumber());
		int currentRobot = 0;
		// ustawianie robotow we wlasciwych odstepach
		for (int i = 1; i < width - 1; i++) {
			if (i % distance == distance / 2) {
				robots.add(new ConcreteRobot(new Position(i, startY), Direction.SOUTH, "Robot-" + currentRobot, configurator.getRobotCacheSize()));
				currentRobot++;
			}
		}
		if (robots.size() != configurator.getRobotNumber()) {
			throw new RuntimeException("Algorytm rozstawiajacy roboty jest nieprawidlowy");
		}
	}

	/**
	 * Dodawanie biurek i sciany miedzy nimi
	 * @param startY polozenie w pionie gdzie maja byc dolozone
	 */
	private void addDesks(int startY) {
		final int passageWidth = width - 2;
		final int distance = passageWidth / (configurator.getDeskNumber());
		int currentDesk = 0;
		// ustawianie robotow we wlasciwych odstepach
		for (int i = 1; i < width - 1; i++) {
			if (i % distance == distance / 2) {
				// dodawanie biurka
				desks.add(new ConcreteDesk(new Position(i, startY), Direction.SOUTH, "Desk-" + currentDesk, configurator.getDeskBooksLimit()));
				currentDesk++;
			} else {
				// dodawanie sciany
				walls.add(new ConcreteWall(new Position(i, startY), Direction.SOUTH));
			}
		}
		if (desks.size() != configurator.getDeskNumber()) {
			throw new RuntimeException("Algorytm rozstawiajacy biurka jest nieprawidlowy");
		}
	}
}
