/**
 * 
 */
package gll.grammar;

import gll.gss.Stack;
import gll.parser.State;
import gll.sppf.DerivationLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * A syntactic sort (= nonterminal symbol) in the grammar.
 * 
 * @author Tillmann Rendel
 */
public class Sort extends Symbol implements DerivationLabel {
	/**
	 * The list of alternative productions for this syntactic sort.
	 */
	private final List<Production> alternatives = new ArrayList<Production>();

	/**
	 * Create a named sort.
	 * 
	 * @param name
	 *            a human-readable name, used for debugging and error messages.
	 */
	public Sort(final String name) {
		super("‹" + name + "›");
	}

	/**
	 * Add an alternative to this syntactic sort.
	 * 
	 * The alternative is specified as a production.
	 * 
	 * @param production
	 *            the production to add as an alternative.
	 */
	public void add(final Production production) {
		alternatives.add(production);
	}

	/**
	 * Add an alternative to this syntactic sort.
	 * 
	 * The alternative is specified as a list of actions that are automatically
	 * wrapped in a production.
	 * 
	 * @param symbols
	 *            the sequence of symbols to add as an alternative.
	 * @return the new production
	 */
	public Production add(final Symbol... symbols) {
		final Production production = new Production(this, symbols);
		add(production);
		return production;
	}

	public void call(final State state, final Stack frame) {
		for (final Production production : alternatives) {
			production.schedule(state, frame);
		}
	}

	/**
	 * Process this symbol during parsing.
	 * 
	 * <p>
	 * This implementation processes each alternative production.
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
		call(state, frame);
	}
}
