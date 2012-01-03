/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.student;

import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.Desk;
import chluper2.environment.Environment;
import chluper2.environment.Student;
import chluper2.environment.Title;
import chluper2.environment.concrete.ConcreteStudent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Klasa managera studentow
 * @author damian
 */
public class DefaultStudentManager implements StudentManager {

	// lista wolnych biurek
	private final LinkedList<String> freeDesks = new LinkedList<String>();
	// mapa studentow i przypisanych biurek
	private final Map<Student, String> deskForStudent = new HashMap<Student, String>();
	// generator liczb losowych
	private final Random random = new Random(55);
	// aktualny kod ksiazki, ksiazki oddawane z polek beda mialy kody ujemne
	private int currebtBookCode = -1;
	// licznik studentow
	private int studentCounter = 0;

	public void init(Environment environment) {
		// uzupelnianie listy biurek
		for (Desk desk : environment.getDesks()) {
			freeDesks.add(desk.getName());
		}
	}

	public ConcreteStudent newStudent(Environment environment) {
		// jezeli sa wolne biurka
		if (!freeDesks.isEmpty()) {
			return createStudent(environment);
		}
		return null;
	}

	public void studentLeave(ConcreteStudent student, Environment environment) {
		// zwracanie biurka
		freeDesks.addLast(deskForStudent.get(student));
		// usuwanie studenta
		deskForStudent.remove(student);
	}

	public StudentAlgorithm createAlgorithm(Student student, Environment environment) {
		String freeDesk = freeDesks.removeFirst();
		// przypisanie
		deskForStudent.put(student, freeDesk);
		// tworzenie algorytmu
		return new DefaultStudentAlgorithm(freeDesk);
	}

	/**
	 * Tworzy studenta
	 * @param environment
	 * @return
	 */
	private ConcreteStudent createStudent(Environment environment) {
		int studentType = random.nextInt(8);
		String studentName = "Student-" + studentCounter + "_" + studentType;
		ConcreteStudent student = null;
		// tworzenie odpowiedniego studenta
		if (studentType < 4) {
			student = new ConcreteStudent(null, null, studentName, new ArrayList<Book>(), createNotEmptyWishList(environment));
		} else if (studentType < 7) {
			student = new ConcreteStudent(null, null, studentName, createNotEmptyBag(environment), Collections.EMPTY_LIST);
		} else {
			student = new ConcreteStudent(null, null, studentName, createNotEmptyBag(environment), createNotEmptyWishList(environment));
		}
		// zwiekszanie liczniak
		studentCounter++;
		return student;
	}

	/**
	 * Metoda zwraca torbe z ksiazkami
	 * @param environment
	 * @return
	 */
	private List<Book> createNotEmptyBag(Environment environment) {
		List<Book> bag = new ArrayList<Book>();
		for (int i = 0; i < random.nextInt(3) + 1; i++) {
			bag.add(new Book(getRandomTitle(environment), currebtBookCode));
			currebtBookCode--;
		}
		return bag;
	}

	private List<Title> createNotEmptyWishList(Environment environment) {
		List<Title> wishList = new ArrayList<Title>();
		for (int i = 0; i < random.nextInt(3) + 1; i++) {
			wishList.add(getRandomTitle(environment));
		}
		return wishList;
	}

	/**
	 * Metoda zwraca losowy tytul ksiazki
	 * @param environment
	 * @return
	 */
	private Title getRandomTitle(Environment environment) {
		// losowanie polki
		Bookshelf bookshelf = environment.getBookshelfs().get(random.nextInt(environment.getBookshelfs().size()));
		// losowanie ksiazki
		Title title = new ArrayList<Title>(bookshelf.getValidTitles()).get(random.nextInt(bookshelf.getValidTitles().size()));
		return title;
	}
}
