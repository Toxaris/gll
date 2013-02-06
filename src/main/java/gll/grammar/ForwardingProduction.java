/**
 * 
 */
package gll.grammar;

import gll.sppf.Unary;
import gll.sppf.SymbolDerivation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tillmann Rendel
 * 
 */
public class ForwardingProduction extends Production {
	private final int index;

	/**
	 * Create ForwardingProduction.
	 * 
	 * @param sort
	 * @param symbols
	 */
	public ForwardingProduction(final Sort sort, final int index, final Symbol... symbols) {
		super(sort, symbols);
		this.index = index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object extract(final Unary derivation) {
		if (derivation.getChildren().size() != 1) {
			throw new Error("Ambiguity or parser problem!");
		}

		final List<SymbolDerivation<?, ?>> subderivations = new ArrayList<SymbolDerivation<?, ?>>();
		derivation.getChildren().iterator().next().getSubderivations(subderivations);

		if (subderivations.size() <= index) {
			throw new Error("Parser problem");
		}

		return subderivations.get(index).extract();
	}
}
