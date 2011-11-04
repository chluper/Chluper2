/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.algorithm.util;

import chluper2.algorithm.Action;
import chluper2.algorithm.ActionType;
import chluper2.algorithm.Algorithm;
import chluper2.algorithm.Coordinator;
import chluper2.environment.Book;
import chluper2.environment.Environment;
import chluper2.environment.Robot;

/**
 * Klasa algorytmu wykonujacego zadania
 * @author damian
 */
public class TaskAlgorithm implements Algorithm {

	private final DeathLockPreventer preventer = new DeathLockPreventer();

	public Action decide(Robot controlledRobot, Environment environment, Coordinator coordinator) {
		// zabezpieczenie przed zakleszczeniami
		if (preventer.prevent(controlledRobot, environment) != null) {
			return preventer.prevent(controlledRobot, environment);
		}
		// prosba o zadanie
		Task currentTask = ((TaskCoordinator) coordinator).requireCurrentTask(controlledRobot);
		// wykonywanie zadania
		//jesli to null to czekamy
		if (currentTask == null) {
			return new Action(ActionType.WAIT);
		}
		// jesli jest cos w kieszeni
		for (Book book : controlledRobot.getCache()) {
			if (book.getTitle().equals(currentTask.getTitle())) {
				// odwozenie ksiazki na polke lub biurko
				if (currentTask.getTargetBookshelf() != null) {
					return new Action(ActionType.DELIVER_BOOK, book, currentTask.getTargetBookshelf());
				} else {
					return new Action(ActionType.DELIVER_BOOK, book, currentTask.getTargetDesk());
				}
			}
		}
		// jesli nie, to jedziemy po to
		if (currentTask.getSourceBookshelf() != null) {
			return new Action(ActionType.GET_BOOK, currentTask.getTitle(), currentTask.getSourceBookshelf());
		} else {
			// wyszukiwanie ksiazki na biurku
			for (Book book : currentTask.getSourceDesk().getBooks()) {
				if (book.getTitle().equals(currentTask.getTitle())) {
					return new Action(ActionType.GET_BOOK, book, currentTask.getSourceDesk());
				}
			}
		}
		// to sie nie powinno zdarzyc
		throw new RuntimeException("Robot: " + controlledRobot +  " nie jest w stanie wykonac zadania: " + currentTask);
	}

}
