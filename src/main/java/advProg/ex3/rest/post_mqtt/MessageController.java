package advProg.ex3.rest.post_mqtt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class MessageController {

	@Autowired
	private IMessageFunctions messageFunctions; 
	
	private static final Logger log = LoggerFactory.getLogger( MessageController.class);
	
	@ApiOperation(value = "Retrieves all messages", notes = "This operation retrieves all Message entities. ", response = Message.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Message.class),
							@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
							@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
							@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
							@ApiResponse(code = 404, message = "Not Found", response = Error.class),
							@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
							@ApiResponse(code = 409, message = "Conflict", response = Error.class),
							@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/message/" , produces = { "application/json;charset=utf-8" }, method = RequestMethod.GET)
	public List<Message> getMessage() { 
		
		List<Message> messages = messageFunctions.findAll();
		return messages; 
		
	}
	
	@ApiOperation(value = "Creates a message", notes = "This operation creates a Message entity.", response = Message.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = Message.class),
							@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
							@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
							@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
							@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
							@ApiResponse(code = 409, message = "Conflict", response = Error.class),
							@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/message", produces = { "application/json;charset=utf-8" }, consumes = { "application/json;charset=utf-8" }, method = RequestMethod.POST)
	public ResponseEntity<Message> createMessage(@ApiParam(value = "The message to be created", required = true) @RequestBody Message m) {
		log.info("Will add new message");
		Message message = messageFunctions.addMessage(m);
		MQTTclient.callClient();
		return new ResponseEntity<Message> (message, HttpStatus.OK);
	}
	
}
