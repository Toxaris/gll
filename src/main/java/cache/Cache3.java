/**
 * 
 */
package cache;

/**
 * A base class for memoizing functions with three arguments.
 * 
 * @author Tillmann Rendel
 */
public abstract class Cache3<A, B, C, D> extends Cache1<A, Function2<B, C, D>> implements Function3<A, B, C, D> {
	protected Cache2<B, C, D> compute(final A key1) {
		return new Cache2<B, C, D>() {
			protected D compute(B key2, C key3) {
				return Cache3.this.compute(key1, key2, key3);
			}
		};
	}

	/**
	 * Compute the memoized function.
	 * 
	 * @param key1 the first argument
	 * @param key2 the second argument
	 * @param key3 the third argument
	 * @return the result
	 */
	protected abstract D compute(final A key1, final B key2, final C key3);

	/**
	 * Apply the memoized function.
	 * 
	 * @param key1 the first argument
	 * @param key2 the second argument
	 * @param key3 the third argument
	 * @return the result
	 */
	public D apply(final A key1, final B key2, final C key3) {
		return apply(key1).apply(key2, key3);
	}
}