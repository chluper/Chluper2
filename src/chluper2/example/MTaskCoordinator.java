/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.example;

import chluper2.algorithm.util.CoordinatorAdapter;
import chluper2.algorithm.util.Graph;
import chluper2.algorithm.util.Task;
import chluper2.algorithm.util.TaskCoordinator;
import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.CustomMarkersManager;
import chluper2.environment.Desk;
import chluper2.environment.Element;
import chluper2.environment.Environment;
import chluper2.environment.Marker;
import chluper2.environment.Position;
import chluper2.environment.Robot;
import chluper2.environment.Student;
import chluper2.environment.Title;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zbychu
 */
public class MTaskCoordinator extends CoordinatorAdapter implements TaskCoordinator {
    // kolejka zadan

    private final LinkedList<Task> freeTask = new LinkedList<Task>();
    // mapa robotow i zadan
    private final Map<Robot, Task> currentTasks = new HashMap<Robot, Task>();
    //mapa zadan dla robotow?
    private final Map<Robot, LinkedList<Task>> halfTasks = new HashMap<Robot, LinkedList<Task>>();
    private Environment environment;
    private int desk;

    public void init(Environment environment, CustomMarkersManager markersManager) {
        for (Robot robot : environment.getRobots()) {
            halfTasks.put(robot, new LinkedList<Task>());
        }
        this.environment = environment;
        desk = 0;
    }

    public Task requireCurrentTask(Robot robot) {
        // jesli mamy zadanie
        if (currentTasks.get(robot) != null) {
            return currentTasks.get(robot);
        }
        // jesli nie to pozyskujemy
        Task task = null;
        if (freeTask.size() > 0) {
            task = freeTask.remove(0);
        }
        // jesli sie cos znalazlo
        if (task != null) {
            currentTasks.put(robot, task);
            return task;
        }
        // jesli nic nie mamy to robot musi poczekac
        return null;
    }

    public void studentPutBook(Student student, Desk desk, Book book, Environment environment) {
        // tworzenie zadania i dodawanie do kolejki
        freeTask.add(new Task(desk, environment.getBookshelfByTitle(book.getTitle()), book.getTitle()));
    }//last

    @Override
    public void studentPutWishlist(Student student, Desk desk, List<Title> wishlist, Environment environment) {
        // tworzenie zadan i dodawanie do kolejki
        for (Title title : wishlist) {
            freeTask.add(new Task(environment.getBookshelfByTitle(title), desk, title));
        } //first
    }

    @Override
    public void robotPutBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment) {
        // konczenie zadania - albo zamiana
        //   System.out.println("klade na bookshelf - " + robot.getName());
        currentTasks.remove(robot);

        if (halfTasks.get(robot).size() > 0) {
            currentTasks.put(robot, halfTasks.get(robot).removeFirst());
        }
    }

    @Override
    public void robotPutBook(Robot robot, Desk desk, Book book, Environment environment) {
        // konczenie zadania - albo zamiana - polozenie ksiazki na biurku
        //     System.out.println("klade na biurko - " + robot.getName());
        currentTasks.remove(robot);
        if (halfTasks.get(robot).size() > 0) {
            currentTasks.put(robot, halfTasks.get(robot).removeFirst());
        }
    }

    @Override
    public void studentTakenBook(Student student, Desk desk, Book book, Environment environment) {
        for (Robot robot : currentTasks.keySet()) {
            Task task = currentTasks.get(robot);
            if (desk.equals(task.getSourceDesk()) && book.getTitle().equals(task.getTitle())) {
          //      currentTasks.remove(robot);
            }
        }
    }

    public void robotTakenBook(Robot robot, Desk desk, Book book, Environment environment) {
        //zmiana zadania? - robot wzial ksiazke, ktora mial dostarczyc z  biurka do bookshelf
        
        //public Graph(int environmentWidth, int environmentHeight, Set<Position> nodes, Position source) {       
//		Set<Position> nodes = new HashSet<Position>();
//		// dodawanie markerow
//		for (Element element : environment.getMarkersByType(Marker.ROBOT_AREA)) {
//			nodes.add(element.getPosition());
//		}
//		// usuwanie przeszkod poza soba samym
//		for (Element element : environment.getRobots()) {
//			if (!robot.equals(element)) {
//				nodes.remove(element.getPosition());
//			}
//		}        
//        Graph graph = new Graph(environment.getWidth(),environment.getHeight(), nodes, robot.getPosition());
//       
//        System.out.println("koszt: " + graph.getDistanceTo(desk.getPosition()));        
        
        if (robot.getCacheSize() > robot.getCache().size()) {
            //pobranie nastepnego zadania
            Task task = null;
            for (int i = 0; i < freeTask.size(); i++) {
                if (freeTask.get(i).getSourceDesk() != null) {
              //  if(freeTask.get(i).getSourceDesk().equals(desk)) {
                    //znaleziono ten sam typ zadania
                    task = freeTask.remove(i);
                    break;
                }
            }
            if (task != null) {
                LinkedList<Task> tasks = halfTasks.get(robot);
                tasks.add(currentTasks.get(robot));
                currentTasks.put(robot, task);
                halfTasks.put(robot, tasks);
            }
        }



        //  System.out.println("WSIOLEM KSIAZKE");
    }

    public void robotTakenBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment) {
        //zmiana zadania? - robot wzial ksiazke, ktora mial dostarczyc do biurka z bookshelf

        if (robot.getCacheSize() > robot.getCache().size()) {
            //pobranie nastepnego zadania
            Task task = null;
            for (int i = 0; i < freeTask.size(); i++) {
                if (freeTask.get(i).getTargetDesk() != null) {
                    task = freeTask.remove(i);
                    break;
                }
            }
            if (task != null) {
                LinkedList<Task> tasks = halfTasks.get(robot);
                tasks.add(currentTasks.get(robot));
                currentTasks.put(robot, task);
                halfTasks.put(robot, tasks);
            }
        }
    }
}