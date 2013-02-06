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
	 * The terminal symbol “<code>,</code>”.
	 */
	public static final TerminalSymbol COMMA = TerminalSymbol.singleton("comma", ',');

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
	 * The terminal symbol “<code>=</code>”.
	 */
	public static final TerminalSymbol EQUALS = TerminalSymbol.singleton("equals", '=');

	/**
	 * The terminal symbol “<code>></code>”.
	 */
	public static final TerminalSymbol GT = TerminalSymbol.singleton("greater than", '>');

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
	 * A terminal symbol that is an lower case letter (as determined by
	 * {@link Character#isLowerCase(int)}).
	 */
	public static final TerminalSymbol LOWER_CASE = new TerminalSymbol("upper case letter") {
		@Override
		public boolean accept(final int codepoint) {
			return Character.isLowerCase(codepoint);
		}
	};

	/**
	 * The terminal symbol “<code>(</code>”.
	 */
	public static final TerminalSymbol LPAREN = TerminalSymbol.singleton("left parenthesis", '(');

	/**
	 * The terminal symbol “<code>|</code>”.
	 */
	public static final TerminalSymbol PIPE = TerminalSymbol.singleton("pipe", '|');

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

	/**
	 * A terminal symbol that is an upper case letter (as determined by
	 * {@link Character#isUpperCase(int)}).
	 */
	public static final TerminalSymbol UPPER_CASE = new TerminalSymbol("upper case letter") {
		@Override
		public boolean accept(final int codepoint) {
			return Character.isUpperCase(codepoint);
		}
	};

}
