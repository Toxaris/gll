/**
 * 
 */
package gll.grammar;

import gll.gss.Stack;
import gll.parser.State;

/**
 * A terminal symbol in a grammar.
 * 
 * @author Tillmann Rendel
 */
public abstract class TerminalSymbol extends Symbol {
	/**
	 * Create a terminal symbol for a character.
	 * 
	 * @param character
	 *            the character to accept.
	 * 
	 * @return a terminal symbol that accepts {@code character}
	 */
	public static TerminalSymbol singleton(final char character) {
		return singleton((int) character);
	}

	/**
	 * Create a terminal symbol for a Unicode codepoint.
	 * 
	 * @param codepoint
	 *            the codepoint to accept
	 * 
	 * @return a terminal symbol that accepts {@code codepoint}
	 */
	public static TerminalSymbol singleton(final int codepoint) {
		return new Singleton(codepoint);
	}

	/**
	 * Create a terminal symbol with a custom name for a character.
	 * 
	 * @param character
	 *            the character to accept.
	 * 
	 * @return a terminal symbol that accepts {@code character}
	 */
	public static TerminalSymbol singleton(final String name, final char character) {
		return singleton(name, (int) character);
	}

	/**
	 * Create a terminal symbol with a custom name for a Unicode codepoint.
	 * 
	 * @param name
	 *            the custom name
	 * @param codepoint
	 *            the codepoint to accept
	 * 
	 * @return a terminal symbol that accepts {@code codepoint}
	 */
	public static TerminalSymbol singleton(final String name, final int codepoint) {
		return new Singleton(name, codepoint);
	}

	/**
	 * Create token.
	 * 
	 * @param name
	 *            human-readable name, used for debugging and error messages
	 */
	public TerminalSymbol(final String name) {
		super(name);
	}

	/**
	 * Decided whether to accept a token.
	 * 
	 * @param codepoint
	 *            the current token
	 * @return {@code true} if the token should be accepted, {@code false}
	 *         otherwise
	 */
	public abstract boolean accept(int codepoint);

	/**
	 * Process this symbol during parsing.
	 * 
	 * <p>
	 * This implementation calls {@link #accept(int)} to decided whether to
	 * accept the current token or not. If the current token is accepted, a
	 * parser process is scheduled to continue parsing after the token.
	 * </p>
	 * 
	 * @param state
	 *            the parser state
	 * @param caller
	 *            the stack frame of the running parser process
	 * @param derivation
	 *            the current derivation
	 * @param previous
	 *            where to continue parsing after this symbol has been fully
	 *            processed
	 * @param codepoint
	 *            the current token
	 */
	@Override
	public void call(final State state, final Stack frame, final int codepoint) {
		if (accept(codepoint)) {
			state.scheduleLater(frame, state.tokenDerivation);
		}
	}
}
