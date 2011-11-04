/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chluper2.gui;

import chluper2.environment.Direction;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * Klasa do przechowywania obrazka elementu
 * @author damian
 */
public class ElementImage {

	// sciezka do obrazkow
	private static final String IMAGE_DIR_PATH = "/res/graphics/";
	// grafika elementu
	private final Map<Direction, BufferedImage> images = new HashMap<Direction, BufferedImage>();

	/**
	 * Tworzy obrazek
	 * @param imageFilePath sciezka do obrazka
	 * @param alpha przezroczystosc
	 * @param elementSize wielkosc obrazka
	 */
	public ElementImage(String imageFilePath, float alpha, int elementSize) {
		// wczytywanie obrazka
		ImageIcon image = new ImageIcon(getClass().getResource(IMAGE_DIR_PATH + imageFilePath));
		images.put(Direction.SOUTH, createImage(image.getImage(), Direction.SOUTH, alpha, elementSize));
		images.put(Direction.EAST, createImage(image.getImage(), Direction.EAST, alpha, elementSize));
		images.put(Direction.NORTH, createImage(image.getImage(), Direction.NORTH, alpha, elementSize));
		images.put(Direction.WEST, createImage(image.getImage(), Direction.WEST, alpha, elementSize));
	}

	/**
	 * Metoda tworzy obrazek, dodaje alfe i go obraca
	 * @param image obrazek oryginalny
	 * @param alpha alfa
	 * @param angle kont obrotu
	 * @param elementSize wielkosc obrazka
	 * @return
	 */
	public BufferedImage createImage(Image image, Direction direction, float alpha, int elementSize) {

		// tworzenie obrazka
		BufferedImage bi = new BufferedImage(elementSize, elementSize, BufferedImage.TYPE_INT_ARGB);
		// wyluskiwanie grafiki
		Graphics2D g = bi.createGraphics();

		// dodawanie przezroczytsosci
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC, alpha);
		g.setComposite(ac);
		
		// obracanie obrazka
		int x = 0;
		int y = 0;
		double angle = 0;
		switch (direction) {
			case SOUTH:
				x = 0;
				y = 0;
				angle = 0;
				break;
			case WEST:
				x = 0;
				y = -elementSize;
				angle = 90;
				break;
			case NORTH:
				x = -elementSize;
				y = -elementSize;
				angle = 180;
				break;
			case EAST:
				x = -elementSize;
				y = 0;
				angle = 270;
				break;
		}
		g.rotate(Math.toRadians(angle));

		// wrysowanie oryginalnego obrazka
		g.drawImage(image, x, y, elementSize, elementSize, null);
		g.dispose();

		return bi;
	}

	/**
	 * Metoda zwraca obrazek obtucony w odpowiednia strone
	 * @param direction
	 * @return
	 */
	public Image getImage(Direction direction) {
		return images.get(direction);
	}

}
