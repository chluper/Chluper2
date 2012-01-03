/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.student;

import chluper2.environment.Environment;
import chluper2.environment.Student;
import chluper2.environment.concrete.ConcreteStudent;

/**
 * Klasa odpowiedzialna za zarzadzanie studentami.
 * Tworzy nowych studentow i algorytmy
 * @author damian
 */
public interface StudentManager {

	/**
	 * Metoda wywolywana na poczatku
	 * @param environment
	 */
	public void init(Environment environment);

	/**
	 * Metoda wywolywana aby otrzymac nowego studenta
	 * @param currentTick
	 * @return student lub null jesli nie ma sie pojawic
	 */
	public ConcreteStudent newStudent(Environment environment);

	/**
	 * Metoda do uzyskiwania algorytmu dla studenta
	 * @param student
	 * @param environment
	 * @return
	 */
	public StudentAlgorithm createAlgorithm(Student student, Environment environment);

	/**
	 * Metoda wywolywana kiedy student wyjdzie
	 * @param student
	 * @param currentTick
	 */
	public void studentLeave(ConcreteStudent student, Environment environment);
}
