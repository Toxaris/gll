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
 * Test that the parser correctly handles a grammar with hidden left-recursion.
 * 
 * <pre>
 * S  ::=  C a  |  d
 * B  ::=  ε  |  a
 * C  ::=  b  |  B C b  |  b b
 * </pre>
 * 
 * <p>
 * This is grammar Γ₁ from Scott and Johnstone (2010, Sec. 5).
 * </p>
 * 
 * @author Tillmann Rendel
 */
public class TestParserWithHiddenLeftRecursion extends AllTests {
	private Sort S = new Sort("S");

	/**
	 * Create the grammar.
	 */
	@Before
	public void setUp() {
		final Sort B = new Sort("B");
		final Sort C = new Sort("C");

		final TerminalSymbol a = TerminalSymbol.singleton('a');
		final TerminalSymbol b = TerminalSymbol.singleton('b');
		final TerminalSymbol d = TerminalSymbol.singleton('d');

		S.add(C, a);
		S.add(d);

		B.add();
		B.add(a);

		C.add(b);
		C.add(B, C, b);
		C.add(b, b);
	}

	/**
	 * Test that the empty word {@code ""} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testEmpty() throws IOException {
		assertRejected(S, "");
	}

	/**
	 * Test that the word {@code "d"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testD() throws IOException {
		assertAccepted(S, "d");
	}

	/**
	 * Test that the word {@code "ba"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testBA() throws IOException {
		assertAccepted(S, "ba");
	}

	/**
	 * Test that the word {@code "bba"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testBBA() throws IOException {
		assertAccepted(S, "bba");
	}

	/**
	 * Test that the word {@code "aba"} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testABA() throws IOException {
		assertRejected(S, "aba");
	}

	/**
	 * Test that the word {@code "abba"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testABBA() throws IOException {
		assertAccepted(S, "abba");
	}

	/**
	 * Test that the word {@code "aabbba"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testAABBBA() throws IOException {
		assertAccepted(S, "aabbba");
	}
}