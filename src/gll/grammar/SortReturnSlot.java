/**
 * 
 */
package gll.grammar;

import gll.gss.Stack;
import gll.parser.State;
import gll.sppf.DerivationLabel;
import gll.sppf.IntermediateDerivation;
import gll.sppf.NonterminalSymbolDerivation;
import gll.sppf.ProductionDerivation;

/**
 * @author Tillmann Rendel
 * 
 */
public class SortReturnSlot extends ReturnSlot {
	/**
	 * The production this slot is associated with.
	 */
	private final Production production;

	/**
	 * Create ReturnSlot.
	 * 
	 * @param the
	 *            production this slot is associated with.
	 */
	public SortReturnSlot(final Production production) {
		this.production = production;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendSuffix(final Slot slot, final StringBuilder prefix, final StringBuilder suffix) {
		super.appendSuffix(slot, prefix, suffix);
		production.appendPrefix(slot, prefix);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DerivationLabel getLabel() {
		return production.getSort();
	}

	/**
	 * @return
	 */
	public Sort getSort() {
		return production.getSort();
	}

	/**
	 * Parse according to this slot.
	 * 
	 * <p>
	 * This implementation schedules processes at the return addresses according
	 * to the stack frame of the current process.
	 * </p>
	 * 
	 * @param state
	 *            the parser state
	 * @param frame
	 *            the stack frame of the current parser process
	 * @param derivation
	 *            the current derivation
	 * @param codepoint
	 *            the current codepoint to parse
	 */
	@Override
	public void parse(final State state, final Stack frame, final IntermediateDerivation<?> derivation,
			final int codepoint) {

		final ProductionDerivation wrapped = new ProductionDerivation(production, derivation);
		final NonterminalSymbolDerivation result = state.createNonterminalSymbolDerivation(production.getSort(),
				derivation.getFirst(), wrapped);
		frame.schedule(state, result, codepoint);
	}
}
