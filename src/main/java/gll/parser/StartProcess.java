/**
 * 
 */
package gll.parser;

import gll.grammar.Sort;
import gll.gss.Stack;

/**
 * @author Tillmann Rendel
 * 
 */
public class StartProcess extends Process {
	private final Sort sort;

	/**
	 * Create start process.
	 * 
	 * @param stack
	 * @param sort
	 */
	public StartProcess(final Stack stack, final Sort sort) {
		super(stack);
		this.sort = sort;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final State state, final int codepoint) {
		sort.call(state, stack, codepoint);
	}
}
