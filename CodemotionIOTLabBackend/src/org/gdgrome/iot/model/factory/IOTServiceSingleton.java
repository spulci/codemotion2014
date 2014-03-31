package org.gdgrome.iot.model.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class IOTServiceSingleton {
	private static final EntityManagerFactory iotInstance = Persistence
		      .createEntityManagerFactory("transactions-optional");

		  private IOTServiceSingleton() {
		  }

		  public static EntityManagerFactory makeInstance() {
		    return iotInstance;
		  }
}
