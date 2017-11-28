package aula;

public class Main {

	private static Model model;
	
	public static void main(String[] args) {
		
		model = Model.getInstance();
		initializeModel(model);
		View view = new View();
		model.registerObserver(view); //connection Model -> View
		view.setController( new ControllerHospitais(model, view));
		view.receiveUsersMessages();
		
	
	}
	
	public static void initializeModel(Model model){
		Model.hospitais.add(new Hospital("Vi Valle", "Rua Linneu de Moura", new LatLng(-23.198418, -45.916525)));
		Model.hospitais.add(new Hospital("Pronto Socorro", "Av. JK", new LatLng(-23.179879, -45.853550)));
	}

}