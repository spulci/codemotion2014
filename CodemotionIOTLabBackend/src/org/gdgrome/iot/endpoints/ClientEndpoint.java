package org.gdgrome.iot.endpoints;

import java.util.List;



import java.util.logging.Logger;

import org.gdgrome.iot.model.dao.ClientDao;
import org.gdgrome.iot.model.entities.ClientBean;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.Key;

//TODO 3: Esponiamo un servizio sul Cloud con i Google Cloud Endpoint

// 1) Annotiamo la classe facendola diventare un'API remota di nome "clientEndpoint"
public class ClientEndpoint {
	
	private static final Logger log = Logger.getLogger(ClientEndpoint.class.getName());
	
	public ClientEndpoint() {
		
	}
	
	@ApiMethod(name = "operation.listClients", httpMethod="post")
	public List<ClientBean> listClientBeans(){
		log.info("Endpoint call: operation.listClients" );
		
		List<ClientBean> clientBeanLst = null;
		ClientDao daoClient = new ClientDao();
		clientBeanLst = daoClient.fetchClientBeans();	
		return clientBeanLst;
	}
	
	//2) Annotiamo il metodo da esporre dando come name all'operazione remota "operation.addClient" e usando HTTP in POST
	public void addClient(ClientBean client){
		log.info("Endpoint call: operation.addClient" );
		// 3 Creiamo un'istanza di ClientDao e su questa chiamiamo il servizio creato al passo 2
	}
	
	@ApiMethod(name = "operation.removeClient", httpMethod="post")
	public void removeClient(Key id){
		log.info("Endpoint call: operation.removeClient" );
		
		ClientDao daoClient = new ClientDao();
		daoClient.removeClientBeanById(id);
	}
	
	

}
