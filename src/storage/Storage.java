//@@ A0107375E
package storage;
import common.*;
import java.io.File;

public class Storage {
	
	//===========================
	//       Attributes
	//===========================
	
	private FileIo fileIo;
	private FileManagement fileManagement;
	private boolean isConnectedToFileIo;
	
	//===========================
	//       Constructor
	//===========================
	
	public Storage(State state) {
		this.fileIo = new FileIo(state);
		this.fileManagement = new FileManagement(state);
		this.isConnectedToFileIo = connectFileToIO();
	}
	
	//===========================
	//       Functions
	//===========================
	
	/**
	 * This method loads the datafile into the State object
	 * @return whether loading is successful
	 */
	public boolean executeLoadState() {
		assert isConnectedToFileIo;
		return fileIo.loadState();
	}
	
	/**
	 * This method saves the State object into the datafile
	 * @return whether saving is successful
	 */
	public boolean executeSaveState() {
		assert isConnectedToFileIo;
		return fileIo.saveState();
	}
	
	/**
	 * This method changes the directory of the datafile
	 * @param directory
	 * @return whether changing is successful
	 */
	public boolean executeSaveConfig() {
		assert isConnectedToFileIo;
		return fileManagement.saveConfigFile();
	}
	
	//===========================
	//     Helper Functions
	//===========================
	
	
	/**
	 * This method connects the file with the IO part of this package.
	 * @return whether connects to FileIo Class
	 */
	private boolean connectFileToIO() {
		File dataFile = fileManagement.getDataFile();
		fileIo.setDataFile(dataFile);
		File archiveFile = fileManagement.getFinishedFile();
		fileIo.setArchiveFile(archiveFile);
		return true;
	}
}
