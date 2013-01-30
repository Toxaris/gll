/**
 * 
 */
package gll.grammar;

import gll.gss.Stack;
import gll.parser.State;
import gll.sppf.IntermediateDerivation;
import gll.sppf.SymbolDerivation;
import gll.sppf.SymbolIntermediateDerivation;

/**
 * @author Tillmann Rendel
 * 
 */
public class FinalSlot extends Slot {
	/**
	 * Create the final slot.
	 */
	public FinalSlot() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendPrefix(final Slot slot, final StringBuilder prefix) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendSuffix(final Slot slot, final StringBuilder prefix, final StringBuilder suffix) {
	}

	@Override
	public SymbolIntermediateDerivation createDerivation(final State state, final IntermediateDerivation<?> lhs,
			final SymbolDerivation<?, ?> rhs) {
		// TODO avoid having to implement this method
		return null;
	}

	@Override
	public void parse(final State state, final Stack frame, final IntermediateDerivation<?> derivation,
			final int codepoint) {
	}
}
