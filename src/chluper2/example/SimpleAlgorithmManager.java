/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.example;

import chluper2.algorithm.Algorithm;
import chluper2.algorithm.AlgorithmManager;
import chluper2.algorithm.Coordinator;
import chluper2.environment.CustomMarkersManager;
import chluper2.environment.Environment;
import chluper2.environment.Robot;

/**
 *
 * @author damian
 */
public class SimpleAlgorithmManager implements AlgorithmManager {

	public void init(Environment environment, CustomMarkersManager markersManager) {
	}

	public Algorithm createAlgorithm(Robot controlledRobot, Environment environment) {
		return new SimpleAlgorithm();
	}

	public Coordinator createCoordinator(Environment environment) {
		return null;
	}



}
