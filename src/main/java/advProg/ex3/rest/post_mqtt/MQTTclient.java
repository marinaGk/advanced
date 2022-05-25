package advProg.ex3.rest.post_mqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTclient implements MqttCallback {
	
	MqttClient myClient;
	MqttConnectOptions connOpt;
	Message mes;

	static final String M2MIO_THING = UUID.randomUUID().toString();
	static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
	
	static final Boolean subscriber = true;
	static final Boolean publisher = true;
	
	private static final Logger log = LoggerFactory.getLogger(MQTTclient.class);
	public static final String TOPIC = "post_message";
	
	public void connectionLost(Throwable t) {
		log.info("Connection lost!");
	}
	
	public void deliveryComplete(IMqttDeliveryToken token) {
	}
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		log.info("\n");
		log.info("-------------------------------------------------");
		log.info("| Topic:" + topic);
		log.info("| Message: " + new String(message.getPayload()));
		log.info("-------------------------------------------------");
		log.info("\n");
	}

	public static void callClient() { 
		MQTTclient smc = new MQTTclient();
		
		List <Message> list = new ArrayList<Message>(); 
		list =  MessageFunctions.messages;
		Message newMsg = list.get(list.size()-1);
		System.out.println(new String (newMsg.getData()));
		
		smc.runClient(list.get(list.size() - 1));
	}
	
	public void runClient(Message m) { 
		
		String clientID = M2MIO_THING;
		connOpt = new MqttConnectOptions();
		
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(20);
		
		try {
			
			myClient = new MqttClient(BROKER_URL, clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		} 
		catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		log.info("Connected to" + BROKER_URL);
		
		String myTopic = TOPIC;
		MqttTopic topic = myClient.getTopic(myTopic);
		
		if (publisher) {
			String val = m.getData();
			String pubMsg = "{\"value\":" + val + "}";
			int pubQoS = 0;
			MqttMessage message = new MqttMessage(pubMsg.getBytes());
			message.setQos(pubQoS);
			message.setRetained(false);
			
			log.info("Publishing to topic \"" + topic + "\" qos " + pubQoS + "\" value " + val);
			
			MqttDeliveryToken token = null;
			try {
				// publish message to broker
				token = topic.publish(message);
				// Wait until the message has been delivered to the broker
				token.waitForCompletion();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			
			if (subscriber) { 
				Thread.sleep(5000);
			}
			myClient.disconnect();
			
		}
		catch (Exception e) { 
			
			e.printStackTrace();
			
		}
		
	}

}