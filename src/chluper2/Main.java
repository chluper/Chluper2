/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2;

import chluper2.algorithm.AlgorithmManager;
import chluper2.environment.creator.EnvironmentConfigurator;
import chluper2.example.SimpleTaskAlgorithmManager;
<<<<<<< HEAD
import chluper2.example.VerySimpleTaskAlgorithmManager;
=======
>>>>>>> 7b5997d6edaab3c3314bfde9b92771f188fbb70f
import chluper2.gui.SimulationFrame;
import chluper2.simulation.Simulator;

/**
 *
 * @author damian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

		EnvironmentConfigurator configurator = new EnvironmentConfigurator(3, 2);


//		AlgorithmManager algorithmManager = new SimpleAlgorithmManager();

	//	AlgorithmManager algorithmManager = new SimpleTaskAlgorithmManager();
                AlgorithmManager algorithmManager = new VerySimpleTaskAlgorithmManager();



		Simulator simulator = new Simulator(configurator, algorithmManager);

		new SimulationFrame(simulator);
    }

}
