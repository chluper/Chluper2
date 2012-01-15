/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2;

import chluper2.algorithm.AlgorithmManager;
import chluper2.environment.Environment;
import chluper2.environment.creator.EnvironmentConfigurator;
import chluper2.example.MTaskAlgorithmManager;
import chluper2.example.SimpleAlgorithmManager;
import chluper2.example.SimpleTaskAlgorithmManager;
import chluper2.example.TwoKindsRobotsTaskAlgorithManager;
import chluper2.example.VerySimpleTaskAlgorithmManager;
import chluper2.gui.SimulationFrame;
import chluper2.simulation.Simulator;
import chluper2.simulation.SimulatorListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zbychu
 */
public class NewMain {

    // private static AlgorithmManager algorithmManager;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        List<Integer> librarySizeList = Arrays.asList(new Integer[]{2, 3, 4});
        List<Integer> desksNumber = Arrays.asList(new Integer[]{1, 2, 3, 4, 5});//, 6}); //6!!
        List<Integer> robotsNumber = Arrays.asList(new Integer[]{1, 2, 3});
        List<Integer> cacheSize = Arrays.asList(new Integer[]{1, 2, 3});
        List<AlgorithmManager> algorithms = new LinkedList();// = Arrays.asList(new AlgorithmManager[]{new MTa})
        algorithms.add(new TwoKindsRobotsTaskAlgorithManager());
        algorithms.add(new SimpleTaskAlgorithmManager());
        algorithms.add(new VerySimpleTaskAlgorithmManager());
        algorithms.add(new MTaskAlgorithmManager());

        for (AlgorithmManager algorithmManager : algorithms) {
            for (Integer librarySize : librarySizeList) {
                for (Integer numberDesk : desksNumber) {
                    for (Integer robotNumber : robotsNumber) {
                        for (Integer cache : cacheSize) {
                            //sta;la - byla 5
                            int stala = 4;
                            EnvironmentConfigurator configurator = new EnvironmentConfigurator(numberDesk, robotNumber);
                            configurator.setBookshefRowNumber(librarySize);
                            configurator.setBookshelfColumnNumber(stala * librarySize);
                            configurator.setBookshelfInRow(stala);
                            configurator.setRobotCacheSize(cache);

                            //      AlgorithmManager algorithmManager = new SimpleAlgorithmManager();
                            // AlgorithmManager algorithmManager = new SimpleTaskAlgorithmManager();

                            Simulator simulator = new Simulator(configurator, algorithmManager);

                            simulator.setDeleyBetweenSteps(0);
                            simulator.setContinousSimulation(true);
                            SimulatorListener listener = new savingListener(simulator, librarySize, numberDesk, robotNumber, cache, algorithmManager.getClass().getName());
                            simulator.addSimulatorListener(listener);

                            simulator.getThread().join();

                        }
                    }
                }
            }
        }
    }
}
