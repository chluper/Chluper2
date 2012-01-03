/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.student;

import chluper2.environment.Book;
import chluper2.environment.Desk;
import chluper2.environment.Environment;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Student;

/**
 * Domyslny algorytm studenta
 * @author damian
 */
public class DefaultStudentAlgorithm implements StudentAlgorithm {

	// stany
	private enum State {

		GO_TO_DESK,
		BOOK_SERVICE,
		GO_TO_EXIT,
	}
	private final String deskName;
	private State currentState = State.GO_TO_DESK;

	/**
	 * Konstruktor
	 * @param deskName nazwa biurka do ktorego bedzie podazal student
	 */
	public DefaultStudentAlgorithm(String deskName) {
		this.deskName = deskName;
	}

	public StudentAction decide(Student controlledStudent, Environment environment) {
		Desk targetDesk = environment.getDeskByName(deskName);
		// w zaleznosci od stanu
		switch (currentState) {
			case GO_TO_DESK:
				if (!targetDesk.getStudentPadPosition().equals(controlledStudent.getPosition())) {
					// jesli jeszcze nie doszlismy to idziemy
					return new StudentAction(StudentActionType.GO_TO, targetDesk.getStudentPadPosition());
				}
				if (!targetDesk.getCorrectStudentDirection().equals(controlledStudent.getDirection())) {
					// jesli student nie jest obrucony w dobra strona
					return new StudentAction(StudentActionType.TURN, targetDesk.getCorrectStudentDirection());
				}
				// zmiana stanu
				currentState = State.BOOK_SERVICE;
				return new StudentAction(StudentActionType.LOGIN);
			case BOOK_SERVICE:
				if (!controlledStudent.isSatisfied()) {
					// wypozyczanie ksiazek
					for (Book book : targetDesk.getBooks()) {
						// jesli na bourku jest cos z listy zyczen
						if (targetDesk.getWishList().contains(book.getTitle())) {
							// pakowanie do torby
							return new StudentAction(StudentActionType.TAKE, book);
						}
					}

					// oddawanie ksiazek
					for (Book book : controlledStudent.getBag()) {
						// jesli w plecaku jest cos czego nie ma na liscie zyczen
						if (!controlledStudent.getWishList().contains(book.getTitle())) {
							// jesli biurko nie jest zajete
							if (targetDesk.getBooks().size() < targetDesk.getBooksLimit() - 1) {
								return new StudentAction(StudentActionType.PUT, book);
							} else {
								// czekamy na zwolnienie
								return new StudentAction(StudentActionType.WAIT);
							}
						}
					}
					// czekanie na ksiazki
					return new StudentAction(StudentActionType.WAIT);
				}
				// student usatysfakcjonowany wiec wraca do wyjscia
				currentState = State.GO_TO_EXIT;
				return new StudentAction(StudentActionType.LOGOUT);
			case GO_TO_EXIT:
				// wyluskiwanie pozycji wyjscia
				Position exit = environment.getMarkersByType(Marker.STUDENT_EXIT).get(0).getPosition();
				if (!exit.equals(controlledStudent.getPosition())) {
					// jesli jeszcze nie doszlismy to idziemy
					return new StudentAction(StudentActionType.GO_TO, exit);
				}
				// jesli doszlismy, to wychodzimy
				return new StudentAction(StudentActionType.LEAVE);
			default:
				throw new RuntimeException("Nieobslugiwany stan");
		}
	}
}
