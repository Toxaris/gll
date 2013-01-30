/**
 * 
 */
package gll.sppf;

/**
 * @author Tillmann Rendel
 *
 */
public class RootLabel implements DerivationLabel {
	@Override
	public String toString() {
		return "root";
	}
	
	private RootLabel() {
	}
	
	private static RootLabel instance = new RootLabel();
	
	/**
	 * @return
	 */
	public static DerivationLabel create() {
		return instance;
	}
}