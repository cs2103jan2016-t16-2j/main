package logic;

import common.Font;
import common.State;
import common.Theme;
import common.ViewMode;

public class Config implements Operation {
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public Config(State state) {
		this.state = state;
	}

	/**
	 * Perform change of font, theme and storage path
	 *
	 * @return  boolean to indication whether the addition is successful
	 */
	@Override
	public boolean process() {
		
		Font newFont = state.getNewFont();
		Theme newTheme = state.getNewTheme();
		String newDir = state.getNewDirectory();
		

		ViewMode newViewMode = state.getNewViewMode();
		state.setViewMode(newViewMode);
		
		if(newFont != null){
			state.setFont(newFont);
		}
		
		if(newTheme != null){
			state.setTheme(newTheme);
		}
		
		if(newDir != null){
			state.setNewDirectory(newDir);
		}
		
		return false;
	}

}
