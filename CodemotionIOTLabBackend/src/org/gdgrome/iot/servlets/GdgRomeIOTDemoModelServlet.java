package org.gdgrome.iot.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import org.gdgrome.iot.model.dao.ClientDao;
import org.gdgrome.iot.model.dao.MessageDao;
import org.gdgrome.iot.model.entities.ClientBean;

@SuppressWarnings("serial")
public class GdgRomeIOTDemoModelServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String messaggio = req.getParameter("messaggio");
		
		MessageDao daoMessagge = new MessageDao();
		daoMessagge.sendMessagge(messaggio);
		
		resp.sendRedirect("/jsp/risposta.jsp");
	
	}
	

}
