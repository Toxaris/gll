/**
 * 
 */
package gll.grammar;

import gll.gss.Stack;
import gll.parser.State;
import gll.sppf.Intermediate;
import gll.sppf.IntermediateCons;
import gll.sppf.SymbolDerivation;

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
	public IntermediateCons createDerivation(final State state, final Intermediate<?> lhs,
			final SymbolDerivation<?, ?> rhs) {
		// TODO avoid having to implement this method
		return null;
	}

	@Override
	public void parse(final State state, final Stack frame, final Intermediate<?> derivation, final int codepoint) {
	}
}
