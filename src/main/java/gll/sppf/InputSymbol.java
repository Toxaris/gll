/**
 * 
 */
package gll.sppf;

/**
 * @author Tillmann Rendel
 * 
 */
public class InputSymbol extends Position {
	public int codepoint;

	/**
	 * Create Symbol.
	 * 
	 * @param codepoint
	 * @param position
	 */
	public InputSymbol(final int codepoint, final int position) {
		super(position);
		this.codepoint = codepoint;
	}

	/**
	 * @return
	 */
	public int getCodepoint() {
		return codepoint;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.appendCodePoint(codepoint);
		return builder.toString();
	}
}
