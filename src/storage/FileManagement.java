package storage;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class FileManagement {
	
	
	// Attributes
	protected File file;
	protected Gson gson = new Gson();
	protected File configFile;
	
	// Config Attributes
	private File directory;

	// Logger
	private final static Logger LOGGER = Logger.getLogger(FileManagement.class.getName());
	
	public FileManagement(){
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
			String defaultDirectory = System.getProperty("user.home") + "/WallistDatabase";
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			writer.write(defaultDirectory);
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
			String directoryString = reader.readLine();
			this.directory = new File(directoryString);
			reader.close();
		} catch (IOException e){
			LOGGER.log(Level.WARNING, "Configfile is not created successfully...", e);
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
		if (!configFile.exists()) {
			createConfigFile();
		} else {
			loadConfigFile();
		}
		return true;
	}
	
	public File getFile() {
		return this.file;
	}
	
	/**
 	 * This method takes in the datafile directory, and retrieve the file if it exists, 
 	 * if not a new one will be created
 	 * @param dataDir
 	 * @return
 	 */
 	private boolean connectDataFile() {
 		LOGGER.log(Level.INFO, "Retrieving Datafile...");
 		this.file = new File(this.directory, "data.txt");
 		System.out.println(this.directory);
 		if (!file.exists()) {
 			try {
 				LOGGER.log(Level.INFO, "Datafile does not exist, creating new datafile...");
				return file.createNewFile();
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Datafile is not created successfully...", e);
				return false;
			}
 		}
 		return true;
 	}
	
	public boolean changeDirectory(String directoryString) {
		
		return true;
	}

}
