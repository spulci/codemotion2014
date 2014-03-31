package org.gdgrome.iot.model.dao;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.gdgrome.iot.common.MessageKeyDispatcher;
import org.gdgrome.iot.model.dao.interfaces.IMessageDao;
import org.gdgrome.iot.model.entities.ClientBean;
import org.gdgrome.iot.model.entities.MessageDataBean;
import org.gdgrome.iot.model.factory.IOTServiceSingleton;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;


public class MessageDao implements IMessageDao {
	
	public final static String API_KEY = MessageKeyDispatcher.getString("Message.api.key"); //$NON-NLS-1$

	private static final Logger log = Logger.getLogger(MessageDao.class.getName());

	public MessageDao() {
	}

	@Override
	public void sendMessagge(String message)
			throws IOException {
		
		log.info(MessageDao.class.getName() + " sendMessagge() with message = " + message);
		Sender sender = new Sender(API_KEY);
		ClientDao daoClient = new ClientDao();
		
	    MessageDataBean messageDataBean = new MessageDataBean();
	    messageDataBean.setMessage(message);
	    messageDataBean.setTimestamp(System.currentTimeMillis());
	    
	    EntityManager mgr = IOTServiceSingleton.makeInstance().createEntityManager();
	    
	    log.info(MessageDao.class.getName() + " sendMessagge() persisting message now");
	    try {
	      mgr.persist(messageDataBean);
	    }
	    catch(EntityExistsException e){
			log.severe("Entyty already exist with id = " + messageDataBean.getId().getId() + "   " + e.getMessage());
		}
		catch(Exception e){
			log.severe("Exeception for entity with id = " + messageDataBean.getId().getId() + "   " + e.getMessage());
		}
	    finally {
	      mgr.close();
	    }
	    
	    
	    List<ClientBean> clients = daoClient.fetchClientBeans();
	    
	    log.info(MessageDao.class.getName() + " sendMessagge() sending msg via GCM to all now");
	    
	    for (ClientBean beanClient : clients) {
	    	try{
	    		log.info("sending message to client with registration id = " + beanClient.getRegistrationId());
	    		doSendViaGcm(message, sender, beanClient);
	    	}
	    	catch(IOException e){
	    		log.severe("IOException for GCM send for message = " + message + " and bean registration id = " + beanClient.getRegistrationId());
	    	} 
	      
	    }
	}
	
	private static Result doSendViaGcm(String message, Sender sender, ClientBean clientBean) throws IOException {

			ClientDao daoClient = new ClientDao();
			
		    if (message.length() > 1000) {
		      message = message.substring(0, 1000) + "[...]"; //$NON-NLS-1$
		    }

		    Builder builder = new Message.Builder();
		    builder.addData("message", "{\"text\":\""+message + "\"}");
		    Message msg = builder.build();

		    Result result = sender.send(msg, clientBean.getRegistrationId(),
		        5);
		    if (result.getMessageId() != null) {
		      String canonicalRegId = result.getCanonicalRegistrationId();
		      if (canonicalRegId != null) {
		        daoClient.removeClientByRegistrationId(clientBean.getRegistrationId());
		        clientBean.setRegistrationId(canonicalRegId);
		        daoClient.addClientBean(clientBean);
		      }
		    } else {
		      String error = result.getErrorCodeName();
		      if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
		        daoClient.removeClientByRegistrationId(clientBean.getRegistrationId());
		      }
		    }

		    return result;
		  }
	

}
