package aula;

import com.pengrad.telegrambot.model.Update;

public class ControllerHospital implements Controller{
	private Model model;
	private View view;
	
	public ControllerHospital(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void search(Update update){
		view.sendTypingMessage(update);
	}

}
