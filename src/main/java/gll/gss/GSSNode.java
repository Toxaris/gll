/**
 * 
 */
package gll.gss;

import graph.GraphNode;

/**
 * A node in the graph-structured stack. That can either be a stack or a link
 * between stacks.
 * 
 * @author Tillmann Rendel
 */
public abstract class GSSNode<Child extends GSSNode<?>> extends
		GraphNode<Child> {
}
