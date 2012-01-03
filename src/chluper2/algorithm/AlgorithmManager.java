/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.algorithm;

import chluper2.environment.CustomMarkersManager;
import chluper2.environment.Environment;
import chluper2.environment.Robot;

/**
 * Interfejs odpowiedzialny za tworzenie algorytmow i koordynatora.
 * Wszystkie metody sa wywolywane przed startem symulacji
 * @author damian
 */
public interface AlgorithmManager {

	/**
	 * Metoda wywolywana na poczatku
	 * @param environment srodowisko
	 * @param markersManager obiekt do zarzadzania znacznikami
	 */
	public void init(Environment environment, CustomMarkersManager markersManager);

	/**
	 * Metoda wywolywana aby uzyskac algorytm dla konkretnego robota
	 * @param controlledRobot robot dla ktorego ma zostac utworzony algorytm
	 * @param environment srodowisko
	 * @return utworzony algorytm
	 */
	public Algorithm createAlgorithm(Robot controlledRobot, Environment environment);

	/**
	 * Metoda wywolywana aby uzyskac koordynatora algorytmow
	 * @param environment
	 * @return koordynator lub null jesli nie ma takiego byc
	 */
	public Coordinator createCoordinator(Environment environment);
}
