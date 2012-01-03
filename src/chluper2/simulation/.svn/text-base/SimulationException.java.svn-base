/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.simulation;

/**
 * Wyjatek ogolnego beldu symulacji.
 * Powinien byc rzucany zawsze, gdy stanie sie cos niedopuszczalnego w czasie symulacji
 * @author damian
 */
public class SimulationException extends RuntimeException {

	/**
	 * Blad symulacji
	 * @param simulationTick
	 * @param message
	 */
	public SimulationException(int simulationTick, String message) {
		super("[krok:" + simulationTick + "] " +  message);
	}
}
