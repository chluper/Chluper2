/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chluper2.algorithm;

/**
 * Klasa reprezentujaca decyzje podejmowane przez algorytm
 * @author damian
 */
public final class Action {

	private final ActionType actionType;
	private final Object arg0;
	private final Object arg1;

	/**
	 * Tworzenie nowej akcji
	 * @param actionType typ akcji
	 * @param arg0 argument jesli jest wymagany
	 * @param arg0 argument jesli jest wymagany
	 */
	public Action(ActionType actionType, Object arg0, Object arg1) {
		this.actionType = actionType;
		this.arg0 = arg0;
		this.arg1 = arg1;
	}

	/**
	 * Tworzenie nowej akcji
	 * @param actionType typ akcji
	 * @param arg0 argument jesli jest wymagany
	 */
	public Action(ActionType actionType, Object arg0) {
		this.actionType = actionType;
		this.arg0 = arg0;
		this.arg1 = null;
	}

	/**
	 * Tworzenie nowej akcji
	 * @param actionType typ akcji
	 */
	public Action(ActionType actionType) {
		this.actionType = actionType;
		this.arg0 = null;
		this.arg1 = null;
	}

	/**
	 * Zwraca typ akcji
	 * @return
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * Zwraca argumet 0, jesli taki istnieje
	 * @return
	 */
	public Object getArg0() {
		return arg0;
	}

	/**
	 * Zwraca argumet 0, jesli taki istnieje
	 * @return
	 */
	public Object getArg1() {
		return arg1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Action other = (Action) obj;
		if (this.actionType != other.actionType) {
			return false;
		}
		if (this.arg0 != other.arg0 && (this.arg0 == null || !this.arg0.equals(other.arg0))) {
			return false;
		}
		if (this.arg1 != other.arg1 && (this.arg1 == null || !this.arg1.equals(other.arg1))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 23 * hash + this.actionType.hashCode();
		hash = 23 * hash + (this.arg0 != null ? this.arg0.hashCode() : 0);
		hash = 23 * hash + (this.arg1 != null ? this.arg1.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "|" + actionType + "|" + arg0 + "|" + arg1 + "]";
	}
}
