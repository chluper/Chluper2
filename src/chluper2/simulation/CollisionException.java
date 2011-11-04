/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.simulation;

import chluper2.environment.Element;
import chluper2.environment.Position;

/**
 * Wyjatek kolizji
 * @author damian
 */
public class CollisionException extends SimulationException {

	/**
	 * Blad kolizji
	 * @param simuletionTick takt symulacji
	 * @param el0
	 * @param el1
	 * @param position
	 */
	public CollisionException(int simuletionTick, Element el0, Element el1, Position position) {
		super(simuletionTick, "Nastopila kolizja pomiedzy elementami: " + el0 + " i: " + el1 + " na polu: " + position);
	}
}
