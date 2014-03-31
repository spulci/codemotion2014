package org.gdgrome.iot.endpoints;

import java.io.IOException;
import java.util.logging.Logger;

import org.gdgrome.iot.model.dao.MessageDao;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

@Api(name="messageEndpoint")
public class MessageEndpoint {
	
	private static final Logger log = Logger.getLogger(MessageEndpoint.class.getName());

	public MessageEndpoint() {
	}
	
	@ApiMethod(name = "operation.send", httpMethod="post")
	public void sendMessage(@Named("message") String message) throws IOException{
		log.info("Endpoint call: operation.listClients");
		
		MessageDao daoMessage = new MessageDao();
		daoMessage.sendMessagge(message);	
	}

}
