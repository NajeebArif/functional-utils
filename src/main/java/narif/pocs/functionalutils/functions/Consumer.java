package narif.pocs.functionalutils.functions;

@FunctionalInterface
public interface Consumer<T> extends java.util.function.Consumer<T>{
	
	void accept(T t);

}
