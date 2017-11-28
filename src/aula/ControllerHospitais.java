package aula;

import com.pengrad.telegrambot.model.Update;

public class ControllerHospitais implements Controller {
	


	private Model model;
	private View view;
	
	public void search(Update update){
		view.sendTypingMessage(update);
		model.getHospitais(update);
		//model.conHospital(update);
	}
	
	public ControllerHospitais(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}

}
