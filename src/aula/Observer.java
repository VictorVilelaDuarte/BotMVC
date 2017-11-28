package aula;

import java.util.List;

public interface Observer {
	public void update(long chatId, List<Hospital> hospitais);

}
