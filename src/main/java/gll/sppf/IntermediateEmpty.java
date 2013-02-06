/**
 * 
 */
package gll.sppf;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A derivation node in the shared packed parse forest that represents an empty
 * list of derivations.
 * 
 * @author Tillmann Rendel
 */
public class IntermediateEmpty extends Intermediate<EmptyLabel> {
	private final Position first;

	private final Position last;

	/**
	 * Create an empty derivation.
	 * 
	 * @param tokenIndex
	 */
	public IntermediateEmpty(final Position first, final Position last) {
		super(EmptyLabel.create());
		this.first = first;
		this.last = last;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Binary> getChildren() {
		return Collections.emptySet();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubderivations(final List<SymbolDerivation<?, ?>> result) {
	}
}
