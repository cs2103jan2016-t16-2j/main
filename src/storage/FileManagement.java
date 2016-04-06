package storage;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
public class FileManagement {
	
	
	// Attributes
		protected File file;
		protected Gson gson = new Gson();

		// Logger
		private final static Logger LOGGER = Logger.getLogger(FileManagement.class.getName());
	public FileManagement(){
		connectFile(getDataFileDirectory());
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
 	private boolean connectFile(File dataDir) {
 		LOGGER.log(Level.INFO, "Retrieving Datafile...");
 		this.file = new File(dataDir, "data.txt");
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

 	/**
	 * This method creates a directory at user's home directory. Database text file
	 * will be stored here.
	 * @return
	 */
	private File getDataFileDirectory() {
		LOGGER.log(Level.INFO, "Retrieving the datafile directory...");
		File dataDir = new File(System.getProperty("user.home") + "/WallistDatabase");
		
		if (!dataDir.exists()) {
			dataDir.mkdirs();
			LOGGER.log(Level.INFO, "Directory not exist or removed, creating new directory");
		}
		return dataDir;
	}
	
	public boolean changeDirectory(String directory) {
		//Todo
		return true;
	}

}
