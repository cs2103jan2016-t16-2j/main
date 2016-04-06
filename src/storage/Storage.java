package storage;
import common.*;
import java.io.File;
public class Storage {

	private FileIo fileIO;
	private FileManagement fileManagement;
	private boolean isConnectedToFile;
	
	public Storage(State state) {
		this.fileIO = new FileIo(state);
		this.fileManagement = new FileManagement();
		this.isConnectedToFile = connectFileToIO();
	}

	public boolean executeLoadState() {
		assert isConnectedToFile;
		return fileIO.loadState();
	}
	
	public boolean executeSaveState() {
		assert isConnectedToFile;
		return fileIO.saveState();
	}
	
	public boolean executeChangeDirectory(String directory) {
		assert isConnectedToFile;
		return fileManagement.changeDirectory(directory);
	}
	
	// Helper functions
	
	
	/**
	 * This method connects the file with the IO part of this package.
	 */
	private boolean connectFileToIO() {
		File file = fileManagement.getFile();
		return fileIO.setFile(file);
	}
}
