package narif.pocs.functionalutils.functions;

/**
 * @author Najeeb
 * 
 * A functional interface used to represent an Effect.
 *
 * @param <T>
 */
@FunctionalInterface
public interface Effect<T> { 
	void apply(T t);

}
