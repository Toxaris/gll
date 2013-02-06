/**
 * 
 */
package gll.grammar;

import gll.parser.State;
import gll.sppf.DerivationLabel;
import gll.sppf.IntermediateDerivation;
import gll.sppf.SymbolDerivation;
import gll.sppf.SymbolIntermediateDerivation;

/**
 * @author Tillmann Rendel
 * 
 */
public abstract class ReturnSlot extends Slot {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fomegastar.syntax.parser.Slot#createDerivation(fomegastar.syntax.parser
	 * .Derivation, fomegastar.syntax.parser.Derivation)
	 */
	@Override
	public SymbolIntermediateDerivation createDerivation(final State state, final IntermediateDerivation lhs,
			final SymbolDerivation<?, ?> rhs) {
		return state.createBranch(this, lhs, rhs);
	}

	public abstract DerivationLabel getLabel();
}
