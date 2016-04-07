package storage;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import common.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
public class FileManagement {
	
	
	// Attributes
	protected File dataFile;
	protected File configFile;
	private State state;
	// Config Attributes
	private File directory;

	// Logger
	private final static Logger LOGGER = Logger.getLogger(FileManagement.class.getName());
	
	public FileManagement(State state){
		this.state = state;
		connectConfigFile();
		connectDataFile();
	}
	
	//============================
	//       main functions
	//============================
	
	
	
	private boolean createConfigFile() {
		LOGGER.log(Level.INFO, "Configfile does not exist, creating new configFile...");
		try {
			configFile.createNewFile();
			
			// Default setting
			String defaultDataDirectory = System.getProperty("user.home") + "/WallistDatabase";
			this.directory = new File(defaultDataDirectory);
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			writer.write(defaultDataDirectory);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
		
	private boolean loadConfigFile() {
		
		LOGGER.log(Level.INFO, "Retrieving Congfiguration file...");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			String currentConfigLine = reader.readLine();
			state.setCurrentDirectory(currentConfigLine); // pass the directory to state
			this.directory = new File(currentConfigLine); 
			reader.close();
		} catch (IOException e){
			LOGGER.log(Level.WARNING, "Configfile is not loaded successfully...", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	/**
	 * 
	 * @return
	 */
	protected boolean connectConfigFile() {
		this.configFile = new File("config.txt"); // connects the file
		if (!configFile.exists()) { // if not exist create a default one
			createConfigFile();
		}
		loadConfigFile();
		return true;
	}
	

	public File getFile() {
		return this.dataFile;
	}
	
	/**
 	 * This method takes in the datafile directory, and retrieve the file if it exists, 
 	 * if not a new one will be created
 	 * @param dataDir
 	 * @return
 	 */
 	private boolean connectDataFile() {
 		LOGGER.log(Level.INFO, "Retrieving Datafile...");
 		this.dataFile = new File(this.directory, "data.txt");
 		if (!dataFile.exists()) {
 			try {
 				LOGGER.log(Level.INFO, "Datafile does not exist, creating new datafile...");
				return dataFile.createNewFile();
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Datafile is not created successfully...", e);
				return false;
			}
 		}
 		return true;
 	}
	
	public boolean changeDirectory(String directoryString) {
		// read the File, stores the setting
		// Todo
		// input the new directory
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile));
			writer.write(directoryString);
			// other setting goes here
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
