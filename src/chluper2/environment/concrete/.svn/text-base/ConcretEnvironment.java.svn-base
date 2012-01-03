/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.environment.concrete;

import chluper2.environment.Bookshelf;
import chluper2.environment.CustomMarkersManager;
import chluper2.environment.Desk;
import chluper2.environment.Element;
import chluper2.environment.Environment;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Robot;
import chluper2.environment.Student;
import chluper2.environment.Title;
import chluper2.environment.Wall;
import java.util.ArrayList;
import java.util.List;

/**
 * Konkretne srodowisko, czyli kontener przechowujacy wszystkie elementy
 * @author damian
 */
public class ConcretEnvironment implements Environment, CustomMarkersManager, Copyable<ConcretEnvironment> {

	private final int width;
	private final int height;
	// polki
	private final List<ConcreteBookshelf> bookshelfs;
	// biurka
	private final List<ConcreteDesk> desks;
	// sciany
	private final List<ConcreteWall> walls;
	// roboty
	private final List<ConcreteRobot> robots;
	// studenci
	private final List<ConcreteStudent> students = new ArrayList<ConcreteStudent>();
	// znaczniki podstawowe
	private final List<Marker> markers;
	// znaczniki wlasne
	private final List<Marker> customMarkers = new ArrayList<Marker>();
	// takt symulacji
	private int simulationStep = 0;
	// maksymalny czas obslugi studenta
	private final int maxServiceTime;
	// maksymalny czas akcji robota
	private final int maxActionTime;
	// ilosc obsluzonych studentow
	private int servicedStudentNumber = 0;
	// suma czasu obslugi studentow
	private long studentServiceTimeSum = 0;
	// maksymalny czas obslugi (jaki sie trafil)
	private int maximalStudentServiceTime = 0;

	/**
	 * Tworzy srodowisko
	 * @param width
	 * @param height
	 * @param bookshelfs
	 * @param desks
	 * @param walls
	 * @param robots
	 * @param markers
	 * @param maxServiceTime
	 * @param maxActionTime
	 */
	public ConcretEnvironment(int width, int height, List<ConcreteBookshelf> bookshelfs, List<ConcreteDesk> desks, List<ConcreteWall> walls, List<ConcreteRobot> robots, List<Marker> markers, int maxServiceTime, int maxActionTime) {
		this.width = width;
		this.height = height;
		this.bookshelfs = bookshelfs;
		this.desks = desks;
		this.walls = walls;
		this.robots = robots;
		this.markers = markers;
		this.maxServiceTime = maxServiceTime;
		this.maxActionTime = maxActionTime;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<Element> getAllElements() {
		List<Element> elements = new ArrayList<Element>();
		elements.addAll(bookshelfs);
		elements.addAll(desks);
		elements.addAll(walls);
		elements.addAll(robots);
		elements.addAll(students);
		elements.addAll(markers);
		elements.addAll(customMarkers);
		return elements;
	}

	public List<Element> getElementsAt(Position position) {
		List<Element> foundElements = new ArrayList<Element>();
		for (Element element : getAllElements()) {
			if (element.getPosition().equals(position)) {
				foundElements.add(element);
			}
		}
		return foundElements;
	}

	public List<Bookshelf> getBookshelfs() {
		return new ArrayList<Bookshelf>(bookshelfs);
	}

	public Bookshelf getBookshelfByName(String name) {
		for (Bookshelf bookshelf : bookshelfs) {
			if (bookshelf.getName().equals(name)) {
				return bookshelf;
			}
		}
		return null;
	}

	public Bookshelf getBookshelfByTitle(Title title) {
		for (Bookshelf bookshelf : bookshelfs) {
			if (bookshelf.isValidTitle(title)) {
				return bookshelf;
			}
		}
		return null;
	}

	public List<Desk> getDesks() {
		return new ArrayList<Desk>(desks);
	}

	public Desk getDeskByName(String name) {
		for (Desk desk : desks) {
			if (desk.getName().equals(name)) {
				return desk;
			}
		}
		return null;
	}

	public List<Wall> getWalls() {
		return new ArrayList<Wall>(walls);
	}

	public List<Robot> getRobots() {
		return new ArrayList<Robot>(robots);
	}

	public Robot getRobotByName(String name) {
		for (Robot robot : robots) {
			if (robot.getName().equals(name)) {
				return robot;
			}
		}
		return null;
	}

	public List<Student> getStudents() {
		return new ArrayList<Student>(students);
	}

	public Student getStudentByName(String name) {
		for (Student student : students) {
			if (student.getName().equals(name)) {
				return student;
			}
		}
		return null;
	}

	public List<Marker> getMarkers() {
		List<Marker> list = new ArrayList<Marker>();
		list.addAll(markers);
		list.addAll(customMarkers);
		return list;
	}

	public List<Marker> getMarkersAt(Position position) {
		List<Marker> foundMarkers = new ArrayList<Marker>();
		for (Marker marker : getMarkers()) {
			if (marker.getPosition().equals(position)) {
				foundMarkers.add(marker);
			}
		}
		return foundMarkers;
	}

	public List<Marker> getMarkersByType(String type) {
		List<Marker> foundMarkers = new ArrayList<Marker>();
		for (Marker marker : getMarkers()) {
			if (marker.getType().equals(type)) {
				foundMarkers.add(marker);
			}
		}
		return foundMarkers;
	}

	/**
	 * Dodaj studenta do srodowiska
	 * @param student
	 */
	public void addStudent(ConcreteStudent student) {
		students.add(student);
	}

	/**
	 * Usuwa studenta ze srodowiska
	 * @param student
	 */
	public void removeStudent(ConcreteStudent student) {
		students.remove(student);
	}

	public void addCustomMarker(Marker marker) {
		customMarkers.add(marker);
	}

	public void removeCustomMarker(Marker marker) {
		customMarkers.remove(marker);
	}

	public List<Marker> getCustomMarkers() {
		return customMarkers;
	}

	public int getSimulationStep() {
		return simulationStep;
	}

	public int getMaxServiceTime() {
		return maxServiceTime;
	}

	public int getMaxActionTime() {
		return maxActionTime;
	}

	/**
	 * Metoda ustawia takt symulacji
	 * @param simulationTick
	 */
	public void setSimulationStep(int simulationStep) {
		this.simulationStep = simulationStep;
	}

	public int getServicedStudentNumber() {
		return servicedStudentNumber;
	}

	public double getAverageStudentServiceTime() {
		if (servicedStudentNumber == 0) {
			return 0;
		}
		return (studentServiceTimeSum * 1.0) / servicedStudentNumber;
	}

	public int getMaximalStudentServiceTime() {
		return maximalStudentServiceTime;
	}

	/**
	 * Dodaje nowy czas obslugi studenta
	 * @param serviceTime
	 */
	public void addStudentServiceTime(int studentServiceTime) {
		servicedStudentNumber++;
		studentServiceTimeSum += studentServiceTime;
		maximalStudentServiceTime = Math.max(maximalStudentServiceTime, studentServiceTime);
	}

	/**
	 * Zwraca kopie srodowiska
	 * @return
	 */
	public ConcretEnvironment copy() {
		List<ConcreteBookshelf> bookshelfsCopy = copyList(bookshelfs);
		List<ConcreteDesk> desksCopy = copyList(desks);
		List<ConcreteWall> wallsCopy = new ArrayList<ConcreteWall>(walls);
		List<ConcreteRobot> robotsCopy = copyList(robots);
		List<ConcreteStudent> studentsCopy = copyList(students);
		List<Marker> markersCopy = new ArrayList<Marker>(markers);
		List<Marker> customMarkersCopy = new ArrayList<Marker>(customMarkers);
		ConcretEnvironment copy = new ConcretEnvironment(width, height, bookshelfsCopy, desksCopy, wallsCopy, robotsCopy, markersCopy, maxServiceTime, maxActionTime);
		copy.students.addAll(studentsCopy);
		copy.customMarkers.addAll(customMarkersCopy);
		copy.simulationStep = simulationStep;
		copy.servicedStudentNumber = servicedStudentNumber;
		copy.studentServiceTimeSum = studentServiceTimeSum;
		copy.maximalStudentServiceTime = maximalStudentServiceTime;
		return copy;
	}

	@Override
	public String toString() {
		return "[" + Environment.class.getSimpleName() + "|" + simulationStep + "]";
	}

	

	/**
	 * Metoda kopiuje liste elementow, kopiujac te elementy
	 * @param <T> typ kopiowanego elementu
	 * @param list lista zrodlowa
	 * @return
	 */
	public static <T extends Copyable<T>> List<T> copyList(List<T> list) {
		List<T> newList = new ArrayList<T>();
		for (T t : list) {
			newList.add(t.copy());
		}
		return newList;
	}
}
