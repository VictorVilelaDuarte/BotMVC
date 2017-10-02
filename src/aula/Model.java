package aula;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import com.pengrad.telegrambot.model.Update;

public class Model implements Subject{
	private List<Observer> observers = new LinkedList<Observer>();
	public List<Hospital> hospitais = new LinkedList<Hospital>();
	
	private static Model uniqueInstance;
	
	
	private Model(){}
	
	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}
	
	public void registerObserver(Observer observer){
		observers.add(observer);
	}
	
	public void notifyObservers(long chatId){
		for(Observer observer:observers){
			observer.update(null, chatId);
		}
	}
	
	public void addHospital(Hospital Hospital){
		this.hospitais.add(Hospital);
	}
	
}
