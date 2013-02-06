/**
 * 
 */
package gll.grammar;

import gll.parser.State;
import gll.sppf.DerivationLabel;
import gll.sppf.Intermediate;
import gll.sppf.IntermediateCons;
import gll.sppf.SymbolDerivation;

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
	public IntermediateCons createDerivation(final State state, final Intermediate<?> lhs,
			final SymbolDerivation<?, ?> rhs) {
		return state.append(this, lhs, rhs);
	}

	public abstract DerivationLabel getLabel();
}
