/**
 * 
 */
package gll.grammar;

/**
 * A terminal symbol that stands for exactly one token.
 * 
 * @author Tillmann Rendel
 */
public class Singleton extends TerminalSymbol {
	/**
	 * The codepoint to accept.
	 */
	private final int codepoint;

	/**
	 * Create singleton token.
	 * 
	 * @param codepoint
	 *            the codepoint to accept
	 */
	Singleton(int codepoint) {
		super(new String(Character.toChars(codepoint)));
		this.codepoint = codepoint;
	}

	/**
	 * Decided whether to accept a token.
	 * 
	 * <p>
	 * This implementation accepts the codepoint passed to the constructor.
	 * </p>
	 * 
	 * @param codepoint
	 *            the current token
	 * @return {@code true} if the token should be accepted, {@code false}
	 *         otherwise
	 */
	@Override
	public boolean accept(int codepoint) {
		return this.codepoint == codepoint;
	}
}