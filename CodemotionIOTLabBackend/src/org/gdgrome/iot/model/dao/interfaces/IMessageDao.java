package org.gdgrome.iot.model.dao.interfaces;

import java.io.IOException;

import org.gdgrome.iot.model.entities.ClientBean;

import com.google.android.gcm.server.Sender;

public interface IMessageDao {

	public void sendMessagge(String message) throws IOException;
	
}
