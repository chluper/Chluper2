/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.example;

import chluper2.algorithm.Algorithm;
import chluper2.algorithm.util.CoordinatorAdapter;
import chluper2.algorithm.util.Task;
import chluper2.algorithm.util.TaskCoordinator;
import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.CustomMarkersManager;
import chluper2.environment.Desk;
import chluper2.environment.Environment;
import chluper2.environment.Robot;
import chluper2.environment.Student;
import chluper2.environment.Title;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author zbychu
 */
public class VerySimpleTaskCoordinator extends CoordinatorAdapter implements TaskCoordinator {

	// kolejka zadan
	private final List<Task> queue = new LinkedList<Task>();
	// mapa robotow i zadan
	private final Map<Robot, Task> currentTasks = new HashMap<Robot, Task>();
        
        //mapa zadan dla robotow?
        private final Map<Robot, LinkedList<Task>> halfTasks = new HashMap<Robot,  LinkedList<Task>>();
        
        
    public void init(Environment environment, CustomMarkersManager markersManager) {
        for(Robot robot : environment.getRobots()){
            halfTasks.put(robot, new LinkedList<Task>());
        }
               
    }
    
	public Task requireCurrentTask(Robot robot) {
		// jesli mamy zadanie
		if (currentTasks.get(robot) != null) {
			return currentTasks.get(robot);
		}
		// jesli nie to pozyskujemy
                Task task = null;
                if(queue.size()>0)
		task = queue.remove(0);
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
		queue.add(new Task(desk, environment.getBookshelfByTitle(book.getTitle()), book.getTitle()));
	}

	@Override
	public void studentPutWishlist(Student student, Desk desk, List<Title> wishlist, Environment environment) {
		// tworzenie zadan i dodawanie do kolejki
		for (Title title : wishlist) {
			queue.add(new Task(environment.getBookshelfByTitle(title), desk, title));
		}
	}

	@Override
	public void robotPutBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment) {
		// konczenie zadania - albo zamiana
            System.out.println("POLOZYLEM KSIONSZKE");
		currentTasks.remove(robot);
                
            if(halfTasks.get(robot).size()>0)
                currentTasks.put(robot, halfTasks.get(robot).removeFirst());                
                
                
	}

	@Override
	public void robotPutBook(Robot robot, Desk desk, Book book, Environment environment) {
		// konczenie zadania - albo zamiana - polozenie ksiazki na biurku
                        currentTasks.remove(robot);

            if(halfTasks.get(robot).size()>0)
                currentTasks.put(robot, halfTasks.get(robot).removeFirst());
                
            System.out.println("POLOZYLEM KSIAZKE");
            System.out.println("MAM: "+ halfTasks.get(robot).size());

	}

	@Override
	public void studentTakenBook(Student student, Desk desk, Book book, Environment environment) {
		for (Robot robot : currentTasks.keySet()) {
			Task task = currentTasks.get(robot);
			if (desk.equals(task.getSourceDesk()) && book.getTitle().equals(task.getTitle())) {
				System.out.println("Student wzial ksiazke przeznaczona dla robota");
                                
			}
		}
	}
   
    public void robotTakenBook(Robot robot, Desk desk, Book book, Environment environment) {
        //zmiana zadania?
                    if(robot.getCacheSize()>robot.getCache().size()){
                        //pobranie nastepnego zadania
                        Task task = null;
                        for(int i = 0;i<queue.size();i++){
                           if(queue.get(i).getSourceDesk()!=null){
                               //znaleziono ten sam typ zadania
                                    task = queue.remove(i);
                                    break;
                           }
                        }
                        if(task != null){
                        LinkedList tasks = halfTasks.get(robot);
                        tasks.add(currentTasks.get(robot));
                        currentTasks.put(robot, task);
                        halfTasks.put(robot, tasks);
                        }
                    }        
        
        
        
        System.out.println("WSIOLEM KSIAZKE");
    }

    public void robotTakenBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment) {
        //zmiana zadania? - robot wzial ksiazke, ktora mial dostarczyc do biurka
                    if(robot.getCacheSize()>robot.getCache().size()){
                        //pobranie nastepnego zadania
                        Task task = null;
                        for(int i = 0;i<queue.size();i++){
                           if(queue.get(i).getTargetDesk()!=null){
                               //znaleziono ten sam typ zadania
                                    task = queue.remove(i);
                                    break;
                           }
                        }
                        if(task != null){
                        LinkedList tasks = halfTasks.get(robot);
                        tasks.add(currentTasks.get(robot));
                        currentTasks.put(robot, task);
                        halfTasks.put(robot, tasks);
                        }
                    }
        System.out.println("WZIOLEM KSIONSZKE");
    }

    //public void robotPutBook(Robot robot, Desk desk, Book book, Environment environment) {
        //zmiana zadania?
      //  System.out.println("POLOZYLEM KSIAZKE");
    //}

    //public void robotPutBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment) {
    //}

    //public void newStep(Environment environment, CustomMarkersManager markersManager) {
    //}

    //public void robotBeforeAction(Robot robot, Algorithm algorithm, Environment environment) {
    //}
    
    //public void studentTakenBook(Student student, Desk desk, Book book, Environment environment) {
    //}

    //public void studentPutBook(Student student, Desk desk, Book book, Environment environment) {
    //}

    //public void studentPutWishlist(Student student, Desk desk, List<Title> wishlist, Environment environment) {
    //}
    
}
