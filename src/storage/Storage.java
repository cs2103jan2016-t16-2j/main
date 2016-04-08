package storage;
import common.*;
import java.io.File;
public class Storage {

	private FileIo fileIo;
	private FileManagement fileManagement;
	private boolean isConnectedToFile;
	
	public Storage(State state) {
		this.fileIo = new FileIo(state);
		this.fileManagement = new FileManagement(state);
		this.isConnectedToFile = connectFileToIO();
	}

	public boolean executeLoadState() {
		assert isConnectedToFile;
		return fileIo.loadState();
	}
	
	public boolean executeSaveState() {
		assert isConnectedToFile;
		return fileIo.saveState();
	}
	
	public boolean executeChangeDirectory(String directory) {
		assert isConnectedToFile;
		return fileManagement.changeDirectory();
	}
	
	// Helper functions
	
	
	/**
	 * This method connects the file with the IO part of this package.
	 */
	private boolean connectFileToIO() {
		File file = fileManagement.getFile();
		return fileIo.setFile(file);
	}
}
