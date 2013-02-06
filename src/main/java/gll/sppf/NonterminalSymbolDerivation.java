/**
 * 
 */
package gll.sppf;

import gll.grammar.Sort;

import java.util.HashSet;
import java.util.Set;

/**
 * Should this be a unary packed node?
 * 
 * @author Tillmann Rendel
 */
public class NonterminalSymbolDerivation extends SymbolDerivation<Sort, ProductionDerivation> {

	private final Set<ProductionDerivation> children = new HashSet<ProductionDerivation>();

	private final Position first;

	private final Position last;

	/**
	 * Creates a derivation for a nonterminal symbol.
	 * 
	 * @param label
	 * @param first
	 * @param last
	 */
	public NonterminalSymbolDerivation(final Sort label, final Position first, final Position last) {
		super(label);
		this.first = first;
		this.last = last;
	}

	public void add(final ProductionDerivation child) {
		children.add(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object extract() {
		if (children.size() != 1) {
			throw new Error("Ambiguity or parser problem!");
		}
		return children.iterator().next().extract();
	}

	@Override
	public void extractText(final StringBuilder builder) {
		if (children.size() != 1) {
			throw new Error("Ambiguity or parser problem!");
		}
		children.iterator().next().extractText(builder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<? extends ProductionDerivation> getChildren() {
		return children;
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
}
