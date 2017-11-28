package aula;

//API KEY = AIzaSyD4XIjCdSGeTSvmTar_VpOwsVBo8ZhmcKc
//https://developers.google.com/places/web-service/search 
//Gson
//kaggle.com


import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.model.Update;



public class Model implements Subject{
	public List<Observer> observers = new LinkedList<Observer>();
	static List<Hospital> hospitais = new LinkedList<Hospital>();

	
	private static Model uniqueInstance;
	
	
	
	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}
	
	public void registerObserver(Observer observer){
		observers.add(observer);
		//this.hospitais.add((Hospital) hospitais);
	}
	
	
	public void notifyObservers(long chatId, List<Hospital> hospitais){
		for(Observer observer:observers){
			observer.update(chatId, hospitais);
		}
	}

	public void getHospitais(Update update){
		long chatId = update.message().chat().id();	
		this.notifyObservers(chatId, hospitais);
	}
	
	
}
