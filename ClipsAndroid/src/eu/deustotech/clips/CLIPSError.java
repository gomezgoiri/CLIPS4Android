package eu.deustotech.clips;

/**
 * The errors thrown by CLIPS.
 * These errors are described in detail in the "Appendix G" of the "Basic Programming Guide".
 */
public class CLIPSError extends Exception {
	private static final long serialVersionUID = 1L;
	
	final String module;
	final int code;
	
	public CLIPSError(String module, int code, String message) {
		// TODO in CLIPS, the message is displayed in subsequent calls using "EnvPrintRouter"
		// which is used almost everywhere.	
		super(message);
		this.module = module;
		this.code = code;
	}
	
	public String toString() {
		return "[" + this.module + this.code + "] " + getMessage();
	}

	public String getModule() {
		return module;
	}

	public int getCode() {
		return code;
	}
}