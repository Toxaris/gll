/**
 * 
 */
package gll.sppf;

import gll.grammar.Slot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A packed derivation node in the shared packed parse forest. A packed node is
 * labeled by a grammar slot and points two derivation nodes. Two packed nodes
 * are merged if they are fully equivalent, that is, they are labeled by the
 * same grammar slot and point to the same derivation nodes.
 * 
 * @author Tillmann Rendel
 */
public class Binary extends SPPFNode<Derivation<?, ?>> {
	/**
	 * The left-hand side.
	 */
	private final IntermediateDerivation lhs;

	/**
	 * The position between the left-hand side and the right-hand side.
	 */
	private final Position position;

	/**
	 * The right-hand side.
	 */
	private final SymbolDerivation<?, ?> rhs;

	/**
	 * The slot associated with this packed node.
	 */
	private final Slot slot;

	/**
	 * Create packed node.
	 * 
	 * @param slot
	 *            the slot associated with this packed node
	 * @param position
	 *            the position between the left-hand side and the right-hand
	 *            side
	 * @param lhs
	 *            the left-hand side
	 * @param rhs
	 *            the right-hand side
	 */
	public Binary(final Slot slot, final Position position, final IntermediateDerivation lhs,
			final SymbolDerivation<?, ?> rhs) {
		super();
		this.slot = slot;
		this.position = position;
		this.lhs = lhs;
		this.rhs = rhs;
	}

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
		final Binary other = (Binary) obj;
		if (lhs == null) {
			if (other.lhs != null) {
				return false;
			}
		} else if (!lhs.equals(other.lhs)) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		if (rhs == null) {
			if (other.rhs != null) {
				return false;
			}
		} else if (!rhs.equals(other.rhs)) {
			return false;
		}
		if (slot == null) {
			if (other.slot != null) {
				return false;
			}
		} else if (!slot.equals(other.slot)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the two sub-derivations.
	 * </p>
	 */
	@Override
	public Set<Derivation<?, ?>> getChildren() {
		final Set<Derivation<?, ?>> result = new HashSet<Derivation<?, ?>>();
		result.add(lhs);
		result.add(rhs);
		return result;
	}

	/**
	 * Return the indexed children of this graph node. The indexed children are
	 * a subset of the children that can be meaningfully selected by position.
	 * 
	 * <p>
	 * This implementation returns the left sub-derivation at index 0, and the
	 * right sub-derivation at index 1.
	 * </p>
	 * 
	 * @return the two sub-derivations.
	 */
	@Override
	public List<Derivation<?, ?>> getIndexedChildren() {
		final List<Derivation<?, ?>> result = new ArrayList<Derivation<?, ?>>(2);
		result.add(lhs);
		result.add(rhs);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the grammar slot associated with this packed
	 * node and the position between the two sub-derivations.
	 * </p>
	 */
	@Override
	public String[] getLabels() {
		return new String[] { /* slot.toString() , */position.getPositionString() };
	}

	/**
	 * Return the name of an indexed child.
	 * 
	 * <p>
	 * This implementation returns "left" and "right".
	 * </p>
	 * 
	 * @param index
	 *            the index to return the name for.
	 * @return the name of that child.
	 */
	@Override
	public String getPortName(final int index) {
		switch (index) {
		case 0:
			return "left";
		case 1:
			return "right";
		default:
			return super.getPortName(index);
		}
	}

	public void getSubderivations(final List<SymbolDerivation<?, ?>> result) {
		lhs.getSubderivations(result);
		result.add(rhs);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((rhs == null) ? 0 : rhs.hashCode());
		result = prime * result + ((slot == null) ? 0 : slot.hashCode());
		return result;
	}

	/**
	 * Return whether this node should be drawn with rounded corners.
	 * 
	 * <p>
	 * This implementation returns {@code true}.
	 * </p>
	 * 
	 * @return {@code true} if this node should be drawn with rounded corners,
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean isRounded() {
		return true;
	}

}
