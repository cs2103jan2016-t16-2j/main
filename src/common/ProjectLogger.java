//@@author A0107375E
package common;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ProjectLogger {
	
	private FileHandler fileHandler;
	private Logger LOGGER;
	
	public ProjectLogger(String className) {
		System.out.println(className);
		LOGGER = Logger.getLogger(className);
		
		try {
			this.fileHandler = new FileHandler("Log.txt");
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		LOGGER.addHandler(fileHandler);
		SimpleFormatter formatter = new SimpleFormatter();  
        fileHandler.setFormatter(formatter);
	}
	
	public void warning(String log) {
		LOGGER.warning(log);
	}
	
	public void info(String log) {
		LOGGER.info(log);
	}
	
	
}
