/**
 * 
 */
package gll.sppf;

import gll.grammar.Slot;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A derivation node in the shared packed parse forest that corresponds to a
 * derivation of the special symbol ε.
 * 
 * Shouldn't this be <code>EmptySymbolDerivation</code>?
 * 
 * @author Tillmann Rendel
 */
public class EmptyIntermediateDerivation extends IntermediateDerivation {
	/**
	 * Create an empty derivation.
	 * 
	 * @param tokenIndex
	 */
	public EmptyIntermediateDerivation(final Slot slot, final Position previous, final Position next) {
		super(slot, next, previous);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Binary> getChildren() {
		return Collections.emptySet();
	}

	@Override
	public void getSubderivations(final List<SymbolDerivation<?, ?>> result) {
	}
}
