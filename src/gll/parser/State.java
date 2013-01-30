package gll.parser;

import gll.grammar.Slot;
import gll.grammar.Sort;
import gll.gss.Frame;
import gll.gss.Initial;
import gll.gss.Link;
import gll.gss.Stack;
import gll.sppf.AfterInput;
import gll.sppf.BeforeInput;
import gll.sppf.Binary;
import gll.sppf.EmptyIntermediateDerivation;
import gll.sppf.InputSymbol;
import gll.sppf.IntermediateDerivation;
import gll.sppf.NonterminalSymbolDerivation;
import gll.sppf.Position;
import gll.sppf.ProductionDerivation;
import gll.sppf.SymbolDerivation;
import gll.sppf.SymbolIntermediateDerivation;
import gll.sppf.TerminalSymbolDerivation;
import graph.dot.GraphBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cache.Cache1;
import cache.Cache2;

/**
 * The state of a GLL parser.
 * 
 * @author Tillmann Rendel
 */
public class State {
	/**
	 * The deque of currently active descriptors.
	 * 
	 * <p>
	 * This set contains all descriptors that have to be executed to fully
	 * process the current token.
	 * </p>
	 */
	public final Deque<Process> active = new ArrayDeque<Process>();

	/**
	 * The set of already scheduled descriptors for the next token.
	 * 
	 * <p>
	 * This set is used to handle left recursion by ensuring that no descriptor
	 * is scheduled twice.
	 * </p>
	 * 
	 * @param foo
	 *            bar baz boo
	 */
	public final Set<Stack> deadLater = new HashSet<Stack>();

	/**
	 * The set of already scheduled descriptors for the current token.
	 * 
	 * <p>
	 * This set is used to handle left recursion by ensuring that no descriptor
	 * is scheduled twice.
	 * </p>
	 */
	public final Map<Slot, Set<Stack>> deadNow = new HashMap<Slot, Set<Stack>>();

	/**
	 * All stack frames created for the current token.
	 */
	public Map<Slot, Frame> frames = new HashMap<Slot, Frame>();

	/**
	 * The set of descriptors for the next token.
	 * 
	 * <p>
	 * This set is filled during processing of the current token.
	 * </p>
	 */
	public Set<FutureProcess> future = new HashSet<FutureProcess>();

	public Position next = BeforeInput.create();

	/**
	 * The set of stack frames that have been popped for the current token.
	 */
	public final Map<Frame, Set<SymbolDerivation<?, ?>>> popped = new HashMap<Frame, Set<SymbolDerivation<?, ?>>>();

	/**
	 * Our position in the token stream.
	 * 
	 * <p>
	 * 0 is the position just before the first token.
	 * </p>
	 */
	public int position = -1;

	public Sort start;

	/**
	 * The derivation associated with the current token.
	 */
	public TerminalSymbolDerivation tokenDerivation;
	public Slot tokenReturnSlot;

	/**
	 * The initial stack frame.
	 */
	private final Initial empty = new Initial();

	private final Cache1<Slot, EmptyIntermediateDerivation> emptyIntermediateDerivations = new Cache1<Slot, EmptyIntermediateDerivation>() {
		@Override
		protected EmptyIntermediateDerivation compute(final Slot label) {
			return new EmptyIntermediateDerivation(label, previous, next);
		}
	};

	private Position first;

	/**
	 * The set of all GSS stacks ever created (for debugging!)
	 */
	private final Set<Stack> gss = new HashSet<Stack>();

	private final Cache2<Sort, Position, NonterminalSymbolDerivation> nonterminalSymbolDerivations = new Cache2<Sort, Position, NonterminalSymbolDerivation>() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected NonterminalSymbolDerivation compute(final Sort sort, final Position first) {
			return new NonterminalSymbolDerivation(sort, first, previous);
		}
	};

	private Position previous = null;

	private NonterminalSymbolDerivation result;

	private final Cache2<Slot, Position, SymbolIntermediateDerivation> symbolIntermediateDerivations = new Cache2<Slot, Position, SymbolIntermediateDerivation>() {
		@Override
		protected SymbolIntermediateDerivation compute(final Slot label, final Position first) {
			return new SymbolIntermediateDerivation(label, first, previous);
		}
	};

	/**
	 * @param slot
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	public SymbolIntermediateDerivation createBranch(final Slot slot, final IntermediateDerivation<?> lhs,
			final SymbolDerivation<?, ?> rhs) {
		final Position first = lhs.getFirst();
		final Position middle = rhs.getFirst();

		final SymbolIntermediateDerivation result = symbolIntermediateDerivations.apply(slot, first);

		result.add(new Binary(slot, middle, lhs, rhs));

		return result;
	}

	/**
	 * @param slot
	 * @return
	 */
	public IntermediateDerivation<?> createEmpty(final Slot slot) {
		return emptyIntermediateDerivations.apply(slot);
	}

	/**
	 * @param wrapped
	 * @return
	 */
	public NonterminalSymbolDerivation createNonterminalSymbolDerivation(final Sort sort, final Position first,
			final ProductionDerivation derivation) {
		final NonterminalSymbolDerivation result = nonterminalSymbolDerivations.apply(sort, first);
		result.add(derivation);
		return result;
	}

	/**
	 * Marks as scheduled. Returns whether it was scheduled before.
	 * 
	 * @param production
	 * @param action
	 * @param caller
	 * @return
	 */
	public boolean deadNow(final Slot slot, final Stack frame) {
		Set<Stack> frames = deadNow.get(slot);
		if (frames == null) {
			frames = new HashSet<Stack>();
			deadNow.put(slot, frames);
		}

		if (frames.contains(frame)) {
			return true;
		} else {
			frames.add(frame);
			return false;
		}
	}

	/**
	 * Return the initial stack frame.
	 * 
	 * @return the initial stack frame.
	 */
	public Initial empty() {
		gss.add(empty);
		return empty;
	}

	public NonterminalSymbolDerivation getResult() {
		return result;
	}

	/**
	 * @param node
	 */
	public void markPopped(final Frame frame, final SymbolDerivation<?, ?> result) {
		Set<SymbolDerivation<?, ?>> results = popped.get(frame);
		if (results == null) {
			results = new HashSet<SymbolDerivation<?, ?>>();
		}
		results.add(result);
	}

	public void nextToken(final int codepoint) {
		// reset caches that depend on current token position
		active.addAll(future);
		future.clear();

		deadNow.clear();
		deadLater.clear();

		frames.clear();
		popped.clear();
		symbolIntermediateDerivations.clear();
		nonterminalSymbolDerivations.clear();
		emptyIntermediateDerivations.clear();

		// increase token position
		position = position + 1;

		// create current symbol and related things
		previous = next;
		if (codepoint >= 0) {
			final InputSymbol symbol = new InputSymbol(codepoint, position);
			tokenDerivation = new TerminalSymbolDerivation(symbol);
			next = symbol;
		} else {
			next = new AfterInput(position);
			tokenDerivation = null;
		}
		if (position == 0) {
			first = next;
		}

		result = nonterminalSymbolDerivations.apply(start, first);
	}

	/**
	 * Create a stack frame with the given data.
	 * 
	 * <p>
	 * This is the function <emph>create</emph> from Scott and Johnstone (2010).
	 * </p>
	 * 
	 * @param slot
	 * @param stack
	 * @param token
	 * @param derivation
	 */
	public Stack push(final Slot slot, final Stack caller, final int token, final IntermediateDerivation<?> derivation) {
		Frame callee = frames.get(slot);
		if (callee == null) {
			callee = new Frame(slot, token);
			frames.put(slot, callee);
			gss.add(callee);
		}

		final Link link = callee.link(caller, derivation);

		final Set<SymbolDerivation<?, ?>> results = popped.get(callee);
		if (results != null) {
			for (final SymbolDerivation<?, ?> result : results) {
				link.schedule(this, result, slot);
			}
		}

		return callee;
	}

	public void scheduleLater(final Stack caller, final TerminalSymbolDerivation derivation) {
		if (!deadLater.contains(caller)) {
			deadLater.add(caller);
			future.add(new FutureProcess(caller, derivation));
		}
	}

	/**
	 * @param production
	 * @param action
	 * @param caller
	 * @param derivation
	 */
	public void scheduleNow(final Slot slot, final Stack caller, final IntermediateDerivation<?> derivation) {
		if (!deadNow(slot, caller)) {
			active.add(new SlotProcess(slot, caller, derivation));
		}
	}

	public void writeGSS(final String file) {
		final GraphBuilder builder = new GraphBuilder();
		for (final Stack stack : gss) {
			builder.visit(stack);
		}
		try {
			builder.write(new PrintWriter(file));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
