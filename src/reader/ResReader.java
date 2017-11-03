package reader;

import java.io.InputStream;

public class ResReader extends Reader {

	public ResReader(InputStream in, char[] identifiers, String[] tokens) {
		super(in, identifiers, tokens);
	}
	
	/**
	 * Sets a restore point to which the reader can jump back to later.
	 * Only one restore point can exist at a time, calling this method
	 * overwrites any existing restore point.
	 */
	public void setRestorePoint() {
		
	}
	
	/**
	 * Since the reader uses memory whenever a restore point exists
	 * it is a good idea to clear it whenever it's not needed.
	 */
	public void clearRestorePoint() {
		
	}
	
	/**
	 * Jumps to the restore point <b>without clearing</b> it.
	 */
	public void jumpRestorePoint() {
		
	}

}
