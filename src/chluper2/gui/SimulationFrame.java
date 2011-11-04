/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.gui;

import chluper2.simulation.Simulator;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Klasa okna do podgladu i zarzadzania symulacja
 * @author damian
 */
public class SimulationFrame {

	// dekorowane okno
	private JFrame frame;

	/**
	 * Tworzy okno
	 * @param simulator obiekt symulatora
	 */
	public SimulationFrame(final Simulator simulator) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				// tworzenie okna
				frame = new JFrame("Chluper");
				frame.add(new SimulationPanel(simulator));
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
