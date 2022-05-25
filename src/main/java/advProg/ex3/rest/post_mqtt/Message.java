package advProg.ex3.rest.post_mqtt;

public class Message {
	
	private int id;
	private String data; 
	
	public int getId() { 
		return id;
	}
	
	public void setId(int id) { 
		this.id = id;
	}
	
	public String getData() { 
		return data; 
	}
	
	public void setData(String data) { 
		this.data = data;
	}
	
	public Message(int id, String data) {
		super(); 
		this.id = id;
		this.data = data;
	}
 }
