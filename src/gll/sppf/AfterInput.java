/**
 * 
 */
package gll.sppf;


/**
 * @author Tillmann Rendel
 * 
 */
public class AfterInput extends Position {
	/**
	 * Create AfterInput.
	 * 
	 */
	public AfterInput(final int position) {
		super(position);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPositionString() {
		return "$";
	}
}
