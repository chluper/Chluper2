/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.example;

import chluper2.algorithm.util.CoordinatorAdapter;
import chluper2.algorithm.util.Task;
import chluper2.algorithm.util.TaskCoordinator;
import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
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
 * @author kinga
 */
public class TwoKindsRobotsTaskCoordinator extends CoordinatorAdapter implements TaskCoordinator {

    //Lista aktualnych zadań
    private final Map<Robot, Task> currentTasks = new HashMap<Robot, Task>();
    //Lista poprzednich zadań
    private final Map<Task, Robot> recentTasks = new HashMap<Task, Robot>();
    //Lista tasków przypisanych do robota
    private final Map<Task, Robot> robotTasks = new HashMap<Task, Robot>();
    //Kolejka tasków do pobrania
    private final Queue<Task> queueBooksToReturn = new LinkedList<Task>();
    //Kolejka tasków do oddania
    private final Queue<Task> queueWishList = new LinkedList<Task>();

    public Task requireCurrentTask(Robot robot) {

        if (currentTasks.get(robot) != null) {
            return currentTasks.get(robot);
        }

        if (robotTasks.containsValue(robot)) {
            for (Task task : robotTasks.keySet()) {
                if (robotTasks.get(task).equals(robot)) {
                    currentTasks.put(robot, task);
                    return task;
                }
            }
        }

        if (queueBooksToReturn.size() > queueWishList.size()) {
            return this.getNewTaskFromQueue(queueBooksToReturn, robot);
        } else {
            return this.getNewTaskFromQueue(queueWishList, robot);
        }
    }

    @Override
    public void studentPutBook(Student student, Desk desk, Book book, Environment environment) {
        // tworzenie zadania i dodawanie do kolejki
        queueBooksToReturn.add(new Task(desk, environment.getBookshelfByTitle(book.getTitle()), book.getTitle()));
    }

    @Override
    public void studentPutWishlist(Student student, Desk desk, List<Title> wishlist, Environment environment) {
        // tworzenie zadan i dodawanie do kolejki
        for (Title title : wishlist) {
            queueWishList.add(new Task(environment.getBookshelfByTitle(title), desk, title));
        }
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

    @Override
    public void robotPutBook(Robot robot, Desk desk, Book book, Environment environment) {
        robotTasks.remove(currentTasks.get(robot));
        recentTasks.remove(currentTasks.get(robot));
        currentTasks.remove(robot);
        System.out.println("Robot " + robot.getName() + " odłożył książkę na biurko");
        this.requireCurrentTask(robot);
    }

    @Override
    public void robotPutBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment) {
        robotTasks.remove(currentTasks.get(robot));
        recentTasks.remove(currentTasks.get(robot));
        currentTasks.remove(robot);
        System.out.println("Robot " + robot.getName() + "  odłożył książkę na półkę");
        this.requireCurrentTask(robot);
    }

    @Override
    public void robotTakenBook(Robot robot, Desk desk, Book book, Environment environment) {
        //Dostarczanie z biurka
        if (robot.getCache().contains(book) && currentTasks.get(robot).getSourceDesk().equals(desk)) {
            System.out.println("Robot: " + robot.getName() + " ma w kieszeni książkę: " + book + " którą dosatrczy na półkę.");
            if (robot.getCacheSize() > robot.getCache().size()) {
                recentTasks.put(currentTasks.get(robot), robot);
                this.getNewCurrentTask(robot);
            }
        }
    }

    @Override
    public void robotTakenBook(Robot robot, Bookshelf bookshelf, Book book, Environment environment) {
        //Dostarczanie z półki
        if (robot.getCache().contains(book) && currentTasks.get(robot).getSourceBookshelf().equals(bookshelf)) {
            System.out.println("Robot: " + robot.getName() + " ma w kieszeni książkę: " + book + " którą dosatrczy na biurko.");
            if (robot.getCacheSize() > robot.getCache().size()) {
                recentTasks.put(currentTasks.get(robot), robot);
                this.getNewCurrentTask(robot);
            }
        }
    }

    private Task getNewTaskFromQueue(Queue<Task> queue, Robot robot) {
        Task task = null;
        for (int i = 0; i < robot.getCacheSize(); i++) {
            if (queue.peek() != null) {
                task = queue.poll();
                robotTasks.put(task, robot);
            }
        }
        if (task != null) {
            currentTasks.put(robot, task);
        }
        return task;
    }

    private void getNewCurrentTask(Robot robot) {
        if (robotTasks.containsValue(robot)) {
            for (Task task : robotTasks.keySet()) {
                if (robotTasks.get(task).equals(robot) && !recentTasks.containsKey(task)) {
                    currentTasks.put(robot, task);
                }
            }
        }
    }
}
