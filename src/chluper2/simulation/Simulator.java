/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.simulation;

import chluper2.algorithm.AlgorithmManager;
import chluper2.environment.Environment;
import chluper2.environment.concrete.ConcretEnvironment;
import chluper2.environment.creator.EnvironmentConfigurator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Symulator 
 * @author damian
 */
public class Simulator implements Runnable {

	/// aktualna symulacja
	private final Simulation simulation;
	// watek
	private final Thread thread = new Thread(this, "SimulationThread");
	// czas pomiedzy krokami [ms]
	private volatile int deleyBetweenSteps = 200;
	// symulacja wlaczona
	private volatile boolean continousSimulation = false;
	// kroki symulacji do zrobienia
	private volatile int stepsToMake = 0;
	// lista sluchaczy
	private Set<SimulatorListener> listeners = new CopyOnWriteArraySet<SimulatorListener>();

	/**
	 * Metoda tworzy nowy obiekt symulatora
	 * @param configurator konfigurator srodowiska
	 * @param algorithmManager manager algorytmow
	 */
	public Simulator(EnvironmentConfigurator configurator, AlgorithmManager algorithmManager) {
		this.simulation = new Simulation(configurator, algorithmManager);
		thread.start();
	}

	/**
	 * Zwraca opoznienie pomiedzy krokami (dla pracy ciaglej)
	 * @return opoznienie w ms
	 */
	public int getDeleyBetweenSteps() {
		return deleyBetweenSteps;
	}

	/**
	 * Ustawia opoznienie pomiedzy krokomi (dla pracy ciaglej)
	 * @param deleyBetweenSteps opoznienie w ms
	 */
	public void setDeleyBetweenSteps(int deleyBetweenSteps) {
		this.deleyBetweenSteps = deleyBetweenSteps;
	}

	/**
	 * Zwraca true jesli jest uruchomiona ciagla symulacja
	 * @return
	 */
	public boolean isContinousSimulation() {
		return continousSimulation;
	}

	/**
	 * Utawia ilosc krokow do zrobienia
	 * @param stepsToMake
	 */
	public void setStepsToMake(int stepsToMake) {
		this.stepsToMake = stepsToMake;
	}

	/**
	 * Wlacza lub wylacza ciagla symulacje
	 * @param continousSimulation true jesli ma byc wlaczona symulacja
	 */
	public void setContinousSimulation(boolean continousSimulation) {
		this.continousSimulation = continousSimulation;
	}

	/**
	 * Metoda zwaca kopie srodowiska
	 * @return kopia srodowiska lub null
	 */
	public synchronized Environment getEnvironmentCopy() {
		return ((ConcretEnvironment) simulation.getEnvironment()).copy();
	}

	/**
	 * Anulowana symulacja i konczony watek
	 */
	public void cancel() {
		thread.interrupt();
	}

	/**
	 * Metoda dodaje sluchacza
	 * @param listener
	 */
	public void addSimulatorListener(SimulatorListener listener) {
		listeners.add(listener);
	}

	/**
	 * Metoda usuwa sluchacza
	 * @param listener
	 */
	public void removeSimulatorListener(SimulatorListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Pojedynczy krok symulacji
	 */
	private synchronized void singleStep() {
		try {
			// wykonywanie kroku
			simulation.step();
			// powiadamianie sluchaczy
			for (SimulatorListener simulatorListener : listeners) {
				simulatorListener.nextStep();
			}
		} catch (Exception e) {
			// anulowanie dalszego dzialania
			cancel();
			// powiadamianie sluchaczy
			for (SimulatorListener simulatorListener : listeners) {
				simulatorListener.simulationError(e);
			}
			// wyswietlenie
			e.printStackTrace();
		}
	}

	/**
	 * Watek symulacji
	 */
	public void run() {
		try {
			while (!thread.isInterrupted()) {
				// sprawdzanie czy sa kroki do wykonania
				if (stepsToMake > 0) {
					// wykonanie kroku
					singleStep();
					// dekrementacja
					stepsToMake--;
				} else {
					// jesli symulacja uruchomiona
					if (continousSimulation) {
						// wykonanie kroku
						singleStep();
						// powiadamianie sluchaczy
						for (SimulatorListener simulatorListener : listeners) {
							simulatorListener.nextStep();
						}
						// odczekanie
						Thread.sleep(deleyBetweenSteps);
					} else {
						// odczekanie chwili, zeby dac procesorowi zyc
						Thread.sleep(50);
					}
				}

			}
		} catch (InterruptedException e) {
			// konczenie dzialania
		}
	}
}
