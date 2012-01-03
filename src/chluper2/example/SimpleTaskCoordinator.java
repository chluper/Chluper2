/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.example;

import chluper2.algorithm.util.CoordinatorAdapter;
import chluper2.algorithm.util.Task;
import chluper2.algorithm.util.TaskCoordinator;
import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.Desk;
import chluper2.environment.Environment;
import chluper2.environment.Robot;
import chluper2.environment.Student;
import chluper2.environment.Title;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Klasa koordynatora zdolnego przydzielac zadania,
 * Zadania wykonywane w kolejnosci nadchodzienia
 * @author damian
 */
public class SimpleTaskCoordinator extends CoordinatorAdapter implements TaskCoordinator {

	// kolejka zadan
	private final Queue<Task> queue = new LinkedList<Task>();
	// mapa robotow i zadan
	private final Map<Robot, Task> currentTasks = new HashMap<Robot, Task>();

	@Override
	public void studentPutBook(Student student, Desk desk, Book book, Environment environment) {
		// tworzenie zadania i dodawanie do kolejki
		queue.add(new Task(desk, environment.getBookshelfByTitle(book.getTitle()), book.getTitle()));
	}

	@Override
	public void studentPutWishlist(Student student, Desk desk, List<Title> wishlist, Environment environment) {
		// tworzenie zadan i dodawanie do kolejki
		for (Title title : wishlist) {
			queue.add(new Task(environment.getBookshelfByTitle(title), desk, title));
		}
	}

	public Task requireCurrentTask(Robot robot) {
		// jesli mamy zadanie
		if (currentTasks.get(robot) != null) {
			return currentTasks.get(robot);
		}
		// jesli nie to pozyskujemy
		Task task = queue.poll();
		// jesli sie cos znalazlo
		if (task != null) {
			currentTasks.put(robot, task);
			return task;
		}
		// jesli nic nie mamy to robot musi poczekac
		return null;
	}

	@Override
	public void robotPutBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment) {
		// konczenie zadania
		currentTasks.remove(robot);
	}

	@Override
	public void robotPutBook(Robot robot, Desk desk, Book book, Environment environment) {
		// konczenie zadania
		currentTasks.remove(robot);
	}

	@Override
	public void studentTakenBook(Student student, Desk desk, Book book, Environment environment) {
		for (Robot robot : currentTasks.keySet()) {
			Task task = currentTasks.get(robot);
			if (desk.equals(task.getSourceDesk()) && book.getTitle().equals(task.getTitle())) {
				System.out.println("Student wzial ksiazke przeznaczona dla robota");
			}
		}
	}


}
