/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.algorithm.util;

import chluper2.algorithm.Action;
import chluper2.algorithm.ActionType;
import chluper2.environment.Environment;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Robot;
import java.util.List;
import java.util.Random;

/**
 * Klasa pomocnicza dla algorytmu majaca na celu zabezpieczanie przez zakleszczeniem
 * Jesli robot przez jakis czas sie nie porusza to wymuszony zostaje ruch
 * @author damian
 */
public class DeathLockPreventer {
	// czas po jakim musi nastapic ruch
	private static final int MAX_STEP_WITHOUT_MOVE = 15;
	// ostatnia pozycja robota
	private Position previousPosition = null;
	// czas (krok symulacji) ostatniej zmiany pozycji
	private int lastPositionChangeTime = 0;
	// random
	private Random random = new Random(11);

	/**
	 * Metoda zabezpieczajaca przez zakleszczeniami.
	 * Jasli robot nie zmieni swojego polozenia przez wystarczajaca ilosc krokow,
	 * to jest zwracana akcja jaka nalezy podjac
	 * @param controlledRobot
	 * @param environment
	 * @return akcja lub null, jesli nic nie trzeba robic
	 */
	public Action prevent(Robot controlledRobot, Environment environment) {
		// jesli pozycja sie zmienila
		if (!controlledRobot.getPosition().equals(previousPosition)) {
			// aktualizacja
			previousPosition = controlledRobot.getPosition();
			lastPositionChangeTime = environment.getSimulationStep();
		} else {
			// okreslenie jak dlugo nie nastapil ruch
			if (environment.getSimulationStep() - lastPositionChangeTime > MAX_STEP_WITHOUT_MOVE) {
				// podejmowanie akcji
				List<Marker> markers = environment.getMarkersByType(Marker.ROBOT_AREA);
				// podazanie do losowego znacznika
				return new Action(ActionType.GO_TO, markers.get(random.nextInt(markers.size())).getPosition());
			}
		}
		return null;
	}

}
