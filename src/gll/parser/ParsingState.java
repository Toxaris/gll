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
public class ParsingState implements State {
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

	/**
	 * The next position in the token stream.
	 */
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

	/**
	 * The start symbol of the grammar we are parsing.
	 */
	public Sort start;

	/**
	 * The derivation associated with the current token.
	 */
	public TerminalSymbolDerivation tokenDerivation;

	/**
	 * The return slot associated with the current token.
	 */
	public Slot tokenReturnSlot;

	/**
	 * The initial stack frame.
	 */
	private final Initial empty = new Initial();

	/**
	 * A cache to avoid recreating identical empty intermediate derivations.
	 */
	private final Cache1<Slot, EmptyIntermediateDerivation> emptyIntermediateDerivations = new Cache1<Slot, EmptyIntermediateDerivation>() {
		@Override
		protected EmptyIntermediateDerivation compute(final Slot label) {
			return new EmptyIntermediateDerivation(label, previous, next);
		}
	};

	/**
	 * The first position in the token stream.
	 */
	private Position first;

	/**
	 * The set of all GSS stacks ever created (for debugging!)
	 */
	private final Set<Stack> gss = new HashSet<Stack>();

	/**
	 * A cache to avoid recreating identical derivations for nonterminal
	 * symbols.
	 */
	private final Cache2<Sort, Position, NonterminalSymbolDerivation> nonterminalSymbolDerivations = new Cache2<Sort, Position, NonterminalSymbolDerivation>() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected NonterminalSymbolDerivation compute(final Sort sort, final Position first) {
			return new NonterminalSymbolDerivation(sort, first, previous);
		}
	};

	/**
	 * The previous position in the token stream.
	 */
	private Position previous = null;

	/**
	 * The current result of parsing.
	 */
	private NonterminalSymbolDerivation result;

	/**
	 * A cache to avoid recreating identical derivations for symbols.
	 */
	private final Cache2<Slot, Position, SymbolIntermediateDerivation> symbolIntermediateDerivations = new Cache2<Slot, Position, SymbolIntermediateDerivation>() {
		@Override
		protected SymbolIntermediateDerivation compute(final Slot label, final Position first) {
			return new SymbolIntermediateDerivation(label, first, previous);
		}
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SymbolIntermediateDerivation createBranch(final Slot slot, final IntermediateDerivation lhs,
			final SymbolDerivation<?, ?> rhs) {
		final Position first = lhs.getFirst();
		final Position middle = rhs.getFirst();

		final SymbolIntermediateDerivation result = symbolIntermediateDerivations.apply(slot, first);

		result.add(new Binary(slot, middle, lhs, rhs));

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntermediateDerivation createEmpty(final Slot slot) {
		return emptyIntermediateDerivations.apply(slot);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NonterminalSymbolDerivation createNonterminalSymbolDerivation(final Sort sort, final Position first,
			final ProductionDerivation derivation) {
		final NonterminalSymbolDerivation result = nonterminalSymbolDerivations.apply(sort, first);
		result.add(derivation);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TerminalSymbolDerivation createTokenDerivation() {
		return tokenDerivation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * {@inheritDoc}
	 */
	@Override
	public Initial empty() {
		gss.add(empty);
		return empty;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPosition() {
		return position;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NonterminalSymbolDerivation getResult() {
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void markPopped(final Frame frame, final SymbolDerivation<?, ?> result) {
		Set<SymbolDerivation<?, ?>> results = popped.get(frame);
		if (results == null) {
			results = new HashSet<SymbolDerivation<?, ?>>();
		}
		results.add(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * {@inheritDoc}
	 */
	@Override
	public Stack push(final Slot slot, final Stack caller, final int token, final IntermediateDerivation derivation) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void scheduleLater(final Stack caller, final TerminalSymbolDerivation derivation) {
		if (!deadLater.contains(caller)) {
			deadLater.add(caller);
			future.add(new FutureProcess(caller, derivation));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void scheduleNow(final Slot slot, final Stack caller, final IntermediateDerivation derivation) {
		if (!deadNow(slot, caller)) {
			active.add(new SlotProcess(slot, caller, derivation));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
