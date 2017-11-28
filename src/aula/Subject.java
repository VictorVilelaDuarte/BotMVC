package aula;

import java.util.List;

public interface Subject {
	//private List<Observer> observers = new LinkedList<Observer>();
	
	public void registerObserver(Observer observer);
	
	public void notifyObservers(long chatId, List<Hospital> hospitais);

}
