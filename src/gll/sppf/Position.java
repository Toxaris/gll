/**
 * 
 */
package gll.sppf;

/**
 * @author Tillmann Rendel
 * 
 */
public class Position implements DerivationLabel {
	private final int position;

	/**
	 * Create Position.
	 * 
	 */
	public Position(final int position) {
		this.position = position;
	}

	/**
	 * @return
	 */
	public String getPositionString() {
		return Integer.toString(position);
	}
}
