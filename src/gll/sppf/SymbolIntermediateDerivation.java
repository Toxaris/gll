/**
 * 
 */
package gll.sppf;

import gll.grammar.Slot;
import graph.dot.Color;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A derivation node in the shared packed parse forest with children. The
 * children are packed nodes.
 * 
 * @author Tillmann Rendel
 */
public class SymbolIntermediateDerivation extends IntermediateDerivation<Binary> {
	/**
	 * The children of this node
	 */
	private final Set<Binary> children = new HashSet<Binary>();

	/**
	 * Create branch.
	 * 
	 * @param first
	 * @param last
	 */
	public SymbolIntermediateDerivation(final Slot slot, final Position first, final Position last) {
		super(slot, first, last);
	}

	/**
	 * Add new child to this node. This method is called to merge derivation
	 * nodes.
	 * 
	 * @param child
	 *            the new child
	 */
	public void add(final Binary child) {
		children.add(child);
	}

	@Override
	public Set<Binary> getChildren() {
		return children;
	}

	/**
	 * Return the color this node should be drawn in. The same color is used for
	 * the node, the text of the node, and all edges starting at the node.
	 * 
	 * <p>
	 * This implementation returns {@link Color#red}, if this node has more than
	 * one child, that is, if this node represents an ambiguity, or
	 * {@link Color#blue} otherwise.
	 * </p>
	 * 
	 * @return the color this node should be drawn in.
	 */
	@Override
	public Color getColor() {
		if (children.size() > 1) {
			return Color.red;
		} else {
			return Color.blue;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubderivations(final List<SymbolDerivation<?, ?>> result) {
		if (getChildren().size() != 1) {
			throw new Error("parse error or ambiguous derivation!");
		}

		final Binary child = getChildren().iterator().next();

		child.getSubderivations(result);
	}
}
