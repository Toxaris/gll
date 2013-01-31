/**
 * 
 */
package gll.grammar.common;

import gll.grammar.TerminalSymbol;

/**
 * TerminalSymbols for commonly used characters and character classes.
 * 
 * @author Tillmann Rendel
 */
public class Characters {
	/**
	 * The terminal symbol “<code>:</code>”.
	 */
	public static final TerminalSymbol COLON = TerminalSymbol.singleton("colon", ':');

	/**
	 * The terminal symbol “<code>-</code>”.
	 */
	public static final TerminalSymbol DASH = TerminalSymbol.singleton("dash", '-');

	/**
	 * A terminal symbol that is a digit (as determined by
	 * {@link Character#isDigit(int)}).
	 */
	public static final TerminalSymbol DIGIT = new TerminalSymbol("digit") {
		@Override
		public boolean accept(final int codepoint) {
			return Character.isDigit(codepoint);
		}
	};

	/**
	 * The terminal symbol “<code>.</code>”.
	 */
	public static final TerminalSymbol DOT = TerminalSymbol.singleton("dot", '.');

	/**
	 * The terminal symbol “<code>{</code>”.
	 */
	public static final TerminalSymbol LBRACE = TerminalSymbol.singleton("left brace", '{');

	/**
	 * The terminal symbol “<code>[</code>”.
	 */
	public static final TerminalSymbol LBRACKET = TerminalSymbol.singleton("left bracket", '[');

	/**
	 * A terminal symbol that is a letter (as determined by
	 * {@link Character#isLetter(int)}).
	 */
	public static final TerminalSymbol LETTER = new TerminalSymbol("letter") {
		@Override
		public boolean accept(final int codepoint) {
			return Character.isLetter(codepoint);
		}
	};

	/**
	 * The terminal symbol “<code>(</code>”.
	 */
	public static final TerminalSymbol LPAREN = TerminalSymbol.singleton("left parenthesis", '(');

	/**
	 * The terminal symbol “<code>}</code>”.
	 */
	public static final TerminalSymbol RBRACE = TerminalSymbol.singleton("right brace", '}');

	/**
	 * The terminal symbol “<code>]</code>”.
	 */
	public static final TerminalSymbol RBRACKET = TerminalSymbol.singleton("right bracket", ']');

	/**
	 * The terminal symbol “<code>)</code>”.
	 */
	public static final TerminalSymbol RPAREN = TerminalSymbol.singleton("right parenthesis", ')');

	/**
	 * The terminal symbol “<code>;</code>”.
	 */
	public static final TerminalSymbol SEMICOLON = TerminalSymbol.singleton("semicolon", ';');

	/**
	 * The terminal symbol “<code> </code>” (space).
	 */
	public static final TerminalSymbol SPACE = TerminalSymbol.singleton("space", ' ');

}
