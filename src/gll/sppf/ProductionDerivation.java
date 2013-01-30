/**
 * 
 */
package gll.sppf;

import gll.grammar.Production;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Tillmann Rendel
 */
public class ProductionDerivation extends Derivation<Production, IntermediateDerivation<?>> {
	private final IntermediateDerivation<?> child;

	/**
	 * Create ProductionDerivation.
	 * 
	 * @param label
	 * @param child
	 */
	public ProductionDerivation(final Production label, final IntermediateDerivation<?> child) {
		super(label);
		this.child = child;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ProductionDerivation other = (ProductionDerivation) obj;
		if (child == null) {
			if (other.child != null) {
				return false;
			}
		} else if (!child.equals(other.child)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public Object extract() {
		return getLabel().extract(this);
	}

	/**
	 * @param builder
	 */
	public void extractText(final StringBuilder builder) {
		final List<SymbolDerivation<?, ?>> subderivations = new ArrayList<SymbolDerivation<?, ?>>();
		child.getSubderivations(subderivations);
		for (final SymbolDerivation<?, ?> subderivation : subderivations) {
			subderivation.extractText(builder);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<? extends IntermediateDerivation<?>> getChildren() {
		return Collections.singleton(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position getFirst() {
		return child.getFirst();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position getLast() {
		return child.getLast();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		return result;
	}
}
