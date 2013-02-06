/**
 * 
 */
package graph;

import graph.dot.Color;
import graph.dot.GraphBuilder;
import graph.dot.Node;

import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * A node in a directed graph.
 * 
 * @author Tillmann Rendel
 * 
 */
public abstract class GraphNode<Child extends GraphNode<?>> {
	/**
	 * Return the children of this graph node.
	 * 
	 * @return the children of this graph node.
	 */
	public abstract Set<? extends Child> getChildren();

	/**
	 * Return the indexed children of this graph node. The indexed children are
	 * a subset of the children that can be meaningfully selected by position.
	 * 
	 * <p>
	 * This implementation returns an empty list.
	 * </p>
	 * 
	 * @return the indexed children of this graph node.
	 */
	public List<Child> getIndexedChildren() {
		return Collections.emptyList();
	}

	/**
	 * Return whether this node should be drawn with rounded corners.
	 * 
	 * <p>
	 * This implementation returns {@code false}.
	 * </p>
	 * 
	 * @return {@code true} if this node should be drawn with rounded corners,
	 *         {@code false} otherwise.
	 */
	public boolean isRounded() {
		return false;
	}

	/**
	 * Return the color this node should be drawn in. The same color is used for
	 * the node, the text of the node, and all edges starting at the node.
	 * 
	 * <p>
	 * This implementation returns {@link Color#base00}, a dark gray color
	 * suitable for light background.
	 * </p>
	 * 
	 * @return the color this node should be drawn in.
	 */
	public Color getColor() {
		return Color.base00;
	}

	/**
	 * Export this node and all reachable nodes to dot format.
	 * 
	 * @param builder
	 *            the builder to export to
	 * @return The dot node created for this graph node.
	 */
	public Node toDot(GraphBuilder builder) {
		return builder.createNode(this, getColor(), isRounded(), getLabels());
	}

	/**
	 * Return the name of an indexed child.
	 * 
	 * <p>
	 * This implementation always returns the empty string.
	 * </p>
	 * 
	 * @param index
	 *            the index to return the name for.
	 * @return the name of that child.
	 */
	public String getPortName(int index) {
		return "";
	}

	/**
	 * Return the components of this node's label.
	 * 
	 * @return the components of this node's label.
	 */
	public abstract String[] getLabels();

	/**
	 * Return whether this node is used to represent a multi-edge or labeled
	 * edge.
	 * 
	 * <p>
	 * This implementation returns {@code false}.
	 * </p>
	 * 
	 * @return {@code true} if this node is used to represent a multi-edge or
	 *         labeled edge, {@code false} otherwise.
	 */
	public boolean isEdge() {
		return false;
	}
	
	/**
	 * Return a string representation of this graph node.
	 * 
	 * <p>
	 * This implementation constructs a human-readable string from the result of
	 * {@link #getLabels()}.
	 * </p>
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		boolean first = true;
		for (String label : getLabels()) {
			if (first) {
				first = false;
			} else {
				builder.append(", ");
			}
			builder.append(label);
		}
		builder.append(")");
		return builder.toString();
	}
}