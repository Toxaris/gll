/**
 * 
 */
package gll.sppf;

import gll.grammar.Slot;

import java.util.List;

/**
 * @author Tillmann Rendel
 * 
 */
public abstract class IntermediateDerivation extends Derivation<Slot, Binary> {
	private final Position first;
	private final Position last;

	/**
	 * Create IntermediateDerivation.
	 * 
	 * @param label
	 * @param first
	 * @param last
	 */
	public IntermediateDerivation(final Slot label, final Position first, final Position last) {
		super(label);
		this.first = first;
		this.last = last;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position getFirst() {
		return first;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position getLast() {
		return last;
	}

	public abstract void getSubderivations(List<SymbolDerivation<?, ?>> result);
}
