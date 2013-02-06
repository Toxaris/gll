/**
 * 
 */
package gll.parser;

import gll.grammar.Slot;
import gll.grammar.Sort;
import gll.gss.Frame;
import gll.gss.Initial;
import gll.gss.Stack;
import gll.sppf.Intermediate;
import gll.sppf.IntermediateCons;
import gll.sppf.IntermediateEmpty;
import gll.sppf.NonterminalSymbolDerivation;
import gll.sppf.Position;
import gll.sppf.SymbolDerivation;
import gll.sppf.TerminalSymbolDerivation;
import gll.sppf.Unary;

/**
 * An interface to the state of a GLL parser.
 * 
 * @author Tillmann Rendel
 */
public interface State {

	/**
	 * Create a packed node in the SPPF to combine the two intermediate
	 * derivations.
	 * 
	 * <p>
	 * I think this function shows up in the paper on GLL parsing?
	 * </p>
	 * 
	 * @param slot
	 *            the current grammar slot
	 * @param lhs
	 *            the first intermediate derivation to combine in the binary
	 *            node
	 * @param rhs
	 *            the second intermediate derivation to combine in the binary
	 *            node
	 * @return a intermediate derivation that contains a packed node that
	 *         combines {@code lhs} and {@code rhs}
	 */
	IntermediateCons append(final Slot slot, final Intermediate<?> lhs, final SymbolDerivation<?, ?> rhs);

	/**
	 * Create an empty derivation for some grammar slot.
	 * 
	 * <p>
	 * I think this function shows up in the paper on GLL parsing?
	 * </p>
	 * 
	 * @param slot
	 *            the current grammar slot
	 * @return empty derivation
	 */
	IntermediateEmpty createEmpty();

	/**
	 * Create a derivation for a nonterminal symbol.
	 * 
	 * @param sort
	 *            the sort of the nonterminal symbol
	 * @param first
	 *            the first token of the new derivation
	 * @param derivation
	 *            the derivation for the production
	 * @return a derivation for the sort
	 */
	NonterminalSymbolDerivation createNonterminalSymbolDerivation(final Sort sort, final Position first,
			final Unary derivation);

	/**
	 * Create a token derivation for the current token.
	 * 
	 * @return token derivation
	 */
	TerminalSymbolDerivation createTokenDerivation();

	/**
	 * Mark as scheduled. Return whether it was scheduled before.
	 * 
	 * @param slot
	 * @param frame
	 * @return
	 */
	boolean deadNow(final Slot slot, final Stack frame);

	/**
	 * Return the initial stack frame.
	 * 
	 * @return the initial stack frame.
	 */
	Initial empty();

	/**
	 * Return the current position in the token stream.
	 * 
	 * @return current position
	 */
	int getPosition();

	/**
	 * Return the result of parsing.
	 * 
	 * @return the result of parsing.
	 */
	NonterminalSymbolDerivation getResult();

	/**
	 * Mark a result as being popped from a frame.
	 * 
	 * <p>
	 * We have to remember the popped results because we want to consider them
	 * if we later link additional parent frames to the frame. See
	 * {@link #push(Slot, Stack, int, IntermediateCons)}.
	 * </p>
	 * 
	 * @param frame
	 * @param result
	 */
	void markPopped(final Frame frame, final SymbolDerivation<?, ?> result);

	/**
	 * Prepare the parser for consuming the next token.
	 * 
	 * @param codepoint
	 *            the next token
	 */
	void nextToken(final int codepoint);

	/**
	 * Create a stack frame with the given data.
	 * 
	 * <p>
	 * If the newly created stack frame already exists, we reconsider all
	 * results that have been marked for the existing frame. See
	 * {@link #markPopped(Frame, SymbolDerivation)}.
	 * </p>
	 * 
	 * <p>
	 * This is the function <emph>create</emph> from Scott and Johnstone (2010).
	 * </p>
	 * 
	 * @param slot
	 * @param caller
	 * @param token
	 * @param derivation
	 */
	Stack push(final Slot slot, final Stack caller, final int token, final Intermediate<?> derivation);

	/**
	 * Schedule a parsing process to be run after we finish processing the
	 * current token. This method is only called after processing a terminal
	 * symbol, so the first action of the future process will be to jump back to
	 * the slot in the grammar after the terminal symbol.
	 * 
	 * @param caller
	 *            the stack frame of the caller of the terminal symbol. This
	 *            will be the stack frame of the future parsing process.
	 * @param derivation
	 *            the derivation of the token just consumed. This will be merged
	 *            with the derivation in the caller stack frame.
	 */
	void scheduleLater(final Stack caller, final TerminalSymbolDerivation derivation);

	/**
	 * Schedule a parsing process to be run before we finish processing the
	 * current token. This method is called after all kinds of processing steps
	 * except consuming a token, so the first action of the scheduled parsing
	 * process will be to continue parsing at some grammar slot.
	 * 
	 * @param slot
	 *            the grammar slot to be processed by the scheduled process
	 * @param caller
	 *            the stack frame to be associated with the scheduled process
	 * @param derivation
	 *            the intermediate derivation constructed so far
	 */
	void scheduleNow(final Slot slot, final Stack caller, final Intermediate<?> derivation);

	/**
	 * Exports the graph-structured stack to a <code>*.dot</code> file (for
	 * GraphViz).
	 * 
	 * @param file
	 *            the name of the file to write to
	 */
	void writeGSS(final String file);

}