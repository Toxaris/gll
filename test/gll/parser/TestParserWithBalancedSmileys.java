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
	public final void testExample01() throws IOException {
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
	public final void testExample02() throws IOException {
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
	public final void testExample03() throws IOException {
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
	public final void testExample04() throws IOException {
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
	public final void testExample05() throws IOException {
		assertRejected(S, ")(");
	}

	/**
	 * Test that the word
	 * {@code "(((a)):()a(()(((:))a((:)():(((()()a)))(:a(::)(a)))(a)((a::():(a)():)a(a(a(:aa(:()(a(((((()))))))))"}
	 * is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample06() throws IOException {
		assertRejected(S,
				"(((a)):()a(()(((:))a((:)():(((()()a)))(:a(::)(a)))(a)((a::():(a)():)a(a(a(:aa(:()(a(((((()))))))))");
	}

	/**
	 * Test that the word
	 * {@code "():)((()():(:())))::aa((((:(((:)))::a:(:))()a)):(a):::((()a((a(aa(():))(():())((::a)a)):)()"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample07() throws IOException {
		assertAccepted(S, "():)((()():(:())))::aa((((:(((:)))::a:(:))()a)):(a):::((()a((a(aa(():))(():())((::a)a)):)()");
	}

	/**
	 * Test that the word
	 * {@code ":(a):(:)aa)a(:()::():))a:aaa:)(:)((()()))a()(((()(:)))(:(aa:()())())a((a)a:(:()))(a((():)))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample08() throws IOException {
		assertAccepted(S, ":(a):(:)aa)a(:()::():))a:aaa:)(:)((()()))a()(((()(:)))(:(aa:()())())a((a)a:(:()))(a((():)))");
	}

	/**
	 * Test that the word
	 * {@code ":a:)(:))()(()()a)aaa::a()()a:()()a::)((()(a(a))))try implementing sleep sort if you are stuck:(:)a)"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample09() throws IOException {
		assertAccepted(S,
				":a:)(:))()(()()a)aaa::a()()a:()()a::)((()(a(a))))try implementing sleep sort if you are stuck:(:)a)");
	}

	/**
	 * Test that the word
	 * {@code "(a())(::)(a))():(((a(()(:))a(:)))(:(:(:((():)(a))(:))(a)():(:(()aa):)(a((())a)a((a):)()(:("}
	 * is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample10() throws IOException {
		assertRejected(S, "(a())(::)(a))():(((a(()(:))a(:)))(:(:(:((():)(a))(:))(a)():(:(()aa):)(a((())a)a((a):)()(:(");
	}

	/**
	 * Test that the word
	 * {@code "(::a((a)a:()):):a)aa:)a(:::))(a())aa(a():))(:)a)((():)(:a:)a))):a(a)((:()(()())a))()a((()a))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample11() throws IOException {
		assertAccepted(S,
				"(::a((a)a:()):):a)aa:)a(:::))(a())aa(a():))(:)a)((():)(:a:)a))):a(a)((:()(()())a))()a((()a))");
	}

	/**
	 * Test that the word
	 * {@code "()(((a)((aa)))a)a()(a)(aa:a)()(((:())aa)):()():():a:(a)(a())a:)::a:(aa:):()((a:)())aa)a(a:)"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample12() throws IOException {
		assertAccepted(S, "()(((a)((aa)))a)a()(a)(aa:a)()(((:())aa)):()():():a:(a)(a())a:)::a:(aa:):()((a:)())aa)a(a:)");
	}

	/**
	 * Test that the word
	 * {@code ":)()((a)):(():a:a:)(:a)):)(()(:)::::(a(::a())(a):(:((((:(aa(()))a)(((((((((()a()a):)))((:)))))))))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample13() throws IOException {
		assertAccepted(S,
				":)()((a)):(():a:a:)(:a)):)(()(:)::::(a(::a())(a):(:((((:(aa(()))a)(((((((((()a()a):)))((:)))))))))");
	}

	/**
	 * Test that the word
	 * {@code "a(a)::(((::)))())((a)(:((:a())):((::(:()(a)))i am trapped in a test case generator :(:(a(:::))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample14() throws IOException {
		assertAccepted(S,
				"a(a)::(((::)))())((a)(:((:a())):((::(:()(a)))i am trapped in a test case generator :(:(a(:::))");
	}

	/**
	 * Test that the word
	 * {@code "():)a((a:((aaa(()))(((()a))()))a(:)):)a((:())(a:(:):((a(:(::())a()())::()a)(a)):((aa)a(:(())"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample15() throws IOException {
		assertAccepted(S,
				"():)a((a:((aaa(()))(((()a))()))a(:)):)a((:())(a:(:):((a(:(::())a()())::()a)(a)):((aa)a(:(())");
	}

	/**
	 * Test that the word
	 * {@code "((:):::(()()):)(()()():())aaa)(:(a:)a:((())a(((a(:())aa():a:)((()):)(()(:)(a())a:()a)a():("}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample16() throws IOException {
		assertAccepted(S, "((:):::(()()):)(()()():())aaa)(:(a:)a:((())a(((a(:())aa():a:)((()):)(()(:)(a())a:()a)a():(");
	}

	/**
	 * Test that the word {@code "(:a))"} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample17() throws IOException {
		assertRejected(S, "(:a))");
	}

	/**
	 * Test that the word
	 * {@code "::((:))(((:)(aaa)(a())()(a:)(:)(:)()):)a())aa)())(():a):()::):)a()())a()):):(:a)a):()(a)(a)"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample18() throws IOException {
		assertAccepted(S, "::((:))(((:)(aaa)(a())()(a:)(:)(:)()):)a())aa)())(():a):()::):)a()())a()):):(:a)a):()(a)(a)");
	}

	/**
	 * Test that the word
	 * {@code "()a(:)(a:a):(())):a()():((a(:):a()()::)(a:)(()a((a:)(a)a(a:a:)(a)a(a:(()()()::a()a()(()a:())))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample19() throws IOException {
		assertAccepted(S,
				"()a(:)(a:a):(())):a()():((a(:):a()()::)(a:)(()a((a:)(a)a(a:a:)(a)a(a:(()()()::a()a()(()a:())))");
	}

	/**
	 * Test that the word
	 * {@code "()((:a(a()()a))())((:a(:a)(()a((((a((a(()(:aa()()()))):)(():):)(:(a))():(())(():()):):(()a))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample20() throws IOException {
		assertAccepted(S,
				"()((:a(a()()a))())((:a(:a)(()a((((a((a(()(:aa()()()))):)(():):)(:(a))():(())(():()):):(()a))");
	}

	/**
	 * Test that the word {@code "(((((((((())))))))))"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample21() throws IOException {
		assertAccepted(S, "(((((((((())))))))))");
	}

	/**
	 * Test that the word {@code "(((((((((((((((((((())))))))))))))))))))"} is
	 * accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample22() throws IOException {
		assertAccepted(S, "(((((((((((((((((((())))))))))))))))))))");
	}

	/**
	 * Test that the word {@code "((((((((((:))))))))))"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample23() throws IOException {
		assertAccepted(S, "((((((((((:))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "((((((((((((((((((((((((((((((((((((((((((((((((((:))))))))))))))))))))))))))))))))))))))))))))))))))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample24() throws IOException {
		assertAccepted(S,
				"((((((((((((((((((((((((((((((((((((((((((((((((((:))))))))))))))))))))))))))))))))))))))))))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "(((((((((((((((((((((((((((((((((((((((((((((((((((:))))))))))))))))))))))))))))))))))))))))))))))))))"}
	 * is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample25() throws IOException {
		assertRejected(S,
				"(((((((((((((((((((((((((((((((((((((((((((((((((((:))))))))))))))))))))))))))))))))))))))))))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "(a((f((g(((g((:))))g))))))::((((((((((((((((((((:)))))))))))))))))))) ((((((((((((((((((((((((((((((((((((((((((((((((((:))))))))))))))))))))))))))))))))))))))))))))))))))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample26() throws IOException {
		assertAccepted(
				S,
				"(a((f((g(((g((:))))g))))))::((((((((((((((((((((:)))))))))))))))))))) ((((((((((((((((((((((((((((((((((((((((((((((((((:))))))))))))))))))))))))))))))))))))))))))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:))))))))))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample27() throws IOException {
		assertAccepted(
				S,
				"((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "((((((((((((:))))))))))((((((((((:())))))))))))"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample28() throws IOException {
		assertAccepted(S, "((((((((((((:))))))))))((((((((((:())))))))))))");
	}

	/**
	 * Test that the word {@code "(((((((((()))))))))))"} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample29() throws IOException {
		assertRejected(S, "(((((((((()))))))))))");
	}

	/**
	 * Test that the word {@code "(((((((((((((((((((()))))))))))))))))))))"} is
	 * rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample30() throws IOException {
		assertRejected(S, "(((((((((((((((((((()))))))))))))))))))))");
	}

	/**
	 * Test that the word {@code "((((((((((:)))))))))))"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample31() throws IOException {
		assertAccepted(S, "((((((((((:)))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "(a((f((g(((g((:))))g))))))::((((((((((((((((((((:)))))))))))))))))))) ((((((((((((((((((((((((((((((((((((((((((((((((((:)))))))))))))))))))))))))))))))))))))))))))))))))))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample32() throws IOException {
		assertAccepted(
				S,
				"(a((f((g(((g((:))))g))))))::((((((((((((((((((((:)))))))))))))))))))) ((((((((((((((((((((((((((((((((((((((((((((((((((:)))))))))))))))))))))))))))))))))))))))))))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))))"}
	 * is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample33() throws IOException {
		assertAccepted(
				S,
				"((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "((((((((((((:))))))))))((((((((((:)))))))))))))"} is accepted.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample34() throws IOException {
		assertAccepted(S, "((((((((((((:))))))))))((((((((((:)))))))))))))");
	}

	/**
	 * Test that the word {@code "((((((((((:))))))))))))"} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample35() throws IOException {
		assertRejected(S, "((((((((((:))))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "((((((((((((:))))))))))((((((((((:)))))))))))))))"} is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample36() throws IOException {
		assertRejected(S, "((((((((((((:))))))))))((((((((((:)))))))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))))))))))"}
	 * is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample37() throws IOException {
		assertRejected(
				S,
				"((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))) ((((((((((:)))))))))))))))))");
	}

	/**
	 * Test that the word
	 * {@code "(a((f((g(((g((:))))g))))))::((((((((((((((((((((:)))))))))))))))))))) ((((((((((((((((((((((((((((((((((((((((((((((((((:))))))))))))))))))))))))))))))))))))))))))))))))))))))"}
	 * is rejected.
	 * 
	 * @throws IOException
	 *             when something goes wrong with the Reader.
	 */
	@Test
	public final void testExample38() throws IOException {
		assertRejected(
				S,
				"(a((f((g(((g((:))))g))))))::((((((((((((((((((((:)))))))))))))))))))) ((((((((((((((((((((((((((((((((((((((((((((((((((:))))))))))))))))))))))))))))))))))))))))))))))))))))))");
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
