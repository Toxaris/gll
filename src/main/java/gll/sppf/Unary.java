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
 * A <i>unary</i> packed derivation node in the shared packed parse forest.
 * 
 * <p>
 * The packed node is labeled by a production and points to one derivation node.
 * Two unary packed nodes are merged if they are fully equivalent, that is, they
 * are labeled by the same production and point to the same derivation node.
 * </p>
 * 
 * @author Tillmann Rendel
 */
public class Unary extends Packed {
	private final Intermediate<?> child;

	private final Production production;

	/**
	 * Create unary packed derivation.
	 * 
	 * @param production
	 * @param child
	 */
	public Unary(final Production production, final Intermediate<?> child) {
		super();
		this.production = production;
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
		final Unary other = (Unary) obj;
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
		return production.extract(this);
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
	public Set<? extends Intermediate<?>> getChildren() {
		return Collections.singleton(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getLabels() {
		return new String[] { production.toString() };
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
