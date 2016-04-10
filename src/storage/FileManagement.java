//@@author A0107375E
package storage;
import java.util.logging.Level;
import java.util.logging.Logger;
import common.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManagement {
	
	//============================
	//       Attributes
	//============================
	
	// basic attributes
	protected File dataFile;
	protected File configFile;
	protected File finishedFile;
	private State state;
	
	// configuration attributes
	private File directory;
	private Theme theme;
	private Font font;
	
	// config setting
	
	private static final String DEFAULT_DIRECTORY = System.getProperty("user.home") + "/WallistDatabase"; 
	private static final String DEFAULT_THEME = "AUTUMN";
	private static final String DEFAULT_FONT = "SEGOE";
	
	private static final String CONFIG_FILE_NAME = "config.txt";
	private static final String DATA_FILE_NAME = "data.txt";
	private static final String ARCHIVE_FILE_NAME = "archive.txt";
	
	// logger
	private final static Logger LOGGER = Logger.getLogger(FileManagement.class.getName());

	// logging message displayed
	private static final String CONNECT_ARCHIVE = "Connecting archive file...";
	private static final String CONNECT_CONFIG = "Connecting configuration file...";
	private static final String CONNECT_DATA = "Connecting data file...";
	
	private static final String CREATE_CONFIG = "Configuration file does not exist, creating new configFile...";
	private static final String CREATE_CONFIG_SUCCESS = "Configuration file is created successfully";
	private static final String CREATE_CONFIG_FAILURE = "Configuration file is not created successfully";
	
	private static final String LOAD_CONFIG = "Loading congfiguration file...";
	private static final String LOAD_CONFIG_SUCCESS = "Configuration file is loaded succesfully.";
	private static final String LOAD_CONFIG_FAILURE = "Configuration file is not loaded successfully...";
	
	private static final String CREATE_DATA = "Data file does not exist, creating new datafile...";
	private static final String CREATE_DATA_SUCCESS = "Data file is created successfully!";
	private static final String CREATE_DATA_FAILURE = "Data file is not created successfully!";
	
	private static final String CREATE_ARCHIVE = "Archive file does not exist, creating new archivefile...";
	private static final String CREATE_ARCHIVE_SUCCESS = "Archive file is created successfully!";
	private static final String CREATE_ARCHIVE_FAILURE = "Archive file is not created successfully!";
	
	
	//============================
	//       Constructor(s)
	//============================
	
	public FileManagement(State state){
		this.state = state;
		connectConfigFile();
		connectDataFile();
		connectArchiveFile();
	}
	
	//============================
	//       Functions
	//============================
	
	// configuration file
	/**
	 * This method creates a default configuration file.
	 * @return boolean value of whether it is successful
	 */
	private boolean createConfigFile() {
		LOGGER.log(Level.INFO, CREATE_CONFIG);
		
		try {
			configFile.createNewFile();
			
			//default configuration setting
			String defaultDataDirectory = DEFAULT_DIRECTORY;
			String defaultTheme = DEFAULT_THEME;
			String defaultFont = DEFAULT_FONT;
			this.directory = new File(defaultDataDirectory);
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			writer.write(defaultDataDirectory);
			writer.newLine();
			writer.write(defaultTheme);
			writer.newLine();
			writer.write(defaultFont);
			writer.newLine();
			writer.close();
			
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, CREATE_CONFIG_FAILURE, e);
			e.printStackTrace();
			return false;
		}
		LOGGER.log(Level.INFO, CREATE_CONFIG_SUCCESS);
		return true;
	}
	
	/**
	 * This method loads the configuration file and update the state with its setting.
	 * @return boolean value of whether it is successful
	 */
	private boolean loadConfigFile() {
		LOGGER.log(Level.INFO, LOAD_CONFIG);
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			String currentConfigLine = reader.readLine();
			this.state.setCurrentDirectory(currentConfigLine);
			
			String themeString = reader.readLine();
			this.state.setTheme(Theme.valueOf(themeString));
			
			String fontString = reader.readLine();
			this.state.setFont(Font.valueOf(fontString));
			reader.close();
		} catch (IOException e){
			LOGGER.log(Level.WARNING, LOAD_CONFIG_FAILURE);
			e.printStackTrace();
			return false;
		}
		
		LOGGER.log(Level.INFO, LOAD_CONFIG_SUCCESS);
		return true;
	}
	

	/**
	 * This method method connect the configuration file with this class, or create a new one if 
	 * no configuration file exists
	 * @return boolean value of whether it is successful
	 */
	private boolean connectConfigFile() {
		LOGGER.log(Level.INFO,CONNECT_CONFIG);
		this.configFile = new File(CONFIG_FILE_NAME); // connects the file
	
		if (!configFile.exists()) { // if not exist create a default one
			createConfigFile();
		}
		
		loadConfigFile();
		return true;
	}
	
	/**
 	 * This method changes the directory in the configuration file
 	 * @param directoryString
 	 * @return boolean value of whether it is successful
 	 */
	public boolean saveConfigFile() {
		this.theme = state.getTheme();
		this.font = state.getFont();
		String currentDirectoryString = state.getCurrentDirectory();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile));
			writer.write(currentDirectoryString);
			writer.write(theme.toString());
			writer.write(font.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public File getDataFile() {
		return this.dataFile;
	}
	
	public File getFinishedFile() {
		return this.finishedFile;
	}
	
	/**
 	 * This method method connect the data file with this class, or create a new one if 
	 * no data file exists
 	 * @return boolean value of whether it is successful
 	 */
 	private boolean connectDataFile() {
 		LOGGER.log(Level.INFO,CONNECT_DATA);
 		this.dataFile = new File(this.directory, DATA_FILE_NAME);
 		
 		if (!dataFile.exists()) {
 			createDataFile();
 		}

 		LOGGER.log(Level.INFO, CREATE_DATA_SUCCESS);
 		return true;
 	}
 	
 	
 	/**
 	 * This method creates a data file according to the directory stated in the configuration file
 	 * @return boolean value of whether it is successful
 	 */
 	private boolean createDataFile() {
 		LOGGER.log(Level.INFO, CREATE_DATA);
		
		try {
			dataFile.createNewFile();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, CREATE_DATA_FAILURE, e);
			e.printStackTrace();
			return false;
		}
		
		LOGGER.log(Level.INFO, CREATE_DATA_SUCCESS);
		return true;
 	}
 	
 	/**
 	 * This method method connect the data file with this class, or create a new one if 
	 * no data file exists
 	 * @return boolean value of whether it is successful
 	 */
 	private boolean connectArchiveFile() {
 		LOGGER.log(Level.INFO,CONNECT_ARCHIVE);
 		this.finishedFile = new File(this.directory, ARCHIVE_FILE_NAME);
 		
 		if (!finishedFile.exists()) {
 			createArchiveFile();
 		}

 		LOGGER.log(Level.INFO, CREATE_ARCHIVE_SUCCESS);
 		return true;
 	}
 	
 	/**
 	 * This method creates an archive file according to the directory stated in the configuration file
 	 * @return boolean value of whether it is successful
 	 */
 	private boolean createArchiveFile() {
 		LOGGER.log(Level.INFO, CREATE_ARCHIVE);
		
		try {
			finishedFile.createNewFile();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, CREATE_ARCHIVE_FAILURE, e);
			e.printStackTrace();
			return false;
		}
		
		LOGGER.log(Level.INFO, CREATE_DATA_SUCCESS);
		return true;
 	}
 	
 	

}
