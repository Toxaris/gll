/**
 * 
 */
package cache;

import java.util.HashMap;
import java.util.Map;

/**
 * A base class for memoizing functions with one argument.
 * 
 * @author Tillmann Rendel
 */
public abstract class Cache1<A, B> implements Function1<A, B> {
	/**
	 * Memoized results.
	 */
	private final Map<A, B> store = new HashMap<A, B>();

	/**
	 * Apply the memoized function.
	 * 
	 * @param key
	 *            the argument
	 * @return the value
	 */
	public B apply(A key) {
		if (store.containsKey(key)) {
			return store.get(key);
		} else {
			B result = compute(key);
			store.put(key, result);
			return result;
		}
	}

	/**
	 * Compute the memoized function.
	 * 
	 * @param key
	 *            the argument
	 * @return the value
	 */
	protected abstract B compute(A key);

	/**
	 * Clear the memoization table.
	 */
	public void clear() {
		store.clear();
	}
}
