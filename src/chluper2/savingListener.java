/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2;

import chluper2.simulation.Simulator;
import chluper2.simulation.SimulatorListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zbychu
 */
public class savingListener implements SimulatorListener {

        private Simulator simulator;
        private int librarySize;
        private int numberDesk;
        private int robotNumber;
        private int cache;

        savingListener(Simulator simulator, int librarySize, int numberDesk, int robotNumber, int cache) {
            this.simulator = simulator;
            this.librarySize = librarySize;
            this.robotNumber = robotNumber;
            this.numberDesk = numberDesk;
            this.cache = cache;
        }

        public void nextStep() {
         //   System.out.println("Obsluzono: "+ simulator.getEnvironmentCopy().getServicedStudentNumber());
         //   System.out.println("Par: " + librarySize + ";" + numberDesk + ";" + robotNumber + ";" + cache);
          //  if (simulator.getEnvironmentCopy().getServicedStudentNumber() == 10) {
            if(simulator.getEnvironmentCopy().getSimulationStep()== 10000){
                simulator.cancel();
             //   System.out.println(simulator.getEnvironmentCopy().getAverageStudentServiceTime());
                String s = "" + librarySize + ";" + numberDesk + ";" + robotNumber + ";" + cache + ";" + simulator.getEnvironmentCopy().getSimulationStep()                 
                +";"+simulator.getEnvironmentCopy().getAverageStudentServiceTime() + ";" + "";

                System.out.println(s);
                try {
   
                    save("Chluper2:SimpleTaskAlgorithm", s);
                } catch (IOException ex) {
                    Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public void simulationError(Throwable t) {
            System.out.println("blad!");
            System.out.println("oto blad: " + t.toString());
        }
    

    public static void save(String fileName, String line) throws IOException {
        FileWriter w = new FileWriter(fileName, true);
        w.write(line + "\n");
        w.flush();
        w.close();
    }
}
