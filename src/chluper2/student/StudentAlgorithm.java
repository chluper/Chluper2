/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.student;

import chluper2.environment.Environment;
import chluper2.environment.Student;

/**
 * Interfejs algorytmu studenta
 * @author damian
 */
public interface StudentAlgorithm {

	/**
	 * Metoda wywolywana w czasie symulacji, aby uzyskac decyzje o akcji od studenta
	 * @param controlledStudent  student ktorym sterujemy
	 * @param environment srodowisko
	 */
	public StudentAction decide(Student controlledStudent, Environment environment);
}
