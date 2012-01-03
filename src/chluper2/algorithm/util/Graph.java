/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.algorithm.util;

import chluper2.environment.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Graf sluzy do odnajdowania najkrotszych sciezek do celu, lub okreslania odleglosci.
 * Nie jest uwzgledniany koszt skretu.
 * @author damian
 */
public class Graph {

	// koszt ruchu
	private final int MOVE_COST = 1;
	// mapa srodowiska
	private final Position[][] map;
	// wielkosc srodowiska
	private final int environmentWidth;
	private final int environmentHeight;
	// wezly poprzedzajace
	private final Map<Position, Position> previouses = new HashMap<Position, Position>();
	// koszty ruchu
	private final Map<Position, Integer> costs = new HashMap<Position, Integer>();
	// kolejka
	private final Queue<Position> queue = new LinkedList<Position>();
	// zrodlo
	private final Position source;

	/**
	 * Tworzy graf,
	 * @param environmentWidth szerokosc srodowiska
	 * @param environmentHeight wysokosc srodowiska
	 * @param nodes zbior punktow ktore maja byc wezlami (po ktorych mozna sie poruszac)
	 * @param source
	 */
	public Graph(int environmentWidth, int environmentHeight, Set<Position> nodes, Position source) {
		this.environmentWidth = environmentWidth;
		this.environmentHeight = environmentHeight;
		this.source = source;
		// tworzenie mapy
		map = new Position[environmentWidth][environmentHeight];
		// uzupelnianie
		for (Position node : nodes) {
			map[node.getX()][node.getY()] = node;
		}
		// tworzene wezla startowego
		if (map[source.getX()][source.getY()] == null) {
			throw new RuntimeException("Brak zrodla w wezlach mapy");
		}
		// oznaczanie wezla poczatkowego
		costs.put(source, 0);
		queue.add(source);
		// wyliczanie
		calculate();
	}

	/**
	 * Metoda zwraca odleglosc do danego polozenia
	 * @param position polozenie
	 * @return odleglosc lub -1 jesli sie nie da dojsc
	 */
	public int getDistanceTo(Position position) {
		if (costs.containsKey(position)) {
			return costs.get(position);
		} else {
			return -1;
		}
	}

	/**
	 * Metoda zwraca sciezke do danego punktu.
	 * Sciezka to lista kolejnych punktow, nie zawiera zrodla
	 * @param polozenie
	 * @return sciezke lub null jesli sie nie da dojsc
	 */
	public List<Position> getPathTo(Position position) {
		// okreslanie czy da sie dojsc
		if (!previouses.containsKey(position)) {
			return null;
		}
		LinkedList<Position> path = new LinkedList<Position>();
		Position currentPosition = position;
		// dopuki sa poprzednicy i nie jest to zrodlo
		while (currentPosition != null && !source.equals(currentPosition)) {
			// dodawanie na poczatek
			path.addFirst(currentPosition);
			// pobieranie poprzednika
			currentPosition = previouses.get(currentPosition);
		}
		return path;
	}

	/**
	 * wyliczanie kosztow i wyznaczanie poprzednikow
	 */
	private void calculate() {
		// tak dlugo jak mozna gdzies dojsc
		while (!queue.isEmpty()) {
			// aktualnie rozpatrywany wezel
			Position currPosition = queue.poll();
			// poszukiwanie sasiadow
			for (Position neighbour : getNeighbours(currPosition)) {
				// jesli sasiad jeszcze nie byl rozpatrywany
				if (!costs.containsKey(neighbour)) {
					// przypisywanie poprzednika
					previouses.put(neighbour, currPosition);
					// przypisywanie kosztu
					costs.put(neighbour, costs.get(currPosition)  + MOVE_COST);
					// dodanie do kolejki
					queue.add(neighbour);
				}
			}
		}
	}

	/**
	 * Metoda zwraca sasiadow danej pozycji
	 * @param position
	 * @return
	 */
	private Set<Position> getNeighbours(Position position) {
		Set<Position> result = new HashSet<Position>();
		// wspolzedne
		int x = position.getX();
		int y = position.getY();
		// po kolei sprawdzanie kazdej
		if (x > 0) {
			if (map[x-1][y] != null) {
				result.add(map[x-1][y]);
			}
		}
		if (y > 0) {
			if (map[x][y-1] != null) {
				result.add(map[x][y-1]);
			}
		}
		if (x < environmentWidth - 1) {
			if (map[x+1][y] != null) {
				result.add(map[x+1][y]);
			}
		}
		if (y < environmentHeight - 1) {
			if (map[x][y+1] != null) {
				result.add(map[x][y+1]);
			}
		}
		return result;
	}


}
