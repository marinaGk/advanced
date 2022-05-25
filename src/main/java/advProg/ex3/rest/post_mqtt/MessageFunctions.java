package advProg.ex3.rest.post_mqtt;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MessageFunctions implements IMessageFunctions {

	static List <Message> messages = new ArrayList<Message>(); 
	int counter =  0;
	
	public MessageFunctions() { 
		
		super(); 
		counter++;
		messages.add(new Message(counter, "Lorem ipsum or some shit I suppose"));

	}
	
	//@Override
	public List<Message> findAll() { 
		
		return messages;
		
	}
	
	
	@Override 
	public Message addMessage(Message m) { 
 
		counter++;
		m.setId(counter);
		messages.add(m);
		return m; 
	}
	

}
