package aula;



import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Location;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class View implements Observer {
	TelegramBot bot = TelegramBotAdapter.build("375036253:AAGoNnGFmdS-4SaOP20Y2KgsilrIiLqd3B8");

	//Object that receives messages
	GetUpdatesResponse updatesResponse;
	//Object that send responses
	SendResponse sendResponse;
	//Object that manage chat actions like "typing action"
	BaseResponse baseResponse;


	int queuesIndex=0;


	Controller controller;//Strategy Pattern -- connection View -> Controller

	boolean searchBehaviour = false;

	//private Observer observer;

	private List<Hospital> hospitais = new LinkedList<Hospital>();
	//private long chatId;

	
	public void setController(Controller controller){ //Strategy Pattern
		this.controller = controller;
	}

	public void receiveUsersMessages() {

		int m = 0;
		String resp = null;
		boolean bola = true;


		// loop infinito pode ser alterado por algum timer de intervalo curto
		while(true) {

			// executa comando no Telegram para obter as mensagens pendentes a
			// partir de um off-set (limite inicial)
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			// lista de mensagens
			List<Update> updates = updatesResponse.updates();

			// an�lise de cada a��o da mensagem
			for (Update update : updates) {
				
			
				
				// atualiza��o do off-set
				m = update.updateId() + 1;

				System.out.println("Recebendo mensagem: " + update.message().text());
				while (bola){
					// envio de "Escrevendo" antes de enviar a resposta
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					// verifica��o de a��o de chat foi enviada com sucesso
					System.out.println("Resposta de Chat Action Enviada? " + baseResponse.isOk());

					Keyboard keyboard = new ReplyKeyboardMarkup(
							new String[]{"Queimaduras","Fraturas"},
							new String[]{"Sangramentos","Envenenamento"},
							new String[]{"Enviar localiza��o"}
							).resizeKeyboard(true);

					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "ol�"));
					sendResponse = bot.execute(
							new SendMessage(update.message().chat().id(), "Bem-vindo ao Bot de Primeiros Socorros!"));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Escolha sua emergencia").replyMarkup(keyboard));

					bola = false;
				}
				resp = update.message().text();
				if(resp == null){
					Location localizacao = update.message().location();	
					System.out.println(localizacao.latitude()+" "+localizacao.longitude());
					LatLng loc = new LatLng(localizacao.latitude().doubleValue(), localizacao.longitude().doubleValue());
					controller.search(update);
					for(Hospital hosp : this.hospitais){
						InlineKeyboardMarkup googlemaps = new InlineKeyboardMarkup(
								new InlineKeyboardButton[]{
										new InlineKeyboardButton("Ver no mapa ").url("https://www.google.com.br/maps/@"+hosp.getLocalizacao().latitude+","+hosp.getLocalizacao().longitude+",16z")
								});
						Double dist = Distancia.calcularDistancia(loc, hosp.getLocalizacao());
						String result = String.format("%.2f", dist);
						System.out.printf("O hospital "+ hosp.getNome() +" tem a distancia de: %.2f KM \n \n",dist);
						System.out.println(hosp.getLocalizacao().latitude);
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"O hospital "+ hosp.getNome() +" tem a distancia de: "+result+"Km").replyMarkup(googlemaps));
					}
				}

				else{
					System.out.println("lala");
				}


				if (resp != null){
					switch (resp) {

					case ("Queimaduras"):
						InlineKeyboardMarkup queimadura = new InlineKeyboardMarkup(
								new InlineKeyboardButton[]{
										new InlineKeyboardButton("Saiba mais ").url("https://g.co/kgs/m7iM4O"),
								});
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "� Isolar a v�tima do agente causador do acidente (fogo ou subst�ncias qu�micas lesivas, por exemplo, um �cido)\n"
							+"� Lavar a �rea queimada com �gua corrente limpa\n"
							+"� Se houver tecido da vestimenta aderido ao ferimento, este pode ser retirado de forma a n�o aumentar a les�o, no instante em que se estiver lavando o local\n"
							+"� N�o colocar �gua fria, gelo ou sab�o sobre o ferimento\n"
							+"� Proteja o local com pano e tecido limpo, mantendo a regi�o mais elevada em rela��o ao resto do corpo para evitar incha�o\n"
							+"� Procure um centro de queimadura mais pr�ximo de sua resid�ncia.").replyMarkup(queimadura));			
					break;

					case ("Fraturas"):
						InlineKeyboardMarkup fratura = new InlineKeyboardMarkup(
								new InlineKeyboardButton[]{
										new InlineKeyboardButton("Saiba mais ").url("https://g.co/kgs/m7iM4O"),
								});
					sendResponse = bot.execute(new SendMessage(update.message().chat().id().longValue(), "� Se a fratura for exposta, cubra o ferimento com gaze ou pano limpo. Em hip�tese alguma tente realinhar o membro ou retornar o osso, isso pode agravar a situa��o"
							+"� Imobilize a regi�o lesada com t�bua, papel�o ou madeirhora, envolvendo uma faixa\n"
							+"� Em caso de sangramento incessante (hemorragia), realize uma leve compress�o com pano limpo.").replyMarkup(fratura));
					break;

					case ("Sangramentos"):
						InlineKeyboardMarkup sangramento = new InlineKeyboardMarkup(
								new InlineKeyboardButton[]{
										new InlineKeyboardButton("Saiba mais ").url("https://g.co/kgs/m7iM4O"),
								});
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "� Colocar um pano ou papel limpo no ferimento\n"
							+"� Fazer press�o sobre o local, o suficiente para deter o sangramento.\n" 
							+"� Eleve o bra�o ou a perna da v�tima, mantendo a press�o sobre o ferimento.\n" 
							+"� Levar a v�tima ao pronto-socorro.").replyMarkup(sangramento));
					break;

					case ("Enviar localiza��o"):

						Keyboard local = new ReplyKeyboardMarkup(
								new KeyboardButton[]{
										new KeyboardButton("Sim, enviar localiza��o!").requestLocation(true)
								}					        
								).resizeKeyboard(true);

					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Enviando sua localiza��o voc� estar� procurando os hospitais mais proximos, voc� tem certeza?").replyMarkup(local));

					break;
					}
				}
			}
		}
	}


	public void sendTypingMessage(Update update){
		baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

	}


	public void callController(Update update){
		this.controller.search(update);
	}


	public void update(long chatId, List<Hospital> hospitais ) {
		System.out.println("Passou");
		this.hospitais = hospitais;
		System.out.println(this.hospitais.size());
	
	}

}