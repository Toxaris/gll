/**
 * 
 */
package gll.sppf;

/**
 * @author Tillmann Rendel
 * 
 */
public class BeforeInput extends Position {
	private static final BeforeInput instance = new BeforeInput();

	public static BeforeInput create() {
		return instance;
	}

	/**
	 * Create BeforeInput.
	 * 
	 */
	public BeforeInput() {
		super(-1);
	}

	@Override
	public String toString() {
		return "$";
	}
}
