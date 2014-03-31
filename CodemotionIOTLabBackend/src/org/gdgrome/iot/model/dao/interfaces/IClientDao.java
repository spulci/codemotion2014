package org.gdgrome.iot.model.dao.interfaces;

import java.util.List;

import org.gdgrome.iot.model.entities.ClientBean;

import com.google.appengine.api.datastore.Key;

public interface IClientDao {

	public List<ClientBean> fetchClientBeans();
	public ClientBean fetchClientBeanById(long id);
	public ClientBean fetchClientBeanByRegistrationId(String id);
	public ClientBean fetchClientBeanByMessageId(String id);
	public void addClientBean(ClientBean entity);
	public void removeClientBeanById(Key id);
	public void removeClientByRegistrationId(String id);
}
