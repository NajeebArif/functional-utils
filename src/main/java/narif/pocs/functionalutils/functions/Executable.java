package narif.pocs.functionalutils.functions;

/**
 * @author Najeeb
 * 
 * A Functional interface to represent an execution
 * @param <T>
 */
@FunctionalInterface
public interface Executable<T> {
	void exec();
}
