/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.example;

import chluper2.algorithm.Action;
import chluper2.algorithm.ActionType;
import chluper2.algorithm.Algorithm;
import chluper2.algorithm.Coordinator;
import chluper2.environment.Book;
import chluper2.environment.Bookshelf;
import chluper2.environment.Desk;
import chluper2.environment.Environment;
import chluper2.environment.Robot;
import chluper2.environment.Title;

/**
 *
 * @author damian
 */
public class SimpleAlgorithm implements Algorithm {

	public Action decide(Robot controlledRobot, Environment environment, Coordinator coordinator) {
		for (Book book : controlledRobot.getCache()) {
			// czy dostarczyc
			for (Desk desk : environment.getDesks()) {
				if (desk.getWishList() != null && desk.getWishList().contains(book.getTitle())) {
					return new Action(ActionType.DELIVER_BOOK, book, desk);
				}
			}
			// odwozimy
			Bookshelf bookshelf = environment.getBookshelfByTitle(book.getTitle());
			return new Action(ActionType.DELIVER_BOOK, book, bookshelf);
		}

		for (Desk desk : environment.getDesks()) {
			for (Book book : desk.getBooks()) {
				if (desk.getWishList() == null || !desk.getWishList().contains(book.getTitle())) {
					return new Action(ActionType.GET_BOOK, book, desk);
				}
			}


			if (desk.getWishList() != null) {
				for (Title title : desk.getWishList()) {
					Bookshelf bookshelf = environment.getBookshelfByTitle(title);
					return new Action(ActionType.GET_BOOK, title, bookshelf);
				}
			}
		}


		return new Action(ActionType.WAIT);
	}
}
