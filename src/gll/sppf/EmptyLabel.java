/**
 * 
 */
package gll.sppf;

/**
 * @author Tillmann Rendel
 *
 */
public class EmptyLabel implements DerivationLabel {
	@Override
	public String toString() {
		return "ε";
	}
	
	private EmptyLabel() {
	}
	
	private static EmptyLabel instance = new EmptyLabel();
	
	/**
	 * @return
	 */
	public static EmptyLabel create() {
		return instance;
	}
}