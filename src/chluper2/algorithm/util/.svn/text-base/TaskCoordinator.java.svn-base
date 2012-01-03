/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.algorithm.util;

import chluper2.algorithm.Coordinator;
import chluper2.environment.Robot;

/**
 * Interfejs koordynatora przydzielajacego zadania
 * @author damian
 */
public interface TaskCoordinator extends Coordinator {

	/**
	 * Metoda wywolywana przez algorytm robota aby uzyskac aktualnie wykonywane zadanie
	 * @param robot robot dla ktorego ma byc przydzielone zadanie
	 * @return zadanie lub null jesli robot ma poczekac
	 */
	public Task requireCurrentTask(Robot robot);

}
