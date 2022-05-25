package advProg.ex3.rest.post_mqtt;

import java.util.List;

public interface IMessageFunctions {
	
	List<Message> findAll();
	Message addMessage(Message m);
	
}
