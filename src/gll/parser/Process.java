/**
 * 
 */
package gll.parser;

import gll.gss.Stack;

/**
 * @author Tillmann Rendel
 * 
 */
public abstract class Process {
	/**
	 * Our stack.
	 */
	protected final Stack stack;

	/**
	 * Create process.
	 * 
	 * @param stack
	 */
	public Process(final Stack stack) {
		this.stack = stack;
	}

	public abstract void execute(State state, int codepoint);
}
