/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.gui;

import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.Desk;
import chluper2.environment.Element;
import chluper2.environment.Environment;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Robot;
import chluper2.environment.Student;
import chluper2.environment.Title;
import chluper2.environment.Wall;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

/**
 * Panel ze srodowiskiem
 * @author damian
 */
public class EnvironmentPanel extends JPanel {

	// domyslna wielkosc elemetu
	private static final int DEFAULT_ELEMENT_SIZE = 32;
	// obrazki elementow
	private final Map<String, ElementImage> elementImages = new HashMap<String, ElementImage>();
	// wielkosc pojedynczego elemetu
	private int elementSize = DEFAULT_ELEMENT_SIZE;
	// czy pokazywac znaczniki
	private boolean showMarkers = true;
	// ostatnia kopia srodowiska
	private Environment currentEnvironment = null;
	// tlo
	private Image backgroundImage = null;
	// znaczniki
	private Image markersImage = null;
	// ostatnie zdarzenie myszy
	private MouseEvent lastMouseEvent = null;

	/**
	 * 
	 * Konstruktor
	 */
	public EnvironmentPanel(Environment environment) {
		// wczytywanie obrazkow
		fillElementImages();
		// nowe srodowisko
		newEnvironment(environment);

		// sluchacz zdarzen myszy
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// przechwytywanie zdarzenia w celu podmiany tooltipa
				lastMouseEvent = e;
				refreshToolTip();
			}
		});

		// wymuszanie pokazaywania podpowiedzi od razu i bez konca
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		ToolTipManager.sharedInstance().setInitialDelay(0);
		ToolTipManager.sharedInstance().setReshowDelay(100);
	}

	/**
	 * Metoda okresla czy maja byc pokazywane znaczniki
	 * @param showMarkers
	 */
	public void setShowMarkers(boolean showMarkers) {
		this.showMarkers = showMarkers;
		refresh();
	}

	/**
	 * Okresla wielkosc w pikselach pojedynczego elementu
	 * Wywolywac W EDT
	 * @param elementSize
	 */
	public void setElementSize(int elementSize) {
		this.elementSize = elementSize;
		fillElementImages();
		// tlo i znaczniki
		backgroundImage = createBackgroundImage(currentEnvironment);
		markersImage = createMarkersImage(currentEnvironment);
		// nowa wielkosc panelu
		setPanelSize();
		// odswiezenie
		refresh();
	}

	/**
	 * Metoda wywolywana kiedy pojawi sie nowe srodowisko,
	 * @param environment nowa wersja srodowiska
	 */
	public void newEnvironment(Environment environment) {
		// srodowisko
		currentEnvironment = environment;
		// tlo i znaczniki
		backgroundImage = createBackgroundImage(currentEnvironment);
		markersImage = createMarkersImage(currentEnvironment);
		// nowa wielkosc panelu
		setPanelSize();
		// odswiezenie
		refresh();
	}

	/**
	 * Metoda wywolywana kiedy pojawi sie nowy krok
	 * @param environment kopia srodowiska z nowym krokiem
	 */
	public void newStep(Environment environment) {
		// srodowisko
		currentEnvironment = environment;
		// odswiezenie
		refresh();
	}

	/**
	 * Metoda wymusza odswiezenie widoku srodowiska
	 */
	private void refresh() {
		// ponowne narysowanie
		repaint();
		// tooltip
		refreshToolTip();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (currentEnvironment != null) {
			// rysowanie elementow
			g.drawImage(backgroundImage, 0, 0, null);
			if (showMarkers) {
				g.drawImage(markersImage, 0, 0, null);
			}
			// elemnty zmienne
			g.drawImage(createChangingImage(currentEnvironment), 0, 0, null);
		}
	}

	/**
	 * Metoda wywolywana kiedy ma sie zmienic podpowiedz pod mysza
	 */
	private void refreshToolTip() {
		if (lastMouseEvent != null) {
			// wyluskiwanie polozenia
			Position position = new Position(lastMouseEvent.getPoint().x / elementSize, lastMouseEvent.getPoint().y / elementSize);
			// ustawianie podpowiedzi
			setToolTipText(createToolTip(currentEnvironment, position));
			// wymuszenie wyswietlenia
			ToolTipManager.sharedInstance().mouseEntered(lastMouseEvent);
		}
	}

	/**
	 * Metoda tworzy podpowiedz dla okreslonego polozenia na ekranie
	 * @param environment srodowisko
	 * @param position pozycja spod ktorej maja byc wyluskane informacje
	 * @return
	 */
	private String createToolTip(Environment environment, Position position) {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		// polozenie
		builder.append("<b>" + position + "</b>");
		// przejscie po elementach
		for (Element element : environment.getElementsAt(position)) {
			switch (element.getElementType()) {
				case WALL:
					builder.append("<br><b>Wall</b>");
					break;
				case BOOKSHELF:
					Bookshelf bookshelf = (Bookshelf) element;
					builder.append("<br><b>Bookshelf</b>: <i>" + bookshelf.getName() + "</i>");
					builder.append("<br>titles: <i>");
					for (Title title : bookshelf.getValidTitles()) {
						builder.append(title.getTitle() + ", ");
					}
					builder.append("</i>");
					break;
				case DESK:
					Desk desk = (Desk) element;
					builder.append("<br><b>Desk</b>: <i>" + desk.getName() + "</i>");
					builder.append("<br>books: <i>");
					for (Book book : desk.getBooks()) {
						builder.append(book.getTitle().getTitle() + "/" + book.getUniqueCode() + ", ");
					}
					builder.append("</i>");
					builder.append("<br>wishlist: <i>");
					for (Title title : desk.getWishList()) {
						builder.append(title.getTitle() + ", ");
					}
					builder.append("</i>");
					break;
				case MARKER:
					Marker marker = (Marker) element;
					builder.append("<br><b>Marker</b>: <i>" + marker.getType() + "</i>");
					break;
				case ROBOT:
					Robot robot = (Robot) element;
					builder.append("<br><b>Robot</b>: <i>" + robot.getName() + "</i>");
					builder.append("<br>cache: <i>");
					for (Book book : robot.getCache()) {
						builder.append(book.getTitle().getTitle() + "/" + book.getUniqueCode() + ", ");
					}
					builder.append("</i>");
					break;
				case STUDENT:
					Student student = (Student) element;
					builder.append("<br><b>Student</b>: <i>" + student.getName() + "</i>");
					builder.append("<br>wishlist: <i>");
					for (Title title : student.getWishList()) {
						builder.append(title.getTitle() + ", ");
					}
					builder.append("</i>");
					builder.append("<br>bag: <i>");
					for (Book book : student.getBag()) {
						builder.append(book.getTitle().getTitle() + "/" + book.getUniqueCode() + ", ");
					}
					builder.append("</i>");
					builder.append("<br>service time: <i>" + student.getServiceTime() + "</i>");
					builder.append(student.isSatisfied() ? "<br>satisfied" : "<br>not satisfied");
					break;
			}
		}
		builder.append("</html>");
		return builder.toString();
	}

	/**
	 * Zwraca obrazek srodowiska
	 * @param environment
	 * @return
	 */
	private Image createBackgroundImage(Environment environment) {
		BufferedImage bi = new BufferedImage(environment.getWidth() * elementSize, environment.getHeight() * elementSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		// rysowanie scian
		for (Wall wall : environment.getWalls()) {
			drawElement(g, wall, elementImages.get("wall"));
		}
		// rysowanie biurek
		for (Desk desk : environment.getDesks()) {
			drawElement(g, desk, elementImages.get("desk"));
		}
		// rysowanie polek
		for (Bookshelf bookshelf : environment.getBookshelfs()) {
			drawElement(g, bookshelf, elementImages.get("bookshelf"));
		}
		g.dispose();
		return bi;
	}

	/**
	 * Zwraca obrazek znacznikow na srodowiska
	 * @param environment
	 * @return
	 */
	private Image createMarkersImage(Environment environment) {
		BufferedImage bi = new BufferedImage(environment.getWidth() * elementSize, environment.getHeight() * elementSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		// rysowanie znacznikow
		for (Marker marker : environment.getMarkers()) {
			if (marker.getType().equals(Marker.ROBOT_AREA)) {
				drawElement(g, marker, elementImages.get("marker1"));
			} else if (marker.getType().equals(Marker.STUDENT_AREA)) {
				drawElement(g, marker, elementImages.get("marker2"));
			} else if (marker.getType().equals(Marker.STUDENT_ENTRY)) {
				drawElement(g, marker, elementImages.get("entry"));
			} else if (marker.getType().equals(Marker.STUDENT_EXIT)) {
				drawElement(g, marker, elementImages.get("exit"));
			}
		}
		g.dispose();
		return bi;
	}

	/**
	 * Zwraca obrazek robotow i studentow
	 * @param environment
	 * @return
	 */
	private Image createChangingImage(Environment environment) {
		BufferedImage bi = new BufferedImage(environment.getWidth() * elementSize, environment.getHeight() * elementSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		// rysowanie scian
		for (Robot robot : environment.getRobots()) {
			drawElement(g, robot, elementImages.get("robot"));
		}
		// rysowanie studentow
		for (Student student : environment.getStudents()) {
			String imgageName = "student" + student.getName().split("_")[1];
			drawElement(g, student, elementImages.get(imgageName));
		}
		g.dispose();
		return bi;
	}

	/**
	 * Metooda rysuje element w odpowiednim miejscu
	 * @param g kontekst graficzny na ktorym bedzie rysowane
	 * @param element
	 * @param elementImage
	 */
	private void drawElement(Graphics2D g, Element element, ElementImage elementImage) {
		g.drawImage(elementImage.getImage(element.getDirection()), element.getPosition().getX() * elementSize, element.getPosition().getY() * elementSize, null);
	}

	/**
	 * Metoda ustawia wielkosc paneku na podstawie wielkosci srodowiska
	 * @param environment
	 */
	private void setPanelSize() {
		Dimension d = new Dimension(currentEnvironment.getWidth() * elementSize, currentEnvironment.getHeight() * elementSize);
		setMinimumSize(d);
		setPreferredSize(d);
		setMaximumSize(d);
		setBackground(Color.WHITE);
	}

	/**
	 * Metoda wypelnia mape z elementami
	 */
	private void fillElementImages() {
		elementImages.clear();
		// wczytywanie obrazkow
		elementImages.put("bookshelf", new ElementImage("bookshelf.png", 1, elementSize));
		elementImages.put("desk", new ElementImage("desk.png", 1, elementSize));
		elementImages.put("wall", new ElementImage("wall2.png", 1, elementSize));
		elementImages.put("robot", new ElementImage("robot1.png", 0.7f, elementSize));
		elementImages.put("student1", new ElementImage("person1.png", 0.8f, elementSize));
		elementImages.put("student2", new ElementImage("person2.png", 0.8f, elementSize));
		elementImages.put("student3", new ElementImage("person3.png", 0.8f, elementSize));
		elementImages.put("student4", new ElementImage("person4.png", 0.8f, elementSize));
		elementImages.put("student5", new ElementImage("person5.png", 0.8f, elementSize));
		elementImages.put("student6", new ElementImage("person6.png", 0.8f, elementSize));
		elementImages.put("student7", new ElementImage("person7.png", 0.8f, elementSize));
		elementImages.put("student0", new ElementImage("person8.png", 0.8f, elementSize));
		elementImages.put("entry", new ElementImage("exit2.png", 0.4f, elementSize));
		elementImages.put("exit", new ElementImage("exit1.png", 0.4f, elementSize));
		elementImages.put("marker1", new ElementImage("marker1.png", 0.3f, elementSize));
		elementImages.put("marker2", new ElementImage("marker2.png", 0.3f, elementSize));
	}
}
