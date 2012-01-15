/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2;

import chluper2.algorithm.AlgorithmManager;
import chluper2.environment.creator.EnvironmentConfigurator;
import chluper2.example.MTaskAlgorithmManager;
import chluper2.example.SimpleTaskAlgorithmManager;
import chluper2.example.TwoKindsRobotsTaskAlgorithManager;
import chluper2.example.VerySimpleTaskAlgorithmManager;
import chluper2.gui.SimulationFrame;
import chluper2.simulation.Simulator;
import chluper2.simulation.SimulatorListener;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author damian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //EnvironmentConfigurator configurator = new EnvironmentConfigurator(1, 3);




        EnvironmentConfigurator configurator = new EnvironmentConfigurator(6, 1);

        //3;1;3;2
        //2;1;3;1
        //2;1;3;2


        // librarySize + ";" + numberDesk + ";" + robotNumber + ";" + cache
        int librarySize = 2;
        configurator.setBookshefRowNumber(librarySize);
        configurator.setDeskNumber(3);
        configurator.setBookshelfColumnNumber(5 * librarySize);
        configurator.setBookshelfInRow(5);
        configurator.setRobotNumber(1);
        configurator.setRobotCacheSize(3);

//		AlgorithmManager algorithmManager = new SimpleAlgorithmManager();

        //	AlgorithmManager algorithmManager = new SimpleTaskAlgorithmManager();
//                AlgorithmManager algorithmManager = new VerySimpleTaskAlgorithmManager();
//        AlgorithmManager algorithmManager = new TwoKindsRobotsTaskAlgorithManager();


        	AlgorithmManager algorithmManager = new SimpleTaskAlgorithmManager();
      //  AlgorithmManager algorithmManager = new MTaskAlgorithmManager();




        final Simulator simulator = new Simulator(configurator, algorithmManager);
//
       // simulator.setDeleyBetweenSteps(0);
       // simulator.setContinousSimulation(true);
        
//                simulator.addSimulatorListener(new SimulatorListener() {
//                    
//            public void nextStep() {
//               // System.out.println(simulator.getEnvironmentCopy().getAverageStudentServiceTime());
//                if(simulator.getEnvironmentCopy().getServicedStudentNumber() == 100){
//                    simulator.cancel();
//                    System.out.println(simulator.getEnvironmentCopy().getAverageStudentServiceTime());
//                    System.out.println("obsluzono: " + simulator.getEnvironmentCopy().getServicedStudentNumber());
//                    
//                }
//            }
//
//            public void simulationError(Throwable t) {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//        });
//                while(true)
//                if(simulator.getEnvironmentCopy().getServicedStudentNumber() == 100) {
//                simulator.cancel();
//                System.out.println(simulator.getEnvironmentCopy().getAverageStudentServiceTime());
//                
//                } else {
//                    System.out.println("obsluzeni studenci: " + simulator.getEnvironmentCopy().getServicedStudentNumber());
//                }
        new SimulationFrame(simulator);

    }
}
