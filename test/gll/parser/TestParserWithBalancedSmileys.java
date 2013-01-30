/**
 * 
 */
package gll.parser;

import gll.grammar.Sort;
import gll.grammar.TerminalSymbol;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test that the parser correctly handles the "balanced smileys" grammar:
 * 
 * <pre>
 * S  ::=  ε | [a-z] | ' ' | ':' | ':' P | '(' S ')' | S S
 * P  ::=  '(' | ')'
 * </pre>
 * 
 * <p>
 * This grammar should accept all messages with balanced parentheses and
 * smileys. The parentheses in the smileys don't count for balancing.
 * </p>
 * 
 * <p>
 * This test case was proposed by Paolo Giarrusso, inspired by the
 * "balanced smileys" problem of the Facebook Hacker Cup 2013 qualification
 * round.
 * </p>
 * 
 * @author Tillmann Rendel
 */
public class TestParserWithBalancedSmileys extends AllTests {
	private final Sort S = new Sort("S");

	/**
	 * Create the grammar.
	 */
	@Before
	public void setUp() {
		final Sort P = new Sort("P");

		final TerminalSymbol letter = new TerminalSymbol("[a-z]") {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean accept(final int codepoint) {
				return codepoint >= 'a' && codepoint <= 'z';
			}
		};

		final TerminalSymbol space = TerminalSymbol.singleton(' ');
		final TerminalSymbol colon = TerminalSymbol.singleton(':');
		final TerminalSymbol lparen = TerminalSymbol.singleton('(');
		final TerminalSymbol rparen = TerminalSymbol.singleton(')');

		S.add();
		S.add(letter);
		S.add(space);
		S.add(colon);
		S.add(colon, P);
		S.add(lparen, S, rparen);
		S.add(S, S);

		P.add(lparen);
		P.add(rparen);
	}

	/**
	 * Test that the empty word {@code ""} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testEmpty() throws IOException {
		assertAccepted(S, "");
	}

	/**
	 * Test that the word {@code ":(("} is rejected.
	 * 
	 * <p>
	 * This is one of the examples from the Facebook Hacker Cup.
	 * </p>
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample1() throws IOException {
		assertRejected(S, ":((");
	}

	/**
	 * Test that the word {@code "i am sick today (:()"} is accepted.
	 * 
	 * <p>
	 * This is one of the examples from the Facebook Hacker Cup.
	 * </p>
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample2() throws IOException {
		assertAccepted(S, "i am sick today (:()");
	}

	/**
	 * Test that the word {@code "(:)"} is accepted.
	 * 
	 * <p>
	 * This is one of the examples from the Facebook Hacker Cup.
	 * </p>
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample3() throws IOException {
		assertAccepted(S, "(:)");
	}

	/**
	 * Test that the word {@code "hacker cup: started :):)"} is accepted.
	 * 
	 * <p>
	 * This is one of the examples from the Facebook Hacker Cup.
	 * </p>
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample4() throws IOException {
		assertAccepted(S, "hacker cup: started :):)");
	}

	/**
	 * Test that the word {@code ")("} is rejected.
	 * 
	 * <p>
	 * This is one of the examples from the Facebook Hacker Cup.
	 * </p>
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample5() throws IOException {
		assertRejected(S, ")(");
	}

	/**
	 * Test that the word {@code "hello world"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testHelloWorld() throws IOException {
		assertAccepted(S, "hello world");
	}

}
