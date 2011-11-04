/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.algorithm;

import chluper2.environment.Environment;
import chluper2.environment.Robot;

/**
 * Interfejs wszystkich algorytmow
 * @author damian
 */
public interface Algorithm {

	/**
	 * Metoda wywolywana w czasie symulacji, aby uzyskac decyzje o akcji od robota
	 * @param controlledRobot robot ktorym sterujemy
	 * @param environment srodowisko
	 * @param coordinator koordynator jesli istnieje lub null jesli go nie ma
	 */
	public Action decide(Robot controlledRobot, Environment environment, Coordinator coordinator);
}
